package br.com.linecode.imgprocess.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class HsvColorChange {

    @Min(value = 0, message = "Min value for porp minHue is 0.")
    @Max(value = 179, message = "max value for porp minHue is 179.")
    private int minHue;

    @Min(value = 0, message = "Min value for porp maxHue is 0.")
    @Max(value = 179, message = "max value for porp maxHue is 179.")
    private int maxHue;

    @Min(value = 0, message = "Min value for porp newHue is 0.")
    @Max(value = 179, message = "max value for porp newHue is 179.")
    private int newHue;

    private double saturationAdjustment;
    private double brightnessAdjustment;

    @SuppressWarnings("unused")
    private HsvColorChange() {
        // constructor for jackson
    }

    public HsvColorChange(int minHue, int maxHue, int newHue, double saturationAdjustment,
            double brightnessAdjustment) {
        this.minHue = minHue;
        this.maxHue = maxHue;
        this.newHue = newHue;
        this.saturationAdjustment = saturationAdjustment;
        this.brightnessAdjustment = brightnessAdjustment;
    }

    public int getMinHue() {
        return minHue;
    }

    public int getMaxHue() {
        return maxHue;
    }

    public int getNewHue() {
        return newHue;
    }

    public double getSaturationAdjustment() {
        return saturationAdjustment;
    }

    public double getBrightnessAdjustment() {
        return brightnessAdjustment;
    }
}