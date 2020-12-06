package com.covid.tracker.mockdata;

import com.covid.tracker.model.ApiHistory;
import com.covid.tracker.model.Country;
import com.covid.tracker.model.CovidData;
import com.covid.tracker.model.CovidTotal;
import org.bson.types.ObjectId;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class for creating mock objects
 */
public class CovidMockData {

    public static final String COUNTRY_NAME = "India";
    public static final String ALPHA_2_CODE = "IN";
    public static final String ALPHA_3_CODE = "IND";
    public static final double LATITUDE = 20.593684;
    public static final double LONGITUDE = 78.96288;
    public static final boolean FAVOURITE = false;
    public static final String COMMENT_BY_NAME = "Comment";

    public static List<Country> getCountryList_Mock(){
        List<Country> countryList = new ArrayList<>();
        countryList.add(getCountry_Mock());
        return countryList;
    }

    public static Country getCountry_Mock(){
        Country country = new Country();
        country.set_id(new ObjectId());
        country.setName(COUNTRY_NAME);
        country.setAlpha2code(ALPHA_2_CODE);
        country.setAlpha3code(ALPHA_3_CODE);
        country.setLatitude(LATITUDE);
        country.setLongitude(LONGITUDE);
        country.setFavourite(FAVOURITE);
        return country;
    }

    public static CovidTotal getCovidTotal_Mock(){
        CovidTotal covidTotal = new CovidTotal();
        covidTotal.set_id(new ObjectId());
        covidTotal.setConfirmed(new BigInteger("212132"));
        return covidTotal;
    }

    public static List<CovidTotal> getCovidTotalList_Mock(){
        List<CovidTotal> covidTotalList = new ArrayList<>();
        covidTotalList.add(getCovidTotal_Mock());
        return covidTotalList;
    }

    public static CovidData getCovidData_Mock(){
        List<String> commentsList = new ArrayList<>();
        commentsList.add("Serious Situation");
        CovidData covidData = new CovidData();
        covidData.set_id(new ObjectId());
        covidData.setCountry("India");
        covidData.setConfirmed(new BigInteger("100000"));
        covidData.setRecovered(new BigInteger("70000"));
        covidData.setComments(commentsList);
        return covidData;
    }

    public static List<CovidData> getCovidDataList_Mock(){
        List<CovidData> covidDataList = new ArrayList<>();
        covidDataList.add(getCovidData_Mock());
        return covidDataList;
    }

    public static List<String> getCommentsList_Mock(){
        List<String> commentsList = new ArrayList<>();
        commentsList.add("Comment:1");
        commentsList.add("Comment:2");
        return commentsList;
    }

    public static Map<String , Set<String>> getCountriesCodeMap_Mock(){
        Map<String,Set<String>> map = new HashMap<>();
        Set<String> set = new HashSet<>();
        set.add(ALPHA_2_CODE);
        set.add(ALPHA_3_CODE);
        map.put(COUNTRY_NAME,set);
        return map;
    }

    public static Map<String,String> getCommentByNameMap_Mock(){
        Map<String,String> map = new HashMap<>();
        map.put(COUNTRY_NAME,"Rising cases rapidly");
        return map;
    }

    public static Map<String,String> getCommentByCodeMap_Mock(){
        Map<String,String> map = new HashMap<>();
        map.put(ALPHA_2_CODE,"Rising cases rapidly");
        return map;
    }

    public static ApiHistory getApiHistory_Mock(boolean latest) throws ParseException {
        ApiHistory apiHistory = new ApiHistory();
        apiHistory.set_id(new ObjectId());
        if(latest){
            apiHistory.setDate(new Date());
        }else{
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = "2020-02-11";
            Date dateObject = sdf.parse(dateString);
            apiHistory.setDate(dateObject);
        }

        apiHistory.setApiName("total");
        return apiHistory;
    }

    public static List<ApiHistory> getApiHistoryList_Mock(boolean latest) throws ParseException {
        List<ApiHistory> apiHistoryList = new ArrayList<>();
        apiHistoryList.add(getApiHistory_Mock(latest));
        return apiHistoryList;
    }


    public static List<Country> getCountryListCountryCodeMap_Mock(){
        List<Country> countryList = new ArrayList<>();
        countryList.add(getCountry_Mock());
        Country country = new Country();
        country.set_id(new ObjectId());
        country.setName("United States of America");
        country.setAlpha2code("US");
        country.setAlpha3code("USA");
        country.setLatitude(17.593684);
        country.setLongitude(48.96288);
        country.setFavourite(FAVOURITE);
        countryList.add(country);
        return countryList;
    }

    public static Model getModel_Mock(){
        return new ConcurrentModel();
    }

}
