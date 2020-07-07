package br.com.linecode.imgprocess.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.linecode.imgprocess.model.Region;
import br.com.linecode.imgprocess.service.FilterService;
import br.com.linecode.shared.util.HttpServletResponseUtil;

@RestController
@RequestMapping("filter")
public class FilterController {
    
    @Autowired
    private FilterService filtlerService;

    @GetMapping("blur")
	public void blur(@RequestPart MultipartFile file, double alpha, HttpServletResponse response)  throws IOException {
		HttpServletResponseUtil.writeNewImageInResponse(file, filtlerService.blur(file, alpha), response);
    }
    
    @GetMapping("blur/region")
	public void blur(@RequestPart MultipartFile file, double alpha, Region region, HttpServletResponse response)  throws IOException {
		HttpServletResponseUtil.writeNewImageInResponse(file, filtlerService.blur(file, alpha, region), response);
    }
    
    @GetMapping("grayScale")
	public void grayScale(@RequestPart MultipartFile file, HttpServletResponse response)  throws IOException {
		HttpServletResponseUtil.writeNewImageInResponse(file, filtlerService.grayScale(file), response);
	}
}