package com.afforess.bukkit.minecartmaniacore.event;
import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;


public class MinecartManiaListener implements CustomEventListener, Listener{

	public MinecartManiaListener() {
		
	}
	

	/**
     * Called when a minecart attempts to perform any action. Only occurs after a minecart changes position
     * 
     * @param event
     */
	public void onMinecartActionEvent(MinecartActionEvent event) {
		
	}
	
	/**
     * Avoid using.
     * 
     * @param event
     */
	public void onCustomEvent(Event event) {
		if (event instanceof MinecartActionEvent) {
			onMinecartActionEvent((MinecartActionEvent)event);
		}
	}

}
