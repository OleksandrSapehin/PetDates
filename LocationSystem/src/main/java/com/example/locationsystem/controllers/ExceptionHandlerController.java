package com.example.locationsystem.controllers;

import com.example.locationsystem.Exceptions.FriendAlreadyExistsException;
import com.example.locationsystem.Exceptions.PersonNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(FriendAlreadyExistsException.class)
    public ModelAndView handleFriendAlreadyExistsException(FriendAlreadyExistsException ex) {
        ModelAndView modelAndView = new ModelAndView("personInfo");
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }
    @ExceptionHandler(PersonNotFoundException.class)
    public ModelAndView handlePersonNotFoundException(PersonNotFoundException ex){
        ModelAndView modelAndView = new ModelAndView("personInfo");
        modelAndView.addObject("errorMessage",ex.getMessage());
        return modelAndView;
    }
}
