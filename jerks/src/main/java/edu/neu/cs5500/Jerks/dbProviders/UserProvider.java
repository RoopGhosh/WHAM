package edu.neu.cs5500.Jerks.dbProviders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import edu.neu.cs5500.Jerks.definitions.Address;
import edu.neu.cs5500.Jerks.definitions.EventCategory;
import edu.neu.cs5500.Jerks.definitions.User;

/* Author: Karthik Chandranna
 * Creation Date: 11/02/2015 6:04 AM EST
 * Description: All user related database calls and methods goes into this class 
 * */
public class UserProvider {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("WHAMv0.1");
	EntityManager em = null;


	public UserProvider() {
		em = factory.createEntityManager();
	}


	public User createUser(User user)
	{
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		return user;
	}

	public User findByEmail(String email)
	{
		User user = null;
		em.getTransaction().begin();
		user= em.find(User.class, email);
		if(user!=null && user.getEmail()!=null)
		{
			em.getTransaction().commit();
			return user;
		}
		em.getTransaction().commit();
		return null;

	}


	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() { 

		em.getTransaction().begin();
		Query query = em.createQuery("select u from User u");
		List<User> users = (List<User>)query.getResultList();
		em.getTransaction().commit();
		return users;
	}


	public void deleteUser(String email) {
		em.getTransaction().begin();
		User user = em.find(User.class, email);
		em.remove(user);
		em.getTransaction().commit();
	}

	public User updateUser(String email, User updateUser) {
		em.getTransaction().begin();
		User u = em.find(User.class, email);
		if(email.equals(updateUser.getEmail()) && u!=null){
			em.merge(updateUser);
		}
		em.getTransaction().commit();
		return updateUser;
	}

	public static void main(String[] args) {
		UserProvider dao = new UserProvider();
		Address address = new Address("200 Beacon St", "Apt 1", "Gotham", "MA", "US", "02115", 42.337f, -71.072f);		
		List<String> areaOfInterest = new ArrayList<>();
		areaOfInterest.add(EventCategory.music.toString());
		areaOfInterest.add(EventCategory.holiday.toString());
		List<String> disLikes = new ArrayList<>();
		disLikes.add(EventCategory.politics_activism.toString());
		disLikes.add(EventCategory.religion_spirituality.toString());
		User newUser = new User("brucewayne2@batman.movie", "Bruce", "Wayne", "pwd1234", address, "6179991111", new GregorianCalendar(1990, Calendar.MARCH, 11).getTime(), "M", areaOfInterest, disLikes);
		User user = dao.createUser(newUser);
		System.out.println(user.toString());
		
		user.setPassword("batmanPwd1234");
		user = dao.updateUser(user.getEmail(), user);
		System.out.println(user.toString());
		
		System.out.println(dao.findByEmail(user.getEmail()).toString());
		
		for(User u : dao.findAllUsers()){
			System.out.println(u.toString());
		}
		 
		//dao.deleteUser(user.getEmail());
		
	}
}
