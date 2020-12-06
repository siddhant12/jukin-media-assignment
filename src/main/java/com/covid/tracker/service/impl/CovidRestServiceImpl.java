package com.covid.tracker.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.covid.tracker.service.CovidRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.covid.tracker.model.Country;
import com.covid.tracker.model.CovidData;
import com.covid.tracker.model.CovidTotal;
import com.covid.tracker.model.exception.CovidRapidAPIException;

@Repository
public class CovidRestServiceImpl implements CovidRestService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CovidRestServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${get.countries.api.url}")
	private String countriesApiUrl;

	@Value("${get.total.api.url}")
	private String totalApiUrl;

	@Value("${get.covid.name.api.url}")
	private String covidByNameUrl;

	@Value("${get.covid.code.api.url}")
	private String covidByCodeUrl;

	@Value("${api.host}")
	private String apiHost;

	@Value("${api.key}")
	private String apiKey;

	@Override
	public List<Country> getListOfCountries() {
		LOGGER.info("Start of getListOfCountries method ==> ");
		HttpHeaders headers = buildRequest();
		HttpEntity<String> request = new HttpEntity<>(headers);
		try {
			LOGGER.info("Start of Rest Template Exhange withparams {}  ==> ", countriesApiUrl);
			ResponseEntity<List<Country>> response = restTemplate.exchange(countriesApiUrl, HttpMethod.GET, request,
					new ParameterizedTypeReference<List<Country>>() {
					});
			List<Country> resp = response.getBody();
			if (CollectionUtils.isEmpty(resp)) {
				LOGGER.info("Empty RESPONSE Received from API", resp);
				return resp;
			}
			LOGGER.info("End of Rest Template Exhange and response received {}  with size ==> ", resp, resp.size());
			return response.getBody();
		} catch (Exception e) {
			LOGGER.error("Exception occurred {}", e);
			throw new CovidRapidAPIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),
					"Exception Occurred While Getting Data from Service");
		}
	}

	private HttpHeaders buildRequest() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-RapidAPI-Host", apiHost);
		headers.add("X-RapidAPI-Key", apiKey);
		return headers;

	}

	@Override
	public List<CovidTotal> getTotal() {
		LOGGER.info("Start of getTotal method ==> ");
		HttpHeaders headers = buildRequest();
		HttpEntity<String> request = new HttpEntity<>(headers);

		LOGGER.info("Start of Rest Template Exhange withparams {}  ==> ", countriesApiUrl);
		ResponseEntity<List<CovidTotal>> response = null;
		try {
			response = restTemplate.exchange(totalApiUrl, HttpMethod.GET, request,
					new ParameterizedTypeReference<List<CovidTotal>>() {
					});
		} catch (Exception e) {
			throw new CovidRapidAPIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),
					"Exception Occurred While Getting Data from Service");

		}
		List<CovidTotal> resp = response.getBody();
		if (CollectionUtils.isEmpty(resp)) {
			LOGGER.info("Empty RESPONSE Received from API", resp);
			return Collections.emptyList();
		}
		LOGGER.info("End of Rest Template Exhange and response received {}  with size ==> ", resp, resp.size());

		LOGGER.info("End of getListOfCountries method ==> ");
		return resp;
	}

	@Override
	public List<CovidData> getCovidDataByName(String name) {
		LOGGER.info("Start of getCovidDataByName method ==> ");
		HttpHeaders headers = buildRequest();
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("format", "json");
		queryMap.put("name", name);
		HttpEntity<String> request = new HttpEntity<>(headers);
		try {
			LOGGER.info("Start of Rest Template Exhange withparams {}  ==> ", covidByNameUrl);
			ResponseEntity<List<CovidData>> response = restTemplate.exchange(covidByNameUrl, HttpMethod.GET, request,
					new ParameterizedTypeReference<List<CovidData>>() {
					}, queryMap);
			List<CovidData> resp = response.getBody();
			if (CollectionUtils.isEmpty(resp)) {
				LOGGER.info("Empty RESPONSE Received from API", resp);
				return Collections.emptyList();
			}
			LOGGER.info("End of Rest Template Exhange and response received {}  with size ==> ", resp, resp.size());
			return resp;
		} catch (Exception e) {
			LOGGER.error("Exception occurred{}", e);
			throw new CovidRapidAPIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),
					"Exception Occurred While Getting Data from Service");
		}
	}

	@Override
	public List<CovidData> getCovidDataByCode(String code) {
		LOGGER.info("Start of getCovidDataByCode method ==> ");
		HttpHeaders headers = buildRequest();
		HttpEntity<String> request = new HttpEntity<>(headers);
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("format", "json");
		queryMap.put("code", code);
		try {
			LOGGER.info("Start of Rest Template Exhange withparams {}  ==> ", covidByCodeUrl);
			ResponseEntity<List<CovidData>> response = restTemplate.exchange(covidByCodeUrl, HttpMethod.GET, request,
					new ParameterizedTypeReference<List<CovidData>>() {
					}, queryMap);
			List<CovidData> resp = response.getBody();
			if (CollectionUtils.isEmpty(resp)) {
				LOGGER.info("Empty RESPONSE Received from API", resp);
				return Collections.emptyList();
			}
			LOGGER.info("End of Rest Template Exhange and response received {}  with size ==> ", resp, resp.size());
			return resp;
		} catch (Exception e) {
			LOGGER.error("Exception occurred{}", e);
			throw new CovidRapidAPIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),
					"Exception Occurred While Getting Data from Service");
		}
	}

}
