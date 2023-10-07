package lv.venta.repos.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lv.venta.models.users.Academic_personel;


@Repository
public interface IRepoAcademicPersonel extends CrudRepository<Academic_personel, Long>{

}
