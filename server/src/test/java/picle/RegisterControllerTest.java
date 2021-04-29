package picle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.mockito.Matchers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import picle.controller.RegisterController;
import picle.dao.UserDao;
import picle.entity.RegisterUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private RegisterController registerController;

    @Autowired
    private ObjectMapper mapper;

    private RegisterUser user;

    private MockMvc mvc;

    @Before
    public void setUp() {
        user = new RegisterUser("Alp", "pw");

        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(registerController).build();
    }

    @Test
    public void notRegisteredYetTest() throws Exception {
        String json = mapper.writeValueAsString(user);

        Mockito.when(userDao.register(Matchers.anyObject())).thenReturn(true);
        this.mvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void alreadyRegisteredTest() throws Exception {
        String json = mapper.writeValueAsString(user);

        Mockito.when(userDao.register(Matchers.anyObject())).thenReturn(false);
        this.mvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).andExpect(status().is4xxClientError());
    }

    @Test
    public void isRegisteredByExistingUsernameTest() throws Exception {
        Mockito.when(userDao.userExists(Matchers.anyString())).thenReturn(true);
        this.mvc.perform(get("/register/is_registered/Apl")).andExpect(status().isOk());
    }

    @Test
    public void isRegisteredByNonExistingUsernameTest() throws Exception {
        Mockito.when(userDao.userExists(Matchers.anyString())).thenReturn(false);
        this.mvc.perform(get("/register/is_registered/Shefique")).andExpect(status().is4xxClientError());
    }
}
