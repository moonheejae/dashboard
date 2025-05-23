package com.cknb.htPlatform.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class ApiController {
    @Value("${application.urls.admin}")
    private String ALLOWED_REFERER;

    @GetMapping("/isValid")
    public String validateToken(
            @RequestParam("userNo") String userNo, HttpSession session
            ) {
        if(userNo!=null){
            session.setAttribute("userNo", userNo); // 세션에 userNo 저장
            return "redirect:home/total";
        }else {
            return "redirect:" + ALLOWED_REFERER;
        }
    }

    @GetMapping("/")
    public String mainPage(
            Model model
    ) {
        model.addAttribute("title", "total");
        return "home/total"; // 메인 페이지
    }

    @GetMapping("/download/{subMenuName}")
    public String downloadPage(
            @PathVariable("subMenuName") String subMenuName,
            Model model
    ) {
        model.addAttribute("title", subMenuName);
        return "download/" + subMenuName;  // 메인 컨텐츠 변경
    }

    @GetMapping("/exe/{subMenuName}")
    public String exePage(
            @PathVariable("subMenuName") String subMenuName,
            Model model
    ) {
        model.addAttribute("title", subMenuName);
        return "exe/" + subMenuName;  // 메인 컨텐츠 변경
    }

    @GetMapping("/scan/{subMenuName}")
    public String scanPage(
            @PathVariable("subMenuName") String subMenuName,
            Model model
    ) {
        model.addAttribute("title", subMenuName);
        return "scan/" + subMenuName;  // 메인 컨텐츠 변경
    }

    @GetMapping("/total/{subMenuName}")
    public String totalPage(
            @PathVariable("subMenuName") String subMenuName,
            Model model
    ) {
        model.addAttribute("title", subMenuName);
        return "total/" + subMenuName;  // 메인 컨텐츠 변경
    }

    @GetMapping("/notice/{subMenuName}")
    public String noticePage(
            @PathVariable("subMenuName") String subMenuName,
            Model model
    ) {
        model.addAttribute("title", subMenuName);
        return "notice/" + subMenuName;  // 메인 컨텐츠 변경
    }

    @GetMapping("/user/{subMenuName}")
    public String userPage(
            @PathVariable("subMenuName") String subMenuName,
            Model model
    ) {
        model.addAttribute("title", subMenuName);
        return "user/" + subMenuName;  // 메인 컨텐츠 변경
    }

    @GetMapping("/marketing/{subMenuName}")
    public String marketingPage(
            @PathVariable("subMenuName") String subMenuName,
            Model model
    ) {
        model.addAttribute("title", subMenuName);
        return "marketing/" + subMenuName;  // 메인 컨텐츠 변경
    }
}