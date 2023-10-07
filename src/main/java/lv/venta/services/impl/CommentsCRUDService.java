package lv.venta.services.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.Comments;
import lv.venta.models.users.Person;
import lv.venta.repos.IRepoComments;
import lv.venta.services.ICommentsCRUDService;

@Service
public class CommentsCRUDService implements ICommentsCRUDService{
	
	@Autowired
	IRepoComments commentsRepo;

	@Override
	public List<Comments> getAll() {
		return (List<Comments>) commentsRepo.findAll();
	}

	

	
	@Override
	public Comments findById(long id) {
		try {
			
			for (Comments comments : getAll()) {
				if (comments.getComment_id() == id) {

					return comments;
				}
			}
	    }
		catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public void insertNewComments(Comments comments) {
		//commentsList.add(comments);
		return;
	}

	@Override
    public void deleteCommentsById(long id) throws Exception {
        for (Comments comments : getAll()) {
            if (comments.getComment_id() == id) {
                //commentsList.remove(comments);
                return;
            }
        }
        throw new Exception("Comments with id " + id + " not found.");
    }




	@Override
	public ArrayList<Comments> selectAllComments() {
		// TODO Auto-generated method stub
		return null;
	}

}
