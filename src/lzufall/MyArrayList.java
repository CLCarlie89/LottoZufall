/**
 * 
 */
package lzufall;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author cliebsch
 * 
 */
public class MyArrayList<E> extends ArrayList<E> {

	private static final long serialVersionUID = 1L;

	public void addAll(ArrayList<E> list) {
		for (int i = 0; i < list.size(); i++) {
			super.add(list.get(i));
		}
	}

	public void addAll(List<E> list) {
		for (int i = 0; i < list.size(); i++) {
			super.add(list.get(i));
		}
	}

	public void addAll(Vector<E> list) {
		for (int i = 0; i < list.size(); i++) {
			super.add(list.get(i));
		}
	}

	@Override
	public boolean contains(Object o) {
		return super.contains(o);
	}

	/*public void sort() {
		
		E ar[] = null;
		E[] a 
		= toArray(ar);
		
		Arrays.sort(a);
		clear();
		addAll(Arrays.asList(a));
	}*/
}
