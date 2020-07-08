package com.podo.pododev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.ws.rs.ApplicationPath;

@ActiveProfiles("test")
@SpringBootTest
public class PodoDevWebApplicationTests {

    @Test
    public void contextLoads() {
    }

}
