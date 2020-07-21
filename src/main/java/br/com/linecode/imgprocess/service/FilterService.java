package br.com.linecode.imgprocess.service;

import java.io.IOException;

import org.opencv.core.Mat;
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

    @Autowired
	private ValidatorService validatorService; 

    //TODO implementar teste
    public byte[] sepia(MultipartFile file) throws IOException {

        MultipartFileUtil.assertFile(file);

        Mat image = MatUtil.getMat(file.getBytes());
        Mat sepia = MatUtil.sepia(image);
        String extension = MultipartFileUtil.getExtension(file);

        return MatUtil.getBlob(sepia, extension);

    }

    public byte[] blur(MultipartFile file, double alpha) throws IOException {
        
        MultipartFileUtil.assertFile(file);
        
        if (alpha >= 5) {

            Mat image = MatUtil.getMat(file.getBytes());
            Mat blur = MatUtil.blur(image, alpha);
            String extension = MultipartFileUtil.getExtension(file);

            return MatUtil.getBlob(blur, extension);
            
        }

        throw new BadRequestException(INVALID_ALPHA);
    }
    
    public byte[] blur(MultipartFile file, double alpha, Region region) throws IOException {
        
        MultipartFileUtil.assertFile(file);
        assertRegion(region);

        if (alpha >= 5) {

            Mat image = MatUtil.getMat(file.getBytes());
            Mat blur = MatUtil.blur(image, alpha, region);
            String extension = MultipartFileUtil.getExtension(file);

            return MatUtil.getBlob(blur, extension);
        }

        throw new BadRequestException(INVALID_ALPHA);
    }

    public byte[] blurMold(MultipartFile file, double alpha) throws IOException{

        MultipartFileUtil.assertFile(file);
        
        Mat foreground = MatUtil.getMat(file.getBytes());
        Mat background = MatUtil.copy(foreground);
        String extension = MultipartFileUtil.getExtension(file);

        alpha = alpha > 5 ? alpha : DEFAULT_BLUR_ALPHA;

        background = MatUtil.blur(foreground, alpha);
        background = MatUtil.increaseSize(background, DEFAULTL_RESIZE_PERCENTAGE);
        foreground = MatUtil.decreaseSize(foreground, DEFAULTL_RESIZE_PERCENTAGE);

        Mat blurMold = MatUtil.putForeground(background, foreground);

        return MatUtil.getBlob(blurMold, extension);
    }

    public byte[] grayScale(MultipartFile file) throws IOException {
        
        MultipartFileUtil.assertFile(file);
        
        Mat image = MatUtil.getMat(file.getBytes());
        Mat grayScale = MatUtil.grayScale(image);
        String extension = MultipartFileUtil.getExtension(file);

        return MatUtil.getBlob(grayScale, extension);
    }

    public byte[] grayScale(MultipartFile file, Region region) throws IOException {
        
        MultipartFileUtil.assertFile(file);
        assertRegion(region);
        
        Mat image = MatUtil.getMat(file.getBytes());
        Mat grayScale = MatUtil.grayScale(image, region);
        String extension = MultipartFileUtil.getExtension(file);

        return MatUtil.getBlob(grayScale, extension);

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
}   