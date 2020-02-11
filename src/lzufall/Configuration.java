package lzufall;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import mydefaultmodels.MyComboBoxModel;

public class Configuration extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MyComboBoxModel<String> combomodel = new MyComboBoxModel<String>();
	private final JComboBox<String> comboBox = new JComboBox<String>(combomodel);
	private final JPanel panel = new JPanel();
	private final JButton btnOkSave = new JButton("OK /Speichern");
	private final JButton btnAbbruch = new JButton("Abbruch");
	private Properties defaultproperties = new Properties();
	private final JScrollPane scroll = new JScrollPane();
	private JTextArea fehler = new JTextArea();
	Lotto_Zufall_GUI lotto_Zufall_GUI;

	public Configuration(Lotto_Zufall_GUI lotto_Zufall_GUI) {
		this(lotto_Zufall_GUI,"", true, null);
	}

	/**
	 * Create the frame.
	 * 
	 * @param prop
	 */
	public Configuration(Lotto_Zufall_GUI lotto_Zufall_GUI,Properties prop) {
		this(lotto_Zufall_GUI,"", true, prop);

	}

	/**
	 * 
	 * @param modal
	 */
	public Configuration(Lotto_Zufall_GUI lotto_Zufall_GUI,boolean modal, Properties prop) {

		this(lotto_Zufall_GUI,"", modal, prop);
	
	}

	/**
	 * 
	 * @param title
	 */
	public Configuration(Lotto_Zufall_GUI lotto_Zufall_GUI,String title, Properties prop) {
		this(lotto_Zufall_GUI,title, true, prop);

	}

	/**
	 * 
	 * @param lotto_Zufall_GUI 
	 * @param title
	 * @param modal
	 */
	public Configuration(Lotto_Zufall_GUI lotto_Zufall_GUI, String title, boolean modal, Properties prop) {

		setTitle(title);
		setModal(modal);

		setDefaultproperties();

		setList();

		setLanguage(prop);
		initGUI();
		this.lotto_Zufall_GUI=lotto_Zufall_GUI;
	}

	private void setList() {
		File file = new File("language/language.txt");
		FileReader pw = null;
		BufferedReader bw = null;
		MyArrayList<String> mylist = new MyArrayList<String>();

		try {
			pw = new FileReader(file);
			bw = new BufferedReader(pw);
			String line;
			while ((line = bw.readLine()) != null) {
				mylist.add(line);
			}
			String selection = mylist.get(0);
			mylist.remove(0);
			combomodel.addElements(mylist);

			int index = combomodel.getIndexOf(selection);
			if (index >= 0) {
				comboBox.setSelectedIndex(index);
			} else {
				comboBox.setSelectedIndex(0);
			}

		} catch (FileNotFoundException ex) {
			combomodel.addElement("default");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				pw.close();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			bw = null;
			pw = null;
			file = null;
		}

	}

	public ArrayList<String> lesen() {

		String zeile = "";

		ArrayList<String> pfade = new ArrayList<String>();

		try (FileReader f = new FileReader("configuration.txt"); BufferedReader r = new BufferedReader(f);) {

			// pfade.add(zeile);

			while ((zeile = r.readLine()) != null) {

				pfade.add(zeile);
			}
			r.close();
			f.close();

		} catch (Exception e) {
			e.printStackTrace();
			fehler.setText(fehler.getText() + "\nFehlertyp: " + e.getClass().toString() + "\nMessage: " + e.getMessage());
		}

		return pfade;
	}

	private void setDefaultproperties() {
		defaultproperties.setProperty("btnAbbruch", "Abbruch");
		defaultproperties.setProperty("btnOkSave", "OK /Speichern");
	}

	private void setLanguage(Properties prop) {
		if (prop == null) {
			prop = defaultproperties;
		}
		btnAbbruch.setText(prop.getProperty("btnAbbruch"));
		btnOkSave.setText(prop.getProperty("btnOkSave"));
	}

	private void initGUI() {

		setLayout(new BorderLayout(10, 10));

		scroll.setViewportView(fehler);
		scroll.setColumnHeaderView(new JLabel("Fehler:"));

		panel.setLayout(new GridLayout(1, 2, 10, 10));

		btnOkSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOkSave_actionPerformed(e);
			}
		});

		panel.add(btnOkSave);
		btnAbbruch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAbbruch_actionPerformed(e);
			}
		});

		panel.add(btnAbbruch);

		add(comboBox, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);

	}

	protected void btnAbbruch_actionPerformed(ActionEvent e) {
		setVisible(false);
		dispose();

	}

	protected void btnOkSave_actionPerformed(ActionEvent e) {

		PrintWriter pw = null;
		BufferedWriter bw = null;
		File file = new File("language/language.txt");
		ArrayList<String> text = combomodel.getElements();
		String inhalt = "";
		// String pfade = comboBox.getTextTextfield();

		try {
			pw = new PrintWriter(file);
			bw = new BufferedWriter(pw);

			for (int i = 0; i < text.size(); i++) {
				inhalt = inhalt + text.get(i) + "\n";
			}
			bw.write(comboBox.getSelectedItem() + "\n" + inhalt);

			
			
		} catch (IOException ex) {
			ex.printStackTrace();

			fehler.setText(fehler.getText() + "\nFehlertyp: " + ex.getClass().toString() + "\nMessage: " + ex.getMessage());
		}
		// Wird normalerweise nur im TestFall ohne KauGui ausgeworfen
		catch (NullPointerException ex) {
			ex.printStackTrace();
			fehler.setText(fehler.getText() + "\nFehlertyp: " + ex.getClass().toString() + "\nMessage: " + ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			fehler.setText(fehler.getText() + "\nFehlertyp: " + ex.getClass().toString() + "\nMessage: " + ex.getMessage());
		} finally {
			try {
				bw.close();
				pw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bw = null;
			pw = null;
			file = null;
			
			setVisible(false);
			lotto_Zufall_GUI.setLanguageStrings();
			dispose();
		}

	}

	public static void main(String[] args) {

		Configuration configuration = new Configuration(null);
		configuration.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		configuration.pack();
		configuration.setVisible(true);
	}
}
