package com.app.quantitymeasurement.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// Forwards all non-API, non-static routes to index.html
// so Angular's client-side router works when frontend is served from Spring Boot
@Controller
public class WebConfig {

    @RequestMapping(value = {"/{path:[^\\.]*}"})
    public String redirect() {
        return "forward:/index.html";
    }
}