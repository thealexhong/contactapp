/*
 * Alex Hong (C) 2014
 */

package contactapp;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;


/**
 * Constucts a frame for the records.
 */
public class DatabaseApp extends JFrame implements ActionListener
{
    // file variables
    String filename;
    String fileExtension = ".psr";
    String fileHeader = "<%&*#BJ@J#(ND)$>";

    // file io variable
    BufferedReader input;
    String temp;

    // dialog
    JDialog dialog;

    ContactApp a = new ContactApp (); // ContactApp instance

    // Constructs a frame
    public DatabaseApp ()
    {
    	// Constructs a frame with a frame title
    	super ("Record Database");

    	// Creates a new AdressBook object and adds it to the program
    	getContentPane ().add (a);

    	// Creates menu items
    	JMenuItem newItem = new JMenuItem ("New");
    	JMenuItem openItem = new JMenuItem ("Open..");
    	JMenuItem saveItem = new JMenuItem ("Save");
    	JMenuItem saveAsItem = new JMenuItem ("Save As..");
    	JMenuItem quitItem = new JMenuItem ("Quit");
    	JMenuItem helpItem = new JMenuItem ("Help");
    	JMenuItem aboutItem = new JMenuItem ("About");

    	// Sets up the menu
    	JMenu fileMenu = new JMenu ("File");
    	JMenu helpMenu = new JMenu ("Help");

    	// Adds the menu items to the menu
    	fileMenu.add (newItem);
    	fileMenu.add (openItem);
    	fileMenu.add (saveItem);
    	fileMenu.add (saveAsItem);
    	fileMenu.add (quitItem);
    	helpMenu.add (aboutItem);
    	helpMenu.add (helpItem);

    	// Creates a menu bar
    	JMenuBar myMenus = new JMenuBar ();

    	// Sets the menu bar for this frame
    	setJMenuBar (myMenus);

    	// Adds the menus to the menu bar
    	myMenus.add (fileMenu);
    	myMenus.add (helpMenu);

    	// Adds this action listener to receive action events from these menu items
    	newItem.addActionListener (this);
    	openItem.addActionListener (this);
    	saveItem.addActionListener (this);
    	saveAsItem.addActionListener (this);
    	quitItem.addActionListener (this);
    	helpItem.addActionListener (this);
    	aboutItem.addActionListener (this);

    	// Sets the window's size and shows the window
    	setSize (675, 200);
    	setVisible (true);

    	// Sets the close operation
    	setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

    	// Sets the layout to AbsoluteLayout (null)
    	getContentPane ().setLayout (null);

    	// Frame is not resizable
    	setResizable (true);
    }


    // Performs a specific action for each menu item accessed
    public void actionPerformed (ActionEvent ae)
    {
    	// User has selected the "New" menu item
    	if (ae.getActionCommand ().equals ("New"))
    	{
    	    newDatabase ();
    	}
    	// User has selected the "Open.." menu item
    	if (ae.getActionCommand ().equals ("Open.."))
    	{
    	    open ();
    	}
    	// User has selected the "Save" menu item
    	if (ae.getActionCommand ().equals ("Save"))
    	{
    	    save ();
    	}
    	// User has selected the "Save As.." menu item
    	if (ae.getActionCommand ().equals ("Save As.."))
    	{
    	    saveAs ();
    	}
    	// User has selected the "Quit" menu item
    	if (ae.getActionCommand ().equals ("Quit"))
    	{
    	    System.exit (0); // exits the program
    	}
    	// User has selected the "Help" menu item
    	if (ae.getActionCommand ().equals ("Help"))
    	{
    	    showDialog ("Help", "Yes, Help!", 100); //shows a help dialog
    	}
    	// User has selected the "About" menu item
    	if (ae.getActionCommand ().equals ("About"))
    	{
    	    showDialog ("About", "This program records personal information.",
                300); //shows a about dialog
    	}
    }


    // Creates a new data base
    private void newDatabase ()
    {
    	dispose (); // dispose the current window
    	filename = null; // filename is null
    	// clears array list
    	a.person.clear ();
    	new DatabaseApp (); // Creates a new DatabaseApp object
    }


    /** 
     * Asks the user for the filename (errortraps) to save or open
     *  @param  string is either open or save as
     */
    private void askFileName (String string)
    {
    	int spaceCounter = 0; // counts the spaces in a filename

    	// Gets the user's input for filename in an input dialog
    	filename = JOptionPane.showInputDialog ("Enter the filename to "
            + string + " excluding the extension (.psr): ", "My Records");

    	// User has selected the Cancel option
    	if (filename == null)
    	{
    	    return; // exits the method
    	}

    	// filename must be at least 1 character long
    	if (!(filename.length () < 1))
    	{
    	    // Find invalid characters in filename
    	    for (int xChar = 0 ; xChar < filename.length () ; xChar++)
    	    {
        		// filename cannot have the characters \, /, :, *, ?, <, >, |, "
        		if (filename.charAt (xChar) == '\\' || filename.charAt (xChar) == '/'
        			|| filename.charAt (xChar) == ':' || filename.charAt (xChar) == '*'
        			|| filename.charAt (xChar) == '?' || filename.charAt (xChar) == '<'
        			|| filename.charAt (xChar) == '>' || filename.charAt (xChar) == '|'
        			|| filename.charAt (xChar) == '\"')
        		{
        		    // Error Message
        		    JOptionPane.showMessageDialog (this, 
                        "Filenames cannot have the characters \\, /, :, *, ?, <, >, |, \".", 
                        "Error", JOptionPane.ERROR_MESSAGE);
        		    filename = null; // filename is null
        		    break; //exits the loop
        		}
        		// increment spaceCounter
        		if (filename.charAt (xChar) == ' ')
        		{
        		    spaceCounter++;
        		}
            }
	    }

    	// checks if the filename is all spaces or if the filename is nothing
    	if (filename.length () < 1 || spaceCounter == filename.length ())
    	    // Error Message
    	    JOptionPane.showMessageDialog (this, "The filename is invalid.",
                "Error", JOptionPane.ERROR_MESSAGE);
    }


    /**
     * Checks if the file already exist.
     *  @return true        file exist
     *  @param filename     name of file
     **/
    private boolean checkExistence (String filename)
    {
    	// Reads data in a file
    	try
    	{
    	    input = new BufferedReader(new FileReader (filename + fileExtension)); //reads the data at this location
    	    temp = input.readLine (); //gets the input for temp
    	    // Something is in the file, the file exists
    	    if (temp != null)
    	    {
                return (true); // returns true, there is an existing file
    	    }
    	}
    	catch (IOException e)
    	{
            return false; // File does not exist, return false
    	}
    	return (false); //returns false, there is no file with the name of filename
    }


    // Asks the user for a valid filename to open
    public void open ()
    {
    	askFileName ("open"); // asks the user for the filename to open
    	// User has selected the Cancel option
    	if (filename == null)
    	    return; // exits the method
    	// checks if the file exist
    	if (checkExistence (filename))
    	{
    	    // reads the file
    	    try
    	    {
        		// checks if the file header matches
        		if (!(temp.equals (fileHeader)))
        		{
        		    // Error Message
        		    JOptionPane.showMessageDialog (this,
                        "The file is corrupted..", "Error",
                        JOptionPane.ERROR_MESSAGE);
        		    return;
        		}
        		// sets the current record number
        		a.sCurrentRecordNum = 0;
        		// sets the total number of records
        		a.sTotal = Integer.parseInt (input.readLine ());
        		// clears array list
        		a.person.clear ();
        		// reads the file and loads the data in the array
        		for (int x = 0 ; x < a.sTotal ; x++)
        		{
        		    a.person.add (new PersonRecord ());
        		    ((PersonRecord) a.person.get (x)).setFirst (input.readLine ());
        		    ((PersonRecord) a.person.get (x)).setInitial (input.readLine ());
        		    ((PersonRecord) a.person.get (x)).setLast (input.readLine ());
        		    ((PersonRecord) a.person.get (x)).setPhone (input.readLine ());
        		}
        		// sets the text fields for the first record
        		a.firstField.setText (((PersonRecord) a.person.get (0)).getFirstName ());
        		a.initialField.setText (((PersonRecord) a.person.get (0)).getInitial ());
        		a.lastField.setText (((PersonRecord) a.person.get (0)).getLastName ());
        		a.phoneField.setText (((PersonRecord) a.person.get (0)).getPhone ());
        		// sets the record label
        		a.recordLabel.setText ("Records: 1 of " + a.sTotal);
    	    }
    	    catch (IOException e)
    	    {
        	    JOptionPane.showMessageDialog (this, e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
    	    }
    	}
    	else
    	    // Error Message
    	    JOptionPane.showMessageDialog (this, "The file does not exist.",
                "Error", JOptionPane.ERROR_MESSAGE);
    }


    // Write data to a file
    private void writeFile ()
    {
    	PrintWriter output; // output variable
    	// store data in a file
    	try
    	{
    	    output = new PrintWriter (new FileWriter (filename + fileExtension)); //stores the data at this location

    	    // writes in the file
    	    output.println (fileHeader);
    	    output.println (a.sTotal);
    	    // prints up to the total record
    	    for (int x = 0 ; x < a.sTotal ; x++)
    	    {
        		output.println (((PersonRecord) a.person.get (x)).getFirstName ());
        		output.println (((PersonRecord) a.person.get (x)).getInitial ());
        		output.println (((PersonRecord) a.person.get (x)).getLastName ());
        		output.println (((PersonRecord) a.person.get (x)).getPhone ());
    	    }
    	    output.close (); // closes the file
    	}
    	catch (IOException e)
    	{
    	    JOptionPane.showMessageDialog (this, e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    	}
    }


    // Asks the user for a valid filename and stores data to the file
    private void saveAs ()
    {
    	// checks if the user have data to save
    	if (a.person.size () > 0)
    	{
    	    askFileName ("save as"); // asks the user for the filename to save as
    	    // the user chooses to cancel
    	    if (filename == null)
    		    return; // exits the method
    	    // checks if the file already exist
    	    if (checkExistence (filename))
    	    {
        		// Asks the user to replace the existing file
        		int option = JOptionPane.showConfirmDialog (this, "The file already exists. Do you wish to replace the existing file?", "Replace?", JOptionPane.YES_NO_OPTION);

        		// User has selected the Yes option
        		if (option == JOptionPane.YES_OPTION)
        		    save (); // saves the file
    	    }
    	    else
    		    save (); // saves the file
    	}
    	else
    	    // Error Message
    	    JOptionPane.showMessageDialog (this, "There is no record to save.", "Error", JOptionPane.ERROR_MESSAGE);
    }


    // Saves the file
    public void save ()
    {
	// checks if a filename exist
	if (filename != null)
	    // checks if the user have data to save
	    if (a.person.size () > 0)
		    writeFile (); //writes to a file
	    else
		    saveAs (); // goes to error message
	else
	    saveAs (); // Asks the user for a valid filename and stores data to the file
    }


    // Displays a dialog
    private void showDialog (String dialogTitle, String string, int size)
    {
    	dialog = new JDialog (this, dialogTitle); //constructs a dialog with a title
    	dialog.setSize (size, 90); //sets the dimensions of the dialog window
    	dialog.setResizable (false); //the dialog is not resizable
    	dialog.getContentPane ().setLayout (new FlowLayout ()); //sets the layout of the dialog with a centered alignment
    	JLabel label = new JLabel (string); //creates a text message
    	JButton closeButton = new JButton ("Close"); //creates a push button
    	closeButton.addActionListener (new ActionListener ()  //adds an action listener to the close button
    	{
    	    //performs an action when the close button is pushed
    	    public void actionPerformed (ActionEvent e)
    	    {
    		    dialog.dispose (); //disposes the dialog
    	    }
    	}
    	);
    	dialog.getContentPane ().add (label); //adds the help text message to the dialog
    	dialog.getContentPane ().add (closeButton); //adds the close button to the dialog
    	dialog.setLocationRelativeTo (this); //sets the location of the dialog relative to this object
    	dialog.show (); //shows the dialog
    }


    //main method
    public static void main (String[] args)
    {
    	// Decorate the window
    	setDefaultLookAndFeelDecorated (true);
    	// Create new DatabaseApp
    	new DatabaseApp ();
    }
}


