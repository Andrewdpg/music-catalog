package model;

import model.audio.IPlayable;
import model.audio.Podcast;
import model.audio.Song;
import model.audio.podcast.Category;
import model.audio.song.Genre;

public abstract class Audio implements IPlayable, Comparable<Audio> {

    private String name;
    private String imageURL;
    private String owner;
    private String id;
    private int timesReproduced;
    private int duration;

    public static final Class<?>[] children = { Song.class, Podcast.class };
    public static final Class<?>[] classifications = { Genre.class, Category.class };

    public Audio(String name, String imageURL, String owner, int duration) {
        this.name = name;
        this.imageURL = imageURL;
        this.owner = owner;
        this.duration = duration;
        this.timesReproduced = 0;
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

    public int getTimesReproduced() {
        return timesReproduced;
    }

    public void increaseReproductions() {
        timesReproduced++;
    }

    @Override
    public String toString() {
        return name + " - by: " + owner;
    }

    @Override
    public void play() {
        int played = 0;
        int seconds = 0;
        int minutes = 0;
        while (played < getDuration() || played < 5000) {
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
    }

    public abstract Enum<?> getClassication();

    @Override
    public int compareTo(Audio o) {
        int result = 0;
        if(getTimesReproduced() > o.getTimesReproduced()){
            result = -1;
        }else if(getTimesReproduced() == o.getTimesReproduced()){
            result = 0;
        }else{
            result = 1;
        }
        return result;
    }

    
}
