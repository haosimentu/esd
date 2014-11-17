package com.gooseeker.view;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;

public class ChartEntity
{
    /** 图表的宽度 */
    private int width;
    
    /** 图表的高度 */
    private int height;
    
    /** 图表的标题 */
    private String title;
    
    /** 目录轴显示的标签（x轴） */
    private String categoryAxisLabel;
    
    /** 数值轴显示的标签（y轴）*/
    private String valueAxisLabel;
    
    /** 数据集，由逻辑类生成 */
    private CategoryDataset dataset;
    
    /** 图标方向 水平，垂直 */
    private PlotOrientation orientation = PlotOrientation.VERTICAL;
    public int getWidth()
    {
        return width;
    }
    public void setWidth(int width)
    {
        this.width = width;
    }
    public int getHeight()
    {
        return height;
    }
    public void setHeight(int height)
    {
        this.height = height;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getCategoryAxisLabel()
    {
        return categoryAxisLabel;
    }
    public void setCategoryAxisLabel(String categoryAxisLabel)
    {
        this.categoryAxisLabel = categoryAxisLabel;
    }
    public String getValueAxisLabel()
    {
        return valueAxisLabel;
    }
    public void setValueAxisLabel(String valueAxisLabel)
    {
        this.valueAxisLabel = valueAxisLabel;
    }
    public CategoryDataset getDataset()
    {
        return dataset;
    }
    public void setDataset(CategoryDataset dataset)
    {
        this.dataset = dataset;
    }
    public PlotOrientation getOrientation()
    {
        return orientation;
    }
    public void setOrientation(PlotOrientation orientation)
    {
        this.orientation = orientation;
    }
}
