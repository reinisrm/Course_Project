package lv.venta.repos;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.Comments;

public interface IRepoComments extends CrudRepository<Comments, Long>{

}
