package lv.venta.repos.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lv.venta.models.users.Person;

@Repository
public interface IRepoPerson extends CrudRepository<Person, Long>{

}
