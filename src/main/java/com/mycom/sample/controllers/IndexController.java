package com.mycom.sample.controllers;

//import com.jojoldu.book.springboot.config.auth.LoginUser;
//import com.jojoldu.book.springboot.config.auth.dto.SessionUser;

import com.mycom.sample.services.PostsService;
import com.mycom.sample.controllers.dtos.PostsResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {

    @Autowired
    private final PostsService postsService;

    @GetMapping("/")
    public String index( Model model ) {
        model.addAttribute("posts", postsService.findAllDesc());
//        if (user != null) {
//            model.addAttribute("userName", user.getName());
//        }
        return "index";
    }

//    @GetMapping("/")
//    public String index() {
//        return "index";
//    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate( Model model, @PathVariable Long id ) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
