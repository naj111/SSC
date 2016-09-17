
package Image_Process;

import Error_Logs.PathConfig;
import static Neural_Network.DetectDisease_NN.DetectDisease;

import static Image_Process.SkinColour.SkinCol;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.RescaleOp;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class ImageProcess {

    public static PathConfig I_PATH = new PathConfig(); // path configuration initialized
    public static String MainPath = (I_PATH.GetPaths("PImage")); // settign the image to the specific path
    public static double impulseRatio = 0.01; // noise removal impulse ration set 
    private static int size = 100;
    private static int pixSize = 1;

    
    //Image segmentation of the right cheek section//
    public static void RightCheek(String Path) throws Exception {

        BufferedImage MainImage = ImageIO.read(new File(Path));
        BufferedImage RightCheek = MainImage.getSubimage(100, 100, 100, 100);
        ImageIO.write(RightCheek, "jpg", new File(MainPath + "RightCheek.jpg"));
        SkinCol(MainPath + "RightCheek.jpg");
        setContrast(MainPath + "RightCheek.jpg");

        //impulseNoise(MainPath + "RightCheek.jpg");
        Grayscale(MainPath + "RightCheek.jpg");
        LoadImage();

    }
    //Image segmentation of the left cheek section//

    public static void LeftCheek(String Path) throws Exception {

        BufferedImage MainImage = ImageIO.read(new File(Path));
        BufferedImage LeftCheek = MainImage.getSubimage(100, 100, 100, 100);
        ImageIO.write(LeftCheek, "jpg", new File(MainPath + "LeftCheek.jpg"));
        SkinCol(MainPath + "LeftCheek.jpg");
    }

    // chanigning the coloured image to grayscale image (black and white)//
    public static void Grayscale(String Path) {

        try {

            BufferedImage ColouImage = ImageIO.read(new File(Path));

            double image_width = ColouImage.getWidth();
            double image_height = ColouImage.getHeight();

            BufferedImage BalckandwhiteImage = null;
            BufferedImage img = ColouImage;

            BalckandwhiteImage = new BufferedImage((int) image_width, (int) image_height,
                    BufferedImage.TYPE_BYTE_BINARY);
            Graphics2D gg = BalckandwhiteImage.createGraphics();
            gg.drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null);

            ImageIO.write(BalckandwhiteImage, "jpg", new File(MainPath + "GrayScalImage.jpg"));

        } catch (Exception e) {
            System.out.println(e);
        }

    }
    
    // adjusting the contrast of the image //
    public static void setContrast(String image) throws IOException {
        BufferedImage tempImage = ImageIO.read(new File(image));

        float brightenFactor = 1.2f; // brightness factor set
        RescaleOp op = new RescaleOp(brightenFactor, -10, null);//??
        tempImage = op.filter(tempImage, null);
        BufferedImage Cheek = tempImage;
        ImageIO.write(Cheek, "jpg", new File(MainPath + " Cheekcontast.jpg"));
    }

    //Image Noise Removal (Impulse noise removal)//
    public static void impulseNoise(String path) throws IOException {
        BufferedImage output = ImageIO.read(new File(path));
        BufferedImage image = ImageIO.read(new File(path));

        output.setData(image.getData());

        Raster source = image.getRaster();
        WritableRaster out = output.getRaster();

        double rand;
        double halfImpulseRatio = impulseRatio / 2.0;
        int bands = out.getNumBands();
        int width = image.getWidth();  // width of the image
        int height = image.getHeight(); // height of the image
        java.util.Random randGen = new java.util.Random();

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                rand = randGen.nextDouble();
                if (rand < halfImpulseRatio) {
                    for (int b = 0; b < bands; b++) {
                        out.setSample(i, j, b, 0);
                    }
                } else if (rand < impulseRatio) {
                    for (int b = 0; b < bands; b++) {
                        out.setSample(i, j, b, 255);
                    }
                }
            }
        }

        ImageIO.write(output, "jpg", new File(MainPath + " noiseremovl.jpg"));

    }

    public static String[] LoadImage() {
        String[] numberArray = null;
        try {

            BufferedImage image = ImageIO.read(new File(MainPath + "GrayScalImage.jpg"));
            numberArray = toStringArray(image);
            // DetectDisease(numberArray);
            int count = 0;
            for (String s : numberArray) {
                System.out.print(s + ",");
                count++;

                if (count == size) {
                    System.out.println("");
                    count = 0;
                }
            }
        } catch (Exception ex) {
            // Logger.getLogger(ImageBinarization.class.getName()).log(Level.SEVERE, null, ex);
        }

        return numberArray;

    }

    private static String[] toStringArray(BufferedImage numberImage) throws Exception {
        String[] arrayRep = new String[size * size];
        try {
            int arrayIndex = 0;
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    BufferedImage subImage = numberImage.getSubimage(col, row, pixSize, pixSize);

                    int num = subImage.getRGB(0, 0);
                    //System.out.println(num);
                    if (num == -1) {
                        arrayRep[arrayIndex] = "0";
                    } else {
                        arrayRep[arrayIndex] = "1";
                    }
                    arrayIndex++;
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return arrayRep;
    }
}
