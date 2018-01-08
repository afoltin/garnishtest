package org.garnishtest.demo.rest_simple.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/calc")
public final class CalculatorController {

    @RequestMapping(value = "/add/{a}/{b}", method = RequestMethod.GET)
    @ResponseBody
    public String addPlainText(@PathVariable("a") double a, @PathVariable("b") double b) {
        return (a + b) + "";
    }

    @RequestMapping(value = "/sub/{a}/{b}", method = RequestMethod.GET)
    @ResponseBody
    public String subPlainText(@PathVariable("a") double a, @PathVariable("b") double b) {
        return (a - b) + "";
    }


}