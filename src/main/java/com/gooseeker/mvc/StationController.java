package com.gooseeker.mvc;

import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.gooseeker.business.StationService;
import com.gooseeker.dao.beans.Station;
import com.gooseeker.util.Constants;
import com.gooseeker.util.JsonUtil;
import com.gooseeker.util.SeekerUser;

@Controller
public class StationController 
{
	private StationService stationService;
	
	@Autowired
	public void setStationService(StationService stationService) {
		this.stationService = stationService;
	}


	@RequestMapping("/engineer/station.html")
	public ModelAndView pipeline(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("station");
		modelAndView.addObject("menus", user.getMenus());
		modelAndView.addObject("name", user.getName());
		return modelAndView;
	}
	
	@RequestMapping("/engineer/stationQuery.html")
	public ModelAndView pipelineQuery(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		long  userId=user.getUserId();
		boolean flag=true;
		response.setContentType("application/json;charset=utf-8");
		String keyword = request.getParameter("keyword");
		int pageNum =request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		PrintWriter pw = response.getWriter();
		List<Station> stationList = stationService.findStations4Page(keyword,(pageNum-1)*Constants.PAGE_NUM,Constants.PAGE_NUM);
		int count = stationService.findStations4PageCount(keyword);
		String jsonData = JsonUtil.getJSONString(stationList);
		pw.println("{\"jsonData\":" +jsonData + ",\"userId\":\""+userId+ "\",\"success\":"+flag+ ",\"total\":\""+count+"\","+"\"pageSize\":\""+Constants.PAGE_NUM+"\","+"\"pageNo\":\""+pageNum+"\"}");
		
		return null;
	}
	
	@RequestMapping("/engineer/stationDelete.html")
	public ModelAndView pipelineDelete(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		
		response.setContentType("application/json;charset=utf-8");
		String ids = request.getParameter("ids");
		int result = 0;
		if(ids != null && !"".equals(ids))
		{
			String[] idList = ids.trim().split(",");
			for(String id : idList)
			{
				if("".equals(id))
				{
					continue;
				}
				result = stationService.deleteStation(Long.parseLong(id));
			}
		}
//		long id = Long.parseLong(request.getParameter("id") == null ? "0" : request.getParameter("id"));
		PrintWriter pw = response.getWriter();
		boolean flag=true;
		if(result > 0)
		{
			String jsonData = "删除成功";
			pw.println("{\"jsonData\":\"" +jsonData+ "\",\"success\":"+flag+"}");
		}
		else
		{
			flag=false;
			String jsonData = "删除失败";
			pw.println("{\"jsonData\":\"" +jsonData+ "\",\"success\":"+flag+"}");
		}
		
		return null;
	}
	
	@RequestMapping("/engineer/stationAdd.html")
	public ModelAndView accountAdd(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		boolean flag=true;
		PrintWriter pw = response.getWriter();
		//name:name,number:number,pipelineId:pipelineId,address:address,desc:desc
		String name = request.getParameter("name");
		String number = request.getParameter("number");
		long pipelineId = request.getParameter("pipelineId") == null ? 0L : Long.parseLong(request.getParameter("pipelineId"));
		String address = request.getParameter("address");
		String subAddress = request.getParameter("subAddress");
		String desc = request.getParameter("desc");
		
		long result = stationService.insertStation(name, pipelineId, number, address,subAddress, desc);
		
		if(result > 0)
		{
			String jsonData = "新增成功";
			pw.println("{\"jsonData\":\"" +jsonData+ "\",\"success\":"+flag+"}");
		}
		else
		{
			flag = false;
			String jsonData = "新增失败";
			pw.println("{\"jsonData\":\"" +jsonData+ "\",\"success\":"+flag+"}");
		}
		
		return null;
	}
	
	@RequestMapping("/engineer/getStation.html")
	public ModelAndView getStation(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		long id=request.getParameter("id") == null ? 0 : Long.parseLong(request.getParameter("id"));
		boolean flag=true;
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json;charset=utf-8");
		Station station = stationService.getStation(id);
		String jsonData = JsonUtil.getJSONString(station);
		pw.println("{\"jsonData\":" +jsonData + ",\"success\":"+flag+"}");
		
		return null;
	}
	
	@RequestMapping("/engineer/stationUpdate.html")
	public ModelAndView accountUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		boolean flag=true;
		PrintWriter pw = response.getWriter();
		//{id:id,name:name,number:number,pipelineId:pipelineId,address:address,desc:desc}
		long id = request.getParameter("id") == null ? 0 : Long.parseLong(request.getParameter("id"));
		String name = request.getParameter("name");
		String number = request.getParameter("number");
		String pipelineId = request.getParameter("pipelineId");
		String address = request.getParameter("address");
		String desc = request.getParameter("desc");
		int result = stationService.updateStation(id, name, number, pipelineId, address,desc);
		
		if(result > 0)
		{
			String jsonData = "更新成功";
			pw.println("{\"jsonData\":\"" +jsonData+ "\",\"success\":"+flag+"}");
		}
		else
		{
			flag = false;
			String jsonData = "更新失败";
			pw.println("{\"jsonData\":\"" +jsonData+ "\",\"success\":"+flag+"}");
		}
		
		return null;
	}
	
	@RequestMapping("/engineer/listStation.html")
	public ModelAndView listStationById(HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		long  userId=user.getUserId();
		boolean flag=true;
		response.setContentType("application/json;charset=utf-8");
		long pipelineId = Long.parseLong(request.getParameter("pipelineId"));
		int pageNum =request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		PrintWriter pw = response.getWriter();
		List<Station> stationList = stationService.listStations4Page(pipelineId,(pageNum-1)*Constants.PAGE_NUM,Constants.PAGE_NUM);
		int count = stationService.listStations4PageCount(pipelineId);
		String jsonData = JsonUtil.getJSONString(stationList);
		pw.println("{\"jsonData\":" +jsonData + ",\"userId\":\""+userId+ "\",\"success\":"+flag+ ",\"total\":\""+count+"\","+"\"pageSize\":\""+Constants.PAGE_NUM+"\","+"\"pageNo\":\""+pageNum+"\"}");
		
		return null;
	
	}
	
}
