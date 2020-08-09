package br.com.linecode.imgprocess.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.testng.annotations.DataProvider;

import br.com.linecode.imgprocess.model.Region;
import br.com.linecode.imgprocess.model.RgbColor;
import br.com.linecode.imgprocess.util.MultipartFileUtil;
import br.com.linecode.util.TestUtil;

public abstract class FilterServiceDataProvider {

    private static final String INVALID_ALPHA_MSG = "Invalid alpha value, min value should be be 5.";
    private static final String INVALID_REGION = "Enter a region of image.";
    private static final String INVALID_X = "invalid x.";
    private static final String INVALID_Y = "invalid y.";
    private static final String INVALID_WIDTH = "invalid width.";
    private static final String INVALID_HEIGHT = "invalid height.";

    private static final TestUtil UTIL = new TestUtil();

    @DataProvider(name = "blurErrorTestDataProvider")
    public static Object[][] blurErrorTestDataProvider() throws IOException {
        MultipartFile validFile = UTIL.getPNGMultiPartFile();
        //@formatter:off
        return new Object[][] {
            {validFile,  0d,  INVALID_ALPHA_MSG},
            {validFile, -3d,  INVALID_ALPHA_MSG},
            {validFile,  4d,  INVALID_ALPHA_MSG},
            {validFile,  4.8, INVALID_ALPHA_MSG}
        };
        //@formatter:on
    }

    @DataProvider(name = "blurRegionErrorTestDataProvider")
    public static Object[][] blurRegionErrorTestDataProvider() throws IOException {
        
        MultipartFile img = UTIL.getPNGMultiPartFile();
        double alpha = 10;
        Region undefined = null;
        Region invalidX = new Region(-1, 20, 20, 20);
        Region invalidY = new Region(20, -1, 20, 20);
        Region invaidWidth = new Region(20, 20, 2, 20);
        Region invalidheight = new Region(20, 20, 20, 2);

        //@formatter:off
        return new Object[][] {
            {img, undefined, alpha, INVALID_REGION},
            {img, invalidX, alpha, INVALID_X},
            {img, invalidY, alpha, INVALID_Y},
            {img, invaidWidth, alpha, INVALID_WIDTH},
            {img, invalidheight, alpha, INVALID_HEIGHT},
        };
        //@formatter:on
    }

    @DataProvider(name = "grayScaleRegionErrorTestDataProvider")
    public static Object[][] grayScaleRegionErrorTestDataProvider() throws IOException {
        
        MultipartFile img = UTIL.getPNGMultiPartFile();
        Region undefined = null;
        Region invalidX = new Region(-1, 20, 20, 20);
        Region invalidY = new Region(20, -1, 20, 20);
        Region invaidWidth = new Region(20, 20, 2, 20);
        Region invalidheight = new Region(20, 20, 20, 2);

        //@formatter:off
        return new Object[][] {
            {img, undefined, INVALID_REGION},
            {img, invalidX, INVALID_X},
            {img, invalidY, INVALID_Y},
            {img, invaidWidth, INVALID_WIDTH},
            {img, invalidheight, INVALID_HEIGHT},
        };
        //@formatter:on
    }

    @DataProvider(name = "rgbMoldErrorTestDataProvider")
    public static Object[][] rgbMoldErrorTestDataProvider() throws IOException {

        RgbColor validColor = new RgbColor(100, 100, 100);
        MultipartFile validFile = UTIL.getPNGMultiPartFile();

        RgbColor minRInvalid = new RgbColor(-1, 100, 100);
        RgbColor maxRInvalid = new RgbColor(256, 100, 100);

        RgbColor minGInvalid = new RgbColor(100, -1, 100);
        RgbColor maxGInvalid = new RgbColor(100, 256, 100);

        RgbColor miBInvalid = new RgbColor(100, 100, -1);
        RgbColor maxBInvalid = new RgbColor(100, 100, 256);

        //@formatter:off
        return new Object[][] {
            {null, validColor, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {UTIL.getMP4MultiPartFile(), validColor, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {UTIL.getEmptyMultiPartFile(), validColor, MultipartFileUtil.EMPTY_FILE_MESSAGE},
            {validFile, minRInvalid, "Min value for R is 0."},
            {validFile, maxRInvalid, "Max value for R is 255."},
            {validFile, minGInvalid, "Min value for G is 0."},
            {validFile, maxGInvalid, "Max value for G is 255."},
            {validFile, miBInvalid, "Min value for B is 0."},
            {validFile, maxBInvalid, "Max value for B is 255."}
        };
        //@formatter:on
    }

    @DataProvider(name = "rgbMoldPrincipalColorErrorTestDataProvider")
    public static Object[][] rgbMoldPrincipalColorErrorTestDataProvider() throws IOException {
        RgbColor validColor = new RgbColor(100, 100, 100);
        //@formatter:off
        return new Object[][] {
            {null, validColor, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {UTIL.getMP4MultiPartFile(), validColor, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {UTIL.getEmptyMultiPartFile(), validColor, MultipartFileUtil.EMPTY_FILE_MESSAGE}
        };
        //@formatter:on
    }

    @DataProvider(name = "sepiaErrorTestDataProvider")
    public static Object[][] sepiaErrorTestDataProvider() throws IOException {
        
        MultipartFile img = UTIL.getPNGMultiPartFile();
        Region undefined = null;
        Region invalidX = new Region(-1, 20, 20, 20);
        Region invalidY = new Region(20, -1, 20, 20);
        Region invaidWidth = new Region(20, 20, 2, 20);
        Region invalidheight = new Region(20, 20, 20, 2);
        Region validRegion = new Region(20, 20, 20, 20);

        //@formatter:off
        return new Object[][] {
            {null, validRegion, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {img, undefined, INVALID_REGION},
            {img, invalidX, INVALID_X},
            {img, invalidY, INVALID_Y},
            {img, invaidWidth, INVALID_WIDTH},
            {img, invalidheight, INVALID_HEIGHT},
        };
        //@formatter:on
    }

    @DataProvider(name = "grayScaleMagicColorErrorTestDataProvider")
    public static Object[][] grayScaleMagicColorErrorTestDataProvider() throws IOException {

        MultipartFile undefinedFile = null;
        MultipartFile emptyFile = UTIL.getEmptyMultiPartFile();
        MultipartFile mp4File = UTIL.getMP4MultiPartFile();
        MultipartFile validFile = UTIL.getPNGMultiPartFile();

        //@formatter:off
        return new Object[][] {
            {undefinedFile, 179, 179, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {emptyFile, 179, 179, MultipartFileUtil.EMPTY_FILE_MESSAGE},
            {mp4File, 179, 179, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {validFile, -10, 179, "Min hue value should be 0 - 179"},
            {validFile, 255, 179, "Min hue value should be 0 - 179"},
            {validFile, 179, -10, "Max hue value should be 0 - 179"},
            {validFile, 179, 255, "Max hue value should be 0 - 179"}
        };
        //@formatter:on
    }

    @DataProvider(name = "grayScaleMagicColorRegionErrorTestDataProvider")
    public static Object[][] grayScaleMagicColorRegionErrorTestDataProvider() throws IOException {

        MultipartFile undefinedFile = null;
        MultipartFile emptyFile = UTIL.getEmptyMultiPartFile();
        MultipartFile mp4File = UTIL.getMP4MultiPartFile();
        MultipartFile validFile = UTIL.getPNGMultiPartFile();

        Region undefined = null;
        Region invalidX = new Region(-1, 20, 20, 20);
        Region invalidY = new Region(20, -1, 20, 20);
        Region invaidWidth = new Region(20, 20, 2, 20);
        Region invalidheight = new Region(20, 20, 20, 2);
        Region validRegion = new Region(20, 20, 20, 20);

        //@formatter:off
        return new Object[][] {
            {undefinedFile, validRegion, 179, 179, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {emptyFile, validRegion, 179, 179, MultipartFileUtil.EMPTY_FILE_MESSAGE},
            {mp4File, validRegion, 179, 179, MultipartFileUtil.INVALID_FILE_MESSAGE},
            {validFile, validRegion, -10, 179, "Min hue value should be 0 - 179"},
            {validFile, validRegion, 255, 179, "Min hue value should be 0 - 179"},
            {validFile, validRegion, 179, -10, "Max hue value should be 0 - 179"},
            {validFile, validRegion, 179, 255, "Max hue value should be 0 - 179"},
            {validFile, undefined, 179, 179, INVALID_REGION},
            {validFile, invalidX, 179, 179, INVALID_X},
            {validFile, invalidY, 179, 179, INVALID_Y},
            {validFile, invaidWidth, 179, 179, INVALID_WIDTH},
            {validFile, invalidheight, 179, 179, INVALID_HEIGHT},
        };
        //@formatter:on
    }
}