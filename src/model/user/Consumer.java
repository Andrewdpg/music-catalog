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

public abstract class Consumer extends User {

    private List<Playlist> playlists;
    private Map<String, Timestamp> purchasedSongs;
    private String id;

    public Consumer(String nickname, String id) {
        super(nickname);
        this.id = id;
        this.playlists = new ArrayList<Playlist>();
        this.purchasedSongs = new HashMap<String, Timestamp>();
    }

    /**
     * Adds a new playlist to this user
     * 
     * @param newPlaylist playlist to be added
     * @return boolean with the result
     */
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

    /**
     * Looks for a playlist by its id
     * 
     * @param id playlist's id
     * @return A playlist object in case of it's found. null otherwise.
     */
    public Playlist getPlaylist(String id) {
        int pos = getPlaylistPos(id);
        Playlist list = null;
        if (pos != -1) {
            list = playlists.get(pos);
        }
        return list;
    }

    /**
     * Returns a playlist position in the list
     * 
     * @param id playlist's id
     * @return position of the playlist. -1 if it isn't found.
     */
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Validates if the user is able to buy another song
     * 
     * @return True if it has not reached its limit (True by default)
     */
    public boolean canPurchaseASong() {
        return true;
    }

    /**
     * Validates if the user is able to create another playlist
     * 
     * @return True if it has not reached its limit (True by default)
     */
    public boolean canCreateAPlaylist() {
        return true;
    }

    /**
     * Adds a purchased song to its directory
     * 
     * @param songId Bought song id
     */
    public void addPurchasedSong(String songId) {
        purchasedSongs.put(songId, Timestamp.from(Instant.now()));
    }

    /**
     * Validates if it already owns a song
     * 
     * @param id id of the song to validate
     * @return True in case it already owns it. false otherwise.
     */
    public boolean boughtSong(String id) {
        return purchasedSongs.containsKey(id);
    }

    /**
     * Adds a new audio to a specific playlist
     * 
     * @param playlistId playlist where the audio is going to be added.
     * @param audio      Audio to be added.
     * @return A message with the final result.
     */
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

    /**
     * Adds an audio to a specific playlist, validating first, in case it is a song,
     * that the user owns it.
     * 
     * @param playlistId Playlist where the audio is going to be added.
     * @param audio      Audio to be added
     * @return A message with the final result
     */
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

    /**
     * Removes an audio from a playlist
     * 
     * @param playlistId Playlist to be edited
     * @param audio      Audio to be deleted
     * @return A message with the final result
     */
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

    /**
     * Looks for a playlist and changes its name
     * 
     * @param playlistId Playlist to me renamed
     * @param newName    new name for the playlist
     * @return A string with the final result
     */
    public String changeNameTo(String playlistId, String newName) {
        int pos = getPlaylistPos(playlistId);
        String msg = "Playlist not found.";
        if (pos != -1) {
            msg = "Name successfully changed to " + newName;
            playlists.get(pos).setName(newName);
        }
        return msg;
    }

    /**
     * Looks for a playlist and changes an audio position in it.
     * 
     * @param playlistId  playlist to be edited
     * @param oldPosition position of the audio to be moved
     * @param newPosition new position for the audio
     * @return A message with the final result
     */
    public String changePositionOf(String playlistId, int oldPosition, int newPosition) {
        int pos = getPlaylistPos(playlistId);
        String msg = "Playlist not found.";
        if (pos != -1) {
            msg = "Successfully changed";
            playlists.get(pos).changeAudioPosition(oldPosition, newPosition);
        }
        return msg;
    }

    /**
     * Looks for a playlist and deletes it
     * 
     * @param playlistId Playlist to be deleted
     * @return A message with the final result
     */
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
