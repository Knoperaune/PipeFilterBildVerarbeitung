/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BookPAFF;

import Filters.GenericProcessor;

/**
 *
 * @author phil
 */
public class PageProcessor extends GenericProcessor<CharSequence, CharSequence> {
    private int _lines;
	private int _maxLines;
	private StringBuilder _line;

	public PageProcessor(int maxLines) {
		_lines = 0;
		_maxLines = maxLines;
		_line = new StringBuilder();
	}

	@Override
	public void process(CharSequence data) {
		if (_lines > _maxLines) {
			addOutput(_line);
			_line = new StringBuilder();
			_lines = 0;
		}

		_line.append(data);
		_line.append("\n");
		_lines++;
	}

	@Override
	public void flush() {
		if (_line.length() > 0) {
			addOutput(_line);
			_line = new StringBuilder();
		}
	}
}
