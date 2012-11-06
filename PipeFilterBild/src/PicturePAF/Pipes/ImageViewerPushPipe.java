/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF.Pipes;
import Filters.PushFilter;
import PicturePAF.old.JImageViewer;
import Pipes.SinglePushPipe;
import javax.media.jai.PlanarImage;
/**
 *
 * @author phil
 */
public class ImageViewerPushPipe extends SinglePushPipe<PlanarImage> {

	private String _title;

	/**
	 * Creates a new image viewer pushed pipe
	 * 
	 * @param destination the destination filter to which data should be pushed
	 * @param title the title used as window title when showing the image
	 */
	public ImageViewerPushPipe(PushFilter<PlanarImage, ?> destination, String title) {
		super(destination);

		_title = title;
	}

	@Override
	public void push(PlanarImage data) {
		new JImageViewer(data, _title);

		// let the simple pushed pipe do the work.
		super.push(data);
	}
}
