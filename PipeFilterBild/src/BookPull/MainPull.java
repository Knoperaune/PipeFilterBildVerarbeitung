/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BookPull;

import BookPAFF.ConsoleBook;
import BookPAFF.FileSource;
import BookPAFF.LineProcessor;
import BookPAFF.PageProcessor;
import BookPAFF.WordProcessor;
import Filters.PullFilter;
import Filters.PushFilter;
import Pipes.PullPipe;
import Pipes.SinglePullPipe;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 *
 * @author phil
 */
public class MainPull {
      public static void main(String[] args) {
		try {
                        String path = "C:\\Users\\phil\\Desktop\\FHV\\5. Semester\\Systemarchitekturen\\Übung\\PipesAndFilters\\PipesAndFilters\\src\\Tests\\pg11.txt";
			//holt daten
                        FileSource source = new FileSource(new FileInputStream(/*"pg11.txt"*/path));
                        
                        //macht die wörter (leerzeichen etc sind wörter)zieht sie aus der source
			PullFilter<Character[], CharSequence> wordfilter = new PullFilter<Character[], CharSequence>(
					new WordProcessor(), source);
                       SinglePullPipe<CharSequence> wordpipe = new SinglePullPipe<CharSequence>(wordfilter);
                       PullFilter<CharSequence, CharSequence> linefilter = new PullFilter<CharSequence, CharSequence>(new LineProcessor(60),wordpipe);
                       SinglePullPipe<CharSequence> linepipe = new SinglePullPipe<CharSequence>(linefilter);
                       PullFilter<CharSequence, CharSequence> pagefilter = new PullFilter<CharSequence, CharSequence>(
					new PageProcessor(40), linepipe);
                       SinglePullPipe<CharSequence> pagepipe = new SinglePullPipe<CharSequence>(pagefilter);
                       ConsoleBook cb = new ConsoleBook();
                       PagePipe pp = new PagePipe(pagepipe, cb);
                       while (pp.doit()) {                        
                        
                    }
                } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
