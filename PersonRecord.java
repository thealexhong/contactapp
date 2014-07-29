/*
 * Alex Hong (C) 2014
 */

package contactapp;

/**
 * Stores personal data in a record structure.
 */
public class PersonRecord
{
    // Class Variables
    private String first, initial, last, phone;

    /**
     * Constructs a personal record containing <first>,
     *  <initial>, <last>, and <phone>.
     *  @param first    first name
     *  @param initial  initial
     *  @param last     last name
     *  @param phone    phone number
     */
    public PersonRecord (String first, String initial, String last, String phone)
    {
    	this.first = first;
    	this.initial = initial;
    	this.last = last;
    	this.phone = phone;
    }


    /**
     * Constructs a personal record containing <first>,
     *  <last>, and <phone>.
     *  @param first    first name
     *  @param last     last name
     *  @param phone    phone number
     */
    public PersonRecord (String first, String last, String phone)
    {
    	this.first = first;
    	this.last = last;
    	this.phone = phone;
    }


    /**
     * Constructs a personal record containing <first>, and
     *  <phone>.
     *  @param first    first name
     *  @param last     last name
     */
    public PersonRecord (String first, String last)
    {
    	this.first = first;
    	this.last = last;
    }


    /**
     * Constructs a personal record containing <first>
     *  @param first    first name
     */
    public PersonRecord (String first)
    {
	this.first = first;
    }


    /**
     * Constructs a personal record
     */
    public PersonRecord ()
    {
    }


    /**
     * Sets the first name to a new name.
     *  @param newName     the new name
     */
    public void setFirst (String newName)
    {
	    first = newName;
    }


    /**
     * Sets the initial to a new initial.
     *  @param newInitial     the new initial
     */
    public void setInitial (String newInitial)
    {
        initial = newInitial;
    }


    /**
     * Sets the last name to a new last name.
     *  @param newLast     the new last name
     */
    public void setLast (String newLast)
    {
        last = newLast;
    }


    /** 
     * Sets the phone number to a new phone number.
     *  @param newPhone     the new phone number
     */
    public void setPhone (String newPhone)
    {
	    phone = newPhone;
    }


    /** 
     * Returns this person's first name
     *   @return     the first name
     */
    public String getFirstName ()
    {
	    return formatFirst (first);
    }


    /**
     * Returns this person's last name
     *   @return     the last name
     */
    public String getLastName ()
    {
	    return formatLast (last);
    }


    /**
     * Returns this person's initial
     *   @return     the initial
     */
    public String getInitial ()
    {
	    return formatInitial (initial);
    }


    /**
     * Returns this person's phone number
     *   @return    the phone number
     */
    public String getPhone ()
    {
	    return formatPhone (phone);
    }


    /**
     * Returns this person's first name properly formatted
     *   @return     the formatted first name
     */
    private String formatFirst (String first)
    {
	    first = first.trim (); // returns a copy of first string without leading spaces
    	// checks if the string is valid
    	if (!(first.equals ("")))
    	{
    	    // returns the string with the first character capitalized
    	    if (first.length () == 1)
    		    return first.substring (0, 1).toUpperCase ();
    	    return first.substring (0, 1).toUpperCase () + first.substring (1).toLowerCase ();
    	}
    	return "";
    }


    /**
     * Returns this person's last name properly formatted
     *   @return     the formatted last name
     */
    private String formatLast (String last)
    {
	    return formatFirst (last);
    }


    /**
     * Returns this person's initial properly formatted
     *   @return     the formatted initial
     */
    private String formatInitial (String initial)
    {
	    initial = initial.trim (); // returns a copy of first string without leading spaces
    	// checks if the string is valid
    	if (!(initial.equals ("")))
    	    return (initial.substring (0, 1).toUpperCase () + ".");
    	return "";
    }


    /**
     * Returns this person's phone number properly formatted
     *   @return     the formatted phone number
     */
    private String formatPhone (String phone)
    {
    	phone = phone.trim (); // returns a copy of first string without leading spaces
    	if (!(phone.equals ("")))
    	{
    	    if (phone.length () == 7)
    		return (phone.substring (0, 3) + "-" + phone.substring (3));
    	    if (phone.length () == 8)
    		return phone;
    	}
    	return "";
    }


    /**
     * Checks if the phone number contains only digits
     *   @return     <code>true</code> if the phone number is correct
     *               <code>false</code> if the phone number is incorrect
     */
    public static boolean checkPhone (String phone)
    {
    	// digit counter
    	int digitCounter = 0;
    	// the phone number must be 7 or 8 digits long
    	if (phone.length () == 7 || phone.length () == 8)
    	{
    	    // reads each character in the phone number
    	    for (int x = 0 ; x < phone.length () ; x++)
    	    {
        		// returns false if the 4th character is not '-' when the length is 8
        		if (phone.length () == 8 && x == 3 && phone.charAt (x) != '-')
        		    return false;
        		// increment digit counter if the character is a digit
        		if (Character.isDigit (phone.charAt (x)))
        		    digitCounter++;
    	    }
    	    // returns true only if there is 7 digits in the phone number
    	    if (digitCounter == 7)
    		    return true;
    	}
    	else
    	    // the user has not enter a phone number
    	    if (phone.equals (""))
    		    return true;
    	return false;
    }
}


