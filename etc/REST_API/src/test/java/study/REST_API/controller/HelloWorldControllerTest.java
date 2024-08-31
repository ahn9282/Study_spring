package study.REST_API.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import study.REST_API.domain.User;
import study.REST_API.exception.UserNotFoundException;
import study.REST_API.service.UserDaoService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.util.Predicates.isTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HelloWorldControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    UserDaoService userDaoService;

    private MockMvc mock;
    @BeforeEach
    public void startTest(){
        mock = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createUserTest() throws Exception {
        String userData = "{\"name\":\"desktop1\"}";
        MvcResult mvcResult = mock.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userData))
                .andExpect(status().isCreated()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        System.out.println(response.getContentAsString());
        System.out.println(response.getForwardedUrl());
        System.out.println(response.getStatus());

        List<User> all = userDaoService.findAll();
        User findUser = null;
        for (User user : all) {
            if(user.getName().equals("desktop1")){
                findUser = user;
            }
        }
        assertThat(findUser.getName()).isEqualTo("desktop1");
    }
    @Test
    void notFoundErrorTEst() throws Exception {
        MvcResult mvcResult = mock.perform(get("/users/6"))
                .andExpect(status().isNotFound())
                .andReturn();
        Exception exception = mvcResult.getResolvedException();
        assertThat(exception).isInstanceOf(UserNotFoundException.class);
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();

    }

}