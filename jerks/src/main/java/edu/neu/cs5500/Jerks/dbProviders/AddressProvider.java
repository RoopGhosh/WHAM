package edu.neu.cs5500.Jerks.dbProviders;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import edu.neu.cs5500.Jerks.definitions.*;

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
		List<Address> address = (List<Address>)query.getResultList();
		em.getTransaction().commit();
		return address;
	}


	public Address updateAddress(int addressId, Address address) {
		em.getTransaction().begin();
		Address a = em.find(Address.class, addressId);
		if(a!=null){
			address.setAddressId(addressId);			
			em.merge(address);
		}
		em.getTransaction().commit();
		return address;
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
