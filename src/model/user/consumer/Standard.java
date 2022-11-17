package model.user.consumer;

import model.Ad;
import model.user.Consumer;

public final class Standard extends Consumer {

    private static final int MAX_SONGS = 100;
    private static final int MAX_PLAYLIST = 20;

    private double adPercentage;

    public Standard(String nickname, String id) {
        super(nickname, id);
        this.adPercentage = 0.0;
    }

    /**
     * Increases the load of an Ad. iof the percentage is over 1 (100%), it will return an Ad.
     * @param percengate percentage to increase
     * @return An ad in case it reacehs 100%, empty String otherwise.
     */
    public String increaseAdPercentage(double percengate) {
        String value = "";
        adPercentage += percengate;
        if (getAdPercentage() >= 1.0) {
            value = Ad.getAdd();
            adPercentage = 0.0;
        }
        return value;
    }

    public double getAdPercentage() {
        return this.adPercentage;
    }

    @Override
    public boolean canPurchaseASong() {
        return super.getPurchasedSongs().size() < MAX_SONGS;
    }

    @Override
    public boolean canCreateAPlaylist() {
        return super.getPlaylists().size() < MAX_PLAYLIST;
    }

}
