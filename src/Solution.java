import java.io.*;
import java.util.*;

class Solution {

	static String[] diffBetweenTwoStrings(String source, String target) {
		String s = new StringBuilder(source).reverse().toString();
		String t = new StringBuilder(target).reverse().toString();

		int sLen = s.length();
		int tLen = t.length();
		int[][] m = new int[tLen + 1][sLen + 1];
		int[][][] bt = new int[tLen + 1][sLen + 1][2];
		int[] cbt = null;
		for (int j = 0; j <= sLen; j++) {
			m[0][j] = 0;
			bt[0][j] = null;
		}
		for (int i = 0; i <= tLen; i++) {
			m[i][0] = 0;
			bt[i][0] = null;
		}
		for (int i = 1; i <= tLen; i++) {
			char st = t.charAt(i - 1);
			for (int j = 1; j <= sLen; j++) {
				char sc = s.charAt(j - 1);
				int ccs = 0;
				cbt = null;
				if (st == sc) {
					ccs = m[i - 1][j - 1] + 1;
					cbt = new int[] { i - 1, j - 1 };
				} else {
					if (m[i][j - 1] < m[i - 1][j]) {
						ccs = m[i - 1][j];
						cbt = new int[] { i - 1, j };
					} else {
						ccs = m[i][j - 1];
						cbt = new int[] { i, j - 1 };
					}
				}
				m[i][j] = ccs;
				bt[i][j] = cbt;
			}
		}
		String[] r = new String[tLen + sLen];
		int i = 0;
		int ci = tLen;
		int cj = sLen;
		/*
		 * for (int[] fila : m) { System.out.println(Arrays.toString(fila)); }
		 */

		cbt = bt[ci][cj];
		while (cbt != null) {
			char ss = s.charAt(cj - 1);
			char st = t.charAt(ci - 1);
//			System.out.println(Arrays.toString(cbt));
			if (ci == cbt[0]) {
				r[i] = "-" + ss;
			} else {
				if (cj == cbt[1]) {
					r[i] = "+" + st;
				} else {
					r[i] = "" + ss;
				}
			}
			ci = cbt[0];
			cj = cbt[1];
			cbt = bt[ci][cj];
			i++;
		}
		if (cj > 0 && ci > 0 && s.charAt(cj - 1) == t.charAt(ci - 1)) {
			r[i++] = "" + s.charAt(cj - 1);
		} else {
			if (cj > 0) {
				r[i++] = "-" + s.charAt(cj - 1);
			}
			if (ci > 0) {
				r[i++] = "+" + t.charAt(ci - 1);
			}
		}
		for (int j = cj - 2; j >= 0; j--) {
			r[i++] = "-" + s.charAt(j);
		}
		for (int k = ci - 2; k >= 0; k--) {
			r[i++] = "+" + t.charAt(k);
		}

//		return reverse(Arrays.copyOfRange(r, 0, i));
		return Arrays.copyOfRange(r, 0, i);
	}

	public static void main(String[] args) {
		String exp[][] = { { "-A", "+B" }, { "-A", "-B", "+C", "+D" },
				{ "A", "B", "-C", "D", "-E", "F", "+F", "G", "+H" },
				{ "+P", "+P", "G", "-H", "-M", "-X", "G", "+X", "H", "+H", "U", "-G", "-X", "L", "+L" } };
		int i = 0;
		for (String[] tc : new String[][] { { "A", "B" }, { "AB", "CD" }, { "ABCDEFG", "ABDFFGH" },
				{ "GHMXGHUGXL", "PPGGXHHULL" } }) {
			String[] r = diffBetweenTwoStrings(tc[0], tc[1]);
			if (!Arrays.equals(r, exp[i])) {
				System.out.println("exp " + Arrays.toString(exp[i]) + " real " + Arrays.toString(r));

			}
			i++;
		}
	}

	// XXX:
	// https://stackoverflow.com/questions/2137755/how-do-i-reverse-an-int-array-in-java
	public static String[] reverse(String[] data) {
		int left = 0;
		int right = data.length - 1;

		while (left < right) {
			// swap the values at the left and right indices
			String temp = data[left];
			data[left] = data[right];
			data[right] = temp;

			// move the left and right index pointers in toward the center
			left++;
			right--;
		}
		return data;
	}
}

// s=  A B C D E F   G
// t=  A B   D   F F G H
// lcs=A B   D   F   G
//     

// (A, AB); AB, A

// (AB, ABB) = 1 + (A, AB)

//          A  B  C  D  E  F  G
// A        1  1  1  1  1  1  1
// AB       1  2  2  2  2  2  2  
// ABD      1  2  2  3  3  
// ABDF     1
// ABDFF    1
// ABDFFG   1
// ABDFFGH  1