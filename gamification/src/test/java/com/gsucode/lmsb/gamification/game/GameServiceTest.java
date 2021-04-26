package com.gsucode.lmsb.gamification.game;

import com.gsucode.lmsb.gamification.challenge.ChallengeSolvedDTO;
import com.gsucode.lmsb.gamification.game.GameService.GameResult;
import com.gsucode.lmsb.gamification.game.badgeprocessors.BadgeProcessor;
import com.gsucode.lmsb.gamification.game.domain.BadgeCard;
import com.gsucode.lmsb.gamification.game.domain.BadgeType;
import com.gsucode.lmsb.gamification.game.domain.ScoreCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class GameServiceTest {
    private GameService gameService;

    @Mock
    private ScoreRepository scoreRepository;
    @Mock
    private BadgeRepository badgeRepository;
    @Mock
    BadgeProcessor badgeProcessor;

    @BeforeEach
    void setUp() {
        gameService = new GameServiceImpl(scoreRepository, badgeRepository, List.of(badgeProcessor));
    }

    @Test
    void attemptCorrectResultTest() {
        //given
        long userId = 1L, attemptId = 10L;
        ChallengeSolvedDTO challengeSolvedDTO = new ChallengeSolvedDTO(attemptId, true, 30, 42, userId, "john_doe");
        ScoreCard scoreCard = new ScoreCard(userId, attemptId);
        given(scoreRepository.getTotalScoreForUser(userId)).willReturn(Optional.of(10));
        given(scoreRepository.findByUserIdOrderByScoreTimestampDesc(userId)).willReturn((List.of(scoreCard)));
        given(badgeRepository.findByUserIdOrderByBadgeTimestampDesc(userId)).willReturn(List.of(new BadgeCard(userId, BadgeType.FIRST_WON)));
        given(badgeProcessor.badgeType()).willReturn(BadgeType.LUCKY_NUMBER);
        given(badgeProcessor.processForOptionalBadge(10, List.of(scoreCard), challengeSolvedDTO)).willReturn(Optional.of(BadgeType.LUCKY_NUMBER));

        //when
        final GameResult result = gameService.newAttemptForUser(challengeSolvedDTO);

        //then
        then(result).isEqualTo(new GameResult(10, List.of(BadgeType.LUCKY_NUMBER)));
        verify(scoreRepository).save(scoreCard);
        verify(badgeRepository).saveAll(List.of(new BadgeCard(userId, BadgeType.LUCKY_NUMBER)));

    }

    @Test
    void attemptWrongResultTest() {
        //given
        ChallengeSolvedDTO challengeSolvedDTO = new ChallengeSolvedDTO(100L, false, 30, 50, 200L, "john_doe");

        //when
        GameResult result = gameService.newAttemptForUser(challengeSolvedDTO);

        //then
        then(result).isEqualTo(new GameResult(0, List.of()));

    }
}