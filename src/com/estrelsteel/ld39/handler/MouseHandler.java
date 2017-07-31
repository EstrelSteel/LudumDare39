package com.estrelsteel.ld39.handler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {

	private double x;
	private double y;
	private boolean avaliable;
	private boolean rightClick;
	
	public MouseHandler() {
		this.x = -1;
		this.y = -1;
		this.avaliable = false;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public boolean isAvaliable() {
		return avaliable;
	}
	
	public boolean isRightClick() {
		return rightClick;
	}
	
	public void setAvaliable(boolean avaliable) {
		this.avaliable = avaliable;
	}
	
	public void setRightClick(boolean rightClick) {
		this.rightClick = rightClick;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON2 || e.getButton() == MouseEvent.BUTTON3) {
			avaliable = true;
			x = e.getX();
			y = e.getY();
			if(e.getButton() == MouseEvent.BUTTON2 || e.getButton() == MouseEvent.BUTTON3) {
				rightClick = true;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON2 || e.getButton() == MouseEvent.BUTTON3) {
			avaliable = false;
			x = -1;
			y = -1;
			rightClick = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
