package model;

import model.audio.Podcast;
import model.audio.Song;

public abstract class Audio {

    private String name;
    private String imageURL;
    private String owner;
    private String id;
    private int duration;

    public static final Class<?>[] children = { Song.class, Podcast.class };

    public Audio(String name, String imageURL, String owner, int duration) {
        this.name = name;
        this.imageURL = imageURL;
        this.owner = owner;
        this.duration = duration;
        this.id = UtilMatrix.generateCode(1, UtilMatrix.randomMatrix(6, 6));
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

    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        String value = name +  " - by: " + owner;
        return value;
    }
}
