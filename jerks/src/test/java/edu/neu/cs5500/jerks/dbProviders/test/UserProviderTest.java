/*package edu.neu.cs5500.jerks.dbProviders.test;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import edu.neu.cs5500.Jerks.dbProviders.*;
import edu.neu.cs5500.Jerks.definitions.*;
import edu.neu.cs5500.jerks.business.test.TestRandom;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;

 Author: Karthik Chandranna
 * Creation Date: 11/25/2015 6:04 PM EST
 * Description: UserProvider tests
 * 
public class UserProviderTest {

	TestRandom rand = new TestRandom();
	AddressProvider addrDao = new AddressProvider();
	UserProvider userDao = new UserProvider();
	Address address;
	List<String> areaOfInterest = new ArrayList<>();
	List<String> disLikes = new ArrayList<>();
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Before
	public void beforeMethod() {
		address = new Address(rand.nextAlphaNumStr(10), rand.nextAlphaNumStr(5), rand.nextStr(5), rand.nextStr(2),
				rand.nextStr(2), rand.nextNum(5), rand.nextFloat(2, 3), rand.nextFloat(2, 3));
		addrDao.createAddress(address);
		System.out.println(address.toString());
		areaOfInterest.add(EventCategory.music.toString());
		areaOfInterest.add(EventCategory.holiday.toString());
		disLikes.add(EventCategory.politics_activism.toString());
		disLikes.add(EventCategory.religion_spirituality.toString());

	}

	@Test()
	public void testCreateUserWithAllFields() {
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				rand.nextNum(10), rand.nextDate(), "M", areaOfInterest, disLikes);
		User clone = user.clone();
		userDao.createUser(clone);
		System.out.println("Expected Address:\n" + user.toString() + "\nActual Address:\n" + clone.toString());
		assertUser(user, clone);
	}

	@Test()
	public void testCreateUserWithEmptyFields() {
		User user = new User();
		exception.expect(javax.persistence.RollbackException.class);
		userDao.createUser(user);
	}

	@Test()
	public void testCreateUserWithEmailAsNull() {
		User user = new User(null, rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				rand.nextNum(10), rand.nextDate(), "M", areaOfInterest, disLikes);
		exception.expect(javax.persistence.RollbackException.class);
		userDao.createUser(user);
	}

	@Test()
	public void testCreateUserWithInvalidEmail() {
		User user = new User(rand.nextNum(256), rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				rand.nextNum(10), rand.nextDate(), "M", areaOfInterest, disLikes);
		exception.expect(javax.persistence.RollbackException.class);
		userDao.createUser(user);
	}

	@Test()
	public void testCreateUserWithFNameAsNull() {
		User user = new User(rand.nextEmail(), null, rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				rand.nextNum(10), rand.nextDate(), "M", areaOfInterest, disLikes);
		exception.expect(javax.persistence.RollbackException.class);
		userDao.createUser(user);
	}

	@Test()
	public void testCreateUserWithInvalidFName() {
		User user = new User(rand.nextEmail(), rand.nextStr(51), rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				rand.nextNum(10), rand.nextDate(), "M", areaOfInterest, disLikes);
		exception.expect(javax.persistence.RollbackException.class);
		userDao.createUser(user);
	}

	@Test()
	public void testCreateUserWithLNameAsNull() {
		User user = new User(rand.nextEmail(), rand.nextStr(10), null, rand.nextAlphaNumStr(10), address,
				rand.nextNum(10), rand.nextDate(), "M", areaOfInterest, disLikes);
		exception.expect(javax.persistence.RollbackException.class);
		userDao.createUser(user);
	}

	@Test()
	public void testCreateUserWithInvalidLName() {
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextStr(51), rand.nextAlphaNumStr(10), address,
				rand.nextNum(10), rand.nextDate(), "M", areaOfInterest, disLikes);
		exception.expect(javax.persistence.RollbackException.class);
		userDao.createUser(user);
	}

	@Test()
	public void testCreateUserWithPwdAsNull() {
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextAlphaNumStr(10), null, address,
				rand.nextNum(10), rand.nextDate(), "M", areaOfInterest, disLikes);
		exception.expect(javax.persistence.RollbackException.class);
		userDao.createUser(user);
	}

	@Test()
	public void testCreateUserWithInvalidPwd() {
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(21), address,
				rand.nextNum(10), rand.nextDate(), "M", areaOfInterest, disLikes);
		exception.expect(javax.persistence.RollbackException.class);
		userDao.createUser(user);
	}

	@Test()
	public void testCreateUserWithAddressAsNull() {
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(10), null,
				rand.nextNum(10), rand.nextDate(), "M", areaOfInterest, disLikes);
		User clone = user.clone();
		userDao.createUser(clone);
		System.out.println("Expected Address:\n" + user.toString() + "\nActual Address:\n" + clone.toString());
		assertUser(user, clone);
	}

	@Test()
	public void testCreateUserWithPhoneNumAsNull() {
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				null, rand.nextDate(), "M", areaOfInterest, disLikes);
		User clone = user.clone();
		userDao.createUser(clone);
		System.out.println("Expected Address:\n" + user.toString() + "\nActual Address:\n" + clone.toString());
		assertUser(user, clone);
	}

	@Test()
	public void testCreateUserWithInvalidPhoneNum() {
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				rand.nextNum(11), rand.nextDate(), "M", areaOfInterest, disLikes);
		exception.expect(javax.persistence.RollbackException.class);
		userDao.createUser(user);
	}

	@Test()
	public void testCreateUserWithDateAsNull() {
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				rand.nextNum(10), null, "M", areaOfInterest, disLikes);
		User clone = user.clone();
		userDao.createUser(clone);
		System.out.println("Expected Address:\n" + user.toString() + "\nActual Address:\n" + clone.toString());
		assertUser(user, clone);
	}

	@Test()
	public void testCreateUserWithGenderAsNull() {
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				rand.nextNum(10), rand.nextDate(), null, areaOfInterest, disLikes);
		User clone = user.clone();
		userDao.createUser(clone);
		System.out.println("Expected Address:\n" + user.toString() + "\nActual Address:\n" + clone.toString());
		assertUser(user, clone);
	}

	@Test()
	public void testCreateUserWithInvalidGender() {
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				rand.nextNum(11), rand.nextDate(), "M", areaOfInterest, disLikes);
		exception.expect(javax.persistence.RollbackException.class);
		userDao.createUser(user);
	}

	@Test()
	public void testCreateUserWithInterestsAsNull() {
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				rand.nextNum(10), rand.nextDate(), "M", null, disLikes);
		User clone = user.clone();
		userDao.createUser(clone);
		System.out.println("Expected Address:\n" + user.toString() + "\nActual Address:\n" + clone.toString());
		assertUser(user, clone);
	}

	@Test()
	public void testCreateUserWithInvalidInterests() {
		areaOfInterest.add(rand.nextStr(1100));
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				rand.nextNum(11), rand.nextDate(), "M", areaOfInterest, disLikes);
		exception.expect(javax.persistence.RollbackException.class);
		userDao.createUser(user);
	}

	@Test()
	public void testCreateUserWithDislikesAsNull() {
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				rand.nextNum(10), rand.nextDate(), "M", areaOfInterest, null);
		User clone = user.clone();
		userDao.createUser(clone);
		System.out.println("Expected Address:\n" + user.toString() + "\nActual Address:\n" + clone.toString());
		assertUser(user, clone);
	}

	@Test()
	public void testCreateUserWithInvalidDislikes() {
		disLikes.add(rand.nextStr(1100));
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				rand.nextNum(11), rand.nextDate(), "M", areaOfInterest, disLikes);
		exception.expect(javax.persistence.RollbackException.class);
		userDao.createUser(user);
	}

	@Test
	public void testFindByEmailWithValidEmail() {
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				rand.nextNum(10), rand.nextDate(), "M", areaOfInterest, disLikes);
		userDao.createUser(user);
		User respUser = userDao.findByEmail(user.getEmail());
		System.out.println("Expected Address:\n" + user.toString() + "\nActual Address:\n" + respUser.toString());
		assertUser(user, respUser);
	}

	@Test
	public void testFindByEmailWithInvalidEmail() {
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				rand.nextNum(10), rand.nextDate(), "M", areaOfInterest, disLikes);
		userDao.createUser(user);
		User respUser = userDao.findByEmail(rand.nextNum(20));
		Assert.assertNull("Found an unexpected user !!", respUser);
	}

	@Test
	public void testFindByEmailWithEmailAsNull() {
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				rand.nextNum(10), rand.nextDate(), "M", areaOfInterest, disLikes);
		userDao.createUser(user);
		User respUser = userDao.findByEmail(null);
		Assert.assertNull("Found an unexpected user !!", respUser);
	}

	@Test()
	public void testFindAllUsers() {
		List<User> users = userDao.findAllUsers();
		Assert.assertTrue("List of users is negative!!", users.size() > -1);
	}

	@Test()
	public void testFindAllUsersFetchUser() {
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				rand.nextNum(10), rand.nextDate(), "M", areaOfInterest, disLikes);
		userDao.createUser(user);
		List<User> users = userDao.findAllUsers();
		boolean found = false;
		for (User u : users) {
			if (u.getEmail() == user.getEmail()) {
				found = true;
				break;
			}
		}
		Assert.assertTrue("User not found !!!", found);
	}

	@Test
	public void testDeleteUserWithValidEmail() {
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				rand.nextNum(10), rand.nextDate(), "M", areaOfInterest, disLikes);
		userDao.createUser(user);
		userDao.deleteUser(user.getEmail());
		User respUser = userDao.findByEmail(user.getEmail());
		Assert.assertNull("Found an unexpected user !!", respUser);
	}

	@Test
	public void testDeleteUserWithInvalidEmail() {
		exception.expect(IllegalArgumentException.class);
		userDao.deleteUser(rand.nextStr(256));
	}

	@Test
	public void testDeleteUserWithNonExistentEmail() {
		exception.expect(IllegalArgumentException.class);
		userDao.deleteUser(rand.nextStr(40));
	}

	@Test
	public void testDeleteUserWithEmailAsNull() {
		exception.expect(IllegalArgumentException.class);
		userDao.deleteUser(null);
	}

	@Test
	public void testUpdateUserWithValidEmail() {
		User user = new User(rand.nextEmail(), rand.nextStr(10), rand.nextStr(10), rand.nextAlphaNumStr(10), address,
				rand.nextNum(10), rand.nextDate(), "M", areaOfInterest, disLikes);
		userDao.createUser(user);
		user.setFirstName(rand.nextStr(15));
		User clone = user.clone();
		userDao.updateUser(clone.getEmail(), clone);
		System.out.println("Expected Address:\n" + user.toString() + "\nActual Address:\n" + clone.toString());
		assertUser(user, clone);
	}

	@Test
	public void testUpdateUserWithInvalidEmail() {
		User updateuser = userDao.updateUser(rand.nextStr(256), new User());
		Assert.assertNull("Illegal user update !!", updateuser);
	}

	@Test
	public void testUpdateUserWithNonExistentEmail() {
		User updateuser = userDao.updateUser(rand.nextStr(40), new User());
		Assert.assertNull("Illegal user update !!", updateuser);
	}

	@Test
	public void testUpdateUserWithEmailAsNull() {
		exception.expect(IllegalArgumentException.class);
		userDao.updateUser(null, new User());
	}

	private void assertUser(User expected, User actual) {

		Assert.assertEquals("The Email is incorrect!!!", expected.getEmail(), actual.getEmail());
		Assert.assertEquals("The First Name is incorrect!!!", expected.getFirstName(), actual.getFirstName());
		Assert.assertEquals("The Gender is incorrect!!!", expected.getGender(), actual.getGender());
		Assert.assertEquals("The Last Name is incorrect!!!", expected.getLastName(), actual.getLastName());
		Assert.assertEquals("The Password is incorrect!!!", expected.getPassword(), actual.getPassword());
		Assert.assertEquals("The Phone number is incorrect!!!", expected.getPhoneNumber(), actual.getPhoneNumber());
		Assert.assertEquals("The DOB is incorrect!!!", expected.getDOB(), actual.getDOB());
		if (expected.getAreaOfInterest() == null)
			Assert.assertNull("Expected areas of interest to be null !!", actual.getAreaOfInterest());
		else
			Assert.assertTrue("The Areas of interest is incorrect!!!",
					expected.getAreaOfInterest().containsAll(actual.getAreaOfInterest())
							&& actual.getAreaOfInterest().containsAll(expected.getAreaOfInterest()));

		if (expected.getDislikes() == null)
			Assert.assertNull("Expected dislikes to be null !!", actual.getDislikes());
		else
			Assert.assertTrue("The Dislikes are incorrect!!!", expected.getDislikes().containsAll(actual.getDislikes())
					&& actual.getDislikes().containsAll(expected.getDislikes()));
		AddressProviderTest.assertAddress(expected.getAddress(), actual.getAddress());
	}

}
*/