package model.user;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Audio;
import model.User;
import model.audio.Playlist;
import model.audio.Song;
import model.user.consumer.Preferences;

public abstract class Consumer extends User {

    private List<Playlist> playlists;
    private Map<String, Timestamp> purchasedSongs;
    private Preferences preferences;
    private String id;

    public Consumer(String nickname, String id) {
        super(nickname);
        this.id = id;
        this.playlists = new ArrayList<Playlist>();
        this.purchasedSongs = new HashMap<String, Timestamp>();
        this.preferences = new Preferences();
    }

    public boolean addPlaylist(Playlist newPlaylist) {
        boolean isAdded = false;
        if (canCreateAPlaylist()) {
            isAdded = playlists.add(newPlaylist);
        }
        return isAdded;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public Playlist getPlaylist(String id) {
        int pos = getPlaylistPos(id);
        Playlist list = null;
        if (pos != -1) {
            list = playlists.get(pos);
        }
        return list;
    }

    public int getPlaylistPos(String id) {
        int pos = -1;
        for (int i = 0; i < playlists.size() && pos == -1; i++) {
            if (playlists.get(i).getId().equals(id)) {
                pos = i;
            }
        }
        return pos;
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

    public boolean canCreateAPlaylist() {
        return true;
    }

    public void addPurchasedSong(String songId) {
        purchasedSongs.put(songId, Timestamp.from(Instant.now()));
    }

    public boolean boughtSong(String id) {
        return purchasedSongs.containsKey(id);
    }

    private String addAudioToPlaylist(String playlistId, Audio audio) {
        String msg = "Playlist not found.";
        int pos = getPlaylistPos(playlistId);
        if (pos != -1) {
            if (playlists.get(pos).addAudio(audio)) {
                msg = "Audio successully added";
            }
        }
        return msg;
    }

    public String addAudioTo(String playlistId, Audio audio) {
        String msg = "The user havent purchased this song";
        if (audio instanceof Song) {
            if (purchasedSongs.containsKey(audio.getId())) {
                msg = addAudioToPlaylist(playlistId, audio);
            }
        } else {
            msg = addAudioToPlaylist(playlistId, audio);
        }
        return msg;
    }

    public String removeAudioFrom(String playlistId, Audio audio) {
        int pos = getPlaylistPos(playlistId);
        String msg = "Playlist not found.";
        if (pos != -1) {
            if (playlists.get(pos).removeAudio(audio)) {
                msg = "Audio successully removed";
            }
        }
        return msg;
    }

    public String changeNameTo(String playlistId, String newName) {
        int pos = getPlaylistPos(playlistId);
        String msg = "Playlist not found.";
        if (pos != -1) {
            msg = "Name successfully changed to " + newName;
            playlists.get(pos).setName(newName);
        }
        return msg;
    }

    public String changePositionOf(String playlistId, int oldPosition, int newPosition) {
        int pos = getPlaylistPos(playlistId);
        String msg = "Playlist not found.";
        if (pos != -1) {
            msg = "Successfully changed";
            playlists.get(pos).changeAudioPosition(oldPosition, newPosition);
        }
        return msg;
    }

    public String deletePlaylist(String playlistId) {
        int pos = getPlaylistPos(playlistId);
        String msg = "Playlist not found.";
        if (pos != -1) {
            playlists.remove(pos);
            msg = "Successfully deleted.";
        }
        return msg;
    }

}
