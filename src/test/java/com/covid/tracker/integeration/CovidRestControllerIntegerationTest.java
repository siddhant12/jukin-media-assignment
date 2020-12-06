package com.covid.tracker.integeration;

import com.covid.tracker.CovidTrackerApplication;
import com.covid.tracker.entity.ApiResponseDTO;
import com.covid.tracker.model.Country;
import com.covid.tracker.repsitory.CovidDataRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static com.covid.tracker.mockdata.CovidMockData.getCountry_Mock;


/**
 * We can also assert our integration test cases with the expected response from the API
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CovidTrackerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CovidRestControllerIntegerationTest {

    @LocalServerPort
    private int port;

    /**
     * Added this for future purpose so that we can do integration test for database and repository
     */
    @Autowired
    private CovidDataRepository covidDataRepository;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();



    /**
     * Integration Test case for Get All Countries
     */
    @Test
    void getAllCountriesTest(){
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                createURLWithPort("/countries"),
                HttpMethod.GET, entity, Object.class);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    /**
     * Integration Test cases for Get Total Detail total covid details of country
     */
    @Test
    void getCovidDetailsTest(){
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                createURLWithPort("/covidDetails"),
                HttpMethod.GET, entity, Object.class);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    /**
     * Integration Test case for getting covid data by country name
     */
    @Test
    void getCovidDataByNameTest(){
        ResponseEntity<Object> responseEntity = null;
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        responseEntity = restTemplate.exchange(
                createURLWithPort("/covidDetailsByName/India"),
                HttpMethod.GET, entity, Object.class);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());

        responseEntity = restTemplate.exchange(
                createURLWithPort("/covidDetailsByName/I"),
                HttpMethod.GET, entity, Object.class);
        Assert.assertEquals(500, responseEntity.getStatusCodeValue());
    }

    /**
     * Integration Test case for getting covid data by country code
     */
    @Test
    void getCovidDataByCodeTest(){
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                createURLWithPort("/covidDetailsByCode/IN"),
                HttpMethod.GET, entity, Object.class);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());

        responseEntity = restTemplate.exchange(
                createURLWithPort("/covidDetailsByCode/I"),
                HttpMethod.GET, entity, Object.class);
        Assert.assertEquals(500, responseEntity.getStatusCodeValue());
    }

    /**
     * Integration Test case for add comment for country by name
     */
    @Test
    void addCommentByNameTest(){
        Map<String,String> map = new HashMap<>();
        map.put("name","India");
        map.put("comment","Serious Situation");
        HttpEntity<Map<String,String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<ApiResponseDTO> responseEntity = restTemplate.exchange(
                createURLWithPort("/addCommentsByName"),
                HttpMethod.POST, entity, ApiResponseDTO.class);
        Assert.assertEquals(500, responseEntity.getStatusCodeValue());
    }

    /**
     * Integration Test case for add comment for country by code
     */
    @Test
    void addCommentByCodeTest(){
        Map<String,String> map = new HashMap<>();
        map.put("code","IN");
        map.put("comment","Serious Situation");
        HttpEntity<Map<String,String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<ApiResponseDTO> responseEntity = restTemplate.exchange(
                createURLWithPort("/addCommentsByCode"),
                HttpMethod.POST, entity, ApiResponseDTO.class);
        Assert.assertEquals(500, responseEntity.getStatusCodeValue());
    }

    /**
     * Integration Test case for updating country as favorite or not
     */
    @Test
    void getUpdatedCountryTest(){
        Country country = getCountry_Mock();
        country.setName("Afghanistan");
        HttpEntity<Country> entity = new HttpEntity<>(country, headers);
        ResponseEntity<ApiResponseDTO> responseEntity = restTemplate.exchange(
                createURLWithPort("/updateCountry"),
                HttpMethod.POST, entity, ApiResponseDTO.class);
        Assert.assertEquals(500, responseEntity.getStatusCodeValue());
    }

    /**
     * Integration Test case for get comments by country name
     */
    @Test
    void getCommentByNameTest(){
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                createURLWithPort("/commentByName/India"),
                HttpMethod.GET, entity, Object.class);
        Assert.assertEquals(500, responseEntity.getStatusCodeValue());

    }

    /**
     * Integration Test case for get comments by country code
     */
    @Test
    void getCommentByCodeTest(){
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                createURLWithPort("/commentByCode/IN"),
                HttpMethod.GET, entity, Object.class);
        Assert.assertEquals(500, responseEntity.getStatusCodeValue());

    }

    /**
     * Integration Test case for get country name code map
     */
    @Test
    void getCountriesCodeMapTest(){
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                createURLWithPort("/countriesMap"),
                HttpMethod.GET, entity, Object.class);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());

    }



    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
