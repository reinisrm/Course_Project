package lv.venta.repos;

import lv.venta.models.Application;
import org.springframework.data.repository.CrudRepository;

public interface IRepoApplication extends CrudRepository<Application, Long> {


}
