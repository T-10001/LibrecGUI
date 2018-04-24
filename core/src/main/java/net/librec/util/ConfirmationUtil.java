package net.librec.util;

import javax.swing.JOptionPane;

public class ConfirmationUtil {

	public static Boolean getConfirmation(String message, String title)
	{
		return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
	}
}
