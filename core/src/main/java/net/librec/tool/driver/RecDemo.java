/**
 * Copyright (C) 2016 LibRec
 * <p>
 * This file is part of LibRec.
 * LibRec is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * LibRec is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with LibRec. If not, see <http://www.gnu.org/licenses/>.
 */
package net.librec.tool.driver;

import net.librec.conf.Configuration;
import net.librec.conf.Configuration.Resource;
import net.librec.data.convertor.TextDataConvertor;
import net.librec.data.model.TextDataModel;
import net.librec.job.RecommenderJob;
import net.librec.math.structure.DenseMatrix;
import net.librec.math.structure.DenseVector;
import net.librec.math.structure.SparseMatrix;
import net.librec.tool.LibrecTool;
import net.librec.util.NotificationUtil;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.io.IOUtils;

import ds.desktop.notify.DesktopNotify;
import ds.desktop.notify.NotificationBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import ds.desktop.notify.DesktopNotify;
import ds.desktop.notify.NotificationBuilder;
/**
 * RecDriver
 *
 * @author WangYuFeng
 */
public class RecDemo {

	final boolean NO_TIME_OUT = false;
	final boolean TIMEOUT = true; 
	
    /**
     * Execute the command with the given arguments.
     *
     * @param args command specific arguments.
     * @return exit code.
     * @throws Exception if error occurs
     */
    public int run() throws Exception{
        Configuration conf = new Configuration();

//        //run job
//        String[] recommenderClasses = conf.getStrings("rec.recommender.class");
//        for(String recommenderClass : recommenderClasses)
//        {
//        	Resource resource = new Resource("conf/" + recommenderClass + ".properties");
//		    conf.addResource(resource);
////        	conf.set("rec.recommender.class", recommenderClass);
//        	//conf.set("data.input.path", "filmtrust/rating");
//        	RecommenderJob job = new RecommenderJob(conf);
//        	job.runJob();
//        	System.out.println("Finished ");
//        	
////        	System.out.println(recommenderClass);
//        }
//        // notification
//        NotificationUtil notifier = new NotificationUtil();
//        notifier.notify("All jobs done.", NO_TIME_OUT);
//        RecommenderJob job = new RecommenderJob(conf);
//        job.notify("All jobs done.", NO_TIME_OUT);
        /*
        File upOne = new File(System.getProperty("user.dir")).getParentFile();
        System.setProperty("user.dir", upOne.toString());
        System.out.println(System.getProperty("user.dir").toString());
        String path = System.getProperty("user.dir").toString();
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(path + File.separator + "conf" + File.separator + "old" + File.separator +  "diversityaccuracy.properties");
//        	input = new FileInputStream("conf/librec.properties");
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (String name : prop.stringPropertyNames()) {
            conf.set(name, prop.getProperty(name));
//            System.out.println(name + "=" + prop.getProperty(name));
        }
        
        TextDataConvertor convertor = new TextDataConvertor(path + File.separator + "data" + File.separator + "filmtrust-rating");
        convertor.setDataset("filmtrust-rating");
        RecommenderJob job = new RecommenderJob(conf);
        job.setDataConvertor(convertor);
        job.runJob();
*/
//        for (String name : prop.stringPropertyNames()) {
//            conf.set(name, prop.getProperty(name));
//        }
//        conf.set("data.input.path", "filmtrust-rating");
        
//    	String[] recommenderClasses = conf.getStrings("rec.recommender.class");
//    	System.out.println(conf.get("rec.recommender.class"));
//    	for(String recommenderClass : recommenderClasses)
//    	{
//    		System.out.println(recommenderClass);
//    		Configuration conf2 = new Configuration();
//    		Properties prop2 = new Properties();
//            InputStream input2 = null;
//            try {
//                input2 = new FileInputStream(path + File.separator + "conf" + File.separator + recommenderClass + ".properties");
//                System.out.println((new File(path + File.separator + "conf" + File.separator + recommenderClass + ".properties")).toString());
////            	input2 = new FileInputStream(path + "/conf/"  + recommenderClass + ".properties");
////              System.out.println((new File(path + "/conf/"  + recommenderClass + ".properties")).toString());
//                prop2.load(input2);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            for (String name : prop2.stringPropertyNames()) {
//            	conf2.set(name, prop2.getProperty(name));
//            }
////    		Resource resource = new Resource(path + File.separator + "conf" + File.separator + recommenderClass + ".properties");
////		    conf.addResource(resource);
//    		RecommenderJob job = new RecommenderJob(conf);
//    		job.runJob();
//    		System.out.println("Finished ");
//    	}
    	// notification
//    	NotificationUtil.staticNotify("All jobs done.", NO_TIME_OUT);
        double[][] testMatrix = 
        	{ 
        			{0, 1, 2, 3},
        			{1, 3, 4, 5},
        			{2, 1, 3, 1},
        			{3, 2, 1, 4}
        			
        	};
        DenseMatrix matrix = new DenseMatrix(4, 4);
        matrix.transpose();
        for(int i = 0; i < 4; i++)
        {
        	for(int j = 0; j < 4; j++)
        		matrix.set(i, j, testMatrix[i][j]);
        }
        
        System.out.println(matrix.toString());
        
        
//        System.out.println(list.toString());
//        Collections.sort(map.keySet());
        DenseMatrix timeSortedMatrix = getSortedMatrix(matrix, 3);
//        int index = 0;
        int count = 0;
//        for(int index = 0; index < list.size(); index++)
//        {
//	        for(int row = 0; row < matrix.numRows; row++)
//	        {
//	        	count++;
//	        	if(list.get(index) == matrix.get(row, 3))
//	        	{
//	        		DenseVector v = matrix.row(row);
//	        		timeSortedMatrix.setRow(index, v);
//	        		
//	        		break;
//	        	}
//	        }
//        }
        
//        for(int index = 0; index < list.size(); index++)
//        {
//	        for(int row = 0; row < matrix.numRows; row++)
//	        {
//	        	count++;
//	        	if(list.get(index) == matrix.get(row, 3))
//	        	{
//	        		DenseVector v = matrix.row(row);
//	        		timeSortedMatrix.setRow(index, v);
//	        		
//	        		break;
//	        	}
//	        }
//        }
        
//        System.out.println(list.toString());
        System.out.println(timeSortedMatrix.toString());
        System.out.println(count);
        return 0;
    }

    public static void main(String[] args) throws Exception {
//    	RecDemo demo = new RecDemo();
//    	demo.run();
//    	Configuration conf = new Configuration();
//    	 File upOne = new File(System.getProperty("user.dir")).getParentFile();
//         System.setProperty("user.dir", upOne.toString());
//         System.out.println(System.getProperty("user.dir").toString());
//         String path = System.getProperty("user.dir").toString();
//         Properties prop = new Properties();
//         InputStream input = null;
//         try {
//             input = new FileInputStream(path + File.separator + "conf" + File.separator + "old" + File.separator +  "diversityaccuracy.properties");
////         	input = new FileInputStream("conf/librec.properties");
//             prop.load(input);
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//
//
//         for (String name : prop.stringPropertyNames()) {
//             conf.set(name.trim(), prop.getProperty(name).trim());
////             System.out.println(name + "=" + prop.getProperty(name));
//         }
         
//         Properties prop2 = new Properties();
//         InputStream input2 = null;
//         try {
//             input = new FileInputStream(path + File.separator + "conf" + File.separator + "old" + File.separator +  "diversityaccuracy.properties");
////         	input = new FileInputStream("conf/librec.properties");
//             prop2.load(input2);
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//        System.out.println(path);
         
//        conf.set("data.input.path", "filmtrust-rating");
//		conf.set("data.model.format", "text");
//		conf.set("rec.eval.enable", "true");
//		conf.set("rec.random.seed", "1");
//		conf.set("data.column.format", "UIR");
//		conf.set("data.model.splitter", "ratio");
//		conf.set("data.convert.binarize.threshold", "-1.0");
//		conf.set("data.splitter.ratio", "user");
//		conf.set("data.splitter.trainset.ratio", "0.8");
//		
//		conf.get
		
//		TextDataConvertor convertor = new TextDataConvertor(path + File.separator + "data" + File.separator + "filmtrust-rating");
//		convertor.setDataset("filmtrust-rating");
//		
//		
//		RecommenderJob job = new RecommenderJob(conf);
//		job.setDataConvertor(convertor);
//		job.runJob();
//    	
    	String pathname = "C:/Users/PinkySwear/workspace/librecclone_librec2/data/filmtrust-rating/";
//		FileInputStream fs = new FileInputStream(new File(pathname + "ratings_0.txt"));
//		String targetFileStr = IOUtils.toString(fs, "UTF-8");
//		fs.close();
    	TextDataConvertor convertor = new TextDataConvertor(pathname);
    	convertor.processData();
    	convertor.getPreferenceMatrix();
    	TextDataModel model = new TextDataModel();
    	model.setDataConvertor(convertor);
    	model.buildConvert();
    	SparseMatrix matrix = (SparseMatrix) model.getTrainDataSet();
    	;
    	System.out.println(matrix.toString());
//    	model.
    }
    
    public DenseMatrix getSortedMatrix(DenseMatrix mat, int column)
    {
    	DenseMatrix sortedMatrix = new DenseMatrix(mat.numRows, mat.numColumns);
    	DenseVector vector = mat.column(column);
    	//      double[] d = Collections.sort(vector.getData());
    	//      List<Double> list = new ArrayList<>();
    	TreeMap<Double, Integer> map = new TreeMap<>();
    	int row = 0;
    	for(double d : vector.getData())
    	{
    		//      	list.add(d);
    		map.put(d, row++);
    	}
    	row = 0;
    	//    int column = 0;
    	for(double d : map.navigableKeySet())
    	{
    		sortedMatrix.setRow(row++, mat.row(map.get(d)));//(row, vals);.set(matrix.row(map.get(d)), map.get(d), d);
    	}
    	return sortedMatrix;
    }
}

