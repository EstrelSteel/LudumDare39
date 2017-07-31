package com.estrelsteel.ld39;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.Launcher;
import com.estrelsteel.engine2.actor.Text;
import com.estrelsteel.engine2.events.listener.RenderListener;
import com.estrelsteel.engine2.events.listener.StartListener;
import com.estrelsteel.engine2.events.listener.StopListener;
import com.estrelsteel.engine2.events.listener.TickListener;
import com.estrelsteel.engine2.file.GameFile;
import com.estrelsteel.engine2.grid.PixelGrid;
import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.engine2.window.WindowSettings;
import com.estrelsteel.engine2.world.FrozenWorld;
import com.estrelsteel.engine2.world.Menu;
import com.estrelsteel.ld39.events.EventType;
import com.estrelsteel.ld39.events.GamesEvent;
import com.estrelsteel.ld39.events.MEconEvent;
import com.estrelsteel.ld39.events.MoneyClaimEvent;
import com.estrelsteel.ld39.events.MonthlyEvent;
import com.estrelsteel.ld39.events.PropagandaEvent;
import com.estrelsteel.ld39.events.RegionPayEvent;
import com.estrelsteel.ld39.events.SoldierPayEvent;
import com.estrelsteel.ld39.events.TutorialEvent;
import com.estrelsteel.ld39.handler.MouseHandler;
import com.estrelsteel.ld39.region.Empire;
import com.estrelsteel.ld39.region.Region;
import com.estrelsteel.ld39.region.Status;
import com.estrelsteel.ld39.region.effects.CapitalRegionEffect;
import com.estrelsteel.ld39.region.effects.EmpireStabilityRegionEffect;
import com.estrelsteel.ld39.region.effects.OccupiedRegionEffect;
import com.estrelsteel.ld39.region.effects.PlayerChoiceRegionEffect;
import com.estrelsteel.ld39.region.effects.RegionEffect;
import com.estrelsteel.ld39.region.effects.SoldiersRegionEffect;
import com.estrelsteel.ld39.region.effects.StabilityRegionEffect;
import com.estrelsteel.ld39.region.effects.TaxRegionEffect;
import com.estrelsteel.ld39.region.effects.TreasuryRegionEffect;

public class LD39 implements StartListener, StopListener, TickListener, RenderListener {
	
	/*
	 * 
	 * THE EMPIRE'S FALL
	 * 
	 * BY: ESTRELSTEEL
	 * LUDUM DARE 39 - COMPO
	 * 
	 * THEME: Running out of Power
	 * 
	 * (30.07.2017)
	 * 
	 */
	
	private Launcher l;
	private MouseHandler m;
	
	private Map map;
	private Empire empire;
	private FrozenWorld world;
	private Region tempRegion;
	private int month;
	private Menu actions;
	private ArrayList<MonthlyEvent> active_events;
	private final int unique_events = 5;
	
	private Tutorial tut;
	private boolean intro;
	private Menu title;
	
	public static void main(String[] args) {
		new LD39();
	}
	
	@SuppressWarnings("static-access")
	public LD39() {
		l = new Launcher();
		Rectangle size;
		if(System.getProperty("os.name").startsWith("Windows")) {
			size = QuickRectangle.location(0, 0, 950, 630);
		}
		else {
			size = QuickRectangle.location(0, 0, 960, 640);
		}
		l.getEngine().setWindowSettings(new WindowSettings(size, "The Empire's Fall - EstrelSteel", "v1.0a", 0));
		
		m = new MouseHandler();
		
		l.getEngine().START_EVENT.addListener(this);
		l.getEngine().STOP_EVENT.addListener(this);
		l.getEngine().RENDER_EVENT.addListener(this);
		l.getEngine().TICK_EVENT.addListener(this);
		l.getEngine().addMouseListener(m);
		
		l.getEngine().ROUNDING_FORMAT = new DecimalFormat("0.##");
		
		l.getEngine().development = true;
//		l.getEngine().devPath = System.getProperty("user.home") + "/Documents/usb/LD39/LD39";
		l.getEngine().devPath = GameFile.getCurrentPath();
		
		l.boot();
	}

	
	@Override
	public void start() {
		month = 1;
		world = new FrozenWorld(new PixelGrid());
		map = new Map(QuickRectangle.location(0, 0, 640, 640));
		map.setRunningAnimationNumber(1);
		empire = new Empire(map.getRegions());
		world.getObjects().add(map);
		world.getObjects().add(new Hud(QuickRectangle.location(640, 0, 320, 640)));
		Button p = new Button("POLITICAL", QuickRectangle.location(665, 250, 128, 64));
		p.setRunningAnimationNumber(0);
		Button s = new Button("STABILITY", QuickRectangle.location(807, 250, 128, 64));
		s.setRunningAnimationNumber(1);
		Button a = new Button("ACTIONS", QuickRectangle.location(736, 560, 128, 64));
		a.setRunningAnimationNumber(2);
		Button n = new Button("NEXT_MONTH", QuickRectangle.location(256, 556, 128, 64));
		n.setRunningAnimationNumber(3);
		world.getObjects().add(p);
		world.getObjects().add(s);
		world.getObjects().add(a);
		world.getObjects().add(n);
		
		actions = new Menu("ACTIONS", false);
		Hud a_hud = new Hud(QuickRectangle.location(32, 32, 576, 576));
		a_hud.setRunningAnimationNumber(1);
		actions.getObjects().add(a_hud);
		Button t = new Button("TAXES", QuickRectangle.location(80, 120, 128, 64));
		t.setRunningAnimationNumber(4);
		Button so = new Button("SOLDIERS", QuickRectangle.location(80, 240, 128, 64));
		so.setRunningAnimationNumber(5);
		Button r = new Button("RELEASE", QuickRectangle.location(432, 496, 128, 64));
		r.setRunningAnimationNumber(6);
		Button ev = new Button("EVENTS", QuickRectangle.location(220, 120, 128, 64));
		ev.setRunningAnimationNumber(7);
		Button c = new Button("CLOSE", QuickRectangle.location(524, 80, 32, 32));
		c.setRunningAnimationNumber(8);
		Button cur = new Button("CURFEW", QuickRectangle.location(220, 240, 128, 64));
		cur.setRunningAnimationNumber(12);
		Button rest = new Button("RESTRICT", QuickRectangle.location(360, 240, 128, 64));
		rest.setRunningAnimationNumber(13);
		Text txt_info = new Text("Right-click on a button to get more info.", QuickRectangle.location(80, 580, 0, 0), new Font("Menlo", Font.BOLD, 12), new Color(64, 16, 0));
		Text txt_empire = new Text("EMPIRE:", QuickRectangle.location(80, 110, 0, 0), new Font("Menlo", Font.BOLD, 30), new Color(64, 16, 0));
		Text txt_region = new Text("REGION:", QuickRectangle.location(80, 220, 0, 0), new Font("Menlo", Font.BOLD, 30), new Color(64, 16, 0));
		Text txt_events = new Text("0", QuickRectangle.location(330, 140, 0, 0), new Font("Menlo", Font.BOLD, 16), new Color(192, 0, 0));
		actions.getObjects().add(t);
		actions.getObjects().add(so);
		actions.getObjects().add(r);
		actions.getObjects().add(a);
		actions.getObjects().add(ev);
		actions.getObjects().add(c);
		actions.getObjects().add(cur);
		actions.getObjects().add(rest);
		actions.getObjects().add(txt_info);
		actions.getObjects().add(txt_empire);
		actions.getObjects().add(txt_region);
		actions.getObjects().add(txt_events);
		
		ArrayList<Region> regions = new ArrayList<Region>();
		regions.addAll(map.getRegions());
		for(int i = 0; i < regions.size(); i++) {
			if(regions.get(i).getName().equalsIgnoreCase("REGION_0")) {
				regions.get(i).getRegionEffects().add(new CapitalRegionEffect());
			}
//			else if(regions.get(i).getName().equalsIgnoreCase("REGION_4")){
//				regions.get(i).getRegionEffects().add(new OccupiedRegionEffect(0.005));
//			}
			else {
				regions.get(i).getRegionEffects().add(new OccupiedRegionEffect(0.003));
			}
			regions.get(i).getRegionEffects().add(new TaxRegionEffect(empire.getTaxes()));
			regions.get(i).getRegionEffects().add(new StabilityRegionEffect(regions.get(i).getStatus()));
			regions.get(i).getRegionEffects().add(new EmpireStabilityRegionEffect(Status.values()[empire.getStability()]));
			regions.get(i).getRegionEffects().add(new SoldiersRegionEffect(0));
			regions.get(i).getRegionEffects().add(new TreasuryRegionEffect(empire.getTreasury()));
			regions.get(i).updateRebelRate();
		}
		active_events = new ArrayList<MonthlyEvent>();
		active_events.add(new TutorialEvent(empire, 100));
//		active_events.add(new RegionPayEvent(empire, regions.get(0), 100, 10, 0.005));
//		active_events.add(new SoldierPayEvent(empire, 100, 50));
//		active_events.add(new GamesEvent(empire, map.getRegions(), 10000 + (500 * (month / 5)), -0.01 * ((double) month / 37.0)));

		tut = new Tutorial("Tutorial", QuickRectangle.location(0, 0, 960, 640));
		intro = false;
		
		title = new Menu("TITLE", true);
		Button play = new Button("PLAY", QuickRectangle.location(181, 388, 210, 70));
		play.setRunningAnimationNumber(10);
		Button scores = new Button("SCORES", QuickRectangle.location(571, 388, 210, 70));
		scores.setRunningAnimationNumber(11);
		title.getObjects().add(new Title(QuickRectangle.location(0, 0, 960, 640)));
		title.getObjects().add(play);
		title.getObjects().add(scores);
	}

	@Override
	public void stop() {
		
	}

	@Override
	public Graphics2D render(Graphics2D ctx) {
		if(!title.isVisible()) {
			world.renderWorld(ctx);
			ctx.setColor(new Color(64, 16, 0));
			ctx.setFont(new Font("Menlo", Font.BOLD, 30));
			ctx.drawString("EMPIRE:", 660, 50);
			ctx.setFont(new Font("Menlo", Font.BOLD, 16));
			ctx.drawString("Month " + month, 840, 48);
			ctx.drawString("Free Soldiers: " + Hud.stringPopulation(empire.getFreeSoldiers()), 680, 80);
			ctx.drawString("Active Soldiers: " + Hud.stringPopulation(empire.getActiveSoldiers()), 680, 100);
			ctx.drawString("Total Population: " + Hud.stringPopulation(empire.getPopulation()), 680, 120);
			ctx.drawString("Average Stability: " + Status.values()[empire.updateStability(map.getRegions())], 680, 140);
			ctx.drawString("Treasury: $" + Hud.stringPopulation(empire.getTreasury()), 680, 180);
			ctx.drawString("Tax Rate: " + empire.getTaxes() * 100 + "%", 680, 200);
			ctx.drawString("Income: $" + Hud.stringPopulation(empire.getIncome()) + " per month", 680, 220);
			ctx.drawString("Right click on a region to get info about rebel recruitment.", 10, 20);
			ctx.setStroke(new BasicStroke(5));
			int mid = 320;
			if(map.getCurrentRegion() != null) {
				ctx.drawLine(680, mid, 920, mid);
				ctx.setFont(new Font("Menlo", Font.BOLD, 30));
				ctx.drawString("REGION " + (map.getCurrentRegion().getID() + 1) + ":", 660, 50 + mid);
				ctx.setFont(new Font("Menlo", Font.BOLD, 16));
				ctx.drawString("Active Soldiers: " + Hud.stringPopulation(map.getCurrentRegion().getSoldiers()), 680, 80 + mid);
				
				ctx.drawString("Total Population: " + Hud.stringPopulation(map.getCurrentRegion().getPopulation()), 680, 100 + mid);
				ctx.drawString("Stability: " + map.getCurrentRegion().getStatus(), 680, 120 + mid);
				ctx.drawString("Rebels: " + Hud.stringPopulation(map.getCurrentRegion().getRebel()), 680, 140 + mid);
				ctx.drawString("Rebel Recruitment: " + Engine2.ROUNDING_FORMAT.format(map.getCurrentRegion().getRebelRate() * 100) + "%", 680, 160 + mid);
			}
			actions.renderWorld(ctx);
			if(!intro/* && l.getEngine().getStats().getFrames() > 30*/) {
				intro = true;
				JOptionPane.showMessageDialog(l.getEngine(), "Welcome to the glorious Empire!!!! Or... Once glorious Empire.\n\nYou have been appointed by our "
						+ "Emperor to keep our this state alive for ideal the rest of eternity, but more realistically, 36 months.\n"
						+ "The Emperor has given you full control of the Empire to give you your best chance at sustaining it.\n"
						+ "You can control taxation, military, and any political events that may arise during your years here.");
				int ans = JOptionPane.showOptionDialog(l.getEngine(), "Would you like to enable the tutorial to guide you through the first month of your reign.", "Tutorial", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if(ans == JOptionPane.NO_OPTION) {
					tut = null;
				}
				
			}
			if(tut != null && intro) {
				tut.render(ctx, world);
			}
		}
		else {
			title.renderWorld(ctx);
		}
		return ctx;
	}
	
	@Override
	public void tick() {
		if(tut != null) {
			if(tut.getRunningAnimationNumber() == 1) {
				if(map.getMapMode() == MapMode.STABILITY) {
					tut.setRunningAnimationNumber(2);
				}
			}
			else if(tut.getRunningAnimationNumber() == 2) {
				if(map.getCurrentRegion() != null && map.getCurrentRegion().getName().equalsIgnoreCase("REGION_4")) {
					tut.setRunningAnimationNumber(3);
				}
			}
			else if(tut.getRunningAnimationNumber() == 3) {
				if(actions.isVisible()) {
					tut.setRunningAnimationNumber(4);
				}
			}
			else if(tut.getRunningAnimationNumber() == 4) {
				if(active_events.size() == 0) {
					tut.setRunningAnimationNumber(5);
				}
			}
			else if(tut.getRunningAnimationNumber() == 5) {
				if(empire.getFreeSoldiers() < empire.getTotalSoldiers()) {
					tut.setRunningAnimationNumber(6);
				}
			}
			else if(tut.getRunningAnimationNumber() == 6) {
				if(!actions.isVisible()) {
					tut.setRunningAnimationNumber(7);
				}
			}
		}
		((Text) actions.getObjects().get(actions.getObjects().size() - 1)).setText(active_events.size() + "");
		if(m.getX() > 0 && m.getY() > 0 && m.isAvaliable()) {
			Renderable r;
			String msg;
			if(title.isVisible()) {
				r = title.checkCollide(new RectangleCollideArea(QuickRectangle.location(m.getX(), m.getY(), 1, 1)), map);
				if(r instanceof Button) {
					Button b = (Button) r;
					if(b.getName().equalsIgnoreCase("PLAY")) {
						title.setVisible(false);
					}
					else if(b.getName().equalsIgnoreCase("SCORES")) {
						GameFile file = new GameFile(Engine2.devPath + "/res/scores.txt");
						try {
							file.setLines(file.readFile());
						} 
						catch (IOException e) {
							e.printStackTrace();
						}
						msg = "SCORES: ";
						String args[];
						if(file.getLines().size() == 0) {
							msg = msg + "\nThere are no scores to show.";
						}
						for(int i = 0; i < file.getLines().size(); i++) {
							args = file.getLines().get(i).split(" ");
							msg = msg + "\n" + args[0].trim() + "\n\tRegional Score: " + args[1].trim() + "\n\tFinal Score: " + args[2].trim();
						}
						JOptionPane.showMessageDialog(l.getEngine(), msg);
					}
				}
			}
			else if(!actions.isVisible()) {
				tempRegion = map.getRegion(m.getX(), m.getY());
				if(tempRegion != null) {
					m.setAvaliable(false);
					map.setLastRegion(map.getCurrentRegion());
					map.setCurrentRegion(tempRegion);
					if(map.getLastRegion() != null) {
						map.getLastRegion().setLocation(QuickRectangle.location(0, 0, 640, 640));
					}
					map.getCurrentRegion().setLocation(QuickRectangle.location(-32, -32, 704, 704));
					if(m.isRightClick()) {
						msg = "REGION " + (map.getCurrentRegion().getID() + 1) + " Rebel Breakdown:";
						for(int i = 0; i < map.getCurrentRegion().getRegionEffects().size(); i++) {
							msg = msg + "\n\t" + map.getCurrentRegion().getRegionEffects().get(i).getName() + ":\n\t\t\t\t" 
									+ Engine2.ROUNDING_FORMAT.format(map.getCurrentRegion().getRegionEffects().get(i).getRebelRate() * 100) + "%";
						}
						msg = msg + "\n\n### ###\nTotal Rebel Rate\n\t\t\t\t" + Engine2.ROUNDING_FORMAT.format(map.getCurrentRegion().getRebelRate() * 100) + "%";
						JOptionPane.showMessageDialog(l.getEngine(), msg);
						m.setRightClick(false);
					}
				}
				else {
					r = world.checkCollide(new RectangleCollideArea(QuickRectangle.location(m.getX(), m.getY(), 1, 1)), map);
					if(r instanceof Button) {
						m.setAvaliable(false);
						Button b = (Button) r;
						if(b.getName().equalsIgnoreCase("POLITICAL")) {
							map.changeMapMode(MapMode.POLITICAL);
						}
						else if(b.getName().equalsIgnoreCase("STABILITY")) {
							map.changeMapMode(MapMode.STABILITY);
						}
						else if(b.getName().equalsIgnoreCase("ACTIONS")) {
							actions.setVisible(!actions.isVisible());
						}
						else if(b.getName().equalsIgnoreCase("NEXT_MONTH")) {
							if(active_events.size() <= 0) {
								nextMonth();
							}
							else {
								JOptionPane.showMessageDialog(l.getEngine(), "You haven't answered all the events.");
							}
						}
					}
				}
			}
			else {
				r = actions.checkCollide(new RectangleCollideArea(QuickRectangle.location(m.getX(), m.getY(), 1, 1)), map);
				if(r instanceof Button) {
					m.setAvaliable(false);
					Button b = (Button) r;
					if(b.getName().equalsIgnoreCase("ACTIONS")) {
						actions.setVisible(!actions.isVisible());
					}
					else if(b.getName().equalsIgnoreCase("TAXES")) {
						if(!m.isRightClick()) {
							String ntax = JOptionPane.showInputDialog(l.getEngine(), "The current tax rate is: " + empire.getTaxes() * 100 + "%; enter in your new rate.");
							if(ntax != null) {
								if(ntax.startsWith("%")) {
									ntax = ntax.substring(1);
								}
								else if(ntax.endsWith("%")) {
									ntax = ntax.substring(0, ntax.length() - 1);
								}
								double t = Double.parseDouble(ntax);
								if(t > 1.0) {
									t = t / 100;
								}
								if(t < 0) {
									t = t * -1;
								}
								empire.setTaxes(t);
							}
						}
						else {
							JOptionPane.showMessageDialog(l.getEngine(), "A higher tax rate increases rebel recruitment, which lowers stability in a region.\nRebels do not pay taxes.");
							m.setRightClick(false);
						}
					}
					else if(b.getName().equalsIgnoreCase("EVENTS")) {
						if(!m.isRightClick()) {
							if(active_events.size() > 0) {
								MonthlyEvent event = active_events.get(0);
								
								if(event.isDone()) {
									System.err.println("Attempted to start a completed event!");
									active_events.remove(0);
								}
								else {
									if(event instanceof RegionPayEvent) {
										map.setCurrentRegion(((RegionPayEvent) event).getRegion());
									}
									if(event.getType() == EventType.MESSAGE) {
										JOptionPane.showMessageDialog(l.getEngine(), event.getDescription());
										event.answerEvent(0);
										active_events.remove(0);
									}
									else if(event.getType() == EventType.TRUE_FALSE) {
										int ans = JOptionPane.showOptionDialog(l.getEngine(), event.getDescription(), "Event", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
										if(ans == JOptionPane.YES_OPTION) {
											event.answerEvent(0);
										}
										else {
											event.answerEvent(1);
										}
										active_events.remove(0);
									}
								}
							}
							else {
								JOptionPane.showMessageDialog(l.getEngine(), "You have viewed and resolved all events this month.");
							}
						}
						else {
							JOptionPane.showMessageDialog(l.getEngine(), "Events are things that have occured in the past month. You will need to make decisions on "
									+ "these events before you can progress to the next month.");
							m.setRightClick(false);
						}
					}
					else if(b.getName().equalsIgnoreCase("CLOSE")) {
						actions.setVisible(false);
					}
					else {
						if(map.getCurrentRegion() != null) {
							if(b.getName().equalsIgnoreCase("SOLDIERS")) {
								if(!m.isRightClick()) {
									if(map.getCurrentRegion().getStatus() != Status.INDEPENDENT) {
										String sol = JOptionPane.showInputDialog(l.getEngine(), "There are currently " + Hud.stringPopulation(map.getCurrentRegion().getSoldiers()) +" stationed in this region. You have " 
												+ Hud.stringPopulation(empire.getFreeSoldiers()) + " soldiers avaliable.\nEnter in the new amount of soldiers you would like to station in this region in the order of thousands:");
										if(sol != null) {
											if(sol.endsWith("k")) {
												sol = sol.substring(0, sol.length() - 1);
											}
											int s = Integer.parseInt(sol);
											if(s > empire.getFreeSoldiers() + map.getCurrentRegion().getSoldiers()) {
												JOptionPane.showMessageDialog(l.getEngine(), "That is more soldiers than you control.");
											}
											else if(s < 200 && map.getCurrentRegion().isRestricted()) {
												JOptionPane.showMessageDialog(l.getEngine(), "You cannot do this.\nYou need at least 200k soldiers to continue restricting movement.");
											}
											else if(s < 100 && map.getCurrentRegion().hasCurfew()) {
												JOptionPane.showMessageDialog(l.getEngine(), "You cannot do this.\nYou need at least 100k soldiers to continue a curfew.");
											}
											else {
												map.getCurrentRegion().setSoldiers(s);
												empire.updateActiveSoldiers(map.getRegions());
												for(RegionEffect e : map.getCurrentRegion().getRegionEffects()) {
													if(e instanceof SoldiersRegionEffect) {
														((SoldiersRegionEffect) e).updateRebelRate(s);
													}
												}
												map.getCurrentRegion().updateRebelRate();
											}
										}
									}
									else {
										JOptionPane.showMessageDialog(l.getEngine(), "You cannot alter and independent state.");
									}
								}
								else {
									JOptionPane.showMessageDialog(l.getEngine(), "Soldiers in a region increase stability and suppress rebel recruitment.");
									m.setRightClick(false);
								}
							}
							else if(b.getName().equalsIgnoreCase("CURFEW")) {
								if(!m.isRightClick()) {
									if(map.getCurrentRegion().getStatus() != Status.INDEPENDENT) {
										msg = "";
										if(map.getCurrentRegion().hasCurfew()) {
											msg = "This region currently has a curfew. Do you want to repeal the curfew and increase rebel recruitment by 0.5%?";
										}
										else {
											if(map.getCurrentRegion().getSoldiers() >= 100) {
												msg = "This region has no curfew. Do you want to add one, increasing the rebel numbers by 15k and reducing recruitment by 0.5%?";
											}
											else {
												msg = null;
												JOptionPane.showMessageDialog(l.getEngine(), "You need 100k soldiers in this region to implement a curfew. You have " 
														+ Hud.stringPopulation(map.getCurrentRegion().getSoldiers()));
											}
										}
										if(msg != null) {
											int ans = JOptionPane.showOptionDialog(l.getEngine(), msg, "Curfew", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
											if(ans == JOptionPane.YES_OPTION) {
												if(!map.getCurrentRegion().hasCurfew()) {
													map.getCurrentRegion().setRebel(map.getCurrentRegion().getRebel() + 15);
													map.getCurrentRegion().getRegionEffects().add(new PlayerChoiceRegionEffect("Curfew", -0.005));
													map.getCurrentRegion().setCurfew(true);
													map.getCurrentRegion().updateRebelRate();
												}
												else {
													map.getCurrentRegion().setCurfew(false);
													for(int i = 0; i < map.getCurrentRegion().getRegionEffects().size(); i++) {
														if(map.getCurrentRegion().getRegionEffects().get(i).getName().equalsIgnoreCase("Curfew")) {
															map.getCurrentRegion().getRegionEffects().remove(i);
															i--;
														}
													}
												}
											}
										}
									}
									else {
										JOptionPane.showMessageDialog(l.getEngine(), "You cannot alter and independent state.");
									}
								}
								else {
									JOptionPane.showMessageDialog(l.getEngine(), "Curfews decrease the rebel's ability to recruit by 0.5%.\nBut when enacted, 15k people join the rebel cause."
											+ "\nCurfews also require 100k soldiers to maintain.");
									m.setRightClick(false);
								}
							}
							else if(b.getName().equalsIgnoreCase("RESTRICT")) {
								if(!m.isRightClick()) {
									if(map.getCurrentRegion().getStatus() != Status.INDEPENDENT) {
										msg = "";
										if(map.getCurrentRegion().isRestricted()) {
											msg = "This region currently is restricted. Do you want to repeal the movement restriction and increase rebel recruitment by 1%?";
										}
										else {
											if(map.getCurrentRegion().getSoldiers() >= 200) {
												msg = "This region has no movement restrictions. Do you want to add one, increasing the rebel numbers by 40k and reducing recruitment by 1%?";
											}
											else {
												msg = null;
												JOptionPane.showMessageDialog(l.getEngine(), "You need 200k soldiers in this region to implement a movement restriction. You have " 
														+ Hud.stringPopulation(map.getCurrentRegion().getSoldiers()));
											}
										}
										if(msg != null) {
											int ans = JOptionPane.showOptionDialog(l.getEngine(), msg, "Movement Restriction", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
											if(ans == JOptionPane.YES_OPTION) {
												if(!map.getCurrentRegion().hasCurfew()) {
													map.getCurrentRegion().setRebel(map.getCurrentRegion().getRebel() + 40);
													map.getCurrentRegion().getRegionEffects().add(new PlayerChoiceRegionEffect("Movement Restriction", -0.01));
													map.getCurrentRegion().setRestricted(true);
													map.getCurrentRegion().updateRebelRate();
												}
												else {
													map.getCurrentRegion().setRestricted(false);
													for(int i = 0; i < map.getCurrentRegion().getRegionEffects().size(); i++) {
														if(map.getCurrentRegion().getRegionEffects().get(i).getName().equalsIgnoreCase("Movement Restriction")) {
															map.getCurrentRegion().getRegionEffects().remove(i);
															i--;
														}
													}
												}
											}
										}
									}
									else {
										JOptionPane.showMessageDialog(l.getEngine(), "You cannot alter and independent state.");
									}
								}
								else {
									JOptionPane.showMessageDialog(l.getEngine(), "Movement Restrictions decrease the rebel's ability to recruit by 1%.\nBut when enacted, 40k people join the rebel cause."
											+ "\nCurfews also require 200k soldiers to maintain.");
									m.setRightClick(false);
								}
							}
							else if(b.getName().equalsIgnoreCase("RELEASE")) {
								if(!m.isRightClick()) {
									if(map.getCurrentRegion().getStatus() != Status.INDEPENDENT) {
										int release = JOptionPane.showConfirmDialog(l.getEngine(), "Are you sure you would like release this region.\nThis action is irreversable.");
										if(release == JOptionPane.YES_OPTION) {
											boolean valid = true;
											for(RegionEffect e : map.getCurrentRegion().getRegionEffects()) {
												if(e instanceof CapitalRegionEffect) {
													valid = false;
													JOptionPane.showMessageDialog(l.getEngine(), "You cannot release your capital.");
													break;
												}
											}
											if(valid) {
												map.getCurrentRegion().setStatus(Status.INDEPENDENT);
												map.changeMapMode(map.getMapMode());
												empire.updatePopulation(map.getRegions());
											}
										}
									}
									else {
										JOptionPane.showMessageDialog(l.getEngine(), "You cannot alter and independent state.");
									}
								}
								else {
									JOptionPane.showMessageDialog(l.getEngine(), "Releasing a region makes it fully independent. It's stability will no longer affect your stability, "
											+ "all your soldiers will be withdrawn, and your tax rate will no longer apply.\nYou cannot release the capital region.");
									m.setRightClick(false);
								}
							}
						}
						else {
							JOptionPane.showMessageDialog(l.getEngine(), "You need to select a region first.");
						}
					}
				}
			}
		}
	}
	
	public void showScore() throws IOException {
		JOptionPane.showMessageDialog(l.getEngine(), "It is the end of the 36 months, let's see your score.");
		int loyal = 0; //5
		int stable = 0; //4
		int protest = 0; //3
		int riots = 0; //2
		int rev = 0; //1
		int ind = 0; //0
		
		for(Region r : map.getRegions()) {
			if(r.getStatus() == Status.LOYAL) {
				loyal++;
			}
			else if(r.getStatus() == Status.STABLE) {
				stable++;
			}
			else if(r.getStatus() == Status.PROTESTS) {
				protest++;
			}
			else if(r.getStatus() == Status.RIOTS) {
				riots++;
			}
			else if(r.getStatus() == Status.REVOLUTION) {
				rev++;
			}
			else if(r.getStatus() == Status.INDEPENDENT) {
				ind++;
			}
		}
		String msg = "Score:";
		msg = msg + "\n\tRegions Loyal: " + loyal;
		msg = msg + "\n\tRegions Stable: " + stable;
		msg = msg + "\n\tRegions Protesting: " + protest;
		msg = msg + "\n\tRegions Rioting: " + riots;
		msg = msg + "\n\tRegions in Revolution: " + rev;
		msg = msg + "\n\tRegions Independent: " + ind;
		msg = msg + "\n\n\tTreasury: " + empire.getTreasury();
		int r_score = loyal * 400 + stable * 300 + protest * 200 + riots * 100 + rev * 0 + ind * -200;
		int score = r_score + empire.getTreasury();
		msg = msg + "\n\n\nRegional Score: " + r_score;
		msg = msg + "\n\nFinal Score: " + score;
		JOptionPane.showMessageDialog(l.getEngine(), msg);
		String name = JOptionPane.showInputDialog(l.getEngine(), "Enter in a name for your score. (Press cancel to not save your score).");
		if(!(name == null || name.trim().equalsIgnoreCase(""))) {
			System.out.println("Saving score...");
			name = name.trim();
			name = name.replaceAll(" ", "_");
			GameFile scores = new GameFile(Engine2.devPath + "/res/scores.txt");
			scores.setLines(scores.readFile());
			scores.getLines().add(name.trim() + " " + r_score + " " + score + " " + loyal + " " + stable + " " + protest + " " + riots + " " + rev + " " + ind + " " + empire.getTreasury());
			scores.saveFile();
			System.out.println("Score saved.");
		}
		int ans = JOptionPane.showOptionDialog(l.getEngine(), "Thank you for play The Empire's Fall\n"
				+ "By: EstrelSteel\n\nLudum Dare 39 - Compo\n\tRunning out of Power\n\n\n\nWould you like to continue?", 
				"Thank you", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if(ans == JOptionPane.NO_OPTION) {
			l.getEngine().stop();
		}
	}
	
	public void nextMonth() {
		tut = null;
		month++;
		Collection<Region> regions = map.getRegions();
		for(Region r : regions) {
			r.setRebel(r.getRebel() + (int) (r.getPopulation() * r.updateRebelRate()));
			if(r.getRebel() < 0) {
				r.setRebel(0);
			}
			else if(r.getRebel() > r.getPopulation()) {
				r.setRebel(r.getPopulation());
			}
			r.updateStatus();
			for(RegionEffect e : r.getRegionEffects()) {
				if(e instanceof EmpireStabilityRegionEffect) {
					((EmpireStabilityRegionEffect) e).updateRebelRate(Status.values()[empire.getStability()]);
				}
				else if(e instanceof StabilityRegionEffect) {
					((StabilityRegionEffect) e).updateRebelRate(r.getStatus());
				}
				else if(e instanceof TaxRegionEffect) {
					((TaxRegionEffect) e).updateRebelRate(empire.getTaxes());
				}
				else if(e instanceof TreasuryRegionEffect) {
					((TreasuryRegionEffect) e).updateRebelRate(empire.getTreasury());
				}
			}
		}
		empire.updateStability(regions);
		empire.updateActiveSoldiers(regions);
		empire.updateRebels(regions);
		empire.setTreasury(empire.getTreasury() + empire.getIncome());
		map.changeMapMode(map.getMapMode());
		ArrayList<Region> arr_r = new ArrayList<Region>();
		arr_r.addAll(regions);
		for(int i = 0; i < arr_r.size(); i++) {
			if(arr_r.get(i).getStatus() == Status.INDEPENDENT) {
				arr_r.remove(i);
				i--;
			}
		}
		for(int i = 0; i < month / 10 + 1; i++) {
			loadEvent(month, arr_r);
		}
		System.out.println("Added " + active_events.size() + " for the new month.");
		if(month == 37) {
			try {
				showScore();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void loadEvent(int month, ArrayList<Region> regions) {
		int e = (int) (Math.random() * unique_events);
		int r = (int) (Math.random() * regions.size());
		switch(e) {
		case 0:
			active_events.add(new SoldierPayEvent(empire, 300 * ((month / 10) + 1), 100 * (month % 10)));
			break;
		case 1:
			active_events.add(new RegionPayEvent(empire, regions.get(r), 150 * ((month / 3) + 1), 2, 0.0005 * month));
			break;
		case 2:
			active_events.add(new GamesEvent(empire, map.getRegions(), (int) (10000 + (500 * (month / 5.0))), -0.01 * ((double) month / 37.0)));
			break;
		case 3:
			active_events.add(new PropagandaEvent(empire, map.getRegions(), (int) (1000 + (50 * (month / 5.0))), 0.005, 10 + (month % 10)));
			break;
		case 4:
			active_events.add(new MEconEvent(empire, map.getRegions(), (int) (2000 + (50 * (month / 5.0))), 0.005));
			break;
		case 5:
			active_events.add(new MoneyClaimEvent(empire, regions.get(r), (int) (1500 + (50 * (month / 5.0))), 20));
			break;
		}
	}
}
