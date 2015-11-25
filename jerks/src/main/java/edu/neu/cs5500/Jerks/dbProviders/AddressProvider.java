package edu.neu.cs5500.Jerks.dbProviders;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import edu.neu.cs5500.Jerks.definitions.*;
import edu.neu.cs5500.Jerks.resources.test.TestRandom;

/* Author: Karthik Chandranna
 * Creation Date: 11/02/2015 6:04 AM EST
 * Description: All address related database calls and methods goes into this class 
 * */
public class AddressProvider {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("WHAMv0.1");
	EntityManager em = null;


	public AddressProvider() {
		em = factory.createEntityManager();
	}


	public void createAddress(Address address)
	{
		em.getTransaction().begin();
		em.persist(address);
		em.getTransaction().commit();
	}

	
	@SuppressWarnings("unchecked")
	public List<Address> findAllAddresses() { 

		em.getTransaction().begin();
		Query query = em.createQuery("select a from Address a");
		List<Address> addresses = (List<Address>)query.getResultList();
		em.getTransaction().commit();
		return addresses;
	}


	public Address updateAddress(int addressId, Address address) {
		em.getTransaction().begin();
		Address a = em.find(Address.class, addressId);
		if(a!=null){
			address.setAddressId(addressId);			
			em.merge(address);
		}
		em.getTransaction().commit();
		return a;
	}

	
	public static void main(String[] args) {
		
		TestRandom rand = new TestRandom();
		AddressProvider dao = new AddressProvider();
		Address address = new Address(rand.nextAlphaNumStr(10), rand.nextAlphaNumStr(5), rand.nextStr(5), rand.nextStr(2), rand.nextStr(2), rand.nextNum(5), rand.nextFloat(2,3), rand.nextFloat(2,3));		
		dao.createAddress(address);
		System.out.println(address.toString());
		
		address.setAddressLine1(rand.nextAlphaNumStr(10));
		address = dao.updateAddress(address.getAddressId(), address);
		System.out.println(address.toString());
		
		for(Address a : dao.findAllAddresses()){
			System.out.println(a.toString());
		}
	}

}
