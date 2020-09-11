package com.bcp.exchangerate.web;


import com.bcp.exchangerate.config.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("token")
@RequiredArgsConstructor
public class TokenController {


    private final TokenProvider tokenProvider;

    @PostMapping
    public Mono<String> token() {
        return Mono.just(tokenProvider.createToken("joh", LocalDateTime.now()));
    }



}
