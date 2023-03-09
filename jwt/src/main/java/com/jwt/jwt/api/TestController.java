package com.jwt.jwt.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin ()
    {
        return "admin page";
    }

    @GetMapping("/operator")
    @PreAuthorize("hasRole('OPERATOR')")
    public String operator ()
    {
        return "operator page";
    }
    @GetMapping("/teamleader")
    @PreAuthorize("hasRole('TEAMLEADER')")
    public String teamleader ()
    {
        return "teamleader page";
    }






}
