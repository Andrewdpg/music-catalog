package model.user;

import model.User;

public abstract class Producer extends User {

    private String name;
    private String imageURL;
    private int plays;
    private int playingTime;

    public Producer(String nickname, String name, String imageURL) {
        super(nickname);
        this.name = name;
        this.imageURL = imageURL;
        this.plays = 0;
        this.playingTime = 0;
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

    public int getPlays() {
        return plays;
    }

    public int getPlayingTime() {
        return playingTime;
    }

    public void addPlay() {
        this.plays += 1;
    }

    public void addPlayingTime(int time) {
        this.playingTime += time;
    }

}
