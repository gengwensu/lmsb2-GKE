package com.gsucode.lmsb.gamification.game.badgeprocessors;

import com.gsucode.lmsb.gamification.game.domain.BadgeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class GoldBadgeProcessorTest {
    private GoldBadgeProcessor badgeProcessor;

    @BeforeEach
    void setUp(){
        badgeProcessor = new GoldBadgeProcessor();
    }

    @Test
    void grantBadgewhenScoreOver() {
        //given
        //when
        Optional<BadgeType> badgeTypeOptional = badgeProcessor.processForOptionalBadge(
                510,
                List.of(),
                null
        );
        //then
        then(badgeTypeOptional).contains(BadgeType.GOLD);
    }

    @Test
    void grantNoBadgeWhenScoreUnder() {
        //given
        //when
        Optional<BadgeType> badgeTypeOptional = badgeProcessor.processForOptionalBadge(
                490,
                List.of(),
                null
        );
        //then
        then(badgeTypeOptional).isEmpty();
    }
}