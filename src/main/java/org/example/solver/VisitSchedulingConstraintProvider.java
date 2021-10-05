package org.example.solver;

import static org.optaplanner.core.api.score.stream.Joiners.equal;
import static org.optaplanner.core.api.score.stream.Joiners.filtering;
import static org.optaplanner.core.api.score.stream.Joiners.lessThan;
import static org.optaplanner.core.api.score.stream.Joiners.overlapping;
import static org.optaplanner.core.api.score.stream.ConstraintCollectors.sum;

import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

import org.example.domain.TimeGrain;
import org.example.domain.Visit;

public class VisitSchedulingConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                vetConflict(constraintFactory),
                avoidOvertime(constraintFactory),
                startAndEndOnSameDay(constraintFactory),
                doVisitsAsSoonAsPossible(constraintFactory),
                scheduleBiggerHoldingsFirst(constraintFactory),
        };
    }

    // ************************************************************************
    // Hard constraints
    // ************************************************************************

    protected Constraint vetConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.fromUnfiltered(Visit.class)
                .filter(leftVisit -> leftVisit.getVet() != null && leftVisit.getStartingTimeGrain() != null)
                .join(constraintFactory.fromUnfiltered(Visit.class).filter(rightVisit -> rightVisit.getStartingTimeGrain() != null),
                        equal(Visit::getVet),
                        lessThan(Visit::getId),
                        overlapping(visit -> visit.getStartingTimeGrain().getGrainIndex(),
                                visit -> visit.getStartingTimeGrain().getGrainIndex() +
                                        visit.getHolding().getDurationInGrains()))
                .penalizeConfigurable("Vet conflict",
                        (leftAssignment, rightAssignment) -> rightAssignment.calculateOverlap(leftAssignment));
    }

    protected Constraint avoidOvertime(ConstraintFactory constraintFactory) {
        return constraintFactory.fromUnfiltered(Visit.class)
                .filter(visit -> visit.getStartingTimeGrain() != null)
                .ifNotExists(TimeGrain.class,
                        equal(Visit::getLastTimeGrainIndex, TimeGrain::getGrainIndex))
                .penalizeConfigurable("Don't go in overtime", Visit::getLastTimeGrainIndex);
    }

    protected Constraint startAndEndOnSameDay(ConstraintFactory constraintFactory) {
        return constraintFactory.fromUnfiltered(Visit.class)
                .filter(visit -> visit.getStartingTimeGrain() != null)
                .join(TimeGrain.class,
                        equal(Visit::getLastTimeGrainIndex, TimeGrain::getGrainIndex),
                        filtering((visit,
                                timeGrain) -> visit.getStartingTimeGrain().getDay() != timeGrain.getDay()))
                .penalizeConfigurable("Start and end on same day");
    }

    // ************************************************************************
    // Medium constraints
    // ************************************************************************

    protected Constraint scheduleBiggerHoldingsFirst(ConstraintFactory constraintFactory) {
        return constraintFactory.fromUnfiltered(Visit.class)
                .filter(visit -> visit.getVet() != null && visit.getStartingTimeGrain() == null)
                .penalizeConfigurable("Schedule bigger holdings first", visit -> visit.getHolding().getDurationInGrains());
    }

    // ************************************************************************
    // Soft constraints
    // ************************************************************************

    protected Constraint doVisitsAsSoonAsPossible(ConstraintFactory constraintFactory) {
        return constraintFactory.fromUnfiltered(Visit.class)
                .filter(visit -> visit.getStartingTimeGrain() != null)
                .penalizeConfigurable("Do all visits as soon as possible",
                        Visit::getLastTimeGrainIndex);
    }

    
   
}
