package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @Autowired
    private VisitScheduleRepository visitScheduleRepository;

    @GetMapping
    public String view(Model model){
        model.addAttribute("vets", visitScheduleRepository.getSingletonVisitSchedule().getVetList());
        model.addAttribute("holdings", visitScheduleRepository.getSingletonVisitSchedule().getHoldingList());
        
        return "schedule";
    }
    
}
