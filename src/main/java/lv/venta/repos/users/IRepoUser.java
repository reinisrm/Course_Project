package lv.venta.repos.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lv.venta.models.users.User;


@Repository
public interface IRepoUser extends CrudRepository<User, Long>{

	User findByEmail(String email);

}
