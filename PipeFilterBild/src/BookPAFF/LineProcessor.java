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
public class LineProcessor extends GenericProcessor<CharSequence, CharSequence> {
    private int _maxLength;
	private StringBuilder _line;

	public LineProcessor(int maxLength) {
		_maxLength = maxLength;
		_line = new StringBuilder();
	}

	@Override
	public void process(CharSequence data) {
		if ((_line.length() + data.length()) > _maxLength) {
			addOutput(_line);
			_line = new StringBuilder();
		}

		_line.append(data).append(" ");
	}

	@Override
	public void flush() {
		if (_line.length() > 0) {
			addOutput(_line);
			_line = new StringBuilder();
		}
	}
}
