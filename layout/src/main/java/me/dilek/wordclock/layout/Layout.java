package me.dilek.wordclock.layout;

import java.util.ArrayList;
import java.util.Collections;
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

	public List<String> words() {
		return rows.stream().flatMap(Row::stream).collect(Collectors.toList());
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

}
