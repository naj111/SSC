
package Process;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;




public class OtusThreshold {
    
    // Return histogram of grayscale image
    private static int[] imageHistogram(BufferedImage input) {
        int[] histogram = new int[256];
        for(int i=0; i<histogram.length; i++) histogram[i] = 0;
            for(int i=0; i<input.getWidth(); i++) {
                for(int j=0; j<input.getHeight(); j++) {
                    int red = new Color(input.getRGB (i, j)).getRed();
                    histogram[red]++;
                }
            }
        return histogram;
    }
    
    // The luminance method
    public static BufferedImage toGray(BufferedImage original) {
 
        int alpha, red, green, blue;
        int newPixel;
 
        BufferedImage lum = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
 
        for(int i=0; i<original.getWidth(); i++) {
            for(int j=0; j<original.getHeight(); j++) {
 
                // Get pixels by R, G, B
                alpha = new Color(original.getRGB(i, j)).getAlpha();
                red = new Color(original.getRGB(i, j)).getRed();
                green = new Color(original.getRGB(i, j)).getGreen();
                blue = new Color(original.getRGB(i, j)).getBlue();
                red = (int) (0.21 * red + 0.71 * green + 0.07 * blue);
                // Return back to original format
                newPixel = colorToRGB(alpha, red, red, red);
                // Write pixels into image
                lum.setRGB(i, j, newPixel);
            }
        }
        return lum;
    }
    
    // Get binary treshold using Otsu's method
    private static int otsuTreshold(BufferedImage original) {
 
        int[] histogram = imageHistogram(original);
        int total = original.getHeight() * original.getWidth();
 
        float sum = 0;
        for(int i=0; i<256; i++) sum += i * histogram[i];
 
        float sumB = 0;
        int wB = 0;
        int wF = 0;
 
        float varMax = 0;
        int threshold = 0;
 
        for(int i=0 ; i<256 ; i++) {
            wB += histogram[i];
            if(wB == 0) continue;
            wF = total - wB;
 
            if(wF == 0) break;
 
            sumB += (float) (i * histogram[i]);
            float mB = sumB / wB;
            float mF = (sum - sumB) / wF;
 
            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);
 
            if(varBetween > varMax) {
                varMax = varBetween;
                threshold = i;
            }
        }
        return threshold;
    }
    
    public static BufferedImage binarize(BufferedImage original) {
 
        int red;
        int newPixel;
        int threshold = otsuTreshold(original);
        BufferedImage binarized = 
                new BufferedImage(original.getWidth(),
                        original.getHeight(),
                        original.getType());
        for(int i=0; i<original.getWidth(); i++) {
            for(int j=0; j<original.getHeight(); j++) {
 
                // Get pixels
                red = new Color(original.getRGB(i, j)).getRed();
                int alpha = new Color(original.getRGB(i, j)).getAlpha();
                if(red > threshold) {
                    newPixel = 255;
                }
                else {
                    newPixel = 0;
                }
                newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);
                binarized.setRGB(i, j, newPixel);
            }
        }
 
        return binarized;
 
    }
 
    // Convert R, G, B, Alpha to standard 8 bit
    private static int colorToRGB(int alpha, int red, int green, int blue) {
 
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red; newPixel = newPixel << 8;
        newPixel += green; newPixel = newPixel << 8;
        newPixel += blue;
 
        return newPixel;
 
    }
    
    public static BufferedImage gaussianBlur(BufferedImage image,double sigma) {

        int height = image.getHeight(null);
        int width = image.getWidth(null);
        BufferedImage tempImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        BufferedImage filteredImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        //--->>
        int n = (int) (6 * sigma + 1);
        double[] window = new double[n];
        double s2 = 2 * sigma * sigma;
        window[(n - 1) / 2] = 1;
        for (int i = 0; i < (n - 1) / 2; i++) {
            window[i] = Math.exp((double) (-i * i) / (double) s2);
            window[n - i - 1] = window[i];
        }

        //--->>
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double sum = 0;
                double[] colorRgbArray = new double[]{0, 0, 0};
                for (int k = 0; k < window.length; k++) {
                    int l = i + k - (n - 1) / 2;
                    if (l >= 0 && l < width) {
                        Color imageColor = new Color(image.getRGB(l, j));
                        colorRgbArray[0] = colorRgbArray[0] + imageColor.getRed() * window[k];
                        colorRgbArray[1] = colorRgbArray[1] + imageColor.getGreen() * window[k];
                        colorRgbArray[2] = colorRgbArray[2] + imageColor.getBlue() * window[k];
                        sum += window[k];
                    }
                }
                for (int t = 0; t < 3; t++) {
                    colorRgbArray[t] = colorRgbArray[t] / sum;
                }
                Color tmpColor = new Color((int) colorRgbArray[0], (int) colorRgbArray[1], (int) colorRgbArray[2]);
                tempImage.setRGB(i, j, tmpColor.getRGB());
            }
        }

        //--->>
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double sum = 0;
                double[] colorRgbArray = new double[]{0, 0, 0};
                for (int k = 0; k < window.length; k++) {
                    int l = j + k - (n - 1) / 2;
                    if (l >= 0 && l < height) {
                        Color imageColor = new Color(tempImage.getRGB(i, l));
                        colorRgbArray[0] = colorRgbArray[0] + imageColor.getRed() * window[k];
                        colorRgbArray[1] = colorRgbArray[1] + imageColor.getGreen() * window[k];
                        colorRgbArray[2] = colorRgbArray[2] + imageColor.getBlue() * window[k];
                        sum += window[k];
                    }
                }
                for (int t = 0; t < 3; t++) {
                    colorRgbArray[t] = colorRgbArray[t] / sum;
                }
                Color tmpColor = new Color((int) colorRgbArray[0], (int) colorRgbArray[1], (int) colorRgbArray[2]);
                filteredImage.setRGB(i, j, tmpColor.getRGB());
            }
        }
        return filteredImage;
    }
    
    public static BufferedImage setContras(BufferedImage image){
        BufferedImage tempImage = image;
        float brightenFactor = 1.2f;
        RescaleOp op = new RescaleOp(brightenFactor, -10, null);
        tempImage = op.filter(image, tempImage);
        return tempImage ;
    }
    
    public static BufferedImage invertImage(BufferedImage image) {
        BufferedImage inputFile = image;
        for (int x = 0; x < inputFile.getWidth(); x++) {
            for (int y = 0; y < inputFile.getHeight(); y++) {
                int rgba = inputFile.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(),
                                255 - col.getGreen(),
                                255 - col.getBlue());
                inputFile.setRGB(x, y, col.getRGB());
            }
        }
        return image;
    }
    

}
