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
import br.com.linecode.shared.excpetion.BadRequestException;
import br.com.linecode.shared.service.ValidatorService;

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
}