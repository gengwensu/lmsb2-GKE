package com.gsucode.lmsb.gamification.game;

import com.gsucode.lmsb.gamification.game.domain.BadgeCard;
import com.gsucode.lmsb.gamification.game.domain.BadgeType;
import com.gsucode.lmsb.gamification.game.domain.LeaderBoardRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class LeaderBoardServiceTest {
    private LeaderBoardService leaderBoardService;
    @Mock
    private ScoreRepository scoreRepository;
    @Mock
    private BadgeRepository badgeRepository;

    @BeforeEach
    void setUp() {
        leaderBoardService = new LeaderBoardServiceImpl(scoreRepository, badgeRepository);
    }

    @Test
    void retrieveLeaderBoardTest() {
        //given
        LeaderBoardRow lbr = new LeaderBoardRow(1L, 100L, List.of());
        given(scoreRepository.createLBRSortByTotalScore()).willReturn(List.of(lbr));
        given(badgeRepository.findByUserIdOrderByBadgeTimestampDesc(1L)).willReturn(List.of(new BadgeCard(1L, BadgeType.FIRST_WON), new BadgeCard(1L, BadgeType.BRONZE)));

        //when
        List<LeaderBoardRow> leaderBoard = leaderBoardService.getCurrentLeaderBoard();

        //then
        List<LeaderBoardRow> expectedLeaderBoard = List.of(new LeaderBoardRow(1L, 100L, List.of(BadgeType.FIRST_WON.getDescription(), BadgeType.BRONZE.getDescription())));
        then(leaderBoard).isEqualTo(expectedLeaderBoard);
    }
}