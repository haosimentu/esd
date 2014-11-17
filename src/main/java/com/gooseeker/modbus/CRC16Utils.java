package com.gooseeker.modbus;

import java.nio.ByteBuffer;

public class CRC16Utils
{
	final short P_16_REFLECTED = (short) 0xA001;

	private static short[] crc_tab_8005_reflected = new short[256];

	private static CRC16Utils theInstance = null;
	
	public static CRC16Utils getInstance()
	{
		if (null == theInstance)
		{
			synchronized (CRC16Utils.class)
			{
				if (null == theInstance)
				{
					theInstance = new CRC16Utils();
				}
			}
		}
		
		return theInstance;
	}
	
	private CRC16Utils()
	{
		init_all_tab();
	}
	
	private short rshiftu16(short value, int nb)
	{
		return (short) ((value >> nb) & ~(((short) 0x8000) >> (nb - 1)));
	}

	private void init_crc16_reflected_tab(short[] table, short polynom)
	{
		int i, j;
		short crc16;

		for (i = 0; i < 256; i++)
		{
			crc16 = (short) i;

			for (j = 0; j < 8; j++)
			{
				if ((crc16 & 0x0001) != 0)
					crc16 = (short) (rshiftu16(crc16, 1) ^ polynom);
				else
					crc16 = rshiftu16(crc16, 1);
			}
			table[i] = crc16;
		}
	}

	private void init_all_tab()
	{
		init_crc16_reflected_tab(crc_tab_8005_reflected, P_16_REFLECTED);
	}

	private short update_crc16_reflected(short[] table, short crc, short c)
	{
		short short_c;

		short_c = (short) (0x00ff & c);

		return (short) (rshiftu16(crc, 8) ^ table[(crc ^ short_c) & 0xff]);
	}

	private short update_crc16_A001(short crc, short c)
	{
		return update_crc16_reflected(crc_tab_8005_reflected, crc, c);
	}

	private short calculate_crc16_Modbus(byte[] p, int length)
	{
		short crc;
		int i;

		crc = (short) 0xFFFF;

		for (i = 0; i < length; i++)
		{
			crc = update_crc16_A001(crc, p[i]);
		}
	
		return crc;
	}
	
	private ByteBuffer string2Hex(String command)
	{
		String[] cmd = command.split(" ");
		int len = cmd.length;
		ByteBuffer bb = ByteBuffer.allocate(len + 2);
		
		char hi = 0;
		char lo = 0;
		byte temp = 0;
		for (int i = 0; i < len; i++)
		{
			hi = cmd[i].charAt(0);
			lo = cmd[i].charAt(1);
			
			temp = (byte)((toHex(hi) << 4) | toHex(lo));
			bb.put(temp);
		}
		
		return bb;
	}
	
	public byte toHex(char c)
	{
		char t = Character.toUpperCase(c);
		return (byte)"0123456789ABCDEF".indexOf(t);
	}
	
	public byte[] modbus_crc16(String cmd)
	{
		ByteBuffer bb = string2Hex(cmd);
		
		short crc16 = calculate_crc16_Modbus(bb.array(), bb.capacity() - 2);

		short swapCrc16 = Short.reverseBytes(crc16);
		bb.putShort(swapCrc16);
		
		return bb.array();
	}

	public static void main(String[] args)
	{
		String cmdStr = "01 04 00 00 00 02";
		byte[] cmd = CRC16Utils.getInstance().modbus_crc16(cmdStr);
		System.out.println(cmd[cmd.length - 1]);
	}
}
