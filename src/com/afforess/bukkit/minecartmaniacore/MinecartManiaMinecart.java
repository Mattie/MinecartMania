package com.afforess.bukkit.minecartmaniacore;

import org.bukkit.Material;
import org.bukkit.Minecart;
import org.bukkit.Vector;

public class MinecartManiaMinecart {
	public final Minecart minecart;
	private static final double maxMomentum = 1E308;
	private Vector previousLocation;
	private Vector previousMotion;
	
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
	
	public int getX(){
		return minecart.getLocation().getBlockX();
	}
	
	public int getY(){
		return minecart.getLocation().getBlockY();
	}
	
	public int getZ(){
		return minecart.getLocation().getBlockZ();
	}
	
	private int getBlockIdBeneath() {
		return MinecartManiaWorld.getBlockAt(getX(), getY()-1, getZ()).getTypeID();
	}
	
	private boolean isPoweredBeneath() {
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
			if (minecart.getPassenger() == null && MinecartManiaWorld.getBlockAt(getX(), getY(), getZ()).getType().equals(Material.Rails)) 	{
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
	
	public boolean isOnRails() {
		return MinecartManiaWorld.getBlockAt(getX(), getY(), getZ()).getTypeID() == Material.Rails.getID();
	}
}
