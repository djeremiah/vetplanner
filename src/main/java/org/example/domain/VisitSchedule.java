package org.example.domain;

import java.util.List;

import org.optaplanner.core.api.domain.constraintweight.ConstraintConfigurationProvider;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

@PlanningSolution
public class VisitSchedule {

    public static final Long SINGLETON_PROBLEM_ID = 1L;

    @ConstraintConfigurationProvider
    private VisitConstraintConfiguration constraintConfiguration;

    @ProblemFactCollectionProperty
    private List<Holding> holdingList;

    @ProblemFactCollectionProperty
    private List<Day> dayList;
    
    @ValueRangeProvider(id = "timeGrainRange")
    @ProblemFactCollectionProperty
    private List<TimeGrain> timeGrainList;
    
    @ValueRangeProvider(id = "vetRange")
    @ProblemFactCollectionProperty
    private List<Vet> vetList;
    
    @PlanningEntityCollectionProperty
    private List<Visit> visitList;

    @PlanningScore
    private HardMediumSoftScore score;

    public VisitSchedule(){}

    public VisitSchedule(List<Holding> holdingList, List<Day> dayList, List<TimeGrain> timeGrainList, List<Vet> vetList, List<Visit> visitList, VisitConstraintConfiguration constraintConfiguration){
        this.holdingList = holdingList;
        this.dayList = dayList;
        this.timeGrainList = timeGrainList;
        this.vetList = vetList;
        this.visitList = visitList;
        this.constraintConfiguration = constraintConfiguration;
    }

    public VisitConstraintConfiguration getConstraintConfiguration() {
        return constraintConfiguration;
    }

    public void setConstraintConfiguration(VisitConstraintConfiguration constraintConfiguration) {
        this.constraintConfiguration = constraintConfiguration;
    }

    public List<Holding> getHoldingList() {
        return holdingList;
    }

    public void setHoldingList(List<Holding> holdingList) {
        this.holdingList = holdingList;
    }

    public List<Day> getDayList() {
        return dayList;
    }

    public void setDayList(List<Day> dayList) {
        this.dayList = dayList;
    }

    public List<TimeGrain> getTimeGrainList() {
        return timeGrainList;
    }

    public void setTimeGrainList(List<TimeGrain> timeGrainList) {
        this.timeGrainList = timeGrainList;
    }

    public List<Vet> getVetList() {
        return vetList;
    }

    public void setVetList(List<Vet> vetList) {
        this.vetList = vetList;
    }

    public List<Visit> getVisitList() {
        return visitList;
    }

    public void setVisitList(List<Visit> visitList) {
        this.visitList = visitList;
    }

    public HardMediumSoftScore getScore() {
        return score;
    }

    public void setScore(HardMediumSoftScore score) {
        this.score = score;
    }

}
