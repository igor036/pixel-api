package br.com.linecode.config.app;

import org.opencv.core.Core;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "br.com.linecode")
public class App  {
	
    public static void main( String[] args ) {
    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    	SpringApplication.run(App.class, args);
    }
}
