package model.audio;

import model.Audio;
import model.audio.podcast.Category;

public final class Podcast extends Audio {

    private String description;
    private Category category;

    public Podcast(String name, String imageURL, int duration, String description, Category category, String owner) {
        super(name, imageURL, owner, duration);
        this.description = description;
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
