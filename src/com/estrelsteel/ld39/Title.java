package com.estrelsteel.ld39;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.Image;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Title extends Actor {

	public Title(Rectangle loc) {
		super("TITLE_OVERLAY", loc);
		getAnimations().add(0, new Animation("TITLE_IMG", 0));
		getAnimations().get(0).getFrames().add(new Image(Engine2.devPath + "/res/img/title.png"));
	}

}
