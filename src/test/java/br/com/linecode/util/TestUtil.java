package br.com.linecode.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

public class TestUtil {

    public File getResourceFile(String path) {
        return new File(getResourcePath(path));
    }

    public MultipartFile getResourceMultiPartFie(String path) throws IOException {

        MockitoAnnotations.initMocks(this);

        Path resource = Paths.get(getResourcePath(path));
        File resourceFile = resource.toFile();
        String contentType = Files.probeContentType(resource);
        byte[] content = FileUtils.readFileToByteArray(resourceFile);
        MultipartFile multipartFile = mock(MultipartFile.class);

        when(multipartFile.getBytes()).thenReturn(content);
        when(multipartFile.getContentType()).thenReturn(contentType);
        when(multipartFile.getOriginalFilename()).thenReturn(resourceFile.getName());
        when(multipartFile.getSize()).thenReturn(Long.valueOf(content.length));

        return multipartFile;
    }

    private String getResourcePath(String path) {
        //@formatter:off
        return getClass().
            getClassLoader().
            getResource(path).
            getFile();
        //@formatter:on
    }
}