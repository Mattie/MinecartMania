package com.afforess.bukkit.minecartmaniacore;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import com.afforess.bukkit.minecartmaniacore.event.MinecartActionEvent;

public class MinecartManiaCoreListener extends VehicleListener{
	@SuppressWarnings("unused")
	private MinecartManiaCore core;
	
	public MinecartManiaCoreListener(MinecartManiaCore instance) {
		core = instance;
	}
	
	public void onVehicleMove(VehicleMoveEvent event) {
		if (event.getVehicle() instanceof Minecart) {
			Minecart cart = (Minecart)event.getVehicle();
			MinecartManiaMinecart minecart = MinecartManiaWorld.getMinecartManiaMinecart(cart);
			
			minecart.doRealisticFriction();
			if (minecart.isMoving()) {
				minecart.setPreviousFacingDir(minecart.getDirectionOfMotion());
			}
			if (minecart.hasChangedPosition()) {
				
				MinecartActionEvent e = new MinecartActionEvent(minecart);
				MinecartManiaCore.server.getPluginManager().callEvent(e);
				
				boolean action = e.isActionTaken();
		    	if (!action) {
		    		action = minecart.doHighSpeedBooster();
		    	}
		    	if (!action) {
		    		action = minecart.doLowSpeedBooster();
		    	}
		    	if (!action) {
		    		action = minecart.doHighSpeedBrake();
		    	}
		    	if (!action) {
		    		action = minecart.doLowSpeedBrake();
		    	}
		    	if (!action) {
		    		action = minecart.doReverse();
		    	}
		    	if (!action) {
		    		action = minecart.doCatcherBlock();
		    	}
		    	if (!action) {
		    		action = minecart.doEjectorBlock();
		    	}
				
				minecart.updateMotion();
				minecart.updateLocation();
			}
		}
    }
	
    public void onVehicleEntityCollision(VehicleEntityCollisionEvent event) {
    	if (event.getVehicle() instanceof Minecart) {
    		Minecart cart = (Minecart)event.getVehicle();
			Entity collisioner = event.getEntity();
			if (collisioner.getLocation().getBlockX() == cart.getLocation().getBlockX()) {
				if (collisioner.getLocation().getBlockY() == cart.getLocation().getBlockY()) {
					if (collisioner.getLocation().getBlockZ() == cart.getLocation().getBlockZ()) {
						event.setCancelled(true);
					}
				}
			}
    	}
    }

}
