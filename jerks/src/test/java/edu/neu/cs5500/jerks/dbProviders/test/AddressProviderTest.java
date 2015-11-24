package edu.neu.cs5500.jerks.dbProviders.test;

import org.junit.Test;
import edu.neu.cs5500.Jerks.dbProviders.AddressProvider;
import edu.neu.cs5500.Jerks.definitions.Address;
import edu.neu.cs5500.Jerks.resources.test.TestRandom;
import junit.framework.Assert;

public class AddressProviderTest {
	
	TestRandom rand = new TestRandom();
	
	@Test()
	public void testCreateAddressBasic(){
		//Address addr = new Address("address line 1", "address Line 2","boston", "MA", "US", "02115", 42.337f, -71.072f);
		Address addr = new Address(rand.nextAlphaNumStr(10), rand.nextAlphaNumStr(5), rand.nextStr(5), rand.nextStr(2), rand.nextStr(2), rand.nextNum(5), rand.nextFloat(2,3), rand.nextFloat(2,3));
		AddressProvider addrDao = new AddressProvider();
		Address newAddr = addrDao.createAddress(addr);
		addr.setAddressId(newAddr.getAddressId());
		System.out.println("Actual Address:\n" + newAddr.toString() +"\nExpected Address:\n" + addr.toString());
		assertAddress(newAddr, addr);
	}
	
	@Test()
	public void testCreateAddressWithEmptyAddress(){
		Address addr = new Address();
		AddressProvider addrDao = new AddressProvider();
		Address newAddr = addrDao.createAddress(addr);
		addr.setAddressId(newAddr.getAddressId());
		System.out.println("Actual Address:\n" + newAddr.toString() +"\nExpected Address:\n" + addr.toString());
		assertAddress(newAddr, addr);
	}

	private void assertAddress(Address actual, Address expected) {
		
		Assert.assertEquals("The addressId is incorrect!!!", actual.getAddressId(), expected.getAddressId());
		Assert.assertEquals("The addressLine1 is incorrect!!!", actual.getAddressLine1(), expected.getAddressLine1());
		Assert.assertEquals("The addressLine2 is incorrect!!!", actual.getAddressLine2(), expected.getAddressLine2());
		Assert.assertEquals("The city is incorrect!!!", actual.getCity(), expected.getCity());
		Assert.assertEquals("The state is incorrect!!!", actual.getState(), expected.getState());
		Assert.assertEquals("The country is incorrect!!!", actual.getCountry(), expected.getCountry());
		Assert.assertEquals("The zipCode is incorrect!!!", actual.getZipCode(), expected.getZipCode());
		Assert.assertEquals("The latitude is incorrect!!!", actual.getLatitude(), expected.getLatitude());
		Assert.assertEquals("The longitude is incorrect!!!", actual.getLongitude(), expected.getLongitude());
	}

}
