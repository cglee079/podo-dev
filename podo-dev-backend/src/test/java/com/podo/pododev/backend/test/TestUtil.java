package com.podo.pododev.backend.test;

import com.podo.pododev.backend.domain.user.model.User;
import com.podo.pododev.backend.global.security.oauth.OAuthAuthentication;
import com.podo.pododev.backend.global.security.oauth.OAuthUserDetails;
import org.apache.commons.io.FileUtils;
import org.mockito.Mockito;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;

public class TestUtil {

    public static String getStringFromResource(String requirePath, String... otherPaths) {
        String[] requirePathInResources = {"src", "test", "resources", requirePath};

        final String[] filePath = Stream.concat(Arrays.stream(requirePathInResources), Arrays.stream(otherPaths))
                .toArray(String[]::new);

        final File file = FileUtils.getFile(filePath);

        return readFileToString(file);
    }

    private static String readFileToString(File file) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public static void setAuth(User user) {
        final OAuthUserDetails mockAuth = Mockito.mock(OAuthUserDetails.class);
        given(mockAuth.getUserId()).willReturn(user.getId());
        SecurityContextHolder.getContext().setAuthentication(new OAuthAuthentication(mockAuth));
    }
}

