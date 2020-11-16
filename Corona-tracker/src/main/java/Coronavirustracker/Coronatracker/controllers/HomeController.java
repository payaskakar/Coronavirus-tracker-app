package Coronavirustracker.Coronatracker.controllers;

import Coronavirustracker.Coronatracker.LocationStats;
import Coronavirustracker.Coronatracker.services.coronvirusservicedata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
        @Autowired
    coronvirusservicedata coronvirusservicedata;
@GetMapping("/")
    public String name(Model model){
   List<LocationStats>allStats= coronvirusservicedata.getAllStats();
   int totalReportedCases=   allStats.stream().mapToInt(stat ->stat.getLatestTotalCases()).sum();
    int newCases=   allStats.stream().mapToInt(stat ->stat.getDiffFromPrevDay()).sum();

    model.addAttribute("LocationStats",allStats);
    model.addAttribute("totalReportedCases",totalReportedCases);
    model.addAttribute("newCases",newCases);

        return "home";
    }
}
