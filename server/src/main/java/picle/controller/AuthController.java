package picle.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles HTTP methods.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * GET method to check validity of credentials.
     * http://localhost:8080/auth
     */
    @RequestMapping (method = RequestMethod.GET)
    public void checkAuthentication() {
    }

}

