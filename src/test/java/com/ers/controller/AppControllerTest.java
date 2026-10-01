package com.ers.controller;

import com.ers.model.User;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AppControllerTest {

    @Test
    void shouldNotCrashWhenEmployeeProfileMissing() {
        User user = new User(
                "demo-user",
                "demo-pass",
                "EMPLOYEE",
                true,
                LocalDateTime.now()
        );

        assertDoesNotThrow(() ->
                new AppController(
                        new Scanner(new StringReader("")),
                        user,
                        null
                )
        );
    }
}
