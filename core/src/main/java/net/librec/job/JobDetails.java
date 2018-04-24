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

public class JobDetails extends AbstractJob {

	private String queueNo;
	
	public JobDetails(String dataSet, String algorithmName, String dateStarted)
	{
		this.dataSet = dataSet;
		this.algorithmName = algorithmName;
		this.dateStarted = dateStarted;
	}
	
	public JobDetails(String queueNo, String dataSet, String algorithmName, String dateStarted)
	{
		this.queueNo = queueNo;
		this.dataSet = dataSet;
		this.algorithmName = algorithmName;
		this.dateStarted = dateStarted;
	}
	
	public JobDetails(String queueNo, String dataSet, String datasetParams, String algorithmName, String dateStarted)
	{
		this.queueNo = queueNo;
		this.dataSet = dataSet;
		this.dataParams = datasetParams;
		this.algorithmName = algorithmName;
		this.dateStarted = dateStarted;
	}
	
	public JobDetails(String queueNo, String dataSet, String datasetParams, HashMap<String, String> dataProps, String algorithmName, String dateStarted)
	{
		this.queueNo = queueNo;
		this.dataSet = dataSet;
		this.dataParams = datasetParams;
		this.dataProps = dataProps;
		this.algorithmName = algorithmName;
		this.dateStarted = dateStarted;
	}
	
	public JobDetails(String queueNo, String dataSet, String datasetParams, String algorithmName, String algorithmParameters, String dateStarted)
	{
		this.queueNo = queueNo;
		this.dataSet = dataSet;
		this.dataParams = datasetParams;
		this.algorithmName = algorithmName;
		this.algoParams = algorithmParameters;
		this.dateStarted = dateStarted;
	}
	
//	public JobDetails(String jobID, String dataSet, String algorithmName, String dateStarted, String dateFinished) {
//		super();
//		this.jobID = jobID;
//		this.dataSet = dataSet;
//		this.algorithmName = algorithmName;
//		this.dateStarted = dateStarted;
//		this.dateFinished = dateFinished;
//	}
	
	public String makeString()
	{
		String s = jobID + StringUtil.FOUR_COLONS + dataSet + StringUtil.FOUR_COLONS + dataParams + StringUtil.FOUR_COLONS + algorithmName + StringUtil.FOUR_COLONS + algoParams + StringUtil.FOUR_COLONS + dateStarted + StringUtil.FOUR_COLONS + dateFinished;
		return s;
	}

	public String getQueueNo() {
		return queueNo;
	}

	public void setQueueNo(String queueNo) {
		this.queueNo = queueNo;
	}
	
	public String makeJobString()
	{
		String s = dataSet + StringUtil.FOUR_COLONS 
				+ dataParams + StringUtil.FOUR_COLONS 
				+ algorithmName + StringUtil.FOUR_COLONS 
				+ algoParams + StringUtil.FOUR_COLONS 
				+ dateStarted + StringUtil.FOUR_COLONS 
				+ dateFinished + StringUtil.FOUR_COLONS
				+ timeTaken;
		return s;
	}
	
	public HashMap<String, String> parseDataParams()
	{
		HashMap<String, String> map = new HashMap<>();
		String[] pairs = this.dataParams.split(StringUtil.FOUR_SPACES);
		for(String pair : pairs)
		{
			if(pair.contains("data.splitter."))
			{
				String splitterType = pair.substring("data.splitter.".length());
				System.out.println("splitter type" + splitterType);
				String[] splitAndValue = splitterType.split("=");
				System.out.println("splitAndValue" + splitAndValue[0] + " " + splitAndValue[1]);
//				map.put(key, value)
				String modelSplitter = "data.model.splitter";// + splitAndValue[0].split(".")[0];
				// put the splitter model e.g ratio
				map.put(modelSplitter, splitAndValue[0].split(".")[0]);
//				System.out.println("splitter" + splitter);
				map.put("data.splitter." + splitAndValue[0].split(".")[0], splitAndValue[0].split(".")[1]);
				if(modelSplitter.equals("ratio"))
				{
					map.put("data.splitter.trainset.ratio", splitAndValue[1]);
				}
				else if(modelSplitter.equals("givenn"))
				{
					map.put("data.splitter.givenn.n", splitAndValue[1]);
				}
				else if(modelSplitter.equals("cv"))
				{
					map.put("data.splitter.cv.number", splitAndValue[1]);
				}
			}
//			else
//			{
//				String[] separated = pair.split("=");
//				map.put(separated[0], separated[1]);
//			}
			if(pair.contains("data.column.format"))
			{
				map.put("data.column.format", pair.split("=")[1]);
			}
//			System.out.println();
		}
//		System.out.println();
		for(String key : map.keySet())
		{
			System.out.println(key + "=" + map.get(key));
		}
		return map;
	}
	
}