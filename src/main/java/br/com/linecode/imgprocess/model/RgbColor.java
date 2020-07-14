package br.com.linecode.imgprocess.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class RgbColor {
    
    @Min(value = 0, message = "Min value for R is 0.")
    @Max(value = 255, message = "Max value for R is 255.")
    private int r;

    @Min(value = 0, message = "Min value for G is 0.")
    @Max(value = 255, message = "Max value for G is 255.")
    private int g;

    @Min(value = 0, message = "Min value for B is 0.")
    @Max(value = 255, message = "Max value for B is 255.")
    private int b;

    @SuppressWarnings("unused")
	private RgbColor() {
		//constructor for jackson
	}

    public RgbColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }
}