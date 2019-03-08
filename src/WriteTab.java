/*
 * WriteTab.java of Chromascript,
 * the high-density paper-based data storage program
 *
 * by Charles Thompson, do not distribute!
 */

import java.awt.*;
import javax.swing.*;

public class WriteTab extends Panel
{
  /* Variable that makes eclipse shut up */
  private static final long serialVersionUID = 1L;
  
  /* Title of tab*/
  private static final String title = "Create a Chromascript";
  
  /* Instructions displayed at the top of the write tab */
  private final String instructText = "Select a file to Chromascript:";
  /* Text displayed on the file selection button */
  private final String fileSelButtonText = "File";
  /* Text displayed on the Chromascript button */
  private final String chromascriptButtonText = "Chromascript!";
  /* Text displayed next to the paper size dropdown */
  private final String paperSelDropdownText = "Paper size:";
  /* Text displayed next to the DPI dropdown */
  private final String dpiSelDropdownText = "DPI:";
  
  /* Panel where instructions are displayed*/
  private Panel instructPanel = null;
  /* Panel that holds the file selection button and current file name label */
  private Panel fileSelPanel = null;
  /* Panel that holds all the different options */
  private Panel optionsPanel = null;
  /* Panel that holds the chromascript button */
  private Panel chromascriptPanel = null;
  /* Dropdown menu for the different paper sizes */
  private static JComboBox paperSelDropdown = null;
  /* Dropdown menu for the different DPI densities */
  private static JComboBox dpiSelDropdown = null;
  /* Button that when clicked calls the InFileSelButton class */
  private Button fileSelButton = null;
  /* Button that when clicked calls the ChromascriptButton class */
  private Button chromascriptButton = null;
  /* The label that displays the current selected input file's filename */
  private static Label fileNameLabel = null;
  
  /* Paper size dropdown text, default is US Letter */
  private final String paperSelDropdownOpts[] =
  {
    "Index Card",
    "Gov Letter",
    "Letter",
    "Legal",
    "Ledger",
    "A4",
    "A3",
    "A2",
    "A1",
    "A0"
  };
  /* DPI dropdown text, default is 300 */
  private final String dpiSelDropdownOpts[] =
  {
    "60",
    "90",
    "200",
    "300",
    "600",
    "1200",
    "2400",
  };
  /* Paper widths in inch 1/10ths */
  private final static short paperWidths[] =
  {
    30, /* Index Card is 3 inches wide */
    85, /* US Gov. Letter is 8.5 inches wide */
    85, /* US Letter is 8.5 inches wide */
    85, /* US Legal is 8.5 inches wide */
    110, /* US Ledger is 11 inches wide */
    83, /* A4 is 8.3 inches wide */
    117, /* A3 is 11.7 inches wide */
    165, /* A2 is 16.5 inches wide */
    234, /* A1 is 23.4 inches wide */
    331, /* A0 is 33.1 inches wide */
  };
  /* Paper heights in inch 10ths */
  private final static short paperHeights[] =
  {
    50, /* Index Card is 5 inches long */
    100, /* US Gov. Letter 10 inches long */
    110, /* US Letter is 11 inches long */
    140, /* US Legal is 14 inches long */
    170, /* US Ledger is 17 inches long */
    117, /* A4 is 11.7 inches long */
    165, /* A3 is 16.5 inches long */
    234, /* A2 is 234 inches long */
    331, /* A1 is 33.1 inches long */
    468, /* A0 is 46.8 inches long */
  };
  /* Printer dot densities in DPI */
  private final static short dpis[] =
  {
    60,
    90,
    200,
    300,
    600,
    1200,
    2400,
  };
  
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
  
  /* Get the selected page's width in pixels */
  public static int getPageWidth()
  {
    return
    (
      dpis[dpiSelDropdown.getSelectedIndex()] *
      /* The paper width is divided by 10 to convert inch-tenths to inches */
      paperWidths[paperSelDropdown.getSelectedIndex()] / 10
    );
  }
  
  /* Get the selected page's height in pixels*/
  public static int getPageHeight()
  {
    return
    (
      dpis[dpiSelDropdown.getSelectedIndex()] *
      /* The paper width is divided by 10 to convert inch-tenths to inches */
      paperHeights[paperSelDropdown.getSelectedIndex()] / 10
    );
  }
  
  public static short getDPI()
  {
    return dpis[dpiSelDropdown.getSelectedIndex()];
  }
  
  public WriteTab()
  {
    /* Initialize all the panels with a flow layout */
    instructPanel = new Panel(new FlowLayout());
    fileSelPanel = new Panel(new FlowLayout());
    optionsPanel = new Panel(new FlowLayout());
    chromascriptPanel = new Panel(new FlowLayout());
    /* Initialize the dropdowns with their respective options */
    paperSelDropdown = new JComboBox(paperSelDropdownOpts);
    dpiSelDropdown = new JComboBox(dpiSelDropdownOpts);
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
    
    /* Initialize the options panel */
    /* Letter sized paper is most common in the US, so set it as default */
    paperSelDropdown.setSelectedIndex(2);
    paperSelDropdown = GUI.setSize(paperSelDropdown, 2, 0.8);
    
    /* 300 DPI works on most if not all printers in use, so set it as default */
    dpiSelDropdown.setSelectedIndex(3);
    dpiSelDropdown = GUI.setSize(dpiSelDropdown, 2, 0.8);
    
    optionsPanel.add(new Label(paperSelDropdownText));
    optionsPanel.add(paperSelDropdown);
    optionsPanel.add(new Label(dpiSelDropdownText));
    optionsPanel.add(dpiSelDropdown);
    optionsPanel = GUI.setSize(optionsPanel, 16, 1);
    
    /* Initialize the Chromascript panel/button */
    chromascriptButton = GUI.setSize(chromascriptButton, 15, 0.8);
    chromascriptButton.addActionListener(new ChromascriptButton());
    
    chromascriptPanel = GUI.setSize(chromascriptPanel, 16, 1);
    chromascriptPanel.add(chromascriptButton);
    
    /* Add everything to our panel, and we're done! */
    this.setLayout(new FlowLayout());
    this.add(instructPanel);
    this.add(fileSelPanel);
    this.add(optionsPanel);
    this.add(chromascriptPanel);
  }
  
  /* Returns the text that is to be displayed on the tab itself */
  public static String getTitle()
  {
    return title;
  }
}
