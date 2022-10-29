package model.audio;

import model.Audio;
import model.audio.song.Genre;

public final class Song extends Audio {

    private Genre genre;

    public Song(String name, String imageURL, int duration, Genre genre, String owner) {
        super(name, imageURL, owner, duration);
        this.genre = genre;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

}
