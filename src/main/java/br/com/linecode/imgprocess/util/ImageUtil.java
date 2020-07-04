package br.com.linecode.imgprocess.util;

import java.io.IOException;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs; 
import org.opencv.imgproc.Imgproc;

import br.com.linecode.imgprocess.model.Dimenssion;
import br.com.linecode.imgprocess.model.Region;

public abstract class ImageUtil {
	
	
	private static final String INVALID_CROP_SIZE_MSG = "The informed size are bigger of original size.";
	
	/**
	 * This method create a new image by original image with new size informeted.
	 * 
	 * @param blobImage {@link byte[]}
	 * @param extension {@link String} extension from original file
	 * @param dimenssion (new size) {@link Dimenssion}
	 * 
	 * @return {@link byte[]}
	 * 
	 * @throws IOException
	 */
	public static byte[] resize(byte[] blobImage, String extension, Dimenssion dimenssion) throws IOException {
		
		Mat image = getMat(blobImage);
		Size size = new Size(dimenssion.getWidth(), dimenssion.getHeight());
		Imgproc.resize(image, image, size);
		
		return getBlob(image, extension);
	}
	
	/**
	 * This method genered a sub image from original image by a region imformed.
	 * 
	 * @param blobImage {@link byte[]}
	 * @param extension {@link String} extension from original file
	 * @param region {@link Region}
	 * 
	 * @return {@link byte[]}
	 * 
	 * @throws IOException, IllegalArgumentException
	 */
	public static byte[] crop(byte[] blobImage, String extension, Region region) throws IOException, IllegalArgumentException {
		
		Mat image = getMat(blobImage);
		assertCrop(image, region);
		Rect rect = new Rect(region.getPointX(), region.getPointY(), region.getWidth(), region.getHeight());
		image = image.submat(rect);
		
		return getBlob(image, extension);
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
	private static Mat getMat(byte[] blobImage) throws IOException {
		return Imgcodecs.imdecode(new MatOfByte(blobImage), Imgcodecs.IMREAD_UNCHANGED);
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
	private static byte[] getBlob(Mat image, String extension) {
		
		MatOfByte bytes = new MatOfByte();
		Imgcodecs.imencode(".".concat(extension), image, bytes);
		
		return bytes.toArray();
	}
	
	private static void assertCrop(Mat image, Region region) {
		
		int rectWidth = region.getPointX() + region.getWidth();
		int rectHeight = region.getPointY() + region.getHeight();
		
		if (rectWidth > image.cols() || rectHeight > image.rows()) {
			throw new IllegalArgumentException(INVALID_CROP_SIZE_MSG);
		}
	}
}
