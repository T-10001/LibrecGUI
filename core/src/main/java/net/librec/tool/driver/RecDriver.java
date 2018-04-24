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
import net.librec.job.RecommenderJob;
import net.librec.job.RecommenderJobOld;
import net.librec.tool.LibrecTool;
import net.librec.util.NotificationUtil;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import ds.desktop.notify.DesktopNotify;
import ds.desktop.notify.NotificationBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import ds.desktop.notify.DesktopNotify;
import ds.desktop.notify.NotificationBuilder;
/**
 * RecDriver
 *
 * @author WangYuFeng
 */
public class RecDriver implements LibrecTool {

	final boolean NO_TIME_OUT = false;
	final boolean TIMEOUT = true; 
	
    /**
     * Execute the command with the given arguments.
     *
     * @param args command specific arguments.
     * @return exit code.
     * @throws Exception if error occurs
     */
    public int run(String[] args) throws Exception{
        // init options
        Options options = new Options();
        options.addOption("build", false, "build model");
        options.addOption("load", false, "load model");
        options.addOption("save", false, "save model");
        options.addOption("exec", false, "run job");
        options.addOption("conf", true, "the path of configuration file");
        options.addOption("jobconf", true, "a specified key-value pair for configuration");
        options.addOption("D", true, "a specified key-value pair for configuration");
        // parse options
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args, false);
        // init configuration
        Configuration conf = new Configuration();
        if (cmd.hasOption("conf")) {
            String confFilePath = cmd.getOptionValue("conf");         
            Properties prop = new Properties();
            prop.load(new FileInputStream(confFilePath));
            for (String name : prop.stringPropertyNames()) {
                conf.set(name, prop.getProperty(name));
            }
        }
        if (cmd.hasOption("jobconf")) {
            String[] optionValues = cmd.getOptionValues("jobconf");
            for (String optionValue : optionValues) {
                String[] keyValuePair = optionValue.split("=");
                conf.set(keyValuePair[0], keyValuePair[1]);
            }
        }
        if (cmd.hasOption("D")) {
            String[] optionValues = cmd.getOptionValues("D");
            for (String optionValue : optionValues) {
                String[] keyValuePair = optionValue.split("=");
                conf.set(keyValuePair[0], keyValuePair[1]);
            }
        }
        
        
        
//        System.out.println("start Size = " + formatMemory(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
//        System.out.println("Max Size = " + formatMemory(Runtime.getRuntime().maxMemory()));
//        
//        System.out.println("something...");
//        RecommenderJobOld job = new RecommenderJobOld(conf);
//        job.runJob();
////        System.out.println("Finished");
//        
////        
//        System.out.println("final Size = " + formatMemory(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
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
        File upOne = new File(System.getProperty("user.dir")).getParentFile();
        System.setProperty("user.dir", upOne.toString());
        System.out.println(System.getProperty("user.dir").toString());
        String path = System.getProperty("user.dir").toString();
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(path + File.separator + "conf" + File.separator +  "librec.properties");
//        	input = new FileInputStream("conf/librec.properties");
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (String name : prop.stringPropertyNames()) {
            conf.set(name, prop.getProperty(name));
//            System.out.println(name + "=" + prop.getProperty(name));
        }
        
        System.out.println("Not parallel:");
        RecommenderJobOld job = new RecommenderJobOld(conf);
        job.runJob();
        
        System.out.println("\nParallel:");
        for(int i = 0; i < 4; i++)
        {
	        runJobs(conf);
        }   
        
//        
//        
//    	String[] recommenderClasses = conf.getStrings("rec.recommender.class");
//    	System.out.println(conf.get("rec.recommender.class"));
//    	for(String recommenderClass : recommenderClasses)
//    	{
//    		System.out.println(recommenderClass);
////    		Properties prop2 = new Properties();
////            InputStream input2 = null;
////            try {
////                input2 = new FileInputStream(path + File.separator + "conf" + File.separator + recommenderClass + ".properties");
////                System.out.println((new File(path + File.separator + "conf" + File.separator + recommenderClass + ".properties")).toString());
////            	input2 = new FileInputStream(path + "/conf/"  + recommenderClass + ".properties");
//////                System.out.println((new File(path + "/conf/"  + recommenderClass + ".properties")).toString());
////                prop2.load(input2);
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////            for (String name : prop2.stringPropertyNames()) {
////                conf.set(name, prop2.getProperty(name));
////            }
//    		Resource resource = new Resource(path + File.separator + "conf" + File.separator + recommenderClass + ".properties");
//		    conf.addResource(resource);
//    		RecommenderJob job = new RecommenderJob(conf);
//    		job.runJob();
//    		System.out.println("Finished ");
//    	}
//    	// notification
//    	NotificationUtil notifier = new NotificationUtil();
//    	notifier.notify("All jobs done.", NO_TIME_OUT);
        return 0;
    }

    public static void main(String[] args) throws Exception {
        LibrecTool tool = new RecDriver();

        Options options = new Options();
        options.addOption("build", false, "build model");
        options.addOption("load", false, "load model");
        options.addOption("save", false, "save model");
        options.addOption("exec", false, "run job");
        options.addOption("conf", true, "the path of configuration file");
        options.addOption("jobconf", true, "a specified key-value pair for configuration");
        options.addOption("D", true, "a specified key-value pair for configuration");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("build")) {
            ;
        } else if (cmd.hasOption("load")) {
            ;
        } else if (cmd.hasOption("save")) {
            ;
        } else if (cmd.hasOption("exec")) {
//            tool.run(args);
        }
        
        tool.run(args);
    }
    
    public void runJobs(final Configuration conf)
    {
    	SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>()
		{
			@Override
			protected Boolean doInBackground() throws Exception {
				RecommenderJobOld job = new RecommenderJobOld(conf);
		        job.runJob();
				return true;
			}
		};
		worker.execute();
    }
    
    public static String formatMemory(long heapSize)
	{
//		long heapMaxSize = Runtime.getRuntime().totalMemory();
        String size = "" + heapSize;
        if (heapSize < 1024) size = heapSize + " B";
        else
        {
        	int z = (63 - Long.numberOfLeadingZeros(heapSize)) / 10;
        	size = String.format("%.1f %sB", (double)heapSize / (1L << (z*10)), " KMGTPE".charAt(z));
        }
        return size;
//        System.out.println("Total memory = " + size);
//        System.out.println("Total memory = " + size);
	}
}