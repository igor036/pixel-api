package br.com.linecode.imgprocess.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import br.com.linecode.imgprocess.model.Dimenssion;
import br.com.linecode.imgprocess.model.HsvColor;
import br.com.linecode.imgprocess.model.Region;
import br.com.linecode.imgprocess.model.RgbColor;
import br.com.linecode.imgprocess.model.HsvColorChange;
import br.com.linecode.shared.excpetion.BadRequestException;

public abstract class MatUtil {

	private static final String INVALID_REGION_WIDTH = "the width of region are bigger than image width!";
	private static final String INVALID_REGION_HEIGHT = "the height of region are bigger than image height!";

	/**
	 * This method create a new image by original image with new size informeted.
	 * 
	 * @param image      {@link Mat}
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
	 * 
	 * Into put a widget in a image.
	 * 
	 * @param img        {@link Mat}
	 * @param widget     {@link Mat}
	 * @param sumHueZero {@link Mat} - define if should be apply overlay in a pixel
	 *                   with hsv hue value 0
	 * 
	 * @return {@link Mat}
	 */
	public static Mat overlay(Mat img, Mat widget, boolean sumHueZero) {

		Mat hsvWidget = new Mat();
		Mat overlayImage = new Mat(img.size(), CvType.CV_8UC3);
		Imgproc.cvtColor(widget, hsvWidget, Imgproc.COLOR_BGR2HSV);

		for (int x = 0; x < widget.rows(); x++) {
			for (int y = 0; y < widget.cols(); y++) {

				double[] pixel1 = img.get(x, y);
				double[] pixel2 = widget.get(x, y);

				if (pixel2.length == 4) {

					// PNG WITH OPACITY
					double alpha = pixel2[3] / 255f;
					for (int i = 0; i < 3; i++)
						pixel1[i] = pixel2[i] * alpha + pixel1[i] * (1 - alpha);

				} else {

					// PHOTO WITHOUT OPACITY
					if (!Arrays.equals(pixel1, pixel2)) {
						for (int i = 0; i < 3; i++) {
							if (hsvWidget.get(x, y)[0] != 0 || sumHueZero) {
								pixel1[i] = pixel2[i];
							}
						}
					}
				}

				overlayImage.put(x, y, pixel1);
			}
		}

		return overlayImage;
	}

	/**
	 * Convert an image to a blur image
	 * 
	 * @param image {@link Mat}
	 * @param alpha {@link double}
	 * 
	 * @return {@link Mat}
	 * @throws IOException
	 */
	public static Mat blur(Mat image, double alpha) throws IOException {
		Mat blur = new Mat();
		Imgproc.blur(image, blur, new Size(alpha, alpha));
		return blur;
	}

	/**
	 * Convert a region of image to a blur region
	 * 
	 * @param image  {@link Mat}
	 * @param alpha  {@link double}
	 * @param region {@link Region}
	 * 
	 * @return {@link Mat}
	 * @throws IOException
	 */
	public static Mat blur(Mat image, double alpha, Region region) throws IOException {

		Rect rect = regionToRect(region);
		assertRegion(image, region);

		Mat blur = copy(image);
		Imgproc.blur(blur.submat(rect), blur.submat(rect), new Size(alpha, alpha));

		return blur;
	}

	/**
	 * Convert an image to gray scale
	 * 
	 * @param image {@link Mat}
	 * @return {@link Mat}
	 * @throws IOException
	 */
	public static Mat grayScale(Mat image) throws IOException {

		Mat grayScale = new Mat();

		Imgproc.cvtColor(image, grayScale, Imgproc.COLOR_BGR2GRAY);
		Imgproc.cvtColor(grayScale, grayScale, Imgproc.COLOR_GRAY2BGR);

		return grayScale;
	}

	/***
	 * 
	 * Convert image a grayscale image preserving a color.
	 * 
	 * @param image  {@link Mat}
	 * @param minHue {@link int}
	 * @param maxHue {@link int}
	 * @return {@link Mat}
	 * @throws IOException
	 */
	public static Mat grayScaleMagicColor(Mat image, int minHue, int maxHue) throws IOException {

		Mat hsv = new Mat();
		Mat grayScale = grayScale(image);

		Imgproc.cvtColor(image, hsv, Imgproc.COLOR_BGR2HSV);

		for (int x = 0; x < hsv.rows(); x++) {
			for (int y = 0; y < hsv.cols(); y++) {

				double[] hsvPixel = hsv.get(x, y);
				if (hsvPixel[0] >= minHue && hsvPixel[0] <= maxHue) {
					grayScale.put(x, y, image.get(x, y));
				}
			}
		}

		return grayScale;
	}

	/**
	 * Convert image to a sepia image
	 * 
	 * @param image {@link Mat}
	 * @return {@link Mat}
	 * @throws IOException
	 */
	public static Mat sepia(Mat image) throws IOException {

		Mat sepia = copy(image);

		for (int x = 0; x < sepia.rows(); x++) {
			for (int y = 0; y < sepia.cols(); y++) {

				//@formatter:off
                double R = sepia.get(x, y)[2], 
                       G = sepia.get(x, y)[1], 
                       B = sepia.get(x, y)[0];
                
                double[] pixel = {
                    0.272*R + 0.534*G + 0.131*B,
                    0.349*R + 0.686*G + 0.168*B,
                    0.393*R + 0.769*G + 0.189*B
				};
				//@formatter:on

				sepia.put(x, y, pixel);
			}
		}

		return sepia;
	}

	/**
	 * Convert a {@link Mat} to {@link byte[]}
	 * 
	 * this method is using when this class nedd return a blob from process image.
	 * 
	 * @param image     {@link Mat}
	 * @param extension {@link String} extension from original file
	 * @return {@link byte[]}
	 * 
	 * @throws IOException
	 */
	public static byte[] getBlob(Mat image, String extension) {

		MatOfByte bytes = new MatOfByte();
		Imgcodecs.imencode(".".concat(extension), image, bytes);

		return bytes.toArray();
	}

	/**
	 * Convert a {@link byte[]} to {@link Mat}
	 * 
	 * this method is using when this class get a {@link blobImage} and need convert
	 * that to {@link Mat} object for image process.
	 * 
	 * @param blobImage {@link byte[]}
	 * @return {@link Mat}
	 * 
	 * @throws IOException
	 */
	public static Mat getMat(byte[] blobImage) throws IOException {
		return Imgcodecs.imdecode(new MatOfByte(blobImage), Imgcodecs.IMREAD_UNCHANGED);
	}

	/**
	 * Increase width and height in percentage value
	 * 
	 * @param image      {@link Mat}
	 * @param percentage {@link double}
	 * @return {@link Mat}
	 */
	public static Mat increaseSize(Mat image, double percentage) throws IOException {
		int width = (int) (image.width() + (image.width() * percentage));
		int height = (int) (image.height() + (image.height() * percentage));
		return MatUtil.resize(image, new Dimenssion(width, height));
	}

	public static Mat decreaseSize(Mat image, double percentage) throws IOException {
		int width = (int) (image.width() - (image.width() * percentage));
		int height = (int) (image.height() - (image.height() * percentage));
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

		int width = foreground.cols() + x;
		int height = foreground.rows() + y;

		Point a = new Point(x, y);
		Point b = new Point(width, height);

		foreground.copyTo(image.submat(new Rect(a, b)));
		return image;
	}

	/**
	 * Create an image by color rgb and size.
	 * 
	 * @param color {@link Mat}
	 * @param size  {@link Mat}
	 * @return {@link Mat}
	 * 
	 */
	public static Mat getRGBMat(RgbColor color, Dimenssion size) {
		Scalar scalar = new Scalar(color.getB(), color.getG(), color.getR());
		return new Mat(size.getHeight(), size.getWidth(), CvType.CV_8UC3, scalar);
	}

	/**
	 * Return the color that more present in the image in <b>RGB<b> space.
	 * 
	 * @param image
	 * @return {@link RgbColor}
	 */
	public static RgbColor findPrincipalColor(Mat image) {

		Mat hsv = new Mat();
		Map<HsvColor, Integer> hueMap = new HashMap<>();

		Imgproc.cvtColor(image, hsv, Imgproc.COLOR_BGR2HSV);

		for (int x = 0; x < hsv.rows(); x++) {
			for (int y = 0; y < hsv.cols(); y++) {

				double[] pixel = hsv.get(x, y);
				HsvColor hsvColor = new HsvColor((int) pixel[0], (int) pixel[1], (int) pixel[2]);
				hueMap.put(hsvColor, hueMap.getOrDefault(hsvColor, 0) + 1);

			}
		}

		List<Map.Entry<HsvColor, Integer>> entrys = new ArrayList<>(hueMap.entrySet());
		entrys.sort((Map.Entry<HsvColor, Integer> a, Map.Entry<HsvColor, Integer> b) -> Integer.compare(a.getValue(),
				b.getValue()));

		HsvColor hsvColor = entrys.get(0).getKey();
		Mat hsvPixelMat = new Mat(1, 1, CvType.CV_8UC3, new Scalar(hsvColor.getH(), hsvColor.getS(), hsvColor.getV()));
		Mat rgbPixelMat = new Mat();

		Imgproc.cvtColor(hsvPixelMat, rgbPixelMat, Imgproc.COLOR_HSV2RGB);

		double[] rgbPixel = rgbPixelMat.get(0, 0);
		RgbColor rgbColor = new RgbColor((int) rgbPixel[0], (int) rgbPixel[1], (int) rgbPixel[2]);

		return rgbColor;
	}

	/**
	 * Change the brightness of image.
	 * 
	 * @param image {@link Mat}
	 * @param alpha {@link double}
	 * @return {@link Mat}
	 */
	public static Mat brightness(Mat image, double alpha) {

		Mat hsv = new Mat();
		Mat brightness = new Mat();
		Imgproc.cvtColor(image, hsv, Imgproc.COLOR_BGR2HSV);

		for (int x = 0; x < hsv.rows(); x++) {
			for (int y = 0; y < hsv.cols(); y++) {

				double[] pixel = hsv.get(x, y);
				pixel[2] *= alpha;
				hsv.put(x, y, pixel);

			}
		}

		Imgproc.cvtColor(hsv, brightness, Imgproc.COLOR_HSV2BGR);
		return brightness;
	}

	/**
	 * Change the saturagion of image.
	 * 
	 * @param image {@link Mat}
	 * @param alpha {@link double}
	 * @return {@link Mat}
	 */
	public static Mat saturation(Mat image, double alpha) {

		Mat hsv = new Mat();
		Mat saturation = new Mat();
		Imgproc.cvtColor(image, hsv, Imgproc.COLOR_BGR2HSV);

		for (int x = 0; x < hsv.rows(); x++) {
			for (int y = 0; y < hsv.cols(); y++) {

				double[] pixel = hsv.get(x, y);
				pixel[1] *= alpha;
				hsv.put(x, y, pixel);

			}
		}

		Imgproc.cvtColor(hsv, saturation, Imgproc.COLOR_HSV2BGR);
		return saturation;
	}

	/**
	 * Change contrast and brightness of image
	 * 
	 * @param image {@link Mat}
	 * @param alpha {@link double} - contrast value
	 * @param beta  {@link double} - brigthness value
	 * @return {@link Mat}
	 */
	public static Mat contrastAndBrightness(Mat image, double alpha, double beta) {

		Mat contrastAndBrightness = copy(image);

		for (int x = 0; x < contrastAndBrightness.rows(); x++) {
			for (int y = 0; y < contrastAndBrightness.cols(); y++) {

				double[] pixel = contrastAndBrightness.get(x, y);
				for (int i = 0; i < 3; i++)
					pixel[i] = (int) (alpha * (pixel[i] + beta));

				contrastAndBrightness.put(x, y, pixel);
			}
		}

		return contrastAndBrightness;
	}

	/**
	 * 
	 * Chamge RGB color range for new RGB color.
	 * 
	 * @param image       {@link Mat}
	 * @param colorChange {@link RgbColorChange}
	 * @return {@link Mat}
	 */
	public static Mat colorChange(Mat image, HsvColorChange colorChange) {

		Mat changedColorImg = new Mat();
		Imgproc.cvtColor(image, changedColorImg, Imgproc.COLOR_BGR2HSV);

		for (int x = 0; x < changedColorImg.rows(); x++) {
			for (int y = 0; y < changedColorImg.cols(); y++) {

				double[] hsvPixel = changedColorImg.get(x, y);

				if (hsvPixel[0] >= colorChange.getMinHue() && hsvPixel[0] <= colorChange.getMaxHue()) {
					hsvPixel[0] = colorChange.getNewHue();
					hsvPixel[1] += colorChange.getSaturationAdjustment();
					hsvPixel[2] += colorChange.getBrightnessAdjustment();
					changedColorImg.put(x, y, hsvPixel);
				}
			}
		}

		Imgproc.cvtColor(changedColorImg, changedColorImg, Imgproc.COLOR_HSV2BGR);

		return changedColorImg;
	}

	/**
	 * 
	 * Apply chromakey effect.
	 * 
	 * Change all Hue(HSV) value from foreground image by content of backgorund image.
	 * 
	 * @param foreground {@link Mat} - image with a background in the color range that you need to change by the content background.
	 * @param background {@link Mat} - background image.
	 * @param minHe {@link Integer} - min hue value.
	 * @param maxHue {@link Integer} - max hue value.
	 * 
	 * @return {@link Mat}
	 */
	public static Mat chromaKey(Mat foreground, Mat background, int minHe, int maxHue) {

		Mat hsvImage = new Mat(foreground.size(), CvType.CV_8UC3);
		Mat chromaKeyImage = copy(foreground);

		Imgproc.cvtColor(foreground, hsvImage, Imgproc.COLOR_BGR2HSV);
		Imgproc.resize(background, background, foreground.size());

		for (int x = 0; x < hsvImage.rows(); x++) {
			for (int y = 0; y < hsvImage.cols(); y++) {

				double[] hsvPixel = hsvImage.get(x, y);
				if (hsvPixel[0] >= minHe && hsvPixel[0] <= maxHue) {
					chromaKeyImage.put(x, y, background.get(x, y));
				}
			} 	
		} 

		return chromaKeyImage;
	}

	public static Mat copy(Mat image) {
		Mat copy = new Mat();
		image.copyTo(copy);
		return copy;
	}

	public static Rect regionToRect(Region region) {
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
