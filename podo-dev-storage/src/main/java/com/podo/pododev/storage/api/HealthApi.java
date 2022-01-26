package com.podo.pododev.storage.api;

import com.podo.pododev.storage.application.FileCRUDService;
import com.podo.pododev.storage.dto.FileDelete;
import com.podo.pododev.storage.dto.FileWrite;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping
@RestController
public class HealthApi {

    @GetMapping("/")
    public String home() {
        return "OK";
    }

}
