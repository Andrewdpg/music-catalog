package model;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ConsumerUser extends User {

    private List<String> playlists;
    private Map<String, Timestamp> purchasedSongs;
    private Preferences preferences;
    private String id;

    public ConsumerUser(String nickname, String id) {
        super(nickname);
        this.id = id;
        this.playlists = new ArrayList<String>();
        this.purchasedSongs = new HashMap<String, Timestamp>();
        this.preferences = new Preferences();
    }

    public List<String> getPlaylists() {
        return playlists;
    }

    public Map<String, Timestamp> getPurchasedSongs() {
        return purchasedSongs;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean canPurchaseASong() {
        return true;
    }

    public boolean canCreateAPlaylist(){
        return true;
    }

    public void addPurchasedSong(String songId) {
        purchasedSongs.put(songId, Timestamp.from(Instant.now()));
    }

}
