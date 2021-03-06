package br.com.linecode.imgprocess.service;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.validation.Validation;

import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import br.com.linecode.imgprocess.model.Dimenssion;
import br.com.linecode.imgprocess.model.HsvColorChange;
import br.com.linecode.imgprocess.model.Region;
import br.com.linecode.shared.excpetion.BadRequestException;
import br.com.linecode.shared.service.ValidatorService;

public class ManipulateServiceUnitTest {

    @Spy
    private ValidatorService validatorService = new ValidatorService(); 

    @InjectMocks
    private ManipulateService manipulateServiceMock = new ManipulateService(); 

    @BeforeMethod
    private void configMock() {
        //@formatter:off
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(
            validatorService, "validator",
            Validation.buildDefaultValidatorFactory().getValidator()
        );
        //@formatter:on
    }

    @Test(dataProvider = "resizeErrorTestDataProvider", dataProviderClass = ManipulateServiceDataProvider.class)
    public void resizeErrorTest(MultipartFile img, Dimenssion dimenssion, String msgError) throws IOException, URISyntaxException {
        try {
            manipulateServiceMock.resize(img, dimenssion);
        } catch(IllegalArgumentException | BadRequestException ex) {
            Assert.assertTrue(ex.getMessage().contains(msgError));
        }
    }

    @Test(dataProvider = "cropErrorTestDataProvider", dataProviderClass = ManipulateServiceDataProvider.class)
    public void cropErrorTest(MultipartFile img, Region region, String msgError) throws IOException {
        try {
            manipulateServiceMock.crop(img, region);
        } catch(IllegalArgumentException | BadRequestException ex) {
            Assert.assertTrue(ex.getMessage().contains(msgError));
        }
    }

    @Test(dataProvider = "brightnessErrorTestDataProvider", dataProviderClass = ManipulateServiceDataProvider.class)
    public void brightnessErrorTest(MultipartFile file, Double alpha, String msgError) throws IOException {
        try {
            manipulateServiceMock.brightness(file, alpha);
        } catch(IllegalArgumentException | BadRequestException ex) {
            Assert.assertTrue(ex.getMessage().contains(msgError));
        }
    }

    @Test(dataProvider = "brightnessRegionErrorTestDataProvider", dataProviderClass = ManipulateServiceDataProvider.class)
    public void brightnessRegionErrorTest(MultipartFile file, Double alpha, Region region, String msgError) throws IOException {
        try {
            manipulateServiceMock.brightness(file, alpha, region);
        } catch(IllegalArgumentException | BadRequestException ex) {
            Assert.assertTrue(ex.getMessage().contains(msgError));
        }
    }

    @Test(dataProvider = "saturationErrorTestDataProvider", dataProviderClass = ManipulateServiceDataProvider.class)
    public void saturationErrorTest(MultipartFile file, Double alpha, String msgError) throws IOException {
        try {
            manipulateServiceMock.saturation(file, alpha);
        } catch(IllegalArgumentException | BadRequestException ex) {
            Assert.assertTrue(ex.getMessage().contains(msgError));
        }
    }

    @Test(dataProvider = "saturationRegionErrorTestDataProvider", dataProviderClass = ManipulateServiceDataProvider.class)
    public void saturationRegionErrorTest(MultipartFile file, Double alpha, Region region, String msgError) throws IOException {
        try {
            manipulateServiceMock.saturation(file, alpha, region);
        } catch(IllegalArgumentException | BadRequestException ex) {
            Assert.assertTrue(ex.getMessage().contains(msgError));
        }
    }

    @Test(dataProvider = "contrastAndBrightnessErrorTestDataProvider", dataProviderClass = ManipulateServiceDataProvider.class)
    public void contrastAndBrightnessError(MultipartFile file, Double alpha, Double beta, String msgError) throws IOException {
        try {
            manipulateServiceMock.contrastAndBrightness(file, alpha, beta);
        } catch(IllegalArgumentException | BadRequestException ex) {
            Assert.assertTrue(ex.getMessage().contains(msgError));
        }
    }

    @Test(dataProvider = "contrastAndBrightnessRegionErrorTestDataProvider", dataProviderClass = ManipulateServiceDataProvider.class)
    public void contrastAndBrightnessRegionErrorTest(MultipartFile file, Double alpha, Double beta, Region region, String msgError) throws IOException {
        try {
            manipulateServiceMock.contrastAndBrightness(file, alpha, beta, region);
        } catch(IllegalArgumentException | BadRequestException ex) {
            Assert.assertTrue(ex.getMessage().contains(msgError));
        }
    }

    @Test(dataProvider = "colorChangeErrorTestDataProvider", dataProviderClass = ManipulateServiceDataProvider.class)
    public void colorChangeErrorTes(MultipartFile file, HsvColorChange colorChange, String msgError) throws IOException {
        try {
            manipulateServiceMock.colorChange(file, colorChange);
        } catch(IllegalArgumentException | BadRequestException ex) {
            Assert.assertTrue(ex.getMessage().contains(msgError));
        }
    }

    @Test(dataProvider = "colorChangeRegionErrorTestDataProvider", dataProviderClass = ManipulateServiceDataProvider.class)
    public void colorChangeRegionErrorTes(MultipartFile file, HsvColorChange colorChange, Region region, String msgError) throws IOException {
        try {
            manipulateServiceMock.colorChange(file, colorChange, region);
        } catch(IllegalArgumentException | BadRequestException ex) {
            Assert.assertTrue(ex.getMessage().contains(msgError));
        }
    }

    @Test(dataProvider = "sumImageErrorTestDataProvider", dataProviderClass = ManipulateServiceDataProvider.class)
    public void sumImageErrorTestData(MultipartFile fileA, MultipartFile fileB, String msgError) throws IOException {
        try {
            manipulateServiceMock.sumImage(fileA, fileB);
        } catch(IllegalArgumentException | BadRequestException ex) {
            Assert.assertTrue(ex.getMessage().contains(msgError));
        }
    }
}