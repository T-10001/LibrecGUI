package net.librec.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public class HeaderRenderer extends JCheckBox implements TableCellRenderer {
	  public HeaderRenderer(JTableHeader header, final int targetColumnIndex) {
	    super((String)null);
	    setOpaque(false);
	    setFont(header.getFont());
	    header.addMouseListener(new MouseAdapter() {
	      @Override public void mouseClicked(MouseEvent e) {
	        JTableHeader header = (JTableHeader)e.getSource();
	        JTable table = header.getTable();
	        TableColumnModel columnModel = table.getColumnModel();
	        int vci = columnModel.getColumnIndexAtX(e.getX());
	        int mci = table.convertColumnIndexToModel(vci);
	        if(mci == targetColumnIndex) {
	          TableColumn column = columnModel.getColumn(vci);
	          Object v = column.getHeaderValue();
	          boolean b = Status.DESELECTED.equals(v)?true:false;
	          TableModel m = table.getModel();
	          for(int i=0; i<m.getRowCount(); i++) m.setValueAt(b, i, mci);
	          column.setHeaderValue(b?Status.SELECTED:Status.DESELECTED);
	        }
	      }
	    });
	  }
	  @Override public Component getTableCellRendererComponent(
	      JTable tbl, Object val, boolean isS, boolean hasF, int row, int col) {
	    if(val instanceof Status) {
	      switch((Status)val) {
	      case SELECTED:
	        setSelected(true); setEnabled(true); break;
	      case DESELECTED:
	        setSelected(false); setEnabled(true); break;
	      case INDETERMINATE:
	        setSelected(true); setEnabled(false); break;
	      }
	    } else {
	      setSelected(true); setEnabled(false);
	    }
	    TableCellRenderer r = tbl.getTableHeader().getDefaultRenderer();
	    JLabel l=(JLabel)r.getTableCellRendererComponent(tbl, null, isS, hasF, row, col);

	    l.setIcon(new CheckBoxIcon(this));
	    l.setText(null); //XXX Nimbus LnF ???
	    ////This block has no effect at all as all the columns are center aligned
	    ////(pointed out by rcnpl)
	    //if(tbl.convertColumnIndexToModel(col)==0) {
	    //  l.setHorizontalAlignment(SwingConstants.CENTER);
	    //} else {
	    //  l.setHorizontalAlignment(SwingConstants.LEFT);
	    //}
	    //<ins>
	    l.setHorizontalAlignment(SwingConstants.CENTER);
	    //</ins>
	    return l;
	  }
	}
	//<ins>
	class LeftAlignHeaderRenderer implements TableCellRenderer {
	  @Override public Component getTableCellRendererComponent(
	    JTable t, Object v, boolean isS, boolean hasF, int row, int col) {
	    TableCellRenderer r = t.getTableHeader().getDefaultRenderer();
	    JLabel l=(JLabel)r.getTableCellRendererComponent(t, v, isS, hasF, row, col);
	    l.setHorizontalAlignment(SwingConstants.LEFT);
	    return l;
	  }
	}
	//</ins>
	enum Status { SELECTED, DESELECTED, INDETERMINATE }
	class CheckBoxIcon implements Icon {
	  private final JCheckBox check;
	  public CheckBoxIcon(JCheckBox check) {
	    this.check = check;
	  }
	  @Override public int getIconWidth() {
	    return check.getPreferredSize().width;
	  }
	  @Override public int getIconHeight() {
	    return check.getPreferredSize().height;
	  }
	  @Override public void paintIcon(Component c, Graphics g, int x, int y) {
	    SwingUtilities.paintComponent(
	        g, check, (Container)c, x, y, getIconWidth(), getIconHeight());
	  }
	}
	class HeaderCheckBoxHandler implements TableModelListener {
	  private final JTable table;
	  public HeaderCheckBoxHandler(JTable table) {
	    this.table = table;
	  }
	  @Override public void tableChanged(TableModelEvent e) {
	    if(e.getType()==TableModelEvent.UPDATE && e.getColumn()==0) {
	      int mci = 0;
	      int vci = table.convertColumnIndexToView(mci);
	      TableColumn column = table.getColumnModel().getColumn(vci);
	      Object title = column.getHeaderValue();
	      if(!Status.INDETERMINATE.equals(title)) {
	        column.setHeaderValue(Status.INDETERMINATE);
	      } else {
	        int selected = 0, deselected = 0;
	        TableModel m = table.getModel();
	        for(int i=0; i<m.getRowCount(); i++) {
	          if(Boolean.TRUE.equals(m.getValueAt(i, mci))) {
	            selected++;
	          } else {
	            deselected++;
	          }
	        }
	        if(selected==0) {
	          column.setHeaderValue(Status.DESELECTED);
	        } else if(deselected==0) {
	          column.setHeaderValue(Status.SELECTED);
	        } else {
	          return;
	        }
	      }
	      table.getTableHeader().repaint();
	    }
	  }
	}