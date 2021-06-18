package com.gsucode.lmsb.multiplication.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private UserRepository userRepository;

    @GetMapping("/{idList}")
    public List<User> getUserByIdList(@PathVariable final List<Long> idList){
        return userRepository.findAllByIdIn(idList);
    }
}
