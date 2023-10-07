package lv.venta.repos.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lv.venta.models.users.Student;

@Repository
public interface IRepoStudent extends CrudRepository<Student, Long>{

}
