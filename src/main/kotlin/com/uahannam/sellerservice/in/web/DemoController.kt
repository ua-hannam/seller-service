package com.uahannam.sellerservice.`in`.web

import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/seller")
class DemoController(private val env: Environment) {

    @GetMapping("/test")
     fun testSeller() : String {
         return String.format("port(local.server.port)="+ env.getProperty("local.server.port")
         +", port(server.port)=" + env.getProperty("server.port")
         +", token secret=" + env.getProperty("token.secret")
         +", token expiration time=" + env.getProperty("token.expiration_time"))
     }
}
