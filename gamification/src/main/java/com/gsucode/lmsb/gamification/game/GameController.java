package com.gsucode.lmsb.gamification.game;

import com.gsucode.lmsb.gamification.challenge.ChallengeSolvedDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/attempts")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    void postResult(@RequestBody ChallengeSolvedDTO dto){
        log.info("Gamification, postResult dto: {}", dto);
        gameService.newAttemptForUser(dto);
    }
}
