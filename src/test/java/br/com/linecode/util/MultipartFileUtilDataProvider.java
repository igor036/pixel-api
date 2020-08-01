package br.com.linecode.util;

import java.io.IOException;

import org.testng.annotations.DataProvider;

import br.com.linecode.imgprocess.util.MultipartFileUtil;

public abstract class MultipartFileUtilDataProvider {

    private static TestUtil UTIL = new TestUtil();

    @DataProvider(name = "assertFileErrorTestDataProvider")
    public static Object[][] assertFileErrorTestDataProvider() throws IOException {
        //@formatter:off
        return new Object[][] {
            {null, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {UTIL.getMP4MultiPartFile(), MultipartFileUtil.INVALID_FILE_MESSAGE},
            {UTIL.getEmptyMultiPartFile(), MultipartFileUtil.EMPTY_FILE_MESSAGE}
        };
        //@formatter:on
    }

    @DataProvider(name = "getExtensionTestDataProvider")
    public static Object[][] getExtensionTestDataProvider() throws IOException {
        //@formatter:off
        return new Object[][] {
            {UTIL.getMP4MultiPartFile(), "mp4"},
            {UTIL.getPNGMultiPartFile(), "png"},
        };
        //@formatter:on
    }
     
}