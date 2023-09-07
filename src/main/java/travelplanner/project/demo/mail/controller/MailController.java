package travelplanner.project.demo.mail.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import travelplanner.project.demo.mail.dto.MailDto;

@RestController
public class MailController {

    @PostMapping("/forgot-password")
    public String forgotPW(@RequestBody MailDto mailDto) {



        return null;
    }
}
