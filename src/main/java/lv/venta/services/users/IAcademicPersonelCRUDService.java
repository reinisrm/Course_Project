package lv.venta.services.users;

import java.util.List;

import lv.venta.enums.Degree;
import lv.venta.models.Comments;
import lv.venta.models.Thesis;
import lv.venta.models.security.MyUser;
import lv.venta.models.users.Academic_personel;


public interface IAcademicPersonelCRUDService {
	
	List<Academic_personel> getAll();
	
	List<Comments> findCommentsByAcademicPersonelId(long id) throws Exception;
	
	List<Thesis> findThesisByAcademicPersonelId(long id) throws Exception;
	
	Academic_personel findById(long id);
	
	
	void addPersonelByUser(MyUser user, Degree degree) throws Exception;
	
	void deletePersonelById(long id) throws Exception;

	void updatePersonelById(int id, Academic_personel personel);

}
