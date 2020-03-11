package br.com.linecode.imgprocess.model;

import javax.validation.constraints.Min;

public class Dimenssion {
	
	@Min(value = 10, message = "invalid width.")
	private int width;
	
	@Min(value = 10, message = "invalid height.")
	private int height;
	
	@SuppressWarnings("unused")
	private Dimenssion() {
		// constructor for jacksonW
	}
	
	public Dimenssion(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
}
