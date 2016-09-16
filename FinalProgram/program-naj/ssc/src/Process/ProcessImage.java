
package Process;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class ProcessImage {
    
    //Image genaralization value for widgth and heigth
    private static int size =28;
    private static int pixSize=1;
    
    
        public static void CropImage() throws Exception{
        
        BufferedImage MainImage = ImageIO.read(new File("C:\\Users\\Naj\\Desktop\\FinalProgram\\program-naj\\Images\\Main.jpg"));
        BufferedImage RightCheek = MainImage.getSubimage(100, 100, 100, 100);
        File Rout = new File("C:\\Users\\Naj\\Desktop\\FinalProgram\\program-naj\\Images\\RC.jpg");
        BufferedImage LeftCheek = MainImage.getSubimage(100, 100, 100, 100);
        File Lout = new File("C:\\Users\\Naj\\Desktop\\FinalProgram\\program-naj\\Images\\LC.jpg");
        ImageIO.write(RightCheek, "jpg", Rout);
        ImageIO.write(LeftCheek, "jpg", Lout);
        String ToProcess1 = "C:\\Users\\Naj\\Desktop\\FinalProgram\\program-naj\\Images\\RC.jpg";
       // String ToProcess2 = "C:\\Users\\SC\\Desktop\\sajithTestImages\\LC.jpg";
        String[] imgPix1 = processImage(ToProcess1);
      //  String[] imgPix2 = ProcessImage.processImage(ToProcess2);
         printLetter(imgPix1);
    }
     
   /////////////////////////////     
      public static void printLetter(String[] letterPixels)
    {
        int indexCount = 0;
        int indexMax=27;
       
        for(int index =0 ;index<letterPixels.length;index++){
            if(indexCount==indexMax){
               // System.out.println(letterPixels[index]);
                indexCount=0;
            }else{
              
                System.out.print(letterPixels[index]+",");
                indexCount++;
            }
        }
    }
   /////////////////////  
    public static String[] processImage(String filePath) throws IOException{
        BufferedImage image = getImage(filePath);
        
        BufferedImage grayImage = OtusThreshold.toGray(image);
        grayImage = OtusThreshold.invertImage(grayImage);
        BufferedImage thresholdImage = OtusThreshold.binarize(grayImage);
        BufferedImage finalImage = OtusThreshold.setContras(thresholdImage);
        String[] numberRep = toStringArray(finalImage);
        
        return numberRep;
    }
    
    private static BufferedImage getImage(String filePath){
        try{
            BufferedImage rawImage = ImageIO.read(new File(filePath));
            int imageType = rawImage.getType();
            
            BufferedImage genImage = new BufferedImage(size, size, imageType);
            Graphics2D graphics = genImage.createGraphics();
            graphics.drawImage(rawImage,0,0,size,size,null);
            graphics.dispose();
            return OtusThreshold.gaussianBlur(genImage, 0.35);
        }
        catch(Exception e){
            System.err.println(e.toString());
        }
        return null;
    }
    
    private static String[] toStringArray(BufferedImage numberImage){
        String[] arrayRep = new String[size*size];
        
        try{
            int arrayIndex =0;
            for(int row=0;row<28;row++){
                for(int col=0;col<28;col++){
                    BufferedImage subImage = numberImage.getSubimage(col,row,pixSize,pixSize);
                    int num = subImage.getRGB(0, 0);
                    if(num==-1){
                        arrayRep[arrayIndex]="1";
                    }
                    else{
                        arrayRep[arrayIndex]="0";
                    }
                    arrayIndex++;
                }
            }
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return arrayRep;
    }
    
}
