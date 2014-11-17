package com.gooseeker.mvc;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gooseeker.business.PipelineService;
import com.gooseeker.business.StationService;
import com.gooseeker.dao.beans.Monitor;
import com.gooseeker.dao.beans.Pipeline;
import com.gooseeker.dao.beans.Station;
import com.gooseeker.dao.beans.Summary;
import com.gooseeker.util.Constants;
import com.gooseeker.util.JsonUtil;
import com.gooseeker.util.SeekerUser;

@Controller
public class MonitorController 
{
    private StationService stationService;
    private PipelineService pipelineService;
    
    @Autowired
    public void setPipelineService(PipelineService pipelineService)
    {
        this.pipelineService = pipelineService;
    }


    @Autowired
    public void setStationService(StationService stationService)
    {
        this.stationService = stationService;
    }


    @RequestMapping("/user/monitor.html")
	public ModelAndView monitor(){
        SeekerUser user = authenticate();
		if (null == user)
        {
            return new ModelAndView("login");
        }
		
		List<Pipeline> pipelines = pipelineService.getAllPipelines();
		Map<Long, List<Station>> stationsMap = getStationStatus();
		//打标签使用的java结束
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("monitor");
		modelAndView.addObject("menus", user.getMenus());
		modelAndView.addObject("pipelines", pipelines);
		modelAndView.addObject("stationsMap", stationsMap);
		modelAndView.addObject("name", user.getName());
		return modelAndView;
	}


    private Map<Long, List<Station>> getStationStatus()
    {
        Map<Long, List<Station>> stationsMap = stationService.queryAllStationInfo();
		List<Summary> summaries = pipelineService.queryPipelineSummary();
		for (Summary summary : summaries)
        {
            if (null == summary || 0 == summary.getMonitors().size())
            {
                continue;
            }
            long pipeId = summary.getPipelineId();
            List<Monitor> monitorList = summary.getMonitors();
            List<Station> stationList = stationsMap.get(pipeId);
            if (null == monitorList 
                || 0 == monitorList.size()
                || null == stationList
                || 0 == stationList.size())
            {
                continue;
            }
                
            for (Station station : stationList)
            {
                for (Monitor monitor : monitorList)
                {
                    if (station.getId() == monitor.getStationId())
                    {
                        station.setState(monitor.getResult());
                    }
                }
            }
        }
        return stationsMap;
    }


    private SeekerUser authenticate()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		return user;
    }
	
    @RequestMapping("/user/refreshpipeline.html")
    public ModelAndView monitorRefresh(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        SeekerUser user = authenticate();
        if (null == user)
        {
            return new ModelAndView("login");
        }
        
        Map<Long, List<Station>> stationsMap = getStationStatus();
        StringBuffer jsonResp = new StringBuffer();
        Set<Entry<Long, List<Station>>> stationsSet = stationsMap.entrySet();
        List<Station> allStations = new ArrayList<Station>();
        for (Entry<Long, List<Station>> entry : stationsSet)
        {
            allStations.addAll(entry.getValue());
        }

        jsonResp.append("{\"result\":");
        jsonResp.append(JsonUtil.getJSONString(allStations)).append(",");
        jsonResp.append("\"success\":\"").append(true).append("\"}");
        
        PrintWriter pw = response.getWriter();
        pw.println(jsonResp.toString());
        return null;
    }
}
