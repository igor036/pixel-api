package br.com.linecode.imgprocess.service;

import java.io.IOException;

import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import br.com.linecode.imgprocess.model.Dimenssion;
import br.com.linecode.imgprocess.model.Region;
import br.com.linecode.imgprocess.util.MatUtil;
import br.com.linecode.imgprocess.util.MultipartFileUtil;
import br.com.linecode.shared.excpetion.BadRequestException;
import br.com.linecode.shared.service.ValidatorService;

@Service
public class ManipulateService {
	
	private static final String INVALID_DIMENSSION_MSG = "Enter a new dimension for image.";
	private static final String INVALID_REGION_MSG = "Enter a region for crop the image.";
	
	@Autowired
	private ValidatorService validatorService; 
	
	public byte[] resize(MultipartFile file, Dimenssion dimenssion) throws IOException {
		
		MultipartFileUtil.assertFile(file);
		assertDimenssion(dimenssion);
		
		Mat image = MatUtil.getMat(file.getBytes());
		Mat resize = MatUtil.resize(image, dimenssion);
		String extension = MultipartFileUtil.getExtension(file);

		return MatUtil.getBlob(resize, extension);
	}
	
	public byte[] crop(MultipartFile file, Region region) throws IOException {
		
		MultipartFileUtil.assertFile(file);
		assertRegion(region);	
		
		try {
			
			Mat image = MatUtil.getMat(file.getBytes());
			Mat crop = MatUtil.crop(image, region);
			String extension = MultipartFileUtil.getExtension(file);

			return MatUtil.getBlob(crop, extension);

		} catch (IllegalArgumentException e) {
			throw new BadRequestException(e.getMessage());
		}
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
