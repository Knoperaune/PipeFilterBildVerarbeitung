/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF.Processor;

import Filters.GenericProcessor;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.util.LinkedList;
import javax.media.jai.JAI;
import javax.media.jai.ParameterBlockJAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.ROI;
import javax.media.jai.RenderedOp;
import javax.media.jai.registry.RenderedRegistryMode;

/**
 *
 * @author phil
 */
public class CentroidProcessor extends GenericProcessor<PlanarImage, PlanarImage> {

    LinkedList<PlanarImage> rois;
    LinkedList<Rectangle> roisRectangles;
    LinkedList<Point> centroids;
    int maxBrightness = 244;
    int ballcount = 7;

    public CentroidProcessor() {
        rois = new LinkedList<>();
        roisRectangles = new LinkedList<>();
        centroids = new LinkedList<>();
    }

    @Override
    public void process(PlanarImage image) {
        /*ParameterBlock pb = new ParameterBlock();
         pb.addSource(image);
         addOutput(JAI.create("centroid", pb));*/

        /*
         ParameterBlockJAI pbj = new ParameterBlockJAI(
         "centroid", RenderedRegistryMode.MODE_NAME
         );
         pbj.setSource("Source0", image);
         ROI roi = new ROI(image);
         pbj.setParameter("roi", roi);
         RenderedOp asdf = JAI.create("centroid", pbj);
         addOutput(image);*/
       /* ParameterBlock pb = new ParameterBlock();
        ROI roi = new ROI(JAI.create("bandselect", image, new int[]{0}));
        pb.add(roi);
        pb.addSource(image);

        PlanarImage centroidImage = JAI.create("centroid", pb, RenderingHints.KEY_ANTIALIASING);
        addOutput(centroidImage);*/
        
        
        
        for (int i = 0; i < ballcount; i++) {
            Rectangle temp = new Rectangle(i * 60, 10, 45, 50);
            roisRectangles.add(temp);
            PlanarImage tempImg = PlanarImage.wrapRenderedImage(
                    (RenderedImage) image.getAsBufferedImage(temp, image.getColorModel()));
            rois.add(tempImg);
           // JAI.create("filestore", tempImg, "roi_loetstelle" + i + ".jpg", "JPEG");
        }

        for (int i = 0; i < rois.size(); i++) {

            PlanarImage pi = rois.get(i);

            int mittelwertX = 0;
            int mittelwertY = 0;

            for (int y = 0; y < pi.getHeight(); y++) {

                LinkedList<Integer> xWhite = new LinkedList<>();

                for (int x = 0; x < pi.getWidth(); x++) {
                    int sample = pi.getData().getPixel(x, y, (int[]) null)[0];

                    if (sample >= maxBrightness) {
                        xWhite.add(x);
                    }
                }

                if (xWhite.size() != 0) {
                    mittelwertX = (mittelwertX + (xWhite.getFirst() + xWhite.getLast()) / 2) / 2;
                }
            }

            for (int x = 0; x < pi.getWidth(); x++) {

                LinkedList<Integer> yWhite = new LinkedList<>();

                for (int y = 0; y < pi.getHeight(); y++) {
                    int sample = pi.getData().getPixel(x, y, (int[]) null)[0];

                    if (sample >= maxBrightness) {
                        yWhite.add(y);
                    }
                }

                if (yWhite.size() != 0) {
                    mittelwertY = (mittelwertY + (yWhite.getFirst() + yWhite.getLast()) / 2) / 2;
                }
            }

            centroids.add(new Point(mittelwertX + roisRectangles.get(i).x,
                    mittelwertY + roisRectangles.get(i).y));
        }

        BufferedImage buf = image.getAsBufferedImage();
        Graphics2D g = buf.createGraphics();

        for (Point p : centroids) {
            g.setColor(Color.BLACK);
            g.fillOval(p.x - 2, p.y - 2, 4, 4);
        }

        //JAI.create("filestore", buf, "centroid.jpg", "JPEG");
        
        addOutput(PlanarImage.wrapRenderedImage(buf));
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
