package com.afforess.bukkit.minecartmaniacore;

import java.util.ArrayList;

import org.bukkit.block.Sign;

import com.afforess.bukkit.minecartmaniacore.MinecartManiaMinecart;

public class SignUtils {
	
	
	public static Sign getSignAt(int x, int y, int z) {
		if (MinecartManiaWorld.getBlockAt(x, y, z).getState() instanceof Sign) {
			return (Sign)MinecartManiaWorld.getBlockAt(x, y, z).getState();
		}
		return null;
	}
	
	public static ArrayList<Sign> getAdjacentSignList(MinecartManiaMinecart minecart, int range) {
		return getAdjacentSignList(minecart.getX(), minecart.getY(), minecart.getZ(), range);
	}
	
	public static ArrayList<Sign> getAdjacentSignList(int x, int y, int z, int range) {
		ArrayList<Sign> signList = new ArrayList<Sign>();
		for (int dx = -(range); dx <= range; dx++){
			for (int dy = -(range); dy <= range; dy++){
				for (int dz = -(range); dz <= range; dz++){
					Sign sign = getSignAt(x+dx, y+dy, z+dz);
					if (sign != null) {
						signList.add(sign);
					}
				}
			}
		}
		return signList;
	}
	
	public static ArrayList<Sign> getParallelSign(int x, int y, int z)
	{
		int range = 1;
		ArrayList<Sign> signList = new ArrayList<Sign>();
		for (int dx = -(range); dx <= range; dx++){
			for (int dz = -(range); dz <= range; dz++){
				Sign sign = getSignAt(x+dx, y, z+dz);
				if (sign != null) {
					signList.add(sign);
				}
			}
		}
		return signList;
	}
	
	public static String getNumber(String s)
	{
		String n = "";
		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if (Character.isDigit(c) || c == '.' || c == '-')
				n += c;
		}
		return n;
	}
	
}
