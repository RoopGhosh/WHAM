package edu.neu.cs5500.Jerks.dbProviders;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import edu.neu.cs5500.Jerks.definitions.Address;
import edu.neu.cs5500.Jerks.definitions.EventCategory;
import edu.neu.cs5500.Jerks.definitions.User;
import edu.neu.cs5500.jerks.business.test.TestRandom;

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

	public void createUser(User user) {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
	}

	public User findByEmail(String email) {
		if (email != null) {
			User user = null;
			em.getTransaction().begin();
			user = em.find(User.class, email);
			if (user != null && user.getEmail() != null) {
				em.getTransaction().commit();
				return user;
			}
			em.getTransaction().commit();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {

		em.getTransaction().begin();
		Query query = em.createQuery("select u from User u");
		List<User> users = (List<User>) query.getResultList();
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
		if (email.equals(updateUser.getEmail()) && u != null) {
			em.merge(updateUser);
		}
		em.getTransaction().commit();
		return u;
	}

	public static void main(String[] args) {

		TestRandom rand = new TestRandom();
		UserProvider dao = new UserProvider();
		Address address = new Address(rand.nextAlphaNumStr(10), rand.nextAlphaNumStr(5), rand.nextStr(5),
				rand.nextStr(2), rand.nextStr(2), rand.nextNum(5), rand.nextFloat(2, 3), rand.nextFloat(2, 3));
		List<String> areaOfInterest = new ArrayList<>();
		areaOfInterest.add(EventCategory.music.toString());
		areaOfInterest.add(EventCategory.holiday.toString());
		List<String> disLikes = new ArrayList<>();
		disLikes.add(EventCategory.politics_activism.toString());
		disLikes.add(EventCategory.religion_spirituality.toString());
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				rand.nextNum(10), rand.nextDate(), "M", areaOfInterest, disLikes);
		dao.createUser(user);
		System.out.println(user.toString());

		user.setPassword(rand.nextAlphaNumStr(15));
		user = dao.updateUser(user.getEmail(), user);
		System.out.println(user.toString());

		System.out.println(dao.findByEmail(user.getEmail()).toString());

		for (User u : dao.findAllUsers()) {
			System.out.println(u.toString());
		}

		// dao.deleteUser(user.getEmail());

	}
}
