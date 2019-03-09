/*
 * ReadButton.java of Chromascript,
 * the high-density paper-based data storage program
 *
 * by Charles Thompson, do not distribute!
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class ReadButton extends JFrame implements ActionListener
{
  private static final long serialVersionUID = 1L;

  @Override
  public void actionPerformed(ActionEvent αevent)
  {
    try
    {
      Chromascript chromascript = new Chromascript();
      
      chromascript.setPageImage(LocalStreams.getInFile());
      GUI.outFileSel();
      
      for(byte i : chromascript.getData())
      {
        System.out.print((char)i);
      }
      
      LocalStreams.writeOut(chromascript.getData());
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
