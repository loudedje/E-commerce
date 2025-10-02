package com.moreno.ecommerceMoreno.controller;


import com.moreno.ecommerceMoreno.model.User;
import com.moreno.ecommerceMoreno.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class UserController {

        @Autowired
        private UserService userService;

        @PostMapping
        public ResponseEntity<User> createUser(@RequestBody User user){
            User created = userService.createUser(user);
            return ResponseEntity.ok(created);
        }



}


