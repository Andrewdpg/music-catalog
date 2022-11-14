package model.audio;

import model.Audio;
import model.audio.song.Genre;

public final class Song extends Audio {

    private Genre genre;
    private double price;
    private int sales;

    public Song(String name, String imageURL, int duration, Genre genre, String owner, double price) {
        super(name, imageURL, owner, duration);
        this.genre = genre;
        this.price = price;
        this.sales = 0;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public Enum<?> getClassication() {
        return this.genre;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }

    public int getSales(){
        return this.sales;
    }

    public void increaseSales(){
        this.sales++;
    }

    public double getErnings() {
        return sales * price;
    }
}
