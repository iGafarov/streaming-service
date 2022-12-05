package com.ssau.streamingservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VideoController {

    @GetMapping("/watch")
    public String watchVideo() {
        return "index.html";
    }
}
