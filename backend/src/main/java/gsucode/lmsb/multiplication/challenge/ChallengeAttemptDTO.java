package gsucode.lmsb.multiplication.challenge;

import lombok.Value;

@Value
public class ChallengeAttemptDTO {
    int factorA, factorB;
    String userAlias;
    int guess;
}
