package com.covid.tracker.service;

import java.util.List;


import com.covid.tracker.model.Country;
import com.covid.tracker.model.CovidData;
import com.covid.tracker.model.CovidTotal;

public interface CovidRestService {

	/**
	 * GEt list of Countries
	 * @return List<Country>
	 */
	List<Country> getListOfCountries();

	/**
	 * Get Covid Detail worldwide
	 * @return List<CovidTotal>
	 */
	List<CovidTotal> getTotal();

	/**
	 * Get Covid Data By Code
	 * @param code
	 * @return List<CovidData>
	 */
	List<CovidData> getCovidDataByCode(String code);

	/**
	 * Get covid data by name
	 * @param name
	 * @return List<CovidData>
	 */
	List<CovidData> getCovidDataByName(String name);

}
