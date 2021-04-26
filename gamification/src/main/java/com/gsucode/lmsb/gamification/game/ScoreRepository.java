package com.gsucode.lmsb.gamification.game;

import com.gsucode.lmsb.gamification.game.domain.LeaderBoardRow;
import com.gsucode.lmsb.gamification.game.domain.ScoreCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends CrudRepository<ScoreCard, Long> {
    /**
     * Gets the total score for a given user: the sum of the scores of all
     * their ScoreCards.
     *
     * @param userId the id of the user
     * @return the total score for the user, empty if the user doesn't exist
     */
    @Query("SELECT SUM(s.score) FROM ScoreCard s WHERE s.userId = :userId GROUP BY s.userId")
    Optional<Integer> getTotalScoreForUser(@Param("userId") Long userId);

    List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);

    @Query("select new com.gsucode.lmsb.gamification.game.domain.LeaderBoardRow(s.userId, sum(s.score)) from ScoreCard s group by s.userId order by sum(s.score) desc")
    List<LeaderBoardRow> createLBRSortByTotalScore();
}
