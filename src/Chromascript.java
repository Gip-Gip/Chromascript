import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/*
 * Chromascript.java of Chromascript,
 * the high-density paper-based data storage program
 *
 * by Charles Thompson, do not distribute!
 */

public class Chromascript
{
  public static Chromascript chromascript = new Chromascript();
  
  public static int repeatCount = 0;
  public static String baseName = null;
  
  public static final int alignMark[] =
  {
    0xFF000000, 0xFFFFFFFF, 0xFF000000,
    0xFFFFFFFF, 0xFF000000, 0xFFFFFFFF,
  };
  
  public static int[] toColors(byte αbytes[])
  {
    int colors[] = new int[αbytes.length];
    
    for(int ι = 0; ι < αbytes.length; ι++)
    {
      colors[ι] = 0xFF808080;
      colors[ι] |= (αbytes[ι] & 0b11100000) << 15;
      colors[ι] |= (αbytes[ι] & 0b00011100) << 10;
      colors[ι] |= (αbytes[ι] & 0b00000011) << 5;
    }
    
    return colors;
  }
  
  public static boolean file2pageFile()
  {
    boolean repeat = true;
    BufferedImage bufferedImage = new BufferedImage
    (
      WriteTab.getPageWidth(),
      WriteTab.getPageHeight(),
      BufferedImage.TYPE_INT_RGB
    );
    byte readBuffer[] =
      new byte[bufferedImage.getWidth() * (bufferedImage.getHeight() - 2)];    
    byte header[] =
      new byte[(bufferedImage.getWidth() - 6) * 2];
    

    try
    {
      int ι = 0;
      while(ι++ < 256)
      {
        header[ι] = (byte)ι;
      }
      for(byte character : LocalStreams.getInFileName().substring(8).getBytes())
      {
        header[ι++] = character;
      }
    }
    catch(Exception ε)
    {
      GUI.errorMessage(ε);
      
      return false;
    }

    try
    {
      for(int ι = 0; ι < readBuffer.length; ι ++)
        readBuffer[ι] = (byte)LocalStreams.readIn();
    }
    catch (LocalStreams.EOF ε)
    {
      repeat = false;
    }
    catch(Exception ε)
    {
      GUI.errorMessage(ε);
      
      return false;
    }
    
    try
    {
      if(!LocalStreams.isOutOpen())
      {
        GUI.outFileSel();
        baseName = LocalStreams.getOutFileName();
        if(!LocalStreams.getOutFileName().endsWith(".png"))
        {
          String newOutFileName = baseName + ".png";
          String outFileDir = LocalStreams.getOutFileDir();
          LocalStreams.closeOut();
          LocalStreams.setOutFile(newOutFileName, outFileDir);
          baseName = LocalStreams.getOutFileName();
        }
      }
      else
      {
        repeatCount ++;
        String newOutFileName = repeatCount + baseName;
        String outFileDir = LocalStreams.getOutFileDir();
        
        LocalStreams.closeOut();
        LocalStreams.setOutFile(newOutFileName, outFileDir);
      }
      bufferedImage.setRGB
      (
          0,
          2,
          bufferedImage.getWidth(),
          bufferedImage.getHeight() - 2,
          toColors(readBuffer),
          0,
          bufferedImage.getWidth()
      );
      
      bufferedImage.setRGB
      (
        0,
        0,
        3,
        2,
        alignMark,
        0,
        3
      );
      
      bufferedImage.setRGB
      (
        bufferedImage.getWidth() - 3,
        0,
        3,
        2,
        alignMark,
        0,
        3
      );
      
      bufferedImage.setRGB
      (
        3,
        0,
        bufferedImage.getWidth() - 6,
        2,
        toColors(header),
        0,
        bufferedImage.getWidth() - 6
      );
      
      ImageIO.write(bufferedImage, "PNG", LocalStreams.getOutFile());
    }
    catch (Exception ε)
    {
      GUI.errorMessage(ε);
      
      return false;
    }
    
    if(!repeat) repeatCount = 0;
    
    return repeat;
  }
}
