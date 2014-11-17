package com.gooseeker.mvc;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gooseeker.business.HistoryService;
import com.gooseeker.dao.beans.History;
import com.gooseeker.util.Commons;
import com.gooseeker.util.Constants;
import com.gooseeker.util.JsonUtil;
import com.gooseeker.util.SeekerUser;
import com.gooseeker.util.XlsxUtils;

@Controller
public class HistoryController
{
	private HistoryService historyService;
	
	@Autowired
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	@RequestMapping("/usr/history.html")
	public ModelAndView history(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		
		//打标签使用的java结束
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("history");
		modelAndView.addObject("menus", user.getMenus());
		modelAndView.addObject("name", user.getName());
		return modelAndView;
	}
	
	@RequestMapping("/user/historyQuery.html")
	public ModelAndView historyQuery(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		long  userId=user.getUserId();
		boolean flag=true;
		String jsonData = null;
		PrintWriter pw = response.getWriter();
		String keyword = request.getParameter("keyword");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		response.setContentType("application/json;charset=utf-8");
		int pageNum =request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		List<History> historyList = historyService.queryHistoryByName4Page(keyword, startTime, endTime, (pageNum-1)*Constants.PAGE_NUM,Constants.PAGE_NUM);
		int count = historyService.queryHistoryByName4PageCount(keyword, startTime, endTime);
		jsonData = JsonUtil.getJSONString(historyList);
		pw.println("{\"jsonData\":" +jsonData + ",\"userId\":\""+userId+ "\",\"success\":"+flag+ ",\"total\":\""+count+"\","+"\"pageSize\":\""+Constants.PAGE_NUM+"\","+"\"pageNo\":\""+pageNum+"\"}");
		
		return null;
	}
	
	@RequestMapping("/user/historyExport.html")
	public ModelAndView historyExport(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		long  userId=user.getUserId();
		boolean flag=true;
		PrintWriter pw = response.getWriter();
		String keyword = request.getParameter("keyword");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		response.setContentType("application/json;charset=utf-8");
		List<History> historyList = historyService.queryHistoryByName4Page(keyword, startTime, endTime, 0,Constants.EXPORT_RECORD_NUM);
		String fileName = "monitor_"+userId+"_"+keyword+"_"+Commons.dateFormat(new Date(),"yyyyMMddHHmmss")+".xlsx";
		File path = new File(System.getProperty("webapp.root")+"/exports");
		if(!path.exists())
		{
			path.mkdirs();
		}
		boolean result = XlsxUtils.export2File4Xlsx(new File(path,fileName), historyList);
		if(result)
		{
			String jsonData = "导出完成";
			pw.println("{\"jsonData\":\"" +jsonData + "\",\"success\":"+flag+ ",\"file\":\""+fileName+"\"}");
		}
		else
		{
			String jsonData = "导出失败，数据过大";
			pw.println("{\"jsonData\":\"" +jsonData + "\",\"success\":"+flag+ ",\"file\":\""+fileName+"\"}");
		}
		
		return null;
	}
	
}
