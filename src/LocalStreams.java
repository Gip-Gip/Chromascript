/*
 * LocalStreams.java of Chromascript,
 * the high-density paper-based data storage program
 *
 * by Charles Thompson, do not distribute!
 */

import java.io.*;

public class LocalStreams
{
  /* Input File */
  private static File inFile = null;
  /* Input file's stream */
  private static FileInputStream inFileStream = null;
  /* Output file */
  private static File outFile = null;
  /* Output file's stream */
  private static FileOutputStream outFileStream = null;
  
  /* Input file's name */
  private static String inFileName = null;
  /* Input file's parent directory */
  private static String inFileDir = null;
  /* Input file's size */
  private static long inFileSize = -1;
  /* Output file's name */
  private static String outFileName = null;
  /* Output file's parent directory */
  private static String outFileDir = null;
  
  public class EOF extends Throwable
  {
    private static final long serialVersionUID = 1L;
  }
  
  public class NoFileSel extends Exception
  {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString()
    {
      return "No File Selected!";
    }
  }
  
  private static LocalStreams localStreams = new LocalStreams();
  private static EOF eof = localStreams.new EOF();
  private static NoFileSel noFileSel = localStreams.new NoFileSel();
  
  public static void setInFile(String αname, String αdir) throws Exception
  {
    inFile = new File(αdir + αname);
    inFileStream = new FileInputStream(inFile);
    inFileSize = inFile.length();
    inFileName = αname;
    inFileDir = αdir;
  }
  
  public static void setOutFile(String αname, String αdir) throws Exception
  {
    outFile = new File(αdir + αname);
    if(outFile.exists()) outFile.delete();
    outFile.createNewFile();
    outFileStream = new FileOutputStream(outFile);
    outFileName = αname;
    outFileDir = αdir;
  }
  
  public static String getInFileName() throws Exception
  {
    if(inFileName != null) return inFileName;
    throw(new Exception("No file selected!"));
  }
  
  public static String getInFileDir() throws Exception
  {
    if(inFileDir != null) return inFileDir;
    throw(new Exception("No file selected"));
  }
  
  public static long getInFileSize() throws Exception
  {
    if(inFileSize != -1) return inFileSize;
    throw(noFileSel);
  }
  
  public static File getInFile() throws Exception
  {
    if(inFile != null) return inFile;
    throw(noFileSel);
  }
  
  public static String getOutFileName() throws Exception
  {
    if(outFileName != null) return outFileName;
    throw(noFileSel);
  }
  
  public static String getOutFileDir() throws Exception
  {
    if(outFileDir != null) return outFileDir;
    throw(noFileSel);
  }
  
  public static File getOutFile() throws Exception
  {
    if(outFile != null) return outFile;
    throw(noFileSel);
  }
  
  public static short readIn() throws Exception, EOF
  {
    if(inFileStream == null) throw(noFileSel);
    short readVal = (short)inFileStream.read();
    
    if(readVal == -1) throw(eof);
    
    return readVal;
  }
  
  public static boolean isInOpen()
  {
    return (inFile != null);
  }
  
  public static boolean isOutOpen()
  {
    return (outFile != null);
  }
  
  public static void writeOut(byte αbyte) throws Exception
  {
    if(outFileStream == null) throw(noFileSel);
    outFileStream.write(αbyte);
  }
  
  public static void closeIn() throws Exception
  {
    if(inFile != null) inFileStream.close();
    inFile = null;
    inFileStream = null;
    inFileName = null;
    inFileDir = null;
    inFileSize = -1;
  }
  
  public static void closeOut() throws Exception
  {
    if(outFile != null) outFileStream.close();
    outFile = null;
    outFileStream = null;
    outFileName = null;
    outFileDir = null;
  }
  
  public static void closeAll() throws Exception
  {
    closeIn();
    closeOut();
  }
}
