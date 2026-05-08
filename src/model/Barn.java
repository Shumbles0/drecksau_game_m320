package model;


public class Barn {

    private boolean hasDoor;
    private boolean hasLightningRod;

    public Barn() {
        this.hasDoor = false;
        this.hasLightningRod = false;
    }

    public boolean hasDoor()         { return hasDoor; }
    public boolean hasLightningRod() { return hasLightningRod; }

    public void addDoor()         { this.hasDoor = true; }
    public void addLightningRod() { this.hasLightningRod = true; }
}