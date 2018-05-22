package com.bogusnazar.intime.Controllers;

import com.bogusnazar.intime.DAO.UserRepository;
import com.bogusnazar.intime.Models.User;
import com.bogusnazar.intime.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/users/add",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        String email = userRepository.findByEmail(user.getEmail()).getEmail();
        if (email != null) {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        } else {
            User savedUser = userRepository.save(user);
            return new ResponseEntity<User>(savedUser, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/users/delete/{userId}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userID) {
        userRepository.deleteById(userID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/users/login",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<User> loginUser(@RequestBody User login) {
        logger.info("SentEmail:" + login.getEmail() + "SentPassword: " + login.getPassword());
        User response = userRepository.findByEmail(login.getEmail());
        logger.info("UserEmail: " + response.getEmail() + "UserPassword: " + response.getPassword());
        if (response != null) {
            if (login.getPassword().equals(response.getPassword())) {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                httpHeaders.add("X-AUTH", jwtUtil.generateToken(response.getEmail(), response.getRole()));
                return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

