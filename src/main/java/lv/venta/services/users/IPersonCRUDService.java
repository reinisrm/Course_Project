package lv.venta.services.users;

import java.util.List;

import lv.venta.models.users.Person;

public interface IPersonCRUDService {
	
	void reigsterPerson(Person person);
	
	List<Person> allPersons();

}
