/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BookPAFF;

import Filters.PullFilter;
import Filters.Pump;
import Filters.PushFilter;
import Pipes.SinglePullPipe;
import Pipes.SinglePushPipe;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 *
 * @author phil
 */
public class MainProgram {
    public static void main(String[] args) {
		try {
                        String path = "C:\\Users\\phil\\Desktop\\FHV\\5. Semester\\Systemarchitekturen\\Übung\\PipesAndFilters\\PipesAndFilters\\src\\Tests\\pg11.txt";//"C:\\Users\\phil\\Desktop\\FHV\\5. Semester\\Systemarchitekturen\\Übung\\SysArchitekturen\\PipesAndFilters\\src\\Tests\\pg11.txt";
			//holt daten
                        FileSource source = new FileSource(new FileInputStream(/*"pg11.txt"*/path));
                        
                        //macht die wörter (leerzeichen etc sind wörter)zieht sie aus der source
			PullFilter<Character[], CharSequence> word = new PullFilter<Character[], CharSequence>(
					new WordProcessor(), source);

                        //ist für die ausgabe
			ConsoleBook destination = new ConsoleBook();
                        
                        //baut zeilen zu einer seite zusammen, drückt sie in die ausgabe (ist hier ausnahme)
			PushFilter<CharSequence, CharSequence> page = new PushFilter<CharSequence, CharSequence>(
					new PageProcessor(40), destination);
                                        
                        //Pumpe delegiert: Der LineProcessor verarbeitet, Word holt sich die daten und macht wörter
                        //LineProcssor setzt wörter zusammen
                        //Pumpe gibt an Page weiter
                        //Page gibt an destination weiter (gibt auf console aus)
                        //LineProcessor holt Wörter und baut sie zur Zeile zusammen
			Pump<CharSequence, CharSequence> line = new Pump<CharSequence, CharSequence>(
					new LineProcessor(60), new SinglePullPipe<CharSequence>(
							word), new SinglePushPipe<CharSequence>(page));

			while (line.doit()) {

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
