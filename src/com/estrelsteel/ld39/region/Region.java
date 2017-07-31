package com.estrelsteel.ld39.region;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.Image;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.ld39.region.effects.RegionEffect;

public class Region extends Actor {
	
	private int population;
	private int rebel;
	private int soldiers;
	private Status status;
	private double rebel_rate;
	private ArrayList<RegionEffect> effects;
	private int id;
	private boolean curfew;
	private boolean restrict;
	
	public Region(Color c, Rectangle loc, int id, Status status, int population) {
		super("REGION_" + id, loc);

		getAnimations().add(0, new Animation("REGION_EMPTY", 0));
		getAnimations().get(0).getFrames().add(new Image(Engine2.devPath + "/res/img/null.png"));
		getAnimations().add(1, new Animation("REGION_" + id, 1));
		getAnimations().get(1).getFrames().add(new Image(Engine2.devPath + "/res/img/region/region" + id + ".png"));
		getAnimations().add(2, new Animation("REGION_" + id + "_LOYAL", 2));
		getAnimations().get(2).getFrames().add(new Image(Engine2.devPath + "/res/img/region/region" + id + ".png"));
		getAnimations().add(3, new Animation("REGION_" + id + "_STABLE", 3));
		getAnimations().get(3).getFrames().add(new Image(Engine2.devPath + "/res/img/region/region" + id + ".png"));
		getAnimations().add(4, new Animation("REGION_" + id + "_PROTESTS", 4));
		getAnimations().get(4).getFrames().add(new Image(Engine2.devPath + "/res/img/region/region" + id + ".png"));
		getAnimations().add(5, new Animation("REGION_" + id + "_RIOTS", 5));
		getAnimations().get(5).getFrames().add(new Image(Engine2.devPath + "/res/img/region/region" + id + ".png"));
		getAnimations().add(6, new Animation("REGION_" + id + "_REVOLUTION", 6));
		getAnimations().get(6).getFrames().add(new Image(Engine2.devPath + "/res/img/region/region" + id + ".png"));
		getAnimations().add(7, new Animation("REGION_" + id + "_INDEPENDENT", 7));
		getAnimations().get(7).getFrames().add(new Image(Engine2.devPath + "/res/img/region/region" + id + ".png"));
		for(int i = 0; i < getAnimations().size(); i++) {
			if(!getAnimations().get(i).getFrames().get(0).isImageLoaded()) {
				getAnimations().get(i).getFrames().get(0).loadImage();
			}
		}
		BufferedImage stability = getAnimations().get(1).getFrames().get(0).getImage();
		for(int xx = 0; xx < stability.getWidth(); xx++) {
			for(int yy = 0; yy < stability.getHeight(); yy++) {
				if(stability.getRGB((int) xx, (int) yy) == c.getRGB()) {
					getAnimations().get(2).getFrames().get(0).getImage().setRGB(xx, yy, new Color(0, 255, 0).getRGB());
					getAnimations().get(3).getFrames().get(0).getImage().setRGB(xx, yy, new Color(128, 255, 0).getRGB());
					getAnimations().get(4).getFrames().get(0).getImage().setRGB(xx, yy, new Color(255, 255, 0).getRGB());
					getAnimations().get(5).getFrames().get(0).getImage().setRGB(xx, yy, new Color(255, 128, 0).getRGB());
					getAnimations().get(6).getFrames().get(0).getImage().setRGB(xx, yy, new Color(255, 0, 0).getRGB());
					getAnimations().get(7).getFrames().get(0).getImage().setRGB(xx, yy, new Color(0, 0, 255).getRGB());
				}
			}
		}
		setRunningAnimationNumber(1);
		
		this.status = status;
		this.population = population;
		
		this.rebel = (int) (population * status.getRebelPercentage());
		this.rebel_rate = 0.005;
		this.effects = new ArrayList<RegionEffect>();
		this.id = id;
		this.curfew = false;
		this.restrict = false;
	}
	
	public int getRebel() {
		return rebel;
	}
	
	public int getSoldiers() {
		return soldiers;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public int getPopulation() {
		return population;
	}
	
	public double getRebelRate() {
		return rebel_rate;
	}
	
	public int getID() {
		return id;
	}
	
	public ArrayList<RegionEffect> getRegionEffects() {
		return effects;
	}
	
	public boolean isRestricted() {
		return restrict;
	}
	
	public boolean hasCurfew() {
		return curfew;
	}
	
	public double updateRebelRate() {
		rebel_rate = 0.005;
		for(int i = 0; i < effects.size(); i++) {
			rebel_rate = rebel_rate + effects.get(i).getRebelRate();
		}
		return rebel_rate;
	}
	
	public Status updateStatus() {
		if(status != Status.INDEPENDENT) {
			int s = status.ordinal();
			double rp = ((double) rebel) / population;
			if(s > 0 && rp < Status.values()[s - 1].getRebelPercentage()) {
				status = Status.values()[s - 1];
			}
			else if(s < 5 && rp > Status.values()[s + 1].getRebelPercentage()) {
				status = Status.values()[s + 1];
			}
		}
		return status;
	}
	
	public void setRebel(int rebel) {
		this.rebel = rebel;
	}
	
	public void setSoldiers(int soldiers) {
		this.soldiers = soldiers;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public void setPopulation(int population) {
		this.population = population;
	}
	
	public void setRebelRate(double rebel_rate) {
		this.rebel_rate = rebel_rate;
	}
	
	public void setRegionEffects(ArrayList<RegionEffect> effects) {
		this.effects = effects;
	}
	
	public void setCurfew(boolean curfew) {
		this.curfew = curfew;
	}
	
	public void setRestricted(boolean restrict) {
		this.restrict = restrict;
	}
}