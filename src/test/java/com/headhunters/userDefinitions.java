package com.headhunters;

import com.headhunters.model.User;
import com.headhunters.security.JwtTokenProvider;
import com.headhunters.service.impl.UserService;
import com.headhunters.validator.UserValidator;
import io.cucumber.core.backend.StepDefinition;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StepDefinitions {

    private RestTemplate restTemplate = new RestTemplate();
    private String BASE_URL = "http://localhost:8080/api";
    private String USER_API = "/users";
    Logger logger = LoggerFactory.getLogger(StepDefinitions.class);

    @Given("I can register")
    public void i_can_register() {
        String url = BASE_URL + USER_API + "/getAll";
        Iterable<User> allUsers = restTemplate.getForObject(url, Iterable.class);
        logger.info(String.valueOf(allUsers));
        Assert.assertTrue(allUsers.spliterator().getExactSizeIfKnown() != 0);
    }

    @Given("^I send a post to create an account with username (.*), password (.*) and confirmPassword (.*)")
    public void i_send_a_post(String username, String password, String confirmPassword) {
        String url = BASE_URL + USER_API + "/register";
        User newUser = new User();
        newUser.setUsername("admin@gmail.com");
        newUser.setPassword("testing321");
        newUser.setConfirmPassword("testing321");
        User userSaved = restTemplate.postForObject(url, newUser, User.class);
        logger.debug("SAVED USER: ", userSaved);
        Assert.assertNotNull(userSaved);
    }

    @Then("I should be able to login into the application")
    public void i_should_be_able_to_login_into_the_application() {
        String url = BASE_URL + USER_API + "/login";
        User existingUser = new User();
        existingUser.setUsername("admin@gmail.com");
        existingUser.setPassword("testing321");
        User loggedInUser = restTemplate.postForObject(url, existingUser, User.class);
        logger.info("Loging user!");
        Assert.assertNotNull(loggedInUser);
    }
}
