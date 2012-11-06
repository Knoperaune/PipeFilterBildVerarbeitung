/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF.Pipes;

import PicturePAF.old.JImageViewer;
import Pipes.PushPipe;
import javax.media.jai.PlanarImage;
/**
 *
 * @author phil
 */
public class ImageViewerPushDest implements PushPipe<PlanarImage> {

	@Override
	public void push(PlanarImage data) {
		new JImageViewer(data, "destination");
	}
}
