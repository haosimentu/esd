package com.gooseeker.mvc;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gooseeker.business.UserService;
import com.gooseeker.dao.beans.Userr;
import com.gooseeker.util.Constants;
import com.gooseeker.util.JsonUtil;
import com.gooseeker.util.SeekerUser;

@Controller
public class AdminController 
{
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	@RequestMapping("/admin/account.html")
	public ModelAndView account(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("menus", user.getMenus());
		modelAndView.addObject("name", user.getName());
		modelAndView.setViewName("admin");
		return modelAndView;
	}
	
	@RequestMapping("/admin/accountQuery.html")
	public ModelAndView accountQuery(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		long  userId=user.getUserId();
		boolean flag=true;
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json;charset=utf-8");
		String keyword = request.getParameter("keyword");
		int pageNum =request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		
		List<Userr> userList = userService.findUsers4Page(keyword,(pageNum-1)*Constants.PAGE_NUM,Constants.PAGE_NUM);
		int count = userService.findUsers4PageCount(keyword);
		String jsonData = JsonUtil.getJSONString(userList);
		pw.println("{\"jsonData\":" +jsonData + ",\"userId\":\""+userId+ "\",\"success\":"+flag+ ",\"total\":\""+count+"\","+"\"pageSize\":\""+Constants.PAGE_NUM+"\","+"\"pageNo\":\""+pageNum+"\"}");
		
		return null;
	}
	
	@RequestMapping("/admin/accountUpdate.html")
	public ModelAndView accountUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		boolean flag=true;
		PrintWriter pw = response.getWriter();
//		id:id,username:username,password:password,email:email,mobile:mobile
		long id = request.getParameter("userid") == null ? 0 : Long.parseLong(request.getParameter("userid"));
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String roles = request.getParameter("roles");
		String[] rs = roles.split("\\|");
		List<String> roleList = new ArrayList<String>();
		for(String r : rs)
		{
			if("".equals(r) || null == r)
			{
				continue;
			}
			roleList.add(r);
		}
		
		int result = userService.updateUserWithRole(id, username, password, email, mobile,roleList);
		
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
	
	@RequestMapping("/admin/accountDelete.html")
	public ModelAndView accountDelete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		long id = Long.parseLong(request.getParameter("id") == null ? "0" : request.getParameter("id"));
		PrintWriter pw = response.getWriter();
		
		int result = userService.deleteUserById(id);
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
	
	@RequestMapping("/admin/accountAdd.html")
	public ModelAndView accountAdd(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		boolean flag=true;
		PrintWriter pw = response.getWriter();
		String username = request.getParameter("username");
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String roles = request.getParameter("roles");
		String[] rs = roles.split("\\|");
		List<String> roleList = new ArrayList<String>();
		for(String r : rs)
		{
			if("".equals(r) || null == r)
			{
				continue;
			}
			roleList.add(r);
		}
		long result = userService.insertUserWithRole(username,account, password, email, mobile,roleList);
		
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
	
	@RequestMapping("/admin/getUser.html")
	public ModelAndView getUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		long  userId=request.getParameter("id") == null ? 0 : Long.parseLong(request.getParameter("id"));
		boolean flag=true;
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json;charset=utf-8");
		Userr u = userService.getUserById(userId);
		String jsonData = JsonUtil.getJSONString(u);
		pw.println("{\"jsonData\":" +jsonData + ",\"success\":"+flag+"}");
		
		return null;
	}
}
