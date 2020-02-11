package lzufall;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class Zeilenrechner extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JPanel panel = new JPanel();
	private final JTextField txtY = new JTextField();
	
	private final JTextField txtX = new JTextField();
	private final JPanel panel_1 = new JPanel();
	private final JTextField txterg = new JTextField();
	private final JButton btnStart = new JButton("Start");
	private final JLabel lblber = new JLabel(" �ber ", SwingConstants.CENTER);
	private final JLabel lblNewLabel = new JLabel("Ergebnis :  ");
	private final JLabel lblY = new JLabel("   y  ");
	private final JLabel lblX = new JLabel("   x   ");
	private final JPanel panel_2 = new JPanel();
	private final JPanel panel_3 = new JPanel();
	private final JPanel panel_4 = new JPanel();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTextArea txtrZurBedienungX = new JTextArea();
	private final JPanel panel_5 = new JPanel();
	private Properties defaultproperties = new Properties();

	/**
	 * Create the panel.
	 * 
	 * @param prop
	 */
	public Zeilenrechner(Properties prop) {
		txterg.setColumns(10);
		setDefaultproperties();
		setLanguage(prop);
		initGUI();
	}

	private void setLanguage(Properties prop) {
		if (prop == null) {
			prop = defaultproperties;
		}
		lblNewLabel.setText(prop.getProperty("lblNewLabel"));
		btnStart.setText(prop.getProperty("btnStart"));
		lblY.setText(prop.getProperty("lblY"));
		lblX.setText(prop.getProperty("lblX"));
				
	}
	
	private void setDefaultproperties() {
		defaultproperties.setProperty("lblber", " �ber ");
		defaultproperties.setProperty("lblNewLabel", "Ergebnis :  ");
		defaultproperties.setProperty("lblY", "   y  ");
		defaultproperties.setProperty("lblX", "   x   ");
		defaultproperties.setProperty("btnStart", "Start");
	}

	private void initGUI() {
		setLayout(new BorderLayout(0, 0));

		panel_4.setBorder(new TitledBorder("Hinweis"));

		panel_4.setLayout(new GridLayout(1, 0, 0, 0));
		txtrZurBedienungX.setLineWrap(true);
		txtrZurBedienungX.setText("Zur Bedienung:\r\nX und Y entsprechen den Werten des Hauptfensters m\u00FCssen hier also in umgekehrter Reihnfolge eingegeben werden.");
		scrollPane.setViewportView(txtrZurBedienungX);

		panel_4.add(scrollPane);

		add(panel_4, BorderLayout.CENTER);

		add(panel_5, BorderLayout.SOUTH);
		panel_5.setLayout(new GridLayout(2, 1, 0, 0));
		panel_5.add(panel);

		panel.setBorder(new TitledBorder("y �ber x"));

		panel.setLayout(new GridLayout(1, 3, 10, 10));

		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		panel_2.add(lblY, BorderLayout.WEST);
		panel_2.add(txtY);

		panel.add(lblber);

		panel.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		panel_3.add(lblX, BorderLayout.WEST);
		panel_3.add(txtX);
		panel_5.add(panel_1);

		panel_1.setBorder(new TitledBorder(" "));

		panel_1.setLayout(new BorderLayout(0, 0));

		panel_1.add(lblNewLabel, BorderLayout.WEST);

		panel_1.add(txterg, BorderLayout.CENTER);

		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnStart_ActionPerformed(evt);
			}
		});

		panel_1.add(btnStart, BorderLayout.EAST);
	}

	protected void btnStart_ActionPerformed(ActionEvent evt) {
		try {
			int y = Integer.parseInt(txtY.getText());
			int x = Integer.parseInt(txtX.getText());
			int erg = LZufall.bin(y, x);

			txterg.setText("" + erg);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Zahlenlesefehler", "Zahlenlesefehler", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();

		}
	}
}
