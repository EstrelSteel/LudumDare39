package com.estrelsteel.ld39;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.image.Image;
import com.estrelsteel.engine2.shape.collide.Collision;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Button extends Actor {

	public Button(String name, Rectangle loc) {
		super(name, loc);
		
		getAnimations().add(0, new Animation("BUTTON_MAP_POLITICAL", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(2 * 16, 0 * 16, 4 * 16, 2 * 16)));
		getAnimations().add(1, new Animation("BUTTON_MAP_STABILITY", 1));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(6 * 16, 0 * 16, 4 * 16, 2 * 16)));
		getAnimations().add(2, new Animation("BUTTON_ACTIONS", 2));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(10 * 16, 0 * 16, 4 * 16, 2 * 16)));
		getAnimations().add(3, new Animation("BUTTON_NEXT_MONTH", 3));
		getAnimations().get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(2 * 16, 2 * 16, 4 * 16, 2 * 16)));
		getAnimations().add(4, new Animation("BUTTON_TAXES", 4));
		getAnimations().get(4).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(6 * 16, 2 * 16, 4 * 16, 2 * 16)));
		getAnimations().add(5, new Animation("BUTTON_SOLDIERS", 5));
		getAnimations().get(5).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(10 * 16, 2 * 16, 4 * 16, 2 * 16)));
		getAnimations().add(6, new Animation("BUTTON_RELEASE", 6));
		getAnimations().get(6).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(2 * 16, 4 * 16, 4 * 16, 2 * 16)));
		getAnimations().add(7, new Animation("BUTTON_EVENTS", 7));
		getAnimations().get(7).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(6 * 16, 4 * 16, 4 * 16, 2 * 16)));
		getAnimations().add(8, new Animation("BUTTON_CLOSE", 8));
		getAnimations().get(8).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(14 * 16, 0 * 16, 1 * 16, 1 * 16)));
		getAnimations().add(9, new Animation("BUTTON_CONFIRM", 9));
		getAnimations().get(9).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(15 * 16, 0 * 16, 1 * 16, 1 * 16)));
		getAnimations().add(10, new Animation("BUTTON_PLAY", 10));
		getAnimations().get(10).getFrames().add(new Image(Engine2.devPath + "/res/img/null.png"));
		getAnimations().add(11, new Animation("BUTTON_SCORES", 11));
		getAnimations().get(11).getFrames().add(new Image(Engine2.devPath + "/res/img/null.png"));
		getAnimations().add(12, new Animation("BUTTON_CURFEW", 13));
		getAnimations().get(12).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(10 * 16, 4 * 16, 4 * 16, 2 * 16)));
		getAnimations().add(13, new Animation("BUTTON_RESTRICT", 14));
		getAnimations().get(13).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(2 * 16, 6 * 16, 4 * 16, 2 * 16)));
		
		setHideOffScreen(false);
		setCollision(new Collision(true, new RectangleCollideArea(loc)));
	}

}
