package com.estrelsteel.ld39;

import java.awt.Graphics2D;

import com.estrelsteel.engine2.Launcher;
import com.estrelsteel.engine2.events.listener.RenderListener;
import com.estrelsteel.engine2.events.listener.StartListener;
import com.estrelsteel.engine2.events.listener.StopListener;
import com.estrelsteel.engine2.events.listener.TickListener;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.engine2.window.WindowSettings;

public class LD39 implements StartListener, StopListener, TickListener, RenderListener {
	
	private Launcher l;
	
	public static void main(String[] args) {
		new LD39();
	}
	
	@SuppressWarnings("static-access")
	public LD39() {
		l = new Launcher();
		Rectangle size;
		if(System.getProperty("os.name").startsWith("Windows")) {
			size = QuickRectangle.location(0, 0, 630, 630);
		}
		else {
			size = QuickRectangle.location(0, 0, 640, 640);
		}
		l.getEngine().setWindowSettings(new WindowSettings(size, " - EstrelSteel", "v1.0a", 0));
		
		l.getEngine().START_EVENT.addListener(this);
		l.getEngine().STOP_EVENT.addListener(this);
		l.getEngine().RENDER_EVENT.addListener(this);
		l.getEngine().TICK_EVENT.addListener(this);
		
		l.getEngine().development = true;
		l.getEngine().devPath = System.getProperty("user.home") + "/Documents/usb/LD39/LD39";
//		l.getEngine().devPath = GameFile.getCurrentPath();
		
		l.boot();
	}

	@Override
	public void start() {
		
	}

	@Override
	public void stop() {
		
	}

	@Override
	public Graphics2D render(Graphics2D ctx) {
		return ctx;
	}

	@Override
	public void tick() {
		
	}
}
