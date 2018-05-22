package com.bogusnazar.intime.DAO;

import com.bogusnazar.intime.Models.Meet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by Artem on 5/16/2018.
 */
@Repository
public interface MeetRepository extends MongoRepository<Meet, String> {

    Meet findByName(@Param("name") String name);

    List<Meet> findByCreatorID(@Param("creatorId") String creatorId);


}
