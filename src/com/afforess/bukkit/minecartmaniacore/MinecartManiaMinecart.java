package com.afforess.bukkit.minecartmaniacore;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.Minecart;
import org.bukkit.Player;
import org.bukkit.Vector;

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
		if (getBlockIdBeneath() == MinecartManiaWorld.getCatcherBlockId() && !isPoweredBeneath())
		{
			stopCart();
			return true;
		}
		return false;
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
		//if (DataUtils.isRealisticFriction()) {
		{
			if (minecart.getPassenger() == null && MinecartManiaWorld.getBlockAt(getX(), getY(), getZ()).getType().equals(Material.RAILS)) 	{
				setMotion(getMotionX() * 1.03774, getMotionY(), getMotionZ()* 1.03774);
	    	}
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
		return MinecartManiaWorld.getBlockAt(getX(), getY(), getZ()).getTypeId() == Material.RAILS.getId();
	}
	
	public boolean isAtIntersection() {
		
		if (this.isOnRails()) {
			int paths = 0;
			if (MinecartManiaWorld.getBlockAt(getX(), getY(), getZ()+1).getType().equals(Material.RAILS)) {
				paths++;
			}
			if (MinecartManiaWorld.getBlockAt(getX(), getY(), getZ()-1).getType().equals(Material.RAILS)) {
				paths++;
			}
			if (MinecartManiaWorld.getBlockAt(getX()+1, getY(), getZ()).getType().equals(Material.RAILS)) {
				paths++;
			}
			if (MinecartManiaWorld.getBlockAt(getX()-1, getY(), getZ()).getType().equals(Material.RAILS)) {
				paths++;
			}
			return paths > 2;
		}
		return false;
	}
}
