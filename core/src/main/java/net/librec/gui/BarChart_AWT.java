package net.librec.gui;

import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

// adapted from https://www.tutorialspoint.com/jfreechart/jfreechart_bar_chart.htm
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.SlidingCategoryDataset;
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities;

import net.librec.job.JobOutput;
import net.librec.util.ConfirmationUtil; 

public class BarChart_AWT extends JFrame {

	private DefaultCategoryDataset data = new DefaultCategoryDataset();
	private List<JobOutput> jobOutputs;
	private String dataset;

	public BarChart_AWT( String applicationTitle , String algorithm ) {
		super( applicationTitle );  
	}

	public BarChart_AWT(List<JobOutput> jobOutputs, String applicationTitle, String dataset) {
		super( applicationTitle );
		this.jobOutputs = jobOutputs;
		this.dataset = dataset;
		setChart();
	}
	
	private void setChart()
	{
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent)
			{
				if (ConfirmationUtil.getConfirmation("Graph will be lost. Are you sure you want to close?", "Really Closing?"))
					dispose();
			}
		});
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Double screenWidth = screenSize.getWidth();
		Double screenHeight = screenSize.getHeight();
		int initWidth = 1280;
		int initHeight = 720;
		addJobOutputs(jobOutputs);
		SlidingCategoryDataset slidingData = new SlidingCategoryDataset(data, 0, data.getColumnCount());
		
		JFreeChart barChart = ChartFactory.createBarChart(
				dataset,           
				"Metric",            
				"Value",            
				slidingData,          
				PlotOrientation.VERTICAL,           
				true, true, false); // include legend, tooltips?, URLs?
		BarRenderer br = (BarRenderer) barChart.getCategoryPlot().getRenderer();
		br.setMaximumBarWidth(.05);
		ChartPanel chartPanel = new ChartPanel( barChart );
		chartPanel.setMaximumDrawWidth( screenWidth.intValue() );
		chartPanel.setMaximumDrawHeight( screenHeight.intValue() );
		chartPanel.setPreferredSize(new Dimension(initWidth, initHeight));

		setContentPane( chartPanel );
	}
	
	public void setView()
	{
		pack();        
		RefineryUtilities.centerFrameOnScreen(this);        
		setVisible(true);
	}
	
	public void addJobOutputs(List<JobOutput> jobOutputs)
	{
		for(JobOutput jobOutput : jobOutputs)
		{
			HashMap<String, Double> hashmap = jobOutput.getEvaluationsMap();
			for(Entry<String, Double> entry : hashmap.entrySet())
			{
				String metric = entry.getKey();
				Double value = entry.getValue();
				if(value != 0.0)
				{
					data.addValue(value, jobOutput.getAlgorithmName() + "(" + jobOutput.getJobID() + ")", metric);
				}
					
			}
			data.addValue(jobOutput.parseDuration(), jobOutput.getAlgorithmName() + "(" + jobOutput.getJobID() + ")", "Time taken");
		}

	}

}