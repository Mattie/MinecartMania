package com.afforess.bukkit.minecartmaniacore;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.afforess.bukkit.minecartmaniacore.DirectionUtils.CompassDirection;


public class MinecartManiaMinecart {
	public final Minecart minecart;
	private static final double maxMomentum = 1E308;
	private Vector previousLocation;
	private Vector previousMotion;
	private DirectionUtils.CompassDirection previousFacingDir = DirectionUtils.CompassDirection.NO_DIRECTION;
	
	private HashMap<String, Object> data = new HashMap<String,Object>();
	
	public MinecartManiaMinecart(Minecart cart) {
		minecart = cart;
		previousMotion = cart.getVelocity().clone();
		previousLocation = cart.getLocation().toVector().clone();
	}
	
	public Vector getPreviousLocation() {
		return previousLocation.clone();
	}
	
	public void updateLocation() {
		previousLocation = minecart.getLocation().toVector().clone();
	}
	
	public Vector getPreviousMotion() {
		return previousMotion.clone();
	}
	
	public void updateMotion() {
		previousMotion = minecart.getVelocity().clone();
	}
	
	public boolean hasChangedPosition() {
		if (getPreviousLocation().getBlockX() != minecart.getLocation().getBlockX()) {
			return true;
		}
		if (getPreviousLocation().getBlockY() != minecart.getLocation().getBlockY()) {
			return true;
		}
		if (getPreviousLocation().getBlockZ() != minecart.getLocation().getBlockZ()) {
			return true;
		}
		
		return false;
	}
	
	public double getMotionX() {
		return minecart.getVelocity().getX();
	}
	
	public double getMotionY() {
		return minecart.getVelocity().getY();
	}
	
	public double getMotionZ() {
		return minecart.getVelocity().getZ();
	}
	
	public void setMotionX(double motionX){
		motionX = MathUtils.range(motionX, maxMomentum, -maxMomentum);
		setMotion(motionX, getMotionY(), getMotionZ());
	}
	
	public void setMotionY(double motionY){
		motionY = MathUtils.range(motionY, maxMomentum, -maxMomentum);
		setMotion(getMotionX(), motionY, getMotionZ());
	}
	
	public void setMotionZ(double motionZ){
		motionZ = MathUtils.range(motionZ, maxMomentum, -maxMomentum);
		setMotion(getMotionX(), getMotionY(), motionZ);
	}
	
	private void setMotion(double motionX, double motionY, double motionZ) {
		Vector newVelocity = new Vector();
		newVelocity.setX(motionX);
		newVelocity.setY(motionY);
		newVelocity.setZ(motionZ);
		minecart.setVelocity(newVelocity);
	}
	
	public void stopCart() {
		setMotion(0D, 0D, 0D);
	}
	
	public boolean isMoving() {
		return getMotionX() != 0D || getMotionY() != 0D || getMotionZ() != 0D;
	}
	
	public int getX(){
		return minecart.getLocation().getBlockX();
	}
	
	public int getY(){
		return minecart.getLocation().getBlockY();
	}
	
	public int getZ(){
		return minecart.getLocation().getBlockZ();
	}
	
	public void setPreviousFacingDir(DirectionUtils.CompassDirection dir) {
		previousFacingDir = dir;
	}

	public DirectionUtils.CompassDirection getPreviousFacingDir() {
		return previousFacingDir;
	}
	
	/**
	 ** Returns the value from the loaded data
	 ** @param the string key the data value is associated with
	 **/
	 public Object getDataValue(String key) {
		 if (data.containsKey(key)) {
			 return data.get(key);
		 }
		 return null;
	 }
	 
	/**
	 ** Creates a new data value if it does not already exists, or resets an existing value
	 ** @param the string key the data value is associated with
	 ** @param the value to store
	 **/	 
	 public void setDataValue(String key, Object value) {
		 data.put(key, value);
	 }
	
	public int getBlockIdBeneath() {
		return MinecartManiaWorld.getBlockAt(getX(), getY()-1, getZ()).getTypeId();
	}
	
	public boolean isPoweredBeneath() {
		return MinecartManiaWorld.isBlockIndirectlyPowered(getX(), getY()-1, getZ()) || MinecartManiaWorld.isBlockIndirectlyPowered(getX(), getY(), getZ());
	}
	
	public boolean doReverse() {
		if (getBlockIdBeneath() == MinecartManiaWorld.getReverseBlockId() && !isPoweredBeneath())
    	{
    		setMotionX(getMotionX() * -1);
    		setMotionZ(getMotionZ() * -1);
    		return true;
    	}
		return false;
	}

	public boolean doLowSpeedBrake() {
		if (getBlockIdBeneath() == MinecartManiaWorld.getLowSpeedBrakeBlockId() && !isPoweredBeneath())
    	{
    		setMotionX(getMotionX() / 2D);
    		setMotionZ(getMotionZ() / 2D);
    		return true;
    	}
		return false;
	}

	public boolean doHighSpeedBrake() {
		if (getBlockIdBeneath() == MinecartManiaWorld.getHighSpeedBrakeBlockId() && !isPoweredBeneath())
    	{
    		setMotionX(getMotionX() / 8D);
    		setMotionZ(getMotionZ() / 8D);
    		return true;
    	}
		return false;
	}

	public boolean doLowSpeedBooster() {
		if (getBlockIdBeneath() == MinecartManiaWorld.getLowSpeedBoosterBlockId() && !isPoweredBeneath())
    	{
    		setMotionX(getMotionX() * 2D);
    		setMotionZ(getMotionZ() * 2D);
    		return true;
    	}
		return false;
	}
	
	public boolean doHighSpeedBooster() {
		if (getBlockIdBeneath() == MinecartManiaWorld.getHighSpeedBoosterBlockId() && !isPoweredBeneath())
    	{
    		setMotionX(getMotionX() * 8D);
    		setMotionZ(getMotionZ() * 8D);
    		return true;
    	}
		return false;
	}

	public boolean doCatcherBlock() {
		if (getBlockIdBeneath() == MinecartManiaWorld.getCatcherBlockId())
 		{
			if (isPoweredBeneath()) {
				if (!isMoving()) {
					launchCart();
					return true;
				}
			}
			else {
				stopCart();
				return true;
			}
		}
		return false;
	}
	
	private void launchCart() {
		ArrayList<Sign> signList = SignUtils.getAdjacentSignList(this, 1);
		for (Sign sign : signList) {
			for (int i = 0; i < 4; i++) {
				if (sign.getLine(i).toLowerCase().indexOf("north") > -1) {
					if (MinecartUtils.validMinecartTrack(getX()-1, getY(), getZ(), 2, DirectionUtils.CompassDirection.NORTH)) {
						sign.setLine(i, "[North]");
						sign.update();
						setMotion(DirectionUtils.CompassDirection.NORTH, 0.6D);
						return;
					}
				}
				if (sign.getLine(i).toLowerCase().indexOf("east") > -1) {
					if (MinecartUtils.validMinecartTrack(getX(), getY(), getZ()-1, 2, DirectionUtils.CompassDirection.EAST)) {
						sign.setLine(i, "[East]");
						sign.update();
						setMotion(DirectionUtils.CompassDirection.EAST, 0.6D);
						return;
					}
				}
				if (sign.getLine(i).toLowerCase().indexOf("south") > -1) {
					if (MinecartUtils.validMinecartTrack(getX()+1, getY(), getZ(), 2, DirectionUtils.CompassDirection.SOUTH)) {
						sign.setLine(i, "[South]");
						sign.update();
						setMotion(DirectionUtils.CompassDirection.SOUTH, 0.6D);
						return;
					}
				}
				if (sign.getLine(i).toLowerCase().indexOf("west") > -1) {
					if (MinecartUtils.validMinecartTrack(getX(), getY(), getZ()+1, 2, DirectionUtils.CompassDirection.WEST)) {
						sign.setLine(i, "[West]");
						sign.update();
						setMotion(DirectionUtils.CompassDirection.WEST, 0.6D);
						return;
					}
				}
			}
		}
		
		if (MinecartUtils.validMinecartTrack(getX()-1, getY(), getZ(), 2, DirectionUtils.CompassDirection.NORTH)) {
			setMotion(DirectionUtils.CompassDirection.NORTH, 0.6D);
		}
		else if (MinecartUtils.validMinecartTrack(getX(), getY(), getZ()-1, 2, DirectionUtils.CompassDirection.EAST)) {
			setMotion(DirectionUtils.CompassDirection.EAST, 0.6D);
		}
		else if (MinecartUtils.validMinecartTrack(getX()+1, getY(), getZ(), 2, DirectionUtils.CompassDirection.SOUTH)) {
			setMotion(DirectionUtils.CompassDirection.SOUTH, 0.6D);
		}
		else if (MinecartUtils.validMinecartTrack(getX(), getY(), getZ()+1, 2, DirectionUtils.CompassDirection.WEST)) {
			setMotion(DirectionUtils.CompassDirection.WEST, 0.6D);
		}
	}

	public void setMotion(CompassDirection direction, double speed) {
		if (direction.equals(DirectionUtils.CompassDirection.NORTH))
			setMotionX(-speed);	
		else if (direction.equals(DirectionUtils.CompassDirection.SOUTH))
			setMotionX(speed);
		else if (direction.equals(DirectionUtils.CompassDirection.EAST))
			setMotionZ(-speed);	
		else if (direction.equals(DirectionUtils.CompassDirection.WEST))
			setMotionZ(speed);	
		else
			throw new IllegalArgumentException();
	}

	public DirectionUtils.CompassDirection getDirectionOfMotion()
	{
		if (getMotionX() < 0.0D) return DirectionUtils.CompassDirection.NORTH;
		if (getMotionZ() < 0.0D) return DirectionUtils.CompassDirection.EAST;
		if (getMotionX() > 0.0D) return DirectionUtils.CompassDirection.SOUTH;
		if (getMotionZ() > 0.0D) return DirectionUtils.CompassDirection.WEST;
		return DirectionUtils.CompassDirection.NO_DIRECTION;
	}
	
	public void doRealisticFriction() {
		if (minecart.getPassenger() == null && MinecartManiaWorld.getBlockAt(getX(), getY(), getZ()).getType().equals(Material.RAILS)) 	{
			setMotion(getMotionX() * 1.03774, getMotionY(), getMotionZ()* 1.03774);
    	}
	}

	public boolean doEjectorBlock() {
		if (getBlockIdBeneath() == MinecartManiaWorld.getEjectorBlockId() && !isPoweredBeneath()) {
			if (minecart.getPassenger() != null) {
				return minecart.eject();
			}
		}
		return false;
	}
	
	public boolean hasPlayerPassenger() {
		return getPlayerPassenger() != null;
	}
	
	public Player getPlayerPassenger() {
		if (minecart.getPassenger() == null) {
			return null;
		}
		if (minecart.getPassenger() instanceof Player) {
			return (Player)minecart.getPassenger();
		}
		return null;
	}
	
	public boolean isOnRails() {
		return MinecartUtils.isMinecartTrack(MinecartManiaWorld.getBlockAt(getX(), getY(), getZ()));
	}
	
	/**
	 ** Determines whether or not the track the minecart is currently on is the center piece of a large track intersection. Returns true if it is an intersection.
	 **/
	public boolean isAtIntersection() {
		if (this.isOnRails()) {
			return MinecartUtils.isAtIntersection(getX(), getY(), getZ());
		}
		return false;
	}
	
	public Block getBlockTypeAhead() {
		return DirectionUtils.getBlockTypeAhead(getDirectionOfMotion(), getX(), getY(), getZ());
	}
	
	public Block getBlockTypeBehind() {
		return DirectionUtils.getBlockTypeAhead(DirectionUtils.getOppositeDirection(getDirectionOfMotion()), getX(), getY(), getZ());
	}

	public void doPressurePlateRails() {
		if (MinecartManiaWorld.isPressurePlateRails()) {
			if (MinecartManiaWorld.getBlockAt(getX(), getY(), getZ()).getType().equals(Material.STONE_PLATE)
			|| MinecartManiaWorld.getBlockAt(getX(), getY(), getZ()).getType().equals(Material.WOOD_PLATE)) {
				setMotionX(getMotionX() / 0.535D);
				setMotionZ(getMotionZ() / 0.535D);
				if (getBlockTypeAhead() != null) {
					if (getBlockTypeAhead().getType().equals(Material.STONE_PLATE) || getBlockTypeAhead().getType().equals(Material.WOOD_PLATE)){
						setMotionX(getMotionX() * 2.5D);
						setMotionZ(getMotionZ() * 2.5D);
					}
				}
	    	}
		}
	}
}
