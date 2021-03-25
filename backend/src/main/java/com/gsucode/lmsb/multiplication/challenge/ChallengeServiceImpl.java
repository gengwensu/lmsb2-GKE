package com.gsucode.lmsb.multiplication.challenge;

import com.gsucode.lmsb.multiplication.user.User;
import org.springframework.stereotype.Service;

@Service
public class ChallengeServiceImpl implements ChallengeService {
    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO resultAttempt) {
        return new ChallengeAttempt(
                null,
                new User(null, resultAttempt.getUserAlias()),
                resultAttempt.getFactorA(),
                resultAttempt.getFactorB(),
                resultAttempt.getGuess(),
                resultAttempt.getGuess() == resultAttempt.getFactorA() * resultAttempt.getFactorB()
        );
    }
}
