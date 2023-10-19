package lv.venta.repo.security;

import lv.venta.models.security.MyUser;

import org.springframework.data.repository.CrudRepository;

public interface IMyUserRepo extends CrudRepository<MyUser, Integer>{

	MyUser findByUsername(String username);

}
