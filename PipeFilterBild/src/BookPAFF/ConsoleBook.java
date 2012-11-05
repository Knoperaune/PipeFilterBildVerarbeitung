/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BookPAFF;

import Pipes.PushPipe;

/**
 *
 * @author phil
 */
public class ConsoleBook implements PushPipe<CharSequence> {
    int _pageNr;

	public ConsoleBook() {
		_pageNr = 0;
	}

	@Override
	public void push(CharSequence data) {
		if (data != null) {
			System.out.println("Page #" + _pageNr++);
			System.out.println(data);
			System.out.println();
		}
	}
}
