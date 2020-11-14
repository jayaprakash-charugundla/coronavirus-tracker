package com.jgeek.coronavirustracker.controllers;

import com.jgeek.coronavirustracker.models.LocationStats;
import com.jgeek.coronavirustracker.services.CoronaVirusDataService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(LocationStats::getLatestTotalCases).sum();
        long countries = allStats.stream().map(LocationStats::getCountry).distinct().count();
        /*long indiaCases = allStats.stream()
            .filter(locationStats -> locationStats.getCountry().equals("India")).count();*/
        int totalNewCases = allStats.stream().mapToInt(LocationStats::getDiffFromPrevDay).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        model.addAttribute("countries", countries);
        return "home";
    }
}
