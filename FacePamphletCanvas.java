/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;

import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for the display
	 */
	public FacePamphletCanvas() {
		// You fill this in
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		removeAll();
		GLabel message = new GLabel(msg);
		message.setFont(MESSAGE_FONT);
		message.sendToFront();
		add(message, (APPLICATION_WIDTH - message.getWidth()) / 3, APPLICATION_HEIGHT - 120);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {		
		// Profile name in BLUE
		GLabel name = new GLabel(profile.getName());
		name.setColor(Color.BLUE);
		name.setFont(PROFILE_NAME_FONT);
		add(name, LEFT_MARGIN, TOP_MARGIN + name.getHeight());
		
		// Image
		if (profile.getImage() != null) {
			GImage image = profile.getImage();
			image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(image, LEFT_MARGIN, IMAGE_MARGIN + name.getY());
		} else {
			GRect image = new GRect(LEFT_MARGIN, IMAGE_MARGIN + name.getY(), IMAGE_WIDTH, IMAGE_HEIGHT);
			GLabel imgLabel = new GLabel("No Image");
			imgLabel.setFont(PROFILE_IMAGE_FONT);
			add(image);
			add(imgLabel, LEFT_MARGIN + image.getWidth()/2 - imgLabel.getWidth()/2, IMAGE_MARGIN + name.getY() + image.getHeight()/2);
		}
		
		// Status
		String status = profile.getStatus();
		if (status.equals(""))
			status = "No current status";
		else
			status = profile.getName() + " is " + status;
		GLabel statusLabel = new GLabel(status);
		statusLabel.setFont(PROFILE_STATUS_FONT);
		add(statusLabel, LEFT_MARGIN, IMAGE_MARGIN + name.getY() + IMAGE_HEIGHT + STATUS_MARGIN + statusLabel.getHeight());
		
		// Friends
		GLabel friendHeader = new GLabel("Friends:", APPLICATION_WIDTH/2, IMAGE_MARGIN + name.getY());
		friendHeader.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friendHeader);
		
		Iterator<String> it = profile.getFriends().iterator();
		double friendListMargin = IMAGE_MARGIN + name.getY();
		while (it.hasNext()) {
			String friendName = it.next();
			GLabel myFriend = new GLabel(friendName);
			friendListMargin += myFriend.getHeight();
			myFriend.setFont(PROFILE_FRIEND_FONT);
			add(myFriend, APPLICATION_WIDTH/2, friendListMargin);
		}
	}	
}
