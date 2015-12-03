package edu.neu.cs5500.Jerks.dbProviders;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import edu.neu.cs5500.Jerks.definitions.*;

/* Author: Karthik Chandranna
 * Creation Date: 12/02/2015 6:04 AM EST
 * Description: All user related database calls and methods goes into this class 
 * */

public class EventVisitedProvider {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("WHAMv0.1");
	EntityManager em = null;

	public EventVisitedProvider() {
		em = factory.createEntityManager();
	}

	public void createEventsVisted(EventVisited eventVisited) {
		em.getTransaction().begin();
		em.persist(eventVisited);
		em.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<EventVisited> findAllEventsVisited() {

		em.getTransaction().begin();
		Query query = em.createQuery("select ev from EventVisited ev");
		List<EventVisited> eventsVisited = (List<EventVisited>) query.getResultList();
		em.getTransaction().commit();
		return eventsVisited;
	}
	
	@SuppressWarnings("unchecked")
	public List<EventVisited> findAllEventsVisitedBy(String email) {

		if (email != null) {
			em.getTransaction().begin();
			Query query = em.createQuery("select ev from EventVisited ev where ev.email=:email");
			query.setParameter("email", email);
			List<EventVisited> eventsVisited = (List<EventVisited>) query.getResultList();
			em.getTransaction().commit();
			return eventsVisited;
		}
		return null;
	}

	public static void main(String[] args) {
		//TestRandom rand = new TestRandom();
		EventVisitedProvider dao = new EventVisitedProvider();
		String email = "test2@test.com";
		EventVisited eventVisited = new EventVisited(email, "fjaskld3947ashjdhasd92384jk", EventSource.WHAM);
		dao.createEventsVisted(eventVisited);
		eventVisited = new EventVisited(email, "aksdj846asdj238947ryahsr239asdj", EventSource.WHAM);
		dao.createEventsVisted(eventVisited);
		for (EventVisited ev : dao.findAllEventsVisited()) {
			System.out.println(ev.toString());
		}
		for (EventVisited ev : dao.findAllEventsVisitedBy(email)) {
			System.out.println(ev.toString());
		}
	}

}
