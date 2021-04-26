package com.gsucode.lmsb.gamification.game.badgeprocessors;

import com.gsucode.lmsb.gamification.challenge.ChallengeSolvedDTO;
import com.gsucode.lmsb.gamification.game.domain.BadgeType;
import com.gsucode.lmsb.gamification.game.domain.ScoreCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class LuckyNumberBadgeProcessorTest {
    private LuckyNumberBadgeProcessor badgeProcessor;

    @BeforeEach
    void setUp(){
        badgeProcessor = new LuckyNumberBadgeProcessor();
    }

    @Test
    void grantBadgewhenScoreOver() {
        //given
        //when
        Optional<BadgeType> badgeTypeOptional = badgeProcessor.processForOptionalBadge(
                10,
                List.of(new ScoreCard(1L, 1L)),
                new ChallengeSolvedDTO(1L, true, 42, 10, 1L, "john")
        );
        //then
        then(badgeTypeOptional).contains(BadgeType.LUCKY_NUMBER);
    }

    @Test
    void grantNoBadgeWhenScoreUnder() {
        //given
        //when
        Optional<BadgeType> badgeTypeOptional = badgeProcessor.processForOptionalBadge(
                10,
                List.of(new ScoreCard(1L, 1L)),
                new ChallengeSolvedDTO(1L, true, 43, 10, 1L, "john")
        );
        //then
        then(badgeTypeOptional).isEmpty();
    }
}