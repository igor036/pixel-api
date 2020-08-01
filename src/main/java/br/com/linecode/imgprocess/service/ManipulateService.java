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
	private static final String INVALID_REGION_MSG = "Enter a region of image.";
	private static final String INVALID_BRIGHTNESS_ALPHA_MSG = "The brightness alpha value should be  >= 0.1 and <= 3.0";
	private static final String INVALID_SATURARION_ALPHA_MSG = "The saturation alpha value should be  >= 0.1 and <= 3.0";

	private static final double MIN_BRIGHTNESS_ALPHA = 0.1d;
	private static final double MAX_BRIGHTNESS_ALPHA = 3d;

	private static final double MIN_SATURATION_ALPHA = 0.1d;
	private static final double MAX_SATURATION_ALPHA = 3d;
	
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

	public byte[] brightness(MultipartFile file, double alpha) throws IOException {

		if (alpha < MIN_BRIGHTNESS_ALPHA || alpha > MAX_BRIGHTNESS_ALPHA) {
			throw new BadRequestException(INVALID_BRIGHTNESS_ALPHA_MSG);
		}

		MultipartFileUtil.assertFile(file);
		Mat image = MatUtil.getMat(file.getBytes());
		Mat brightness = MatUtil.brightness(image, alpha);
		String extension = MultipartFileUtil.getExtension(file);

		return MatUtil.getBlob(brightness, extension);
	}

	public byte[] brightness(MultipartFile file, double alpha, Region region) throws IOException {

		if (alpha < MIN_BRIGHTNESS_ALPHA || alpha > MAX_BRIGHTNESS_ALPHA) {
			throw new BadRequestException(INVALID_BRIGHTNESS_ALPHA_MSG);
		}

		assertRegion(region);

		MultipartFileUtil.assertFile(file);
		Mat image = MatUtil.getMat(file.getBytes());
		Mat brightness = MatUtil.brightness(image, alpha, region);
		String extension = MultipartFileUtil.getExtension(file);

		return MatUtil.getBlob(brightness, extension);
	}

	public byte[] saturation(MultipartFile file, double alpha) throws IOException {

		if (alpha < MIN_SATURATION_ALPHA || alpha > MAX_SATURATION_ALPHA) {
			throw new BadRequestException(INVALID_SATURARION_ALPHA_MSG);
		}
		
		MultipartFileUtil.assertFile(file);
		Mat image = MatUtil.getMat(file.getBytes());
		Mat brightness = MatUtil.saturation(image, alpha);
		String extension = MultipartFileUtil.getExtension(file);

		return MatUtil.getBlob(brightness, extension);
	}

	public byte[] saturation(MultipartFile file, double alpha, Region region) throws IOException {

		if (alpha < MIN_SATURATION_ALPHA || alpha > MAX_SATURATION_ALPHA) {
			throw new BadRequestException(INVALID_SATURARION_ALPHA_MSG);
		}
		
		assertRegion(region);

		MultipartFileUtil.assertFile(file);
		Mat image = MatUtil.getMat(file.getBytes());
		Mat brightness = MatUtil.saturation(image, alpha, region);
		String extension = MultipartFileUtil.getExtension(file);

		return MatUtil.getBlob(brightness, extension);
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
