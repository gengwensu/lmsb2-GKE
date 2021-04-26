package com.gsucode.lmsb.gamification.game.badgeprocessors;

import com.gsucode.lmsb.gamification.game.domain.BadgeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class SilverBadgeProcessorTest {
    private SilverBadgeProcessor badgeProcessor;

    @BeforeEach
    void setUp(){
        badgeProcessor = new SilverBadgeProcessor();
    }

    @Test
    void grantBadgewhenScoreOver() {
        //given
        //when
        Optional<BadgeType> badgeTypeOptional = badgeProcessor.processForOptionalBadge(
                260,
                List.of(),
                null
        );
        //then
        then(badgeTypeOptional).contains(BadgeType.SILVER);
    }

    @Test
    void grantNoBadgeWhenScoreUnder() {
        //given
        //when
        Optional<BadgeType> badgeTypeOptional = badgeProcessor.processForOptionalBadge(
                240,
                List.of(),
                null
        );
        //then
        then(badgeTypeOptional).isEmpty();
    }
}