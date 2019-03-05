package com.sitp.resourcesharing.Controller;

import com.sitp.resourcesharing.Entity.Comment;
import com.sitp.resourcesharing.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;

    public void removeComment(String id){
        commentRepository.deleteById(id);
    }

    public Comment makeComment(String resource_id,String content,String user_id){
        Comment comment=new Comment(resource_id,content,user_id);
        return commentRepository.save(comment);
    }
}
