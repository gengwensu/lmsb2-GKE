package com.gsucode.lmsb.multiplication.challenge;

import com.gsucode.lmsb.multiplication.user.User;
import com.gsucode.lmsb.multiplication.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChallengeServiceTest {

    private ChallengeService challengeService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ChallengeAttemptRepository attemptRepository;

    @BeforeEach
    void setUp() {
        challengeService = new ChallengeServiceImpl(userRepository, attemptRepository);
        given(attemptRepository.save(any())).will(returnsFirstArg());
    }

    @Test
    public void checkCorrectAttemptTest() {
        //given
        ChallengeAttemptDTO challengeAttemptDTO =
                new ChallengeAttemptDTO(50, 60, "john_doe", 3000);

        //when
        ChallengeAttempt resultAttempt =
                challengeService.verifyAttempt(challengeAttemptDTO);

        //then
        then(resultAttempt.isCorrect()).isTrue();
        verify(userRepository).save(new User("john_doe"));
        verify(attemptRepository).save(resultAttempt);
    }

    @Test
    public void checkWrongAttemptTest() {
        //given
        ChallengeAttemptDTO challengeAttemptDTO =
                new ChallengeAttemptDTO(50, 60, "john_doe", 5000);

        //when
        ChallengeAttempt resultAttempt =
                challengeService.verifyAttempt(challengeAttemptDTO);

        //then
        then(resultAttempt.isCorrect()).isFalse();
        verify(userRepository).save(new User("john_doe"));
        verify(attemptRepository).save(resultAttempt);
    }

    @Test
    void checkExistingUserTest() {
        //given
        User existingUser = new User(1L, "john_doe");
        given(userRepository.findByAlias("john_doe")).willReturn(Optional.of(existingUser));
        ChallengeAttemptDTO challengeAttemptDTO =
                new ChallengeAttemptDTO(50, 60, "john_doe", 5000);

        //when
        ChallengeAttempt resultAttempt =
                challengeService.verifyAttempt(challengeAttemptDTO);

        //then
        then(resultAttempt.isCorrect()).isFalse();
        then(resultAttempt.getUser()).isEqualTo(existingUser);
        verify(userRepository, never()).save(new User("John_doe"));
        verify(attemptRepository).save(resultAttempt);
    }
}