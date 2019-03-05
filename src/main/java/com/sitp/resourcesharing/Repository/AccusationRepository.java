package com.sitp.resourcesharing.Repository;

import com.sitp.resourcesharing.Entity.Accusation;
import com.sitp.resourcesharing.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccusationRepository extends JpaRepository<Accusation, String> {
//    Accusation findAccusationByAccusation_id(String accusation_id);
}
