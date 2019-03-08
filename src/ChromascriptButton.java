/*
 * ChromascriptButton.java of Chromascript,
 * the high-density paper-based data storage program
 *
 * by Charles Thompson, do not distribute!
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class ChromascriptButton extends JFrame implements ActionListener
{
  private static final long serialVersionUID = 1L;

  @Override
  public void actionPerformed(ActionEvent αevent)
  {
    try
    {
      Chromascript chromascript = new Chromascript();
      chromascript.setWidth(WriteTab.getPageWidth());
      chromascript.setHeight(WriteTab.getPageHeight());
      chromascript.setMargins(5, WriteTab.getDPI());
      chromascript.setData(LocalStreams.getInFile());
      GUI.outFileSel();
      ImageIO.write
        (chromascript.getPageImage(), "PNG", LocalStreams.getOutFile());
    }
    catch (Exception ε)
    {
      GUI.errorMessage(ε);
    }
    
    try
    {
      /* After all that close the output file */
      LocalStreams.closeOut();
    }
    catch (Exception ε)
    {
      GUI.errorMessage(ε);
    }
  }
  
}
