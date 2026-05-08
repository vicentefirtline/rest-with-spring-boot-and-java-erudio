package com.vicentefirtline.rest_with_spring_boot_and_java_erudio.controllers;

import com.vicentefirtline.rest_with_spring_boot_and_java_erudio.services.PersonServices;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class TestLogController {
     private org.slf4j.Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());
    @GetMapping("/test")
 public String testLog(){
        logger.debug("This is an DEBUG log, é o log DEBUG");
        logger.info("This is an INFO log, é o log INFO");
        logger.warn("This is an WARN log, é o log WARN");
        logger.error("This is an ERROR log, é o log ERROR");
     return "Logs generated sucessfully! log gerado com sucesso";
 }

}
