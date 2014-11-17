package com.gooseeker.view;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.renderer.category.BarRenderer;
import org.springframework.web.servlet.view.AbstractView;

public class ChartView extends AbstractView
{

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        ChartEntity chartEntity = (ChartEntity)model.get(ChartConst.BAR_CHART_ENTITY);
        
        JFreeChart chart = ChartFactory.createBarChart(chartEntity.getTitle(), 
                                                        chartEntity.getCategoryAxisLabel(), 
                                                        chartEntity.getValueAxisLabel(), 
                                                        chartEntity.getDataset(), 
                                                        chartEntity.getOrientation(), 
                                                        true, 
                                                        true, 
                                                        false);
        chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));
        chart.getCategoryPlot().getDomainAxis().setTickLabelFont(new Font("微软雅黑", Font.BOLD, 11));
        chart.getCategoryPlot().getDomainAxis().setLabelFont(new Font("微软雅黑", Font.BOLD, 12));
//        chart.getCategoryPlot().getRangeAxis().setTickLabelFont(new Font("微软雅黑", Font.BOLD, 11));
//        chart.getCategoryPlot().getRangeAxis().setLabelFont(new Font("微软雅黑", Font.BOLD, 12));
        chart.getCategoryPlot().getRangeAxis().setVisible(false);
        chart.getCategoryPlot().setBackgroundAlpha(0.0f);
        chart.getLegend().setVisible(false);
        
        ValueAxis valueAxis = chart.getCategoryPlot().getRangeAxis();
        valueAxis.setLowerBound(0);
        valueAxis.setAutoRangeMinimumSize(1);
        valueAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
        renderer.setSeriesPaint(0, Color.green);
        renderer.setSeriesPaint(1, Color.red);
        renderer.setSeriesPaint(2, Color.gray);

        ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, chartEntity.getWidth(), chartEntity.getHeight());
    }

}
