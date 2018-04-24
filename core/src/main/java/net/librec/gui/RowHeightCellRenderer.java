package net.librec.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.BadLocationException;

public class RowHeightCellRenderer extends JTextArea implements TableCellRenderer {

    private static final long serialVersionUID = 1L;


    public RowHeightCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
    }//constructor

    public Component getTableCellRendererComponent (JTable table,
                                                    Object value,
                                                    boolean isSelected,
                                                    boolean hasFocus,
                                                    int row,
                                                    int column ) {
        this.setText((String) value);

        if(isSelected) {
            this.setBackground(table.getSelectionBackground());
            this.setForeground(table.getSelectionForeground());
        } else {
            this.setBackground(table.getBackground());
            this.setForeground(table.getForeground());
        }

        final int columnWidth = table.getColumnModel().getColumn(column).getWidth();
        final int rowHeight = table.getRowHeight(row);
        this.setSize(columnWidth, rowHeight);

        this.validate();
        return this;
    }//getTableCellRendererComponent

    @Override
    public Dimension getPreferredSize() {
        try {
            // Get Rectangle for position after last text-character
            final Rectangle rectangle = this.modelToView(getDocument().getLength());
            if(rectangle != null) {
                return new Dimension(this.getWidth(),
                                     this.getInsets().top + rectangle.y + rectangle.height +
                                                                  this.getInsets().bottom);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();  // TODO: implement catch
        }

        return super.getPreferredSize();
    }
}//RowHeightCellRenderer
