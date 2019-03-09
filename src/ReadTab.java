/*
 * ReadTab.java of Chromascript,
 * the high-density paper-based data storage program
 *
 * by Charles Thompson, do not distribute!
 */

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;

public class ReadTab extends Panel
{
  /* Variable that makes eclipse shut up */
  private static final long serialVersionUID = 1L;
  
  /* Title of tab*/
  private static final String title = "Read a Chromascript";
  
  /* Instructions displayed at the top of the write tab */
  private final String instructText = "Select a file to Chromascript:";
  /* Text displayed on the file selection button */
  private final String fileSelButtonText = "File";
  /* Text displayed on the Chromascript button */
  private final String chromascriptButtonText = "Read Chromascript!";

  /* Panel where instructions are displayed*/
  private Panel instructPanel = null;
  /* Panel that holds the file selection button and current file name label */
  private Panel fileSelPanel = null;
  /* Panel that holds the chromascript button */
  private Panel chromascriptPanel = null;
  /* Button that when clicked calls the InFileSelButton class */
  private Button fileSelButton = null;
  /* Button that when clicked calls the ChromascriptButton class */
  private Button chromascriptButton = null;
  /* The label that displays the current selected input file's filename */
  private static Label fileNameLabel = null;
  
  /* Updates the fileNameLabel to the current input file */
  public static void updateFileLabel()
  {
    /* If an input file wasn't chosen, getInFileName will throw an exception */
    try
    {
      fileNameLabel.setText(LocalStreams.getInFileName());
    }
    catch(Exception ε)
    {
      
    }
  }
  
  public ReadTab()
  {
    /* Initialize all the panels with a flow layout */
    instructPanel = new Panel(new FlowLayout());
    fileSelPanel = new Panel(new FlowLayout());
    chromascriptPanel = new Panel(new FlowLayout());
    /* Initialize the buttons with their respective texts */
    fileSelButton = new Button(fileSelButtonText);
    chromascriptButton = new Button(chromascriptButtonText);
    /* Initialize the filename label with a blank label that can be written to 
     * later when the updateFilenameLable function is called */
    fileNameLabel = new Label();
    
    /* Initialize the instruction panel */
    instructPanel.add(new Label(instructText));
    instructPanel = GUI.setSize(instructPanel, 16, 1);
    
    /* Initialize the file selection panel */
    fileSelButton = GUI.setSize(fileSelButton, 2, 0.8);
    fileSelButton.addActionListener(new InFileSelButton());
    /* The fileNameLabel cannot be equal to or greater than the size of it's
     * parent panel. We have to make it slightly smaller to be the most
     * efficient with space */
    fileNameLabel = GUI.setSize(fileNameLabel, 13, 1);
    
    fileSelPanel.add(fileSelButton);
    fileSelPanel.add(fileNameLabel);
    fileSelPanel = GUI.setSize(fileSelPanel, 16, 1);
    
    /* Initialize the Chromascript panel/button */
    chromascriptButton = GUI.setSize(chromascriptButton, 15, 0.8);
    chromascriptButton.addActionListener(new ReadButton());
    
    chromascriptPanel = GUI.setSize(chromascriptPanel, 16, 1);
    chromascriptPanel.add(chromascriptButton);
    
    /* Add everything to our panel, and we're done! */
    this.setLayout(new FlowLayout());
    this.add(instructPanel);
    this.add(fileSelPanel);
    this.add(chromascriptPanel);
  }
  
  /* Returns the text that is to be displayed on the tab itself */
  public static String getTitle()
  {
    return title;
  }
}
