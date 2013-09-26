/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {
	
	// For export as a JAR file
	public static void main(String[] args) {
		new FacePamphlet().start(args);
	}

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		// You fill this in
		addInteractors();
		myData = new FacePamphletDatabase();
		canvas = new FacePamphletCanvas();
		add(canvas);
    }
	
	private void addInteractors() {
		// North
		JLabel name = new JLabel("Name");
		add(name, NORTH);
		
		tf_name = new JTextField(TEXT_FIELD_SIZE);
		tf_name.addActionListener(this);
		add(tf_name, NORTH);
				
		add = new JButton("Add");		add(add, NORTH);
		delete = new JButton("Delete");	add(delete, NORTH);
		lookup = new JButton("Lookup");	add(lookup, NORTH);
				
		// West
		tf_status = new JTextField(TEXT_FIELD_SIZE);	tf_status.addActionListener(this);
		tf_pic = new JTextField(TEXT_FIELD_SIZE);		tf_pic.addActionListener(this);
		tf_friend = new JTextField(TEXT_FIELD_SIZE);	tf_friend.addActionListener(this);
				
		status = new JButton("Post Twitter");
		pic = new JButton("Change Picture");
		friend = new JButton("Add Friend");
				
		add(tf_status, WEST);
		add(status, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
				
		add(tf_pic, WEST);
		add(pic, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
				
		add(tf_friend, WEST);
		add(friend, WEST);		
		
		addActionListeners();
	}
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
		// You fill this in as well as add any additional methods
    	String cmd = e.getActionCommand();
    	String user = tf_name.getText();
    	
    	// Action of 'Add' button
    	if (cmd.equals("Add")) {
    		FacePamphletProfile userProfile = new FacePamphletProfile(user);
    		currentUser = user;
    		if (myData.containsProfile(currentUser)) {
    			canvas.showMessage("A profile with the name " + currentUser + " already exists");
    			canvas.displayProfile(myData.getProfile(currentUser));
    		} else {
    			myData.addProfile(userProfile);
    			canvas.showMessage("New profile created");
    			canvas.displayProfile(myData.getProfile(currentUser));
    		}    		
    	}

    	// Action of 'Delete' button
    	if (cmd.equals("Delete")) {
    		currentUser = user;
    		if (myData.containsProfile(user)) {
    			myData.deleteProfile(user);
    			canvas.showMessage("Profile of " + currentUser +" deleted");
    		} else
    			canvas.showMessage("A profile with the name " + currentUser + " does not exist");
    		currentUser = null;
    	}
    	
    	// Action of 'Lookup' button
    	if (cmd.equals("Lookup")) {
    		currentUser = user;
    		if (myData.containsProfile(currentUser)) {
    			canvas.showMessage("Displaying " + currentUser);
    			canvas.displayProfile(myData.getProfile(currentUser));
    		}
    		else {
    			canvas.showMessage("A profile with the name " + currentUser + " does not exist");
    			currentUser = null;
    		}
    	}
    	
    	// Action of 'Post Twitter'
    	if (e.getSource() == tf_status || cmd.equals("Post Twitter")) {
    		String newStatus = tf_status.getText();
    		if (currentUser != null) {
    			myData.getProfile(currentUser).setStatus(newStatus);
    			canvas.showMessage("Status updated to " + newStatus);
    			canvas.displayProfile(myData.getProfile(currentUser));
    		} else
    			canvas.showMessage("Please select a profile to post Twitter");
    	}
    	
    	// Action of 'Change Picture'
    	if (e.getSource() == tf_pic || cmd.equals("Change Picture")) {
    		GImage image = null;
    		try {
    			image = new GImage(tf_pic.getText());
    			if (currentUser != null) {
        			myData.getProfile(currentUser).setImage(image);
        			canvas.showMessage("Picture updated");
        			canvas.displayProfile(myData.getProfile(currentUser));
        		} else
        			canvas.showMessage("Please select a profile to change picture");
    		} catch (ErrorException ex) {
    			canvas.showMessage("Unable to open image file: <" + tf_pic.getText() + ">");
    			canvas.displayProfile(myData.getProfile(currentUser));
    		}
    	}
    	
    	// Action of 'Add Friend'
    	if (e.getSource() == tf_friend || cmd.equals("Add Friend")) {
    		String newFriend = tf_friend.getText();
    		// If the profile to be updated is valid?
    		if (currentUser != null) {
    			// If the friend to be added is valid?
    			if (myData.containsProfile(newFriend)) {
    				// If the friend to be added is already my friend?
    				if (myData.getProfile(currentUser).getFriends().contains(newFriend)) {
    					canvas.showMessage(currentUser + " already has " + newFriend + " as a friend");
    					canvas.displayProfile(myData.getProfile(currentUser));
    				} else {
    					// Add reciprocal friends
    					myData.getProfile(currentUser).getFriends().add(newFriend);
    					myData.getProfile(newFriend).getFriends().add(currentUser);
    					canvas.showMessage(newFriend + " added as a friend");
    					canvas.displayProfile(myData.getProfile(currentUser));
    				}
    			} else {// friend to be added is not valid
    				canvas.showMessage(newFriend + " does not exist");
    				canvas.displayProfile(myData.getProfile(currentUser));
    			}
    		} else
    			// current profile is not valid
    			canvas.showMessage("Please select a profile to add friend");
    	}
    }
    
    private JTextField tf_name, tf_status, tf_pic, tf_friend;
    private JButton add, delete, lookup, status, pic, friend;
    private FacePamphletDatabase myData;
    private String currentUser;
    private FacePamphletCanvas canvas;
}
