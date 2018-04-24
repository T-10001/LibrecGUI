package net.librec.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class NimbusHeaderCheckBox {
  public JComponent makeUI() {
    Object[] columnNames = {Status.INDETERMINATE, "Integer", "String"};
    Object[][] data = {{true, 1, "b"}, {false, 9, "a"}, {true, 5, "c"}};
    DefaultTableModel model = new DefaultTableModel(data, columnNames) {
      @Override public Class<?> getColumnClass(int column) {
        return getValueAt(0, column).getClass();
      }
    };
    JTable table = new JTable(model);
    model.addTableModelListener(new HeaderCheckBoxHandler(table));

    TableCellRenderer r = new HeaderRenderer(table.getTableHeader(), 0);
    table.getColumnModel().getColumn(0).setHeaderRenderer(r);
    //<ins>
    TableCellRenderer leftAlign = new LeftAlignHeaderRenderer();
    table.getColumnModel().getColumn(1).setHeaderRenderer(leftAlign);
    table.getColumnModel().getColumn(2).setHeaderRenderer(leftAlign);
    //</ins>
    table.getTableHeader().setReorderingAllowed(false);
    return new JScrollPane(table);
  }
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override public void run() { createAndShowGUI(); }
    });
  }
  public static void createAndShowGUI() {
    try {
      //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels())
        if ("Nimbus".equals(laf.getName())) UIManager.setLookAndFeel(laf.getClassName());
    } catch(Exception e) {
      e.printStackTrace();
    }
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.getContentPane().add(new NimbusHeaderCheckBox().makeUI());
    frame.setSize(320, 240);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
