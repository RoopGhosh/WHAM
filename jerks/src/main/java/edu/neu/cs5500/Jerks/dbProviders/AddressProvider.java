package edu.neu.cs5500.Jerks.dbProviders;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import edu.neu.cs5500.Jerks.definitions.*;

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


	public Address createAddress(Address address)
	{
		em.getTransaction().begin();
		em.persist(address);
		em.getTransaction().commit();
		return address;
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
			em.getTransaction().commit();
			return a;
		}
		em.getTransaction().commit();
		return null;
	}

	
	public static void main(String[] args) {
		AddressProvider dao = new AddressProvider();
		Address newAddress = new Address("500 Boylston", "Apt 16", "Boston", "MA", "US", "02115", 42.337f, -71.072f);		
		Address address = dao.createAddress(newAddress);
		System.out.println(address.toString());
		
		address.setAddressLine1("360 Huntington");
		address = dao.updateAddress(address.getAddressId(), address);
		System.out.println(address.toString());
		
		for(Address a : dao.findAllAddresses()){
			System.out.println(a.toString());
		}
	}

}
