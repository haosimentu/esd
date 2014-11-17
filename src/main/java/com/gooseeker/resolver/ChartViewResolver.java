package com.gooseeker.resolver;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

import com.gooseeker.view.ChartView;

/**
 * @ClassName: ChartViewResolver 
 * @Description: 用于分发图表的请求
 * @author Gavin 
 * @date 2014年11月5日 下午10:59:43 
 * @version 1.0
 */
public class ChartViewResolver extends AbstractCachingViewResolver
{
    /**
     * 后缀属性，在spring-mvc.xml中配置
     */
    private String suffix;
    
    @Autowired
    public void setSuffix(String suffix)
    {
        this.suffix = suffix;
    }

    @Override
    protected View loadView(String viewName, Locale locale) throws Exception
    {
        View view = null;
        if (viewName.endsWith(suffix))
        {
            view = new ChartView();
        }
        return view;
    }

}
