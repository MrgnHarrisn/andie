package cosc202.andie;

import java.awt.image.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.awt.Color;

/**
 * <p>
 * ImageOperation to alter the number of colors used in an Image through a
 * k-means clustering algorithim
 * </p>
 * 
 * @author David Brockbank
 * @version 1.0
 */
public class PosteriseKMeansFilter implements ImageOperation, java.io.Serializable {

    private int k;
    private final Random R = new Random();
    private int[] lookUpTable, prevLUT;
    private Cluster[] clusters;

    public PosteriseKMeansFilter(int k) {
        this.k = k;
    }

    /**
     * <p>
     * Apply the specified amount of posterise to the image using k-means clustering
     * algorithm
     * </p>
     * 
     * @param input The image to alter the contrast on
     * @return The resulting posterised image.
     */
    public BufferedImage apply(BufferedImage img) {

        try {

            int height = img.getTileHeight();
            int width = img.getTileWidth();

            lookUpTable = new int[height * width];
            prevLUT = new int[height * width];
            Arrays.fill(prevLUT, -1);

            boolean stable = false;
            boolean firstRound = true;
            initializeClusters(height, width, img);

            while (!stable) {
                int i = 0;
                stable = true;

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        cluster(new Color(img.getRGB(x, y)), firstRound, i);
                        if (lookUpTable[i] != prevLUT[i]) {// if different
                            stable = false;
                        }
                        prevLUT[i] = lookUpTable[i];
                        i++;
                    }
                }
                firstRound = false;
                for (Cluster cluster : clusters) {
                    cluster.recalculateCentroid();
                }
            }

            int i = 0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    img.setRGB(x, y, clusters[lookUpTable[i++]].getMeanColor().getRGB());
                }
            }
        } catch (NullPointerException e) {
            System.out.println(e);
            DialogBox.dialogBox("Need an image to apply filter to");

        }
        return img;

    }

    /**
     * Finds the cluster a given color is closest to
     * 
     * @param color      the given color
     * @param firstRound true if it is the first round of clustering
     * @param i          the linear index of the color
     */
    private void cluster(Color color, boolean firstRound, int i) {
        double minDistance, distance;
        int indexClosest = 0;

        minDistance = Double.MAX_VALUE;

        for (int j = 0; j < clusters.length; j++) {
            distance = clusters[j].distance(color);
            if (distance < minDistance) {
                indexClosest = j;
                minDistance = distance;
            }
        }
        if (!clusters[indexClosest].hasColor(color) && !firstRound) {
            clusters[lookUpTable[i]].removeColor(color);
        }
        clusters[indexClosest].addColor(color);
        lookUpTable[i] = indexClosest;

    }

    /**
     * Checks that the centroid chosen has not all ready been chosen
     * 
     * @param centroid     the centroid to check
     * @param chosenPoints the array to check in
     * @param length       the amount of pixels there are to choose from
     * @return a centroid that is not already chosen
     */
    private int checkDuplicateCentroid(int centroid, int[] chosenPoints, int length) {
        for (int point : chosenPoints) {
            if (centroid == point) {
                return checkDuplicateCentroid(R.nextInt(length), chosenPoints, length);
            }
        }
        return centroid;
    }

    /**
     * Initializes the Clusters with k random pixels. Each pixels color is the
     * initial mean of its cluster
     * 
     * @param height the height of the image
     * @param width  the width of the image
     * @param img    the image
     */
    private void initializeClusters(int height, int width, BufferedImage img) {
        clusters = new Cluster[k];

        int[] chosenPoints = new int[k];
        int length = height * width;

        for (int i = 0; i < k; i++) {
            chosenPoints[i] = checkDuplicateCentroid(R.nextInt(length), chosenPoints, length);
            clusters[i] = new Cluster(
                    new Color(img.getRGB((chosenPoints[i] % width), ((chosenPoints[i] - 1) / width))));
        }

    }

    /**
     * <p>
     * A class to represent a cluster that contains multiple colors
     * </p>
     * 
     * @author David Brockbank
     * 
     * @version 1.0
     */
    private class Cluster {
        private int rTotal, gTotal, bTotal, rMean, gMean, bMean;
        private ArrayList<Color> colors = new ArrayList<Color>();

        /**
         * Constructor to init the cluster with the given centroid
         * 
         * @param color the given centroid
         */
        public Cluster(Color color) {
            rTotal = 0;
            gTotal = 0;
            bTotal = 0;
            addColor(color);
            recalculateCentroid();
        }

        /**
         * Adds a color to the cluster
         * 
         * @param color the color to be added
         */
        public void addColor(Color color) {
            rTotal += color.getRed();
            gTotal += color.getGreen();
            bTotal += color.getBlue();
            colors.add(color);
        }

        /**
         * Removes a color from the cluster
         * 
         * @param color the color to be removed
         */
        public void removeColor(Color color) {
            rTotal -= color.getRed();
            gTotal -= color.getGreen();
            bTotal -= color.getBlue();
            colors.remove(color);
        }

        /**
         * Recalculates the mean (centroid) of the cluster. This is to be done after
         * every "round" of clustering
         */
        public void recalculateCentroid() {
            rMean = rTotal / getSize();
            gMean = gTotal / getSize();
            bMean = bTotal / getSize();
        }

        public boolean hasColor(Color color) {
            return colors.contains(color);
        }

        /**
         * Calculates the distance between a color and this clusters mean color
         * 
         * @param color the color point
         * @return the distance between the given color an the mean of this cluster
         */
        public double distance(Color color) {
            double distance;
            int rDiff = color.getRed() - rMean;
            int gDiff = color.getGreen() - gMean;
            int bDiff = color.getBlue() - bMean;

            distance = Math.sqrt(Math.pow(rDiff, 2) + Math.pow(gDiff, 2) + Math.pow(bDiff, 2));
            return distance;
        }

        /**
         * Gets the centroid of this cluster
         * 
         * @return the mean color (centroid) of this cluster
         */
        public Color getMeanColor() {
            return new Color(rMean, gMean, bMean);
        }

        /**
         * Gets the size of this cluster
         * 
         * @return The size of this cluster
         */
        public int getSize() {
            return colors.size();
        }

        /**
         * To string method
         * 
         * @return a string representation of this cluster
         */
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Color color : colors) {
                sb.append(color);
            }

            return sb.toString();
        }

    }

}
