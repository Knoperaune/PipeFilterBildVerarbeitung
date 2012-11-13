/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF.Pipes;

import Pipes.PushPipe;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
/**
 *
 * @author phil
 */
public class ImageViewerPushDest implements PushPipe<PlanarImage> {

	@Override
	public void push(PlanarImage data) {
            JAI.create("filestore", data, "finishedpush.jpg", "JPEG");
		new JImageViewer(data, "destination");
                
	}
}
