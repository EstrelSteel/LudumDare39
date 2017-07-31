package com.estrelsteel.ld39;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.Image;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Tutorial extends Actor  {

	public Tutorial(String name, Rectangle loc) {
		super(name, loc);

		getAnimations().add(0, new Animation("TUTORIAL_NONE", 0));
		getAnimations().get(0).getFrames().add(new Image(Engine2.devPath + "/res/img/null.png"));
		getAnimations().add(1, new Animation("TUTORIAL_0", 1));
		getAnimations().get(1).getFrames().add(new Image(Engine2.devPath + "/res/img/tutorial0.png"));
		getAnimations().add(2, new Animation("TUTORIAL_1", 2));
		getAnimations().get(2).getFrames().add(new Image(Engine2.devPath + "/res/img/tutorial1.png"));
		getAnimations().add(3, new Animation("TUTORIAL_2", 3));
		getAnimations().get(3).getFrames().add(new Image(Engine2.devPath + "/res/img/tutorial2.png"));
		getAnimations().add(4, new Animation("TUTORIAL_3", 4));
		getAnimations().get(4).getFrames().add(new Image(Engine2.devPath + "/res/img/tutorial3.png"));
		getAnimations().add(5, new Animation("TUTORIAL_4", 5));
		getAnimations().get(5).getFrames().add(new Image(Engine2.devPath + "/res/img/tutorial4.png"));
		getAnimations().add(6, new Animation("TUTORIAL_5", 5));
		getAnimations().get(6).getFrames().add(new Image(Engine2.devPath + "/res/img/tutorial5.png"));
		getAnimations().add(7, new Animation("TUTORIAL_6", 6));
		getAnimations().get(7).getFrames().add(new Image(Engine2.devPath + "/res/img/tutorial6.png"));
		
		setRunningAnimationNumber(1);
	}

}
