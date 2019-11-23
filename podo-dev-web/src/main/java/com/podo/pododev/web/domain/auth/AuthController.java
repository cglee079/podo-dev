package com.podo.pododev.web.domain.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class AuthController {

    @GetMapping("/login/google")
    public void redirectToGoogleOAuth(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth/google");
    }

    
}
