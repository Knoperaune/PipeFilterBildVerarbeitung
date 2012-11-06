/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF.Pipes;

import Filters.PullFilter;
import PicturePAF.old.JImageViewer;
import Pipes.SinglePullPipe;

import javax.media.jai.PlanarImage;
/**
 *
 * @author phil
 */
public class ImageViewerPullPipe extends SinglePullPipe<PlanarImage> {

	private String _title;

	/**
	 * Creates a new image viewer pulled pipe
	 * 
	 * @param source the source filter for this pipe from which the images should be pulled
	 * @param title the title used as window title when showing the image
	 */
	public ImageViewerPullPipe(PullFilter<?, PlanarImage> source, String title) {
		super(source);

		_title = title;
	}

	@Override
	public PlanarImage pull() {
		// let the simple pulled pipe pull the image from the source
		PlanarImage image = super.pull();

		// show the image
		new JImageViewer(image, _title);

		return image;
	}

}