package gsucode.lmsb.multiplication.challenge;

import org.springframework.stereotype.Service;

public interface ChallengeGeneratorService {
    /**
     * @return a randomly-generated challenge with factors between 11 and 99
     */
    Challenge randomChallenge();
}
