package br.com.linecode.imgprocess.model;

import javax.validation.constraints.Min;

public class Region {
	
	@Min(value = 0, message = "invalid x.")
	private int pointX;
	
	@Min(value = 0, message = "invalid y.")
	private int pointY;
	
	@Min(value = 10, message = "invalid width.")
	private int width;
	
	@Min(value = 10, message = "invalid height.")
	private int height;
	
	@SuppressWarnings("unused")
	private Region() {
		//constructor for jackson
	}
	
	public Region(int pointX, int pointY, int width, int height) {
		this.pointX = pointX;
		this.pointY = pointY;
		this.width = width;
		this.height = height;
	}
	
	public int getPointX() {
		return pointX;
	}
	
	public int getPointY() {
		return pointY;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
