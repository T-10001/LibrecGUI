package net.librec.job;

import net.librec.common.LibrecException;
import net.librec.conf.Configuration;
import net.librec.data.DataModel;
import net.librec.data.DataSplitter;
import net.librec.data.splitter.KCVDataSplitter;
import net.librec.data.splitter.LOOCVDataSplitter;
import net.librec.eval.Measure.MeasureValue;
import net.librec.eval.RecommenderEvaluator;
import net.librec.filter.RecommendedFilter;
import net.librec.math.algorithm.Randoms;
import net.librec.recommender.Recommender;
import net.librec.recommender.RecommenderContext;
import net.librec.recommender.item.RecommendedItem;
import net.librec.similarity.RecommenderSimilarity;
import net.librec.util.DateUtil;
import net.librec.util.DriverClassUtil;
import net.librec.util.FileUtil;
import net.librec.util.JobUtil;
import net.librec.util.ReflectionUtil;
import net.librec.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.librec.eval.ranking.*;
import net.librec.eval.rating.MAEEvaluator;
import net.librec.eval.rating.MPEEvaluator;
import net.librec.eval.rating.MSEEvaluator;
import net.librec.eval.rating.RMSEEvaluator;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JobOutput extends AbstractJob {
	
	private LinkedHashMap<String, Double> evaluationsMap = getDefaultEvaluationsMap();
	
	public JobOutput(String jobID)
	{
		this.jobID = jobID;
		this.evaluationsMap = getDefaultEvaluationsMap();
	}
	
	public JobOutput(String jobID, String dataSet, String algorithmName)
	{
		this.jobID = jobID;
		this.dataSet = dataSet;
		this.algorithmName = algorithmName;
	}
	
	public JobOutput(String dataSet, String algorithmName)
	{
		this.dataSet = dataSet;
		this.algorithmName = algorithmName;
	}
	
	private LinkedHashMap<String, Double> getDefaultEvaluationsMap()
	{
		LinkedHashMap<String, Double> defaultEvaluationsMap = new LinkedHashMap<String, Double>();
		defaultEvaluationsMap.put("PRECISION top 5", 0.0);
		defaultEvaluationsMap.put("PRECISION top 10", 0.0);
		defaultEvaluationsMap.put("RECALL top 5", 0.0);
		defaultEvaluationsMap.put("RECALL top 10", 0.0);
		defaultEvaluationsMap.put("AUC top 10", 0.0);
		defaultEvaluationsMap.put("AP top 10", 0.0);
		defaultEvaluationsMap.put("NDCG top 10", 0.0);
		defaultEvaluationsMap.put("RR top 10", 0.0);
		defaultEvaluationsMap.put("Novelty", 0.0);
		defaultEvaluationsMap.put("Novelty top 10", 0.0);
		defaultEvaluationsMap.put("RMSE", 0.0);
		defaultEvaluationsMap.put("MSE", 0.0);
		defaultEvaluationsMap.put("MAE", 0.0);
		defaultEvaluationsMap.put("MPE", 0.0);
		return defaultEvaluationsMap;
	}

	public LinkedHashMap<String, Double> getEvaluationsMap() {
		return evaluationsMap;
	}

	public void setEvaluationsMap(LinkedHashMap<String, Double> evaluationsMap) {
		this.evaluationsMap = evaluationsMap;
	}
	
	public String makeString()
	{
		String s = jobID + StringUtil.FOUR_COLONS 
				+ dataSet + StringUtil.FOUR_COLONS 
				+ dataParams + StringUtil.FOUR_COLONS 
				+ algorithmName + StringUtil.FOUR_COLONS 
				+ algoParams + StringUtil.FOUR_COLONS 
				+ dateStarted + StringUtil.FOUR_COLONS 
				+ dateFinished + StringUtil.FOUR_COLONS
				+ timeTaken;
		for(Map.Entry<String, Double> entry : this.evaluationsMap.entrySet())
		{
			Double value = entry.getValue();
			s += StringUtil.FOUR_COLONS + value;
//			System.out.println(entry);
		}
		return s;
	}
	
	public JobOutput getJobOutput()
	{
		return this;
	}
}