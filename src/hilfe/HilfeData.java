package hilfe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class HilfeData {

	private File hilfsdatei = null;
	private String hilfsdateitext = "";
	private String listname = " ";

	public HilfeData(String listname) {
		this.listname = listname;
	}

	/**
	 * @param hilfsdatei
	 */
	public HilfeData(File hilfsdatei) {
		this.listname = hilfsdatei.getName().substring(0, hilfsdatei.getName().lastIndexOf("."));
		this.hilfsdatei = hilfsdatei;
		hilfsdateitext = getDateiText();
	}

	/**
	 * @param hilfsdatei
	 */
	public HilfeData(String listname, File hilfsdatei) {
		this.listname = listname;
		this.hilfsdatei = hilfsdatei;
		hilfsdateitext = getDateiText();
	}

	/**
	 * s
	 * 
	 * @param hilfsdateitext
	 */
	public HilfeData(String listname, String hilfsdateitext) {
		this.listname = listname;
		this.hilfsdateitext = hilfsdateitext;
	}

	/**
	 * @param hilfsdatei
	 * @param hilfsdateitext
	 */
	public HilfeData(String listname, File hilfsdatei, String hilfsdateitext) {
		this.listname = listname;
		this.hilfsdatei = hilfsdatei;
		this.hilfsdateitext = hilfsdateitext;
		if (hilfsdateitext != getDateiText()) {
			this.hilfsdateitext = this.hilfsdateitext + "\n" + getDateiText();
		}
	}

	public String getDateiText() {
		StringBuilder text = new StringBuilder(10);
		if (hilfsdatei != null) {
			FileReader fw = null;
			BufferedReader bw = null;
			try {
				String t = "";
				fw = new FileReader(hilfsdatei);
				bw = new BufferedReader(fw);
				while ((t = bw.readLine()) != null) {
					text.append(t + "\n");
				}
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			} finally {
				try {
					bw.close();
					fw = null;
					bw = null;
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		return text.toString();
	}

	@Override
	public String toString() {
		return listname;
	}

	/**
	 * @return the listname
	 */
	public String getListname() {
		return listname;
	}

	/**
	 * @param listname
	 *            the listname to set
	 */
	public void setListname(String listname) {
		this.listname = listname;
	}

	/**
	 * @return the hilfsdatei
	 */
	public File getHilfsdatei() {
		return hilfsdatei;
	}

	/**
	 * @param hilfsdatei
	 *            the hilfsdatei to set
	 */
	public void setHilfsdatei(File hilfsdatei) {
		this.hilfsdatei = hilfsdatei;
	}

	/**
	 * @return the hilfsdateitext
	 */
	public String getHilfsdateitext() {
		return hilfsdateitext;
	}

	/**
	 * @param hilfsdateitext
	 *            the hilfsdateitext to set
	 */
	public void setHilfsdateitext(String hilfsdateitext) {
		this.hilfsdateitext = hilfsdateitext;
	}

}
