
package Process;

import static Process.ProcessImage.printLetter;
import static Process.ProcessImage.processImage;
import static Process.SkinColour.ImageInput;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class Segmentation {
    
     public static void ForHead(String Path) throws Exception{
        
        BufferedImage MainImage = ImageIO.read(new File(Path));
        BufferedImage ForHead = MainImage.getSubimage(100, 100, 100, 100);
        File FHOut = new File("C:\\Users\\Naj\\Desktop\\FinalProgram\\program-naj\\Images\\ForHead.jpg");
        ImageIO.write(ForHead, "jpg", FHOut);
        String CPath="C:\\Users\\Naj\\Desktop\\FinalProgram\\program-naj\\Images\\ForHead.jpg";
        ImageInput(CPath);
    }
    
     
     
      
      public static void RightCheek(String Path) throws Exception{
        
        BufferedImage MainImage = ImageIO.read(new File(Path));
        BufferedImage RightCheek = MainImage.getSubimage(100, 100, 100, 100);
        File RCOut = new File("C:\\Users\\Naj\\Desktop\\FinalProgram\\program-naj\\Images\\RightCheek.jpg");
        ImageIO.write(RightCheek, "jpg", RCOut);
    
    }
    
      
      public static void LeftCheek(String Path) throws Exception{
        
        BufferedImage MainImage = ImageIO.read(new File(Path));
        BufferedImage LeftCheek = MainImage.getSubimage(100, 100, 100, 100);
        File LCOut = new File("C:\\Users\\Naj\\Desktop\\FinalProgram\\program-naj\\Images\\LeftCheek.jpg");
        ImageIO.write(LeftCheek, "jpg", LCOut);
    
    }
}
