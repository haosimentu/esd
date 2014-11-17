package com.gooseeker.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gooseeker.dao.beans.History;

public class XlsxUtils {
	
	/**
	 * 
	 * @param file
	 * @param columns
	 * @param tags
	 * @param body
	 * @return
	 */
	public static synchronized boolean export2File4Xlsx(File file, List<History> monitors)
	{
		
		try 
		{
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("monitor");
			XSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
			style.setWrapText(true);
			sheet.setDefaultColumnWidth(16);
			
			List<String> heads = new ArrayList<String>();
			heads.add("流水线");
			heads.add("工位");
			heads.add("监测结果");
			heads.add("监测时间");
			XSSFRow head = sheet.createRow(0);
			for(int i=0;i<heads.size();i++)
			{
				head.setHeightInPoints(30.0F);
				head.createCell(i).setCellValue(heads.get(i));
			}
			
			int bodyLength = monitors.size();
			for(int j=0;j<bodyLength;j++)
			{
				XSSFRow body = sheet.createRow(j+1);
				body.createCell(0).setCellValue(monitors.get(j).getPipelineName());
				body.createCell(1).setCellValue(monitors.get(j).getStationName());
				body.createCell(2).setCellValue(monitors.get(j).getResult());
				body.createCell(3).setCellValue(Commons.dateFormat(monitors.get(j).getMonitorTime(),"yyyy-MM-dd HH:mm:ss"));
			}
			
			FileOutputStream fileOut = new FileOutputStream(file);
			wb.write(fileOut);
			fileOut.close();
			
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
