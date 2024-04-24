package com.bezkoder.springjwt.Vuser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class VUserController {

    private final VUserService service;

    @PostMapping
    public void register(
            @RequestBody VUser user
    ) {
        service.register(user);
    }

    @PostMapping("/login")
    public VUser login(@RequestBody VUser user) {
        return service.login(user);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody VUser email) {
        service.logout(email.getEmail());
    }

    @GetMapping
    public List<VUser> findAll() {
        return service.findAll();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }
}
