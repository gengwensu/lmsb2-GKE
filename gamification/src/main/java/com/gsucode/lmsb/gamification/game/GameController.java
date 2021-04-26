package com.gsucode.lmsb.gamification.game;

import com.gsucode.lmsb.gamification.challenge.ChallengeSolvedDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attempts")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    void postResult(@RequestBody ChallengeSolvedDTO dto){
        gameService.newAttemptForUser(dto);
    }
}
