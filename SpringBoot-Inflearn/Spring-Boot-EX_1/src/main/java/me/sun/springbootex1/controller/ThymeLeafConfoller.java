package me.sun.springbootex1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Dongmyeong Lee
 * @since 2020/02/26
 */
@Controller
public class ThymeLeafConfoller {

    @GetMapping("/thyme")
    public String hello(Model model) {
        model.addAttribute("name", "dexter");
        return "hello";
    }
}
