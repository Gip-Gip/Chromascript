/*
 * ChromascriptButton.java of Chromascript,
 * the high-density paper-based data storage program
 *
 * by Charles Thompson, do not distribute!
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class ChromascriptButton extends JFrame implements ActionListener
{
  private static final long serialVersionUID = 1L;

  @Override
  public void actionPerformed(ActionEvent αevent)
  {
    try
    {
      while(Chromascript.file2pageFile());
    }
    catch (Exception ε)
    {
      GUI.errorMessage(ε);
    }
    try
    {
      LocalStreams.closeOut();
    }
    catch (Exception ε)
    {
      GUI.errorMessage(ε);
    }
  }
  
}
