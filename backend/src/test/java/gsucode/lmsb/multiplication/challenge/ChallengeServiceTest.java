package gsucode.lmsb.multiplication.challenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class ChallengeServiceTest {

    private ChallengeService challengeService;

    @BeforeEach
    void setUp() {
        challengeService = new ChallengeServiceImpl();
    }

    @Test
    public void checkCorrectAttemptTest() {
        //given
        ChallengeAttemptDTO challengeAttemptDTO =
                new ChallengeAttemptDTO(20, 30, "tester ok", 600);

        //when
        ChallengeAttempt resultAttempt =
                challengeService.verifyAttempt(challengeAttemptDTO);

        //then
        then(resultAttempt.isCorrect()).isTrue();
    }

    @Test
    public void checkWrongAttemptTest() {
        //given
        ChallengeAttemptDTO challengeAttemptDTO =
                new ChallengeAttemptDTO(50, 60, "tester notOk", 5000);

        //when
        ChallengeAttempt resultAttempt =
                challengeService.verifyAttempt(challengeAttemptDTO);

        //then
        then(resultAttempt.isCorrect()).isFalse();
    }
}