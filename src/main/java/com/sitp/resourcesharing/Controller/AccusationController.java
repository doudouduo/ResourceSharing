package com.sitp.resourcesharing.Controller;

import com.sitp.resourcesharing.Entity.Accusation;
import com.sitp.resourcesharing.Entity.Resource;
import com.sitp.resourcesharing.Entity.User;
import com.sitp.resourcesharing.Repository.AccusationRepository;
import com.sitp.resourcesharing.Repository.ResourceRepository;
import com.sitp.resourcesharing.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccusationController {

    @Autowired
    private AccusationRepository accusationRepository;
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private UserRepository userRepository;

    public void removeComment(String id){
        accusationRepository.deleteById(id);
    }

    @PostMapping("/makeAccusation")
    public Accusation makeAccusation(String resource_id, String content, String user_id){
        Accusation accusation=new Accusation(resource_id,content,user_id);
        return accusationRepository.save(accusation);
    }

    public void dealWithAccusation(String accusation_id){
        Accusation accusation=accusationRepository.getOne(accusation_id);
        if (accusation==null)return;
        else if (!accusation.isStatus()){
            Resource resource=resourceRepository.findResourceBy_id(accusation.getResource_id());
            User author=userRepository.getOne(resource.getAuthor());
            author.setStatus(false);
            userRepository.save(author);
            accusation.setStatus(true);
            accusationRepository.save(accusation);
        }
    }
}
