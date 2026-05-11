package com.example.SpringBasic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping("/hello")
    public String result(){
        return "hello.html";
    }

    @GetMapping("/")
    public String landingPage(){
        return "Welcome.html";
    }

    @Autowired
    private InfoModel infoModel;

    @PostMapping("/submit")
    public String res(@RequestParam("name") String name, Model model){
        infoModel.save(name);
        model.addAttribute("info", name);
        return "display";
    }
}
