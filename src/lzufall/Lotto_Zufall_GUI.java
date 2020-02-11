package lzufall;

import hilfe.Hilfe;
import hilfe.HilfeData;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicBorders;

@SuppressWarnings("serial")
public class Lotto_Zufall_GUI extends JFrame implements Runnable {

	private JTextField anzahl = new JTextField();
	private JTextArea ergebnis = new JTextArea();

	private JButton start6aus49 = new JButton("Start 6 aus 49");
	private JButton start5aus50 = new JButton("Start 5 aus 50");
	private JButton startXausY = new JButton("Start x aus y");
	private JButton reset = new JButton("Reset");
	private JButton btnabbruch = new JButton("Abbruch");

	private JLabel lblY = new JLabel("   y   ");
	private JLabel lblX = new JLabel("   x   ");
	private JLabel lblAnzreihe = new JLabel(" Anzahl der Reihen: ");
	private JLabel lblAus = new JLabel(" aus ");

	private JMenuBar menuBar = new JMenuBar();
	private JMenu mnDatei = new JMenu("Datei");
	private JMenuItem mntmSpeichern = new JMenuItem("Speichern");
	private JMenuItem mntmExit = new JMenuItem("Beenden");
	private JMenu mnHilfe = new JMenu("Hilfe");
	private JMenuItem mntmHilfe = new JMenuItem("Hilfe");
	private JMenu mnExtras = new JMenu("Extras");
	private JMenuItem mntmZeilenrechner = new JMenuItem("Zeilenrechner");

	private JTextField txtX = new JTextField();
	private JTextField txtY = new JTextField();
	private String startsource = "";

	private JScrollPane jScrollPaneErgebnis = new JScrollPane();

	private String pfad = "";
	private JPanel p1 = new JPanel();

	private ArrayList<String> b;

	private JPanel panel = new JPanel();
	private JPanel panel_1 = new JPanel();
	private JPanel panel_2 = new JPanel();
	private JPanel panel_3 = new JPanel();

	private JDialog hilfe = new JDialog(this);
	private JDialog rechner = new JDialog(this);
	private JDialog configuration = new JDialog(this);

	private Properties prop = new Properties();;

	private Thread thread = new Thread(this);
	private String strBordertitel = "Anz. d. R.";

	private final JMenuItem mntmKonfiguration = new JMenuItem("Konfiguration");

	private Properties defaultproperties = new Properties();
	private String langString = "default";
	private JMenuItem mntmAbout = new JMenuItem("About");

	public Lotto_Zufall_GUI() {
		setTitle("Lotto Zufallsgenerator");
		setName("Lotto");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 300);

		setDefaultproperties();
		setLanguageStrings();

		initGUI();
		initButtons();
		initMenu();

		// pack();

		setResizable(true);

		setVisible(true);

	}

	private void setDefaultproperties() {
		// Configuration
		defaultproperties.setProperty("btnAbbruch", "Abbruch");
		defaultproperties.setProperty("btnOkSave", "OK /Speichern");

		// Main Window
		defaultproperties.setProperty("start6aus49", "Start 6 aus 49");
		defaultproperties.setProperty("start5aus50", "Start 5 aus 50");
		defaultproperties.setProperty("startXausY", "Start x aus y");
		defaultproperties.setProperty("reset", "Reset");
		defaultproperties.setProperty("btnabbruch", "Abbruch");
		defaultproperties.setProperty("lblAnzreihe", " Anzahl der Reihen: ");
		defaultproperties.setProperty("mnDatei", "Datei");
		defaultproperties.setProperty("mntmSpeichern", "Speichern");
		defaultproperties.setProperty("mntmExit", "Beenden");
		defaultproperties.setProperty("mnHilfe", "Hilfe");
		defaultproperties.setProperty("mntmHilfe", "Hilfe");
		defaultproperties.setProperty("mnExtras", "Extras");
		defaultproperties.setProperty("mntmZeilenrechner", "Zeilenrechner");
		defaultproperties.setProperty("mntmKonfiguration", "Konfiguration");
		defaultproperties.setProperty("lblAus", " aus ");
		defaultproperties.setProperty("strBordertitel", "Anz. d. R.");
	}

	public void setLanguageStrings() {
		File languagefile;
		File file = new File("language/language.txt");
		FileInputStream ins = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(file);

			br = new BufferedReader(fr);
			langString = br.readLine();
			if (langString.equals("default")) {
				languagefile = new File("language/de/language_de.txt");
			} else {
				languagefile = new File("language/" + langString + "/language_" + langString + ".txt");
			}
			ins = new FileInputStream(languagefile);
			prop.loadFromXML(ins);

		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			prop = defaultproperties;
		} catch (InvalidPropertiesFormatException e) {
			
			e.printStackTrace();
			prop = defaultproperties;
		} catch (IOException e) {
			
			e.printStackTrace();
			prop = defaultproperties;
		} finally {

			try {
				ins.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			ins = null;
			start6aus49.setText(prop.getProperty("start6aus49"));
			start5aus50.setText(prop.getProperty("start5aus50"));
			startXausY.setText(prop.getProperty("startXausY"));
			reset.setText(prop.getProperty("reset"));
			btnabbruch.setText(prop.getProperty("btnabbruch"));
			lblAnzreihe.setText(prop.getProperty("lblAnzreihe"));
			mnDatei.setText(prop.getProperty("mnDatei"));
			mntmSpeichern.setText(prop.getProperty("mntmSpeichern"));
			mntmExit.setText(prop.getProperty("mntmExit"));
			mnHilfe.setText(prop.getProperty("mnHilfe"));
			mntmHilfe.setText(prop.getProperty("mntmHilfe"));
			mnExtras.setText(prop.getProperty("mnExtras"));
			mntmZeilenrechner.setText(prop.getProperty("mntmZeilenrechner"));
			mntmKonfiguration.setText(prop.getProperty("mntmKonfiguration"));
			lblAus.setText(prop.getProperty("lblAus"));
			strBordertitel = prop.getProperty("strBordertitel");
		}

	}

	public void initMenu() {
		setJMenuBar(menuBar);

		menuBar.add(mnDatei);

		mnDatei.add(mntmSpeichern);

		mnDatei.add(mntmExit);

		menuBar.add(mnExtras);

		mnExtras.add(mntmZeilenrechner);

		mnExtras.add(mntmKonfiguration);

		mnExtras.add(mntmAbout);

		menuBar.add(mnHilfe);

		mnHilfe.add(mntmHilfe);
	}

	public void initButtons() {
		startXausY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				startXausY_ActionPerformed(evt);
			}
		});
		start6aus49.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				start6aus49_ActionPerformed(evt);
			}

		});
		start5aus50.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				start5aus50_ActionPerformed(evt);
			}

		});

		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				reset_ActionPerformed(evt);
			}

		});
		mntmSpeichern.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				speichern_ActionPerformed(evt);

			}
		});
		mntmExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				exit_ActionPerformed(evt);

			}
		});
		btnabbruch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				abbruch_ActionPerformed(evt);

			}
		});
		mntmHilfe.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				mntmHilfe_ActionPerformed(evt);

			}
		});
		mntmZeilenrechner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				mntmZeilenrechner_ActionPerformed(evt);
			}
		});
		mntmKonfiguration.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				
				mntmKonfiguration_ActionPerformed(evt);
			}
		});
		mntmAbout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				
				mntmAbout_ActionPerformed(evt);
			}
		});
	}

	protected void mntmAbout_ActionPerformed(ActionEvent evt) {
		JDialog jdabout = new JDialog(this, "About");
		JTextArea txaabout = new JTextArea();

		String strabout = "\n  Author : CLCarlie89 \n  Version: 1.0\n  Versiondate: 11.02.2020\n  Required Javaversion: 1.7      \n";

		txaabout.setEditable(false);
		txaabout.setText(strabout);

		jdabout.setLayout(new BorderLayout(10, 10));
		jdabout.add(new JLabel(" "), BorderLayout.NORTH);
		jdabout.add(new JLabel(" "), BorderLayout.WEST);

		jdabout.add(txaabout, BorderLayout.CENTER);

		jdabout.add(new JLabel(" "), BorderLayout.EAST);
		jdabout.add(new JLabel(" "), BorderLayout.SOUTH);

		jdabout.pack();
		jdabout.setVisible(true);
	}

	protected void mntmKonfiguration_ActionPerformed(ActionEvent evt) {
		configuration = new Configuration(this,"Configuration", true, prop);

		Point point = this.getLocation();

		if (this.getSize().width < Toolkit.getDefaultToolkit().getScreenSize().width) {
			point.x = point.x + this.getWidth();
		}

		configuration.setLocation(point);
		configuration.setMinimumSize(new Dimension(300, 250));
		configuration.pack();
		configuration.setVisible(true);
	}

	protected void mntmZeilenrechner_ActionPerformed(ActionEvent evt) {
		rechner.dispose();
		rechner = new JDialog(this);
		rechner.getContentPane().add(new Zeilenrechner(prop));

		Point point = this.getLocation();

		if (this.getSize().width < Toolkit.getDefaultToolkit().getScreenSize().width) {
			point.x = point.x + this.getWidth();
		}
		rechner.setTitle("Zeilenrechner");
		rechner.setLocation(point);
		rechner.setMinimumSize(new Dimension(300, 250));
		rechner.pack();
		rechner.setVisible(true);
	}

	protected void mntmHilfe_ActionPerformed(ActionEvent evt) {
		hilfe.dispose();
		hilfe = new JDialog(this);
		Hilfe help = new Hilfe();

		// help.addListData(new HilfeData("Test1", new File("Hilfe.txt")));
		// help.addListData(new HilfeData("Test2", new File("Hilfe.txt"),
		// "Test2"));
		File file;

		if (langString.equals("default")) {
			file = new File("language/de/help_hilfe");
		} else {
			file = new File("language/" + langString + "/help_hilfe");
		}

		File files[] = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			help.addListData(new HilfeData(files[i]));
		}

		hilfe.getContentPane().add(help);
		Point point = this.getLocation();

		if (this.getSize().width < Toolkit.getDefaultToolkit().getScreenSize().width) {
			point.x = point.x + this.getWidth();
		}

		hilfe.setTitle("Hilfe");
		hilfe.setLocation(point);
		hilfe.setMinimumSize(new Dimension(100, 100));
		hilfe.setPreferredSize(new Dimension(300, 300));
		hilfe.pack();
		hilfe.setVisible(true);
	}

	protected void abbruch_ActionPerformed(ActionEvent evt) {
		thread.interrupt();

	}

	protected void exit_ActionPerformed(ActionEvent evt) {
		System.exit(0);

	}

	protected void speichern_ActionPerformed(ActionEvent evt) {
		JFileChooser jfc = new JFileChooser(pfad);
		jfc.setDialogType(JFileChooser.SAVE_DIALOG);
		if (jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			pfad = jfc.getSelectedFile().getAbsolutePath();
		}

		File file = new File(pfad);
		PrintWriter pw = null;
		BufferedWriter bw = null;
		try {
			pw = new PrintWriter(file);
			bw = new BufferedWriter(pw);
			bw.write(ergebnis.getText());

		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			bw = null;
			pw.close();
			pw = null;
		}

	}

	public void initGUI() {
		getContentPane().setLayout(new BorderLayout(10, 10));

		p1.setLayout(new GridLayout(2, 3, 10, 10));

		p1.add(lblAnzreihe);
		anzahl.setBorder(new TitledBorder(strBordertitel));
		p1.add(anzahl);
		panel.setLayout(new GridLayout(1, 3, 10, 10));

		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		panel_2.add(lblX, BorderLayout.WEST);
		panel_2.add(txtX);

		panel.add(lblAus);

		panel.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		panel_3.add(lblY, BorderLayout.WEST);
		panel_3.add(txtY);
		panel.setBorder(new TitledBorder("x aus y"));
		p1.add(panel);
		p1.add(start6aus49);
		p1.add(start5aus50);
		p1.add(startXausY);

		p1.setBorder(BasicBorders.getTextFieldBorder());

		jScrollPaneErgebnis.setViewportView(ergebnis);

		panel_1.setLayout(new GridLayout(1, 2, 0, 0));
		panel_1.add(reset);
		panel_1.add(btnabbruch);

		getContentPane().add(p1, BorderLayout.NORTH);
		getContentPane().add(jScrollPaneErgebnis, BorderLayout.CENTER);

		getContentPane().add(panel_1, BorderLayout.SOUTH);
	}

	protected void startXausY_ActionPerformed(ActionEvent evt) {
		thread = new Thread(this);
		startsource = "X aus Y";
		thread.start();
		System.gc();

	}

	public void reset_ActionPerformed(ActionEvent evt) {
		anzahl.setText("");
		ergebnis.setText("");
		txtX.setText("");
		txtY.setText("");
		System.gc();

	}

	public void start6aus49_ActionPerformed(ActionEvent evt) {
		thread = new Thread(this);
		startsource = "6 aus 49";
		thread.start();
		System.gc();

	}

	public void start5aus50_ActionPerformed(ActionEvent evt) {
		thread = new Thread(this);
		startsource = "5 aus 50";
		thread.start();
		System.gc();
	}

	@Override
	public void run() {
		System.gc();
		LZufall lz = null;
		StringBuilder sb = null;
		try {
			int x = 0;
			int y = 0;
			start6aus49.setEnabled(false);
			start5aus50.setEnabled(false);
			startXausY.setEnabled(false);

			int anzahltext = Integer.parseInt(anzahl.getText());

			lz = new LZufall(anzahltext, this);
			// b = new ArrayList<String>();

			switch (startsource) {
			case "6 aus 49":
				b = lz.getZahlenXausY(6, 49);

				break;
			case "5 aus 50":
				b = lz.getZahlenXausY(5, 50);

				break;
			case "X aus Y":
				x = Integer.parseInt(txtX.getText());
				y = Integer.parseInt(txtY.getText());

				b = lz.getZahlenXausY(x, y);

				break;
			default:
				break;
			}
			if (!thread.interrupted()) {
				// Arrays.sort(b);

				if (startsource != "X aus Y") {
					ergebnis.setText(ergebnis.getText() + "\nIhre Zahlen " + startsource + " sind :\n");
				} else {
					ergebnis.setText(ergebnis.getText() + "\nIhre Zahlen " + startsource + "( " + x + " aus " + y + " ) sind :\n");
				}

				sb = new StringBuilder(b.size() + 1);
				for (int i = 0; i < anzahltext; i++) {
					sb.append("Reihe " + (i + 1) + " : " + b.get(i) + "\n");
				}
				ergebnis.setText(ergebnis.getText() + sb.toString() + "\n");
			}

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Zahlenlesefehler / NumberReadError", "Zahlenlesefehler", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			JOptionPane.showMessageDialog(null, "Out of Memory", "Out of Memory", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "Arrayz�hler ist zu gro� (IndexOutOfBoundsException)", "Array zu gro�", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();

		} catch (MeineException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Zahlenfehler", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Ein Fehler ist aufgetretten./An error has occurred. \n" + e.getClass().toString(), "Fehler", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();

		} finally {
			if (lz != null) {
				lz.destroy();
			}
			b = null;
			lz = null;
			startsource = "";
			sb = null;

			start6aus49.setEnabled(true);
			start5aus50.setEnabled(true);
			startXausY.setEnabled(true);
			System.gc();

		}

		start6aus49.setEnabled(true);
		start5aus50.setEnabled(true);
		startXausY.setEnabled(true);

		thread = null;
		System.gc();
	}

	/**
	 * @return the thread
	 */
	public Thread getThread() {
		return thread;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Lotto_Zufall_GUI();

	}
}
