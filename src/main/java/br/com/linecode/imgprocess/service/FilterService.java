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
import br.com.linecode.imgprocess.model.RgbColor;
import br.com.linecode.imgprocess.util.MatUtil;
import br.com.linecode.imgprocess.util.MultipartFileUtil;
import br.com.linecode.shared.excpetion.BadRequestException;
import br.com.linecode.shared.service.ValidatorService;

@Service
public class FilterService {
    
    private static final double DEFAULT_BLUR_ALPHA = 9d;
    private static final double DEFAULTL_RESIZE_PERCENTAGE = 0.10d;

    private static final String INVALID_REGION_MSG = "Enter a region of image.";
    private static final String INVALID_ALPHA = "Invalid alpha value, min value should be be 5.";
    private static final String INVALID_MIN_HUE_VALUE = "Min hue value should be 0 - 560";
    private static final String INVALID_MAX_HUE_VALUE = "Max hue value should be 0 - 560";

    @Autowired
	private ValidatorService validatorService; 

    public byte[] sepia(MultipartFile file) throws IOException {

        MultipartFileUtil.assertFile(file);

        Mat image = MatUtil.getMat(file.getBytes());
        image = MatUtil.sepia(image);

        return MatUtil.getBlob(image, MultipartFileUtil.getExtension(file));

    }

    public byte[] sepia(MultipartFile file, Region region) throws IOException {

        MultipartFileUtil.assertFile(file);
        assertRegion(region);

        Rect rect = MatUtil.regionToRect(region);
        Mat image = MatUtil.getMat(file.getBytes());
        
        MatUtil.sepia(image.submat(rect)).copyTo(image.submat(rect));
        return MatUtil.getBlob(image, MultipartFileUtil.getExtension(file));

    }

    public byte[] blur(MultipartFile file, double alpha) throws IOException {
        
        MultipartFileUtil.assertFile(file);
        assertBlur(alpha);
        
        Mat image = MatUtil.getMat(file.getBytes());
        image = MatUtil.blur(image, alpha);

        return MatUtil.getBlob(image, MultipartFileUtil.getExtension(file));
    }
    
    public byte[] blur(MultipartFile file, double alpha, Region region) throws IOException {
        
        MultipartFileUtil.assertFile(file);
        assertBlur(alpha);
        assertRegion(region);

        Rect rect = MatUtil.regionToRect(region);
        Mat image = MatUtil.getMat(file.getBytes());

        MatUtil.blur(image.submat(rect), alpha).copyTo(image.submat(rect));
        return MatUtil.getBlob(image, MultipartFileUtil.getExtension(file));
    }

    public byte[] blurMold(MultipartFile file, double alpha) throws IOException{

        MultipartFileUtil.assertFile(file);
        
        Mat foreground = MatUtil.getMat(file.getBytes());
        Mat background = MatUtil.copy(foreground);

        alpha = alpha > 5 ? alpha : DEFAULT_BLUR_ALPHA;

        background = MatUtil.blur(foreground, alpha);
        background = MatUtil.increaseSize(background, DEFAULTL_RESIZE_PERCENTAGE);
        foreground = MatUtil.decreaseSize(foreground, DEFAULTL_RESIZE_PERCENTAGE);

        Mat blurMold = MatUtil.putForeground(background, foreground);
        return MatUtil.getBlob(blurMold, MultipartFileUtil.getExtension(file));
    }

    public byte[] grayScale(MultipartFile file) throws IOException {
        
        MultipartFileUtil.assertFile(file);
        
        Mat image = MatUtil.getMat(file.getBytes());
        image = MatUtil.grayScale(image);

        return MatUtil.getBlob(image, MultipartFileUtil.getExtension(file));
    }

    public byte[] grayScale(MultipartFile file, Region region) throws IOException {
        
        MultipartFileUtil.assertFile(file);
        assertRegion(region);
        
        Rect rect = MatUtil.regionToRect(region);
        Mat image = MatUtil.getMat(file.getBytes());
        image = MatUtil.copy(image);

        MatUtil.grayScale(image.submat(rect)).copyTo(image.submat(rect));
        return MatUtil.getBlob(image, MultipartFileUtil.getExtension(file));

    }

    public byte[] grayScaleMold(MultipartFile file) throws IOException{

        MultipartFileUtil.assertFile(file);
        
        Mat foreground = MatUtil.getMat(file.getBytes());
        Mat background = MatUtil.copy(foreground);
        String extension = MultipartFileUtil.getExtension(file);

        background = MatUtil.grayScale(foreground);
        background = MatUtil.increaseSize(background, DEFAULTL_RESIZE_PERCENTAGE);
        foreground = MatUtil.decreaseSize(foreground, DEFAULTL_RESIZE_PERCENTAGE);

        Mat blurMold = MatUtil.putForeground(background, foreground);

        return MatUtil.getBlob(blurMold, extension);
    }

    public byte[] grayScaleMagicColor(MultipartFile file, int minHue, int maxHue) throws IOException {

        MultipartFileUtil.assertFile(file);
        assertHueValueGrayScaleMagicColor(minHue, maxHue);

        Mat image = MatUtil.getMat(file.getBytes());
        image = MatUtil.grayScaleMagicColor(image, minHue, maxHue);

        return MatUtil.getBlob(image, MultipartFileUtil.getExtension(file)); 
    }


    public byte[] grayScaleMagicColor(MultipartFile file, Region region, int minHue, int maxHue) throws IOException {

        MultipartFileUtil.assertFile(file);
        assertRegion(region);
        assertHueValueGrayScaleMagicColor(minHue, maxHue);

        Rect rect = MatUtil.regionToRect(region);
        Mat image = MatUtil.getMat(file.getBytes());
        Mat grayScale = MatUtil.grayScale(image);
        
        //@formatter:off
        MatUtil.grayScaleMagicColor(image.submat(rect), minHue, maxHue)
            .copyTo(grayScale.submat(rect));

        return MatUtil.getBlob(grayScale, MultipartFileUtil.getExtension(file)); 
        //@formatter:on
    }


    public byte[] rgbMold(MultipartFile file, RgbColor color) throws IOException {

        MultipartFileUtil.assertFile(file);
        validatorService.assertModel(color); 

        String extension = MultipartFileUtil.getExtension(file);
        Mat image = MatUtil.getMat(file.getBytes());

        return MatUtil.getBlob(rgbMold(image, color), extension);
    }

    public byte[] rgbMoldPrincipalColor(MultipartFile file) throws IOException {

        MultipartFileUtil.assertFile(file);
        String extension = MultipartFileUtil.getExtension(file);
        Mat image = MatUtil.getMat(file.getBytes());
        RgbColor color = MatUtil.findPrincipalColor(image);

        return MatUtil.getBlob(rgbMold(image, color), extension);

    }

    public Mat rgbMold(Mat image, RgbColor color) throws IOException {
        
        Mat foreground = MatUtil.copy(image);
        Mat background = MatUtil.getRGBMat(color, getBackgroundDimenssion(foreground));

        foreground = MatUtil.decreaseSize(foreground, DEFAULTL_RESIZE_PERCENTAGE);

        return MatUtil.putForeground(background, foreground);
    }

    private Dimenssion getBackgroundDimenssion(Mat image) {
        int width = (int)(image.width() + (image.width() * DEFAULTL_RESIZE_PERCENTAGE));
        int height = (int)(image.height() +  (image.height()* DEFAULTL_RESIZE_PERCENTAGE));
        return new Dimenssion(width, height);
    }
    
    private void assertRegion(Region region) {
		Assert.notNull(region, INVALID_REGION_MSG);
		validatorService.assertModel(region);
    }
    
    private void assertBlur(double alpha) {
        if (alpha < 5) {
            throw new BadRequestException(INVALID_ALPHA);
        }
    }

    private void assertHueValueGrayScaleMagicColor(int minHue, int maxHue) {
        if (minHue < 0 || minHue > 560) {
            throw new BadRequestException(INVALID_MIN_HUE_VALUE);
        }
        if (maxHue < 0 || maxHue > 560) {
            throw new BadRequestException(INVALID_MAX_HUE_VALUE);
        }
    }
}   