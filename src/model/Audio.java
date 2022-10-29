package model;

public abstract class Audio {

    private String name;
    private String imageURL;
    private String owner;
    private int duration;

    

    public Audio(String name, String imageURL, String owner, int duration) {
        this.name = name;
        this.imageURL = imageURL;
        this.owner = owner;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
