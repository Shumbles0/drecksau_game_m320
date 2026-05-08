package model;


public class Barn {

    private boolean hasDoor;
    private boolean hasLightningRod;
    private boolean isOnFire;

    public Barn() {
        this.hasDoor = false;
        this.hasLightningRod = false;
        this.isOnFire = false;
    }

    public boolean hasDoor()         { return hasDoor; }
    public boolean hasLightningRod() { return hasLightningRod; }
    public boolean isBurning() { return isOnFire; }

    public void addDoor()         { this.hasDoor = true; }
    public void addLightningRod() { this.hasLightningRod = true; }
    public void commitArson() { this.isOnFire = true; }

    public void extinguishFire() { this.isOnFire = false;}

    public void extinguishFireRain() {
        this.isOnFire = false;
        //TODO implement a tick of damage when this is used
    }
}