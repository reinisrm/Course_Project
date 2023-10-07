package lv.venta.services.users.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.enums.Degree;
import lv.venta.models.Comments;
import lv.venta.models.Thesis;
import lv.venta.models.users.Academic_personel;
import lv.venta.models.users.Person;
import lv.venta.models.users.User;
import lv.venta.repos.IRepoComments;
import lv.venta.repos.IRepoThesis;
import lv.venta.repos.users.IRepoAcademicPersonel;
import lv.venta.services.impl.CommentsCRUDService;
import lv.venta.services.impl.ThesisCRUDService;
import lv.venta.services.users.IAcademicPersonelCRUDService;


@Service
public class AcademicPersonelCRUDService implements IAcademicPersonelCRUDService{
	
	@Autowired
	IRepoAcademicPersonel personelRepo;
	
	@Autowired
	IRepoComments commentsRepo;
	
	@Autowired
	IRepoThesis thesisRepo;
	
	@Autowired
	CommentsCRUDService commentsService;
	
	@Autowired
	ThesisCRUDService thesisService;

	@Override
	public List<Academic_personel> getAll() {
		
		return (List<Academic_personel>) personelRepo.findAll();
	}
	
	public void insertNewPersonel(Academic_personel personel) {
		
		if(findById(personel.getPersonId() )== null) {

			
			personelRepo.save(personel);
		}
	}

	@Override
	public void addPersonelByUser(User user, Degree degree) throws Exception {
		
	try {	
		
		Academic_personel temp = new Academic_personel(
				user.getPerson().getPersonName(), 
				user.getPerson().getSurname(), 
				user.getPerson().getPersonalCode(), 
				user, degree);
		
	
		
			if(!getAll().contains(temp)) {
				
				personelRepo.save(temp);
				
			}
	}
	catch (Exception e) {
		
		throw new Exception("Error!");
	}
		
		
		
		
	}

	@Override
	public void deletePersonelById(long id) throws Exception {
		
	try {
		if(findById(id)!= null) {			
			for(Academic_personel temp: getAll()) {
				if(temp.getPersonId() == id) {
					
					
					for(Comments comment: commentsService.getAll()) {
						if(comment.getPersonel().getPersonId() == id) {
							comment.setPersonel(null);
							
							commentsRepo.save(comment);
						}
					}
					
					for(Thesis thesis: thesisService.selectAllThesis()) {
						if(thesis.getPersonel().getPersonId() == id) {
							thesis.setPersonel(null);
							
							thesisRepo.save(thesis);
						}
					}
					
					personelRepo.delete(temp);
					
					
					
				}
			}
			
			
		}
		else {
			throw new Exception("Persona netika atrasta!");
		}
		
		
		

		
	}
	catch (Exception e) {
		
	
		
	}
		
		
	}

	@Override
	public List<Comments> findCommentsByAcademicPersonelId(long id) throws Exception {
		
	try {	
		
		List<Comments> commentsList = new ArrayList<>();
		
		for(Academic_personel personel: getAll()) {
			if(personel.getPersonId() == id) {
				commentsList.add((Comments) personel.getComments());
			}
			
		}
		
		return commentsList;
		
	}
	catch (Exception e) {
		throw new Exception("Id nav personāls vai neeksistē!");
	}
		
	}

	@Override
	public List<Thesis> findThesisByAcademicPersonelId(long id) throws Exception {
		
		List<Thesis> thesisList = new ArrayList<>();
	try {
		for(Academic_personel personel: getAll()) {
			if(personel.getPersonId() == id) {
				
				thesisList.add((Thesis) personel.getThesis());
			}
		}
		
		return thesisList;
		
	}
	catch (Exception e) {
		throw new Exception("Id nav personāls vai neeksistē!");
	}
	}

	@Override
	public Academic_personel findById(long id) {
		
		Academic_personel newPersonel = new Academic_personel();
		
		try {
			
			for(Academic_personel temp : getAll()) {
				if(temp.getPersonId() == id) {
					
					newPersonel = temp;
					
					return newPersonel;
					
				}
			}
			
			
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	
	@Override
	public void updatePersonelById(int id, Academic_personel personel) {
	    for (Academic_personel pers : getAll()) {
	        if (pers.getPersonId() == id) {
	        	
	     	    if (pers != null) {
	     	    	pers.setPersonName(personel.getPersonName());
	     	    	pers.setSurname(personel.getSurname());
	     	    	pers.setDegree(personel.getDegree());
	     	    	pers.setPersonalCode(personel.getPersonalCode());

	     	        personelRepo.save(pers);
	     	    }
	        }
	    }
	}

}


