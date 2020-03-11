package br.com.linecode.shared.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

public abstract class HttpServletResponseUtil {
	
	/**
	 * write new image in response of request.
	 * 
	 *  @param oldImage {@link MultipartFile} image from request.
	 *  @param blobNewImage {@link byte[]} blob of new image.
	 *  @param response {@link HttpServletResponse}
	 *  
	 *  @throws IOException
	 */
	public static void writeNewImageInResponse(MultipartFile oldImage, byte[] blobNewImage, HttpServletResponse response) throws IOException {
		response.setContentType(oldImage.getContentType());
		response.setHeader("Content-disposition", "attachment; filename="+oldImage.getOriginalFilename());
		response.setContentLengthLong(blobNewImage.length);
		response.getOutputStream().write(blobNewImage);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
}
