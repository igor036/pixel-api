package br.com.linecode.util;

import org.springframework.web.multipart.MultipartFile;
import org.testng.Assert;
import org.testng.annotations.Test;

import br.com.linecode.imgprocess.util.MultipartFileUtil;
import br.com.linecode.shared.excpetion.BadRequestException;

public class MultipartFileUtilUnittest {
    
    @Test(dataProvider = "assertFileErrorTestDataProvider", dataProviderClass = MultipartFileUtilDataProvider.class)
    public void assertFileErrorTest(MultipartFile file, String error) {
        try {
            MultipartFileUtil.assertFile(file);
        } catch(BadRequestException ex) {
            Assert.assertEquals(ex.getMessage(), error);
        }
    }

    @Test(dataProvider = "getExtensionTestDataProvider", dataProviderClass = MultipartFileUtilDataProvider.class)
    public void getExtensionTest(MultipartFile file, String extension) {
        Assert.assertEquals(MultipartFileUtil.getExtension(file).toUpperCase(), extension.toUpperCase());
    }
}