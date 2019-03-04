/*
 * Chromascript.java of Chromascript,
 * the high-density paper-based data storage program
 *
 * by Charles Thompson, do not distribute!
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

public class Chromascript
{
  private int id[];
  private int idWidth;
  private final byte idHeight = 2;
  private byte data[];
  private int dataSize;
  private long dataLength;
  private int width;
  private int dataWidth;
  private int height;
  private int dataHeight;
  private int lMargin;
  private int rMargin;
  private int tMargin;
  private int bMargin;
  private BufferedImage pageImage;
  
  private class IdSquare
  {
    public int pointAX;
    public int pointAY;
    public int pointBX;
    public int pointBY;
    
    public double getDiagonal()
    {
      return Math.hypot(pointBX-pointAX, pointBY-pointAY);
    }
    
    public double getSide()
    {
      return Math.sqrt(Math.pow(getDiagonal(), 2) / 2);
    }
    
    public String toString()
    {
      return Integer.toString(pointAX) + " " + Integer.toString(pointAY)
      + " " + Integer.toString(pointBX) + " " + Integer.toString(pointBY)
      + " " + Double.toString(getDiagonal())+ " " + Double.toString(getSide());
    }
  }
  
  byte[] getData()
  {
    calcData();
    return data;
  }
  
  int[] getRgbData()
  {
    return mkRgbData(data);
  }
  
  BufferedImage getPageImage() throws Exception
  {
    calcPageImage();
    return pageImage;
  }
  
  void setData(File αfile) throws Exception
  {
    calcDataSize();
    FileInputStream inStream = new FileInputStream(αfile);
    dataLength = αfile.length();
    data = new byte[dataSize];
    inStream.read(data);
    inStream.close();
  }
  
  void setPageImage(File αfile) throws Exception
  {
    pageImage = ImageIO.read(αfile);
  }
  
  void setWidth(int αwidth)
  {
    width = αwidth;
  }
  
  void setHeight(int αheight)
  {
    height = αheight;
  }
  
  void setLMargin(int αlMargin)
  {
    lMargin = αlMargin + 1;
  }
  
  void setRMargin(int αrMargin)
  {
    rMargin = αrMargin + 1;
  }
  
  void setTMargin(int αtMargin)
  {
    tMargin = αtMargin + idHeight + 1;
  }
  
  void setBMargin(int αbMargin)
  {
    bMargin = αbMargin + 1;
  }
  
  void setMargins(int αinchTenths, int αdpi)
  {
    int marginLength = αinchTenths * αdpi;
    
    marginLength /= 10;
    
    setLMargin(marginLength);
    setRMargin(marginLength);
    setTMargin(marginLength);
    setBMargin(marginLength);
  }
  
  private int meanBrightness(int rgb)
  {
    int brightness = (rgb & 0xFF) + ((rgb >>= 8) & 0xFF) + ((rgb >>= 8) & 0xFF);
    brightness /= 3;
    
    return brightness;
  }
  
  void calcData()
  {
    IdSquare idOne = new IdSquare();
    IdSquare idTwo = new IdSquare();
    
    int darkest = 0xFFFFFF;
    int lightest = 0x000000;
    int range;
    
    for(int y = 0; y < pageImage.getHeight(); y++)
    {
      for(int x = 0; x < pageImage.getWidth(); x++)
      {
        darkest = Math.min(darkest, meanBrightness(pageImage.getRGB(x, y) & 0xFFFFFF));
        lightest = Math.max(lightest, meanBrightness(pageImage.getRGB(x, y) & 0xFFFFFF));
      }
    }
    
    range = lightest - darkest;
    
    for(int y = 0; y < pageImage.getHeight(); y++)
    {
      for(int x = 0; x < pageImage.getWidth(); x++)
      {
        if((meanBrightness(pageImage.getRGB(x, y) & 0xFFFFFF)) < (range / 4 + darkest))
        {
          idOne.pointAX = x;
          idOne.pointAY = y;
        }
      }
    }
    
    idOne.pointBX = idOne.pointAX;
    idOne.pointBY = idOne.pointAY;
    
    while((meanBrightness(pageImage.getRGB(idOne.pointBX, idOne.pointBY) & 0xFFFFFF)) < (range / 4 + darkest))
    {
      if((meanBrightness(pageImage.getRGB(idOne.pointBX + 1, idOne.pointBY) & 0xFFFFFF)) < (range / 4 + darkest))
        idOne.pointBX++;
      
      else if((meanBrightness(pageImage.getRGB(idOne.pointBX, idOne.pointBY + 1) & 0xFFFFFF)) < (range / 4 + darkest))
        idOne.pointBY++;
      
      else
      {
        idOne.pointBX ++;
        idOne.pointBY ++;
      }
    }
    
    System.out.print(idOne);
  }
  
  void calcDataSize()
  {
    calcDataWidth();
    calcDataHeight();
    dataSize = dataWidth * dataHeight;
    dataSize /= 2;
  }
  
  int[] mkRgbData(byte data[])
  {
    int rgbData[] = new int[data.length * 2];
    
    for(int ι = 0; ι < data.length; ι++)
    {
      rgbData[ι*2] = 0xFF3F3F3F;
      rgbData[ι*2] |= ((data[ι] & 0b10000000) > 0 ? 0 : 0x00400000);
      rgbData[ι*2] |= ((data[ι] & 0b01000000) > 0 ? 0 : 0x00004000);
      rgbData[ι*2] |= ((data[ι] & 0b00100000) > 0 ? 0 : 0x00000040);
      rgbData[ι*2] |= ((data[ι] & 0b00010000) > 0 ? 0 : 0x00808080);
      
      rgbData[ι*2+1] = 0xFF3F3F3F;
      rgbData[ι*2+1] |= ((data[ι] & 0b00001000) > 0 ? 0 : 0x00400000);
      rgbData[ι*2+1] |= ((data[ι] & 0b00000100) > 0 ? 0 : 0x00004000);
      rgbData[ι*2+1] |= ((data[ι] & 0b00000010) > 0 ? 0 : 0x00000040);
      rgbData[ι*2+1] |= ((data[ι] & 0b00000001) > 0 ? 0 : 0x00808080);
    }
    
    return rgbData;
  }
  
  void calcDataWidth()
  {
    dataWidth = width - lMargin - rMargin;
  }
  
  void calcDataHeight()
  {
    dataHeight = height - tMargin - bMargin;
  }
  
  void calcId() throws Exception
  {
    int ι = 0;
    int fileSize[] = 
        mkRgbData((Long.toString(dataLength) + "\0").getBytes());
    int fileName[] =
        mkRgbData((LocalStreams.getInFileName() + "\0").getBytes());
    
    idWidth = dataWidth;
    id = new int[idWidth * idHeight];
    
    id[ι] = 0xFF000000;
    
    while((++ι) < 17)
    {
      id[ι] = 0xFF3F3F3F;
      id[ι] |= ((16-ι & 0b1000) > 0 ? 0 : 0x00400000);
      id[ι] |= ((16-ι & 0b0100) > 0 ? 0 : 0x00004000);
      id[ι] |= ((16-ι & 0b0010) > 0 ? 0 : 0x00000040);
      id[ι] |= ((16-ι & 0b0001) > 0 ? 0 : 0x00808080);
    }
    
    for(int character : fileSize)
    {
      id[ι++] = character; 
    }
    
    for(int character : fileName)
    {
      if(ι < idWidth-2) id[ι++] = character;
    }
    
    if(ι == idWidth-2) throw new Exception("Filename too long!");
    
    while(ι < idWidth-1) id[ι++] = 0xFFFFFFFF;
    
    id[ι] = 0xFF000000;
    
    while((++ι) < id.length) id[ι] = 0xFFFFFFFF;
  }
  
  void calcPageImage() throws Exception
  {
    pageImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    
    for(int x = 0; x < width; x++)
    {
      for(int y = 0; y < height; y++)
      {
        pageImage.setRGB(x, y, 0xFFFFFFFF);
      }
    }
    
    calcId();
    
    pageImage.setRGB
      (lMargin, tMargin - idHeight, idWidth, idHeight, id, 0, idWidth);
    
    pageImage.setRGB
      (lMargin, tMargin, dataWidth, dataHeight, getRgbData(), 0, dataWidth);
    
    calcData();
  }
}
