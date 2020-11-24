package com.covid.tracker.repsitory;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.covid.tracker.model.CovidData;
import org.springframework.stereotype.Repository;

@Repository
public interface CovidDataRepository extends MongoRepository<CovidData, ObjectId> {

	/**
	 * Find Covid Data by country
	 * @param country
	 * @return
	 */
	 List<CovidData> findAllByCountry(String country);

	/**
	 * Find Covid Data by country
	 * @param country
	 * @return CovidData
	 */
	 CovidData findByCountry(String country);

	/**
	 * Find Covid Data By code
	 * @param code
	 * @return CovidData
	 */
	CovidData findByCode(String code);

	/**
	 * Find Covid Data By code
	 * @param code
	 * @return List<CovidData>
	 */
	List<CovidData> findAllByCode(String code);
	
}
