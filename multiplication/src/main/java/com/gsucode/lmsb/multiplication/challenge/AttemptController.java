package com.gsucode.lmsb.multiplication.challenge;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/attempts")
public class AttemptController {

    private final ChallengeService challengeService;

    @CrossOrigin
    @PostMapping
    ChallengeAttempt postResult(@RequestBody @Valid ChallengeAttemptDTO challengeAttemptDTO){
        log.info("postResult: " + challengeAttemptDTO.toString());
        return challengeService.verifyAttempt(challengeAttemptDTO);
    }

    @CrossOrigin
    @GetMapping
    List<ChallengeAttempt> getStats(@RequestParam String alias){
        return challengeService.getStatsForUser(alias);
    }
}
