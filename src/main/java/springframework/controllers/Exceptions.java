package springframework.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import springframework.exception.NotFoundException;
import springframework.exception.NumberException;

@ControllerAdvice
public class Exceptions {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView notFoundException(Exception exception){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error");
        modelAndView.addObject("exception",exception);
        return modelAndView;
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberException.class)
    public ModelAndView numberException(Exception e){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception",e);
        return modelAndView;
    }

}
