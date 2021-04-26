package com.gsucode.lmsb.gamification.game.badgeprocessors;

import com.gsucode.lmsb.gamification.game.domain.BadgeType;
import com.gsucode.lmsb.gamification.game.domain.ScoreCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class FirstWonBadgeProcessorTest {
    private FirstWonBadgeProcessor badgeProcessor;

    @BeforeEach
    void setUp(){
        badgeProcessor = new FirstWonBadgeProcessor();
    }

    @Test
    void grantBadgewhenScoreOver() {
        //given
        //when
        Optional<BadgeType> badgeTypeOptional = badgeProcessor.processForOptionalBadge(
                10,
                List.of(new ScoreCard(1L, 1L)),
                null
        );
        //then
        then(badgeTypeOptional).contains(BadgeType.FIRST_WON);
    }

    @Test
    void grantNoBadgeWhenScoreUnder() {
        //given
        //when
        Optional<BadgeType> badgeTypeOptional = badgeProcessor.processForOptionalBadge(
                20,
                List.of(new ScoreCard(1L, 1L), new ScoreCard(1L, 2L)),
                null
        );
        //then
        then(badgeTypeOptional).isEmpty();
    }
}