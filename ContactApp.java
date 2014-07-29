/*
 * Alex Hong (C) 2014
 */

package contactapp;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;

/**
 * Allows user to enter a contact's first name, initial,
 * last name, and their phone number.
 */
public class ContactApp extends JPanel implements ActionListener
{
    private String first, initial, last, phone; // contact's info
    static int sCurrentRecordNum = 0; // keeping track of current record
    static int sTotal = 0; // total record
    final int MAX_RECORD = 50; // maximum num of records that could be created
    ArrayList person = new ArrayList(); // contacts

    // Textfields and labels
    JTextField firstField = new JTextField(20); // first name
    JTextField initialField = new JTextField(20); // initials
    JTextField lastField = new JTextField(20); // surname
    JTextField phoneField = new JTextField(20); // phone number
    JLabel recordLabel = new JLabel("Records: 1 of 1"); // pages

    // Constructs a panel
    public ContactApp()
    {
        // Ask for input using labels and text fields
        userInput();
	    // Set background colour to blue
        setBackground (new Color(22, 184, 237));
    }


    // Ask for first name, initial, last name, and phone number
    public void userInput()
    {
        // Resets the current record number and sTotal for new application
        sCurrentRecordNum = 0;
        sTotal = 0;

        // Labels - Text messages
        JLabel firstLabel = new JLabel ("First Name: ");
        JLabel initialLabel = new JLabel ("Initial: ");
        JLabel lastLabel = new JLabel ("Last Name: ");
        JLabel phoneLabel = new JLabel ("Phone Number: ");

        // Set the font of the labels
        firstLabel.setFont (new Font ("Arial", Font.BOLD, 15));
        initialLabel.setFont (new Font ("Arial", Font.BOLD, 15));
	    lastLabel.setFont (new Font ("Arial", Font.BOLD, 15));
        phoneLabel.setFont (new Font ("Arial", Font.BOLD, 15));

	    // Set the colour of the labels
        firstLabel.setForeground (Color.blue);
        initialLabel.setForeground (Color.blue);
        lastLabel.setForeground (Color.blue);
        phoneLabel.setForeground (Color.blue);

        // Buttons
        JButton deleteButton = new JButton ("Delete");
        JButton nextButton = new JButton ("Next");
        JButton backButton = new JButton ("Back");
        JButton recordButton = new JButton ("Record");

        // Box
        Box labelBox = new Box (1);
        Box textBox = new Box (1);

        // Adds the labels to a box
        labelBox.add (firstLabel);
        labelBox.add (initialLabel);
        labelBox.add (lastLabel);
        labelBox.add (phoneLabel);

        // Adds the text field to a box
        textBox.add (firstField);
        textBox.add (initialField);
        textBox.add (lastField);
        textBox.add (phoneField);

        // Adds the components to the panel
        add (labelBox);
        add (textBox);
        add (deleteButton);
        add (backButton);
        add (recordButton);
        add (nextButton);
        add (recordLabel);

        // Add an action listener to the buttons
        recordButton.addActionListener (this);
        backButton.addActionListener (this);
        nextButton.addActionListener (this);
        deleteButton.addActionListener (this);
    }


    // Performs a specific action for each button pushed
    public void actionPerformed (ActionEvent ae)
    {
	    // User pushed the record, back, or next button
        if (ae.getActionCommand ().equals ("Record") ||
            ae.getActionCommand ().equals ("Back")   ||
            ae.getActionCommand ().equals ("Next")     )
        {
	        // Saves Records automatically
            // Gets the text in the text fields
	        first = firstField.getText ().trim ();
    	    initial = initialField.getText ().trim ();
	        last = lastField.getText ().trim ();
            phone = phoneField.getText ().trim ();

	    // User must enter at least one field to create a record
	    if (!(first.equals ("") && initial.equals ("") &&
              last.equals ("") && phone.equals ("")))
	    {
		    // check if the phone number is valid before the record stores it
            if (PersonRecord.checkPhone (phone) == false)
            {
                // Error Message
                JOptionPane.showMessageDialog (this, 
                    "The phone number is invalid.", "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            // checks if the record exist
            if (sCurrentRecordNum == person.size ())
            {
		        // create a new PersonRecord
                person.add (new PersonRecord (first, initial, last, phone));
                // Calculate the sTotal created records
                sTotal++;
            }
            else
            {
		        /**
                 * the record already exist, alter data without creating
                 * a new record
                 */
		        // overwrite changes to the record
		        ((PersonRecord)person.get(sCurrentRecordNum)).setFirst(first);
		        ((PersonRecord)person.get(sCurrentRecordNum)).setLast(last);
		        ((PersonRecord)person.get(sCurrentRecordNum)).setInitial(initial);
		        ((PersonRecord)person.get(sCurrentRecordNum)).setPhone(phone);
		    }
		
            // display inputs back to the user properly formatted
            firstField.setText(((PersonRecord)person.get(sCurrentRecordNum)).getFirstName());
            initialField.setText(((PersonRecord)person.get(sCurrentRecordNum)).getInitial());
            lastField.setText(((PersonRecord)person.get(sCurrentRecordNum)).getLastName());
            phoneField.setText(((PersonRecord)person.get(sCurrentRecordNum)).getPhone());
	    }
	    else
	    {
		    // displays an error message
            if (ae.getActionCommand ().equals ("Record"))
            {
		        // Error Message
		        JOptionPane.showMessageDialog (this, "No inputs to store.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
	}

	// User pushed the "Next" button
	if (ae.getActionCommand ().equals ("Next"))
	{
	    // increment
	    sCurrentRecordNum++;

	    // Resets sCurrentRecordNum to 0 if it goes pass sTotal
	    if (sCurrentRecordNum > sTotal)
		sCurrentRecordNum = 0;

	    // Resets sCurrentRecordNum to 0
	    if (sCurrentRecordNum == MAX_RECORD)
	    {
		    sTotal = MAX_RECORD;
            sCurrentRecordNum = 0;
	    }

	    // sets up the text fields for the next record
	    if (sCurrentRecordNum == person.size ())
	    {
    		// Clears the text fields for the next record
    		firstField.setText ("");
    		initialField.setText ("");
    		lastField.setText ("");
    		phoneField.setText ("");
	    }
	    else
	    {
    		// sets the text in the text field with the existing records
    		firstField.setText(((PersonRecord)person.get(sCurrentRecordNum)).getFirstName());
    		initialField.setText(((PersonRecord)person.get(sCurrentRecordNum)).getInitial());
    		lastField.setText(((PersonRecord)person.get(sCurrentRecordNum)).getLastName());
    		phoneField.setText(((PersonRecord)person.get(sCurrentRecordNum)).getPhone());
	    }
	}

	// User pushed the "Delete" button
	if (ae.getActionCommand().equals ("Delete"))
	{
	    //checks if the record exist
	    if (sCurrentRecordNum < person.size ())
	    {
    		// remove the element
    		person.remove (sCurrentRecordNum);
    		sTotal--;
	    }
	    else
	    {
    		// Error Message
    		JOptionPane.showMessageDialog (this, "No record to delete.",
                "Error", JOptionPane.ERROR_MESSAGE);
    		return;
	    }
	}

	// User pushed the "Back" or "Delete" button
	if (ae.getActionCommand ().equals ("Back") ||
        ae.getActionCommand ().equals ("Delete"))
	{
	    sCurrentRecordNum--;
	    // Resets sCurrentRecordNum to sTotal
	    if (sCurrentRecordNum == -1)
	    {
    		// Resets sCurrentRecordNum to 0 if sTotal is 0
    		if (sTotal == 0)
    		    sCurrentRecordNum = 0;
    		else
    		    sCurrentRecordNum = sTotal - 1;
	    }

	    //checks if the record exist
	    if (sCurrentRecordNum < person.size ())
	    {
    		// sets the text in the text field with the existing records
    		firstField.setText (((PersonRecord) person.get (sCurrentRecordNum)).getFirstName ());
    		initialField.setText (((PersonRecord) person.get (sCurrentRecordNum)).getInitial ());
    		lastField.setText (((PersonRecord) person.get (sCurrentRecordNum)).getLastName ());
    		phoneField.setText (((PersonRecord) person.get (sCurrentRecordNum)).getPhone ());
	    }
	    else
	    {
    		firstField.setText ("");
    		initialField.setText ("");
    		lastField.setText ("");
    		phoneField.setText ("");
	    }
	}

	// setting recordLabel
	if (sTotal == 0)
	    recordLabel.setText ("Records: " + (sCurrentRecordNum + 1) + " of 1");
	else
	    recordLabel.setText ("Records: " + (sCurrentRecordNum + 1) + " of " + sTotal);
    }
}


