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
		User u = userDao.updateUser(username, user);		
		System.out.println(u.toString());
		System.out.println("updated user dislike successfully");
	}
	
	/*public static void main(String[] args) {
		UserProvider dao = new UserProvider();
		Address address = new Address("38479 234872348 hsdasu", "asfasd", "asdfas", "MA", "US", "02115", 42.337f, -71.072f);		
		List<String> areaOfInterest = new ArrayList<>();
		areaOfInterest.add(EventCategory.music.toString());
		areaOfInterest.add(EventCategory.holiday.toString());
		List<String> disLikes = new ArrayList<>();
		disLikes.add(EventCategory.politics_activism.toString());
		disLikes.add(EventCategory.religion_spirituality.toString());
		User newUser = new User("2313@3424.com", "asd", "asdf", "pwd1234", address, "6179991111", new GregorianCalendar(1990, Calendar.MARCH, 11).getTime(), "M", areaOfInterest, disLikes);
		User user = dao.createUser(newUser);
		UserManager um =  new UserManager();
		System.out.println(user.toString());
		um.dislikeEvent(user.getEmail().toString(), EventCategory.books.toString());
		System.out.println(dao.findByEmail(user.getEmail()).toString());
		
	}*/
}
