package br.com.linecode.imgprocess.service;

import java.io.IOException;

import javax.validation.Validation;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import br.com.linecode.imgprocess.model.Region;
import br.com.linecode.imgprocess.model.RgbColor;
import br.com.linecode.shared.excpetion.BadRequestException;
import br.com.linecode.shared.service.ValidatorService;
import br.com.linecode.util.MultipartFileUtilDataProvider;

public class FilterServiceUnitTest {

    @Spy
    private ValidatorService validatorService = new ValidatorService();

    @InjectMocks
    private FilterService filterServiceMock = new FilterService();

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

    @Test(dataProvider = "blurErrorTestDataProvider", dataProviderClass = FilterServiceDataProvider.class)
    public void blurErrorTest(MultipartFile file, double alpha, String error) throws IOException {
        try {
            filterServiceMock.blur(file, alpha);
        } catch(BadRequestException ex) {
            Assert.assertEquals(ex.getMessage(), error);
        }
    }

    @Test(dataProvider = "blurRegionErrorTestDataProvider", dataProviderClass = FilterServiceDataProvider.class)
    public void blurRegionErrorTestDataProvider(MultipartFile file, Region region, double alpha, String error) throws IOException {
        try {
            filterServiceMock.blur(file, alpha, region);
        } catch(BadRequestException | IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains(error));
        }
    }

    @Test(dataProvider = "assertFileErrorTestDataProvider", dataProviderClass = MultipartFileUtilDataProvider.class)
    public void grayScaleErrorTestDataProvider(MultipartFile file, String error) throws IOException {
        try {
            filterServiceMock.grayScale(file);
        } catch(BadRequestException ex) {
            Assert.assertEquals(ex.getMessage(), error);
        }
    }

    @Test(dataProvider = "grayScaleRegionErrorTestDataProvider", dataProviderClass = FilterServiceDataProvider.class)
    public void grayScaleRegionErrorTest(MultipartFile file, Region region, String error) throws IOException {
        try {
            filterServiceMock.grayScale(file, region);
        } catch(BadRequestException | IllegalArgumentException ex) {
            Assert.assertEquals(ex.getMessage(), error);
        }
    }

    @Test(dataProvider = "assertFileErrorTestDataProvider", dataProviderClass = MultipartFileUtilDataProvider.class)
    public void blurMoldErrorTest(MultipartFile file, String error) throws IOException {
        try {
            filterServiceMock.blurMold(file, 0d);
        } catch(BadRequestException ex) {
            Assert.assertEquals(ex.getMessage(), error);
        }
    }

    @Test(dataProvider = "assertFileErrorTestDataProvider", dataProviderClass = MultipartFileUtilDataProvider.class)
    public void grayScaleErrortest(MultipartFile file, String error) throws IOException {
        try {
            filterServiceMock.grayScale(file);
        } catch(BadRequestException ex) {
            Assert.assertEquals(ex.getMessage(), error);
        }
    }

    @Test(dataProvider = "rgbMoldErrorTestDataProvider", dataProviderClass = FilterServiceDataProvider.class)
    public void rgbMoldErrorTest(MultipartFile file, RgbColor color, String error) throws IOException{
        try {
            filterServiceMock.rgbMold(file, color);
        } catch(BadRequestException ex) {
            Assert.assertEquals(ex.getMessage(), error);
        }
    }

    @Test(dataProvider = "rgbMoldPrincipalColorErrorTestDataProvider", dataProviderClass = FilterServiceDataProvider.class)
    public void rgbMoldPrincipalColorErrorTest(MultipartFile file, RgbColor color, String error) throws IOException{
        try {
            filterServiceMock.rgbMoldPrincipalColor(file);
        } catch(BadRequestException ex) {
            Assert.assertEquals(ex.getMessage(), error);
        }
    }

    @Test(dataProvider = "assertFileErrorTestDataProvider", dataProviderClass = MultipartFileUtilDataProvider.class)
    public void sepiaErrotest(MultipartFile file, String error) throws IOException {
        try {
            filterServiceMock.sepia(file);
        } catch(BadRequestException ex) {
            Assert.assertEquals(ex.getMessage(), error);
        }
    }

    @Test(dataProvider = "sepiaErrorTestDataProvider", dataProviderClass = FilterServiceDataProvider.class)
    public void sepiaErrorTest(MultipartFile file, Region region, String error) throws IOException {
        try {
            filterServiceMock.sepia(file, region);
        } catch(BadRequestException | IllegalArgumentException ex) {
            Assert.assertEquals(ex.getMessage(), error);
        }
    }
}