package com.covid.tracker.controller;

import com.covid.tracker.entity.ApiResponseDTO;
import com.covid.tracker.model.exception.CovidRapidAPIException;
import com.covid.tracker.service.impl.CovidDetailsServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.covid.tracker.mockdata.CovidMockData.*;

/**
 * We can also assert our unit test cases with the expected response from the API
 */

@SpringBootTest
public class CovidRestControllerTest {

    @InjectMocks
    private CovidRestController covidRestController;

    @Mock
    private CovidDetailsServiceImpl covidDetailsService;


    /**
     * Test case for Get All Countries
     */
    @Test
    void getAllCountriesTest(){

        Mockito.when(covidDetailsService.getCountries(false)).thenReturn(getCountryList_Mock());
        ResponseEntity<Object> response = covidRestController.getAllCountries();
        Assert.assertNotNull(response);

        Mockito.when(covidDetailsService.getCountries(false)).thenThrow(new CovidRapidAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Excpetion occurred while getting data from Rapid API", "Message"));
        ResponseEntity<Object> response1 = covidRestController.getAllCountries();
        Assert.assertNotNull(response);
    }

    /**
     * Test cases for Get Total Detail total covid details of country
     */
    @Test
    void getCovidDetailsTest(){
        ResponseEntity<Object> response = null;
        Mockito.when(covidDetailsService.getTotal(false)).thenReturn(getCovidTotalList_Mock());
        response = covidRestController.getCovidDetails();
        Assert.assertNotNull(response);

        Mockito.when(covidRestController.getCovidDetails()).thenThrow(new CovidRapidAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Excpetion occurred while getting data from Rapid API", "Message"));
        response = covidRestController.getCovidDetails();
        Assert.assertNotNull(response);
    }

    /**
     * Test case for getting covid data by country name
     */
    @Test
    void getCovidDataByNameTest(){
        ResponseEntity<Object> response = null;
        Mockito.when(covidDetailsService.getCovidDataByName(COUNTRY_NAME,false)).thenReturn(getCovidDataList_Mock());
        response = covidRestController.getCovidDataByName(COUNTRY_NAME);
        Assert.assertNotNull(response);

        Mockito.when(covidDetailsService.getCovidDataByName(COUNTRY_NAME,false)).thenThrow(new CovidRapidAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Excpetion occurred while getting data from Rapid API", "Message"));
        response = covidRestController.getCovidDataByName(COUNTRY_NAME);
        Assert.assertNotNull(response);
    }

    /**
     * Test case for getting covid data by country code
     */
    @Test
    void getCovidDataByCodeTest(){
        ResponseEntity<Object> response = null;
        Mockito.when(covidDetailsService.getCovidDataByCode(ALPHA_2_CODE,false)).thenReturn(getCovidDataList_Mock());
        response = covidRestController.getCovidDataByCode(COUNTRY_NAME);
        Assert.assertNotNull(response);

        Mockito.when(covidDetailsService.getCovidDataByCode(ALPHA_2_CODE,false)).thenThrow(new CovidRapidAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Excpetion occurred while getting data from Rapid API", "Message"));
        response = covidRestController.getCovidDataByCode(ALPHA_2_CODE);
        Assert.assertNotNull(response);
    }

    /**
     * Test case for updating country as favorite or not
     */
    @Test
    void getUpdatedCountryTest(){
         Mockito.doNothing().when(covidDetailsService).updateCountry(getCountry_Mock());
        ResponseEntity<ApiResponseDTO> response = covidRestController.getUpdatedCountry(getCountry_Mock());
        Assert.assertNotNull(response);
    }

    /**
     * Test case for add comment for country by name
     */
    @Test
    void addCommentByNameTest(){
        Mockito.doNothing().when(covidDetailsService).addCommentByName(getCommentByNameMap_Mock());
        ResponseEntity<ApiResponseDTO> response = covidRestController.addCommentByName(getCommentByNameMap_Mock());
        Assert.assertNotNull(response);
    }

    /**
     * Test case for add comment for country by code
     */
    @Test
    void addCommentByCodeTest(){
        Mockito.doNothing().when(covidDetailsService).addCommentByCode(getCommentByCodeMap_Mock());
        ResponseEntity<ApiResponseDTO> response = covidRestController.addCommentByCode(getCommentByCodeMap_Mock());
        Assert.assertNotNull(response);
    }


    /**
     * Test case for get comments for country by name
     */
    @Test
    void getCommentByNameTest(){
        Mockito.when(covidDetailsService.getCommentByName(COUNTRY_NAME)).thenReturn(getCommentsList_Mock());
        ResponseEntity<List<String>> commentList = covidRestController.getCommentByName(COUNTRY_NAME);
        Assert.assertNotNull(commentList);
    }

    /**
     * Test case get add comments for country by code
     */
    @Test
    void getCommentByCodeTest(){
        Mockito.when(covidDetailsService.getCommentByCode(ALPHA_2_CODE)).thenReturn(getCommentsList_Mock());
        ResponseEntity<List<String>> commentList = covidRestController.getCommentByCode(ALPHA_2_CODE);
        Assert.assertNotNull(commentList);
    }

    /**
     * Test case for get country name code map
     */
    @Test
    void getCountriesCodeMapTest(){
        Mockito.when(covidDetailsService.getCountriesCodeMap()).thenReturn(getCountriesCodeMap_Mock());
        ResponseEntity<Map<String, Set<String>>> getCountriesCodeMap = covidRestController.getCountriesCodeMap();
        Assert.assertNotNull(getCountriesCodeMap);
    }



}
