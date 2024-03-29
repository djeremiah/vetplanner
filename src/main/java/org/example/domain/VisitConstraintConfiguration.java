package org.example.domain;

import org.optaplanner.core.api.domain.constraintweight.ConstraintConfiguration;
import org.optaplanner.core.api.domain.constraintweight.ConstraintWeight;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@ConstraintConfiguration(constraintPackage = "org.example.score")
public class VisitConstraintConfiguration {

    public static final String VET_CONFLICT = "Vet conflict";
    public static final String DONT_GO_IN_OVERTIME = "Don't go in overtime";
    public static final String START_AND_END_ON_SAME_DAY = "Start and end on same day";

    public static final String SCHEDULE_BIGGER_HOLDINGS_FIRST = "Schedule bigger holdings first";

    public static final String DO_ALL_MEETINGS_AS_SOON_AS_POSSIBLE = "Do all visits as soon as possible";
    

    private long id;

    @ConstraintWeight(VET_CONFLICT)
    private HardMediumSoftScore vetConflict = HardMediumSoftScore.ofHard(1);
    @ConstraintWeight(DONT_GO_IN_OVERTIME)
    private HardMediumSoftScore dontGoInOvertime = HardMediumSoftScore.ofHard(1);
    @ConstraintWeight(START_AND_END_ON_SAME_DAY)
    private HardMediumSoftScore startAndEndOnSameDay = HardMediumSoftScore.ofHard(1);

    @ConstraintWeight(SCHEDULE_BIGGER_HOLDINGS_FIRST)
    private HardMediumSoftScore scheduleBiggerHoldingsFirst = HardMediumSoftScore.ofMedium(1);

    @ConstraintWeight(DO_ALL_MEETINGS_AS_SOON_AS_POSSIBLE)
    private HardMediumSoftScore doAllMeetingsAsSoonAsPossible = HardMediumSoftScore.ofSoft(1);

    public VisitConstraintConfiguration() {
    }

    public VisitConstraintConfiguration(long id) {
        this.id = id;
    }

    // ************************************************************************
    // Simple getters and setters
    // ************************************************************************

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    

}
