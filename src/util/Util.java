package util;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

public class Util {

    /**
     * Reads an image from a file.
     * 
     * @param path
     * @return
     * @throws IOException
     */
    static public Image readImageFromFile(String path) throws IOException {
        Image image = null;

        try {
            File file = new File(path);
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    /**
     * Reads an image from a URL.
     * 
     * @param path
     * @return
     * @throws IOException
     */
    static public Image readImageFromURL(String path) throws IOException {
        Image image = null;

        try {
            URL url = new URL(path);
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * Converts a BufferedImage to an Image
     * 
     * @param bufferedImage
     * @return	the converted Image
     */
    static public Image toImage(BufferedImage bufferedImage) {
        return Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());
    }

    /**
     * Returns the width of a string (in pixels)
     * 
     * @param str	the string to measure
     * @param f		the font used to render the string
     * @return		the width of the string in pixels
     */
    static public int getStringWidth(String str, Font f) {
        FontMetrics metrics = new FontMetrics(f) {
            // avoid serialID warning

            private static final long serialVersionUID = 1L;
        };

        Rectangle2D bounds = metrics.getStringBounds(str, null);
        int width = (int) bounds.getWidth();
        return width;
    }

    /**
     * Returns the height of a string (in pixels)
     * 
     * @param str	the string to measure
     * @param f		the font used to render the string
     * @return		the height of the string in pixels
     */
    static public int getStringHeight(String str, Font f) {
        FontMetrics metrics = new FontMetrics(f) {
            // avoid serialID warning

            private static final long serialVersionUID = 1L;
        };

        Rectangle2D bounds = metrics.getStringBounds(str, null);
        int height = (int) bounds.getHeight();
        return height;
    }

    /**
     * Get a String representing the date and time it is right now.
     * 
     * @param dateFormat
     * @return
     */
    static public String getNow(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat myFormat = new SimpleDateFormat(dateFormat);
        return myFormat.format(cal.getTime());
    }

    // TODO: implement correctly
	/*
    static public long getChaoticSeed(){
    long time_ms = Calendar.getInstance().getTimeInMillis();
    return (long) (Long.MAX_VALUE*Math.sin(1/((double)time_ms/100))*(1/(1-(double)time_ms)));
    }*/
    /**
     * Adapted from Util.as by Gideon
     */
    static public Point2D.Double nextCorrelatedGaussians(double r) {
        double d1, d2, n1, n2, lambda;
        Point2D.Double arr = new Point2D.Double();
        boolean isNeg = false;

        if (r < 0) {
            r *= -1;
            isNeg = true;
        }

        lambda = ((r * r) - Math.sqrt(r * r - r * r * r * r)) / (2 * r * r - 1);

        n1 = Globals.GLOBAL_RANDOM.nextGaussian();
        n2 = Globals.GLOBAL_RANDOM.nextGaussian();

        d1 = n1;
        d2 = ((lambda * n1) + ((1 - lambda) * n2)) / Math.sqrt((lambda * lambda) + (1 - lambda) * (1 - lambda));

        if (isNeg) {
            d2 *= -1;
        }

        arr.x = d1;
        arr.y = d2;

        return arr;
    }

    static public Point2D.Double nextCorrelatedUniforms(double r) {
        double d1, d2, n1, n2, lambda;
        Point2D.Double arr = new Point2D.Double();
        boolean isNeg = false;

        if (r < 0) {
            r *= -1;
            isNeg = true;
        }

        lambda = ((r * r) - Math.sqrt(r * r - r * r * r * r)) / (2 * r * r - 1);

        n1 = Globals.GLOBAL_RANDOM.nextDouble();
        n2 = Globals.GLOBAL_RANDOM.nextDouble();

        d1 = n1;
        d2 = ((lambda * n1) + ((1 - lambda) * n2)) / Math.sqrt((lambda * lambda) + (1 - lambda) * (1 - lambda));

        if (isNeg) {
            d2 *= -1;
        }

        arr.x = d1;
        arr.y = d2;

        return arr;
    }

    /**
     * Returns the Pearson's correlation of a 2D distribution of points.
     * <p>
     * Adapted from http://snippets.dzone.com/posts/show/4910
     * 
     * @param scores1	coordinates along the x direction
     * @param scores2	coordinates along the y direction
     * @return			the Pearson's R score
     */
    public static double getPearsonCorrelation(double[] scores1, double[] scores2) {
        double result = 0;
        double sum_sq_x = 0;
        double sum_sq_y = 0;
        double sum_coproduct = 0;
        double mean_x = scores1[0];
        double mean_y = scores2[0];
        for (int i = 2; i < scores1.length + 1; i += 1) {
            double sweep = ((double) i - 1.0) / (double) i;
            double delta_x = scores1[i - 1] - mean_x;
            double delta_y = scores2[i - 1] - mean_y;
            sum_sq_x += delta_x * delta_x * sweep;
            sum_sq_y += delta_y * delta_y * sweep;
            sum_coproduct += delta_x * delta_y * sweep;
            mean_x += delta_x / i;
            mean_y += delta_y / i;
        }
        double pop_sd_x = (double) Math.sqrt(sum_sq_x / scores1.length);
        double pop_sd_y = (double) Math.sqrt(sum_sq_y / scores1.length);
        double cov_x_y = sum_coproduct / scores1.length;
        result = cov_x_y / (pop_sd_x * pop_sd_y);
        return result;
    }

    /**
     * Safely copies a file.
     * <p>
     * Built on top of code adapted from the example at:
     * http://www.exampledepot.com/egs/java.io/CopyFile.html
     *
     * @param inFile
     * @param outFile
     * @return
     */
    public static boolean safeCopyFile(File inFile, File outFile) {

        // failed if the input file doesn't exist, or the output file exists
        if (!inFile.exists()) {
            return false;
        } else if (outFile.exists()) {
            return false;
        }

        try {
            InputStream in = new FileInputStream(inFile);
            OutputStream out = new FileOutputStream(outFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean removeDirectory(File directory) {

        // System.out.println("removeDirectory " + directory);

        if (directory == null) {
            return false;
        }
        if (!directory.exists()) {
            return true;
        }
        if (!directory.isDirectory()) {
            return false;
        }

        String[] list = directory.list();

        // Some JVMs return null for File.list() when the
        // directory is empty.
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                File entry = new File(directory, list[i]);

                //        System.out.println("\tremoving entry " + entry);

                if (entry.isDirectory()) {
                    if (!removeDirectory(entry)) {
                        return false;
                    }
                } else {
                    if (!entry.delete()) {
                        return false;
                    }
                }
            }
        }

        return directory.delete();
    }
}
