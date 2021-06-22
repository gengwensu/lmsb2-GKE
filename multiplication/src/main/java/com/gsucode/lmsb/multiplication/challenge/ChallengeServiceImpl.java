package com.gsucode.lmsb.multiplication.challenge;

import com.gsucode.lmsb.multiplication.serviceclients.GamificationServiceClient;
import com.gsucode.lmsb.multiplication.user.User;
import com.gsucode.lmsb.multiplication.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChallengeServiceImpl implements ChallengeService {

    private final UserRepository userRepository;
    private final ChallengeAttemptRepository attemptRepository;
    private final GamificationServiceClient gameClient;

    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attemptDTO) {
        User user = userRepository.findByAlias(attemptDTO.getUserAlias())
                .orElseGet(() -> {
                    log.info("Creating new user with alias {}" + attemptDTO.getUserAlias());
                    return userRepository.save(new User(attemptDTO.getUserAlias()));
                });
        boolean isCorrect = attemptDTO.getGuess() == attemptDTO.getFactorA() * attemptDTO.getFactorB();
        ChallengeAttempt checkedAttempt = new ChallengeAttempt(null,
                user,
                attemptDTO.getFactorA(),
                attemptDTO.getFactorB(),
                attemptDTO.getGuess(),
                isCorrect);
        ChallengeAttempt storedAttempt = attemptRepository.save(checkedAttempt);

        // Sends the attempt to gamification and prints the response
        boolean status = gameClient.sendAttempt(storedAttempt);
        log.info("Gamification service response: {}", status);
        return storedAttempt;
    }

    @Override
    public List<ChallengeAttempt> getStatsForUser(final String userAlias){
        return attemptRepository.findTop10ByUserAliasOrderByIdDesc(userAlias);
    }
}
