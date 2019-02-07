package com.insurance;

import java.util.Arrays;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import com.datastax.driver.core.utils.UUIDs;
import com.insurance.dto.Customer;
import com.insurance.dto.Person;
import com.insurance.entity.Client;
import com.insurance.repository.ClientRepository;
import com.insurance.repository.CustomerRepository;
import com.insurance.repository.PersonRepository;

@SpringBootApplication
@EnableNeo4jRepositories
public class InsuranceDemoApplication implements CommandLineRunner{
	
	private final static Logger log = LoggerFactory.getLogger(InsuranceDemoApplication.class);

	@Autowired
	private CustomerRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(InsuranceDemoApplication.class, args);
	}

	
	
	
	@Bean
	CommandLineRunner demo(PersonRepository personRepository) {
		return args -> {

			personRepository.deleteAll();

			Person greg = new Person("Greg");
			Person roy = new Person("Roy");
			Person craig = new Person("Craig");

			List<Person> team = Arrays.asList(greg, roy, craig);

			log.info("Before linking up with Neo4j...");

			team.stream().forEach(person -> log.info("\t" + person.toString()));

			personRepository.save(greg);
			personRepository.save(roy);
			personRepository.save(craig);

			greg = personRepository.findByName(greg.getName());
			greg.worksWith(roy);
			greg.worksWith(craig);
			personRepository.save(greg);

			roy = personRepository.findByName(roy.getName());
			roy.worksWith(craig);
			// We already know that roy works with greg
			personRepository.save(roy);

			// We already know craig works with roy and greg

			log.info("Lookup each person by name...");
			team.stream().forEach(person -> log.info(
					"\t" + personRepository.findByName(person.getName()).toString()));
		};
	}
	
	@Bean 
	CommandLineRunner cassandraDemo(ClientRepository clientRepository) {
		return args ->{
			
			clientRepository.deleteAll();

			// save a couple of customers
			clientRepository.save(new Client(UUIDs.timeBased(), "Alice", "Smith"));
			clientRepository.save(new Client(UUIDs.timeBased(), "Bob", "Smith"));
			// fetch all customers
			System.out.println("Clients found with findAll():");
			System.out.println("-------------------------------");
			for (Client client : clientRepository.findAll()) {
				System.out.println(client);
			}
			System.out.println();

			// fetch an individual customer
			System.out.println("Clients found with findByFirstName('Alice'):");
			System.out.println("--------------------------------");
			System.out.println(clientRepository.findByFirstName("Alice"));

			System.out.println("Clients found with findByLastName('Smith'):");
			System.out.println("--------------------------------");
			for (Client client : clientRepository.findByLastName("Smith")) {
				System.out.println(client);
			}
		};
		
	}


	
	
	
	
	@Override
	public void run(String... args) throws Exception {
		repository.deleteAll();

		// save a couple of customers
		repository.save(new Customer("Alice", "Jimm"));
		repository.save(new Customer("Bob", "Smith"));

		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer : repository.findAll()) {
			System.out.println(customer);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println("Customers found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByFirstName("Alice"));

		System.out.println("Customers found with findByLastName('Jimm'):");
		System.out.println("--------------------------------");
		for (Customer customer : repository.findByLastName("Smith")) {
			System.out.println(customer);
		}
		
	}

}

