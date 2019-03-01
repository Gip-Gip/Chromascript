/*
 * Chromascript.java of Chromascript,
 * the high-density paper-based data storage program
 *
 * by Charles Thompson, do not distribute!
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

public class Chromascript
{
  private int id[] = new int[36];
  private final byte idWidth = 18;
  private final byte idHeight = 2;
  private byte data[];
  private int dataSize;
  private int rgbData[];
  private int width;
  private int dataWidth;
  private int height;
  private int dataHeight;
  private int lMargin;
  private int rMargin;
  private int tMargin;
  private int bMargin;
  private BufferedImage pageImage;
  
  int[] getRgbData()
  {
    calcRgbData();
    return rgbData;
  }
  
  BufferedImage getPageImage()
  {
    calcPageImage();
    return pageImage;
  }
  
  void setData(File αfile) throws Exception
  {
    calcDataSize();
    FileInputStream inStream = new FileInputStream(αfile);
    data = new byte[dataSize];
    inStream.read(data);
    inStream.close();
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
  
  void calcDataSize()
  {
    calcDataWidth();
    calcDataHeight();
    dataSize = dataWidth * dataHeight;
    dataSize /= 2;
  }
  
  void calcRgbData()
  {
    rgbData = new int[data.length * 2];
    
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
  }
  
  void calcDataWidth()
  {
    dataWidth = width - lMargin - rMargin;
  }
  
  void calcDataHeight()
  {
    dataHeight = height - tMargin - bMargin;
  }
  
  void calcId()
  {
    int ι = 0;
    
    id[ι] = 0xFF000000;
    
    while((++ι) < 17)
    {
      id[ι] = 0xFF3F3F3F;
      id[ι] |= ((16-ι & 0b1000) > 0 ? 0 : 0x00400000);
      id[ι] |= ((16-ι & 0b0100) > 0 ? 0 : 0x00004000);
      id[ι] |= ((16-ι & 0b0010) > 0 ? 0 : 0x00000040);
      id[ι] |= ((16-ι & 0b0001) > 0 ? 0 : 0x00808080);
    }
    
    id[ι] = 0xFF000000;
    
    while((++ι) < id.length) id[ι] = 0xFFFFFFFF;
  }
  
  void calcPageImage()
  {
    pageImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    
    calcRgbData();
    
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
      (lMargin, tMargin, dataWidth, dataHeight, rgbData, 0, dataWidth);
  }
}
