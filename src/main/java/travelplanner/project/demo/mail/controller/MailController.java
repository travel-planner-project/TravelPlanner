package travelplanner.project.demo.mail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.mail.dto.MailDto;

@RestController
@RequestMapping("/forgot-password")
@RequiredArgsConstructor
public class MailController {

    private final TokenUtil tokenUtil;

    @PostMapping
    public String getChangePasswordApi(@RequestBody MailDto mailDto) {



        return null;
    }

    @GetMapping
    public String getUriMailToken() {
        return null;
    }

}
