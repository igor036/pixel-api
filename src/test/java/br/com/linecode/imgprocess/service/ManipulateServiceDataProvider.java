package br.com.linecode.imgprocess.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.testng.annotations.DataProvider;

import br.com.linecode.imgprocess.model.Dimenssion;
import br.com.linecode.imgprocess.model.Region;
import br.com.linecode.imgprocess.util.MultipartFileUtil;
import br.com.linecode.util.TestUtil;

public abstract class ManipulateServiceDataProvider {
    
    private static final TestUtil TEST_UTIL = new TestUtil();
    
    private static final String INVALID_BRIGHTNESS_ALPHA_MSG = "The brightness alpha value should be  >= 0.1 and <= 3.0";
    private static final String INVALID_SATURARION_ALPHA_MSG = "The saturation alpha value should be  >= 0.1 and <= 3.0";
    private static final String INVALID_CONTRAST_BRIGHTNESS_ALPHA_MSG = "The brightnewss and contrast alpha value should be  >= 0.1 and <= 3.0";
    private static final String INVALID_CONTRAST_BRIGHTNESS_BETA_MSG = "The brightnewss and contrast beta value should be  >= 1.0 and <= 100.0";
    
    private static final String INVALID_X_REGION_MSG = "invalid x.";
    private static final String INVALID_Y_REGION_MSG = "invalid y.";
    private static final String INVALID_WIDTH_REGION = "invalid width.";
    private static final String INVALID_HEIGHT_REGION = "invalid height.";
    private static final String ENTER_REGION_IMAGE = "Enter a region of image.";

    @DataProvider(name = "resizeErrorTestDataProvider")
    public static Object[][] resizeErrorTestDataProvider() throws IOException{

        MultipartFile img = TEST_UTIL.getPNGMultiPartFile();
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

        MultipartFile img = TEST_UTIL.getPNGMultiPartFile();
        Region undefined = null;
        Region invalidX = new Region(-1, 20, 20, 20);
        Region invalidY = new Region(20, -1, 20, 20);
        Region invaidWidth = new Region(20, 20, 2, 20);
        Region invalidheight = new Region(20, 20, 20, 2);
 
        //@formatter:off
        return new Object[][] {
            {img, undefined, ENTER_REGION_IMAGE},
            {img, invalidX, INVALID_X_REGION_MSG},
            {img, invalidY, INVALID_Y_REGION_MSG},
            {img, invaidWidth, INVALID_WIDTH_REGION},
            {img, invalidheight, INVALID_HEIGHT_REGION},
        };
        //@formatter:on
    }

    @DataProvider(name = "brightnessErrorTestDataProvider")
    public static Object[][] brightnessErrorTestDataProvider() throws IOException {

        MultipartFile undefinedFile = null;
        MultipartFile emptyFile = TEST_UTIL.getEmptyMultiPartFile();
        MultipartFile mp4File = TEST_UTIL.getMP4MultiPartFile();
        MultipartFile validFile = TEST_UTIL.getPNGMultiPartFile();

        double invalidMinAlpha = 0;
        double invalidMaxAlpha = 5;
        double validAlpha = 0.1;

        //@formatter:off
        return new Object[][] {
            {undefinedFile, validAlpha, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {emptyFile, validAlpha, MultipartFileUtil.EMPTY_FILE_MESSAGE},
            {mp4File, validAlpha, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {validFile, invalidMaxAlpha, INVALID_BRIGHTNESS_ALPHA_MSG},
            {validFile, invalidMinAlpha, INVALID_BRIGHTNESS_ALPHA_MSG}
        };
        //@formatter:on
    }

    @DataProvider(name = "brightnessRegionErrorTestDataProvider")
    public static Object[][] brightnessRegionErrorTestDataProvider() throws IOException {

        MultipartFile undefinedFile = null;
        MultipartFile emptyFile = TEST_UTIL.getEmptyMultiPartFile();
        MultipartFile mp4File = TEST_UTIL.getMP4MultiPartFile();
        MultipartFile validFile = TEST_UTIL.getPNGMultiPartFile();

        double invalidMinAlpha = 0;
        double invalidMaxAlpha = 5;
        double validAlpha = 0.1;

        Region undefinedrRegion = null;
        Region invalidX = new Region(-1, 20, 20, 20);
        Region invalidY = new Region(20, -1, 20, 20);
        Region invaidWidth = new Region(20, 20, 2, 20);
        Region invalidheight = new Region(20, 20, 20, 2);
        Region validRegion = new Region(20, 20, 20, 20);
 
        //@formatter:off
        return new Object[][] {
            {undefinedFile, validAlpha, validRegion, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {emptyFile, validAlpha, validRegion, MultipartFileUtil.EMPTY_FILE_MESSAGE},
            {mp4File, validAlpha, validRegion, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {validFile, invalidMaxAlpha, validRegion, INVALID_BRIGHTNESS_ALPHA_MSG},
            {validFile, invalidMinAlpha, validRegion, INVALID_BRIGHTNESS_ALPHA_MSG},
            {validFile, validAlpha, undefinedrRegion, ENTER_REGION_IMAGE},
            {validFile, validAlpha, invalidX, INVALID_X_REGION_MSG},
            {validFile, validAlpha, invalidY, INVALID_Y_REGION_MSG},
            {validFile, validAlpha, invaidWidth, INVALID_WIDTH_REGION},
            {validFile, validAlpha, invalidheight, INVALID_HEIGHT_REGION},
        };
        //@formatter:on
    }

    @DataProvider(name = "saturationErrorTestDataProvider")
    public static Object[][] saturationErrorTestDataProvider() throws IOException {

        MultipartFile undefinedFile = null;
        MultipartFile emptyFile = TEST_UTIL.getEmptyMultiPartFile();
        MultipartFile mp4File = TEST_UTIL.getMP4MultiPartFile();
        MultipartFile validFile = TEST_UTIL.getPNGMultiPartFile();

        double invalidMinAlpha = 0;
        double invalidMaxAlpha = 5;
        double validAlpha = 0.1;

        //@formatter:off
        return new Object[][] {
            {undefinedFile, validAlpha, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {emptyFile, validAlpha, MultipartFileUtil.EMPTY_FILE_MESSAGE},
            {mp4File, validAlpha, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {validFile, invalidMaxAlpha, INVALID_SATURARION_ALPHA_MSG},
            {validFile, invalidMinAlpha, INVALID_SATURARION_ALPHA_MSG}
        };
        //@formatter:on
    }

    @DataProvider(name = "saturationRegionErrorTestDataProvider")
    public static Object[][] saturationRegionErrorTestDataProvider() throws IOException {

        MultipartFile undefinedFile = null;
        MultipartFile emptyFile = TEST_UTIL.getEmptyMultiPartFile();
        MultipartFile mp4File = TEST_UTIL.getMP4MultiPartFile();
        MultipartFile validFile = TEST_UTIL.getPNGMultiPartFile();

        double invalidMinAlpha = 0;
        double invalidMaxAlpha = 5;
        double validAlpha = 0.1;

        Region undefinedrRegion = null;
        Region invalidX = new Region(-1, 20, 20, 20);
        Region invalidY = new Region(20, -1, 20, 20);
        Region invaidWidth = new Region(20, 20, 2, 20);
        Region invalidheight = new Region(20, 20, 20, 2);
        Region validRegion = new Region(20, 20, 20, 20);
 
        //@formatter:off
        return new Object[][] {
            {undefinedFile, validAlpha, validRegion, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {emptyFile, validAlpha, validRegion, MultipartFileUtil.EMPTY_FILE_MESSAGE},
            {mp4File, validAlpha, validRegion, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {validFile, invalidMaxAlpha, validRegion, INVALID_SATURARION_ALPHA_MSG},
            {validFile, invalidMinAlpha, validRegion, INVALID_SATURARION_ALPHA_MSG},
            {validFile, validAlpha, undefinedrRegion, ENTER_REGION_IMAGE},
            {validFile, validAlpha, invalidX, INVALID_X_REGION_MSG},
            {validFile, validAlpha, invalidY, INVALID_Y_REGION_MSG},
            {validFile, validAlpha, invaidWidth, INVALID_WIDTH_REGION},
            {validFile, validAlpha, invalidheight, INVALID_HEIGHT_REGION},
        };
        //@formatter:on
    }

    @DataProvider(name = "contrastAndBrightnessRegionErrorTestDataProvider")
    public static Object[][] contrastAndBrightnessRegionErrorTestDataProvider() throws IOException {

        MultipartFile undefinedFile = null;
        MultipartFile emptyFile = TEST_UTIL.getEmptyMultiPartFile();
        MultipartFile mp4File = TEST_UTIL.getMP4MultiPartFile();
        MultipartFile validFile = TEST_UTIL.getPNGMultiPartFile();

        Region undefinedrRegion = null;
        Region invalidX = new Region(-1, 20, 20, 20);
        Region invalidY = new Region(20, -1, 20, 20);
        Region invaidWidth = new Region(20, 20, 2, 20);
        Region invalidheight = new Region(20, 20, 20, 2);
        Region validRegion = new Region(20, 20, 20, 20);

        double invalidMinAlpha = 0d;
        double invalidMaxAlpha = 5d;
        double validAlpha = 0.1;

        double invalidMinBeta = 0d;
        double invalidMaxBeta = 5000d;
        double validBeta = 50d;

        //@formatter:off
        return new Object[][] {
            {undefinedFile, validAlpha, validBeta, validRegion, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {emptyFile, validAlpha, validBeta, validRegion, MultipartFileUtil.EMPTY_FILE_MESSAGE},
            {mp4File, validAlpha, validBeta, validRegion, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {validFile, invalidMinAlpha, validBeta, validRegion, INVALID_CONTRAST_BRIGHTNESS_ALPHA_MSG},
            {validFile, invalidMaxAlpha, validBeta, validRegion, INVALID_CONTRAST_BRIGHTNESS_ALPHA_MSG},
            {validFile, validAlpha, invalidMinBeta, validRegion, INVALID_CONTRAST_BRIGHTNESS_BETA_MSG},
            {validFile, validAlpha, invalidMaxBeta, validRegion, INVALID_CONTRAST_BRIGHTNESS_BETA_MSG},
            {validFile, validAlpha, validBeta, invalidX, INVALID_X_REGION_MSG},
            {validFile, validAlpha, validBeta, invalidY, INVALID_Y_REGION_MSG},
            {validFile, validAlpha, validBeta, invaidWidth, INVALID_WIDTH_REGION},
            {validFile, validAlpha, validBeta, invalidheight, INVALID_HEIGHT_REGION},
            {validFile, validAlpha, validBeta, undefinedrRegion, ENTER_REGION_IMAGE},
        };
        //@formatter:on
    }


    @DataProvider(name = "contrastAndBrightnessErrorTestDataProvider")
    public static Object[][] contrastAndBrightnessErrorTestDataProvider() throws IOException {

        MultipartFile undefinedFile = null;
        MultipartFile emptyFile = TEST_UTIL.getEmptyMultiPartFile();
        MultipartFile mp4File = TEST_UTIL.getMP4MultiPartFile();
        MultipartFile validFile = TEST_UTIL.getPNGMultiPartFile();

        double invalidMinAlpha = 0d;
        double invalidMaxAlpha = 5d;
        double validAlpha = 0.1;

        double invalidMinBeta = 0d;
        double invalidMaxBeta = 5000d;
        double validBeta = 50d;

        //@formatter:off
        return new Object[][] {
            {undefinedFile, validAlpha, validBeta, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {emptyFile, validAlpha, validBeta, MultipartFileUtil.EMPTY_FILE_MESSAGE},
            {mp4File, validAlpha, validBeta, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {validFile, invalidMinAlpha, validBeta, INVALID_CONTRAST_BRIGHTNESS_ALPHA_MSG},
            {validFile, invalidMaxAlpha, validBeta, INVALID_CONTRAST_BRIGHTNESS_ALPHA_MSG},
            {validFile, validAlpha, invalidMinBeta, INVALID_CONTRAST_BRIGHTNESS_BETA_MSG},
            {validFile, validAlpha, invalidMaxBeta, INVALID_CONTRAST_BRIGHTNESS_BETA_MSG},
        };
        //@formatter:on
    }
}