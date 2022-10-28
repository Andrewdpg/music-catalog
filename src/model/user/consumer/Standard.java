package model.user.consumer;

import model.user.Consumer;

public final class Standard extends Consumer {

    private static final int MAX_SONGS = 100;
    private static final int MAX_PLAYLIST = 20;
    private static final int SONGS_IN_BETWEEN = 2;
    private static final int PODCASTS_IN_BETWEEN = 1;

    public Standard(String nickname, String id) {
        super(nickname, id);
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
