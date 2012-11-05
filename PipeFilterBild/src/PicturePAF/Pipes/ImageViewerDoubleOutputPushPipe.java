/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF.Pipes;

import PicturePAF.Pipes.ImageViewerPushPipe;
import Filters.DoubleInputPushFilter;
import Filters.PushFilter;
import javax.media.jai.PlanarImage;
/**
 *
 * @author phil
 */
public class ImageViewerDoubleOutputPushPipe extends ImageViewerPushPipe {

	private DoubleInputPushFilter<?, PlanarImage, ?> _destination2;

	/**
	 * Creates a new image viewer double output pushed pipe
	 * 
	 * @param destination1 the first destination. The images will be pushed to {@link PushedFilter#newInput(Object)}.
	 * @param destination2 the second destination. The images will be pushed to {@link TwoInputPushedFilter#newInput2(Object)}.
	 * @param title the title used as window title when showing the image
	 */
	public ImageViewerDoubleOutputPushPipe(PushFilter<PlanarImage, ?> destination1, DoubleInputPushFilter<?, PlanarImage, ?> destination2, String title) {
		super(destination1, title);

		_destination2 = destination2;
	}

	@Override
	public void push(PlanarImage data) {
		// let the super class push to the first destination.
		super.push(data);

		PlanarImage copy = data.createSnapshot();
		_destination2.newInput2(copy);
	}

}
