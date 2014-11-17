package com.gooseeker.modbus;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Observable;
import java.util.TooManyListenersException;

public class ModbusUtils extends Observable implements SerialPortEventListener
{
	/**
	 * 默认串口，可配置 TODO 添加配置文件，用于动态加载
	 */
	private static final String DEFAULT_COM_PORT = "COM3";
	/**
	 * 读取数据延迟时间，单位ms
	 */
	private static final int DELAY_READ_DATA = 300;
	/**
	 * 打开串口的等待超时时间
	 */
	private static final int DEFAULT_OPEN_TIMEOUT = 10000;
	/**
	 * 默认波特率
	 */
	private static final int DEFAULT_COM_BUAD_RATE = 9600;
	/**
	 * 默认数据位
	 */
	private static final int DEFAULT_COM_DATA_BIT = 8;
	/**
	 * 默认停止位
	 */
	private static final int DEFAULT_COM_STOP_BIT = 1;
	/**
	 * 默认校验位
	 */
	private static final int DEFAULT_COM_PARITY = 0;
	/**
	 * 读取的数据个数
	 */
	private int numBytes;
	/**
	 * 接收缓冲区
	 */
	private static byte[] readBuffer = new byte[1024];
	/**
	 * 接收流句柄
	 */
	private static InputStream inputStream;
	/**
	 * 发送流句柄
	 */
	private static OutputStream outputStream;
	/**
	 * 已打开的串口
	 */
	static SerialPort serialPort;
	/**
	 * 串口打开标记
	 */
	boolean isOpen = false;

	public boolean isOpen()
	{
		return isOpen;
	}

	public ModbusUtils()
	{
		isOpen = false;
	}

	public void open()
	{
		if (isOpen)
		{
			close();
		}
		try
		{
			CommPortIdentifier portId = CommPortIdentifier
					.getPortIdentifier(DEFAULT_COM_PORT);
			serialPort = (SerialPort) portId.open("SerialReader",
					DEFAULT_OPEN_TIMEOUT);
			inputStream = serialPort.getInputStream();
			outputStream = serialPort.getOutputStream();
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			serialPort.setSerialPortParams(DEFAULT_COM_BUAD_RATE,
					DEFAULT_COM_DATA_BIT, DEFAULT_COM_STOP_BIT,
					DEFAULT_COM_PARITY);

			isOpen = true;
		} catch (PortInUseException e)
		{
			System.out.println("端口被占用");
		} catch (TooManyListenersException e)
		{
			System.out.println("监听者过多");
		} catch (UnsupportedCommOperationException e)
		{
			System.out.println("端口操作命令不支持");
		} catch (NoSuchPortException e)
		{
			System.out.println("端口不存在");
		} catch (IOException e)
		{
			System.out.println("串口打开失败");
		}
	}

	public void send(byte[] cmd)
	{
		try
		{
			// outputStream = serialPort.getOutputStream();
			outputStream.write(cmd);
			outputStream.flush();
		} catch (IOException e)
		{
			System.out.println("发送失败");
			return;
		}
	}

	public void close()
	{
		if (isOpen)
		{
			try
			{
				serialPort.notifyOnDataAvailable(false);
				serialPort.removeEventListener();
				inputStream.close();
				outputStream.close();
				serialPort.close();
				isOpen = false;
			} catch (IOException ex)
			{
				System.out.println("关闭失败");
			}
		}
	}

	public void serialEvent(SerialPortEvent event)
	{
		try
		{
			// 端口读入数据事件触发后,等待n毫秒后再读取,以便让数据一次性读完
			Thread.sleep(DELAY_READ_DATA);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		switch (event.getEventType())
		{
		case SerialPortEvent.BI: // 10
		case SerialPortEvent.OE: // 7
		case SerialPortEvent.FE: // 9
		case SerialPortEvent.PE: // 8
		case SerialPortEvent.CD: // 6
		case SerialPortEvent.CTS: // 3
		case SerialPortEvent.DSR: // 4
		case SerialPortEvent.RI: // 5
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2
			break;
		case SerialPortEvent.DATA_AVAILABLE: // 1
			try
			{
				// 有数据可读
				while (inputStream.available() > 0)
				{
					numBytes = inputStream.read(readBuffer);
				}

				for (int i = 0; i < numBytes; i++)
				{
					System.out.printf("%02x ", readBuffer[i] & 0xff);
				}
				
				System.out.println();
				
				changeMessage(readBuffer, numBytes);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			break;
		}
	}
	/**
	 * 监听者
	 * @param message
	 * @param length
	 */
	public void changeMessage(byte[] message, int length)
	{
		setChanged();
		byte[] temp = new byte[length];
		System.arraycopy(message, 0, temp, 0, length);
		notifyObservers(temp);
	}
	/**
	 * 列出当前串口
	 */
	public void listPorts()
	{
		Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();
		while (portEnum.hasMoreElements())
		{
			CommPortIdentifier portIdentifier = (CommPortIdentifier) portEnum
					.nextElement();

			System.out.println(portIdentifier.getName());
		}
	}

	public void openSerialPort()
	{
		open();
	}

	public static void main(String[] args)
	{
		ModbusUtils mo = new ModbusUtils();
		mo.listPorts();
		mo.open();
		byte[] cmd = CRC16Utils.getInstance().modbus_crc16("01 04 00 00 00 02");
		mo.send(cmd);

		while (true)
		{
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			mo.send(cmd);
		}
	}
}
