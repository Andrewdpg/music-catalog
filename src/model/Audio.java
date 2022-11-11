package model;

import model.audio.IPlayable;
import model.audio.Podcast;
import model.audio.Song;

public abstract class Audio implements IPlayable{

    private String name;
    private String imageURL;
    private String owner;
    private String id;
    private int played;
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
        String value = name + " - by: " + owner;
        return value;
    }

    @Override
    public void play() {
        int seconds = 0;
        int minutes = 0;
        while (played < getDuration()) {
            try {
                minutes = played / 60000;
                seconds = (played / 1000) - minutes * 60;
                System.out.print("Playing: " + getName() + " - " + minutes + ":"
                        + (String.valueOf(seconds).length() < 2 ? "0" + seconds : seconds) + "\r");
                Thread.sleep(1000);
                played += getDuration() - played < 1000 ? getDuration() - played : 1000;
            } catch (Exception e) {

            }
        }
        played = 0;
    }
}
