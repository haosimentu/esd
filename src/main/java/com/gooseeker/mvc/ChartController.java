package com.gooseeker.mvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gooseeker.business.PipelineService;
import com.gooseeker.dao.beans.Overview;
import com.gooseeker.dao.beans.Summary;
import com.gooseeker.view.ChartConst;
import com.gooseeker.view.ChartEntity;

/**
 * @ClassName: ChartBuilder 
 * @Description: 用于生成简介页面的柱状图
 * @author Gavin 
 * @date 2014年11月5日 下午10:47:02 
 * @version 1.0
 */
@Controller
public class ChartController
{

    private PipelineService pipelineService;
    private static List<Summary> summaryList = new ArrayList<Summary>();
    private static Date summaryTime = null;
    private ReentrantLock lock = new ReentrantLock();
    
    @Autowired
    public void setPipelineService(PipelineService pipelineService)
    {
        this.pipelineService = pipelineService;
    }


    @RequestMapping("/brefDesc.chart")
    public ModelAndView getBrefDescChart(@RequestParam int pipeLineId)
    {
        ModelAndView mav = new ModelAndView();
        ChartEntity entity = new ChartEntity();
        
        // refresh the memory cache every 1 min. 
        // TODO extract method.
        try
        {
            lock.lock();
            if (null != ChartController.summaryList 
                && null != ChartController.summaryTime
                && 0 != ChartController.summaryList.size())
            {
                Date timeInterval = new Date(new Date().getTime() - ChartController.summaryTime.getTime());
                if (timeInterval.getTime() > 60*1000)
                {
                    ChartController.summaryList = pipelineService.queryPipelineSummary();
                    ChartController.summaryTime =  new Date();
                }
            }
            else
            {
                ChartController.summaryList = pipelineService.queryPipelineSummary();
                ChartController.summaryTime = new Date();
            }
        }
        catch (Exception e)
        {
            
        }
        finally
        {
            lock.unlock();
        }
        
        // find the summary for requestParam pipeLineId
        // TODO use Map instead for return type for queryPipelineSummary
        Summary summaryForPipeId = new Summary();
        for (Summary summary : ChartController.summaryList)
        {
            if (summary.getPipelineId() == pipeLineId)
            {
                summaryForPipeId = summary;
                break;
            }
        }
        
        entity.setWidth(300);
        entity.setHeight(300);
        entity.setCategoryAxisLabel("");
        entity.setValueAxisLabel("");
        entity.setTitle("");
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int normalTime = 0, warningTime = 0, unusedTimes = 0;
        if (null != summaryForPipeId.getOverviews())
        {
            for (Overview overview : summaryForPipeId.getOverviews())
            {
                switch (overview.getResult())
                {
                // normal state
                case 1:
                    normalTime += overview.getDurations();
                    break;
                    // warning state
                case -1:
                    warningTime += overview.getDurations();
                    break;
                    // unused state
                case 0:
                    unusedTimes += overview.getDurations();
                    break;
                default:
                    break;
                }
            }
        }
        unusedTimes += 60 * 60 - normalTime - warningTime; 
        dataset.setValue(normalTime, "green", "正常");
        dataset.setValue(warningTime, "red", "告警");
        dataset.setValue(unusedTimes, "gray", "未用");
        entity.setDataset(dataset);
        mav.addObject(ChartConst.BAR_CHART_ENTITY, entity);
        mav.setViewName("pipeLineChart.chart");
        return mav;
    }
}
