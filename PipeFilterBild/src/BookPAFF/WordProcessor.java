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
public class WordProcessor  extends GenericProcessor<Character[], CharSequence>  {
    private static final char[] WORD_SEPERATORS = { '\r', '\n', ' ' };

	private StringBuilder _word;

	public WordProcessor() {
		_word = new StringBuilder();
	}

	@Override
	public void process(Character[] data) {
		for (int i = 0; i < data.length; i++) {
			boolean seperator = false;

			for (int j = 0; !seperator && j < WORD_SEPERATORS.length; j++) {
				seperator = data[i].equals(WORD_SEPERATORS[j]);
			}

			if (seperator) {
				addOutput(_word);
				_word = new StringBuilder();
			} else {
				_word.append(data[i]);
			}
		}
	}

	@Override
	public void flush() {
		if (_word.length() > 0) {
			addOutput(_word);
			_word = new StringBuilder();
		}
	}
}
