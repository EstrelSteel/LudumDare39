package com.estrelsteel.ld39;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Hud extends Actor {

	public Hud(Rectangle loc) {
		super("HUD_BACKGROUND", loc);
		getAnimations().add(0, new Animation("BACKGROUND_0", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(0 * 16, 0 * 16, 2 * 16, 4 * 16)));
		getAnimations().add(1, new Animation("BACKGROUND_1", 1));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(0 * 16, 4 * 16, 2 * 16, 2 * 16)));
	}

	public static String stringPopulation(int population) {
		boolean neg = false;
		if(population < 0) {
			neg = true;
			population = population * -1;
		}
		String pop = "";
		String mil = (population / 1000) + ",";
		String tho = (population % 1000) + "k";
		// 000,000
		if(!mil.equalsIgnoreCase("0,")) {
			pop = mil;
			if(tho.length() < 3) {
				tho = "00" + tho;
			}
			else if(tho.length() < 4) {
				tho = "0" + tho;
			}
		}
		pop = pop + tho;
		if(neg) {
			pop = "-" + pop;
		}
		return pop;
	}
}
