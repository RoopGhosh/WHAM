/*package edu.neu.cs5500.jerks.dbProviders.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.neu.cs5500.Jerks.dbProviders.AddressProvider;
import edu.neu.cs5500.Jerks.dbProviders.EventProvider;
import edu.neu.cs5500.Jerks.definitions.Address;
import edu.neu.cs5500.Jerks.definitions.Event;
import edu.neu.cs5500.Jerks.definitions.EventSource;
import edu.neu.cs5500.jerks.business.test.TestRandom;
import junit.framework.Assert;

public class EventProviderTest {

	TestRandom rand = new TestRandom();
	AddressProvider addrDao = new AddressProvider();
	EventProvider eventDao = new EventProvider();
	Address address;
	
	@Before
	public void beforeMethod() {
		address = new Address(rand.nextAlphaNumStr(10), rand.nextAlphaNumStr(5), rand.nextStr(5), rand.nextStr(2),
				rand.nextStr(2), rand.nextNum(5), rand.nextFloat(2, 3), rand.nextFloat(2, 3));
		addrDao.createAddress(address);
		System.out.println(address.toString());
	}

	@Test
	public void testCreateEventWithAllFields() throws InterruptedException {
		Thread.sleep(1000);
		Event event = new Event(rand.nextStr(25), rand.nextDate(), address, rand.nextStr(100), rand.nextDouble(),
				rand.nextInt(), rand.nextDouble(), rand.nextInt(), EventSource.WHAM);
		Event clone = event.clone();
		eventDao.createEvent(clone);
		System.out.println("Expected Event:\n" + event.toString() + "\nActual Event:\n" + clone.toString());
		assertEvent(event, clone);
	}

	@Test(expected = Exception.class)
	public void testCreateEventWithEmptyFields() {
		Event event = new Event();
		eventDao.createEvent(event);
	}

	@Test(expected = Exception.class)
	public void testCreateEventWithNameAsNull() {
		Event event = new Event(null, rand.nextDate(), address, rand.nextStr(100), rand.nextDouble(), rand.nextInt(),
				rand.nextDouble(), rand.nextInt(), EventSource.WHAM);
		eventDao.createEvent(event);
	}

	@Test(expected = Exception.class)
	public void testCreateEventWithInvalidName() {
		Event event = new Event(rand.nextStr(256), rand.nextDate(), address, rand.nextStr(100), rand.nextDouble(),
				rand.nextInt(), rand.nextDouble(), rand.nextInt(), EventSource.WHAM);
		eventDao.createEvent(event);
	}

	@Test(expected = Exception.class)
	public void testCreateEventWithDateAsNull() {
		Event event = new Event(rand.nextStr(25), null, address, rand.nextStr(100), rand.nextDouble(), rand.nextInt(),
				rand.nextDouble(), rand.nextInt(), EventSource.WHAM);
		eventDao.createEvent(event);
	}

	@Test()
	public void testCreateEventWithDescriptionAsNull() throws InterruptedException {
		Thread.sleep(1000);
		Event event = new Event(rand.nextStr(25), rand.nextDate(), address, null, rand.nextDouble(), rand.nextInt(),
				rand.nextDouble(), rand.nextInt(), EventSource.WHAM);
		Event clone = event.clone();
		eventDao.createEvent(clone);
		System.out.println("Expected Event:\n" + event.toString() + "\nActual Event:\n" + clone.toString());
		assertEvent(event, clone);
	}

	@Test(expected = Exception.class)
	public void testCreateEventWithInvalidDescription() {
		Event event = new Event(rand.nextStr(256), rand.nextDate(), address, rand.nextStr(8001), rand.nextDouble(),
				rand.nextInt(), rand.nextDouble(), rand.nextInt(), EventSource.WHAM);
		eventDao.createEvent(event);
	}

	@Test(expected = Exception.class)
	public void testCreateEventWithSourceAsNull() {
		Event event = new Event(rand.nextStr(25), rand.nextDate(), address, rand.nextStr(100), rand.nextDouble(),
				rand.nextInt(), rand.nextDouble(), rand.nextInt(), null);
		eventDao.createEvent(event);
	}

	@Test(expected = Exception.class)
	public void testCreateEventWithAddressAsNull() {
		Event event = new Event(rand.nextStr(25), rand.nextDate(), null, rand.nextStr(100), rand.nextDouble(),
				rand.nextInt(), rand.nextDouble(), rand.nextInt(), EventSource.WHAM);
		eventDao.createEvent(event);
	}

	@Test
	public void testFindByIdWithValidEventId() throws InterruptedException {
		Thread.sleep(1000);
		Event event = new Event(rand.nextStr(25), rand.nextDate(), address, rand.nextStr(100), rand.nextDouble(),
				rand.nextInt(), rand.nextDouble(), rand.nextInt(), EventSource.WHAM);
		eventDao.createEvent(event);
		Event respEvent = eventDao.findById(event.getEventId());
		System.out.println("Expected Event:\n" + event.toString() + "\nActual Event:\n" + respEvent.toString());
		assertEvent(event, respEvent);
	}

	@Test
	public void testFindByIdWithInvalidEventId() throws InterruptedException {
		Thread.sleep(1000);
		Event event = new Event(rand.nextStr(25), rand.nextDate(), address, rand.nextStr(100), rand.nextDouble(),
				rand.nextInt(), rand.nextDouble(), rand.nextInt(), EventSource.WHAM);
		eventDao.createEvent(event);
		Event respEvent = eventDao.findById(rand.nextAlphaNumStr(20));
		Assert.assertNull("Found an unexpected event !!", respEvent);
	}

	@Test
	public void testFindByIdWithEventIdAsNull() throws InterruptedException {
		Thread.sleep(1000);
		Event event = new Event(rand.nextStr(25), rand.nextDate(), address, rand.nextStr(100), rand.nextDouble(),
				rand.nextInt(), rand.nextDouble(), rand.nextInt(), EventSource.WHAM);
		eventDao.createEvent(event);
		Event respEvent = eventDao.findById(null);
		Assert.assertNull("Found an unexpected event !!", respEvent);
	}

	@Test()
	public void testFindAllEvents() {
		List<Event> events = eventDao.findAllEvents();
		Assert.assertTrue("List of events is negative!!", events.size() > -1);
	}

	@Test()
	public void testFindAllEventsFetchEvent() throws InterruptedException {
		Thread.sleep(1000);
		Event event = new Event(rand.nextStr(25), rand.nextDate(), address, rand.nextStr(100), rand.nextDouble(),
				rand.nextInt(), rand.nextDouble(), rand.nextInt(), EventSource.WHAM);
		eventDao.createEvent(event);
		List<Event> events = eventDao.findAllEvents();
		boolean found = false;
		for (Event e : events) {
			if (e.getEventId() == event.getEventId()) {
				found = true;
				break;
			}
		}
		Assert.assertTrue("Event not found !!!", found);
	}

	@Test
	public void testDeleteEventWithValidEventId() throws InterruptedException {
		Thread.sleep(1000);
		Event event = new Event(rand.nextStr(25), rand.nextDate(), address, rand.nextStr(100), rand.nextDouble(),
				rand.nextInt(), rand.nextDouble(), rand.nextInt(), EventSource.WHAM);
		eventDao.createEvent(event);
		eventDao.deleteEvent(event.getEventId());
		Event respEvent = eventDao.findById(event.getEventId());
		Assert.assertNull("Found an unexpected event !!", respEvent);
	}

	@Test(expected = Exception.class)
	public void testDeleteEventWithInvalidEventId() {
		eventDao.deleteEvent(rand.nextStr(256));
	}

	@Test(expected = Exception.class)
	public void testDeleteEventWithNonExistentEventId() {
		eventDao.deleteEvent(rand.nextNum(40));
	}

	@Test(expected = Exception.class)
	public void testDeleteEventWithEventIdAsNull() {
		eventDao.deleteEvent(null);
	}

	@Test
	public void testUpdateEventWithValidEventId() throws InterruptedException {
		Thread.sleep(1000);
		Event event = new Event(rand.nextStr(25), rand.nextDate(), address, rand.nextStr(100), rand.nextDouble(),
				rand.nextInt(), rand.nextDouble(), rand.nextInt(), EventSource.WHAM);
		eventDao.createEvent(event);
		event.setName(rand.nextStr(35));
		Event clone = event.clone();
		eventDao.updateEvent(event.getEventId(), clone);
		System.out.println("Expected Event:\n" + event.toString() + "\nActual Event:\n" + clone.toString());
		assertEvent(event, clone);
	}

	@Test
	public void testUpdateEventWithInvalidEventId() {
		Event updateEvent = eventDao.updateEvent(rand.nextStr(256), new Event());
		Assert.assertNull("Illegal event update !!", updateEvent);
	}

	@Test
	public void testUpdateEventWithNonExistentEventId() {
		Event updateEvent = eventDao.updateEvent(rand.nextNum(20), new Event());
		Assert.assertNull("Illegal event update !!", updateEvent);
	}

	@Test(expected = Exception.class)
	public void testUpdateEventWithEventIdAsNull() {
		eventDao.updateEvent(null, new Event());
	}

	private void assertEvent(Event expected, Event actual) {

		Assert.assertEquals("The Event ID is incorrect!!!", expected.getEventId(), actual.getEventId());
		Assert.assertEquals("The Date is incorrect!!!", expected.getDate(), actual.getDate());
		Assert.assertEquals("The Event Description is incorrect!!!", expected.getDescription(),
				actual.getDescription());
		Assert.assertEquals("The Minimum age limit is incorrect!!!", expected.getMinAgeLimit(),
				actual.getMinAgeLimit());
		Assert.assertEquals("The Event Name is incorrect!!!", expected.getName(), actual.getName());
		Assert.assertEquals("The Event rating is incorrect!!!", expected.getRating(), actual.getRating());
		Assert.assertEquals("The number of remaining tickets is incorrect!!!", expected.getRemainingTickets(),
				actual.getRemainingTickets());
		Assert.assertEquals("The Event source is incorrect!!!", expected.getSource(), actual.getSource());
		Assert.assertEquals("The Ticket price is incorrect!!!", expected.getTicketPrice(), actual.getTicketPrice());
		AddressProviderTest.assertAddress(expected.getAddress(), actual.getAddress());
	}

}
*/