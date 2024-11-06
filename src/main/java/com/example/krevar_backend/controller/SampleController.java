package com.example.krevar_backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sample")
@CrossOrigin(origins = "https://staging.krevar.com")
public class SampleController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}
