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
  
  /*When the button's clicked, call the GUI input file selection menu and
   * update the file label */
  @Override
  public void actionPerformed(ActionEvent αevent)
  {
    GUI.inFileSel();
    
    WriteTab.updateFileLabel();
  }
}
