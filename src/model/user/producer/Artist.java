package model.user.producer;

import model.Audio;
import model.audio.Song;
import model.user.Producer;

public final class Artist extends Producer {

    public Artist(String nickname, String name, String imageURL) {
        super(nickname, name, imageURL);
    }

    /**
     * Increases the selling count for a specific Audio
     * @param audio Audio that was sold 
     */
    public void addSale(Audio audio) {
        if (getAudios().get(getAudios().indexOf(audio)) instanceof Song) {
            ((Song) getAudios().get(getAudios().indexOf(audio))).increaseSales();
        }
    }

}
