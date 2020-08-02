package br.com.linecode.imgprocess.service;

import java.io.IOException;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import br.com.linecode.imgprocess.model.Dimenssion;
import br.com.linecode.imgprocess.model.Region;
import br.com.linecode.imgprocess.model.HsvColorChange;
import br.com.linecode.imgprocess.util.MatUtil;
import br.com.linecode.imgprocess.util.MultipartFileUtil;
import br.com.linecode.shared.excpetion.BadRequestException;
import br.com.linecode.shared.service.ValidatorService;

@Service
public class ManipulateService {

	private static final String INVALID_DIMENSSION_MSG = "Enter a new dimension for image.";
	private static final String INVALID_REGION_MSG = "Enter a region of image.";
	private static final String INVALID_COLOR_CHANGE_MSG = "Enter a color change porps.";
	private static final String INVALID_BRIGHTNESS_ALPHA_MSG = "The brightness alpha value should be  >= 0.1 and <= 3.0";
	private static final String INVALID_SATURARION_ALPHA_MSG = "The saturation alpha value should be  >= 0.1 and <= 3.0";
	private static final String INVALID_CONTRAST_BRIGHTNESS_ALPHA_MSG = "The brightnewss and contrast alpha value should be  >= 0.1 and <= 3.0";
	private static final String INVALID_CONTRAST_BRIGHTNESS_BETA_MSG = "The brightnewss and contrast beta value should be  >= 1.0 and <= 100.0";

	private static final double MIN_BRIGHTNESS_ALPHA = 0.1d;
	private static final double MAX_BRIGHTNESS_ALPHA = 3d;

	private static final double MIN_SATURATION_ALPHA = 0.1d;
	private static final double MAX_SATURATION_ALPHA = 3d;

	private static final double MIN_CONTRAST_BRIGHTNESS_ALPHA = 0.1d;
	private static final double MAX_CONTRAST_BRIGHTNESS_ALPHA = 3d;

	private static final double MIN_CONTRAST_BRIGHTNESS_BETA = 1d;
	private static final double MAX_CONTRAST_BRIGHTNESS_BETA = 100d;

	@Autowired
	private ValidatorService validatorService;

	public byte[] resize(MultipartFile file, Dimenssion dimenssion) throws IOException {

		MultipartFileUtil.assertFile(file);
		assertDimenssion(dimenssion);

		Mat image = MatUtil.getMat(file.getBytes());
		image = MatUtil.resize(image, dimenssion);

		return MatUtil.getBlob(image, MultipartFileUtil.getExtension(file));
	}

	public byte[] crop(MultipartFile file, Region region) throws IOException {

		MultipartFileUtil.assertFile(file);
		assertRegion(region);

		try {

			Mat image = MatUtil.getMat(file.getBytes());
			image = MatUtil.crop(image, region);

			return MatUtil.getBlob(image, MultipartFileUtil.getExtension(file));

		} catch (IllegalArgumentException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public byte[] brightness(MultipartFile file, double alpha) throws IOException {

		assertbrightness(alpha);
		MultipartFileUtil.assertFile(file);

		Mat image = MatUtil.getMat(file.getBytes());
		image = MatUtil.brightness(image, alpha);

		return MatUtil.getBlob(image, MultipartFileUtil.getExtension(file));
	}

	public byte[] brightness(MultipartFile file, double alpha, Region region) throws IOException {

		MultipartFileUtil.assertFile(file);
		assertbrightness(alpha);
		assertRegion(region);

		Rect rect = MatUtil.regionToRect(region);
		Mat image = MatUtil.getMat(file.getBytes());

		MatUtil.brightness(image.submat(rect), alpha).copyTo(image.submat(rect));
		return MatUtil.getBlob(image, MultipartFileUtil.getExtension(file));
	}

	public byte[] saturation(MultipartFile file, double alpha) throws IOException {

		assertSaturation(alpha);
		MultipartFileUtil.assertFile(file);

		Mat image = MatUtil.getMat(file.getBytes());
		image = MatUtil.saturation(image, alpha);

		return MatUtil.getBlob(image, MultipartFileUtil.getExtension(file));
	}

	public byte[] saturation(MultipartFile file, double alpha, Region region) throws IOException {

		assertSaturation(alpha);
		assertRegion(region);
		MultipartFileUtil.assertFile(file);

		Rect rect = MatUtil.regionToRect(region);
		Mat image = MatUtil.getMat(file.getBytes());

		MatUtil.saturation(image.submat(rect), alpha).copyTo(image.submat(rect));
		return MatUtil.getBlob(image, MultipartFileUtil.getExtension(file));
	}

	public byte[] contrastAndBrightness(MultipartFile file, double alpha, double beta) throws IOException {

		assertcontrastAndBrightness(alpha, beta);
		MultipartFileUtil.assertFile(file);

		Mat image = MatUtil.getMat(file.getBytes());
		image = MatUtil.contrastAndBrightness(image, alpha, beta);

		return MatUtil.getBlob(image, MultipartFileUtil.getExtension(file));
	}

	public byte[] contrastAndBrightness(MultipartFile file, double alpha, double beta, Region region)
			throws IOException {

		assertcontrastAndBrightness(alpha, beta);
		assertRegion(region);
		MultipartFileUtil.assertFile(file);

		Rect rect = MatUtil.regionToRect(region);
		Mat image = MatUtil.getMat(file.getBytes());

		//@formatter:off
		MatUtil.contrastAndBrightness(image.submat(rect), alpha, beta)
			.copyTo(image.submat(rect));
		//@formatter:on

		return MatUtil.getBlob(image, MultipartFileUtil.getExtension(file));
	}

	public byte[] colorChange(MultipartFile file, HsvColorChange colorChange) throws IOException {

		MultipartFileUtil.assertFile(file);
		assertHsvColorChange(colorChange);

		Mat image = MatUtil.getMat(file.getBytes());
		image = MatUtil.colorChange(image, colorChange);

		return MatUtil.getBlob(image, MultipartFileUtil.getExtension(file));
	}

	public byte[] colorChange(MultipartFile file, HsvColorChange colorChange, Region region) throws IOException {

		MultipartFileUtil.assertFile(file);
		assertRegion(region);
		assertHsvColorChange(colorChange);

		Rect rect = MatUtil.regionToRect(region);
		Mat image = MatUtil.getMat(file.getBytes());
		
		MatUtil.colorChange(image.submat(rect), colorChange).copyTo(image.submat(rect));
		return MatUtil.getBlob(image, MultipartFileUtil.getExtension(file));
	}

	private void assertSaturation(double alpha) {
		if (alpha < MIN_SATURATION_ALPHA || alpha > MAX_SATURATION_ALPHA) {
			throw new BadRequestException(INVALID_SATURARION_ALPHA_MSG);
		}
	}

	private void assertbrightness(double alpha) {
		if (alpha < MIN_BRIGHTNESS_ALPHA || alpha > MAX_BRIGHTNESS_ALPHA) {
			throw new BadRequestException(INVALID_BRIGHTNESS_ALPHA_MSG);
		}
	}

	private void assertcontrastAndBrightness(double alpha, double beta) {
		if (alpha < MIN_CONTRAST_BRIGHTNESS_ALPHA || alpha > MAX_CONTRAST_BRIGHTNESS_ALPHA) {
			throw new BadRequestException(INVALID_CONTRAST_BRIGHTNESS_ALPHA_MSG);
		}

		if (beta < MIN_CONTRAST_BRIGHTNESS_BETA || beta > MAX_CONTRAST_BRIGHTNESS_BETA) {
			throw new BadRequestException(INVALID_CONTRAST_BRIGHTNESS_BETA_MSG);
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

	private void assertHsvColorChange(HsvColorChange colorChange) {
		Assert.notNull(colorChange, INVALID_COLOR_CHANGE_MSG);
		validatorService.assertModel(colorChange);
	}
}
