package br.com.linecode.imgprocess.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.testng.annotations.DataProvider;

import br.com.linecode.imgprocess.model.Dimenssion;
import br.com.linecode.imgprocess.model.Region;
import br.com.linecode.util.TestUtil;

public class ManipulateServiceDataProvider {
    
    private static final String NM_IMG_TEST = "java-logo.png";

    @DataProvider(name = "resizeErrorTestDataProvider")
    public static Object[][] resizeErrorTestDataProvider() throws IOException{

        MultipartFile img = getMultipartFileTest();
        Dimenssion undefined = null;
        Dimenssion invaidWidth = new Dimenssion(2, 100);
        Dimenssion invalidheight = new Dimenssion(100, 2);

        //@formatter:off
        return new Object[][] {
            {img, undefined, "Enter a new dimension for image."},
            {img, invaidWidth, "invalid width."},
            {img, invalidheight, "invalid height."},
        };
        //@formatter:on
    }

    @DataProvider(name = "cropErrorTestDataProvider")
    public static Object[][] cropErrorTestDataProvider() throws IOException{

        MultipartFile img = getMultipartFileTest();
        Region undefined = null;
        Region invalidX = new Region(-1, 20, 20, 20);
        Region invalidY = new Region(20, -1, 20, 20);
        Region invaidWidth = new Region(20, 20, 2, 20);
        Region invalidheight = new Region(20, 20, 20, 2);
 
        //@formatter:off
        return new Object[][] {
            {img, undefined, "Enter a region for crop the image."},
            {img, invalidX, "invalid x."},
            {img, invalidY, "invalid y."},
            {img, invaidWidth, "invalid width."},
            {img, invalidheight, "invalid height."},
        };
        //@formatter:on
    }

    private static MultipartFile getMultipartFileTest() throws IOException {
        return new TestUtil().getResourceMultiPartFie(NM_IMG_TEST);
    }
}