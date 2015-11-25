package edu.neu.cs5500.jerks.dbProviders.test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.neu.cs5500.Jerks.dbProviders.AddressProvider;
import edu.neu.cs5500.Jerks.definitions.Address;
import edu.neu.cs5500.Jerks.resources.test.TestRandom;
import java.util.List;
import junit.framework.Assert;

public class AddressProviderTest {

	TestRandom rand = new TestRandom();
	AddressProvider addrDao = new AddressProvider();
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test()
	public void testCreateAddressWithAllFields() {
		Address addr = new Address(rand.nextAlphaNumStr(10), rand.nextAlphaNumStr(5), rand.nextStr(5), rand.nextStr(2),
				rand.nextStr(2), rand.nextNum(5), rand.nextFloat(2, 3), rand.nextFloat(2, 3));
		Address clone = addr.clone();
		addrDao.createAddress(clone);
		addr.setAddressId(clone.getAddressId());
		System.out.println("Expected Address:\n" + addr.toString() + "\nActual Address:\n" + clone.toString());
		assertAddress(addr, clone);
	}

	@Test()
	public void testCreateAddressWithEmptyAddress() {
		Address addr = new Address();
		Address clone = addr.clone();
		addrDao.createAddress(clone);
		addr.setAddressId(clone.getAddressId());
		System.out.println("Expected Address:\n" + addr.toString() + "\nActual Address:\n" + clone.toString());
		assertAddress(addr, clone);
	}

	@Test()
	public void testCreateAddressPassingAddressId() {
		Address addr = new Address();
		addr.setAddressId(rand.nextInt(1000000000));
		Address clone = addr.clone();
		addrDao.createAddress(clone);
		Assert.assertNotSame("The address Id is same!!! should be auto-generated", addr.getAddressId(),
				clone.getAddressId());
	}

	@Test()
	public void testCreateAddressWithLongAddressLine1() {
		Address addr = new Address();
		addr.setAddressLine1(rand.nextAlphaNumStr(255));
		Address clone = addr.clone();
		addrDao.createAddress(clone);
		Assert.assertEquals("The address line 1 is not the same!!", addr.getAddressLine1(), clone.getAddressLine1());
	}

	@Test()
	public void testCreateAddressWithInvalidAddressLine1Length() {
		Address addr = new Address();
		addr.setAddressLine1(rand.nextAlphaNumStr(256));
		Address clone = addr.clone();
		exception.expect(javax.persistence.RollbackException.class);
		addrDao.createAddress(clone);
	}

	@Test()
	public void testCreateAddressWithLongAddressLine2() {
		Address addr = new Address();
		addr.setAddressLine2(rand.nextAlphaNumStr(255));
		Address clone = addr.clone();
		addrDao.createAddress(clone);
		Assert.assertEquals("The address line 2 is not the same!!", addr.getAddressLine2(), clone.getAddressLine2());
	}

	@Test()
	public void testCreateAddressWithInvalidAddressLine2Length() {
		Address addr = new Address();
		addr.setAddressLine2(rand.nextAlphaNumStr(256));
		Address clone = addr.clone();
		exception.expect(javax.persistence.RollbackException.class);
		addrDao.createAddress(clone);
	}

	@Test()
	public void testCreateAddressWithLongCityName() {
		Address addr = new Address();
		addr.setCity(rand.nextAlphaNumStr(50));
		Address clone = addr.clone();
		addrDao.createAddress(clone);
		Assert.assertEquals("The city name is not the same!!", addr.getCity(), clone.getCity());
	}

	@Test()
	public void testCreateAddressWithInvalidCityLength() {
		Address addr = new Address();
		addr.setCity(rand.nextAlphaNumStr(51));
		Address clone = addr.clone();
		exception.expect(javax.persistence.RollbackException.class);
		addrDao.createAddress(clone);
	}

	@Test()
	public void testCreateAddressWithInvalidCountryLength() {
		Address addr = new Address();
		addr.setCountry(rand.nextAlphaNumStr(3));
		Address clone = addr.clone();
		exception.expect(javax.persistence.RollbackException.class);
		addrDao.createAddress(clone);
	}

	@Test()
	public void testCreateAddressWithInvalidStateLength() {
		Address addr = new Address();
		addr.setState(rand.nextAlphaNumStr(3));
		Address clone = addr.clone();
		exception.expect(javax.persistence.RollbackException.class);
		addrDao.createAddress(clone);
	}

	@Test()
	public void testCreateAddressWithInvalidZipcodeLength() {
		Address addr = new Address();
		addr.setZipCode(rand.nextAlphaNumStr(6));
		Address clone = addr.clone();
		exception.expect(javax.persistence.RollbackException.class);
		addrDao.createAddress(clone);
	}

	@Test()
	public void testFindAllAddresses() {
		List<Address> addresses = addrDao.findAllAddresses();
		Assert.assertTrue("List of addresses is negative!!", addresses.size() > -1);
	}

	@Test()
	public void testFindAllAddressesWithAddressId() {
		Address addr = new Address();
		addrDao.createAddress(addr);
		List<Address> addresses = addrDao.findAllAddresses();
		boolean found = false;
		for (Address a : addresses) {
			if (a.getAddressId() == addr.getAddressId()) {
				found = true;
				break;
			}
		}
		Assert.assertTrue("Address not found !!!", found);
	}

	@Test()
	public void testUpdateAddressWithNegativeAddressId() {

		Address addr = addrDao.updateAddress(-1, new Address());
		Assert.assertNull("Able to find address with negative address Id", addr);
	}
	
	@Test()
	public void testUpdateAddressWithAddressIdAsZero() {

		Address addr = addrDao.updateAddress(0, new Address());
		Assert.assertNull("Able to find address with address Id as zero", addr);
	}
	
	@Test()
	public void testUpdateAddressWithInvalidAddressId() {

		Address addr = addrDao.updateAddress(Integer.parseInt(rand.nextNum(5)), new Address());
		Assert.assertNull("Invalid address found !!!", addr);
	}
	
	@Test()
	public void testUpdateAddress() {
		Address addr = new Address();
		addrDao.createAddress(addr);
		addr.setAddressLine1(rand.nextAlphaNumStr(20));
		addr.setAddressLine2(rand.nextAlphaNumStr(20));
		Address clone = addr.clone();
		addrDao.updateAddress(clone.getAddressId(), clone);
		System.out.println("Expected Address:\n" + addr.toString() + "\nActual Address:\n" + clone.toString());
		assertAddress(addr, clone);
	}

	private void assertAddress(Address expected, Address actual) {

		Assert.assertEquals("The addressId is incorrect!!!", expected.getAddressId(), actual.getAddressId());
		Assert.assertEquals("The addressLine1 is incorrect!!!", expected.getAddressLine1(), actual.getAddressLine1());
		Assert.assertEquals("The addressLine2 is incorrect!!!", expected.getAddressLine2(), actual.getAddressLine2());
		Assert.assertEquals("The city is incorrect!!!", expected.getCity(), actual.getCity());
		Assert.assertEquals("The state is incorrect!!!", expected.getState(), actual.getState());
		Assert.assertEquals("The country is incorrect!!!", expected.getCountry(), actual.getCountry());
		Assert.assertEquals("The zipCode is incorrect!!!", expected.getZipCode(), actual.getZipCode());
		Assert.assertEquals("The latitude is incorrect!!!", expected.getLatitude(), actual.getLatitude());
		Assert.assertEquals("The longitude is incorrect!!!", expected.getLongitude(), actual.getLongitude());
	}

}
