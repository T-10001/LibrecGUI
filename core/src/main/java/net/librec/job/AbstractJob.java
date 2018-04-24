package net.librec.job;

import java.util.HashMap;
import java.util.Properties;

public abstract class AbstractJob {

	protected String jobID;
	protected String dataSet;
	protected String dataParams;
	protected String algorithmName;
	protected String algoParams;
	protected HashMap<String, String> dataProps;
	protected HashMap<String, String> algoProps;
	protected String dateStarted;
	protected String dateFinished;
	protected String timeTaken = "";
	
	public AbstractJob() {
		// TODO Auto-generated constructor stub
	}

	public String getJobID()
	{
		return jobID;
	}

	public void setJobID(String jobID)
	{
		this.jobID = jobID;
	}

	public String getDataSet()
	{
		return dataSet;
	}

	public void setDataSet(String dataSet)
	{
		this.dataSet = dataSet;
	}

	public String getDataParams()
	{
		return dataParams;
	}

	public void setDataParams(String dataParams)
	{
		this.dataParams = dataParams;
	}

	public String getAlgorithmName()
	{
		return algorithmName;
	}

	public void setAlgorithmName(String algorithmName)
	{
		this.algorithmName = algorithmName;
	}

	public String getAlgoParams()
	{
		return algoParams;
	}

	public void setAlgoParams(String algoParams)
	{
		this.algoParams = algoParams;
	}

	public HashMap<String, String> getDataProps()
	{
		return dataProps;
	}

	public void setDataProps(HashMap<String, String> dataProps)
	{
		this.dataProps = dataProps;
	}

	public HashMap<String, String> getAlgoProps()
	{
		return algoProps;
	}

	public void setAlgoProps(HashMap<String, String> algoProps)
	{
		this.algoProps = algoProps;
	}

	public String getDateStarted()
	{
		return dateStarted;
	}

	public void setDateStarted(String dateStarted)
	{
		this.dateStarted = dateStarted;
	}

	public String getDateFinished()
	{
		return dateFinished;
	}

	public void setDateFinished(String dateFinished)
	{
		this.dateFinished = dateFinished;
	}
	
	public void setTimeTaken(String timeTaken)
	{
		this.timeTaken = timeTaken;
	}
	
	public void setTimeTaken()
	{
		
	}

	public String getTimeTaken()
	{
		return timeTaken;
	}

	public double parseDuration()
	{
		String[] array = timeTaken.split(":");
		double hours = Double.parseDouble(array[0]);
		double minutes = Double.parseDouble(array[1]);
		double seconds = Double.parseDouble(array[2]);
		double durationInMinutes = hours*60 + minutes + seconds/60.0; 
		return durationInMinutes;
	}
}
