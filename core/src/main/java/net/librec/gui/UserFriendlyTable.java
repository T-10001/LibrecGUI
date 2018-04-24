/**
 * 
 */
package net.librec.gui;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.stream.Stream;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.commons.io.IOUtils;

import net.librec.job.JobOutput;
import net.librec.util.FileUtil;
import net.librec.util.StringUtil;



/**
 * @author PinkySwear
 *
 */
@SuppressWarnings("serial")
public class UserFriendlyTable extends JTable implements TableModelListener {
	
	public final static boolean INCLUDE = true;
	public final static boolean EXCLUDE = false;
	
//	
	
	
	/**
	 * 
	 */
	public UserFriendlyTable() 
	{
	}

	/**
	 * @param dm
	 */
	public UserFriendlyTable(TableModel dm) {
		super(dm);
	}

	/**
	 * @param dm
	 * @param cm
	 */
	public UserFriendlyTable(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
	}

	/**
	 * @param numRows
	 * @param numColumns
	 */
	public UserFriendlyTable(int numRows, int numColumns) {
		super(numRows, numColumns);
	}

	/**
	 * @param rowData
	 * @param columnNames
	 */
	public UserFriendlyTable(Vector rowData, Vector columnNames) {
		super(rowData, columnNames);
	}

	/**
	 * @param rowData
	 * @param columnNames
	 */
	public UserFriendlyTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
	}

	/**
	 * @param dm
	 * @param cm
	 * @param sm
	 */
	public UserFriendlyTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm, cm, sm);
	}
	
	public List<JobOutput> getSelectedJobOutputs()
	{
		List<JobOutput> jobOutputs = new ArrayList<>();
		DefaultTableModel model = (DefaultTableModel) getModel();
		for(int i = 0; i < model.getRowCount(); i++)
		{
			if(Boolean.valueOf(model.getValueAt(i, model.getColumnCount()-1).toString()))
			{
				JobOutput jobOutput = 
						new JobOutput(model.getValueAt(i, model.findColumn("ID")).toString(), 
						model.getValueAt(i, model.findColumn("Data set")).toString(), 
						model.getValueAt(i, model.findColumn("Algorithm")).toString());
				jobOutput.setTimeTaken(model.getValueAt(i, model.findColumn("Time taken")).toString());
				LinkedHashMap<String, Double> map = new LinkedHashMap<>();
				for(int col = model.findColumn("Date finished") + 2; col < model.getColumnCount()-1; col++)
				{
					map.put(model.getColumnName(col).toString(), Double.parseDouble(model.getValueAt(i, col).toString()));
				}
				jobOutput.setEvaluationsMap(map);
				jobOutputs.add(jobOutput);
			}
		}
		return jobOutputs;
	}
	
    // add a row to end of specified table
	public void addRow(String rowString)
    {
        String[] row = rowString.split(StringUtil.FOUR_COLONS);
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(row);
        adjustColumnWidth();
        model.setValueAt(Boolean.FALSE, model.getRowCount()-1, model.findColumn(""));
    }
    
	public void addRowWithoutSelect(String rowString)
	{
		String[] row = rowString.split(StringUtil.FOUR_COLONS);
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(row);
        adjustColumnWidth();
	}
	
	public void deleteSelectedRows()
    {
    	int initialRowCount = getRowCount();
		List<Integer> indexList = new ArrayList<Integer>();
		DefaultTableModel model = (DefaultTableModel) getModel();
		for(int i = initialRowCount - 1; i > -1; i--)
		{
			if(Boolean.valueOf(model.getValueAt(i, model.getColumnCount()-1).toString()))
			{
				indexList.add(i);
			}
		}
		for(Integer index : indexList)
		{
			model.removeRow(index);
		}
    }
    
	public void deleteRow(Integer rowIndex)
    {
    	DefaultTableModel model = (DefaultTableModel) getModel();
    	model.removeRow(rowIndex);
    }
    
	public void fillTableFromFile(String file)
	{
		try {
    		//String logFilePath = System.getProperty("user.dir") + File.separator + "log" + File.separator + logName + ".log";
    		String allText = FileUtil.readAsString(file);
            List<String> lines = IOUtils.readLines(new StringReader(allText));
            for(String line : lines)
            {
                 addRow(line);
            }
            // make backup of file if the main file is not empty
            if(allText.length() > 0)
            {
            	FileUtil.copyFile(file, file + ".bak");
            }
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
	}
	
	public void writeToFile(String file)
    {
    	//String pathname = System.getProperty("user.dir") + File.separator + "log" + File.separator;
    	try(PrintWriter pw = new PrintWriter(new FileWriter(file, false), true))
		{
    		DefaultTableModel model = (DefaultTableModel) getModel();
    		String logOutput = "";
    		for(int row = 0; row < model.getRowCount(); row++)
    		{
    			for(int col = 0; col < model.getColumnCount() - 1; col++ )
    			{
    				if(model.getValueAt(row, col) != null)
    					logOutput += model.getValueAt(row, col).toString() + StringUtil.FOUR_COLONS;
    				else
    					logOutput += "empty" + StringUtil.FOUR_COLONS;
    			}
    			logOutput += "\n";
//    			logOutput.replaceAll("<html>", StringUtil.FOUR_SPACES);
    		}
    		//System.out.println(logOutput);
    		pw.print(logOutput);
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    }
    
    // adapted from https://stackoverflow.com/a/42888068
	public void adjustColumnWidth()
	{
		int spacing = 10;
		int heightSpace = 17;
		//        DefaultTableModel model = (DefaultTableModel) table.getModel();
		setAutoResizeMode(AUTO_RESIZE_OFF );
		for (int column = 0; column < getColumnCount(); column++)
		{
			DefaultTableModel model = (DefaultTableModel) getModel();
			if(column != model.findColumn(""))
			{
				TableColumn tableColumn = getColumnModel().getColumn(column);
				int preferredWidth = tableColumn.getMinWidth();
				int maxWidth = 0;

				TableCellRenderer rend = getTableHeader().getDefaultRenderer();
				TableCellRenderer rendCol = tableColumn.getHeaderRenderer();
				if (rendCol == null)
					rendCol = rend;
				Component header = rendCol.getTableCellRendererComponent(this, tableColumn.getHeaderValue(), false,
						false, 0, column);
				maxWidth = header.getPreferredSize().width;
				preferredWidth = maxWidth;
				for (int row = 0; row < getRowCount(); row++)
				{
					int preferredHeight = getRowHeight(row);
					TableCellRenderer cellRenderer = getCellRenderer(row, column);
					Component c = prepareRenderer(cellRenderer, row, column);
					int width = c.getPreferredSize().width + getIntercellSpacing().width;
					//					Component c = 
					preferredWidth = Math.max(preferredWidth, width);
					if (column == findColumn("Data set parameters") || column == findColumn("Algorithm parameters") || column == findColumn("Parameters") || column == findColumn("Description"))
					{
						if(model.getValueAt(row, column) != null)
						{
							String params = model.getValueAt(row, column).toString();
							String[] array = null;
//							if(params.contains(","))
//							{
//								array = params.split(",");
//							}
							if(params.contains(StringUtil.FOUR_SPACES))
							{
								array = params.split(StringUtil.FOUR_SPACES);
							}
							if(params.contains("<br>"))
							{
								preferredHeight = Math.max(params.split("<br>").length*heightSpace, preferredHeight);
								setRowHeight(row, preferredHeight);
							}
							if(array != null)
							{
								String formatted = "<html>";
								String temp = "";
								for(String param : array)
								{
//									if(param.contains("="))
//									{
										formatted += param + "<br>";
//									}
//									else
//									{
//										temp += param + ",";
//										formatted += temp;
//									}
								}
								formatted += "<html>";
								model.setValueAt(formatted, row, column);
//								Component comp = prepareRenderer(getCellRenderer(row, column), row, column);
								//								setRowHeight(row, 18*array.length);
								preferredHeight = Math.max(heightSpace*array.length, preferredHeight);
								setRowHeight(row, preferredHeight);
//								System.out.println("column num = " + getColumnCount());
							}						
						}
					}	

				}
				tableColumn.setPreferredWidth(preferredWidth + spacing);
			}
		}

	}
    
    public void adjustRowHeight()
    {
//    	rowheight
//    	try {
//    	    for (int row=0; row<getRowCount(); row++) {
//    	        int rowHeight = getRowHeight();
//
//    	        for (int column=0; column<getColumnCount(); column++) {
//    	            Component comp = prepareRenderer(getCellRenderer(row, column), row, column);
//    	            rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
//    	        }
//
//    	        setRowHeight(row, rowHeight);
//    	    }
//    	} catch(ClassCastException e) { }
//    	for(int col = 0; col < getColumnCount(); col++)
//    	{
//    		
//    	}
    }
    
//    public void setWordWrap(int columnIndex)
//    {
//    	getColumnModel().getColumn(columnIndex+1).setCellRenderer(new WordWrapCellRenderer());
//    }
    
    // adapted from http://www.java2s.com/Tutorial/Java/0240__Swing/FiltertablebythetextinaTextField.htm
    public void includeData(String filterText)
    {      
    	TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(getModel());
    	setRowSorter(sorter);
        if (filterText.length() == 0) 
        {
          sorter.setRowFilter(null);
        } 
        else 
        {
          sorter.setRowFilter(RowFilter.regexFilter(filterText));
        }
    }
    
    public void excludeData(String filterText)
    {
    	TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(getModel());
    	setRowSorter(sorter);
        if (filterText.length() == 0) 
        {
          sorter.setRowFilter(null);
        } 
        else 
        {
          sorter.setRowFilter(RowFilter.notFilter(RowFilter.regexFilter(filterText)));
        }
    }
    
    public void filterData(String filterText, boolean include)
    {
    	TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(getModel());
    	setRowSorter(sorter);
        if (filterText.length() == 0 || (include && !include)) 
        {
          sorter.setRowFilter(null);
        } 
        else 
        {
        	if(include)
        		sorter.setRowFilter(RowFilter.regexFilter(filterText));
        	else if(!include)
        		sorter.setRowFilter(RowFilter.notFilter(RowFilter.regexFilter(filterText)));
        }
    }
    
    public void setSelectionHeader()
    {
    	TableCellRenderer r = new HeaderRenderer(getTableHeader(), getColumnCount()-1); 
        getColumnModel().getColumn(getColumnCount()-1).setHeaderRenderer(r);
    }
    
    public void setSelectionColumnView()
    {
    	moveColumn(getColumnCount()-1, 0);
    }
    
    public void selectAll()
    {
    	for(int i = 0; i < getRowCount(); i ++)
    	{
    		DefaultTableModel model = (DefaultTableModel) getModel();
    		model.setValueAt(Boolean.TRUE, convertRowIndexToModel(i), model.getColumnCount()-1);
    	}
    }
    
    public void selectNone()
    {
    	for(int i = 0; i < getRowCount(); i ++)
    	{
    		DefaultTableModel model = (DefaultTableModel) getModel();
    		model.setValueAt(Boolean.FALSE, convertRowIndexToModel(i), model.getColumnCount()-1);
    	}
    }
    
    public int getRowIndexFromValue(String value)
    {
    	DefaultTableModel model = (DefaultTableModel) getModel();
    	for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount()-1; j++) {
                if (model.getValueAt(i, j).toString().equals(value)) {
                    // what if value is not unique?
                    return i;
                }
            }
        }
    	return -1;
    }
    
    public int getRowIndexFromValue(String value, int column)
    {
    	DefaultTableModel model = (DefaultTableModel) getModel();
    	for(int i = 0; i < model.getRowCount(); i++)
    	{
    		if(model.getValueAt(i, column).toString().equals(value))
    			return i;
    	}
		return -1;
    	
    }
    
    public int findColumn(String columnName)
    {
    	return ((DefaultTableModel) getModel()).findColumn(columnName);
    	
    }
	
}
    
