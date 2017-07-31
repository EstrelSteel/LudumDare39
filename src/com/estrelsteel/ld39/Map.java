package com.estrelsteel.ld39;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collection;
import java.util.HashMap;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.Image;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.engine2.world.FrozenWorld;
import com.estrelsteel.ld39.region.Region;
import com.estrelsteel.ld39.region.Status;

public class Map extends Actor {

	private Region lastRegion;
	private Region currentRegion;
	private MapMode map_mode;

	private HashMap<Color, Region> regions;
	
	public Map(Rectangle loc) {
		super("MAP", loc);
		
		getAnimations().add(0, new Animation("NULL", 0));
		getAnimations().get(0).getFrames().add(new Image(Engine2.devPath + "/res/img/null.png"));
		getAnimations().add(1, new Animation("EMPTY", 1));
		getAnimations().get(1).getFrames().add(new Image(Engine2.devPath + "/res/img/map/finalMapEmpty.png"));
		getAnimations().add(2, new Animation("GENERIC", 2));
		getAnimations().get(2).getFrames().add(new Image(Engine2.devPath + "/res/img/map/finalMapGenericWorld.png"));
		getAnimations().add(3, new Animation("OVERVIEW", 3));
		getAnimations().get(3).getFrames().add(new Image(Engine2.devPath + "/res/img/map/finalMapOverview.png"));
		getAnimations().add(4, new Animation("POLITICS", 4));
		getAnimations().get(4).getFrames().add(new Image(Engine2.devPath + "/res/img/map/finalMapPolitics.png"));
		getAnimations().add(5, new Animation("MAIN", 5));
		getAnimations().get(5).getFrames().add(new Image(Engine2.devPath + "/res/img/map/finalMapWorld.png"));
		
		regions = new HashMap<Color, Region>();
		
		regions.put(new Color(255, 0, 0), new Region(new Color(255, 0, 0), loc, 0, Status.LOYAL, 2323));
		regions.put(new Color(255, 255, 0), new Region(new Color(255, 255, 0), loc, 1, Status.LOYAL, 3092));
		regions.put(new Color(0, 255, 0), new Region(new Color(0, 255, 0), loc, 2, Status.STABLE, 8136));
		regions.put(new Color(0, 255, 255), new Region(new Color(0, 255, 255), loc, 3, Status.STABLE, 1265));
		regions.put(new Color(0, 0, 255), new Region(new Color(0, 0, 255), loc, 4, Status.RIOTS, 1920));
		regions.put(new Color(255, 0, 255), new Region(new Color(255, 0, 255), loc, 5, Status.PROTESTS, 827));
		regions.put(new Color(255, 128, 0), new Region(new Color(255, 128, 0), loc, 6, Status.STABLE, 1246));
		regions.put(new Color(128, 255, 0), new Region(new Color(128, 255, 0), loc, 7, Status.STABLE, 604));
		regions.put(new Color(0, 255, 128), new Region(new Color(0, 255, 128), loc, 8, Status.STABLE, 4031));
		regions.put(new Color(0, 128, 255), new Region(new Color(0, 128, 255), loc, 9, Status.PROTESTS, 591));
		regions.put(new Color(128, 0, 255), new Region(new Color(128, 0, 255), loc, 10, Status.STABLE, 1024));
		
		lastRegion = null;
		currentRegion = null;
		map_mode = MapMode.POLITICAL;
	}
	
	public Region getCurrentRegion() {
		return currentRegion;
	}
	
	public Region getLastRegion() {
		return lastRegion;
	}
	
	public Collection<Region> getRegions() {
		return regions.values();
	}
	
	public MapMode getMapMode() {
		return map_mode;
	}
	
	public Region getRegion(double screenX, double screenY) {
		if(screenX < 0 || screenY < 0 || screenX > 640 || screenY > 640) {
			return null;
		}
		Image map = getAnimations().get(4).getFrames().get(0);
		
		if(!map.isImageLoaded()) {
			map.loadImage();
		}
		
		double scaleX = (double) map.getImage().getWidth() / 640.0;
		double scaleY = (double) map.getImage().getHeight() / 640.0;
		double x = screenX * scaleX;
		double y = screenY * scaleY;
		
		Color colour = new Color(map.getImage().getRGB((int) x, (int) y));
		return regions.get(colour);
	}
	
	public void changeMapMode(MapMode map_mode) {
		this.map_mode = map_mode;
		if(map_mode == MapMode.STABILITY) {
			for(Region r : getRegions()) {
				r.setRunningAnimationNumber(r.getStatus().ordinal() + 2);
			}
		}
		else if(map_mode == MapMode.POLITICAL) {
			for(Region r : getRegions()) {
				if(r.getStatus() != Status.INDEPENDENT) {
					r.setRunningAnimationNumber(1);
				}
				else {
					r.setRunningAnimationNumber(0);
				}
			}
		}
	}
	
	public void setCurrentRegion(Region currentRegion) {
		this.currentRegion = currentRegion;
	}
	
	public void setLastRegion(Region lastRegion) {
		this.lastRegion = lastRegion;
	}
	
	public Graphics2D simpleRender(Graphics2D ctx, FrozenWorld world) {
		ctx = super.simpleRender(ctx, world);
		for(Region r : regions.values()) {
			if(r != currentRegion) {
				r.simpleRender(ctx, world);
			}
		}
		if(currentRegion != null) {
			currentRegion.simpleRender(ctx, world);
		}
		return ctx;
	}
	
	public Graphics2D render(Graphics2D ctx, FrozenWorld world) {
		ctx = super.render(ctx, world);
		for(Region r : regions.values()) {
			if(r != currentRegion) {
				r.render(ctx, world);
			}
		}
		if(currentRegion != null) {
			currentRegion.render(ctx, world);
		}
		return ctx;
	}
	
}
