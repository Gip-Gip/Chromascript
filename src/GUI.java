/*
 * GUI.java of Chromascript,
 * the high-density paper-based data storage program
 *
 * by Charles Thompson, do not distribute!
 */

import java.awt.*;
import javax.swing.*;

public class GUI
{
  private static Frame frame = null;
  private static JTabbedPane tabPane = null;
  private static Dimension frameSize = null;
  private static Dimension screenSize = null;
  private static int size = 0;
  private static final String frameName = "Chromascript " + Main.version;
  public static final String fileDialogTitle = "Choose a file to open";
  
  public static int getSize()
  {
    return size;
  }
  
  public static Frame getFrame()
  {
    return frame;
  }
  
  public static void errorMessage(Exception αε)
  {
    αε.printStackTrace();
    System.err.println("Error! " + αε.toString());
    JOptionPane.showMessageDialog
      (null, αε.toString(), "Error!", JOptionPane.ERROR_MESSAGE);
  }
  
  public static Component βsetSize
    (Component αcomponent, double αwidth, double αheight)
  {
    αcomponent.setSize((int)(size/16*αwidth), (int)(size/16*αheight));
    αcomponent.setPreferredSize(αcomponent.getSize());
    αcomponent.setMaximumSize(αcomponent.getSize());
    αcomponent.setMinimumSize(αcomponent.getSize());
    return αcomponent;
  }
  
  public static Panel setSize(Panel αpanel, double αwidth, double αheight)
  {
    return (Panel)βsetSize(αpanel, αwidth, αheight);
  }
  
  public static Label setSize(Label αlabel, double αwidth, double αheight)
  {
    return (Label)βsetSize(αlabel, αwidth, αheight);
  }
  
  public static Button setSize(Button αbutton, double αwidth, double αheight)
  {
    return (Button)βsetSize(αbutton, αwidth, αheight);
  }
  
  public static JComboBox<String> setSize
    (JComboBox<String> αjComboBox, double αwidth, double αheight)
  {
    return (JComboBox<String>)βsetSize(αjComboBox, αwidth, αheight);
  }
  
  public static JProgressBar setSize
    (JProgressBar αjProgressBar, double αwidth, double αheight)
  {
    return (JProgressBar)βsetSize(αjProgressBar, αwidth, αheight);
  }
  
  private interface FileSelFunc
  {
    public void run(String αname, String αdir) throws Exception;
  }
  
  public static void fileSel(FileSelFunc αfileSelFunc, int αdlgType)
  {
    FileDialog fileDialog = new FileDialog(GUI.getFrame());
    
    fileDialog.setMode(αdlgType);
    fileDialog.setTitle(fileDialogTitle);
    fileDialog.setVisible(true);
    
    if(fileDialog.getFile() != null) try
    {
      αfileSelFunc.run(fileDialog.getFile(), fileDialog.getDirectory());
    }
    catch(Exception ε)
    {
      GUI.errorMessage(ε);
    }
  }
  
  public static void inFileSel()
  {
    fileSel
    (
      (String αname, String αdir) -> LocalStreams.setInFile(αname, αdir),
      FileDialog.LOAD
    );
  }
  
  public static void outFileSel()
  {
    fileSel
    (
      (String αname, String αdir) -> LocalStreams.setOutFile(αname, αdir),
      FileDialog.SAVE
    );
  }
  
  public static void start()
  {
    screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    size = (int)(Math.min(screenSize.width, screenSize.height) * 0.8);
    frame = new Frame(frameName);
    frameSize = new Dimension(size, size);
    tabPane = new JTabbedPane();
    
    frame.addWindowListener(new WindowEvents());
    frame.setSize(frameSize);
    frame.add(tabPane);
    
    tabPane.addTab(WriteTab.getTitle(), new WriteTab());
    tabPane.addTab(ReadTab.getTitle(), new ReadTab());
    
    frame.setResizable(false);
    frame.setVisible(true);
  }
}
