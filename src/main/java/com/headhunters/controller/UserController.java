package com.headhunters.controller;

import com.headhunters.model.User;
import com.headhunters.payload.JWTLoginSuccessResponse;
import com.headhunters.payload.LoginRequest;
import com.headhunters.security.JwtTokenProvider;
import com.headhunters.service.Interfaces.IUserService;
import com.headhunters.service.impl.MapValidationErrorService;
import com.headhunters.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import static com.headhunters.security.SecurityConstants.TOKEN_PREFIX;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;


   /* @DeleteMapping("/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long user_id) {
        userService.delete(user_id);

        return new ResponseEntity<String>("User deleted", HttpStatus.OK);
    }*/

    @RequestMapping("add_album")
    public ResponseEntity<?> addAlbum(@RequestBody HashMap<String, String> mapper) {
        Long album_id = Long.parseLong(mapper.get("album_id"));
        Long user_id = Long.parseLong(mapper.get("user_id"));

        userService.addAlbum(album_id, user_id);

        return new ResponseEntity<String>("Album added to user", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null)
            return errorMap;

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        userValidator.validate(user, result);

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        User newUser = userService.save(user);

        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

}
