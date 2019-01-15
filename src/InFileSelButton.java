/*
 * InFileSelButton.java of Chromascript,
 * the high-density paper-based data storage program
 *
 * by Charles Thompson, do not distribute!
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class InFileSelButton extends JFrame implements ActionListener
{
  private static final long serialVersionUID = 1L;
  
  @Override
  public void actionPerformed(ActionEvent αevent)
  {
    try
    {
      LocalStreams.closeIn();
    }
    catch (Exception ε)
    {
      GUI.errorMessage(ε);
    }
    
    GUI.inFileSel();
    
    WriteTab.updateFileLabel();
  }
}
