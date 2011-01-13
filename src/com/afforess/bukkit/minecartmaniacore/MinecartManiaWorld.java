package com.afforess.bukkit.minecartmaniacore;

import java.util.HashMap;

import org.bukkit.Block;
import org.bukkit.Minecart;
import org.bukkit.World;

public class MinecartManiaWorld {
	private static HashMap<Integer,MinecartManiaMinecart> minecarts = new HashMap<Integer,MinecartManiaMinecart>();
	private static HashMap<String, Object> configuration = new HashMap<String,Object>();
	
	/**
	 ** Returns a new MinecartManiaMinecart from storage if it already exists, or creates and stores a new MinecartManiaMinecart object, and returns it
	 ** 
	 **/
	 public static MinecartManiaMinecart getMinecartManiaMinecart(Minecart minecart) {
        MinecartManiaMinecart testMinecart = minecarts.get(new Integer(minecart.getEntityID()));
        if (testMinecart == null) {
        	MinecartManiaMinecart newCart = new MinecartManiaMinecart(minecart);
        	minecarts.put(new Integer(minecart.getEntityID()), newCart);
        	return newCart;
        } else {
           return testMinecart;
        }
    }
	 
	 /**
	 ** Returns true if the Minecart with the given entityID was deleted, false if not.
	 ** 
	 **/
	 public static boolean delMinecartManiaMinecart(int entityID) {
        if (minecarts.containsKey(new Integer(entityID))) {
            minecarts.remove(new Integer(entityID));
            return true;
        }
        return false;
    }
	 
	 public static Object getConfigurationValue(String key) {
		 if (configuration.containsKey(key)) {
			 return configuration.get(key);
		 }
		 return null;
	 }
	 
	 public static void setConfigurationValue(String key, Object value) {
		 configuration.put(key, value);
	 }
	 
	 public static int getIntValue(Object o) {
		 if (o != null) {
			if (o instanceof Integer) {
				return ((Integer)o).intValue();
			}
		}
		return 0;
	 }
	 
	 
	public static int getReverseBlockId() {
		return getIntValue(getConfigurationValue("reverse block"));
	}
	
	public static int getHighSpeedBoosterBlockId() {
		return getIntValue(getConfigurationValue("high speed booster block"));
	}
	
	public static int getLowSpeedBoosterBlockId() {
		return getIntValue(getConfigurationValue("low speed booster block"));
	}
	
	public static int getHighSpeedBrakeBlockId() {
		return getIntValue(getConfigurationValue("high speed brake block"));
	}
	
	public static int getLowSpeedBrakeBlockId() {
		return getIntValue(getConfigurationValue("low speed brake block"));
	}
	
	public static int getCatcherBlockId() {
		return getIntValue(getConfigurationValue("catcher block"));
	}
	
	public static int getEjectorBlockId() {
		return getIntValue(getConfigurationValue("ejector block"));
	}
	 
	public static World getWorld() {
		return MinecartManiaCore.server.getWorlds()[0];
	}

	public static Block getBlockAt(int x, int y, int z) {
		return getWorld().getBlockAt(x, y, z);
	}
	
	public static void setBlockAt(int type, int x, int y, int z) {
		//TODO implementation...
	}
	
	public static byte getBlockData(int x, int y, int z) {
		return getWorld().getBlockAt(x, y, z).getData();
	}
	
	public static void setBlockData(int x, int y, int z, int data) {
		getWorld().getBlockAt(x, y, z).setData((byte) (data));
	}
	
	public static boolean isBlockIndirectlyPowered(int x, int y, int z) {
			return false;
		}
}
