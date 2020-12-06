package com.covid.tracker.repsitory;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.covid.tracker.model.Country;

import java.util.List;

@Repository
public interface CountryRepository extends MongoRepository<Country, ObjectId> {
	
	public Country findByName(String name);
	List<Country> findAllByOrderByFavouriteDescName();
}
