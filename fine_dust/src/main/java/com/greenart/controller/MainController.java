package com.greenart.controller;

import java.util.List;

import com.greenart.service.AirPollutionService;
import com.greenart.vo.AirPollutionHourVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired AirPollutionService service;

    @GetMapping("/")
    public String getMain(){
        return "air/pollution";
    }
}
