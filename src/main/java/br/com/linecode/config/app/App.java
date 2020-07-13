package br.com.linecode.config.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import br.com.linecode.shared.util.OpenCVUtil;

@SpringBootApplication
@ComponentScan(basePackages = "br.com.linecode")
public class App  {
	
    public static void main( String[] args ) {
        OpenCVUtil.initOpenCV();    
    	SpringApplication.run(App.class, args);
    }
}
