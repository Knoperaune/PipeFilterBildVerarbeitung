/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF;
import java.awt.BorderLayout;
import java.awt.Container;

import javax.media.jai.PlanarImage;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.sun.media.jai.widget.DisplayJAI;
/**
 *
 * @author phil
 */
public class JImageViewer extends JFrame {

	/**
	 * Creates a new window containing the given image
	 * 
	 * @param image the image to show
	 * @param title the title for the window
	 */
	public JImageViewer(PlanarImage image, String title) {

		setTitle(title + " - Dimensions: " + image.getWidth() + "x" + image.getHeight() + " Bands:" + image.getNumBands());

		DisplayJAI dj = new DisplayJAI(image);

		Container contentPane = getContentPane();
		contentPane.add(new JScrollPane(dj), BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 370);
		setVisible(true);
	}
}
