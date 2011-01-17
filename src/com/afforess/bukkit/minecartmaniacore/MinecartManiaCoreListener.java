package com.afforess.bukkit.minecartmaniacore;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import com.afforess.bukkit.minecartmaniacore.event.MinecartActionEvent;

@SuppressWarnings("unused")
public class MinecartManiaCoreListener extends VehicleListener{
	private MinecartManiaCore core;
	
	public MinecartManiaCoreListener(MinecartManiaCore instance) {
		core = instance;
	}
	
	public void onVehicleMove(VehicleMoveEvent event) {
		if (event.getVehicle() instanceof Minecart) {
			Minecart cart = (Minecart)event.getVehicle();
			MinecartManiaMinecart minecart = MinecartManiaWorld.getMinecartManiaMinecart(cart);
			
			minecart.doRealisticFriction();
			minecart.doPressurePlateRails();
			if (minecart.isMoving()) {
				minecart.setPreviousFacingDir(minecart.getDirectionOfMotion());
			}
			else {
				minecart.doCatcherBlock();
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
    		MinecartManiaMinecart minecart = MinecartManiaWorld.getMinecartManiaMinecart(cart);
			Entity collisioner = event.getEntity();
			
			//TODO entity health does not appear to set correctly.
			/*if (collisioner instanceof LivingEntity) {
				LivingEntity victom = (LivingEntity)(collisioner);
				if (!(victom instanceof Player)) {
					if (MinecartManiaWorld.isMinecartsKillMobs()) {
						if (minecart.isMoving()) {
							victom.setHealth(0);
							event.setCancelled(true);
							event.setCollisionCancelled(true);
							event.setPickupCancelled(true);
						}
					}
				}
			}*/
			
			//TODO remove - Autocart can handle itself
			/*if (collisioner.getLocation().getBlockX() == cart.getLocation().getBlockX()) {
				if (collisioner.getLocation().getBlockY() == cart.getLocation().getBlockY()) {
					if (collisioner.getLocation().getBlockZ() == cart.getLocation().getBlockZ()) {
						event.setCancelled(true);
						event.setCollisionCancelled(true);
					}
				}
			}*/
    	}
    }

}
