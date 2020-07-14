package br.com.linecode.imgprocess.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class HsvColor {
    
    @Min(value = 0, message = "Min value for R is 0.")
    @Max(value = 255, message = "Max value for R is 255.")
    private int h;

    @Min(value = 0, message = "Min value for G is 0.")
    @Max(value = 255, message = "Max value for G is 255.")
    private int s;

    @Min(value = 0, message = "Min value for B is 0.")
    @Max(value = 255, message = "Max value for B is 255.")
    private int v;

    @SuppressWarnings("unused")
	private HsvColor() {
		//constructor for jackson
	}

    public HsvColor(int h, int s, int v) {
        this.h = h;
        this.s = s;
        this.v = v;
    }
    
    
    public int getH() {
        return h;
    }

    public int getS() {
        return s;
    }

    public int getV() {
        return v;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + h;
        result = prime * result + s;
        result = prime * result + v;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HsvColor other = (HsvColor) obj;
        if (h != other.h)
            return false;
        if (s != other.s)
            return false;
        if (v != other.v)
            return false;
        return true;
    }    
}