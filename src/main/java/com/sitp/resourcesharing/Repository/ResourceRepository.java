package com.sitp.resourcesharing.Repository;

import com.sitp.resourcesharing.Entity.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ResourceRepository extends MongoRepository<Resource, String> {
    Resource findResourceBy_id(String _id);
    Resource findResourceByFilepath(String filepath);
    List<Resource> findResourceByAuthor(String author);
    List<Resource> findResourceByFilename(String filename);
    List<Resource> findResourceByCollegeAndDomain(String college,String domain);
    List<Resource> findResourceByCollegeAndAuthor(String college,String author);
    List<Resource> findResourceByDomainAndAuthor(String domain,String author);
    List<Resource> findResourceByCollegeAndDomainAndAuthor(String college,String domain,String author);
    List<Resource> findResourceByOnshelf(boolean onshelf);
}
