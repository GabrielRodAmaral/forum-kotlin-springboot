package com.amaral.gabriel.forum.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hello")
class Hello {

    @GetMapping
    fun hello(): String {
        return "Hello spring"
    }
}