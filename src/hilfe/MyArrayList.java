/**
 * 
 */
package hilfe;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author cliebsch
 *
 */
public class MyArrayList<E> extends ArrayList<E> {
	
	private static final long serialVersionUID = 1L;

	public void addAll(ArrayList<E> list){
		for (int i = 0; i < list.size(); i++) {
			super.add(list.get(i));
		}
	}

	public void addAll(List<E> list){
		for (int i = 0; i < list.size(); i++) {
			super.add(list.get(i));
		}
	}
	public void addAll(Vector<E> list){
		for (int i = 0; i < list.size(); i++) {
			super.add(list.get(i));
		}
	}

}
