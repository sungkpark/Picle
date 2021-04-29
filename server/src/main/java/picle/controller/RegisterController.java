package picle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import picle.dao.UserDao;
import picle.entity.RegisterUser;

@RestController
@RequestMapping("/register")
public class RegisterController {

    /**
     * UserDao connects from MariaDbUserDaoImpl.
     */
    @Autowired
    private UserDao userDao;

    /**
     * A POST method that requests to register a User.
     * @param registerUser (username, password)
     * @return HttpStatus.CREATED if user gets registered,
     *     HttpStatus.BAD_REQUEST if username is taken.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> register(
            @RequestBody RegisterUser registerUser) {
        boolean flag = userDao.register(registerUser);
        if (!flag) {
            return new ResponseEntity<>(
                    "User already exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * A GET method that gets whether a username is taken or not.
     * @param username username
     * @return HttpStatus.OK if username is taken,
     *     HttpStatus.NOT_FOUND if username is not taken.
     */
    @RequestMapping(value = "/is_registered/{username}", method = RequestMethod.GET)
    public ResponseEntity<String> isRegisteredByUsername(
            @PathVariable String username) {
        boolean flag = userDao.userExists(username);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
