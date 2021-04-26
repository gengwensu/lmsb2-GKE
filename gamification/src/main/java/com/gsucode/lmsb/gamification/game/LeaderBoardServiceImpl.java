package com.gsucode.lmsb.gamification.game;

import com.gsucode.lmsb.gamification.game.domain.LeaderBoardRow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LeaderBoardServiceImpl implements LeaderBoardService{
    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;

    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() {
        List<LeaderBoardRow> scoreOnly = scoreRepository.createLBRSortByTotalScore();
        return scoreOnly.stream()
                .map(lbr->{
                    List<String> badges = badgeRepository.findByUserIdOrderByBadgeTimestampDesc(
                            lbr.getUserId()
                    ).stream()
                            .map(b->b.getBadgeType().getDescription()).collect(Collectors.toList());
                    return  lbr.withBadges(badges);
                }).collect(Collectors.toList());
    }
}
