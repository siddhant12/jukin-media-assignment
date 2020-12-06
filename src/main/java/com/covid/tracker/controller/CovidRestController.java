package com.covid.tracker.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.covid.tracker.entity.ApiResponseDTO;
import com.covid.tracker.service.impl.CovidDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.covid.tracker.model.Country;
import com.covid.tracker.model.exception.CovidRapidAPIException;

@RestController
public class CovidRestController {

	@Autowired
	private CovidDetailsServiceImpl covidDetailsService;

	@GetMapping(value = { "/countries" })
	public ResponseEntity<Object> getAllCountries() {
		try {
			return ResponseEntity.ok(covidDetailsService.getCountries(false));
		} catch (CovidRapidAPIException e) {
			Object responseMap = new HashMap<>();
			Object country = covidDetailsService.getCountries(true);
			((HashMap<Object, Object>) responseMap).put("error", e.getError());
			((HashMap) responseMap).put("response", country);
			return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = { "/covidDetails" })
	public ResponseEntity<Object> getCovidDetails() {
		try {
			return ResponseEntity.ok(covidDetailsService.getTotal(false));
		} catch (CovidRapidAPIException e) {
			Object responseMap = new HashMap<>();
			Object covidTotal = covidDetailsService.getTotal(true);
			((HashMap) responseMap).put("error", "Data is fetched from Database");
			((HashMap) responseMap).put("response", covidTotal);
			return new ResponseEntity<>(responseMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = { "/covidDetailsByName/{name}" })
	public ResponseEntity<Object> getCovidDataByName(@PathVariable("name") String name) {
		try {
			return ResponseEntity.ok(covidDetailsService.getCovidDataByName(name, false));
		} catch (CovidRapidAPIException e) {
			Object responseMap = new HashMap<>();
			Object covidDataByName = covidDetailsService.getCovidDataByName(name, true);
			((HashMap) responseMap).put("error", "Data is fetched from Database");
			((HashMap) responseMap).put("response", covidDataByName);
			return new ResponseEntity<>(responseMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(value = { "/covidDetailsByCode/{code}" })
	public ResponseEntity<Object> getCovidDataByCode(@PathVariable("code") String code) {
		try {
			return ResponseEntity.ok(covidDetailsService.getCovidDataByCode(code, false));
		} catch (CovidRapidAPIException e) {
			Object responseMap = new HashMap<>();
			Object covidDataByCode = covidDetailsService.getCovidDataByCode(code, true);
			((HashMap) responseMap).put("error", "Data is fetched from Database");
			((HashMap) responseMap).put("response", covidDataByCode);
			return new ResponseEntity<>(responseMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping(value = { "/updateCountry" }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ApiResponseDTO> getUpdatedCountry(@RequestBody Country country) {
		covidDetailsService.updateCountry(country);
		return ApiResponseDTO.response(HttpStatus.OK,"Country updated");
	}

	@PostMapping(value = { "/addCommentsByName" }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ApiResponseDTO> addCommentByName(@RequestBody Map<String, String> body) {
		covidDetailsService.addCommentByName(body);
		return ApiResponseDTO.response(HttpStatus.OK,"Comment Added");
	}

	@PostMapping(value = { "/addCommentsByCode" }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ApiResponseDTO> addCommentByCode(@RequestBody Map<String, String> body) {
		covidDetailsService.addCommentByCode(body);
		return ApiResponseDTO.response(HttpStatus.OK,"Comment Added");
	}

	@GetMapping(value = { "/commentByName/{name}" })
	public ResponseEntity<List<String>> getCommentByName(@PathVariable String name) {
		List<String> commentsList =  covidDetailsService.getCommentByName(name);
		return ResponseEntity.ok(commentsList);
	}

	@GetMapping(value = { "/commentByCode/{code}" })
	public ResponseEntity<List<String>> getCommentByCode(@PathVariable String code) {
		List<String> commentsList = covidDetailsService.getCommentByCode(code);
		return ResponseEntity.ok(commentsList);
	}

	@GetMapping(value = { "/countriesMap" })
	public ResponseEntity<Map<String, Set<String>>> getCountriesCodeMap() {
		Map<String, Set<String>> codeCountryMap =  covidDetailsService.getCountriesCodeMap();
		return ResponseEntity.ok(codeCountryMap);
	}
}
