package picle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import picle.auth.AuthorizationFilter;
import picle.controller.AuthController;
import picle.dao.MariaDbUserDaoImpl;
import picle.entity.User;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    private String password = "walkinthepark";
    private String passwordHash = "1000:"
            + "04f29fab88d4e0b4fe630492e916fef7:"
            + "2ab6ada00e77623d3f59ed8f14e0049d1"
            + "9f9b7c21a5b63fa1c066b2c2e3ce5ec12"
            + "5909888c5ab84d9e7723ecf0c7ca2457d"
            + "c0a82b50d7ab82fde51fad61ba6c3";
    private User user = new User(1, "Sungyboi", passwordHash);

    @Mock
    private MariaDbUserDaoImpl userDao;


    @InjectMocks
    private AuthorizationFilter authorizationFilter;


    private MockMvc mvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(userDao.getUser(anyString())).thenReturn(user);
        AuthController authController = mock(AuthController.class);
        this.mvc = MockMvcBuilders.standaloneSetup(authController).addFilter(authorizationFilter, "/auth/*").build();
    }

    @Test
    public void authCorrectTest() throws Exception {
        this.mvc.perform(get("/auth").header("Authorization", "Basic U3VuZ3lib2k6d2Fsa2ludGhlcGFyaw==")).
                andExpect(status().isOk());
    }

    @Test
    public void authIncorrectTest() throws Exception {
        this.mvc.perform(get("/auth").header("Authorization", "Basic U3VuZ3lib2k6cGFzc3dvcmQ=")).
                andExpect(status().isUnauthorized());

    }

}