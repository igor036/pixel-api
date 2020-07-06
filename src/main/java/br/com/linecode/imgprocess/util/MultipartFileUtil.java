package br.com.linecode.imgprocess.util;

import org.springframework.web.multipart.MultipartFile;

import br.com.linecode.shared.excpetion.BadRequestException;

public abstract class MultipartFileUtil {
	
	private static final String ACCEPTED_EXTENSIONS = "PNG/JPG/JPEG";
	
	public static final String INVALID_FILE_MESSAGE = "Inform an image file, extensions accepted (.png, .jpg, .jpeg).";
	public static final String EMPTY_FILE_MESSAGE = "The informed file are empty";
	
	public static void assertFile(MultipartFile file) {
		assertNotNull(file);
		assertNotEmpty(file);
		assertImageFile(file);
	}
	
	public static String getExtension(MultipartFile file) {
		return file.getOriginalFilename().split("\\.")[1];
	}
	
	private static void assertNotNull(MultipartFile file)  {
		if (file == null) {
			throw new BadRequestException(INVALID_FILE_MESSAGE);
		}
	}
	
	private static void assertNotEmpty(MultipartFile file) {
		if (file.getSize() == 0L)  {
			throw new BadRequestException(EMPTY_FILE_MESSAGE);
		}
	}
	
	private static void assertImageFile(MultipartFile file) {
		String extension =  getExtension(file).toUpperCase();
		if (!ACCEPTED_EXTENSIONS.contains(extension)) {
			throw new BadRequestException(INVALID_FILE_MESSAGE);
		}
	}
}
