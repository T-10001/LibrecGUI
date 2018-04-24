/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.librec.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Stream;
//import javafx.util;

import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import net.librec.common.LibrecException;
import net.librec.conf.Configuration;
import net.librec.conf.Configuration.Resource;
import net.librec.data.DataConvertor;
import net.librec.data.DataModel;
import net.librec.data.convertor.TextDataConvertor;
import net.librec.job.JobDetails;
import net.librec.job.JobOutput;
import net.librec.job.RecommenderJob;
import net.librec.recommender.Recommender;
import net.librec.recommender.cf.ranking.ItemBigramRecommender;
import net.librec.gui.UserFriendlyTable;
import net.librec.gui.AlignHeaderRenderer;
import net.librec.gui.BarChart_AWT;
import net.librec.gui.EditableHeaderRenderer;
import net.librec.gui.HeaderRenderer;
import net.librec.gui.TextAreaOutputStream;
import net.librec.util.ConfirmationUtil;
import net.librec.util.DateUtil;
import net.librec.util.DriverClassUtil;
import net.librec.util.FileUtil;
import net.librec.util.NotificationUtil;
import net.librec.util.StringUtil;


/**
 *
 * @author PinkySwear
 */
public class LibRecGUI extends javax.swing.JFrame /* implements LibrecTool */ {

	private final boolean NO_TIME_OUT = false;
	private final boolean TIMEOUT = true;
	private final String NONE = "None";
	private final String DATE_FORMAT = DateUtil.PATTERN_yyyy_MMM_dd_HH_MM_ss_z;
//	final String FOUR_SPACES = "    ";
	private int jobQueueNo = 0;
	private HashMap<String, DataConvertor> dataConvertorsMap = new HashMap<>();
	private HashMap<String, HashMap<String, List<Object>>> datasetParametersMap = new HashMap<>();
	private HashMap<String, HashMap<String, List<Object>>> algorithmParametersMap = new HashMap<>();
	private HashMap<String, DatasetParameterEditor> datasetParameterEditorsMap = new HashMap<>();
	private HashMap<String, AlgorithmParameterEditor> algorithmParameterEditorsMap = new HashMap<>();
	private HashMap<String, RecommenderJob> jobsQueue = new HashMap<>();
	
	/**
	 * Creates new form NewJFrameForm
	 * @throws  
	 */
	public LibRecGUI() throws IOException {

		System.out.println("Loading...");
		initComponents();
	
		// set the output of console to go into the console text area
		setConsoleOutput();
		
		// set directory to be the root folder of the project
		File upOne = new File(System.getProperty("user.dir")).getParentFile();
		System.setProperty("user.dir", upOne.toString());
		
		//loadDatasets();
		
		String logPath = getPath("log");//System.getProperty("user.dir") + File.separator + "log" + File.separator;
		tableResultsHistory.fillTableFromFile(logPath + "outputHistoryLog.log");
		tableJobHistory.fillTableFromFile(logPath + "jobHistoryLog.log");

		initialiseDataSets();
		initialiseAlgorithms();

		adjustTables();

		initDatasetEditorsMap();
		initAlgoEditorsMap();
		// set tooltips to always display
//		ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
		System.out.printf("Total memory usage currently is: %.3fGiB\n", (Runtime.getRuntime().totalMemory()- Runtime.getRuntime().freeMemory())/ (1024.0 * 1024.0 * 1024.0));
		
		//test();
//		printJavadoc();
		//getTime();
	}
	
	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents()
	{

		tabbedPaneAll = new javax.swing.JTabbedPane();
		buttonCancelSelected = new javax.swing.JButton();
		panelRecommendationTab = new javax.swing.JPanel();
		scrollPaneDatasets = new javax.swing.JScrollPane();
		tableSelectDataSets = new UserFriendlyTable();
		scrollPaneAlgorithms = new javax.swing.JScrollPane();
		tableSelectAlgorithms = new UserFriendlyTable();
		textFieldSearchAlgorithms = new javax.swing.JTextField();
		buttonQueueJobs = new javax.swing.JButton();
		buttonRunSelectedRecTab = new javax.swing.JButton();
		buttonIncludeDatasets = new javax.swing.JButton();
		buttonExcludeQueueDatasets = new javax.swing.JButton();
		textFieldSearchDatasets = new javax.swing.JTextField();
		textFieldSearchAlgorithms = new javax.swing.JTextField();
		buttonExcludeQueueAlgorithms = new javax.swing.JButton();
		buttonIncludeAlgorithms = new javax.swing.JButton();
		checkBoxSelectAllDatasets = new javax.swing.JCheckBox();
		checkBoxSelectAllAlgorithms = new javax.swing.JCheckBox();
        buttonEditDatasetParameters = new javax.swing.JButton();
        buttonEditAlgorithmParameters = new javax.swing.JButton();
		panelJobQueue = new javax.swing.JPanel();
		scrollPaneJobQueue = new javax.swing.JScrollPane();
		tableJobQueue = new UserFriendlyTable();
		buttonDeleteSelectedQueueTab = new javax.swing.JButton();
		buttonRunAll = new javax.swing.JButton();
		buttonRunSelectedQueueTab = new javax.swing.JButton();
		textFieldSearchJobQueue = new javax.swing.JTextField();
		buttonExcludeQueueTab = new javax.swing.JButton();
		buttonIncludeQueueTab = new javax.swing.JButton();
		checkBoxSelectAllJobQueue = new javax.swing.JCheckBox();
		buttonCancelSelected = new javax.swing.JButton();
		panelJobOutput = new javax.swing.JPanel();
		textFieldSearchJobOutput = new javax.swing.JTextField();
		scrollPaneJobOutput = new javax.swing.JScrollPane();
		tableJobOutput = new UserFriendlyTable();
		buttonDeleteSelectedOutputTab = new javax.swing.JButton();
		buttonExcludeOutputTab = new javax.swing.JButton();
		buttonIncludeOutputTab = new javax.swing.JButton();
		buttonGraphSelectedJobOutput = new javax.swing.JButton();
		checkBoxSelectAllJobOutput = new javax.swing.JCheckBox();
		panelHistory = new javax.swing.JPanel();
		tabbedPaneHistory = new javax.swing.JTabbedPane();
		panelResultsHistory = new javax.swing.JPanel();
		scrollPaneResultsHistory = new javax.swing.JScrollPane();
		tableResultsHistory = new UserFriendlyTable();
		textFieldSearchResultsHistory = new javax.swing.JTextField();
		buttonDeleteSelectedResultsHistoryTab = new javax.swing.JButton();
		buttonExcludeResultsHistoryTab = new javax.swing.JButton();
		buttonIncludeResultsHistoryTab = new javax.swing.JButton();
		buttonGraphSelectedResultsHistory = new javax.swing.JButton();
		checkBoxSelectAllResultsHistory = new javax.swing.JCheckBox();
		panelJobHistory = new javax.swing.JPanel();
		scrollPaneJobHistory = new javax.swing.JScrollPane();
		tableJobHistory = new UserFriendlyTable();
		textFieldSearchJobHistory = new javax.swing.JTextField();
		buttonDeleteSelectedJobHistoryTab = new javax.swing.JButton();
		buttonExcludeJobHistoryTab = new javax.swing.JButton();
		buttonIncludeJobHistoryTab = new javax.swing.JButton();
		checkBoxSelectAllJobHistory = new javax.swing.JCheckBox();
		panelConsole = new javax.swing.JPanel();
		scrollPaneConsole = new javax.swing.JScrollPane();
		textAreaConsole = new javax.swing.JTextArea();
		buttonClearConsole = new javax.swing.JButton();
		jToolBar1 = new javax.swing.JToolBar();

		// setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent)
			{
				if (ConfirmationUtil.getConfirmation("Are you sure you want to close?", "Really Closing?"))
				{
//					if(ConfirmationUtil.getConfirmation("Would you like to save datasets so you don't have to reload when running program next time?", "Save datasets?"))
//					{
//						try(FileOutputStream f = new FileOutputStream(new File(getPath("data") + "dataConvertorObjects.ser")) ; ObjectOutputStream o = new ObjectOutputStream(f))
//						{
//							
//							// Write objects to file
////							for(Map.Entry<String, DataConvertor> entry : dataConvertorsMap.entrySet())
////							{
////								o.writeObject(entry.getValue());
////							}
//							o.writeObject(dataConvertorsMap);
//						}
//						catch (IOException e)
//						{
//							e.printStackTrace();
//						}
//						
//					}
					String logPath = getPath("log");//System.getProperty("user.dir") + File.separator + "log" + File.separator;
					tableJobHistory.writeToFile(logPath + "jobHistoryLog.log");
					tableResultsHistory.writeToFile(logPath + "outputHistoryLog.log");
					System.exit(0);
				}

			}
		});

		//tabbedPaneAll.setToolTipText("");

		tableSelectDataSets.setAutoCreateRowSorter(true);
		tableSelectDataSets.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Data set", "Parameters", ""}) {
			Class[] types = new Class[] { java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class };
			boolean[] canEdit = new boolean[] { false, false, true };

			@Override
			public Class getColumnClass(int columnIndex)
			{
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex)
			{
				return canEdit[columnIndex];
			}
		});
		tableSelectDataSets.setColumnSelectionAllowed(true);
		tableSelectDataSets.setRowSelectionAllowed(true);
		scrollPaneDatasets.setViewportView(tableSelectDataSets);
		tableSelectDataSets.getColumnModel().getSelectionModel()
				.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		tableSelectAlgorithms.setAutoCreateRowSorter(true);
		tableSelectAlgorithms.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Algorithm", "Short name", "Parameters", "Description", "" }) {
			Class[] types = new Class[] { java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
					java.lang.Boolean.class };
			boolean[] canEdit = new boolean[] { false, false, false, false, true };

			@Override
			public Class getColumnClass(int columnIndex)
			{
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex)
			{
				return canEdit[columnIndex];
			}
		});
		tableSelectAlgorithms.setColumnSelectionAllowed(true);
		scrollPaneAlgorithms.setViewportView(tableSelectAlgorithms);
		tableSelectAlgorithms.getColumnModel().getSelectionModel()
				.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		buttonQueueJobs.setText("QUEUE JOB(S)");
		buttonQueueJobs.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonQueueJobsActionPerformed(evt);
			}
		});

		buttonRunSelectedRecTab.setText("RUN SELECTED");
		buttonRunSelectedRecTab.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonRunSelectedRecTabActionPerformed(evt);
			}
		});

		buttonIncludeDatasets.setText("INCLUDE");
		buttonIncludeDatasets.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		buttonIncludeDatasets.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonIncludeDatasetsActionPerformed(evt);
			}
		});

		buttonExcludeQueueDatasets.setText("EXCLUDE");
		buttonExcludeQueueDatasets.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		buttonExcludeQueueDatasets.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonExcludeQueueDatasetsActionPerformed(evt);
			}
		});

		textFieldSearchDatasets.setToolTipText("Enter text to include or exclude in the table");

		textFieldSearchAlgorithms.setToolTipText("Enter text to include or exclude in the table");

		buttonExcludeQueueAlgorithms.setText("EXCLUDE");
		buttonExcludeQueueAlgorithms.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonExcludeQueueAlgorithmsActionPerformed(evt);
			}
		});

		buttonIncludeAlgorithms.setText("INCLUDE");
		buttonIncludeAlgorithms.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonIncludeAlgorithmsActionPerformed(evt);
			}
		});

		checkBoxSelectAllDatasets.setText("Select all");
		checkBoxSelectAllDatasets.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				checkBoxSelectAllDatasetsActionPerformed(evt);
			}
		});

		checkBoxSelectAllAlgorithms.setText("Select all");
		checkBoxSelectAllAlgorithms.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				checkBoxSelectAllAlgorithmsActionPerformed(evt);
			}
		});
		
		buttonEditDatasetParameters.setText("EDIT PARAMETERS");
        buttonEditDatasetParameters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditDatasetParametersActionPerformed(evt);
            }
        });

        buttonEditAlgorithmParameters.setText("EDIT PARAMETERS");
        buttonEditAlgorithmParameters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditAlgorithmParametersActionPerformed(evt);
            }
        });

		javax.swing.GroupLayout panelRecommendationTabLayout = new javax.swing.GroupLayout(panelRecommendationTab);
        panelRecommendationTab.setLayout(panelRecommendationTabLayout);
        panelRecommendationTabLayout.setHorizontalGroup(
            panelRecommendationTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRecommendationTabLayout.createSequentialGroup()
                .addGroup(panelRecommendationTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelRecommendationTabLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollPaneDatasets, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE))
                    .addGroup(panelRecommendationTabLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(checkBoxSelectAllDatasets)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonIncludeDatasets)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonExcludeQueueDatasets)
                        .addGap(18, 18, 18)
                        .addComponent(textFieldSearchDatasets, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelRecommendationTabLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonEditDatasetParameters)))
                .addGroup(panelRecommendationTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRecommendationTabLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonEditAlgorithmParameters)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonQueueJobs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonRunSelectedRecTab)
                        .addGap(10, 10, 10))
                    .addGroup(panelRecommendationTabLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(scrollPaneAlgorithms, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE))
                    .addGroup(panelRecommendationTabLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(checkBoxSelectAllAlgorithms)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonIncludeAlgorithms)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonExcludeQueueAlgorithms)
                        .addGap(18, 18, 18)
                        .addComponent(textFieldSearchAlgorithms, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        panelRecommendationTabLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {buttonQueueJobs, buttonRunSelectedRecTab});

        panelRecommendationTabLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {buttonExcludeQueueAlgorithms, buttonExcludeQueueDatasets, buttonIncludeAlgorithms, buttonIncludeDatasets});

        panelRecommendationTabLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {textFieldSearchAlgorithms, textFieldSearchDatasets});

        panelRecommendationTabLayout.setVerticalGroup(
            panelRecommendationTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRecommendationTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRecommendationTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRecommendationTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelRecommendationTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonIncludeDatasets)
                            .addComponent(textFieldSearchAlgorithms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonExcludeQueueAlgorithms)
                            .addComponent(buttonIncludeAlgorithms)
                            .addComponent(textFieldSearchDatasets, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonExcludeQueueDatasets))
                        .addComponent(checkBoxSelectAllDatasets, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(checkBoxSelectAllAlgorithms, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRecommendationTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneDatasets, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                    .addComponent(scrollPaneAlgorithms, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panelRecommendationTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonQueueJobs)
                    .addComponent(buttonRunSelectedRecTab)
                    .addComponent(buttonEditDatasetParameters)
                    .addComponent(buttonEditAlgorithmParameters))
                .addContainerGap())
        );

        panelRecommendationTabLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {buttonQueueJobs, buttonRunSelectedRecTab});

        tabbedPaneAll.addTab("Recommendation setup", panelRecommendationTab);

		tableJobQueue.setAutoCreateRowSorter(true);
		tableJobQueue.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Queue No.", "Data set", "Data set parameters", "Algorithm", "Short name", "Algorithm parameters", "Date added", "Date finished", "Time taken", "" }) {
			Class[] types = new Class[] { 
					java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
					java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class };
			boolean[] canEdit = new boolean[] { 
					false, false, false, false, false, 
					false, false, false, false, true };

			@Override
			public Class getColumnClass(int columnIndex)
			{
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex)
			{
				return canEdit[columnIndex];
			}
		});
		tableJobQueue.setColumnSelectionAllowed(true);
		scrollPaneJobQueue.setViewportView(tableJobQueue);
		tableJobQueue.getColumnModel().getSelectionModel()
				.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		buttonDeleteSelectedQueueTab.setText("DELETE SELECTED");
		buttonDeleteSelectedQueueTab.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonDeleteSelectedQueueTabActionPerformed(evt);
			}
		});

		buttonRunAll.setText("RUN ALL");
		buttonRunAll.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonRunAllActionPerformed(evt);
			}
		});

		buttonRunSelectedQueueTab.setText("RUN SELECTED");
		buttonRunSelectedQueueTab.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonRunSelectedQueueTabActionPerformed(evt);
			}
		});

		textFieldSearchJobQueue.setToolTipText("Enter text to include or exclude in the table");

		buttonExcludeQueueTab.setText("EXCLUDE");
		buttonExcludeQueueTab.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonExcludeQueueTabActionPerformed(evt);
			}
		});

		buttonIncludeQueueTab.setText("INCLUDE");
		buttonIncludeQueueTab.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonIncludeQueueTabActionPerformed(evt);
			}
		});

		checkBoxSelectAllJobQueue.setText("Select all");
		checkBoxSelectAllJobQueue.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				checkBoxSelectAllJobQueueActionPerformed(evt);
			}
		});

		buttonCancelSelected.setText("CANCEL SELECTED");
        buttonCancelSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelSelectedActionPerformed(evt);
            }
        });
		
        javax.swing.GroupLayout panelJobQueueLayout = new javax.swing.GroupLayout(panelJobQueue);
        panelJobQueue.setLayout(panelJobQueueLayout);
        panelJobQueueLayout.setHorizontalGroup(
            panelJobQueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJobQueueLayout.createSequentialGroup()
                .addGroup(panelJobQueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelJobQueueLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelJobQueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollPaneJobQueue, javax.swing.GroupLayout.DEFAULT_SIZE, 1072, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelJobQueueLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(buttonRunSelectedQueueTab)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(buttonRunAll)
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                                .addComponent(buttonCancelSelected)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(buttonDeleteSelectedQueueTab))))
                    .addGroup(panelJobQueueLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(checkBoxSelectAllJobQueue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonIncludeQueueTab)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonExcludeQueueTab)
                        .addGap(18, 18, 18)
                        .addComponent(textFieldSearchJobQueue, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

//        panelJobQueueLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {buttonCancelSelected, buttonDeleteSelectedQueueTab, buttonRunAll, buttonRunSelectedQueueTab});

        panelJobQueueLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {buttonDeleteSelectedQueueTab, buttonRunAll, buttonRunSelectedQueueTab});

        
        panelJobQueueLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {buttonExcludeQueueTab, buttonIncludeQueueTab});

        panelJobQueueLayout.setVerticalGroup(
            panelJobQueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJobQueueLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelJobQueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelJobQueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textFieldSearchJobQueue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonExcludeQueueTab)
                        .addComponent(buttonIncludeQueueTab))
                    .addComponent(checkBoxSelectAllJobQueue, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPaneJobQueue, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(panelJobQueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonRunSelectedQueueTab)
                    .addComponent(buttonRunAll)
                    .addComponent(buttonDeleteSelectedQueueTab))
//                    .addComponent(buttonCancelSelected))
                .addContainerGap())
        );

		panelJobQueueLayout.linkSize(javax.swing.SwingConstants.VERTICAL,
				new java.awt.Component[] { buttonExcludeQueueTab, buttonIncludeQueueTab });

		tabbedPaneAll.addTab("Job queue", panelJobQueue);

		textFieldSearchJobOutput.setToolTipText("Enter text to include or exclude in the table");

		tableJobOutput.setAutoCreateRowSorter(true);
		tableJobOutput.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { 
				"Queue No.", "ID", "Data set", "Data set parameters", "Algorithm", 
				"Algorithm parameters", "Date started", "Date finished", "Time taken", "Precision top 5", 
				"Precision top 10","Recall top 5", "Recall top 10", "AUC top 10", "AP top 10", 
				"NDCG top 10", "RR top 10", "Novelty", "Novelty top 10", "RMSE", 
				"MSE", "MAE", "MPE", "" }) {
			Class[] types = new Class[] { 
					java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
					java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
					java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
					java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
					java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class };
			boolean[] canEdit = new boolean[] { 
					false, false, false, false, false, 
					false, false, false, false, false,
					false, false, false, false, false, 
					false, false, false, false, false, 
					false, false, false, true };

			@Override
			public Class getColumnClass(int columnIndex)
			{
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex)
			{
				return canEdit[columnIndex];
			}
		});
		tableJobOutput.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		tableJobOutput.setColumnSelectionAllowed(true);
		scrollPaneJobOutput.setViewportView(tableJobOutput);
		tableJobOutput.getColumnModel().getSelectionModel()
				.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		buttonDeleteSelectedOutputTab.setText("DELETE SELECTED");
		buttonDeleteSelectedOutputTab.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonDeleteSelectedOutputTabActionPerformed(evt);
			}
		});

		buttonExcludeOutputTab.setText("EXCLUDE");
		buttonExcludeOutputTab.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonExcludeOutputTabActionPerformed(evt);
			}
		});

		buttonIncludeOutputTab.setText("INCLUDE");
		buttonIncludeOutputTab.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonIncludeOutputTabActionPerformed(evt);
			}
		});

		buttonGraphSelectedJobOutput.setText("GRAPH SELECTED");
		buttonGraphSelectedJobOutput.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonGraphSelectedJobOutputActionPerformed(evt);
			}
		});

		checkBoxSelectAllJobOutput.setText("Select all");
		checkBoxSelectAllJobOutput.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				checkBoxSelectAllJobOutputActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout panelJobOutputLayout = new javax.swing.GroupLayout(panelJobOutput);
		panelJobOutput.setLayout(panelJobOutputLayout);
		panelJobOutputLayout.setHorizontalGroup(panelJobOutputLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(panelJobOutputLayout.createSequentialGroup()
						.addGroup(panelJobOutputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)

								.addGroup(panelJobOutputLayout.createSequentialGroup().addContainerGap()
										.addGroup(panelJobOutputLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(scrollPaneJobOutput, javax.swing.GroupLayout.DEFAULT_SIZE,
														1072, Short.MAX_VALUE)
												.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
														panelJobOutputLayout.createSequentialGroup()
																.addGap(0, 0, Short.MAX_VALUE)

																.addComponent(buttonGraphSelectedJobOutput)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(buttonDeleteSelectedOutputTab))))
								.addGroup(panelJobOutputLayout.createSequentialGroup().addGap(14, 14, 14)
										.addComponent(checkBoxSelectAllJobOutput)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(buttonIncludeOutputTab)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(buttonExcludeOutputTab).addGap(18, 18, 18)
										.addComponent(textFieldSearchJobOutput, javax.swing.GroupLayout.PREFERRED_SIZE,
												140, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));

		panelJobOutputLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { buttonExcludeOutputTab, buttonIncludeOutputTab });

		panelJobOutputLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { buttonDeleteSelectedOutputTab, buttonGraphSelectedJobOutput });

		panelJobOutputLayout.setVerticalGroup(panelJobOutputLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(panelJobOutputLayout.createSequentialGroup().addContainerGap().addGroup(panelJobOutputLayout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(panelJobOutputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(textFieldSearchJobOutput, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(buttonExcludeOutputTab).addComponent(buttonIncludeOutputTab))
						.addComponent(checkBoxSelectAllJobOutput, javax.swing.GroupLayout.Alignment.TRAILING))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(scrollPaneJobOutput, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
						.addGap(18, 18, 18)
						.addGroup(panelJobOutputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(buttonDeleteSelectedOutputTab).addComponent(buttonGraphSelectedJobOutput))
						.addContainerGap()));

		panelJobOutputLayout.linkSize(javax.swing.SwingConstants.VERTICAL,
				new java.awt.Component[] { buttonExcludeOutputTab, buttonIncludeOutputTab });

		panelJobOutputLayout.linkSize(javax.swing.SwingConstants.VERTICAL,
				new java.awt.Component[] { buttonDeleteSelectedOutputTab, buttonGraphSelectedJobOutput });

		tabbedPaneAll.addTab("Job output", panelJobOutput);

		tableResultsHistory.setAutoCreateRowSorter(true);
		tableResultsHistory.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { 
				"ID", "Data set", "Data set parameters", "Algorithm", "Algorithm parameters", 
				"Date started", "Date finished", "Time taken", "Precision top 5", "Precision top 10", "Recall top 5", 
				"Recall top 10", "AUC top 10", "AP top 10", "NDCG top 10", "RR top 10", 
				"Novelty", "Novelty top 10", "RMSE", "MSE", "MAE", 
				"MPE", "" }) {
			Class[] types = new Class[] { 
					java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
					java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
					java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
					java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
					java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class };
			boolean[] canEdit = new boolean[] { 
					false, false, false, false, false, 
					false, false, false, false, false, 
					false, false, false, false, false, 
					false, false, false, false, false,
					false, false, true };

			@Override
			public Class getColumnClass(int columnIndex)
			{
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex)
			{
				return canEdit[columnIndex];
			}
		});
		tableResultsHistory.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		tableResultsHistory.setColumnSelectionAllowed(true);
		tableResultsHistory.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		scrollPaneResultsHistory.setViewportView(tableResultsHistory);
		tableResultsHistory.getColumnModel().getSelectionModel()
				.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		buttonDeleteSelectedResultsHistoryTab.setText("DELETE SELECTED");
		buttonDeleteSelectedResultsHistoryTab.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonDeleteSelectedResultsHistoryTabActionPerformed(evt);
			}
		});

		buttonExcludeResultsHistoryTab.setText("EXCLUDE");
		buttonExcludeResultsHistoryTab.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonExcludeResultsHistoryTabActionPerformed(evt);
			}
		});

		buttonIncludeResultsHistoryTab.setText("INCLUDE");
		buttonIncludeResultsHistoryTab.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonIncludeResultsHistoryTabActionPerformed(evt);
			}
		});

		buttonGraphSelectedResultsHistory.setText("GRAPH SELECTED");
		buttonGraphSelectedResultsHistory.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonGraphSelectedResultsHistoryActionPerformed(evt);
			}
		});

		checkBoxSelectAllResultsHistory.setText("Select all");
		checkBoxSelectAllResultsHistory.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				checkBoxSelectAllResultsHistoryActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout panelResultsHistoryLayout = new javax.swing.GroupLayout(panelResultsHistory);
		panelResultsHistory.setLayout(panelResultsHistoryLayout);
		panelResultsHistoryLayout.setHorizontalGroup(panelResultsHistoryLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(panelResultsHistoryLayout.createSequentialGroup().addGroup(
						panelResultsHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)

								.addGroup(panelResultsHistoryLayout.createSequentialGroup().addContainerGap()
										.addGroup(panelResultsHistoryLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(scrollPaneResultsHistory,
														javax.swing.GroupLayout.Alignment.TRAILING,
														javax.swing.GroupLayout.DEFAULT_SIZE, 1067, Short.MAX_VALUE)
												.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
														panelResultsHistoryLayout.createSequentialGroup()
																.addGap(0, 0, Short.MAX_VALUE)

																.addComponent(buttonGraphSelectedResultsHistory)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(buttonDeleteSelectedResultsHistoryTab))))
								.addGroup(panelResultsHistoryLayout.createSequentialGroup().addGap(14, 14, 14)
										.addComponent(checkBoxSelectAllResultsHistory)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(buttonIncludeResultsHistoryTab)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(buttonExcludeResultsHistoryTab).addGap(18, 18, 18).addComponent(
												textFieldSearchResultsHistory, javax.swing.GroupLayout.PREFERRED_SIZE,
												130, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));

		panelResultsHistoryLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { buttonExcludeResultsHistoryTab, buttonIncludeResultsHistoryTab });

		panelResultsHistoryLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { buttonDeleteSelectedResultsHistoryTab, buttonGraphSelectedResultsHistory });

		panelResultsHistoryLayout.setVerticalGroup(
				panelResultsHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING, panelResultsHistoryLayout.createSequentialGroup()
								.addContainerGap().addGroup(panelResultsHistoryLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
												panelResultsHistoryLayout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(textFieldSearchResultsHistory,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(buttonExcludeResultsHistoryTab)
														.addComponent(buttonIncludeResultsHistoryTab))
										.addComponent(checkBoxSelectAllResultsHistory,
												javax.swing.GroupLayout.Alignment.TRAILING))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(scrollPaneResultsHistory, javax.swing.GroupLayout.DEFAULT_SIZE, 389,
										Short.MAX_VALUE)
								.addGap(18, 18, 18)
								.addGroup(panelResultsHistoryLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(buttonDeleteSelectedResultsHistoryTab)
										.addComponent(buttonGraphSelectedResultsHistory))
								.addContainerGap()));

		panelResultsHistoryLayout.linkSize(javax.swing.SwingConstants.VERTICAL,
				new java.awt.Component[] { buttonExcludeResultsHistoryTab, buttonIncludeResultsHistoryTab });

		panelResultsHistoryLayout.linkSize(javax.swing.SwingConstants.VERTICAL,
				new java.awt.Component[] { buttonDeleteSelectedResultsHistoryTab, buttonGraphSelectedResultsHistory });

		tabbedPaneHistory.addTab("Results history", panelResultsHistory);

		tableJobHistory.setAutoCreateRowSorter(true);
		tableJobHistory.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Data set", "Data set parameters", "Algorithm", "Algorithm parameters", "Date added", "Date finished", "Time taken", "" }) {
			Class[] types = new Class[] { java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
					java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class };
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, true };

			@Override
			public Class getColumnClass(int columnIndex)
			{
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex)
			{
				return canEdit[columnIndex];
			}
		});
		tableJobHistory.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
		tableJobHistory.setColumnSelectionAllowed(true);
		scrollPaneJobHistory.setViewportView(tableJobHistory);
		tableJobHistory.getColumnModel().getSelectionModel()
				.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		buttonDeleteSelectedJobHistoryTab.setText("DELETE SELECTED");
		buttonDeleteSelectedJobHistoryTab.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonDeleteSelectedJobHistoryTabActionPerformed(evt);
			}
		});

		buttonExcludeJobHistoryTab.setText("EXCLUDE");
		buttonExcludeJobHistoryTab.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonExcludeJobHistoryTabActionPerformed(evt);
			}
		});

		buttonIncludeJobHistoryTab.setText("INCLUDE");
		buttonIncludeJobHistoryTab.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonIncludeJobHistoryTabActionPerformed(evt);
			}
		});

		checkBoxSelectAllJobHistory.setText("Select all");
		checkBoxSelectAllJobHistory.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				checkBoxSelectAllJobHistoryActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout panelJobHistoryLayout = new javax.swing.GroupLayout(panelJobHistory);
		panelJobHistory.setLayout(panelJobHistoryLayout);
		panelJobHistoryLayout.setHorizontalGroup(panelJobHistoryLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(panelJobHistoryLayout.createSequentialGroup().addGroup(panelJobHistoryLayout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								panelJobHistoryLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
										.addComponent(buttonDeleteSelectedJobHistoryTab))
						.addGroup(panelJobHistoryLayout.createSequentialGroup().addContainerGap().addComponent(
								scrollPaneJobHistory, javax.swing.GroupLayout.DEFAULT_SIZE, 1067, Short.MAX_VALUE))
						.addGroup(panelJobHistoryLayout.createSequentialGroup().addGap(14, 14, 14)
								.addComponent(checkBoxSelectAllJobHistory)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(buttonIncludeJobHistoryTab)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(buttonExcludeJobHistoryTab).addGap(18, 18, 18)
								.addComponent(textFieldSearchJobHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 130,
										javax.swing.GroupLayout.PREFERRED_SIZE)))

						.addContainerGap()));

		panelJobHistoryLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { buttonExcludeJobHistoryTab, buttonIncludeJobHistoryTab });

		panelJobHistoryLayout.setVerticalGroup(panelJobHistoryLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(panelJobHistoryLayout.createSequentialGroup().addContainerGap().addGroup(panelJobHistoryLayout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(panelJobHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(textFieldSearchJobHistory, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(buttonExcludeJobHistoryTab).addComponent(buttonIncludeJobHistoryTab))
						.addComponent(checkBoxSelectAllJobHistory, javax.swing.GroupLayout.Alignment.TRAILING))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(scrollPaneJobHistory, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
						.addGap(18, 18, 18).addComponent(buttonDeleteSelectedJobHistoryTab).addContainerGap()));

		panelJobHistoryLayout.linkSize(javax.swing.SwingConstants.VERTICAL,
				new java.awt.Component[] { buttonExcludeJobHistoryTab, buttonIncludeJobHistoryTab });

		tabbedPaneHistory.addTab("Job history", panelJobHistory);

		javax.swing.GroupLayout panelHistoryLayout = new javax.swing.GroupLayout(panelHistory);
		panelHistory.setLayout(panelHistoryLayout);
		panelHistoryLayout.setHorizontalGroup(panelHistoryLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(tabbedPaneHistory));
		panelHistoryLayout.setVerticalGroup(
				panelHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						panelHistoryLayout.createSequentialGroup().addComponent(tabbedPaneHistory).addGap(0, 0, 0)));

		tabbedPaneAll.addTab("History", panelHistory);

		panelConsole.setToolTipText("View output and progress of recommendations.");

		textAreaConsole.setEditable(false);
		textAreaConsole.setColumns(20);
		textAreaConsole.setRows(5);
		textAreaConsole.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
		scrollPaneConsole.setViewportView(textAreaConsole);

		buttonClearConsole.setText("CLEAR CONSOLE");
		buttonClearConsole.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonClearConsoleActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout panelConsoleLayout = new javax.swing.GroupLayout(panelConsole);
		panelConsole.setLayout(panelConsoleLayout);
		panelConsoleLayout.setHorizontalGroup(panelConsoleLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(panelConsoleLayout.createSequentialGroup().addContainerGap()
						.addGroup(panelConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(scrollPaneConsole, javax.swing.GroupLayout.DEFAULT_SIZE, 1072,
										Short.MAX_VALUE)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										panelConsoleLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
												.addComponent(buttonClearConsole)))
						.addContainerGap()));
		panelConsoleLayout.setVerticalGroup(panelConsoleLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(panelConsoleLayout.createSequentialGroup().addContainerGap()
						.addComponent(scrollPaneConsole, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
						.addGap(18, 18, 18).addComponent(buttonClearConsole).addContainerGap()));

		tabbedPaneAll.addTab("Console", panelConsole);

		jToolBar1.setRollover(false);
		jToolBar1.setFloatable(false);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup().addContainerGap().addComponent(tabbedPaneAll).addContainerGap())
				.addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(tabbedPaneAll)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jToolBar1,
								javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)));

		pack();
		setExtendedState(MAXIMIZED_BOTH);
	}// </editor-fold>//GEN-END:initComponents

	private void buttonRunSelectedQueueTabActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_buttonRunSelectedQueueTabActionPerformed
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			List<JobDetails> jobsToRun = new ArrayList<JobDetails>();

			@Override
			protected Boolean doInBackground() throws Exception
			{
				DefaultTableModel model = (DefaultTableModel) tableJobQueue.getModel();
				for (int i = 0; i < model.getRowCount(); i++)
				{
					if (Boolean.valueOf(
							model.getValueAt(i, model.findColumn("")).toString())
							&& model.getValueAt(i, model.findColumn("Date finished")).toString().equals("N/A"))
					{
						model.setValueAt("Pending...", i, model.findColumn("Date finished"));
						JobDetails job = new JobDetails(model.getValueAt(i, model.findColumn("Queue No.")).toString(),
								model.getValueAt(i, model.findColumn("Data set")).toString(),
								model.getValueAt(i, model.findColumn("Data set parameters")).toString(),
								model.getValueAt(i, model.findColumn("Short name")).toString(),
								model.getValueAt(i, model.findColumn("Algorithm parameters")).toString(),
								model.getValueAt(i, model.findColumn("Date added")).toString());
						jobsToRun.add(job);
					}
				}
				if (jobsToRun.size() > 0)
				{
					runJobs(jobsToRun);
					new NotificationUtil("All selected jobs finished.", NO_TIME_OUT);
				}

				return true;
			}

		};
		if (ConfirmationUtil.getConfirmation("Really run selected jobs?", "Run selected?"))
		{
			worker.execute();
		}
	}// GEN-LAST:event_buttonRunSelectedQueueTabActionPerformed

	private void buttonQueueJobsActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_buttonQueueJobsActionPerformed
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {

			@Override
			protected Boolean doInBackground() throws Exception
			{
				queueJobs(false);
				return true;
			}
		};

		if (ConfirmationUtil.getConfirmation("Really queue job(s)?", "Queue job(s)?"))
		{
			worker.execute();
		}

	}// GEN-LAST:event_buttonQueueJobsActionPerformed

	private void buttonDeleteSelectedResultsHistoryTabActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_buttonDeleteSelectedResultsHistoryTabActionPerformed
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				tableResultsHistory.deleteSelectedRows();
				return true;
			}

		};
		if (ConfirmationUtil.getConfirmation("Really delete selected results from history?", "Delete selected?"))
		{
			worker.execute();
		}
	}// GEN-LAST:event_buttonDeleteSelectedResultsHistoryTabActionPerformed

	private void buttonExcludeQueueTabActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_buttonExcludeQueueTabActionPerformed
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				tableJobQueue.filterData(textFieldSearchJobQueue.getText(), UserFriendlyTable.EXCLUDE);
				return true;
			}
		};
		worker.execute();
	}// GEN-LAST:event_buttonExcludeQueueTabActionPerformed

	private void buttonIncludeQueueTabActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_buttonIncludeQueueTabActionPerformed
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				tableJobQueue.filterData(textFieldSearchJobQueue.getText(), UserFriendlyTable.INCLUDE);
				return true;
			}
		};
		worker.execute();
	}// GEN-LAST:event_buttonIncludeQueueTabActionPerformed

	private void buttonRunAllActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_buttonRunAllActionPerformed

		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			List<JobDetails> jobsToRun = new ArrayList<JobDetails>();

			@Override
			protected Boolean doInBackground() throws Exception
			{
				DefaultTableModel model = (DefaultTableModel) tableJobQueue.getModel();
				for (int i = 0; i < model.getRowCount(); i++)
				{
					if (model.getValueAt(i, model.findColumn("Date finished")).toString().equals("N/A"))
					{
						model.setValueAt("Pending...", i, model.findColumn("Date finished"));
						JobDetails job = new JobDetails(model.getValueAt(i, model.findColumn("Queue No.")).toString(),
								model.getValueAt(i, model.findColumn("Data set")).toString(), 
								model.getValueAt(i, model.findColumn("Data set parameters")).toString(), 
								model.getValueAt(i, model.findColumn("Short name")).toString(),
								model.getValueAt(i, model.findColumn("Algorithm parameters")).toString(),
								model.getValueAt(i, model.findColumn("Date added")).toString());
						jobsToRun.add(job);

					}

				}

				if (jobsToRun.size() > 0)
				{
					runJobs(jobsToRun);
					new NotificationUtil("All jobs in queue finished.", NO_TIME_OUT);
				}
				return true;
			}
		};
		if (ConfirmationUtil.getConfirmation("Really run all jobs in queue?", "Run all?"))
		{
			worker.execute();
		}
	}// GEN-LAST:event_buttonRunAllActionPerformed

	private void buttonIncludeOutputTabActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_buttonIncludeOutputTabActionPerformed
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				tableJobOutput.filterData(textFieldSearchJobOutput.getText(), UserFriendlyTable.INCLUDE);
				return true;
			}
		};
		worker.execute();
	}// GEN-LAST:event_buttonIncludeOutputTabActionPerformed

	private void buttonExcludeOutputTabActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_buttonExcludeOutputTabActionPerformed
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				tableJobOutput.filterData(textFieldSearchJobOutput.getText(), UserFriendlyTable.EXCLUDE);
				return true;
			}
		};
		worker.execute();
	}// GEN-LAST:event_buttonExcludeOutputTabActionPerformed

	private void buttonDeleteSelectedOutputTabActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_buttonDeleteSelectedOutputTabActionPerformed
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				tableJobOutput.deleteSelectedRows();
				return true;
			}
		};
		if (ConfirmationUtil.getConfirmation("Really delete selected outputs from this table?", ""))
		{
			worker.execute();
		}
	}// GEN-LAST:event_buttonDeleteSelectedOutputTabActionPerformed

	// https://stackoverflow.com/a/37115577/6947011
	private void buttonIncludeResultsHistoryTabActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_buttonIncludeResultsHistoryTabActionPerformed
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>()

		{
			@Override
			protected Boolean doInBackground() throws Exception
			{
				tableResultsHistory.filterData(textFieldSearchResultsHistory.getText(), UserFriendlyTable.INCLUDE);

				return true;
			}

		};
		worker.execute();
	}// GEN-LAST:event_buttonIncludeResultsHistoryTabActionPerformed

	private void buttonExcludeResultsHistoryTabActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_buttonExcludeResultsHistoryTabActionPerformed
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				tableResultsHistory.filterData(textFieldSearchResultsHistory.getText(), UserFriendlyTable.EXCLUDE);
				return true;
			}
		};
		worker.execute();
	}// GEN-LAST:event_buttonExcludeResultsHistoryTabActionPerformed

	private void buttonIncludeJobHistoryTabActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_buttonIncludeJobHistoryTabActionPerformed
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				tableJobHistory.filterData(textFieldSearchJobHistory.getText(), UserFriendlyTable.INCLUDE);
				return true;
			}
		};
		worker.execute();
	}// GEN-LAST:event_buttonIncludeJobHistoryTabActionPerformed

	private void buttonExcludeJobHistoryTabActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_buttonExcludeJobHistoryTabActionPerformed
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				tableJobHistory.filterData(textFieldSearchJobHistory.getText(), UserFriendlyTable.EXCLUDE);
				return true;
			}
		};
		worker.execute();
	}// GEN-LAST:event_buttonExcludeJobHistoryTabActionPerformed

	private void buttonDeleteSelectedJobHistoryTabActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_buttonDeleteSelectedJobHistoryTabActionPerformed
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				tableJobHistory.deleteSelectedRows();
				return true;
			}
		};
		if (ConfirmationUtil.getConfirmation("Really delete selected jobs from history?", "Delete selected?"))
		{
			worker.execute();
		}
	}// GEN-LAST:event_buttonDeleteSelectedJobHistoryTabActionPerformed

	private void buttonRunSelectedRecTabActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_buttonRunSelectedRecTabActionPerformed
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			
			@Override
			protected Boolean doInBackground() throws Exception
			{
				queueJobs(true);
				new NotificationUtil("All selected jobs finished.", NO_TIME_OUT);
				return true;
			}
		};
		if (ConfirmationUtil.getConfirmation("Really run selected jobs?", "Run selected?"))
		{
			worker.execute();
		}

	}// GEN-LAST:event_buttonRunSelectedRecTabActionPerformed

	private void queueJobs(boolean run)
	{
		List<String> datasetList = new ArrayList<>();
		List<String> algorithmList = new ArrayList<>();
		List<JobDetails> jobsToRun = new ArrayList<JobDetails>();
		HashMap<String, Integer> algoNumMap = new HashMap<>();
		DefaultTableModel tableModelDatasets = (DefaultTableModel) tableSelectDataSets.getModel();
		DefaultTableModel tableModelAlgorithms = (DefaultTableModel) tableSelectAlgorithms.getModel();
		for (int i = 0; i < tableModelDatasets.getRowCount(); i++)
		{
//			System.out.println("pressed.");
			if (Boolean.valueOf(
					tableModelDatasets.getValueAt(i, tableModelDatasets.getColumnCount() - 1).toString()))
			{
				String colDataset = tableModelDatasets.getValueAt(i, tableModelDatasets.findColumn("Data set")).toString();
				datasetList.add(colDataset);
			}
		}
		for (int j = 0; j < tableModelAlgorithms.getRowCount(); j++)
		{
//			System.out.println("pressed2.");
			if (Boolean.valueOf(
					tableModelAlgorithms.getValueAt(j, tableModelAlgorithms.getColumnCount() - 1).toString()))
			{
				String colAlgorithm = tableModelAlgorithms.getValueAt(j, tableModelAlgorithms.findColumn("Short name")).toString();
				algorithmList.add(colAlgorithm);
				algoNumMap.put(colAlgorithm, j);
//				Pair<String, Integer> pair = new Pair<>();
			}
		}
		for (String dataset : datasetList)
		{
			// need to copy the map not make reference
			HashMap<String, List<Object>> paramsMap = new HashMap<>(datasetParametersMap.get(dataset));
//			Set<String> paramsSet = paramsMap.keySet();
			List<Object> columnFormats = paramsMap.get("data.column.format");
			String binariseKey = "data.convert.binarize.threshold";
			List<Object> binariseValues = paramsMap.get(binariseKey);
			
			paramsMap.remove("data.column.format");
			paramsMap.remove(binariseKey);
			for(Object columnFormat : columnFormats)
			{
				
//				System.out.println(columnFormat + "1");
				for(Object binariseValue : binariseValues)
				{					
					for(String key : paramsMap.keySet())
					{
						List<Object> values = paramsMap.get(key);
						for(Object value : values)
						{ 

							for (String algorithm : algorithmList)
							{
								if(columnFormat.equals("UIR") && key.contains("date"))
								{
									// UIR does not have date information so skip this one
									continue;
								}
								HashMap<String, List<Object>> hashmap = algorithmParametersMap.get(algorithm);
								List<Object> algoParamsList = new ArrayList<>();
								mapPermuteList(hashmap, "", algoParamsList);
								for(Object algorithmParameters : algoParamsList)
								{
									String dateFinished = "N/A";
									String datasetParameters = "<html>data.column.format=" + columnFormat +  "<br>" + key + "=" + value + "<br>" + binariseKey + "=" + binariseValue;
									String dateAdded = DateUtil.getDateFormat(DATE_FORMAT).format(new Date());
									String algoParamsStr = "<html>" + algorithmParameters;
									if(run)
									{
	//										System.out.println("Here.");
										JobDetails job = new JobDetails("" + jobQueueNo, dataset, datasetParameters, algorithm, algoParamsStr , dateAdded);
										jobsToRun.add(job);
										dateFinished = "Pending...";
									}
									tableJobQueue.addRow(jobQueueNo + StringUtil.FOUR_COLONS 
											+ dataset + StringUtil.FOUR_COLONS 
											+ datasetParameters + StringUtil.FOUR_COLONS 
											+ getAlgorithmName(algorithm) + StringUtil.FOUR_COLONS 
											+ algorithm + StringUtil.FOUR_COLONS 
											+ algoParamsStr + StringUtil.FOUR_COLONS
											+ dateAdded + StringUtil.FOUR_COLONS 
											+ dateFinished + StringUtil.FOUR_COLONS 
											+ "");
									jobQueueNo++;
								}
								
							}
						}
					}
				}
			}
		}

		if (jobsToRun.size() > 0)
		{
			runJobs(jobsToRun);
		}

		
	}

	private void buttonDeleteSelectedQueueTabActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_buttonDeleteSelectedQueueTabActionPerformed
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			protected Boolean doInBackground() throws Exception
			{
				tableJobQueue.deleteSelectedRows();
				return true;
			}
		};
		if (ConfirmationUtil.getConfirmation("Really delete selected jobs from queue?", "Delete selected?"))
		{
			worker.execute();
		}

	}// GEN-LAST:event_buttonDeleteSelectedQueueTabActionPerformed

	private void buttonIncludeDatasetsActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_buttonIncludeDatasetsActionPerformed
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				tableSelectDataSets.filterData(textFieldSearchDatasets.getText(), UserFriendlyTable.INCLUDE);
				return true;
			}
		};
		worker.execute();
	}// GEN-LAST:event_buttonIncludeDatasetsActionPerformed

	private void buttonExcludeQueueDatasetsActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_buttonExcludeQueueDatasetsActionPerformed
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				tableSelectDataSets.filterData(textFieldSearchDatasets.getText(), UserFriendlyTable.EXCLUDE);
				return true;
			}
		};
		worker.execute();
	}// GEN-LAST:event_buttonExcludeQueueDatasetsActionPerformed

	private void buttonExcludeQueueAlgorithmsActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_buttonExcludeQueueAlgorithmsActionPerformed
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				tableSelectAlgorithms.filterData(textFieldSearchAlgorithms.getText(), UserFriendlyTable.EXCLUDE);
				return true;
			}
		};
		worker.execute();
	}// GEN-LAST:event_buttonExcludeQueueAlgorithmsActionPerformed

	private void buttonIncludeAlgorithmsActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_buttonIncludeAlgorithmsActionPerformed
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				tableSelectAlgorithms.filterData(textFieldSearchAlgorithms.getText(), UserFriendlyTable.INCLUDE);
				return true;
			}
		};
		worker.execute();

	}// GEN-LAST:event_buttonIncludeAlgorithmsActionPerformed

	private void buttonClearConsoleActionPerformed(java.awt.event.ActionEvent evt)
	{
		if (ConfirmationUtil.getConfirmation("Really clear the console?", "Clear console"))
		{
			setConsoleOutput();
			textAreaConsole.setText("");
		}
	}

	private void buttonGraphSelectedResultsHistoryActionPerformed(java.awt.event.ActionEvent evt)
	{
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				makeGraphs(tableResultsHistory.getSelectedJobOutputs());
				return true;
			}
		};
		if (ConfirmationUtil.getConfirmation("Really make graphs?", "Make graphs?"))
			worker.execute();
	}

	private void buttonGraphSelectedJobOutputActionPerformed(java.awt.event.ActionEvent evt)
	{
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				makeGraphs(tableJobOutput.getSelectedJobOutputs());
				return true;
			}
		};
		if (ConfirmationUtil.getConfirmation("Really make graphs?", "Make graphs?"))
			worker.execute();
	}

	private void checkBoxSelectAllDatasetsActionPerformed(java.awt.event.ActionEvent evt)
	{
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				if (checkBoxSelectAllDatasets.isSelected())
				{
					checkBoxSelectAllDatasets.setText("Select none");
					tableSelectDataSets.selectAll();
				}
				else if (!checkBoxSelectAllDatasets.isSelected())
				{
					checkBoxSelectAllDatasets.setText("Select all");
					tableSelectDataSets.selectNone();
				}
				return true;
			}
		};
		worker.execute();
	}

	private void checkBoxSelectAllAlgorithmsActionPerformed(java.awt.event.ActionEvent evt)
	{
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				if (checkBoxSelectAllAlgorithms.isSelected())
				{
					checkBoxSelectAllAlgorithms.setText("Select none");
					tableSelectAlgorithms.selectAll();
				}
				else if (!checkBoxSelectAllAlgorithms.isSelected())
				{
					checkBoxSelectAllAlgorithms.setText("Select all");
					tableSelectAlgorithms.selectNone();
				}
				return true;
			}
		};
		worker.execute();
	}

	private void checkBoxSelectAllJobQueueActionPerformed(java.awt.event.ActionEvent evt)
	{
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				if (checkBoxSelectAllJobQueue.isSelected())
				{
					checkBoxSelectAllJobQueue.setText("Select none");
					tableJobQueue.selectAll();
				}
				else if (!checkBoxSelectAllJobQueue.isSelected())
				{
					checkBoxSelectAllJobQueue.setText("Select all");
					tableJobQueue.selectNone();
				}
				return true;
			}
		};
		worker.execute();
	}

	private void checkBoxSelectAllJobOutputActionPerformed(java.awt.event.ActionEvent evt)
	{
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				if (checkBoxSelectAllJobOutput.isSelected())
				{
					checkBoxSelectAllJobOutput.setText("Select none");
					tableJobOutput.selectAll();
				}
				else if (!checkBoxSelectAllJobOutput.isSelected())
				{
					checkBoxSelectAllJobOutput.setText("Select all");
					tableJobOutput.selectNone();
				}
				return true;
			}
		};
		worker.execute();
	}

	private void checkBoxSelectAllJobHistoryActionPerformed(java.awt.event.ActionEvent evt)
	{
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				if (checkBoxSelectAllJobHistory.isSelected())
				{
					checkBoxSelectAllJobHistory.setText("Select none");
					tableJobHistory.selectAll();
				}
				else if (!checkBoxSelectAllJobHistory.isSelected())
				{
					checkBoxSelectAllJobHistory.setText("Select all");
					tableJobHistory.selectNone();
				}
				return true;
			}
		};
		worker.execute();
	}

	private void checkBoxSelectAllResultsHistoryActionPerformed(java.awt.event.ActionEvent evt)
	{
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				if (checkBoxSelectAllResultsHistory.isSelected())
				{
					checkBoxSelectAllResultsHistory.setText("Select none");
					tableResultsHistory.selectAll();
				}
				else if (!checkBoxSelectAllResultsHistory.isSelected())
				{
					checkBoxSelectAllResultsHistory.setText("Select all");
					tableResultsHistory.selectNone();
				}
				return true;
			}
		};
		worker.execute();
	}
	
	private void buttonEditAlgorithmParametersActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO the algo edit:
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				DefaultTableModel model = (DefaultTableModel) tableSelectAlgorithms.getModel();
				for(int i = 0; i < model.getRowCount(); i++)
				{
					if (Boolean.valueOf(
							model.getValueAt(i, model.getColumnCount() - 1).toString()))
					{
						// open the dataset modifiers
						final AlgorithmParameterEditor editor = algorithmParameterEditorsMap.get(model.getValueAt(i, model.findColumn("Short name")).toString());
						editor.setVisible(true);
					}
				}
				return true;
			}
		};
		worker.execute();
		
    }                                                             

    private void buttonEditDatasetParametersActionPerformed(java.awt.event.ActionEvent evt) {                                                            
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				DefaultTableModel model = (DefaultTableModel) tableSelectDataSets.getModel();
				for(int i = 0; i < model.getRowCount(); i++)
				{
					if (Boolean.valueOf(
							model.getValueAt(i, model.getColumnCount() - 1).toString()))
					{
						// open the dataset modifiers
						final DatasetParameterEditor editor = datasetParameterEditorsMap.get(model.getValueAt(i, model.findColumn("Data set")).toString());
						editor.setVisible(true);
					}
				}
				return true;
			}
		};
		worker.execute();
    }        

    private void buttonCancelSelectedActionPerformed(java.awt.event.ActionEvent evt) {                                                     
//        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
//			@Override
//			protected Boolean doInBackground() throws Exception
//			{
//				DefaultTableModel model = (DefaultTableModel) tableJobQueue.getModel();
//				for(int i = 0; i < model.getRowCount(); i++)
//				{
//					if (Boolean.valueOf(
//							model.getValueAt(i, model.getColumnCount() - 1).toString()) && model.getValueAt(i, model.findColumn("Date finished")).toString().equals("Pending..."))
//					{
//						
//						model.setValueAt("Cancelled.", i, model.findColumn("Date finished"));
//						//RecommenderJob job = jobsQueue.get(queueNo);
//						//job = null;
//						//jobsQueue.remove(queueNo);
////						jobsQueue.get(queueNo)
//						String queueNo = model.getValueAt(i, model.findColumn("Queue No.")).toString();
////						jobsQueue.get(queueNo).cancel();
//						jobsQueue.remove(queueNo);
//					}
//				}
//				return true;
//			}
//		};
//		if (ConfirmationUtil.getConfirmation("Really cancel the selected jobs?", "Cancel jobs?"))
//		{
//			worker.execute();
//		}
    }
    
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[])
	{
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting
		// code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.
		 * html
		 */
		try
		{
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
			{
				if ("Nimbus".equals(info.getName()))
				{
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch (ClassNotFoundException ex)
		{
			java.util.logging.Logger.getLogger(LibRecGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (InstantiationException ex)
		{
			java.util.logging.Logger.getLogger(LibRecGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (IllegalAccessException ex)
		{
			java.util.logging.Logger.getLogger(LibRecGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (javax.swing.UnsupportedLookAndFeelException ex)
		{
			java.util.logging.Logger.getLogger(LibRecGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				try
				{
					new LibRecGUI().setVisible(true);
				}
				catch (IOException ex)
				{
					Logger.getLogger(LibRecGUI.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton buttonCancelSelected;
	private javax.swing.JButton buttonClearConsole;
	private javax.swing.JButton buttonDeleteSelectedJobHistoryTab;
	private javax.swing.JButton buttonDeleteSelectedOutputTab;
	private javax.swing.JButton buttonDeleteSelectedQueueTab;
	private javax.swing.JButton buttonDeleteSelectedResultsHistoryTab;
    private javax.swing.JButton buttonEditAlgorithmParameters;
    private javax.swing.JButton buttonEditDatasetParameters;
	private javax.swing.JButton buttonExcludeJobHistoryTab;
	private javax.swing.JButton buttonExcludeOutputTab;
	private javax.swing.JButton buttonExcludeQueueAlgorithms;
	private javax.swing.JButton buttonExcludeQueueDatasets;
	private javax.swing.JButton buttonExcludeQueueTab;
	private javax.swing.JButton buttonExcludeResultsHistoryTab;
	private javax.swing.JButton buttonGraphSelectedJobOutput;
	private javax.swing.JButton buttonGraphSelectedResultsHistory;
	private javax.swing.JButton buttonIncludeAlgorithms;
	private javax.swing.JButton buttonIncludeDatasets;
	private javax.swing.JButton buttonIncludeJobHistoryTab;
	private javax.swing.JButton buttonIncludeOutputTab;
	private javax.swing.JButton buttonIncludeQueueTab;
	private javax.swing.JButton buttonIncludeResultsHistoryTab;
	private javax.swing.JButton buttonQueueJobs;
	private javax.swing.JButton buttonRunAll;
	private javax.swing.JButton buttonRunSelectedQueueTab;
	private javax.swing.JButton buttonRunSelectedRecTab;
	private javax.swing.JCheckBox checkBoxSelectAllAlgorithms;
	private javax.swing.JCheckBox checkBoxSelectAllDatasets;
	private javax.swing.JCheckBox checkBoxSelectAllJobHistory;
	private javax.swing.JCheckBox checkBoxSelectAllJobOutput;
	private javax.swing.JCheckBox checkBoxSelectAllJobQueue;
	private javax.swing.JCheckBox checkBoxSelectAllResultsHistory;
	private javax.swing.JToolBar jToolBar1;
	private javax.swing.JPanel panelConsole;
	private javax.swing.JPanel panelHistory;
	private javax.swing.JPanel panelJobHistory;
	private javax.swing.JPanel panelJobOutput;
	private javax.swing.JPanel panelJobQueue;
	private javax.swing.JPanel panelRecommendationTab;
	private javax.swing.JPanel panelResultsHistory;
	private javax.swing.JScrollPane scrollPaneAlgorithms;
	private javax.swing.JScrollPane scrollPaneConsole;
	private javax.swing.JScrollPane scrollPaneDatasets;
	private javax.swing.JScrollPane scrollPaneJobHistory;
	private javax.swing.JScrollPane scrollPaneJobOutput;
	private javax.swing.JScrollPane scrollPaneJobQueue;
	private javax.swing.JScrollPane scrollPaneResultsHistory;
	private javax.swing.JTabbedPane tabbedPaneAll;
	private javax.swing.JTabbedPane tabbedPaneHistory;
	private UserFriendlyTable tableJobHistory;
	private UserFriendlyTable tableJobOutput;
	private UserFriendlyTable tableJobQueue;
	private UserFriendlyTable tableResultsHistory;
	private UserFriendlyTable tableSelectAlgorithms;
	private UserFriendlyTable tableSelectDataSets;
	private javax.swing.JTextArea textAreaConsole;
	private javax.swing.JTextField textFieldSearchAlgorithms;
	private javax.swing.JTextField textFieldSearchDatasets;
	private javax.swing.JTextField textFieldSearchJobHistory;
	private javax.swing.JTextField textFieldSearchJobOutput;
	private javax.swing.JTextField textFieldSearchJobQueue;
	private javax.swing.JTextField textFieldSearchResultsHistory;
	// End of variables declaration//GEN-END:variables

	private void initialiseDataSets() throws IOException
	{
		// https://stackoverflow.com/a/5125258/6947011
		String pathname = System.getProperty("user.dir") + File.separator + "data" + File.separator;
		File file = new File(System.getProperty("user.dir") + File.separator + "data" + File.separator);
		String[] directories = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name)
			{
				return new File(current, name).isDirectory();
			}
		});
		
		for (String directory : directories)
		{
			Properties props = getProps(directory);
			
			HashMap<String,List<Object>> map = new HashMap<>();
			
			String propString = "<html>";
			for(String key : props.stringPropertyNames())
			{
				List<Object> parameters = new ArrayList<>();
				parameters.add(props.getProperty(key));
//				map.put(, props.getProperty(key));
//				parameters = 
				map.put(key, parameters);
				propString += key + "=" + props.getProperty(key) + "<br>";
			}
			datasetParametersMap.put(directory, map);
			
//			for()
			tableSelectDataSets.addRow(directory + StringUtil.FOUR_COLONS + propString);
		}
	}

	private void initialiseAlgorithms()
	{
		String algorithms = "";
		try
		{
//			System.out.println(System.getProperty("user.dir"));
			algorithms = FileUtil.readAsString(getPath("conf") + "algorithmShortNames.txt").trim();
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		if(algorithms.isEmpty())
		{
			algorithms = "aobpr,gbpr,aspectmodelranking,aspectmodelrating,associationrule,asvdpp,bhfree,biasedmf,bpmf,bpoissmf,bpr,bucm,climf,constantguess,"
				+ "eals,external,fismrmse,globalaverage,gplsa,hybrid,itemaverage,itemcluster,itemknn,lda,ldcc,listrankmf,llorma,mfals,mostpopular,nmf,pcc,"
				+ "personalitydiagnosis,plsa,pmf,prankd,randomguess,rankals,ranksgd,rbm,rfrec,rste,sbpr,slim,slopeone,socialmf,sorec,soreg,svdpp,trustsvd,"
				+ "trustmf,urp,useraverage,usercluster,wbpr,wrmf";
		}
//		Class c;
//		try
//		{
//			c = DriverClassUtil.getClass("wrmf");
//			System.out.println(DriverClassUtil.getDriverName("net.librec.recommender.ext.AssociationRuleRecommender"));
//		}
//		catch (ClassNotFoundException e)
//		{
//			e.printStackTrace();
//		}
		

//		System.out.println(algorithms);
		// get algorithm descriptions
		HashMap<String, String> descMap = new HashMap<>();
		BufferedReader br;
		try
		{
			br = new BufferedReader(new FileReader(getPath("doc") + "algorithmDescriptions"));
			String line = null;
			String desc = "";
			while((line = br.readLine()) != null)
			{
//			System.out.println(line);
				desc += line;
				if(line.isEmpty())
				{
					String[] splitString = desc.split("<html>");
					descMap.put(splitString[0], "<html>" + splitString[1]);
//				System.out.println(desc);
					desc = "";
				}	
			}
		}
		catch (IOException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		String[] algorithmsArray = algorithms.split(",");
		for (String algorithm : algorithmsArray)
		{
			try
			{
//				System.out.println("algorithm = " + algorithm);
				if(!algorithm.isEmpty() && DriverClassUtil.getClass(algorithm) != null)
				{
					String algorithmName = DriverClassUtil.getClass(algorithm).getSimpleName();
					algorithmName = algorithmName.replaceAll("(\\p{Ll})(\\p{Lu})","$1 $2");
					if(!algorithmName.contains(" Recommender"))
			        {
						algorithmName = algorithmName.replaceAll("Recommender", " Recommender");
			        }
//					System.out.println(algorithmName);
					Properties props = getProps(algorithm);
					HashMap<String, List<Object>> map = new HashMap<>();//FileUtil.readAsMap(getPath("conf"))
//					String algoParams = "";
					String propString = "<html>";
					for(String name : props.stringPropertyNames())
					{
						
						if(!name.equals("rec.recommender.class"))
						{
							List<Object> valuesList = new ArrayList<>();
							String[] values = props.getProperty(name).split(",");
							propString += name + "=";
							for(String value : values)
							{
								valuesList.add(value);
								propString += value + ",";
							}
							map.put(name, valuesList);
							propString += "<br>";
						}
							
					}
					
					algorithmParametersMap.put(algorithm, map);
					String description = descMap.get(algorithm);
					String algorithmEntry = algorithmName + StringUtil.FOUR_COLONS + algorithm + StringUtil.FOUR_COLONS + propString + StringUtil.FOUR_COLONS
							+ description;
					tableSelectAlgorithms.addRow(algorithmEntry);
				}
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			
		}
	}

	private Configuration getConf(String filename)
	{
		Configuration conf = new Configuration();
		Properties prop = new Properties();
		InputStream input = null;
		try
		{
			input = new FileInputStream(System.getProperty("user.dir") + File.separator + "conf" + File.separator
					+ filename + ".properties");
			prop.load(input);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		for (String name : prop.stringPropertyNames())
		{
			conf.set(name.trim(), prop.getProperty(name).trim());
		}
		return conf;
	}
	
	private Properties getProps(String filename)
	{
		Properties prop = new Properties();
		InputStream input = null;
		try
		{
			input = new FileInputStream(System.getProperty("user.dir") + File.separator + "conf" + File.separator
					+ filename + ".properties");
			prop.load(input);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return prop;
	}

	private void runJobs(List<JobDetails> jobsToRun)
	{			
		for(JobDetails job : jobsToRun)
		{
//			runJob(job);
//			System.out.println(job.getAlgorithmName());
//			jobsQueue.put(job.getQueueNo(), job);
			// for some reason this works but the the other doesnt
			Configuration conf = getConf(job.getAlgorithmName());
//			Configuration conf = new Configuration();
			conf.set("rec.recommeder.class", job.getAlgorithmName());
			String dataset = job.getDataSet();
//			conf.set("dfs.data.dir", "data");
//			conf.set("dfs.result.dir", "result");
			conf.set("data.input.path", dataset);
			conf.set("data.model.format", "text");
			conf.set("rec.eval.enable", "true");
			conf.set("rec.random.seed", "1");
			String datasetParameters = job.getDataParams();
			String[] splitParams = datasetParameters.replaceAll("<html>", "").split("<br>");
//			String[] splitParams = datasetParameters.split(StringUtil.FOUR_SPACES);
//			System.out.println("dataparams = " + datasetParameters);
//			for(String s : splitParams)
//				System.out.println(s);
			// make convertor if doesn't exist
			
			// parse the data parameters
			conf.set("data.column.format", splitParams[0].split("=")[1]);
			String splitterParam = splitParams[1];
			if (splitterParam.contains("cv"))
			{
				conf.set("data.model.splitter", "kcv");
				conf.set("data.splitter.cv.number", "" + Integer.parseInt(splitterParam.split("=")[1]));
			}
			else if (splitterParam.contains("ratio"))
			{
				conf.set("data.model.splitter", "ratio");
				String type = splitterParam.substring("data.splitter.ratio.".length()).replaceAll("=.*", "");
//				System.out.println("type = " + type);
				conf.set("data.splitter.ratio", type);
				conf.set("data.splitter.trainset.ratio", "" + Double.parseDouble(splitterParam.split("=")[1]));
			}
			else if (splitterParam.contains("givenn"))
			{
				conf.set("data.model.splitter", "givenn");
				String type = splitterParam.substring("data.splitter.givenn.".length()).replaceAll("=.*", "");
				conf.set("data.splitter.givenn", type);
				conf.set("data.splitter.givenn.n", "" + Integer.parseInt(splitterParam.split("=")[1]));
			}
			
			// parse the algorithm parameters
			String[] algoParams = job.getAlgoParams().replaceAll("<html>", "").split("<br>");
//			System.out.println("algoparams = " + job.getAlgoParams());
//			String[] algoParams = job.getAlgoParams().split(StringUtil.FOUR_SPACES);
			for(String pair : algoParams)
			{
				String key = pair.split("=")[0];
				String value = pair.split("=")[1];
				conf.set(key, value);
			}
//			for(String name : conf.toString())
//			System.out.println(conf.toString());
//			Iterator<Entry<String,String>> it = conf.iterator();
//			while(it.hasNext())
//			{
//				Entry e = it.next();
//				System.out.println(e.getKey() + "=" + e.getValue());
//			}
			
//			it.forEachRemaining(action);
			if (!dataConvertorsMap.containsKey(dataset+"-"+splitParams[0]+"-"+"-binarise-"+splitParams[2]))
			{
				TextDataConvertor convertor;
				if (splitParams[2].contains(NONE) || conf.get("rec.recommender.isranking").equals("false"))
					convertor = new TextDataConvertor(getPath("data") + dataset);
				else
					convertor = new TextDataConvertor(splitParams[0].split("=")[1], getPath("data") + dataset,
							Double.parseDouble(splitParams[2].split("=")[1]));
				try
				{
					convertor.processData();
				}
				catch (IOException e)
				{
					e.printStackTrace();
//					continue;
				}
				convertor.setDataset(dataset);
				dataConvertorsMap.put(dataset, convertor);
//				System.out.println("Dataset = " + convertor.getDataset());
			}
			try
			{
				RecommenderJob recJob;
				recJob = new RecommenderJob(conf);
//				jobsQueue.put(job.getQueueNo(), recJob);
				recJob.setDataConvertor(dataConvertorsMap.get(dataset));
//				System.out.println("Dataset params = " + datasetParameters + ",algoparams = " + job.getAlgoParams());
				recJob.runJob();

				job.setDateFinished(DateUtil.getDateFormat(DATE_FORMAT).format(new Date()));
				DefaultTableModel model = (DefaultTableModel) tableJobQueue.getModel();
				for (int i = 0; i < model.getRowCount(); i++)
				{
					if (model.getValueAt(i, model.findColumn("Queue No.")).toString().equals(job.getQueueNo()))
					{
						// set finished time
						model.setValueAt(job.getDateFinished(), i,
								model.findColumn("Date finished"));
						tableJobQueue.adjustColumnWidth();
						break;
					}
				}
//				System.out.println("Dataset params = " + datasetParameters + ",algoparams" + job.getAlgoParams());
				recJob.getJobOutput().setDataParams(datasetParameters);
				recJob.getJobOutput().setAlgoParams(job.getAlgoParams());
				String jobOutputEntry = recJob.getJobOutputString();
//				System.out.println("output = " + jobOutputEntry);
				// set the non-short name for algorithm
				job.setAlgorithmName(getAlgorithmName(job.getAlgorithmName()));
				String jobHistoryEntry = job.makeJobString();

				tableJobOutput.addRow(job.getQueueNo() + StringUtil.FOUR_COLONS + jobOutputEntry);
//				System.out.println(job.getQueueNo() + );
				tableResultsHistory.addRow(jobOutputEntry);
				tableJobHistory.addRow(jobHistoryEntry);
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (LibrecException e)
			{
				e.printStackTrace();
			}
			finally
			{
//				continue;
			} 
		}
//		new NotificationUtil("All selected jobs finished.", NO_TIME_OUT);
//		}
	}
/**	
	private void runJob(final JobDetails job)
	{
		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
			@Override
			protected Boolean doInBackground() throws Exception
			{
				System.out.println(job.getAlgorithmName());
				jobsQueue.put(job.getQueueNo(), this);
				
				// for some reason this works but the the other doesnt
				Configuration conf = getConf(job.getAlgorithmName());
//				Configuration conf = new Configuration();
				conf.set("rec.recommeder.class", job.getAlgorithmName());
				String dataset = job.getDataSet();
//				conf.set("dfs.data.dir", "data");
//				conf.set("dfs.result.dir", "result");
				conf.set("data.input.path", dataset);
				conf.set("data.model.format", "text");
				conf.set("rec.eval.enable", "true");
				conf.set("rec.random.seed", "1");
				String datasetParameters = job.getDataParams();
				String[] splitParams = datasetParameters.split(StringUtil.FOUR_SPACES);
				// make convertor if doesn't exist
				
				// parse the data parameters
				conf.set("data.column.format", splitParams[0].split("=")[1]);
				String splitterParam = splitParams[1];
				if (splitterParam.contains("cv"))
				{
					conf.set("data.model.splitter", "kcv");
					conf.set("data.splitter.cv.number", "" + Integer.parseInt(splitterParam.split("=")[1]));
				}
				else if (splitterParam.contains("ratio"))
				{
					conf.set("data.model.splitter", "ratio");
					String type = splitterParam.substring("data.splitter.ratio.".length()).replaceAll("=.*", "");
					System.out.println("type = " + type);
					conf.set("data.splitter.ratio", type);
					conf.set("data.splitter.trainset.ratio", "" + Double.parseDouble(splitterParam.split("=")[1]));
				}
				else if (splitterParam.contains("givenn"))
				{
					conf.set("data.model.splitter", "givenn");
					String type = splitterParam.substring("data.splitter.givenn.".length()).replaceAll("=.*", "");
					conf.set("data.splitter.givenn", type);
					conf.set("data.splitter.givenn.n", "" + Integer.parseInt(splitterParam.split("=")[1]));
				}
				
				// parse the algorithm parameters
				String[] algoParams = job.getAlgoParams().split(StringUtil.FOUR_SPACES);
				for(String pair : algoParams)
				{
					String key = pair.split("=")[0];
					String value = pair.split("=")[1];
					conf.set(key, value);
				}
//				for(String name : conf.toString())
//				System.out.println(conf.toString());
				Iterator<Entry<String,String>> it = conf.iterator();
				while(it.hasNext())
				{
					Entry e = it.next();
					System.out.println(e.getKey() + "=" + e.getValue());
				}
				
//				it.forEachRemaining(action);
				if (!dataConvertorsMap.containsKey(dataset+"-"+splitParams[0]+"-"+"-binarise-"+splitParams[2]))
				{
					TextDataConvertor convertor;
					if (splitParams[2].contains(NONE) || conf.get("rec.recommender.isranking").equals("false"))
						convertor = new TextDataConvertor(getPath("data") + dataset);
					else
						convertor = new TextDataConvertor(splitParams[0].split("=")[1], getPath("data") + dataset,
								Double.parseDouble(splitParams[2].split("=")[1]));
					try
					{
						convertor.processData();
					}
					catch (IOException e)
					{
						e.printStackTrace();
//						continue;
					}
					convertor.setDataset(dataset);
					dataConvertorsMap.put(dataset, convertor);
					System.out.println("Dataset = " + convertor.getDataset());
				}
				try
				{
					RecommenderJob recJob;
//					recJob.
					recJob = new RecommenderJob(conf);
					recJob.setDataConvertor(dataConvertorsMap.get(dataset));
					recJob.runJob();
					job.setDateFinished(DateUtil.getDateFormat(DATE_FORMAT).format(new Date()));
					DefaultTableModel model = (DefaultTableModel) tableJobQueue.getModel();
					for (int i = 0; i < model.getRowCount(); i++)
					{
						if (model.getValueAt(i, model.findColumn("Queue No.")).toString().equals(job.getQueueNo()))
						{
							// set finished time
							model.setValueAt(DateUtil.getDateFormat(DATE_FORMAT).format(new Date()), i,
									model.findColumn("Date finished"));
							tableJobQueue.adjustColumnWidth();
							break;
						}
					}
					recJob.getJobOutput().setDataParams(datasetParameters);
					recJob.getJobOutput().setAlgoParams(job.getAlgoParams());
					String jobOutputEntry = recJob.getJobOutputString();

					// set the non-short name for algorithm
					job.setAlgorithmName(getAlgorithmName(job.getAlgorithmName()));
					String jobHistoryEntry = job.makeJobString();

					tableJobOutput.addRow(job.getQueueNo() + StringUtil.FOUR_COLONS + jobOutputEntry);
//					System.out.println(job.getQueueNo() + );
					tableResultsHistory.addRow(jobOutputEntry);
					tableJobHistory.addRow(jobHistoryEntry);
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				catch (LibrecException e)
				{
					e.printStackTrace();
				}
				finally
				{
//					continue;
				} 
				return true;
			}
		};
		worker.execute();
	}	
*/	
	
	// not working
	@SuppressWarnings("unchecked")
	private void loadDatasets()
	{
		// prompt to load datasets
		if(ConfirmationUtil.getConfirmation("Would you like to load datasets?", "Load datasets?"))
		{
			try (FileInputStream fi = new FileInputStream(new File(getPath("data") + "dataConvertorObjects.ser"));
					ObjectInputStream oi = new ObjectInputStream(fi))
			{
//				while (true)
//				{
//					TextDataConvertor dc = (TextDataConvertor) oi.readObject();
//					dataConvertorsMap.put(dc.getDataset(), dc);
//				}
				dataConvertorsMap = new HashMap<>((Map<String, DataConvertor>) oi.readObject());
				System.out.println("Finished loading datasets.");
			}
			catch (IOException | ClassNotFoundException e)
			{
				//e.printStackTrace();
//				if (e instanceof EOFException)
//				{
//					System.out.println("End of the object file");
//				}
			} 
		}


	}
	
	private void setConsoleOutput()
	{
		if(ConfirmationUtil.getConfirmation("Set log output to be in console tab?\n(Should only select 'No' if running from IDE)", "Set log output"))
		{
			// taken from https://stackoverflow.com/a/343084/6947011
			TextAreaOutputStream taos = new TextAreaOutputStream(textAreaConsole, 60);
			PrintStream ps = new PrintStream(taos);
			System.setOut(ps);
			System.setErr(ps);
		}
	}

	private void makeGraphs(List<JobOutput> jobOutputs)
	{
		LinkedHashMap<String, List<JobOutput>> map = new LinkedHashMap<>();
		for (JobOutput jobOutput : jobOutputs)
		{
			String algoType = "";
			if(jobOutput.getEvaluationsMap().get("RMSE") != 0.0)
			{
				algoType = " rating algorithms";
			}
			else
			{
				algoType = " ranking algorithms";
			}
			String dataset = jobOutput.getDataSet() + algoType;
			if (map.containsKey(dataset))
			{
				map.get(dataset).add(jobOutput);
			}
			else
			{
				map.put(dataset, new ArrayList<JobOutput>());
				map.get(dataset).add(jobOutput);
			}
			// System.out.println(jobOutput.makeString());
		}
		for (Entry<String, List<JobOutput>> entry : map.entrySet())
		{
			String dataset = entry.getKey();
			List<JobOutput> value = entry.getValue();
			BarChart_AWT chart = new BarChart_AWT(value, "Graph for " + dataset, dataset);
			chart.setView();
		}
	}
	
	private String getAlgorithmName(String shortName)
	{
		String algorithmName = shortName;
		try
		{
			algorithmName = DriverClassUtil.getClass(shortName).getSimpleName().replaceAll("(\\p{Ll})(\\p{Lu})","$1 $2");
			if(!algorithmName.contains(" Recommender"))
	        {
				algorithmName = algorithmName.replaceAll("Recommender", " Recommender");
	        }
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return algorithmName;
	}
	
	private void adjustTables()
	{
		// change the selection column to be at start in the view (but not the model)
		tableResultsHistory.setSelectionColumnView();
		tableJobOutput.setSelectionColumnView();
		tableSelectDataSets.setSelectionColumnView();
		tableSelectAlgorithms.setSelectionColumnView();
		tableJobQueue.setSelectionColumnView();
		tableJobHistory.setSelectionColumnView();

		tableResultsHistory.adjustColumnWidth();
		tableJobOutput.adjustColumnWidth();
		tableSelectDataSets.adjustColumnWidth();
		tableSelectAlgorithms.adjustColumnWidth();
		tableJobQueue.adjustColumnWidth();
		tableJobHistory.adjustColumnWidth();

		tableResultsHistory.setShowVerticalLines(true);
		tableJobOutput.setShowVerticalLines(true);
		tableSelectDataSets.setShowVerticalLines(true);
		tableSelectAlgorithms.setShowVerticalLines(true);
		tableJobQueue.setShowVerticalLines(true);
		tableJobHistory.setShowVerticalLines(true);
		
		tableResultsHistory.setShowHorizontalLines(true);
		tableJobOutput.setShowHorizontalLines(true);
		tableSelectDataSets.setShowHorizontalLines(true);
		tableSelectAlgorithms.setShowHorizontalLines(true);
		tableJobQueue.setShowHorizontalLines(true);
		tableJobHistory.setShowHorizontalLines(true);
//		tableSelectAlgorithms.setWordWrap(tableSelectAlgorithms.findColumn("Parameters"));
//		variableTableHeight(tableSelectAlgorithms, tableSelectAlgorithms.findColumn("Parameters"));
		
//		UIManager.put("Table.alternateRowColor", Color.PINK);
		
//		menuInit(tableSelectDataSets);
//		menuInit(tableSelectAlgorithms);
	}

	
	private void setParameters(UserFriendlyTable table, int row) // model row or view row?
	{
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		if(table.equals(tableSelectDataSets))
		{
//			model.setValueAt("test dataset", row, model.findColumn("Parameters"));
			DatasetParameterEditor dEditor = new DatasetParameterEditor(model.getValueAt(table.convertRowIndexToModel(row), model.findColumn("Data set")).toString());
//			dEditor.dispose();
		}
		else if(table.equals(tableSelectAlgorithms))
		{
			model.setValueAt("test algo", row, model.findColumn("Parameters"));
		}
	}

	private String getPath(String folder)
	{
		return System.getProperty("user.dir") + File.separator + folder + File.separator;
	}
	
	private void initDatasetEditorsMap()
	{
		final DefaultTableModel model = (DefaultTableModel) tableSelectDataSets.getModel();
		for(int i = 0; i < model.getRowCount(); i++)
		{
			int col = model.findColumn("Data set");
			final DatasetParameterEditor editor = new DatasetParameterEditor( model.getValueAt(i, col).toString());
			// tell editor what do to
			editor.getSaveButton().addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt)
				{
					if (ConfirmationUtil.getConfirmation("Really save these parameter settings?", "Really save?"))
					{
						HashMap<String,List<Object>> props = new HashMap<>();

						// column format
						List<Object> columnFormats = editor.getColumnFormats();
						props.put("data.column.format", columnFormats);
						
						// making ratings binary?
						if(editor.binariseRatingsIsSelected())
						{
							double start, step, end;
//							String values = "N/A";
							List<Object> values = new ArrayList<>();
							values.add("N/A");
							start = editor.getThresholdMin();
							if(start > 0.0)
							{
//								values = "" + start + "/";
								values.add(start);
								if(editor.binaryStepIsSelected())
								{
									step = editor.getThresholdStep();
									end = editor.getThresholdMax();
									for(double j = start + step; j <= end; j += step)
									{
										values.add(j);
									}
								}
							}
							if(!values.isEmpty())
								props.put("data.convert.binarize.threshold", values);
						}
						else
						{
							List<Object> values = new ArrayList<>(1);
							values.add(NONE);
							props.put("data.convert.binarize.threshold", values);
						}
						// split by ratio
						if(editor.splitRatioIsSelected())
						{
							DefaultTableModel model = (DefaultTableModel) editor.getTableSplitRatio().getModel();
							for(int i = 0; i < editor.getTableSplitRatio().getRowCount(); i++)
							{
								if(Boolean.valueOf(model.getValueAt(i, model.findColumn("")).toString()))
								{
									if(!model.getValueAt(i,model.findColumn("Start value")).toString().isEmpty())
									{
										double start, step = 0.0, end = 0.0;
										start = Double.parseDouble(model.getValueAt(i,model.findColumn("Start value")).toString());
										if(!model.getValueAt(i,model.findColumn("Step")).toString().isEmpty())
											step = Double.parseDouble(model.getValueAt(i,model.findColumn("Step")).toString());
//										List<String> values = "" + start;
										List<Object> values = new ArrayList<>();
										if(start > 0.0)
											values.add(start);
										if (step > 0.0 && !model.getValueAt(i,model.findColumn("End value")).toString().isEmpty())
										{
											end = Double.parseDouble(model.getValueAt(i,model.findColumn("End value")).toString());
											for (double j = start + step; j <= end; j += step)
											{
												values.add(j);
											} 
										}
										if(!model.getValueAt(i, model.findColumn("Manual values")).toString().isEmpty())
										{
											String[] manualValues = model.getValueAt(i, model.findColumn("Manual values")).toString().split(","); 
											for(String manualValue : manualValues)
											{
												if(Double.parseDouble(manualValue) > 0 && !values.contains(manualValue))
													values.add(manualValue);
											}
												
										}
										if(!values.isEmpty())
											props.put("data.splitter.ratio." + model.getValueAt(i, model.findColumn("Type")).toString(), values);
									}
								}
							}

						}
						
						// keep N
						if(editor.keepNIsSelected())
						{
							DefaultTableModel model = (DefaultTableModel) editor.getTableKeepN().getModel();
							for(int i = 0; i < editor.getTableKeepN().getRowCount(); i++)
							{
								if(Boolean.valueOf(model.getValueAt(i, model.findColumn("")).toString()))
								{
									int start, step, end;
									start = Integer.parseInt(model.getValueAt(i,model.findColumn("Start value")).toString());
									step = Integer.parseInt(model.getValueAt(i,model.findColumn("Step")).toString());
									
//									String values = "" + start;
									List<Object> values = new ArrayList<>();
									if(start > 0)
									{
										values.add(start);
										if (step > 0 && !model.getValueAt(i, model.findColumn("End value"))
												.toString().isEmpty())
										{
											end = Integer.parseInt(
													model.getValueAt(i, model.findColumn("End value")).toString());
											for (int j = start + step; j <= end; j += step)
											{
												values.add(j);
											}
										} 
									}
									if(!model.getValueAt(i, model.findColumn("Manual values")).toString().isEmpty())
									{
										String[] manualValues = model.getValueAt(i, model.findColumn("Manual values")).toString().split(","); 
										for(String manualValue : manualValues)
										{
											if (Integer.parseInt(manualValue) > 0 && !values.contains(manualValue))
												values.add(manualValue);
										}
									}
									if(!values.isEmpty())
										props.put("data.splitter.givenn." + model.getValueAt(i, model.findColumn("Type")).toString(), values);
								}
							}
						}
						
						// split by K fold
						if(editor.kFoldsIsSelected())
						{
							int start, step, end;
							start = editor.getStartFolds();
							step = editor.getStepFolds();
							end = editor.getEndFolds();
							List<Object> folds = new ArrayList<>();
							folds.add(start);
							if (step > 0)
							{
								for (int i = start + step; i <= end; i += step)
								{
									folds.add(i);
								} 
							}
							props.put("data.splitter.cv.number", folds);
						}
						datasetParametersMap.put(editor.getDataset(), props);
						int rowNum = tableSelectDataSets.getRowIndexFromValue(editor.getDataset(), model.findColumn("Data set"));
						if(rowNum != -1)
						{
							String propString = "<html>";
							for(Map.Entry<String, List<Object>> entry : props.entrySet())
							{
								propString += entry.getKey() + "=";
								for(Object o : entry.getValue())
								{
									propString += o + ",";
								}
								propString += "<br>";
							}
							model.setValueAt(propString, rowNum, model.findColumn("Parameters"));
							tableSelectDataSets.adjustColumnWidth();
						}
						// save does a print and a dispose
						editor.save();
					}
				}
			});
			datasetParameterEditorsMap.put( model.getValueAt(i, col).toString(), editor);
		}
	}
	
	private void initAlgoEditorsMap()
	{
		final DefaultTableModel tableModelAlgorithm = (DefaultTableModel) tableSelectAlgorithms.getModel();
		for(int i = 0; i < tableModelAlgorithm.getRowCount(); i++)
		{
			int col = tableModelAlgorithm.findColumn("Short name");
			final AlgorithmParameterEditor editor = new AlgorithmParameterEditor( tableModelAlgorithm.getValueAt(i, col).toString());
			// tell editor what do to
			editor.getSaveButton().addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt)
				{
					if (ConfirmationUtil.getConfirmation("Really save these parameter settings?", "Really save?"))
					{
						HashMap<String, List<Object>> props = new HashMap<>();
						DefaultTableModel tableModelDouble = (DefaultTableModel) editor.getTableDoubleParameters().getModel();
						for(int i = 0; i < editor.getTableDoubleParameters().getRowCount(); i++)
						{
//							if(model.getValueAt(i,model.findColumn("Start value")).toString())
							double start = 0, step = 0, end = 0;
							if(!tableModelDouble.getValueAt(i,tableModelDouble.findColumn("Start value")).toString().isEmpty())
								start = Double.parseDouble(tableModelDouble.getValueAt(i,tableModelDouble.findColumn("Start value")).toString());
							if(!tableModelDouble.getValueAt(i,tableModelDouble.findColumn("Step")).toString().isEmpty())
								step = Double.parseDouble(tableModelDouble.getValueAt(i,tableModelDouble.findColumn("Step")).toString());
//								String values = "" + start;
							List<Object> values = new ArrayList<>();
							
							if(start > 0)
							{
//								System.out.print(start);
								values.add(start);
								if (step > 0 && !tableModelDouble.getValueAt(i, tableModelDouble.findColumn("End value"))
										.toString().isEmpty())
								{
									end = Double.parseDouble(
											tableModelDouble.getValueAt(i, tableModelDouble.findColumn("End value")).toString());
									for (double j = start + step; j <= end; j += step)
									{
										values.add(j);
									}
								} 
							}
							String  manualVal = tableModelDouble.getValueAt(i, tableModelDouble.findColumn("Manual values")).toString();
							if(!manualVal.isEmpty())
							{
								String[] manualValues = manualVal.split(","); 
								for(String manualValue : manualValues)
								{
									if(Double.parseDouble(manualValue) > 0 && !values.contains(manualValue))
										values.add(manualValue);
								}
									
							}
							if(!values.isEmpty())
								props.put(tableModelDouble.getValueAt(i, tableModelDouble.findColumn("Parameter")).toString(), values);

						}
						
						DefaultTableModel tableModelInt = (DefaultTableModel) editor.getTableIntParameters().getModel();
						for(int i = 0; i < editor.getTableIntParameters().getRowCount(); i++)
						{
							int start = 0, step = 0, end;
							
							if (!tableModelInt
									.getValueAt(i, tableModelInt.findColumn("Start value")).toString().isEmpty())
							{
								start = Integer.parseInt(tableModelInt
										.getValueAt(i, tableModelInt.findColumn("Start value")).toString());
							}
							if (!tableModelInt.getValueAt(i, tableModelInt.findColumn("Step")).toString().isEmpty())
							{
								step = Integer.parseInt(
										tableModelInt.getValueAt(i, tableModelInt.findColumn("Step")).toString());
							}
							//							String values = "" + start;
							List<Object> values = new ArrayList<>();
							if(start > 0)
							{
								values.add(start);
								if (step > 0 && !tableModelInt.getValueAt(i, tableModelInt.findColumn("End value"))
										.toString().isEmpty())
								{
									end = Integer.parseInt(
											tableModelInt.getValueAt(i, tableModelInt.findColumn("End value")).toString());
									for (int j = start + step; j <= end; j += step)
									{
										values.add(j);
									}
								} 
							}
							if(!tableModelInt.getValueAt(i, tableModelInt.findColumn("Manual values")).toString().isEmpty())
							{
								String[] manualValues = tableModelInt.getValueAt(i, tableModelInt.findColumn("Manual values")).toString().split(","); 
								for(String manualValue : manualValues){
									if(Integer.parseInt(manualValue) > 0 && !values.contains(manualValue))
										values.add(manualValue);
								}
							}
							if(!values.isEmpty())
								props.put(tableModelInt.getValueAt(i, tableModelInt.findColumn("Parameter")).toString(), values);
							
						}
						
						DefaultTableModel tableModelString = (DefaultTableModel) editor.getTableStringParameters().getModel();
						for(int i = 0; i < editor.getTableStringParameters().getRowCount(); i++)
						{
							List<Object> values = new ArrayList<>();
							if(!tableModelString.getValueAt(i, tableModelString.findColumn("Parameter")).toString().isEmpty())
							{
								String value = tableModelString.getValueAt(i, tableModelString.findColumn("Value(s)")).toString();
								values.add(value);
							}

							if(!values.isEmpty())
								props.put(tableModelString.getValueAt(i, tableModelString.findColumn("Parameter")).toString(), values);
						}
						
						algorithmParametersMap.put(editor.getAlgorithm(), props);
						int rowNum = tableSelectAlgorithms.getRowIndexFromValue(editor.getAlgorithm(), tableModelAlgorithm.findColumn("Short name"));
						if(rowNum != -1)
						{
							String propString = "<html>";
							for(Map.Entry<String, List<Object>> entry : props.entrySet())
							{
								propString += entry.getKey() + "=";
								for(Object o : entry.getValue())
								{
									propString += o + ",";
								}
								propString += "<br>";
							}
//							model.setValueAt(propString, rowNum, model.findColumn("Parameters"));
							tableModelAlgorithm.setValueAt(propString, rowNum, tableModelAlgorithm.findColumn("Parameters"));
							tableSelectAlgorithms.adjustColumnWidth();
						}
						editor.save();
					}
				}
			});
//			int rowNum = tableSelectAlgorithms.getRowIndexFromValue(editor.getAlgorithm(), tableModelAlgorithm.findColumn("Short name"));
//			if(rowNum != -1)
//			{
//				tableModelAlgorithm.setValueAt(algorithmParametersMap.get(editor.getAlgorithm()).toString(), rowNum, tableModelAlgorithm.findColumn("Parameters"));
//				tableSelectAlgorithms.adjustColumnWidth();
//			}
			algorithmParameterEditorsMap.put(tableModelAlgorithm.getValueAt(i, col).toString(), editor);
		}
		
	}

	
	//adapted from https://stackoverflow.com/a/5323932/6947011
	private static void mapPermuteList(Map<String, List<Object>> map, String currentPermutation, List<Object> params) {
	    String key = map.keySet().iterator().next(); // get the topmost key

	    // base case
	    if (map.size() == 1) {          
	        for (Object value : map.get(key)) {
//	            System.out.println(currentPermutation + key + "=" + value);
	        	params.add(currentPermutation + key + "=" + value);
	        }
	    } else {
	        // recursive case
	        Map<String, List<Object>> subMap = new HashMap<>(map);

	        for (Object value : subMap.remove(key)) {
	            mapPermuteList(subMap, currentPermutation + key + "=" + value + "<br>" , params);
	        }
	    }
	}
	
//	private static void printJavadoc()
//	{
//		for(Annotation a : ItemBigramRecommender.class.getDeclaredAnnotations())
//		{
////			System.out.println(a.toString());
//		}
////		ItemBigramRecommender.class.
//	}
//	
//	private void getTime()
//	{
//		long second = 1000l;
//		long minute = 60l * second;
//		long hour = 60l * minute;
//		DefaultTableModel model = (DefaultTableModel) tableResultsHistory.getModel();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss z dd-MM-yyyy");
////		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.PATTERN_yyyy_MMM_dd_HH_MM_SS_z);
//		for(int row = 0; row < model.getRowCount(); row++)
//		{
////			if(!model.getValueAt(row, model.findColumn("Date started")).toString().contains("Oct"))
////				continue;
//
//			// parsing input
//			try
//			{
//				Date date1 = dateFormat.parse(model.getValueAt(row, model.findColumn("Date started")).toString());
//				Date date2 = dateFormat.parse(model.getValueAt(row, model.findColumn("Date finished")).toString());
//				// calculation
//				long diff = date2.getTime() - date1.getTime();
//				String timeTaken = String.format("%02d", diff / hour) + ":" + String.format("%02d", (diff % hour) / minute) + ":" + String.format("%02d", (diff % minute) / second);
//
////				model.setValueAt(timeTaken, row, model.findColumn("Time taken"));
//				String rowStr = "";
//				for(int column = 0; column < model.getColumnCount(); column++)
//				{
//					if(column == model.findColumn("Date finished")+1)
//					{
//						rowStr += timeTaken + StringUtil.FOUR_COLONS;
//						rowStr += model.getValueAt(row, column)+StringUtil.FOUR_COLONS;
//						
//					}	
//					else
//						rowStr += model.getValueAt(row, column)+StringUtil.FOUR_COLONS;
//				}
//				System.out.println(rowStr);
//			}	     
//			catch (ParseException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		     
//	
//		}	     
//	}
//	
//	private void test()
//	{
//		List<JobDetails> jobList = new ArrayList<JobDetails>();
//		String dataset = "movielens-ml-100k";
////		String dataset = "filmtrust-rating";
//		String dataParams = "data.column.format=UIRT    data.splitter.ratio.user=0.8    data.convert.binarize.threshold=None";
//		String algoParams = "rec.iterator.maximum=1000    rec.pgm.burn-in=100    rec.pgm.samplelag=100    rec.recommender.isranking=true    rec.recommender.ranking.topn=10";
//		String dateAdded = DateUtil.getDateFormat(DATE_FORMAT).format(new Date());
//		String jobQueue = "0" + StringUtil.FOUR_COLONS + dataset + StringUtil.FOUR_COLONS + dataParams + StringUtil.FOUR_COLONS + getAlgorithmName("diversityaccuracy") + StringUtil.FOUR_COLONS 
//				+ "diversityaccuracy" + StringUtil.FOUR_COLONS + 
//				algoParams + StringUtil.FOUR_COLONS + dateAdded + StringUtil.FOUR_COLONS + "N/A";
//		
//		tableJobQueue.addRow(jobQueue);
//		JobDetails job = new JobDetails("0", dataset, dataParams, 
//				"diversityaccuracy", algoParams, 
//				dateAdded);
//		job.setDateFinished("N/A");
//		jobList.add(job);
//		runJobs(jobList);
//	}
//	// popup menu for clicking to edit dataset and algorithm parameters
//	private void menuInit(final UserFriendlyTable table)
//	{ 
//		table.addMouseListener(new MouseAdapter()
//		{
//			public void mouseClicked(final MouseEvent me)
//			{
//				if(SwingUtilities.isRightMouseButton(me))
//				{
//					JPopupMenu popUpMenu = new JPopupMenu();
//					JMenuItem itemEditParameters = new JMenuItem("Edit parameters");
//					itemEditParameters.addActionListener(new java.awt.event.ActionListener() {
//					    public void actionPerformed(java.awt.event.ActionEvent evt) {
//					    	int row = table.getSelectedRow();
//					    	DefaultTableModel model = (DefaultTableModel) table.getModel();
////					    	if(table.equals(tableSelectDataSets))
////							{
//////								javax.swing.JOptionPane.showMessageDialog(null, model.getValueAt(table.convertRowIndexToModel(row), model.findColumn("Data set")));
////					    		setParameters(table, row);
////							}
////							else if(table.equals(tableSelectAlgorithms))
////							{
//////								javax.swing.JOptionPane.showMessageDialog(null, model.getValueAt(table.convertRowIndexToModel(row), model.findColumn("Algorithm")));
////								setParameters(table, row);
////							}
//					    	setParameters(table, row);
//					    }
//					});
//					popUpMenu.add(itemEditParameters);
//					popUpMenu.show(me.getComponent(), me.getX(), me.getY());
//				}
//				
//			}
//			
//		});
//	}

}