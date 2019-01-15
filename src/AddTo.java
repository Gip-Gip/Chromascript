/*
 * AddTo.java of Chromascript,
 * the high-density paper-based data storage program
 *
 * by Charles Thompson, do not distribute!
 */

public class AddTo
{
  public static byte[] array(byte αarray[], long αlong)
  {
    byte array[] = new byte[αarray.length + 1];
    
    for(int index = 0; index < αarray.length; index++)
    {
      array[index] = αarray[index];
    }
    
    array[αarray.length] = (byte)αlong;
    
    return array;
  }
}
