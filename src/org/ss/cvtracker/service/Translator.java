package org.ss.cvtracker.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Translates patterns from one coding to another. Encodes/decodes ambiguous
 * symbols into more clear depending on environment that uses appropriate
 * patterns.
 * 
 * @author IF-023
 */
public class Translator {
	private static List<Symbols> symbolsList = null;

	// Creates array of symbols, that should be replaced in letter's,
	// resume's and vacancy's patterns. Then creates List of Symbols instances,
	// that contain
	// symbols from array.
	static {
		symbolsList = new ArrayList<Symbols>();
		String[][] symbols = { { "\"", "&#34" }, { "<", "&#60" }, { "[", "&#91" }, { "^", "&#94" }, { ">", "&#62" },
				{ "]", "&#93" }, { "+", "&#43" }, { "=", "&#61" }, { "\\", "&#92" }, { "(", "&#40" }, { ":", "&#58" },
				{ "/", "&#47" }, { "!", "&#33" }, { ";", "&#59" }, { "*", "&#42" }, { "?", "&#63" }, { ")", "&#41" },
				{ ".", "&#46" }, { "&", "&#38" }, { ",", "&#44" }, { "'", "&#39" }, { "{", "&#123" }, { "}", "&#125" } };
		for (int i = 0; i < symbols.length; i++) {
			Symbols symbol = new Translator().new Symbols(symbols[i][0].toCharArray()[0], symbols[i][1]);
			symbolsList.add(symbol);
		}
	}

	/**
	 * Gets key from Symbols object by it's value.
	 * 
	 * @param value
	 *            - symbol's value
	 * @return symbol's key
	 */
	public static Character getKey(String value) {
		for (Symbols symbol : symbolsList) {
			if (symbol.getValue().equals(value)) {
				return symbol.getKey();
			}
		}
		return null;
	}

	/**
	 * Gets value from Symbols object by it's key.
	 * 
	 * @param value
	 *            - symbol's key
	 * @return symbol's value
	 */
	public static String getValue(char key) {
		for (Symbols symbol : symbolsList) {
			if (symbol.getKey() == key) {
				return symbol.getValue();
			}
		}
		return null;
	}

/**
	 * Decodes letter's, resume's or vacancy's patterns (replace symbols like '&#60' to '<')
	 * @param pattern - pattern, that should be decoded
	 * @return decoded pattern
	 */
	public static String decoder(String pattern) {
		char[] chars = pattern.toCharArray();
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < chars.length;) {
			if (chars[i] != '&') {
				strBuf.append(chars[i]);
				++i;
			} else {
				String value = new String(chars, i, 4);
				strBuf.append(getKey(value));
				i += 4;
			}
		}
		return strBuf.toString();
	}

/**
	 * Encodes letter's, resume's, vacancy's patterns (replace symbols like '<' to '&#60')
	 * @param pattern pattern, that should be encoded
	 * @return encoded pattern
	 */
	public static String encoder(String pattern) {
		char[] patternCharc = pattern.toCharArray();
		StringBuffer resultPattern = new StringBuffer();
		for (char c : patternCharc) {
			String value = getValue(c);
			if (value != null) {
				resultPattern.append(value);
			} else {
				resultPattern.append(c);
			}
		}
		return resultPattern.toString();
	}

	/**
	 * Inner class, that contains symbols, that should be encoded/decoded.
	 * 
	 * @author IF-023
	 */
	class Symbols {
		private char key;
		private String value;

		public Symbols(char name, String value) {
			this.key = name;
			this.value = value;
		}

		public char getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}
	}
}
