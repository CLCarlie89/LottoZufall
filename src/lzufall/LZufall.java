package lzufall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class LZufall {
	int n;
	ArrayList<String> a;
	Lotto_Zufall_GUI lotto_Zufall_GUI = null;

	public LZufall() {
		n = 1;
		a = new ArrayList<String>();
	}

	public LZufall(int n) throws Exception, OutOfMemoryError, IndexOutOfBoundsException {
		this.n = n;

		a = new ArrayList<String>();
	}

	public LZufall(int n, Lotto_Zufall_GUI lotto_Zufall_GUI) throws Exception, OutOfMemoryError, IndexOutOfBoundsException {
		this.n = n;
		this.lotto_Zufall_GUI = lotto_Zufall_GUI;
		a = new ArrayList<String>();
	}

	public boolean isThreadInterruppted() {
		if (lotto_Zufall_GUI == null) {
			return false;
		}
		if (lotto_Zufall_GUI.getThread().interrupted()) {
			lotto_Zufall_GUI.getThread().interrupt();
			return true;
		}
		return false;
	}

	/**
	 * Bestimmt rekursiv den Binomialkoeffizienten "n �ber k", z.B. 49 �ber 6
	 * f�r Lotto.
	 * 
	 * @param n
	 * @param k
	 * @return Binomialkoeffizient "n �ber k"
	 */
	public static int bin(final int n, final int k) {
		if (k > n || k < 0)
			return 0;
		if (k == 0 || k == n)
			return 1;
		return bin(n, k - 1) * (n - k + 1) / k;
	}

	public ArrayList<String> getZahlenXausY(int x, int y) throws MeineException {
		if (x >= y) {
			throw new MeineException("X muss kleiner als Y sein.");
		}
		if (n > bin(y, x)) {
			throw new MeineException("Die Anzahl Reihen �bersteigt die maximal m�gliche Anzahl an Reihen.");

			// return null;
		}

		int b[] = new int[x];
		Random r = new Random();
		int z = 0;
		for (int x1 = 0; x1 < n; x1++) {
			if (isThreadInterruppted()) {
				return a;
			}
			for (int i = 0; i < x; i++) {
				while (z == 0) {
					z = r.nextInt(y);

					for (int t = 0; t < i; t++) {
						if (b[t] == z) {
							z = 0;
						}
					}
				}
				b[i] = z;
				z = 0;
			}
			// Quicksort q = new Quicksort();
			// b = q.sortInt(b, 0, x);
			Arrays.sort(b);

			String x2 = "";
			for (int i = 0; i < x; i++) {
				x2 = x2 + " " + b[i] + " ";

			}
			// for (int k = 0; k < x1; k++) {
			if (x1 == 0) {
				//break;
			}

			if (a.contains(x2)) {
				x1--;
			} else {
				a.add(x2);
			}
			// }
			if (isThreadInterruppted()) {
				return a;
			}

		}
		return a;
	}

	public static void main(String[] args) {
		try {
			LZufall lz = new LZufall(12);
			lz.getZahlenXausY(5, 50);
			System.out.println("Ihre Zahlen sind:");
			for (int i = 0; i < 12; i++) {
				System.out.println(lz.a.get(i));
			}

			System.out.println(bin(50, 5));
			System.out.println(Math.pow(5, 50));
			System.out.println(bin(49, 6));
			System.out.println(Math.pow(6, 49));

		} catch (OutOfMemoryError e) {
		
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public void destroy() {
		a = null;
		n = 0;
		lotto_Zufall_GUI = null;
		System.gc();

	}

	public ArrayList<String> getA() {
		return a;
	}

	public void setA(ArrayList<String> a) {
		this.a = a;
	}

}
