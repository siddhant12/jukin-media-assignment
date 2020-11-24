package com.covid.tracker.service.impl;

import com.covid.tracker.model.Country;
import com.covid.tracker.model.CovidData;
import com.covid.tracker.model.CovidTotal;
import com.covid.tracker.model.exception.CovidRapidAPIException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static com.covid.tracker.mockdata.CovidMockData.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CovidRestServiceImplTest {

    @InjectMocks
    private CovidRestServiceImpl covidRestService;

    @Mock
    private RestTemplate restTemplate;


   @BeforeEach
    public void setUp() {
       ReflectionTestUtils.setField(covidRestService, "countriesApiUrl", "https://covid-19-data.p.rapidapi.com/help/countries?format=json");
       ReflectionTestUtils.setField(covidRestService, "totalApiUrl", "https://covid-19-data.p.rapidapi.com/totals?format=json");
       ReflectionTestUtils.setField(covidRestService, "covidByNameUrl", "https://covid-19-data.p.rapidapi.com/country?format={format}&name={name}");
       ReflectionTestUtils.setField(covidRestService, "covidByCodeUrl", "https://covid-19-data.p.rapidapi.com/country/code?format={format}&code={code}");
       ReflectionTestUtils.setField(covidRestService, "apiHost", "covid-19-data.p.rapidapi.com");
       ReflectionTestUtils.setField(covidRestService, "apiKey", "cc119a2a07mshb6adc33a3e346b9p1c17e8jsn6e17f47f3331");
    }

    /**
     * Test case for get List of countries
     */
    @Test
    void getListOfCountriesTest(){
        List<Country> countryList = getCountryList_Mock();
        ResponseEntity<List<Country>> listResponseEntity = null;
        listResponseEntity = new ResponseEntity<>(countryList, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Mockito.<String>any(),
                Mockito.any(HttpMethod.class),
                Mockito.<HttpEntity<String>>any(),
                Mockito.<ParameterizedTypeReference<List<Country>>>any())).thenReturn(listResponseEntity);
        List<Country> countries = covidRestService.getListOfCountries();
        Assert.assertNotNull(countries);

        List<Country> emptyList = Collections.emptyList();
        listResponseEntity = new ResponseEntity<>(emptyList, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Mockito.<String>any(),
                Mockito.any(HttpMethod.class),
                Mockito.<HttpEntity<String>>any(),
                Mockito.<ParameterizedTypeReference<List<Country>>>any())).thenReturn(listResponseEntity);

        List<Country> listOfCountries = covidRestService.getListOfCountries();
        Assert.assertEquals(listOfCountries.isEmpty(),true);

        Mockito.when(restTemplate.exchange(
                Mockito.<String>any(),
                Mockito.any(HttpMethod.class),
                Mockito.<HttpEntity<String>>any(),
                Mockito.<ParameterizedTypeReference<List<Country>>>any()))
                .thenThrow(new CovidRapidAPIException(HttpStatus.INTERNAL_SERVER_ERROR,"Exception Occurred While Getting Data from Service","Message"));
        Assertions.assertThrows(
                CovidRapidAPIException.class, () -> {
                    covidRestService.getListOfCountries();
                }
        );

    }

    /**
     * Test cases for get details of covid worldwide.
     */
    @Test
    void getTotalTest(){
        List<CovidTotal> covidTotalList =  getCovidTotalList_Mock();
        ResponseEntity<List<CovidTotal>> listResponseEntity = null;
        listResponseEntity = new ResponseEntity<>(covidTotalList, HttpStatus.OK);
        List<CovidTotal> covidTotals = null;
        Mockito.when(restTemplate.exchange(
                Mockito.<String>any(),
                Mockito.any(HttpMethod.class),
                Mockito.<HttpEntity<String>>any(),
                Mockito.<ParameterizedTypeReference<List<CovidTotal>>>any())).thenReturn(listResponseEntity);
        covidTotals = covidRestService.getTotal();
        Assert.assertNotNull(covidTotals);

        List<CovidTotal> emptyList = Collections.emptyList();
        listResponseEntity = new ResponseEntity<>(emptyList, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Mockito.<String>any(),
                Mockito.any(HttpMethod.class),
                Mockito.<HttpEntity<String>>any(),
                Mockito.<ParameterizedTypeReference<List<CovidTotal>>>any())).thenReturn(listResponseEntity);

        covidTotals = covidRestService.getTotal();
        Assert.assertEquals(covidTotals.isEmpty(),true);


        Mockito.when(restTemplate.exchange(
                Mockito.<String>any(),
                Mockito.any(HttpMethod.class),
                Mockito.<HttpEntity<String>>any(),
                Mockito.<ParameterizedTypeReference<List<CovidTotal>>>any()))
                .thenThrow(new CovidRapidAPIException(HttpStatus.INTERNAL_SERVER_ERROR,"Exception Occurred While Getting Data from Service","Message"));
        Assertions.assertThrows(
                CovidRapidAPIException.class, () -> {
                    covidRestService.getTotal();
                }
        );

    }

    /**
     * Test case for get details of covid for country by name
     */
    @Test
    void getCovidDataByNameTest(){
        List<CovidData> covidTotalList =  getCovidDataList_Mock();
        ResponseEntity<List<CovidData>> listResponseEntity = null;
        listResponseEntity = new ResponseEntity<>(covidTotalList, HttpStatus.OK);
        List<CovidData> covidTotals = null;
        Mockito.when(restTemplate.exchange(
                Mockito.<String>any(),
                Mockito.any(HttpMethod.class),
                Mockito.<HttpEntity<String>>any(),
                Mockito.<ParameterizedTypeReference<List<CovidData>>>any(),
                Mockito.<String, String> anyMap())).thenReturn(listResponseEntity);
        covidTotals = covidRestService.getCovidDataByName(COUNTRY_NAME);
        Assert.assertNotNull(covidTotals);

        List<CovidData> emptyList = Collections.emptyList();
        listResponseEntity = new ResponseEntity<>(emptyList, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Mockito.<String>any(),
                Mockito.any(HttpMethod.class),
                Mockito.<HttpEntity<String>>any(),
                Mockito.<ParameterizedTypeReference<List<CovidData>>>any(),
                Mockito.<String, String> anyMap())).thenReturn(listResponseEntity);

        covidTotals = covidRestService.getCovidDataByName(COUNTRY_NAME);
        Assert.assertEquals(covidTotals.isEmpty(),true);

        Mockito.when(restTemplate.exchange(
                Mockito.<String>any(),
                Mockito.any(HttpMethod.class),
                Mockito.<HttpEntity<String>>any(),
                Mockito.<ParameterizedTypeReference<List<CovidTotal>>>any(),
                Mockito.<String, String> anyMap()))
                .thenThrow(new CovidRapidAPIException(HttpStatus.INTERNAL_SERVER_ERROR,"Exception Occurred While Getting Data from Service","Message"));
        Assertions.assertThrows(
                CovidRapidAPIException.class, () -> {
                    covidRestService.getCovidDataByName(COUNTRY_NAME);
                }
        );

    }

    /**
     * Test case for get details of covid for country by name
     */
    @Test
    void getCovidDataByCodeTest(){
        List<CovidData> covidTotalList =  getCovidDataList_Mock();
        ResponseEntity<List<CovidData>> listResponseEntity = null;
        listResponseEntity = new ResponseEntity<>(covidTotalList, HttpStatus.OK);
        List<CovidData> covidTotals = null;
        Mockito.when(restTemplate.exchange(
                Mockito.<String>any(),
                Mockito.any(HttpMethod.class),
                Mockito.<HttpEntity<String>>any(),
                Mockito.<ParameterizedTypeReference<List<CovidData>>>any(),
                Mockito.<String, String> anyMap())).thenReturn(listResponseEntity);
        covidTotals = covidRestService.getCovidDataByCode(ALPHA_2_CODE);
        Assert.assertNotNull(covidTotals);

        List<CovidData> emptyList = Collections.emptyList();
        listResponseEntity = new ResponseEntity<>(emptyList, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Mockito.<String>any(),
                Mockito.any(HttpMethod.class),
                Mockito.<HttpEntity<String>>any(),
                Mockito.<ParameterizedTypeReference<List<CovidData>>>any(),
                Mockito.<String, String> anyMap())).thenReturn(listResponseEntity);

        covidTotals = covidRestService.getCovidDataByCode(ALPHA_2_CODE);
        Assert.assertEquals(covidTotals.isEmpty(),true);

        Mockito.when(restTemplate.exchange(
                Mockito.<String>any(),
                Mockito.any(HttpMethod.class),
                Mockito.<HttpEntity<String>>any(),
                Mockito.<ParameterizedTypeReference<List<CovidTotal>>>any(),
                Mockito.<String, String> anyMap()))
                .thenThrow(new CovidRapidAPIException(HttpStatus.INTERNAL_SERVER_ERROR,"Exception Occurred While Getting Data from Service","Message"));
        Assertions.assertThrows(
                CovidRapidAPIException.class, () -> {
                    covidRestService.getCovidDataByCode(ALPHA_2_CODE);
                }
        );

    }
}
