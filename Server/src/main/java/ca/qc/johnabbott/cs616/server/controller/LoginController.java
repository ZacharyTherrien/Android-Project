package ca.qc.johnabbott.cs616.server.controller;

import ca.qc.johnabbott.cs616.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User login controller.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity login(@RequestParam String username, @RequestParam String password) {

        User user = userRepository.findUserByUsername(username);

        if(user != null && user.getPassword().equals(password)) {
            String pathOnServer = "/user/" + user.getUuid();
            return ResponseEntity.ok()
                    .header("Path", pathOnServer)
                    .body("Successful login.");
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Login error: either username doesn't exist of password is incorrect.");

    }
}


