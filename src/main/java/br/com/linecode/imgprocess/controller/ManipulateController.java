package br.com.linecode.imgprocess.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.linecode.imgprocess.model.Dimenssion;
import br.com.linecode.imgprocess.model.Region;
import br.com.linecode.imgprocess.service.ManipulateService;
import br.com.linecode.shared.util.HttpServletResponseUtil;

@RestController
@RequestMapping("manipulate")
public class ManipulateController {
	
	@Autowired
	private ManipulateService manipulateService;
	
	@GetMapping("resize")
	public void resize(@RequestPart MultipartFile file,  Dimenssion dimenssion, HttpServletResponse response) throws IOException {	
		HttpServletResponseUtil.writeNewImageInResponse(file, manipulateService.resize(file, dimenssion), response);
	}
	
	@GetMapping("crop")
	public void crop(@RequestPart MultipartFile file, Region region, HttpServletResponse response) throws IOException {
		HttpServletResponseUtil.writeNewImageInResponse(file, manipulateService.crop(file, region), response);
	}
}
