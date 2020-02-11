/**
 * 
 */
package lzufall;

import java.awt.Component;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/*
 * Thema: LeVeTo
 * Name, Vorname: Liebsch,Carsten
 * Matrikelnummer: 292572
 * Fach: Programmierpraktikum, WS 12/13
 * Matrikel: IF09
 * Datum: 04.01.2013 <Datum letzter �nderung in dieser Datei>
 */
/**
 * @author Carsten Liebsch (cliebsch) IF09 Carsten
 * 
 */
public class MeineException extends Exception {

	private static final long serialVersionUID = 1L;

	private Logger logger;
	private Handler handler;

	/**
	 

	 */
	public MeineException() {
		
	}

	/**
	 * @param message
	 */
	public MeineException(String message) {
		super(message);
		
	}

	/**
	 * @param cause
	 */
	public MeineException(Throwable cause) {
		super(cause);
	
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MeineException(String message, Throwable cause) {
		super(message, cause);
		
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public MeineException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	/**Einige Code gestalltungs Anregungen aus dem JAVA-Tutorial
	 * @param exception
	 *            Die auftretende Exception
	 * @param classname
	 *            Name der Aufrufenden Klasse
	 * @param parent
	 *            rufende/s Componete/Fenster
	 * @param level
	 *            logging-Level z.B. SERVE
	 * 
	 */
	public void printException(Exception exception, String classname,
			Component parent, Level level) {
		Date date = new Date(System.currentTimeMillis());
		DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT,
				Locale.getDefault());
		String time = df.format(date);
		time = time.replace(':', '_');

		df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
		String dati = df.format(date);

		dati = dati + "_" + time;

		try {

			handler = new FileHandler("ERROR\\" + classname + "_" + dati
					+ ".error");
			Logger.getLogger("").addHandler(handler);

			logger = Logger.getLogger(classname + df.format(date));
			String exceptiontyp = exception.getClass().getSimpleName();
			String msg;
			msg = exceptiontyp + "\nProgrammmessage:" + this.getMessage()
					+ "\n\nFehlermessage:\n";
			int wahl = JOptionPane
					.showOptionDialog(
							parent,
							"Nachricht:\n"
									+ this.getMessage()
									+ "\n\nEs ist eine "
									+ exceptiontyp
									+ " aufgetreten.\nWollen Sie einen ausf�hrlichen Fehlerbericht erstellen ?",
							exceptiontyp, JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, null, null);

			if (wahl == JOptionPane.YES_OPTION) {

				StackTraceElement[] elements;
				msg = msg + "\n\nStacktrace:\n";
				elements = exception.getStackTrace();

				for (int i = 0, n = elements.length; i < n; i++) {
					msg = msg + elements[i].toString() + "\n";
				}

				msg = msg + "ist ein " + exceptiontyp + " Fehler aufgetreten.";

			}
			/* XMLFormatter xml = new XMLFormatter(); */

			logger.log(level, msg);

			// TODO log in File

		} catch (Exception e) {
			System.out.println("Meine Exception throws " + e.getClass());
			e.printStackTrace();
		}
	}
}
