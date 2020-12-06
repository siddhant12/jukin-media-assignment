package com.covid.tracker.controller;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static com.covid.tracker.mockdata.CovidMockData.getModel_Mock;

/**
 * We can also assert our unit test cases with the expected response from the API
 */


@SpringBootTest
public class HomeControllerTest {

    @InjectMocks
    private HomeController homeController;

    /**
     * Test case for getting home page
     */
    @Test
    void getHomePageTest(){
        String page = homeController.getHomePage(getModel_Mock());
        Assert.assertNotNull(page);
    }

    /**
     * Test case for getting country page
     */
    @Test
    void getCountryPageTest(){
        String page = homeController.getCountryPage(getModel_Mock());
        Assert.assertNotNull(page);
    }

    /**
     * Test case for getting country by name page
     */
    @Test
    void getCountryByNameTest(){
        String page = homeController.getCountryByName(getModel_Mock());
        Assert.assertNotNull(page);
    }

    /**
     * Test case for getting country by code page
     */
    @Test
    void getCountryByCodeTest(){
        String page = homeController.getCountryByCode(getModel_Mock());
        Assert.assertNotNull(page);
    }

}
