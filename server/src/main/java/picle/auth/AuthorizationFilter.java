package picle.auth;

import org.springframework.core.annotation.Order;
import picle.dao.MariaDbUserDaoImpl;
import picle.dao.UserDao;
import picle.entity.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

// order tag is for filter fire order
@Order(1)
//@Component
public class AuthorizationFilter implements Filter {
    // All following methods are abstract methods in Filter and therefore have to be overriden
    // @Autowired fails with bean definition it seems
    // TODO rewire this to MariaDBDao when implemented
    private UserDao userDao = new MariaDbUserDaoImpl();


    /**
     * Definition imperative as part of implementing interface, called to notify
     * the instance of this class that it is being initialized.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private String decodeAuth(HttpServletRequestWrapper userRequest) {
        String decodedAuth;
        boolean badRequest = false;
        try {
            String encoded = userRequest.getHeader("Authorization");
            if (encoded != null) {
                int divAt1 = encoded.indexOf(" ");
                if (divAt1 == -1) {
                    badRequest = true;
                    decodedAuth = "";
                } else {
                    encoded = encoded.substring(divAt1 + 1);
                    byte[] decodedAuthBytes = Base64.getDecoder().decode(encoded);
                    decodedAuth = new String(decodedAuthBytes);
                }
            } else {
                decodedAuth = "";
            }
        } catch (IllegalArgumentException e) {
            // means false decoding
            decodedAuth = "";
        }

        return decodedAuth;
    }

    /**
     * Wraps incoming request and processes the Authorization header,
     * comparing information found to that in the DB. Discontinues or continues
     * the FilterChain as suitable.
     * @param request Request passed by FilterChain
     * @param response Response passed by FilterChain
     * @param chain Current FilterChain calling this method
     * @throws IOException can be thrown by DAO if trying to receive a non-existent User
     * @throws ServletException if the FilterChain has trouble calling the next filter
     */
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequestWrapper userRequest = new HttpServletRequestWrapper(
                (HttpServletRequest) request);
        // Get encoded string from Authorization header
        String decodedAuth;
        boolean badRequest = false;

        decodedAuth = this.decodeAuth(userRequest);
        if (decodedAuth.equals("")) {
            badRequest = true;
        }

        // Expected decoded format "username:password", if not will get
        // exception from DAO or String class
        int divAt2 = decodedAuth.indexOf(":");

        // clause may not be necessary in the future?
        if (divAt2 != -1) {
            String decodedUsername = decodedAuth.substring(0, divAt2);
            String decodedPassword = decodedAuth.substring(divAt2 + 1);

            // Remember to treat situations where user cannot be found for real DAO
            User check;
            boolean validPass = false;
            try {
                check = userDao.getUser(decodedUsername);
                validPass = PasswordHash.validatePassword(check.getPassword(), decodedPassword);
            } catch (NullPointerException e) {
                check = null;
            } catch (NoSuchAlgorithmException e) {
                // This is a fatal error, after showing exception information w
                System.out.println("FATAL ERROR: UNSUPPORTED ALGORITHM");
                System.out.println(e.toString());
                check = null;
            } catch (InvalidKeySpecException e) {
                System.out.println(e.toString());
                check = null;
            }

            // only smooth condition to continue into other filters and finally controller
            if (check != null && validPass) {
                request.setAttribute("X-Picle-Username", decodedUsername);
                chain.doFilter(request, response);
            } else {
                // means password was wrong
                final HttpServletResponse unauthorizedResponse = (HttpServletResponse) response;
                unauthorizedResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else if (badRequest) {
            final HttpServletResponse unauthorizedResponse = (HttpServletResponse) response;
            unauthorizedResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            // Means authorization is encoded incorrectly, think of what to do in this case
            final HttpServletResponse unauthorizedResponse = (HttpServletResponse) response;
            unauthorizedResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }


    }

    /**
     * Definition imperative as part of implementing interface, called
     * to notify the instance of this class that it is being destroyed.
     */
    @Override
    public void destroy(){

    }

}
