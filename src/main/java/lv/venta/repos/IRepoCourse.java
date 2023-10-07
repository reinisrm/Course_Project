package lv.venta.repos;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.Course;

public interface IRepoCourse extends CrudRepository<Course, Long>{

}
