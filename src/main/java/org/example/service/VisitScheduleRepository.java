package org.example.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.example.domain.Day;
import org.example.domain.Holding;
import org.example.domain.TimeGrain;
import org.example.domain.Vet;
import org.example.domain.Visit;
import org.example.domain.VisitConstraintConfiguration;
import org.example.domain.VisitSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class VisitScheduleRepository{

    private static final Logger LOG = LoggerFactory.getLogger(VisitScheduleRepository.class);

    private List<Visit> visitList;
    private List<Holding> holdingList;
    private List<Vet> vetList;
    private List<Day> dayList;
    private List<TimeGrain> timeGrainList;

    @PostConstruct
    public void init(){
        // Load Holdings and Vets
        holdingList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("vetplanner_holdings.csv")))){
            br.lines().forEach(l -> {
                String[] args = l.split(",");
                holdingList.add(new Holding(Long.parseLong(args[0]), args[1], Holding.Size.valueOf(args[2])));
            });
        } catch (IOException e){
            LOG.error("Failed parsing vetplanner_holdings.csv", e);
        }

        vetList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("vetplanner_vets.csv")))){
            br.lines().forEach(l ->{
                String[] args = l.split(",");
                vetList.add(new Vet(Integer.parseInt(args[0]),args[1]));
            });
        } catch (IOException e){
            LOG.error("Failed parsing vetplanner_vets.csv", e);
        }

        // Set time window to the next seven days
        int startingDay = LocalDate.now().getDayOfYear();
        int startingMinuteOfDay = LocalTime.of(8, 30).toSecondOfDay() / 60;

        dayList = new ArrayList<>();
        timeGrainList = new ArrayList<>();
        int i = 1;
        for(int d = startingDay; d < startingDay + 7; d++){
            Day day = new Day(d);
            dayList.add(day);
            for(int m = startingMinuteOfDay; m < startingMinuteOfDay + (8 * 60); m+=TimeGrain.GRAIN_LENGTH_IN_MINUTES){
                timeGrainList.add(new TimeGrain(i++, day, m));
            }

        }

        // initialize visit list
        visitList = new ArrayList<>();
        long visitId = 0;
        for(Holding holding : holdingList){
            visitList.add(new Visit(visitId++, holding, null, null));
        }
    }

    public VisitSchedule getSingletonVisitSchedule(){
        return getVisitSchedule(VisitSchedule.SINGLETON_PROBLEM_ID);
    }

    public VisitSchedule getVisitSchedule(Long scheduleId) {
        return new VisitSchedule(holdingList, dayList, timeGrainList, vetList, visitList, new VisitConstraintConfiguration());
    }

    public void saveVisitSchedule(VisitSchedule visitSchedule) {
        this.visitList = visitSchedule.getVisitList();
    }

}