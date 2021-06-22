package com.gsucode.lmsb.multiplication.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @CrossOrigin
    @GetMapping("/{idList}")
    public List<User> getUserByIdList(@PathVariable final List<Long> idList){
        log.info("Multiplication, getUserByIdList with input: {}", idList);
        return userRepository.findAllByIdIn(idList);
    }
}
