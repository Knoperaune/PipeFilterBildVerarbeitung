/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BookPAFF;

import Pipes.PullPipe;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author phil
 */
public class FileSource implements PullPipe<Character[]> {
    private InputStream _is;

	public FileSource(InputStream is) {
		_is = is;
	}

	@Override
	public Character[] pull() {
		byte[] buff = new byte[1024];
		int read;
		Character[] chars;

		try {
			read = _is.read(buff);
			if (read > 0) {
				chars = new Character[read];
				for (int i = 0; i < read; i++) {
					chars[i] = (char) buff[i];
				}
				return chars;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null; // end of stream.
	}
}
