package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.audio.Playlist;
import model.audio.Podcast;
import model.audio.Song;
import model.audio.podcast.Category;
import model.audio.song.Genre;
import model.user.Consumer;
import model.user.Producer;
import model.user.consumer.ConsumerType;
import model.user.consumer.Premium;
import model.user.consumer.Standard;
import model.user.producer.Artist;
import model.user.producer.Creator;
import model.user.producer.ProducerType;

public class Controller {

    private List<User> users;

    /**
     * I have to remember to delete all this, it is for testing. Sorry if i didn't
     * 💀
     */
    public Controller() {
        this.users = new ArrayList<User>();

        addUser(new Artist("A", "A", "B"));
        addUser(new Artist("B", "B", "B"));
        addUser(new Artist("C", "C", "B"));
        addUser(new Artist("D", "D", "B"));
        addUser(new Artist("E", "E", "B"));
        addUser(new Artist("F", "F", "B"));
        addUser(new Artist("G", "G", "B"));
        addUser(new Artist("H", "H", "B"));

        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                Audio audio = new Song("S-" + i, "asdfasdfasdf", 10000, Genre.POP, getUsers().get(1).getNickname());
                Audio audio2 = new Song("S-" + (i+20), "asdfasdfasdf", 10000, Genre.ROCK, getUsers().get(2).getNickname());
                Audio audio3 = new Song("S-" + (i+40), "asdfasdfasdf", 10000, Genre.HOUSE, getUsers().get(0).getNickname());
                ((Producer) getUsers().get(1)).addAudio(audio);
                ((Producer) getUsers().get(2)).addAudio(audio2);
                ((Producer) getUsers().get(0)).addAudio(audio3);
            } else {
                Audio audio = new Podcast("P-" + i, "asdfasdfasdf", 5000,
                        "SeSuponeque esto es una descripción",
                        Category.ENTERTAIMENT, getUsers().get(0).getNickname());
                Audio audio2 = new Podcast("P-" + (i+20), "asdfasdfasdf", 5000,
                        "SeSuponeque esto es una descripción",
                        Category.POLITICS, getUsers().get(3).getNickname());
                Audio audio3 = new Podcast("P-" + (i+40), "asdfasdfasdf", 5000,
                        "SeSuponeque esto es una descripción",
                        Category.FASHION, getUsers().get(5).getNickname());
                ((Producer) getUsers().get(0)).addAudio(audio);
                ((Producer) getUsers().get(3)).addAudio(audio2);
                ((Producer) getUsers().get(5)).addAudio(audio3);
            }
        }

        Standard user = new Standard("a", "a");
        Premium user1 = new Premium("p", "p");

        List<Class<?>> typeList = new ArrayList<Class<?>>();
        typeList.add(Song.class);
        typeList.add(Podcast.class);

        int[][] matrix = UtilMatrix.randomMatrix(6, 6);
        String code = UtilMatrix.generateCode(3, matrix);
        user.addPlaylist(new Playlist("No c", typeList, matrix, code));

        List<Audio> audios = getAllAudios();
        user.addPurchasedSong(audios.get(0).getId());
        user.addPurchasedSong(audios.get(3).getId());
        user.addPurchasedSong(audios.get(1).getId());
        user.addPurchasedSong(audios.get(2).getId());
        user.addPurchasedSong(audios.get(7).getId());
        user.addAudioTo(code, audios.get(0));
        user.addAudioTo(code, audios.get(3));
        user.addAudioTo(code, audios.get(1));
        user.addAudioTo(code, audios.get(2));
        user.addAudioTo(code, audios.get(7));

        user1.addPurchasedSong(audios.get(0).getId());
        user1.addPurchasedSong(audios.get(3).getId());
        user1.addPurchasedSong(audios.get(1).getId());
        user1.addPurchasedSong(audios.get(2).getId());
        user1.addPurchasedSong(audios.get(7).getId());
        user1.addAudioTo(code, audios.get(0));
        user1.addAudioTo(code, audios.get(3));
        user1.addAudioTo(code, audios.get(1));
        user1.addAudioTo(code, audios.get(2));
        user1.addAudioTo(code, audios.get(7));

        getUsers().add(user);
        getUsers().add(user1);

    }

    public List<User> getUsers() {
        return this.users;
    }

    public List<Audio> getAllAudios() {
        List<Audio> out = new ArrayList<Audio>();

        for (int i = 0; i < getUsers().size(); i++) {
            if (getUsers().get(i) != null && getUsers().get(i) instanceof Producer) {
                out.addAll(((Producer) getUsers().get(i)).getAudios());
            }
        }

        return out;
    }

    public List<Audio> getAudiosOfType(Class<?> classType) {
        List<Audio> list = getAllAudios();
        list.removeIf(audio -> (audio.getClass() != classType));
        return list;
    }

    /**
     * Searches for an user by its nickname and returns its list's position
     * 
     * @param nickname user to find
     * @return User's position in case it is found. -1 otherwise.
     */
    public int getUserPosition(String nickname) {
        int position = -1;
        User user = getUser(nickname);
        if (user != null) {
            position = getUsers().indexOf(user);
        }
        return position;
    }

    public User getUser(String nickname) {
        User user = null;
        for (int i = 0; i < getUsers().size() && user == null; i++) {
            if (nickname.equals(getUsers().get(i).getNickname())) {
                user = getUsers().get(i);
            }
        }
        return user;
    }

    /**
     * Validates the existence of an user by its nickname
     * 
     * @param nickname user's nickname
     * @return True in case it exist. False otherwise.
     */
    public boolean userExist(String nickname) {
        return getUser(nickname) != null;
    }

    /**
     * Looks for an audio by its id.
     * 
     * @param id id of the audio to look for
     * @return Audio object in case it is found, null if it isn't
     */
    public Audio getAudio(String id) {
        List<Audio> audios = getAllAudios();
        Audio audio = null;
        for (int i = 0; i < audios.size() && audio == null; i++) {
            if (id.equals(audios.get(i).getId())) {
                audio = audios.get(i);
            }
        }
        return audio;
    }

    /**
     * Creates a adds a new customer object with the given data
     * 
     * @param nickname nickname of the new user.
     * @param id       id of the new user
     * @param type     type of consumer (Premium or standard)
     * @return A message with final result.
     */
    public String createConsumer(String nickname, String id, ConsumerType type) {
        String msg;
        switch (type) {
            case STANDARD:
                msg = addUser(new Standard(nickname, id));
                break;
            case PREMIUM:
                msg = addUser(new Premium(nickname, id));
                break;
            default:
                msg = "Invalid type of user";
                break;
        }
        return msg;
    }

    /**
     * Creates and adds a new producer object with the given data
     * 
     * @param nickname niockname of the new user
     * @param name     name of the new user
     * @param imageURL profile image of the new user
     * @param type     type of producer (Artist or content creator)
     * @return a message with the final result
     */
    public String createProducer(String nickname, String name, String imageURL, ProducerType type) {
        String msg;
        switch (type) {
            case ARTIST:
                msg = addUser(new Artist(nickname, name, imageURL));
                break;
            case CONTENT_CREATOR:
                msg = addUser(new Creator(nickname, name, imageURL));
                break;
            default:
                msg = "Invalid type of user";
                break;
        }
        return msg;
    }

    /**
     * Creates and adds a new song with the given data
     * 
     * @param name     song's name
     * @param duration song's duration
     * @param imageURL song's image
     * @param type     song's genre
     * @param nickname owner's/artist's nickname
     * @return A message with the final result
     */
    public String registerSong(String name, int duration, String imageURL, Genre type, String nickname) {
        return addAudio(nickname, new Song(name, imageURL, duration, type, nickname));
    }

    /**
     * Creates and adds a new podcast with the given data
     * 
     * @param name        podcast's name
     * @param duration    podcast's duration
     * @param imageURL    podcast's image
     * @param description podcast's description
     * @param type        podcast's category
     * @param nickname    Owner's/Creator's nickname
     * @return A message with the final result.
     */
    public String registerPodcast(String name, int duration, String imageURL, String description, Category type,
            String nickname) {
        return addAudio(nickname, new Podcast(name, imageURL, duration, description, type, nickname));
    }

    /**
     * Receives and adds a new user with unique nickname.
     * 
     * @param user user to be added
     * @return a message with the final result
     */
    private String addUser(User user) {
        String msg = "Nickname already exist.";
        if (!userExist(user.getNickname())) {
            if (getUsers().add(user)) {
                msg = "User successfully added.";
            } else {
                msg = "Failure. The user was not added.";
            }
        }
        return msg;
    }

    /**
     * Receives and adds a new audio
     * 
     * @param audio Audio to be added
     * @return A message with the final result
     */
    private String addAudio(String nickname, Audio audio) {
        List<Audio> audios = getAllAudios();
        String msg = "Looks like the exact same audio is already registered.";
        if (!audios.contains(audio)) {
            msg = "Non-existent user";
            int pos = getUserPosition(nickname);
            if (pos != -1) {
                msg = "This user is not a producer";
                if (getUsers().get(pos) instanceof Producer) {
                    msg = "Failure. The audio was not added.";
                    if (((Producer) getUsers().get(pos)).addAudio(audio)) {
                        msg = "Audio successfully added.";
                    }
                }
            }
        }
        return msg;
    }

    /**
     * Register's a new playlist with the given data
     * 
     * @param userNickname Creator/owner of the playlist.
     * @param name         playlist's name
     * @param audioTypes   audio types which will contain the playlist
     * @return A message with the final result
     */
    public String registerPlaylist(String userNickname, String name, List<Class<?>> audioTypes) {
        String msg = "Non-existent user";
        int userPos = getUserPosition(userNickname);
        if (userPos != -1) {
            msg = "This user is not a consumer.";
            if (getUsers().get(userPos) instanceof Consumer) {
                msg = "The playlist could not be added.";
                int type = 0;
                for (Class<?> classType : audioTypes) {
                    if (classType == Song.class) {
                        type += 1;
                    }
                    if (classType == Podcast.class) {
                        type += 2;
                    }
                }
                int[][] matrix = UtilMatrix.randomMatrix(6, 6);
                Playlist newPlaylist = new Playlist(name, audioTypes, matrix,
                        UtilMatrix.generateCode(type, matrix));
                if (((Consumer) getUsers().get(userPos))
                        .addPlaylist(newPlaylist)) {
                    msg = "Playlist successfully created";
                }
            }
        }
        return msg;
    }

    /**
     * Returns a string list with an enum class values and names.
     * 
     * @param classType enum class to be listed
     * @return list of enum's values or null in case it isn't an enum class
     */
    public String getEnumTypes(Class<?> classType) {
        String msg = null;
        if (classType.isEnum()) {
            msg = "";
            for (int i = 0; i < classType.getEnumConstants().length; i++) {
                msg += i + ". " + classType.getEnumConstants()[i] + "\n";
            }
        }
        return msg;
    }

    /**
     * Return's all of an user's playlists
     * 
     * @param nickname user to retreive the playlists
     * @return List of Playlists if the user exists. null otherwise.
     */
    public List<Playlist> getUserPlaylists(String nickname) {
        List<Playlist> userPlaylists = null;
        User user = getUser(nickname);
        if (user != null && user instanceof Consumer) {
            userPlaylists = ((Consumer) user).getPlaylists();
        }
        return userPlaylists;
    }

    /**
     * Returns all of the audio supported types (children of Audio class).
     * 
     * @return Array with Audio's children classes
     */
    public Class<?>[] getAudioClasses() {
        return Audio.children;
    }

    public Class<?>[] getAudioClassification() {
        return Audio.classifications;
    }

    /**
     * Returns a list of available audios for a certain user.
     * 
     * @param nickname   User's nickname
     * @param audioTypes Audio types to be retreived
     * @param notIn      A playlist id to discard already added audios
     * @return List of available audios for the selected user.
     */
    public List<Audio> audiosForUser(String nickname, List<Class<?>> audioTypes, String notIn) {
        int userPos = getUserPosition(nickname);
        List<Audio> audios = getAllAudios();
        List<Audio> availableAudios = null;
        if (userPos != -1 && getUsers().get(userPos) instanceof Consumer) {
            availableAudios = new ArrayList<Audio>();
            Consumer user = (Consumer) getUsers().get(userPos);
            List<Audio> excludedAudios = user.getPlaylist(notIn).getAudios();
            for (int i = 0; i < audios.size(); i++) {
                Audio currentAudio = audios.get(i);
                if (audioTypes.contains(currentAudio.getClass()) && !excludedAudios.contains(currentAudio)) {
                    if (currentAudio instanceof Song) {
                        if (user.boughtSong(currentAudio.getId())) {
                            availableAudios.add(currentAudio);
                        }
                    } else {
                        availableAudios.add(currentAudio);
                    }
                }
            }
        }
        return availableAudios;
    }

    /**
     * Returns a list of available audios for a certain user.
     * 
     * @param nickname   User's nickname
     * @param audioTypes Audio types to be retreived
     * @return List of available audios for the selected user.
     */
    public List<Audio> audiosForUser(String nickname, List<Class<?>> audioTypes) {
        int userPos = getUserPosition(nickname);
        List<Audio> audios = getAllAudios();
        List<Audio> availableAudios = null;
        if (userPos != -1 && getUsers().get(userPos) instanceof Consumer) {
            availableAudios = new ArrayList<Audio>();
            Consumer user = (Consumer) getUsers().get(userPos);
            for (int i = 0; i < audios.size(); i++) {
                Audio currentAudio = audios.get(i);
                if (audioTypes.contains(currentAudio.getClass())) {
                    if (currentAudio instanceof Song) {
                        if (user.boughtSong(currentAudio.getId())) {
                            availableAudios.add(currentAudio);
                        }
                    } else {
                        availableAudios.add(currentAudio);
                    }
                }
            }
        }
        return availableAudios;
    }

    /**
     * Returns a list of available audios for a certain user.
     * 
     * @param nickname   User's nickname
     * @param audioTypes Audio types to be retreived
     * @return List of available audios for the selected user.
     */
    public List<Audio> audiosForUser(String nickname) {
        int userPos = getUserPosition(nickname);
        List<Audio> audios = getAllAudios();
        List<Audio> availableAudios = null;
        if (userPos != -1 && getUsers().get(userPos) instanceof Consumer) {
            availableAudios = new ArrayList<Audio>();
            Consumer user = (Consumer) getUsers().get(userPos);
            for (int i = 0; i < audios.size(); i++) {
                Audio currentAudio = audios.get(i);
                if (currentAudio instanceof Song) {
                    if (user.boughtSong(currentAudio.getId())) {
                        availableAudios.add(currentAudio);
                    }
                } else {
                    availableAudios.add(currentAudio);
                }
            }
        }
        return availableAudios;
    }

    /**
     * Adds an existent audio to an user's existent playlist
     * 
     * @param nickname   user's nickname
     * @param playlistId playlist id to add the audio
     * @param audioId    Audio id to add to the playlist
     * @return A message with the final result
     */
    public String addAudioToUserPlaylist(String nickname, String playlistId, String audioId) {
        String msg = "Non-existent user";
        int userPos = getUserPosition(nickname);
        if (userPos != -1) {
            msg = "This user has no playlists.";
            User user = getUsers().get(userPos);
            if (user instanceof Consumer) {
                Audio audio = getAudio(audioId);
                msg = "Non-existent audio";
                if (audio != null) {
                    msg = ((Consumer) getUsers().get(userPos)).addAudioTo(playlistId, audio);
                }
            }
        }
        return msg;
    }

    /**
     * Removes an audio from an user's existent playlist
     * 
     * @param nickname   nickname of the user
     * @param playlistId playlist's id to remove the audio
     * @param audioId    Audio to be removed from playlist
     * @return A message with the final result
     */
    public String removeAudioFromUserPlaylist(String nickname, String playlistId, String audioId) {
        String msg = "Non-existent user";
        int userPos = getUserPosition(nickname);
        if (userPos != -1) {
            msg = "This user has no playlists.";
            User user = getUsers().get(userPos);
            if (user instanceof Consumer) {
                Audio audio = getAudio(audioId);
                msg = "Non-existent audio";
                if (audio != null) {
                    msg = ((Consumer) getUsers().get(userPos)).removeAudioFrom(playlistId, audio);
                }
            }
        }
        return msg;
    }

    /**
     * Changes the name of an existent user's playlist
     * 
     * @param nickname   user's nickname
     * @param playlistId playlist id to be renamed
     * @param newName    new name for the playlist
     * @return A message with the final result
     */
    public String changeNameToUserPlaylist(String nickname, String playlistId, String newName) {
        String msg = "Non-existent user";
        int userPos = getUserPosition(nickname);
        if (userPos != -1) {
            msg = "This user has no playlists.";
            User user = getUsers().get(userPos);
            if (user instanceof Consumer) {
                msg = ((Consumer) getUsers().get(userPos)).changeNameTo(playlistId, newName);
            }
        }
        return msg;
    }

    /**
     * Changes an audio's position inside of an existent playlist
     * 
     * @param nickname    user's/owner's nickname
     * @param playlistId  playlist id to be changed
     * @param oldPosition position of the audio to be moved
     * @param newPosition new position of the audio
     * @return A message with the final result
     */
    public String changePositions(String nickname, String playlistId, int oldPosition, int newPosition) {
        String msg = "Non-existent user";
        int userPos = getUserPosition(nickname);
        if (userPos != -1) {
            msg = "This user has no playlists.";
            User user = getUsers().get(userPos);
            if (user instanceof Consumer) {
                msg = ((Consumer) getUsers().get(userPos)).changePositionOf(playlistId, oldPosition, newPosition);
            }
        }
        return msg;
    }

    /**
     * Deletes an user's existent playlist
     * 
     * @param nickname   user's nickname
     * @param playlistId playlist's id to be deleted
     * @return A message with the final result
     */
    public String deletePlaylistOf(String nickname, String playlistId) {
        String msg = "Non-existent user";
        int userPos = getUserPosition(nickname);
        if (userPos != -1) {
            msg = "This user has no playlists.";
            User user = getUsers().get(userPos);
            if (user instanceof Consumer) {
                msg = ((Consumer) getUsers().get(userPos)).deletePlaylist(playlistId);
            }
        }
        return msg;
    }

    public Playlist getPlaylistById(String id) {
        Playlist playlist = null;
        for (int i = 0; i < getUsers().size() && playlist == null; i++) {
            if (getUsers().get(i) instanceof Consumer) {
                playlist = ((Consumer) getUsers().get(i)).getPlaylist(id);
            }
        }
        return playlist;
    }

    public String increaseAdPercentageTo(String nickname, String audioID) {
        String msg = null;
        int pos = getUserPosition(nickname);
        if (pos != -1) {
            if (getUsers().get(pos) instanceof Standard) {
                Audio audio = getAudio(audioID);
                if (audio != null) {
                    msg = ((Standard) getUsers().get(pos)).increaseAdPercentage(audio instanceof Podcast ? 1 : 0.4);
                }
            }
        }
        return msg;
    }

    public List<Song> getAvailableSongsForPurchase(String nickname) {
        List<Audio> songs = getAudiosOfType(Song.class);
        List<Song> availableSongs = new ArrayList<Song>();
        User user = getUser(nickname);

        if (user != null && user instanceof Consumer) {
            for (Audio song : songs) {
                if (!((Consumer) user).boughtSong(song.getId())) {
                    availableSongs.add((Song) song);
                }
            }
        }

        return availableSongs;
    }

    public String buySongFor(String nickname, String songID) {
        int pos = getUserPosition(nickname);

        String msg = "Non-existent user";
        if (pos != -1) {
            msg = "This user is not a consumer";
            if (getUsers().get(pos) instanceof Consumer) {
                msg = "The audio doesn't exist";
                if (getAudio(songID) != null) {
                    if (((Consumer) getUsers().get(pos)).canPurchaseASong()) {
                        ((Consumer) getUsers().get(pos)).addPurchasedSong(songID);
                        msg = "Song successfully bought";
                    }
                }
            }
        }

        return msg;
    }

    public void userPlayed(String nickname, String audioID) {
        Audio playedAudio = getAudio(audioID);
        if (playedAudio != null) {
            int posOwner = getUserPosition(playedAudio.getOwner());
            if (posOwner != -1) {
                if (getUsers().get(posOwner) instanceof Producer) {
                    ((Producer) getUsers().get(posOwner)).playedAudio(audioID);
                }
            }
        }
    }

    public String getTotalReproductionsByType() {
        String msg = "";
        Map<String, Integer> dir = new HashMap<String, Integer>();
        for (int i = 0; i < getUsers().size(); i++) {
            if (getUsers().get(i) instanceof Producer) {
                ((Producer) getUsers().get(i)).audioTypeStadistics().forEach((key, value) -> {
                    dir.put(key, dir.get(key) != null ? dir.get(key) + value : value);
                });
            }
        }
        for (String key : dir.keySet()) {
            msg += "- " + key.toUpperCase() + ": " + dir.get(key) + "\n";
        }
        return msg;
    }

    public String getMostPlayedByClassification(Class<?> type) {
        String msg = "";
        Map<String, Integer> dir = new HashMap<String, Integer>();
        for (int i = 0; i < getUsers().size(); i++) {
            if (getUsers().get(i) instanceof Producer) {
                ((Producer) getUsers().get(i)).classificationStadistics(type)
                        .forEach((key, value) -> dir.put(key, dir.get(key) != null ? dir.get(key) + value : value));
            }
        }
        int greater = 0;
        for (String key : dir.keySet()) {
            if (dir.get(key) > greater) {
                greater = dir.get(key);
                msg = key.toUpperCase() + ": " + greater;
            } else if (dir.get(key) == greater) {
                msg += "\n" + key.toUpperCase() + ": " + greater;
            }
        }
        return msg;
    }

    public String getMostPlayedClassificationOf(String nickname) {
        String msg = "Non-existent user";
        int pos = getUserPosition(nickname);
        if (pos != -1) {
            msg = "This user is not a producer";
            if (getUsers().get(pos) instanceof Producer) {
                msg = "";
                for (Class<?> type : getAudioClassification()) {
                    Map<String, Integer> dir = new HashMap<String, Integer>();
                    ((Producer) getUsers().get(pos)).classificationStadistics(type)
                            .forEach((key, value) -> dir.put(key, dir.get(key) != null ? dir.get(key) + value : value));
                    int greater = 0;

                    if (!dir.isEmpty()) {
                        msg += type.getSimpleName() + " most played classifications:\n";
                        for (String key : dir.keySet()) {
                            if (dir.get(key) > greater) {
                                greater = dir.get(key);
                                msg = key.toUpperCase() + ": " + greater;
                            } else if (dir.get(key) == greater) {
                                msg += "\n" + key.toUpperCase() + ": " + greater;
                            }
                        }
                        msg += "\n\n";
                    }
                }
                msg = msg.substring(0, msg.length() - 3);
            }
        }
        return msg;
    }

    public String producersRanking() {
        String msg = "";

        List<User> ranking = new ArrayList<User>(getUsers());
        ranking.removeIf(user -> !(user instanceof Producer));
        Collections.sort(ranking);

        List<User> artists = new ArrayList<User>(ranking);
        List<User> creators = new ArrayList<User>(ranking);

        artists.removeIf(user -> !(user instanceof Artist));
        creators.removeIf(user -> !(user instanceof Creator));

        if (!artists.isEmpty()) {
            msg += "\nTop artists:\n\n";
            for (int i = 0; i < artists.size() && i < 5; i++) {
                msg += (i + 1) + ". " + ((Producer) artists.get(i)).toString() + "\n";
            }
            msg += "\n";
        }

        if (!creators.isEmpty()) {
            msg += "\nTop content creators:\n\n";
            for (int i = 0; i < creators.size() && i < 5; i++) {
                msg += (i + 1) + ". " + ((Producer) creators.get(i)).toString() + "\n";
            }
            msg += "\n";
        }

        return msg;
    }

    public String audiosRanking() {
        String msg = "";

        List<Audio> ranking = new ArrayList<Audio>(getAllAudios());
        Collections.sort(ranking);

        List<Audio> podcasts = new ArrayList<Audio>(ranking);
        List<Audio> songs = new ArrayList<Audio>(ranking);

        podcasts.removeIf(audio -> !(audio instanceof Podcast));
        songs.removeIf(audio -> !(audio instanceof Song));

        if (!podcasts.isEmpty()) {
            msg += "\nTop podcasts:\n\n";
            for (int i = 0; i < podcasts.size() && i < 10; i++) {
                msg += (i + 1) + ". " + podcasts.get(i).toString() + " Total reproductions: "
                        + podcasts.get(i).getTimesReproduced() + "\n";
            }
            msg += "\n";
        }

        if (!songs.isEmpty()) {
            msg += "\nTop songs:\n\n";
            for (int i = 0; i < songs.size() && i < 10; i++) {
                msg += (i + 1) + ". " + songs.get(i).toString() + " Total reproductions: "
                        + songs.get(i).getTimesReproduced() + "\n";
            }
            msg += "\n";
        }

        return msg;
    }

}
