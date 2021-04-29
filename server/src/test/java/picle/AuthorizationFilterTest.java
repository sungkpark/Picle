package picle;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.BDDMockito.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import picle.controller.ActivityController;
import picle.dao.MariaDbUserDaoImpl;

import picle.entity.User;
import picle.auth.AuthorizationFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationFilterTest{
    private User user;
    private String hashedGood;
    private String hashedBad;

    private String password = "walkinthepark";
    private String passwordHash = "1000:"
            + "04f29fab88d4e0b4fe630492e916fef7:"
            + "2ab6ada00e77623d3f59ed8f14e0049d1"
            + "9f9b7c21a5b63fa1c066b2c2e3ce5ec12"
            + "5909888c5ab84d9e7723ecf0c7ca2457d"
            + "c0a82b50d7ab82fde51fad61ba6c3";

    @Mock
    private MariaDbUserDaoImpl userDao;


    @InjectMocks
    private AuthorizationFilter authorizationFilter;


    private MockMvc mvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        user = new User(1, "Sungyboi", passwordHash);
        when(userDao.getUser(anyString())).thenReturn(user);
        ActivityController activityController = mock(ActivityController.class);
        this.mvc = MockMvcBuilders.standaloneSetup(activityController).addFilter(authorizationFilter, "/activity/*").build();
    }


    @Test
    public void FilterCorrectTest() throws Exception {
        // as of now this test depends on the Fake class
        mvc.perform(get("/activity/1").
                header("Authorization", "Basic U3VuZ3lib2k6d2Fsa2ludGhlcGFyaw==")).
                andExpect(status().isOk());

    }

    @Test
    public void FilterIncorrectPassWordTest() throws Exception {
        mvc.perform(get("/activity").
                header("Authorization", "Basic U3VuZ3lib2k6cGFzc3dvcmQ=")).
                andExpect(content().string("")).
                andExpect(status().isUnauthorized());
    }

    @Test
    public void FilterBadHashTest() throws Exception {
        mvc.perform(get("/activity").
                header("Authorization", "Basic U3VuZ3lib2k6cGFzc=")).
                andExpect(content().string("")).
                andExpect(status().isBadRequest());
    }

    @Test
    public void FilterNoColonTest() throws Exception {
        mvc.perform(get("/activity").
                header("Authorization", "Basic U3VuZ3lib2l3YWxraW50aGVwYXJr")).
                andExpect(content().string("")).
                andExpect(status().isUnauthorized());
    }

    @Test
    public void FilterIncorrectHeaderFormatTest() throws Exception {
        mvc.perform(get("/activity").
                header("Authorization", "U3VuZ3lib2k6d2Fsa2ludGhlcGFyaw==")).
                andExpect(content().string("")).
                andExpect(status().isBadRequest());
    }

    @Test
    public void FilterEmptyHeaderTest() throws Exception {
        mvc.perform(get("/activity").
                header("Authorization", "")).
                andExpect(content().string("")).
                andExpect(status().isBadRequest());
    }

    @Test
    public void FilterUserNotReturnedTest() throws Exception {
        when(userDao.getUser(anyString())).thenThrow(new NullPointerException());
        mvc.perform(get("/activity").
                header("Authorization", "Basic U3VuZ3lib2k6d2Fsa2ludGhlcGFyaw==")).
                andExpect(content().string("")).
                andExpect(status().isUnauthorized());
    }

    @Test
    public void FilterNoAuthHeaderTest() throws Exception {
        mvc.perform(get("/activity")).
                andExpect(content().string("")).
                andExpect(status().isBadRequest());
    }
}
