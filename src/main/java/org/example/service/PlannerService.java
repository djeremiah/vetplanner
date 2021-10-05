package org.example.service;

import java.util.List;

import org.example.domain.Holding;
import org.example.domain.Vet;
import org.example.domain.Visit;
import org.example.domain.VisitSchedule;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PlannerService {

    @Autowired
    private SolverManager<VisitSchedule, Long> solverManager;
    @Autowired
    private VisitScheduleRepository visitScheduleRepository;

    @GetMapping("/holdings")
    public List<Holding> holdings(){
        return visitScheduleRepository.getSingletonVisitSchedule().getHoldingList();
    }

    @GetMapping("/vets")
    public List<Vet> vets(){
        return visitScheduleRepository.getSingletonVisitSchedule().getVetList();
    }

    // get the current solution
    @GetMapping("/solution")
    public List<Visit> solution(){
        return visitScheduleRepository.getSingletonVisitSchedule().getVisitList();
    }

    // start solving
    @GetMapping("/solve")
    public String solve(){
        SolverJob<VisitSchedule, Long> solverJob = solverManager.solveAndListen(VisitSchedule.SINGLETON_PROBLEM_ID, visitScheduleRepository::getVisitSchedule, visitScheduleRepository::saveVisitSchedule);

        return String.format("Solver: {}; Status: {}", solverJob.getProblemId(), solverJob.getSolverStatus());
    }

    // stop solving
    @GetMapping("/stop")
    public String stop(){
        solverManager.terminateEarly(VisitSchedule.SINGLETON_PROBLEM_ID);
        SolverStatus status = solverManager.getSolverStatus(VisitSchedule.SINGLETON_PROBLEM_ID);

        return String.format("Solver: {}; Status: {}", VisitSchedule.SINGLETON_PROBLEM_ID, status);
    }

    
}
