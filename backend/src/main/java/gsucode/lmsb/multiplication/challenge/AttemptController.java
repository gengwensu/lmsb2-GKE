package gsucode.lmsb.multiplication.challenge;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/attempts")
public class AttemptController {

    private final ChallengeService challengeService;

    @PostMapping
    ChallengeAttempt postResult(@RequestBody @Valid ChallengeAttemptDTO challengeAttemptDTO){
        return challengeService.verifyAttempt(challengeAttemptDTO);
    }

}
