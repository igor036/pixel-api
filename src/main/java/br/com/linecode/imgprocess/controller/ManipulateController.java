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
import br.com.linecode.imgprocess.model.HsvColorChange;
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

	@GetMapping("brightness")
	public void brightness(@RequestPart MultipartFile file, double alpha, HttpServletResponse response)
			throws IOException {
		HttpServletResponseUtil.writeNewImageInResponse(file, manipulateService.brightness(file, alpha), response);
	}

	@GetMapping("brightness/region")
	public void brightness(@RequestPart MultipartFile file, double alpha, Region region, HttpServletResponse response)
			throws IOException {
		HttpServletResponseUtil.writeNewImageInResponse(file, manipulateService.brightness(file, alpha, region), response);
	}

	@GetMapping("saturation")
	public void saturation(@RequestPart MultipartFile file, double alpha, HttpServletResponse response)
			throws IOException {
		HttpServletResponseUtil.writeNewImageInResponse(file, manipulateService.saturation(file, alpha), response);
	}

	@GetMapping("saturation/region")
	public void saturation(@RequestPart MultipartFile file, double alpha, Region region, HttpServletResponse response)
			throws IOException {
		HttpServletResponseUtil.writeNewImageInResponse(file, manipulateService.saturation(file, alpha, region), response);
	}

	@GetMapping("contrast-brightness")
	public void contrastAndBrightness(@RequestPart MultipartFile file, double alpha, double beta, HttpServletResponse response)
			throws IOException {
		HttpServletResponseUtil.writeNewImageInResponse(file, manipulateService.contrastAndBrightness(file, alpha, beta), response);
	}

	@GetMapping("contrast-brightness/region")
	public void contrastAndBrightness(@RequestPart MultipartFile file, double alpha, double beta, Region region, HttpServletResponse response)
			throws IOException {
		HttpServletResponseUtil.writeNewImageInResponse(file, manipulateService.contrastAndBrightness(file, alpha, beta, region), response);
	}

	@GetMapping("color/change")
	public void colorChange(@RequestPart MultipartFile file, HsvColorChange colorChange, HttpServletResponse response)
			throws IOException {
		HttpServletResponseUtil.writeNewImageInResponse(file, manipulateService.colorChange(file, colorChange), response);
	}

	@GetMapping("color/change/region")
	public void colorChange(@RequestPart MultipartFile file, HsvColorChange colorChange, Region region, HttpServletResponse response)
			throws IOException {
		HttpServletResponseUtil.writeNewImageInResponse(file, manipulateService.colorChange(file, colorChange, region), response);
	}
}
