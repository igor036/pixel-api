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
import br.com.linecode.imgprocess.model.RgbColor;
import br.com.linecode.imgprocess.service.FilterService;
import br.com.linecode.shared.util.HttpServletResponseUtil;

@RestController
@RequestMapping("filter")
public class FilterController {
    
    @Autowired
    private FilterService filtlerService;

    @GetMapping("sepia")
    public void sepia(@RequestPart MultipartFile file, HttpServletResponse response)  throws IOException {
      HttpServletResponseUtil.writeNewImageInResponse(file, filtlerService.sepia(file), response);
    }

    @GetMapping("sepia/region")
    public void sepia(@RequestPart MultipartFile file, Region region, HttpServletResponse response)  throws IOException {
      HttpServletResponseUtil.writeNewImageInResponse(file, filtlerService.sepia(file, region), response);
    }

    @GetMapping("blur")
    public void blur(@RequestPart MultipartFile file, double alpha, HttpServletResponse response)  throws IOException {
      HttpServletResponseUtil.writeNewImageInResponse(file, filtlerService.blur(file, alpha), response);
    }
      
    @GetMapping("blur/region")
    public void blur(@RequestPart MultipartFile file, double alpha, Region region, HttpServletResponse response)  throws IOException {
      HttpServletResponseUtil.writeNewImageInResponse(file, filtlerService.blur(file, alpha, region), response);
    }

    @GetMapping("blur/mold")
    public void blurMold(@RequestPart MultipartFile file, double alpha, HttpServletResponse response)  throws IOException {
      HttpServletResponseUtil.writeNewImageInResponse(file, filtlerService.blurMold(file, alpha), response);
    } 
      
    @GetMapping("grayScale")
    public void grayScale(@RequestPart MultipartFile file, HttpServletResponse response)  throws IOException {
      HttpServletResponseUtil.writeNewImageInResponse(file, filtlerService.grayScale(file), response);
    }
      
    @GetMapping("grayScale/region")
    public void grayScale(@RequestPart MultipartFile file, Region region, HttpServletResponse response)  throws IOException {
      HttpServletResponseUtil.writeNewImageInResponse(file, filtlerService.grayScale(file, region), response);
    }

    @GetMapping("grayScale/mold")
    public void grayScaleMold(@RequestPart MultipartFile file, HttpServletResponse response)  throws IOException {
      HttpServletResponseUtil.writeNewImageInResponse(file, filtlerService.grayScaleMold(file), response);
    }

    @GetMapping("rgb/mold")
    public void rgbMold(@RequestPart MultipartFile file, RgbColor color, HttpServletResponse response)  throws IOException {
      HttpServletResponseUtil.writeNewImageInResponse(file, filtlerService.rgbMold(file, color), response);
    }

    @GetMapping("rgb/mold/principal-color")
    public void rgbMoldPrincipalColor(@RequestPart MultipartFile file, HttpServletResponse response)  throws IOException {
      HttpServletResponseUtil.writeNewImageInResponse(file, filtlerService.rgbMoldPrincipalColor(file), response);
    }
}