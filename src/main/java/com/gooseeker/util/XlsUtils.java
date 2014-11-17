package com.gooseeker.util;

public class XlsUtils {
	
//	public static synchronized boolean export2File4Xls(File file,List<String> columns, List<Tag> tags,Map<String, List<String>> body)
//	{
//		try 
//		{
//			HSSFWorkbook wb = new HSSFWorkbook();
//			HSSFCellStyle style = wb.createCellStyle();
//			style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
//			style.setWrapText(true);
//			HSSFSheet sheet = wb.createSheet("mysheet");
//			sheet.setDefaultColumnWidth(16);
//			List<String> heads = new ArrayList<String>();
//			Map<String, String> tagMap = new HashMap<String, String>();
//			heads.addAll(columns);
////			for(Tag t : tags)
////			{
////				heads.add(t.getTagName());
////				tagMap.put(String.valueOf(t.getId()), t.getTagName()+"|"+(heads.size()-1));
////				heads.add(Constants.TONALITY_WORD);
////			}
//			
//			exportHeads(sheet,heads);
//			
//			exportBodys(sheet,body,tagMap,style);
//			FileOutputStream fileOut = new FileOutputStream(file);
//			wb.write(fileOut);
//			fileOut.close();
//			return true;
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	private static void exportBodys(HSSFSheet sheet,
//			Map<String, List<String>> body, Map<String, String> tagMap, HSSFCellStyle style) {
//		Iterator<String> it = body.keySet().iterator();
//		int i=1;
//		while(it.hasNext())
//		{
//			HSSFRow row = sheet.createRow(i);
//			row.setHeightInPoints(30.0F);
//			String key = it.next();
//			String[] keys = key.split(Constants.DEFAULT_SPLIT_WORD);
//			for(int j=0;j<keys.length;j++)
//			{
//				HSSFCell cell = row.createCell(j);
//				cell.setCellStyle(style);
//				cell.setCellValue(keys[j]);
//			}
//			
//			List<String> ids = body.get(key);
//			for(String idd : ids)
//			{
//				String[] tmp = idd.split(Constants.COMMON_SPLIT_WORD);
//				String tg = tagMap.get(tmp[0]);
//				if(null == tg)
//				{
//					continue;
//				}
//				String[] ts = tg.split("|");
//				row.createCell(Integer.parseInt(ts[1])).setCellValue("Y");
//				if(tmp.length == 2)
//				{
//					row.createCell(Integer.parseInt(ts[1])+1).setCellValue(tmp[1]);
//				}
//			}
//			
//			i++;
//		}
//	}
//
//	private static void exportHeads(HSSFSheet sheet, List<String> heads) {
//		HSSFRow row = sheet.createRow(0);
//		for(int i=0;i<heads.size();i++)
//		{
//			row.createCell(i).setCellValue(heads.get(i));
//		}
//	}
}
