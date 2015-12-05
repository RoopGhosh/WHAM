package edu.neu.cs5500.Jerks.business;
import java.util.*;

import edu.neu.cs5500.Jerks.dbProviders.*;
import edu.neu.cs5500.Jerks.definitions.*;

/* Author: Sandeep Ramamoorthy
 * Creation Date: 11/02/2015 6:04 AM EST
 * Description: All business logic related to user goes into this class 
 * */
public class UserManager {

	/*
	 * Description: Adds the provided event name to the
	 * dislikes of the user.
	 */
	public void dislikeEvent(String username, String eventName)
	{
		UserProvider userDao = new UserProvider();
		// Find the user object
		User user = userDao.findByEmail(username);
		// Update the dislikes of the user
		List<String> dislikes = user.getDislikes();
		dislikes.add(eventName);
		user.setDislikes(dislikes);
		// Save changes to DB
		userDao.updateUser(username, user);		
		System.out.println("updated user dislike successfully");
	}
}
