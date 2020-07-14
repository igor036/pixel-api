package br.com.linecode.imgprocess.util;

import java.io.IOException;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import br.com.linecode.imgprocess.model.Dimenssion;
import br.com.linecode.imgprocess.model.Region;
import br.com.linecode.shared.excpetion.BadRequestException;


public abstract class MatUtil {
	
	
	private static final String INVALID_REGION_WIDTH = "the width of region are bigger than image width!";
	private static final String INVALID_REGION_HEIGHT = "the height of region are bigger than image height!";
	
	/**
	 * This method create a new image by original image with new size informeted.
	 * 
	 * @param image 	 {@link Mat}
	 * @param dimenssion (new size) {@link Dimenssion}
	 * 
	 * @return {@link Mat}
	 * 
	 * @throws IOException
	 */
	public static Mat resize(Mat image, Dimenssion dimenssion) throws IOException {
		Size size = new Size(dimenssion.getWidth(), dimenssion.getHeight());
		Imgproc.resize(image, image, size);	
		return image;
	}
	
	/**
	 * This method genered a sub image from original image by a region imformed.
	 * 
	 * @param image  {@link Mat}
	 * @param region {@link Region}
	 * 
	 * @return {@link Mat}
	 * 
	 * @throws IOException, IllegalArgumentException
	 */
	public static Mat crop(Mat image, Region region) throws IOException, IllegalArgumentException {
		assertRegion(image, region);
		return image.submat(regionToRect(region));
	}

	/**
	 * Convert an image to a blur image
	 *  
	 *  @param image 	 {@link Mat}
	 *  @param alpha 	 {@link double}
	 * 
	 *  @return {@link Mat}	   
	 *  @throws IOException
	 */
	public static Mat blur(Mat image, double alpha) throws IOException {
		Mat blur = new Mat();
		Imgproc.blur(image, blur, new Size(alpha, alpha));
		return blur;
	}

	/**
	 * 	Convert a region of image to a blur region
	 *  
	 *  @param image 	 {@link Mat}
	 *  @param alpha 	 {@link double}
	 *  @param region 	 {@link Region}
	 * 
	 *  @return {@link Mat}	   
	 *  @throws IOException
	 */
	public static Mat blur(Mat image, double alpha, Region region) throws IOException {
		
		Rect rect = regionToRect(region);
		assertRegion(image, region);

		Mat blur = copy(image);
		Imgproc.blur(blur.submat(rect), blur.submat(rect), new Size(alpha, alpha));
		
		return blur;		
	}
	
	/**
	 * 	Convert an image to gray scale
	 *  
	 *  @param image {@link Mat}
	 *  @return      {@link Mat}	   
	 *  @throws IOException
	 */
	public static Mat grayScale(Mat image) throws IOException {
		
		Mat grayScale = new Mat();
		
		Imgproc.cvtColor(image, grayScale, Imgproc.COLOR_BGR2GRAY);
		Imgproc.cvtColor(grayScale, grayScale, Imgproc.COLOR_GRAY2BGR);

		return grayScale;
	}

	/**
	 * 	Convert a region of image to a grayscale region
	 * 
	 *  @param image  {@link Mat}
	 *  @param region {@link Region}
	 *  @return       {@link Mat}	   
	 *  @throws IOException
	 */
	public static Mat grayScale(Mat image, Region region) throws IOException {

		assertRegion(image, region);

		Rect rect = regionToRect(region);
		Mat processedImage = copy(image);
		Mat grayScaleRegion = image.submat(rect);

		Imgproc.cvtColor(grayScaleRegion, grayScaleRegion, Imgproc.COLOR_BGR2GRAY);
		Imgproc.cvtColor(grayScaleRegion, grayScaleRegion, Imgproc.COLOR_GRAY2BGR);

		grayScaleRegion.copyTo(processedImage.submat(rect));
		return processedImage;

	}

	/**
	 * Convert a {@link Mat} to {@link byte[]}
	 * 
	 *  this method is using when this class nedd return a blob from process image.
	 *  
	 *  @param image {@link Mat}
	 *  @param extension {@link String} extension from original file
	 *  @return {@link byte[]}
	 *  
	 *  @throws IOException
	 */
	public static byte[] getBlob(Mat image, String extension) {
		
		MatOfByte bytes = new MatOfByte();
		Imgcodecs.imencode(".".concat(extension), image, bytes);
		
		return bytes.toArray();
	}

	/**
	 * Convert a {@link byte[]} to {@link Mat}
	 * 
	 *  this method is using when this class get a {@link blobImage} and
	 *  need convert that to {@link Mat} object for image process.
	 *  
	 *  @param blobImage {@link byte[]}
	 *  @return {@link Mat}
	 *  
	 *  @throws IOException
	 */
	public static Mat getMat(byte[] blobImage) throws IOException {
		return Imgcodecs.imdecode(new MatOfByte(blobImage), Imgcodecs.IMREAD_UNCHANGED);
	}

	/**
	 * Increase width and height in percentage value
	 * 
	 * @param image 	 {@link Mat} 
	 * @param percentage {@link double} 
	 * @return {@link Mat} 
	 */
	public static Mat increaseSize(Mat image, double percentage) throws IOException {
        int width = (int)(image.width() + (image.width() * percentage));
        int height = (int)(image.height() + (image.height()* percentage));
        return MatUtil.resize(image, new Dimenssion(width, height));
	}
	
	public static Mat decreaseSize(Mat image, double percentage) throws IOException {
		int width = (int)(image.width() - (image.width() * percentage));
        int height = (int)(image.height() - (image.height()* percentage));
        return MatUtil.resize(image, new Dimenssion(width, height));
	}

	/**
	 * put a foreground on a background
	 * 
	 * @param background {@link Mat}
	 * @param foreground {@link Mat}
	 * @return {@link Mat}
	 */
	public static Mat putForeground(Mat background, Mat foreground) {

		Mat image = copy(background);
		
		int x = (Math.abs(background.cols() - foreground.cols())) / 2;
		int y = (Math.abs(background.rows() - foreground.rows())) / 2;

		int width = foreground.cols()  + x;
		int height = foreground.rows() + y;
		
		Point a = new Point(x, y);
		Point b = new Point(width, height);

		foreground.copyTo(image.submat(new Rect(a, b)));
		return image;
	}

	public static Mat copy(Mat image) {
		Mat copy = new Mat();
		image.copyTo(copy);
		return copy;
	}

	private static Rect regionToRect(Region region) {
		Point a = new Point(region.getPointX(), region.getPointY());
		Point b = new Point(region.getPointX() + region.getWidth(), region.getPointY() + region.getHeight());
		return new Rect(a, b);
	}
	
	private static void assertRegion(Mat image, Region region) {
		
		int rectWidth = region.getPointX() + region.getWidth();
		int rectHeight = region.getPointY() + region.getHeight();
		
		if (rectWidth > image.cols()) {
			throw new BadRequestException(INVALID_REGION_WIDTH);
		}

		if (rectHeight > image.rows()) {
			throw new BadRequestException(INVALID_REGION_HEIGHT);
		}
	}
}
