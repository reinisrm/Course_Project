package lv.venta.services.users.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.users.Person;
import lv.venta.repos.users.IRepoPerson;
import lv.venta.services.users.IPersonCRUDService;

@Service
public class PersonCRUDService implements IPersonCRUDService{
	
	@Autowired
	IRepoPerson personRepo;

	@Override
	public void reigsterPerson(Person person) {
		
		personRepo.save(person);
		
	}

	@Override
	public List<Person> allPersons() {
		
		return (List<Person>) personRepo.findAll();
	}

}
