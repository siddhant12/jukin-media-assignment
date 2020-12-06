package com.covid.tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping(value = { "/home" })
	public String getHomePage(Model model) {
		return "homePage";
	}

	@GetMapping(value = { "/country" })
	public String getCountryPage(Model model) {
		return "country";
	}

	@GetMapping(value = { "/countryByName" })
	public String getCountryByName(Model model) {
		return "countryByName";
	}

	@GetMapping(value = { "/countryByCode" })
	public String getCountryByCode(Model model) {
		return "countryByCode";
	}

}
