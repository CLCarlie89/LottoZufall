package hilfe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.rtf.RTFEditorKit;

public class Hilfe extends JPanel {

	private static final long serialVersionUID = 1L;

	private JScrollPane scrollPane = new JScrollPane();
	private JTextPane editorPane = new JTextPane();
	private MyListModel<HilfeData> model = new MyListModel<HilfeData>();
	private JList<HilfeData> list = new JList<HilfeData>(model);
	private JPanel panel = new JPanel();

	public Hilfe() {
		initGUI();
	}

	public Hilfe(HilfeData hilfe) {

		addListData(hilfe);
		initGUI();
	}

	public Hilfe(HilfeData[] hilfe) {

		addListData(hilfe);
		initGUI();
	}

	public Hilfe(MyArrayList<HilfeData> hilfe) {

		addListData(hilfe);
		initGUI();
	}

	public void initGUI() {
		setLayout(new BorderLayout(10, 10));

		panel.setLayout(new BorderLayout(10, 10));

		scrollPane.setViewportView(editorPane);

		panel.add(list, BorderLayout.WEST);
		panel.add(scrollPane, BorderLayout.CENTER);

		add(panel);

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {

				HilfeData hdata = model.getElementAt(list.getSelectedIndex());
				File file = hdata.getHilfsdatei();
				
				editorPane.setEditorKit(new StyledEditorKit());
				if (!file.exists() || file == null) {
					editorPane.setText(hdata.getHilfsdateitext());
				} else {

					FileReader rd;
					BufferedReader br;
					try {
						rd = new FileReader(file);
						br = new BufferedReader(rd);

						if (getExtension(file).equals("rtf") || getExtension(file).equals("txt")) {
							editorPane.setEditorKit(new StyledEditorKit());
							if (getExtension(file).equals("rtf")) {
								editorPane.setEditorKit(new RTFEditorKit());
							}
						} else {
							editorPane.setEditorKit(new HTMLEditorKit());
						}

						editorPane.read(br, null);

						// editorPane.setPage(hdata.getHilfsdatei().getAbsolutePath());
					} catch (IOException e) {
					
						e.printStackTrace();
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				}

			}
		});

	}

	public String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	public void addListData(HilfeData hilfe) {
		model.addElement(hilfe);
		list.setSelectedIndex(0);
	}

	public void addListData(HilfeData[] hilfe) {
		model.addElements(hilfe);
		list.setSelectedIndex(0);
	}

	public void addListData(MyArrayList<HilfeData> hilfe) {
		model.addElements(hilfe);
		list.setSelectedIndex(0);
	}

	public static void main(String[] args) {
		MyArrayList<HilfeData> hd = new MyArrayList<HilfeData>();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		hd.add(new HilfeData(new File("D:\\passw.rtf")));
		hd.add(new HilfeData(new File("D:\\links.rtf")));
		hd.add(new HilfeData(new File("D:\\Carsten\\Spiele\\anno\\Tipps\\1404 Hilfe.rtf")));

		Hilfe help = new Hilfe(hd);
		frame.add(help);

		frame.setMinimumSize(new Dimension(300, 300));
		frame.pack();
		frame.setVisible(true);
	}
}
