package com.covid.tracker.repsitory;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.covid.tracker.model.CovidTotal;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalRepository extends MongoRepository<CovidTotal, ObjectId>{

}
