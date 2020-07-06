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

    private static final String CONTENT_TYPE_PNG = "image/png";
    private static final String NM_IMG_TESTE_PNG = "test.png";

    private static final String CONTENT_TYPE_MP4 = "video/mp4";
    private static final String NM_FILE_TESTE_MP4 = "test.mp4";

    public File getResourceFile(String path) {
        return new File(getResourcePath(path));
    }

    public MultipartFile getResourceMultiPartFile(String path) throws IOException {

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

    public MultipartFile getEmptyMultiPartFile() throws IOException {

        MockitoAnnotations.initMocks(this);
        MultipartFile multipartFile = mock(MultipartFile.class);

        when(multipartFile.getBytes()).thenReturn(new byte[] {});
        when(multipartFile.getContentType()).thenReturn(CONTENT_TYPE_PNG);
        when(multipartFile.getOriginalFilename()).thenReturn(NM_IMG_TESTE_PNG);
        when(multipartFile.getSize()).thenReturn(Long.valueOf(0));

        return multipartFile;
    }

    public MultipartFile getMP4MultiPartFile() throws IOException {

        MockitoAnnotations.initMocks(this);
        MultipartFile multipartFile = mock(MultipartFile.class);

        when(multipartFile.getBytes()).thenReturn(new byte[] {1,2});
        when(multipartFile.getContentType()).thenReturn(CONTENT_TYPE_MP4);
        when(multipartFile.getOriginalFilename()).thenReturn(NM_FILE_TESTE_MP4);
        when(multipartFile.getSize()).thenReturn(Long.valueOf(2));

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