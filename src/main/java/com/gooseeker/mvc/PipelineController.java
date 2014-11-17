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

import com.gooseeker.business.PipelineService;
import com.gooseeker.dao.beans.Pipeline;
import com.gooseeker.util.Constants;
import com.gooseeker.util.JsonUtil;
import com.gooseeker.util.SeekerUser;

@Controller
public class PipelineController 
{
	private PipelineService pipelineService;
	
	@Autowired
	public void setPipelineService(PipelineService pipelineService) {
		this.pipelineService = pipelineService;
	}

	@RequestMapping("/engineer/pipeline.html")
	public ModelAndView pipeline(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		
		
		//打标签使用的java结束
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pipeline");
		modelAndView.addObject("menus", user.getMenus());
		modelAndView.addObject("name", user.getName());
		return modelAndView;
	}
	
	@RequestMapping("/engineer/pipelineQuery.html")
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
		List<Pipeline> pipelineList = pipelineService.findPipelines4Page(keyword,(pageNum-1)*Constants.PAGE_NUM,Constants.PAGE_NUM);
		int count = pipelineService.findPipelines4PageCount(keyword);
		String jsonData = JsonUtil.getJSONString(pipelineList);
		pw.println("{\"jsonData\":" +jsonData + ",\"userId\":\""+userId+ "\",\"success\":"+flag+ ",\"total\":\""+count+"\","+"\"pageSize\":\""+Constants.PAGE_NUM+"\","+"\"pageNo\":\""+pageNum+"\"}");
		
		return null;
	}
	
	@RequestMapping("/engineer/pipelineDelete.html")
	public ModelAndView pipelineDelete(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		
		response.setContentType("application/json;charset=utf-8");
		long id = Long.parseLong(request.getParameter("id") == null ? "0" : request.getParameter("id"));
		PrintWriter pw = response.getWriter();
		
		int result = pipelineService.deletePipeline(id);
		
		boolean flag=true;
		if(result > 0)
		{
			String jsonData = "删除成功";
			pw.println("{\"jsonData\":\"" +jsonData+ "\",\"success\":"+flag+"}");
		}
		else
		{
			flag=false;
			String jsonData = "删除流水线错误，请先删除关联工位！";
			pw.println("{\"jsonData\":\"" +jsonData+ "\",\"success\":"+flag+"}");
		}
		
		return null;
	}
	
	@RequestMapping("/engineer/getPipeline.html")
	public ModelAndView getPipeline(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		long pipelineId = request.getParameter("id") == null ? 0 : Long.parseLong(request.getParameter("id"));
		boolean flag=true;
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json;charset=utf-8");
		Pipeline pipeline = pipelineService.getPipeline(pipelineId);
		String jsonData = JsonUtil.getJSONString(pipeline);
		pw.println("{\"jsonData\":" +jsonData + ",\"success\":"+flag+"}");
		
		return null;
	}
	
	@RequestMapping("/engineer/pipelineUpdate.html")
	public ModelAndView pipelineUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		boolean flag=true;
		PrintWriter pw = response.getWriter();
		long id = request.getParameter("id") == null ? 0 : Long.parseLong(request.getParameter("id"));
		String name = request.getParameter("name");
		String number = request.getParameter("number");
		int stations = request.getParameter("stations") == null ? 0 : Integer.parseInt(request.getParameter("stations"));
		String desc = request.getParameter("desc");
		
		int result = pipelineService.updatePipeline(id, name, number,stations, desc);
		
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
	
	@RequestMapping("/engineer/pipelineAdd.html")
	public ModelAndView accountAdd(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		boolean flag=true;
		PrintWriter pw = response.getWriter();
		String name = request.getParameter("name");
		String number = request.getParameter("number");
		String desc = request.getParameter("desc");
		
		long result = pipelineService.insertPipeline(name, number, desc);
		
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
}
