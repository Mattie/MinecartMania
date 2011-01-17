package com.afforess.bukkit.minecartmaniacore;

import org.bukkit.Material;
import org.bukkit.block.Block;


public class MinecartUtils {

	public static boolean isMinecartTrack(Block block) {
		if (block.getType().equals(Material.RAILS)) return true;
		if (MinecartManiaWorld.isPressurePlateRails()) {
			if (block.getType().equals(Material.STONE_PLATE)) return true;
			if (block.getType().equals(Material.WOOD_PLATE)) return true;
		}
		return false;
	}
	
	public static boolean validMinecartTrack(int x, int y, int z, int range, DirectionUtils.CompassDirection facingDir) {
    	if (!isMinecartTrack(MinecartManiaWorld.getBlockAt(x, y, z))) return false;
    	range--;
    	while (range > 0) {
    		if (facingDir == DirectionUtils.CompassDirection.NORTH) x--;
    		if (facingDir == DirectionUtils.CompassDirection.EAST) z--;
    		if (facingDir == DirectionUtils.CompassDirection.SOUTH) x++;
    		if (facingDir == DirectionUtils.CompassDirection.WEST) z++;
    		if (isMinecartTrack(MinecartManiaWorld.getBlockAt(x, y-1, z))) y--;
    		if (isMinecartTrack(MinecartManiaWorld.getBlockAt(x, y+1, z))) y++;
    		if (!isMinecartTrack(MinecartManiaWorld.getBlockAt(x, y, z))) return false;
    		
    		if (isMinecartTrack(MinecartManiaWorld.getBlockAt(x-1, y, z))) facingDir = DirectionUtils.CompassDirection.NORTH;
    		if (isMinecartTrack(MinecartManiaWorld.getBlockAt(x, y, z-1))) facingDir = DirectionUtils.CompassDirection.EAST;
    		if (isMinecartTrack(MinecartManiaWorld.getBlockAt(x+1, y, z))) facingDir = DirectionUtils.CompassDirection.SOUTH;
    		if (isMinecartTrack(MinecartManiaWorld.getBlockAt(x, y, z+1))) facingDir = DirectionUtils.CompassDirection.WEST;
    		range--;
    	}
    	
    	return true;
    }
	
	public static boolean isAtIntersection(int x, int y, int z) {
		int paths = 0;

		int data = MinecartManiaWorld.getBlockData(x, y, z);
		
		if (data == 0 || data == 1) {
			if (MinecartManiaWorld.getBlockAt(x, y, z-1).getType().equals(Material.RAILS)) {
				if (MinecartManiaWorld.getBlockData(x, y, z-1) == 0) {
					paths++;
				}
			}
			if (MinecartManiaWorld.getBlockAt(x, y, z+1).getType().equals(Material.RAILS)) {
				if (MinecartManiaWorld.getBlockData(x, y, z+1) == 0) {
					paths++;
				}
			}
			if (MinecartManiaWorld.getBlockAt(x-1, y, z).getType().equals(Material.RAILS)) {
				if (MinecartManiaWorld.getBlockData(x-1, y, z) == 1) {
					paths++;
				}
			}
			if (MinecartManiaWorld.getBlockAt(x+1, y, z).getType().equals(Material.RAILS)) {
				if (MinecartManiaWorld.getBlockData(x+1, y, z) == 1) {
					paths++;
				}
			}
		}
		
		else if (data == 6) {
			if (MinecartManiaWorld.getBlockAt(x+1, y, z).getType().equals(Material.RAILS) && MinecartManiaWorld.getBlockAt(x, y, z+1).getType().equals(Material.RAILS)) {
				if (MinecartManiaWorld.getBlockData(x+1, y, z) == 1 && MinecartManiaWorld.getBlockData(x, y, z+1) == 0) {
					paths = 2;
					if (MinecartManiaWorld.getBlockAt(x-1, y, z).getType().equals(Material.RAILS)) {
						if (MinecartManiaWorld.getBlockData(x-1, y, z) == 1) {
							paths++; 
						}
					}
					if (MinecartManiaWorld.getBlockAt(x, y, z-1).getType().equals(Material.RAILS)) {
						if (MinecartManiaWorld.getBlockData(x, y, z-1) == 0) {
							paths++;
						}
					}
				}
			}
		}
		else if (data == 7) {
			if (MinecartManiaWorld.getBlockAt(x-1, y, z).getType().equals(Material.RAILS) && MinecartManiaWorld.getBlockAt(x, y, z+1).getType().equals(Material.RAILS)) {
				if (MinecartManiaWorld.getBlockData(x-1, y, z) == 1 && MinecartManiaWorld.getBlockData(x, y, z+1) == 0) {
					paths = 2;
					if (MinecartManiaWorld.getBlockAt(x+1, y, z).getType().equals(Material.RAILS)) {
						if (MinecartManiaWorld.getBlockData(x+1, y, z) == 1) {
							paths++;
						}
					}
					if (MinecartManiaWorld.getBlockAt(x, y, z-1).getType().equals(Material.RAILS)) {
						if (MinecartManiaWorld.getBlockData(x, y, z-1) == 0) {
							paths++;
						}
					}
				}
			}
		}
		else if (data == 8) {
			if (MinecartManiaWorld.getBlockAt(x-1, y, z).getType().equals(Material.RAILS) && MinecartManiaWorld.getBlockAt(x, y, z-1).getType().equals(Material.RAILS)) {
				if (MinecartManiaWorld.getBlockData(x-1, y, z) == 1 && MinecartManiaWorld.getBlockData(x, y, z-1) == 0) {
					paths = 2;
					if (MinecartManiaWorld.getBlockAt(x+1, y, z).getType().equals(Material.RAILS)) {
						if (MinecartManiaWorld.getBlockData(x+1, y, z) == 1) {
							paths++;
						}
					}
					if (MinecartManiaWorld.getBlockAt(x, y, z+1).getType().equals(Material.RAILS)) {
						if (MinecartManiaWorld.getBlockData(x, y, z+1) == 0) {
							paths++;
						}
					}
				}
			}
		}
		else if (data == 9) {
			if (MinecartManiaWorld.getBlockAt(x+1, y, z).getType().equals(Material.RAILS) && MinecartManiaWorld.getBlockAt(x, y, z-1).getType().equals(Material.RAILS)) {
				if (MinecartManiaWorld.getBlockData(x+1, y, z) == 1 && MinecartManiaWorld.getBlockData(x, y, z-1) == 0) {
					paths = 2;
					if (MinecartManiaWorld.getBlockAt(x-1, y, z).getType().equals(Material.RAILS)) {
						if (MinecartManiaWorld.getBlockData(x-1, y, z) == 1) {
							paths++;
						}
					}
					if (MinecartManiaWorld.getBlockAt(x, y, z+1).getType().equals(Material.RAILS)) {
						if (MinecartManiaWorld.getBlockData(x, y, z+1) == 0) {
							paths++;
						}
					}
				}
			}
		}
		
		return paths > 2;
	}
}
