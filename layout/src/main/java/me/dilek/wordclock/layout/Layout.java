package me.dilek.wordclock.layout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Layout {

	private final int rowCount;
	private final int colCount;

	private final List<Row> rows;

	public Layout(int rows, int cols) {
		this.rowCount = rows;
		this.colCount = cols;
		this.rows = new ArrayList<>();
	}

	public boolean add(String word) {
		for (int i = 0; i < rowCount; i++) {
			Row row;
			if (i == rows.size()) {
				row = new Row(colCount);
				rows.add(row);
			} else {
				row = rows.get(i);
			}

			if (row.add(word)) {
				return true;
			}
		}
		return false;
	}

	public List<String> wordsList() {
		return words().collect(Collectors.toList());
	}

	public Stream<String> words() {
		return rows.stream().flatMap(Row::stream);
	}
	
	public List<String> words(int row) {
		if (row < rows.size()) {
			return rows.get(row).words;
		}
		return Collections.emptyList();
	}

	public int rowCount() {
		return rows.size();
	}

	private class Row {

		private final List<String> words = new ArrayList<>();
		private final int maxCols;
		private int len;

		public Row(int maxCols) {
			this.maxCols = maxCols;
			this.len = 0;
		}

		public boolean add(String word) {
			if (len + word.length() <= maxCols) {
				len += word.length();
				words.add(word);
				return true;
			} else
				return false;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (String word : words) {
				char[] chars = word.toCharArray();
				for (char c : chars) {
					if (sb.length() != 0)
						sb.append(' ');
					sb.append(c);
				}
			}
			return sb.toString();
		}

		public Stream<String> stream() {
			return words.stream();
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Row row : rows) {
			sb.append(row).append(System.lineSeparator());
		}
		return sb.toString();
	}

	public boolean contains(Reading reading) {
		int nextIndex = 0;
		List<String> layoutWords = wordsList();
		List<String> readingWords = reading.getWords();
		for (Iterator<String> iterator = readingWords.iterator(); iterator.hasNext();) {
			String word = iterator.next();
			for (; nextIndex < layoutWords.size(); nextIndex++) {
				String next = layoutWords.get(nextIndex);
				if(next != null && next.equals(word)) {
					if(!iterator.hasNext()) {
						return true;
					}
					word = iterator.next();
					continue;//continue for next match
				}
			}
			if(nextIndex >= layoutWords.size()) {
				return false;
			}
			
		}
		return true;
	}

}
