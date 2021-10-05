package org.example.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity()
public class Visit {

    private long id;
    private Holding holding;
    
    // planning variables
    private TimeGrain startingTimeGrain;
    private Vet vet;
    

    public Visit() {
    }

    public Visit(long id, Holding holding, TimeGrain startingTimeGrain, Vet vet) {
        this.id = id;
        this.holding = holding;
        this.startingTimeGrain = startingTimeGrain;
        this.vet = vet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Holding getHolding() {
        return holding;
    }

    public void setHolding(Holding holding) {
        this.holding = holding;
    }

    @PlanningVariable(valueRangeProviderRefs = { "timeGrainRange" }, nullable = true)
    public TimeGrain getStartingTimeGrain() {
        return startingTimeGrain;
    }

    public void setStartingTimeGrain(TimeGrain startingTimeGrain) {
        this.startingTimeGrain = startingTimeGrain;
    }

    @PlanningVariable(valueRangeProviderRefs = { "vetRange" })
    public Vet getVet() {
        return vet;
    }

    public void setVet(Vet vet) {
        this.vet = vet;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    public int calculateOverlap(Visit other) {
        if (startingTimeGrain == null || other.getStartingTimeGrain() == null) {
            return 0;
        }
        int start = startingTimeGrain.getGrainIndex();
        int end = start + holding.getDurationInGrains();
        int otherStart = other.startingTimeGrain.getGrainIndex();
        int otherEnd = otherStart + other.holding.getDurationInGrains();

        if (end < otherStart) {
            return 0;
        } else if (otherEnd < start) {
            return 0;
        }
        return Math.min(end, otherEnd) - Math.max(start, otherStart);
    }

    public Integer getLastTimeGrainIndex() {
        if (startingTimeGrain == null) {
            return null;
        }
        return startingTimeGrain.getGrainIndex() + holding.getDurationInGrains() - 1;
    }

    public String getStartingDateTimeString() {
        if (startingTimeGrain == null ) {
            return null;
        }
        return startingTimeGrain.getDateTimeString();
    }

    @Override
    public String toString() {
        return holding.toString();
    }

}
