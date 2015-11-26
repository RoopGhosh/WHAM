package edu.neu.cs5500.Jerks.dbProviders;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import edu.neu.cs5500.Jerks.definitions.Address;
import edu.neu.cs5500.Jerks.definitions.Event;
import edu.neu.cs5500.Jerks.definitions.EventSource;

/* Author: Karthik Chandranna
 * Creation Date: 11/02/2015 6:04 AM EST
 * Description: All event related database calls and methods goes into this class 
 * */
public class EventProvider {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("WHAMv0.1");
	EntityManager em = null;

	public EventProvider() {
		em = factory.createEntityManager();
	}

	public Event createEvent(Event event) {
		em.getTransaction().begin();
		em.persist(event);
		em.getTransaction().commit();
		return event;
	}

	public Event findById(String eventId) {
		Event event = null;
		em.getTransaction().begin();
		event = em.find(Event.class, eventId);
		if (event != null && event.getName() != null) {
			em.getTransaction().commit();
			return event;
		}
		em.getTransaction().commit();
		return null;

	}

	@SuppressWarnings("unchecked")
	public List<Event> findAllEvents() {

		em.getTransaction().begin();
		Query query = em.createQuery("select e from Event e");
		List<Event> events = (List<Event>) query.getResultList();
		em.getTransaction().commit();
		return events;
	}

	public void deleteEvent(String eventId) {
		em.getTransaction().begin();
		Event event = em.find(Event.class, eventId);
		em.remove(event);
		em.getTransaction().commit();
	}

	public Event updateEvent(String eventId, Event event) {
		em.getTransaction().begin();
		Event e = em.find(Event.class, eventId);
		if (e != null) {
			event.setEventId(eventId);
			em.merge(event);
		}
		em.getTransaction().commit();
		return event;
	}

	public static void main(String[] args) {
		EventProvider dao = new EventProvider();
		Address address = new Address("400 Huntington St", "Apt 1", "Gotham", "MA", "US", "02115", 42.337f, -71.072f);
		Event newEvent = new Event("event1", new Date(), address, "this is event 1", 0.0, 21, 5.0, 100,
				EventSource.WHAM);
		Event event = dao.createEvent(newEvent);
		System.out.println(event.toString());

		event.setDescription("This is THE event 1 !!");
		event = dao.updateEvent(event.getEventId(), event);
		System.out.println(event.toString());

		System.out.println(dao.findById(event.getEventId()).toString());

		for (Event e : dao.findAllEvents()) {
			System.out.println(e.toString());
		}

		// dao.deleteEvent(event.getEventId());
	}

}
