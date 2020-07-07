package br.com.linecode.imgprocess.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import br.com.linecode.imgprocess.model.Region;
import br.com.linecode.imgprocess.util.ImageUtil;
import br.com.linecode.imgprocess.util.MultipartFileUtil;
import br.com.linecode.shared.excpetion.BadRequestException;
import br.com.linecode.shared.service.ValidatorService;

@Service
public class FilterService {
    
    private static final String INVALID_REGION_MSG = "Enter a region of image.";
    private static final String INVALID_ALPHA = "Invalid alpha value, min value should be be 5.";

    @Autowired
	private ValidatorService validatorService; 

    public byte[] blur(MultipartFile file, double alpha) throws IOException {
		MultipartFileUtil.assertFile(file);
        if (alpha <= 5) throw new BadRequestException(INVALID_ALPHA);
		return ImageUtil.blur(file.getBytes(), MultipartFileUtil.getExtension(file), alpha);
    }
    
    public byte[] blur(MultipartFile file, double alpha, Region region) throws IOException {
        MultipartFileUtil.assertFile(file);
        assertRegion(region);
        if (alpha <= 5) throw new BadRequestException(INVALID_ALPHA);
        return ImageUtil.blur(file.getBytes(), MultipartFileUtil.getExtension(file), alpha, region);
    }

    public byte[] grayScale(MultipartFile file) throws IOException {
        MultipartFileUtil.assertFile(file);
        return ImageUtil.grayScale(file.getBytes(), MultipartFileUtil.getExtension(file));
    }

    public byte[] grayScale(MultipartFile file, Region region) throws IOException {
        MultipartFileUtil.assertFile(file);
        assertRegion(region);
        return ImageUtil.grayScale(file.getBytes(), region, MultipartFileUtil.getExtension(file));
    }
    
    private void assertRegion(Region region) {
		Assert.notNull(region, INVALID_REGION_MSG);
		validatorService.assertModel(region);
	}
}