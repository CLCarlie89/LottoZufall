package hilfe;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

/**
 * @author Carsten Liebsch
 * 
 * @param <E>
 */
public class MyListModel<E> extends DefaultListModel<E> {

	private static final long serialVersionUID = 1L;

	/**
	 * Ermöglicht das Anhängen mehrerer Elemente auf einmal
	 * 
	 * @param elements
	 *            Array der Elemente die angehangen werden sollen
	 */
	public void addElements(E[] elements) {
		for (int i = 0; i < elements.length; i++) {
			super.addElement(elements[i]);
		}
	}

	/**
	 * Ermöglicht das Anhängen mehrerer Elemente auf einmal
	 * 
	 * @param elements
	 *            Liste der Elemente die angehangen werden sollen
	 */
	public void addElements(List<E> elements) {
		for (int i = 0; i < elements.size(); i++) {
			addElement(elements.get(i));
		}
	}

	public void addElementAt(int index, E element) {
		ArrayList<E> liste = getElements();
		liste.add(index, element);
		removeAllElements();
		addElements(liste);
	}

	public ArrayList<E> getElements() {
		ArrayList<E> elemente = new ArrayList<E>();
		for (int i = 0; i < this.getSize(); i++) {
			elemente.add(getElementAt(i));
		}
		return elemente;
	}

	public void moveElementInListToIndex(int oldindex, int newindex) {
		ArrayList<E> liste = getElements();
		E element = liste.get(oldindex);
		liste.remove(oldindex);
		liste.add(newindex, element);
		removeAllElements();
		addElements(liste);

	}
}
