/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF.Pipes;
import Pipes.PullPipe;
import javax.media.jai.PlanarImage;
/**
 *
 * @author phil
 */
public class ImageViewerPullSource implements PullPipe<PlanarImage> {

	private PlanarImage _image;

	/**
	 * Creates a new image viewer pulled source
	 * 
	 * @param image the image which should be returned when this pipe is pulled
	 */
	public ImageViewerPullSource(PlanarImage image) {
		super();
		_image = image;
	}

	@Override
	public PlanarImage pull() {
		new JImageViewer(_image, "source");
		return _image;
	}
}