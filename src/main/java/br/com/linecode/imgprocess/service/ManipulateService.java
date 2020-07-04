package br.com.linecode.imgprocess.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import br.com.linecode.imgprocess.model.Dimenssion;
import br.com.linecode.imgprocess.model.Region;
import br.com.linecode.imgprocess.util.ImageUtil;
import br.com.linecode.imgprocess.util.MultipartFileUtil;
import br.com.linecode.shared.excpetion.BadRequestException;
import br.com.linecode.shared.service.ValidatorService;

@Service
public class ManipulateService {
	
	private static final String INVALID_DIMENSSION_MSG = "Enter a new dimension for image.";
	private static final String INVALID_REGION_MSG = "Enter a region for crop the image.";
	private static final String INVALID_ALPHA = "Invalid alpha value, min value should be be 5.";
	
	@Autowired
	private ValidatorService validatorService; 
	
	public byte[] resize(MultipartFile file, Dimenssion dimenssion) throws IOException {
		MultipartFileUtil.assertFile(file);
		assertDimenssion(dimenssion);
		return ImageUtil.resize(file.getBytes(), MultipartFileUtil.getExtension(file), dimenssion);
	}
	
	public byte[] crop(MultipartFile file, Region region) throws IOException {
		
		MultipartFileUtil.assertFile(file);
		assertRegion(region);
		
		try {
			return ImageUtil.crop(file.getBytes(), MultipartFileUtil.getExtension(file), region);
		} catch (IllegalArgumentException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public byte[] blur(MultipartFile file, double alpha) throws IOException {
		MultipartFileUtil.assertFile(file);
		if (alpha <= 5) throw new BadRequestException(INVALID_ALPHA);
		return ImageUtil.blur(file.getBytes(), MultipartFileUtil.getExtension(file), alpha);
	}
	
	private void assertDimenssion(Dimenssion dimenssion) {
		Assert.notNull(dimenssion, INVALID_DIMENSSION_MSG);
		validatorService.assertModel(dimenssion);
	}
	
	private void assertRegion(Region region) {
		Assert.notNull(region, INVALID_REGION_MSG);
		validatorService.assertModel(region);
	}
}
