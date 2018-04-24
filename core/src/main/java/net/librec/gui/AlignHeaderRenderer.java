package net.librec.gui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class AlignHeaderRenderer implements TableCellRenderer {

	private final TableCellRenderer renderer;
	private final int alignment;

	public static void install(final JTable table, final int[] alignments) {
		for (int i = 0; i < alignments.length; ++i)
			install(table, i, alignments[i]);
	}

	public static void install(final JTable table, final int row, final int alignment) {
		table.getTableHeader().getColumnModel().getColumn(row)
				.setHeaderRenderer(new AlignHeaderRenderer(table, alignment));
	}

	private AlignHeaderRenderer(final JTable table, final int alignment) {
		renderer = table.getTableHeader().getDefaultRenderer();
		this.alignment = alignment;
	}

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
			final boolean hasFocus, final int row, final int col) {
		final Component c = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		((JLabel) c).setHorizontalAlignment(alignment);
		return c;
	}

}
