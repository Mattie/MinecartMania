package com.afforess.bukkit.minecartmaniacore;

import com.afforess.bukkit.minecartmaniacore.event.MinecartActionEvent;
import com.afforess.bukkit.minecartmaniacore.event.MinecartManiaListener;

public class MinecartManiaActionListener extends MinecartManiaListener{

	public void onMinecartActionEvent(MinecartActionEvent event) {
		//System.out.println("Minecart action attempted. Minecart ID: " + event.getMinecart().minecart.getEntityID());
	}
}
