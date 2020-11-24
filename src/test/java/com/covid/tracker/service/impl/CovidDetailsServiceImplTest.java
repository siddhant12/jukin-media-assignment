package com.covid.tracker.service.impl;

import com.covid.tracker.model.ApiHistory;
import com.covid.tracker.model.Country;
import com.covid.tracker.model.CovidData;
import com.covid.tracker.model.CovidTotal;
import com.covid.tracker.model.exception.CovidException;
import com.covid.tracker.model.exception.CovidRapidAPIException;
import com.covid.tracker.repsitory.ApiHistoryRepository;
import com.covid.tracker.repsitory.CountryRepository;
import com.covid.tracker.repsitory.CovidDataRepository;
import com.covid.tracker.repsitory.TotalRepository;
import com.covid.tracker.service.CovidRestService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.util.*;

import static com.covid.tracker.mockdata.CovidMockData.*;


@SpringBootTest
public class CovidDetailsServiceImplTest {

    @InjectMocks
    private CovidDetailsServiceImpl covidDetailsService;

    @Mock
    private ApiHistoryRepository apiHistoryRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CovidRestService covidRestService;

    @Mock
    private TotalRepository totalRepository;

    @Mock
    private CovidDataRepository covidDataRepository;

    public static final String COUNTRY = "countries";
    public static final String TOTAL = "total";
    public static final String NAME = "name";
    public static final String CODE = "code";
    public static final String COMMENT = "comment";

    /**
     * Test case for get all countries
     * @throws ParseException
     */
    @Test
    void getCountriesTest() throws ParseException {
        Mockito.when(countryRepository.findAllByOrderByFavouriteDescName()).thenReturn(getCountryList_Mock());
        Mockito.when(apiHistoryRepository.findAllByApiName(COUNTRY, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(false));
        List<Country> countryList = covidDetailsService.getCountries(true);
        Assert.assertNotNull(countryList);

        Mockito.when(apiHistoryRepository.findAllByApiName(COUNTRY, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(true));
        Mockito.when(countryRepository.findAllByOrderByFavouriteDescName()).thenReturn(getCountryList_Mock());
        List<Country> countries = covidDetailsService.getCountries(false);
        Assert.assertNotNull(countries);

        Mockito.when(countryRepository.findAllByOrderByFavouriteDescName()).thenThrow(new CovidRapidAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Exception Occurred While Getting Data from Service","Message"));
        Mockito.when(apiHistoryRepository.findAllByApiName(COUNTRY, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(true));
        Assertions.assertThrows(
                CovidRapidAPIException.class, () -> {
                    covidDetailsService.getCountries(true);
                }
        );
    }

    /**
     * Test case for get all countries
     * @throws ParseException
     */
    @Test
    void getCountriesMethodTest() throws ParseException {
        Mockito.when(apiHistoryRepository.findAllByApiName(COUNTRY, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(false));
        Mockito.when(countryRepository.findAllByOrderByFavouriteDescName()).thenReturn(getCountryList_Mock());
        Mockito.when(covidRestService.getListOfCountries()).thenReturn(getCountryList_Mock());
        Mockito.when(countryRepository.findAllByOrderByFavouriteDescName()).thenReturn(getCountryList_Mock());
        Mockito.when(apiHistoryRepository.save(getApiHistory_Mock(false))).thenReturn(null);
        Mockito.when(countryRepository.saveAll(getCountryList_Mock())).thenReturn(null);
        List<Country> countries1 = covidDetailsService.getCountries(false);
        Assert.assertNotNull(countries1);

        Mockito.when(countryRepository.findAllByOrderByFavouriteDescName()).thenThrow(new CovidException("Message",new Exception(),HttpStatus.INTERNAL_SERVER_ERROR));
        Mockito.when(apiHistoryRepository.findAllByApiName(COUNTRY, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(true));
        Assertions.assertThrows(
                CovidException.class, () -> {
                    covidDetailsService.getCountries(true);
                }
        );
    }

    /**
     * Test case for covid details worldwide
     * @throws ParseException
     */
    @Test
    void getTotalTest() throws ParseException {
        List<CovidTotal> covidTotalList = null;
        Mockito.when(totalRepository.findAll()).thenReturn(getCovidTotalList_Mock());
        Mockito.when(apiHistoryRepository.findAllByApiName(TOTAL, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(false));
        covidTotalList = covidDetailsService.getTotal(true);
        Assert.assertNotNull(covidTotalList);

        Mockito.when(totalRepository.findAll()).thenReturn(getCovidTotalList_Mock());
        Mockito.when(apiHistoryRepository.findAllByApiName(TOTAL, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(true));
        covidTotalList = covidDetailsService.getTotal(false);
        Assert.assertNotNull(covidTotalList);

        Mockito.when(totalRepository.findAll()).thenThrow(new CovidRapidAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Exception Occurred While Getting Data from Service","Message"));
        Mockito.when(apiHistoryRepository.findAllByApiName(TOTAL, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(true));
        Assertions.assertThrows(
                CovidRapidAPIException.class, () -> {
                    covidDetailsService.getTotal(true);
                }
        );


    }

    /**
     * Test case for covid details worldwide
     * @throws ParseException
     */
    @Test
    void getTotalMethodTest() throws ParseException {
        List<CovidTotal> emptyList = new ArrayList<>();
        List<CovidTotal> covidTotalList = null;
        List<ApiHistory> emptyHistory = Collections.emptyList();
        Mockito.when(apiHistoryRepository.findAllByApiName(COUNTRY, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(false));
        Mockito.when(totalRepository.findAll()).thenReturn(emptyList);
        Mockito.when(covidRestService.getTotal()).thenReturn(getCovidTotalList_Mock());
        Mockito.when(apiHistoryRepository.findAllByApiName(TOTAL, Sort.Direction.DESC)).thenReturn(emptyHistory);

        covidTotalList = covidDetailsService.getTotal(false);
        Assert.assertNotNull(covidTotalList);

        Mockito.when(apiHistoryRepository.findAllByApiName(COUNTRY, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(false));
        Mockito.when(totalRepository.findAll()).thenReturn(getCovidTotalList_Mock());
        Mockito.when(covidRestService.getTotal()).thenReturn(getCovidTotalList_Mock());

        covidTotalList = covidDetailsService.getTotal(false);
        Assert.assertNotNull(covidTotalList);

        Mockito.when(totalRepository.findAll()).thenThrow(new CovidException("Message",new Exception(),HttpStatus.INTERNAL_SERVER_ERROR));
        Mockito.when(apiHistoryRepository.findAllByApiName(COUNTRY, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(true));
        Assertions.assertThrows(
                CovidException.class, () -> {
                    covidDetailsService.getTotal(true);
                }
        );
    }

    /**
     * Test case for get Covid details by country name
     * @throws ParseException
     */
    @Test
    void getCovidDataByNameTest() throws ParseException {
        List<CovidData> covidDataList = null;
        Mockito.when(covidDataRepository.findByCountry(COUNTRY_NAME)).thenReturn(getCovidData_Mock());
        Mockito.when(apiHistoryRepository.findAllByApiNameAndType(NAME,COUNTRY_NAME, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(false));
        covidDataList = covidDetailsService.getCovidDataByName(COUNTRY_NAME,true);
        Assert.assertNotNull(covidDataList);

        Mockito.when(covidDataRepository.findByCountry(COUNTRY_NAME)).thenReturn(getCovidData_Mock());
        Mockito.when(apiHistoryRepository.findAllByApiNameAndType(NAME,COUNTRY_NAME, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(true));
        covidDataList = covidDetailsService.getCovidDataByName(COUNTRY_NAME,false);
        Assert.assertNotNull(covidDataList);
        Mockito.when(covidDataRepository.findByCountry(COUNTRY_NAME)).thenThrow(new CovidRapidAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Exception Occurred While Getting Data from Service","Message"));
        Mockito.when(apiHistoryRepository.findAllByApiName(NAME, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(true));
        Assertions.assertThrows(
                CovidRapidAPIException.class, () -> {
                    covidDetailsService.getCovidDataByName(COUNTRY_NAME,true);
                }
        );

    }

    /**
     * Test case for get Covid details by country name
     * @throws ParseException
     */
    @Test
    void getCovidDataByNameMethodTest() throws ParseException {
        List<CovidData> covidDataList = null;
        Mockito.when(covidDataRepository.findByCountry(COUNTRY_NAME)).thenReturn(getCovidData_Mock());
        Mockito.when(apiHistoryRepository.findAllByApiNameAndType(NAME,COUNTRY_NAME, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(false));
        Mockito.when(covidDataRepository.findByCountry(COUNTRY_NAME)).thenReturn(getCovidData_Mock());
        Mockito.when(covidRestService.getCovidDataByName(COUNTRY_NAME)).thenReturn(getCovidDataList_Mock());
        Mockito.when(covidDataRepository.save(getCovidData_Mock())).thenReturn(null);
        covidDataList = covidDetailsService.getCovidDataByName(COUNTRY_NAME,false);
        Assert.assertNotNull(covidDataList);


        Mockito.when(covidDataRepository.findByCountry(COUNTRY_NAME)).thenThrow(new CovidException("Message",new Exception(),HttpStatus.INTERNAL_SERVER_ERROR));
        Mockito.when(apiHistoryRepository.findAllByApiNameAndType(NAME,COUNTRY_NAME, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(true));
        Assertions.assertThrows(
                CovidException.class, () -> {
                    covidDetailsService.getCovidDataByName(COUNTRY_NAME,true);
                }
        );
    }

    /**
     * Test case for get Covid details by country code
     * @throws ParseException
     */
    @Test
    void getCovidDataByCodeTest() throws ParseException {
        List<CovidData> covidDataList = null;
        Mockito.when(covidDataRepository.findByCode(ALPHA_2_CODE)).thenReturn(getCovidData_Mock());
        Mockito.when(apiHistoryRepository.findAllByApiNameAndType(CODE,ALPHA_2_CODE, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(false));
        covidDataList = covidDetailsService.getCovidDataByCode(ALPHA_2_CODE,true);
        Assert.assertNotNull(covidDataList);

        Mockito.when(covidDataRepository.findByCode(ALPHA_2_CODE)).thenReturn(getCovidData_Mock());
        Mockito.when(apiHistoryRepository.findAllByApiNameAndType(CODE,ALPHA_2_CODE, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(true));
        covidDataList = covidDetailsService.getCovidDataByCode(ALPHA_2_CODE,false);
        Assert.assertNotNull(covidDataList);

        Mockito.when(covidDataRepository.findByCode(ALPHA_2_CODE)).thenThrow(new CovidRapidAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Exception Occurred While Getting Data from Service","Message"));
        Mockito.when(apiHistoryRepository.findAllByApiName(CODE, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(true));
        Assertions.assertThrows(
                CovidRapidAPIException.class, () -> {
                    covidDetailsService.getCovidDataByCode(ALPHA_2_CODE,true);
                }
        );

    }

    /**
     * Test case for get Covid details by country code
     * @throws ParseException
     */
    @Test
    void getCovidDataByCodeMethodTest() throws ParseException {

        List<CovidData> covidDataList = null;
        Mockito.when(covidDataRepository.findByCode(ALPHA_2_CODE)).thenReturn(getCovidData_Mock());
        Mockito.when(apiHistoryRepository.findAllByApiNameAndType(CODE,ALPHA_2_CODE, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(false));
        Mockito.when(covidDataRepository.findByCountry(ALPHA_2_CODE)).thenReturn(getCovidData_Mock());
        Mockito.when(covidRestService.getCovidDataByCode(ALPHA_2_CODE)).thenReturn(getCovidDataList_Mock());
        Mockito.when(covidDataRepository.save(getCovidData_Mock())).thenReturn(null);
        covidDataList = covidDetailsService.getCovidDataByCode(ALPHA_2_CODE,false);
        Assert.assertNotNull(covidDataList);

        Mockito.when(covidDataRepository.findByCode(ALPHA_2_CODE)).thenReturn(getCovidData_Mock());
        Mockito.when(apiHistoryRepository.findAllByApiNameAndType(CODE,ALPHA_2_CODE, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(false));
        Mockito.when(covidDataRepository.findByCountry(ALPHA_2_CODE)).thenReturn(getCovidData_Mock());
        Mockito.when(covidRestService.getCovidDataByCode(ALPHA_2_CODE)).thenReturn(Collections.emptyList());
        Assertions.assertThrows(
                CovidException.class, () -> {
                    covidDetailsService.getCovidDataByCode(ALPHA_2_CODE,false);
                }
        );;

        Mockito.when(covidDataRepository.findByCode(ALPHA_2_CODE)).thenThrow(new CovidException("Message",
                new Exception(),HttpStatus.INTERNAL_SERVER_ERROR));
        Mockito.when(apiHistoryRepository.findAllByApiNameAndType(CODE,ALPHA_2_CODE, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(true));
        Assertions.assertThrows(
                CovidException.class, () -> {
                    covidDetailsService.getCovidDataByCode(ALPHA_2_CODE,true);
                }
        );
    }

    /**
     * Test case for updating country as favorite or not
     */
    @Test
    void updateCountryTest(){
        Mockito.when(countryRepository.findByName(COUNTRY_NAME)).thenReturn(getCountry_Mock());
        Mockito.when(countryRepository.save(getCountry_Mock())).thenReturn(null);
        //Mockito.doNothing().when(covidDetailsService).updateCountry(getCountry_Mock());
        covidDetailsService.updateCountry(getCountry_Mock());


        Mockito.when(countryRepository.findByName(COUNTRY_NAME)).thenThrow(new CovidException("Message",
                new Exception(),HttpStatus.INTERNAL_SERVER_ERROR));
        Assertions.assertThrows(
                CovidException.class, () -> {
                    covidDetailsService.updateCountry(getCountry_Mock());
                }
        );
    }

    /**
     * Test case for getting countries code map
     * @throws ParseException
     */
    @Test
    void getCountriesCodeMapTest() throws ParseException {

        Map<String, Set<String>> countriesCodeMap = null;
        Mockito.when(apiHistoryRepository.findAllByApiName(COUNTRY, Sort.Direction.DESC)).thenReturn(getApiHistoryList_Mock(true));
        Mockito.when(countryRepository.findAllByOrderByFavouriteDescName()).thenReturn(getCountryListCountryCodeMap_Mock());
        countriesCodeMap = covidDetailsService.getCountriesCodeMap();
        Assert.assertNotNull(countriesCodeMap);

        Mockito.when(countryRepository.findAllByOrderByFavouriteDescName()).thenThrow(new CovidException("Message",
                new Exception(),HttpStatus.INTERNAL_SERVER_ERROR));
        Assertions.assertThrows(
                CovidException.class, () -> {
                    covidDetailsService.getCountriesCodeMap();
                }
        );
    }

    /**
     * Test cases for adding comment for specific country by name
     */
    @Test
    void addCommentByNameTest(){
        Map<String,String> counrtyCodeMap = new HashMap<>();
        counrtyCodeMap.put(NAME,COUNTRY_NAME);
        counrtyCodeMap.put(COMMENT,COMMENT_BY_NAME);
        Mockito.when(covidDataRepository.findByCountry(counrtyCodeMap.get(NAME))).thenReturn(getCovidData_Mock());
        Mockito.when(covidDataRepository.save(getCovidData_Mock())).thenReturn(null);
        covidDetailsService.addCommentByName(counrtyCodeMap);

        Mockito.when(covidDataRepository.findByCountry(counrtyCodeMap.get(NAME))).thenReturn(null);
        Assertions.assertThrows(
                CovidException.class, () -> {
                    covidDetailsService.addCommentByName(counrtyCodeMap);
                }
        );

        Mockito.when(covidDataRepository.findByCountry(counrtyCodeMap.get(NAME))).thenThrow(new CovidException("Message",
                new Exception(),HttpStatus.INTERNAL_SERVER_ERROR));
        Assertions.assertThrows(
                CovidException.class, () -> {
                    covidDetailsService.addCommentByName(counrtyCodeMap);
                }
        );
    }

    /**
     * Test cases for adding comment for specific country by code
     */
    @Test
    void addCommentByCodeTest(){
        Map<String,String> counrtyCodeMap = new HashMap<>();
        counrtyCodeMap.put(CODE,ALPHA_2_CODE);
        counrtyCodeMap.put(COMMENT,COMMENT_BY_NAME);
        Mockito.when(covidDataRepository.findByCode(counrtyCodeMap.get(CODE))).thenReturn(getCovidData_Mock());
        Mockito.when(covidDataRepository.save(getCovidData_Mock())).thenReturn(null);
        covidDetailsService.addCommentByCode(counrtyCodeMap);

        Mockito.when(covidDataRepository.findByCode(counrtyCodeMap.get(CODE))).thenReturn(null);
        Assertions.assertThrows(
                CovidException.class, () -> {
                    covidDetailsService.addCommentByCode(counrtyCodeMap);
                }
        );

        Mockito.when(covidDataRepository.findByCode(counrtyCodeMap.get(CODE))).thenThrow(new CovidException("Message",
                new Exception(),HttpStatus.INTERNAL_SERVER_ERROR));
        Assertions.assertThrows(
                CovidException.class, () -> {
                    covidDetailsService.addCommentByCode(counrtyCodeMap);
                }
        );
    }

    /**
     * Test cases for getting all comments for specific country by name
     */
    @Test
    void getCommentByNameTest(){
        Mockito.when(covidDataRepository.findByCountry(COUNTRY_NAME)).thenReturn(getCovidData_Mock());
        List<String> commentList = covidDetailsService.getCommentByName(COUNTRY_NAME);
        Assert.assertNotNull(commentList);

        Mockito.when(covidDataRepository.findByCountry(COUNTRY_NAME)).thenReturn(null);
        Assertions.assertThrows(
                CovidException.class, () -> {
                    covidDetailsService.getCommentByName(COUNTRY_NAME);
                }
        );

        Mockito.when(covidDataRepository.findByCountry(COUNTRY_NAME)).thenThrow(new CovidException("Message",
                new Exception(),HttpStatus.INTERNAL_SERVER_ERROR));
        Assertions.assertThrows(
                CovidException.class, () -> {
                    covidDetailsService.getCommentByName(COUNTRY_NAME);
                }
        );
    }

    /**
     * Test cases for getting all comments for specific country by code
     */
    @Test
    void getCommentByCodeTest(){
        Mockito.when(covidDataRepository.findByCode(ALPHA_2_CODE)).thenReturn(getCovidData_Mock());
        List<String> commentList = covidDetailsService.getCommentByCode(ALPHA_2_CODE);
        Assert.assertNotNull(commentList);

        Mockito.when(covidDataRepository.findByCode(ALPHA_2_CODE)).thenReturn(null);
        Assertions.assertThrows(
                CovidException.class, () -> {
                    covidDetailsService.getCommentByCode(ALPHA_2_CODE);
                }
        );

        Mockito.when(covidDataRepository.findByCode(ALPHA_2_CODE)).thenThrow(new CovidException("Message",
                new Exception(),HttpStatus.INTERNAL_SERVER_ERROR));
        Assertions.assertThrows(
                CovidException.class, () -> {
                    covidDetailsService.getCommentByCode(ALPHA_2_CODE);
                }
        );
    }

}
