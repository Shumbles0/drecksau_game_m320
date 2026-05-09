package model;

public class Pig {

    private boolean dirty;
    private Barn barn;

    public Pig() {
        this.dirty = false;
        this.barn = null;
    }

    public boolean isDirty()  { return dirty; }
    public boolean isClean()  { return !dirty; }
    public boolean isInBarn() { return barn != null; }
    public Barn getBarn()     { return barn; }

    public void makeDirty() { this.dirty = true; }
    public void makeClean() { this.dirty = false; }

    public void buildBarn()   { this.barn = new Barn(); }
    public void destroyBarn() { this.barn = null; }

    public boolean isProtectedFromRain() {
        return barn != null;
    }

    public boolean isProtectedFromFarmer() {
        return barn != null && barn.hasDoor();
    }

    public boolean isBarnProtectedFromLightning() {
        return barn != null && barn.hasLightningRod();
    }
}