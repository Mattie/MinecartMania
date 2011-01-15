package com.afforess.bukkit.minecartmaniacore;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.block.Block;

public abstract class DirectionUtils {
	 public enum CompassDirection {
	        NO_DIRECTION(-1),
	        NORTH(0),
	        NORTH_EAST(1),
	        EAST(2),
	        SOUTH_EAST(3),
	        SOUTH(4),
	        SOUTH_WEST(5),
	        WEST(6),
	        NORTH_WEST(7)
	        ;
	        private int id;
	        private static Map<Integer, CompassDirection> map;

	        private CompassDirection(int id){
	            this.id = id;
	            add( id, this );
	        }

	        private static void add( int type, CompassDirection name ) {
	            if (map == null) {
	                map = new HashMap<Integer, CompassDirection>();
	            }

	            map.put(type, name);
	        }

	        public int getType() {
	            return id;
	        }

	        public static CompassDirection fromId(final int type) {
	            return map.get(type);
	        }
	
	    }
	 
	 public static boolean isEqualOrNoDirection(CompassDirection e1, CompassDirection e2)
	 {
		if (e1 == CompassDirection.NO_DIRECTION) return true;
		if (e2 == CompassDirection.NO_DIRECTION) return true;
		if (e1 == e2) return true;
		return false;
	 }
	 
	 public static CompassDirection getLeftDirection(CompassDirection efacingDir) {
		 if (efacingDir == CompassDirection.NORTH) {
			 return CompassDirection.WEST;
		 }
		 if (efacingDir == CompassDirection.EAST) {
			 return CompassDirection.NORTH;
		 }
		 if (efacingDir == CompassDirection.SOUTH) {
			 return CompassDirection.EAST;
		 }
		 if (efacingDir == CompassDirection.WEST) {
			 return CompassDirection.SOUTH;
		 }
		 return CompassDirection.NO_DIRECTION;
	 }
	 
	 public static CompassDirection getRightDirection(CompassDirection efacingDir) {
		 if (efacingDir == CompassDirection.NORTH) {
			 return CompassDirection.EAST;
		 }
		 if (efacingDir == CompassDirection.EAST) {
			 return CompassDirection.SOUTH;
		 }
		 if (efacingDir == CompassDirection.SOUTH) {
			 return CompassDirection.WEST;
		 }
		 if (efacingDir == CompassDirection.WEST) {
			 return CompassDirection.NORTH;
		 }
		 return CompassDirection.NO_DIRECTION;
	 }
	 
	 public static Block getBlockTypeAhead(CompassDirection efacingDir, int x, int y, int z) {
			if (efacingDir == CompassDirection.NORTH) return MinecartManiaWorld.getBlockAt(x-1, y, z);
			if (efacingDir == CompassDirection.EAST) return MinecartManiaWorld.getBlockAt(x, y, z-1);
			if (efacingDir == CompassDirection.SOUTH) return MinecartManiaWorld.getBlockAt(x+1, y, z);
			if (efacingDir == CompassDirection.WEST) return MinecartManiaWorld.getBlockAt(x, y, z+1);
			return null;
		}
	 
	 public static int getMinetrackRailDataForDirection(CompassDirection eOverrideDir, CompassDirection eFacingDir)
	 {
		 if (eFacingDir == CompassDirection.NORTH) {
			 if (eOverrideDir == CompassDirection.EAST) {
				 return 9;
			 }
			 if (eOverrideDir == CompassDirection.NORTH) {
				 return 1;
			 }
			 if (eOverrideDir == CompassDirection.WEST) {
				 return 6;
			 }
		 }
		 if (eFacingDir == CompassDirection.EAST) {
			 if (eOverrideDir == CompassDirection.EAST) {
				 return 0;
			 }
			 if (eOverrideDir == CompassDirection.NORTH) {
				 return 7;
			 }
			 if (eOverrideDir == CompassDirection.SOUTH) {
				 return 6;
			 }
		 }
		 if (eFacingDir == CompassDirection.WEST) {
			 if (eOverrideDir == CompassDirection.WEST) {
				 return 0;
			 }
			 if (eOverrideDir == CompassDirection.NORTH) {
				 return 8;
			 }
			 if (eOverrideDir == CompassDirection.SOUTH) {
				 return 9;
			 }
		 }
		 if (eFacingDir == CompassDirection.SOUTH) {
			 if (eOverrideDir == CompassDirection.WEST) {
				 return 7;
			 }
			 if (eOverrideDir == CompassDirection.EAST) {
				 return 8;
			 }
			 if (eOverrideDir == CompassDirection.SOUTH) {
				 return 1;
			 }
		 }
		 return -1;
	 }

	public static CompassDirection getOppositeDirection(
			CompassDirection directionOfMotion) {
		int val = directionOfMotion.getType();
		if (val < 4)
			val += 4;
		else
			val -= 4;
		return CompassDirection.fromId(val);
	}
	
	public static CompassDirection getDirectionFromRotation(double degrees) {
		while (degrees < 0D) {
			degrees += 360D;
		}
		while (degrees > 360D) {
			degrees -= 360D;
		}
		if (0 <= degrees && degrees < 45) {
            return CompassDirection.NORTH;
        } else if (45 <= degrees && degrees < 135) {
            return CompassDirection.EAST;
        } else if (135 <= degrees && degrees < 225) {
            return CompassDirection.SOUTH;
        } else if (225 <= degrees && degrees < 315) {
        	return CompassDirection.WEST;
        } else if (315 <= degrees && degrees <= 360.0) {
        	return CompassDirection.NORTH;
        }
		
		return CompassDirection.NO_DIRECTION;
	}
}
