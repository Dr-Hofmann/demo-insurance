package com.insurance.repository;


import java.util.List;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.insurance.entity.Client;

public interface ClientRepository extends CrudRepository<Client, String> {

	@Query("Select * from client where firstname=?0 ALLOW FILTERING")
	Client findByFirstName(String firstName);

	@Query("Select * from client where lastname=?0 ALLOW FILTERING")
	List<Client> findByLastName(String lastName);

}