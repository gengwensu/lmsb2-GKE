package com.gsucode.lmsb.gamification.game.badgeprocessors;

import com.gsucode.lmsb.gamification.challenge.ChallengeSolvedDTO;
import com.gsucode.lmsb.gamification.game.domain.BadgeType;
import com.gsucode.lmsb.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BronzeBadgeProcessor implements BadgeProcessor {
    @Override
    public Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCardList, ChallengeSolvedDTO solved) {
        return currentScore > 50 ? Optional.of(BadgeType.BRONZE) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.BRONZE;
    }
}