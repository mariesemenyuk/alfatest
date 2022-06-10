package com.example.alfatest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;

    @GetMapping("/rate/{currency}")
    public ResponseEntity<Object> getCurrentRate(@PathVariable("currency") String currency) {
        String url = rateService.getImageUrl(currency);

        URI pic = URI.create(url);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(pic);

        return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
    }
}
