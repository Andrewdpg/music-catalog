package model;

import java.util.ArrayList;
import java.util.List;

import model.audio.Playlist;
import model.audio.Podcast;
import model.audio.Song;
import model.audio.podcast.Category;
import model.audio.song.Genre;
import model.user.Consumer;
import model.user.consumer.ConsumerType;
import model.user.consumer.Premium;
import model.user.consumer.Standard;
import model.user.producer.Artist;
import model.user.producer.Creator;
import model.user.producer.ProducerType;

public class Controller {

    private List<User> users;
    private List<Audio> audios;

    public Controller() {
        this.users = new ArrayList<User>();
        this.audios = new ArrayList<Audio>();

        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                Audio audio = new Song("A-" + i, "asdfasdfasdf", 100, Genre.POP, "B");
                audios.add(audio);
            } else {
                Audio audio = new Podcast("P-" + i, "asdfasdfasdf", 100, "SeSuponeque esto es una descripción",
                        Category.POLITICS, "B");
                audios.add(audio);
            }
        }

        Standard user = new Standard("a", "a");

        List<Class<?>> typeList = new ArrayList<Class<?>>();
        typeList.add(Song.class);
        typeList.add(Podcast.class);

        int[][] matrix = UtilMatrix.randomMatrix(6, 6);
        String code = UtilMatrix.generateCode(3, matrix);
        user.addPlaylist(new Playlist("No c", typeList, matrix, code));

        user.addAudioTo(code, audios.get(0));
        user.addAudioTo(code, audios.get(3));
        user.addAudioTo(code, audios.get(1));
        user.addAudioTo(code, audios.get(2));
        user.addAudioTo(code, audios.get(7));

        user.addPurchasedSong(audios.get(4).getId());
        user.addPurchasedSong(audios.get(12).getId());
        user.addPurchasedSong(audios.get(18).getId());

        users.add(user);

    }

    public int getUserPosition(String nickname) {
        int position = -1;
        User user = getUser(nickname);
        if (user != null) {
            position = users.indexOf(user);
        }
        return position;
    }

    public User getUser(String nickname) {
        User user = null;
        for (int i = 0; i < users.size() && user == null; i++) {
            if (nickname.equals(users.get(i).getNickname())) {
                user = users.get(i);
            }
        }
        return user;
    }

    public boolean userExist(String nickname) {
        return getUser(nickname) != null;
    }

    public Audio getAudio(String id) {
        Audio audio = null;
        for (int i = 0; i < audios.size() && audio == null; i++) {
            if (id.equals(audios.get(i).getId())) {
                audio = audios.get(i);
            }
        }
        return audio;
    }

    public String addConsumer(String nickname, String id, ConsumerType type) {
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

    public String addProducer(String nickname, String name, String imageURL, ProducerType type) {
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

    public String registerSong(String name, int duration, String imageURL, Genre type, String nickname) {
        return addAudio(new Song(name, imageURL, duration, type, nickname));
    }

    public String registerPodcast(String name, int duration, String imageURL, String description, Category type,
            String nickname) {
        return addAudio(new Podcast(name, imageURL, duration, description, type, nickname));
    }

    private String addUser(User user) {
        String msg = "Nickname already exist.";
        if (!userExist(user.getNickname())) {
            if (users.add(user)) {
                msg = "User successfully added.";
            } else {
                msg = "Failure. The user was not added.";
            }
        }
        return msg;
    }

    private String addAudio(Audio audio) {
        String msg = "Looks like the exact same audio is already registered.";
        if (!audios.contains(audio)) {
            if (audios.add(audio)) {
                msg = "Audio successfully added.";
            } else {
                msg = "Failure. The audio was not added.";
            }
        }
        return msg;
    }

    public String registerPlaylist(String userNickname, String name, List<Class<?>> audioTypes) {
        String msg = "Non-existent user";
        int userPos = getUserPosition(userNickname);
        if (userPos != -1) {
            msg = "This user is not a consumer.";
            if (users.get(userPos) instanceof Consumer) {
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
                if (((Consumer) users.get(userPos))
                        .addPlaylist(newPlaylist)) {
                    msg = "Playlist successfully created";
                }
            }
        }
        return msg;
    }

    public String getEnumTypes(Class<?> classType) {
        String msg = "";
        for (int i = 0; i < classType.getEnumConstants().length; i++) {
            msg += i + ". " + classType.getEnumConstants()[i] + "\n";
        }
        return msg;
    }

    public List<Playlist> getUserPlaylist(String nickname) {
        List<Playlist> userPlaylists = null;
        User user = getUser(nickname);
        if (user != null && user instanceof Consumer) {
            userPlaylists = ((Consumer) user).getPlaylists();
        }
        return userPlaylists;
    }

    public Class<?>[] getAudioClasses() {
        return Audio.children;
    }

    public List<Audio> audiosForUser(String nickname, List<Class<?>> audioTypes, String notIn) {
        int userPos = getUserPosition(nickname);
        List<Audio> availableAudios = null;
        if (userPos != -1 && users.get(userPos) instanceof Consumer) {
            availableAudios = new ArrayList<Audio>();
            Consumer user = (Consumer) users.get(userPos);
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

    public String addAudioToUserPlaylist(String nickname, String playlistId, String audioId) {
        String msg = "Non-existent user";
        int userPos = getUserPosition(nickname);
        if (userPos != -1) {
            msg = "This user has no playlists.";
            User user = users.get(userPos);
            if (user instanceof Consumer) {
                Audio audio = getAudio(audioId);
                msg = "Non-existent audio";
                if (audio != null) {
                    msg = ((Consumer) users.get(userPos)).addAudioTo(playlistId, audio);
                }
            }
        }
        return msg;
    }

    public String removeAudioFromUserPlaylist(String nickname, String playlistId, String audioId) {
        String msg = "Non-existent user";
        int userPos = getUserPosition(nickname);
        if (userPos != -1) {
            msg = "This user has no playlists.";
            User user = users.get(userPos);
            if (user instanceof Consumer) {
                Audio audio = getAudio(audioId);
                msg = "Non-existent audio";
                if (audio != null) {
                    msg = ((Consumer) users.get(userPos)).removeAudioFrom(playlistId, audio);
                }
            }
        }
        return msg;
    }

    public String changeNameToUserPlaylist(String nickname, String playlistId, String newName) {
        String msg = "Non-existent user";
        int userPos = getUserPosition(nickname);
        if (userPos != -1) {
            msg = "This user has no playlists.";
            User user = users.get(userPos);
            if (user instanceof Consumer) {
                msg = ((Consumer) users.get(userPos)).changeNameTo(playlistId, newName);
            }
        }
        return msg;
    }

    public String changePositions(String nickname, String playlistId, int oldPosition, int newPosition) {
        String msg = "Non-existent user";
        int userPos = getUserPosition(nickname);
        if (userPos != -1) {
            msg = "This user has no playlists.";
            User user = users.get(userPos);
            if (user instanceof Consumer) {
                msg = ((Consumer) users.get(userPos)).changePositionOf(playlistId, oldPosition,newPosition);
            }
        }
        return msg;
    }

    public String deletePlaylistOf(String nickname, String playlistId) {
        String msg = "Non-existent user";
        int userPos = getUserPosition(nickname);
        if (userPos != -1) {
            msg = "This user has no playlists.";
            User user = users.get(userPos);
            if (user instanceof Consumer) {
                msg = ((Consumer) users.get(userPos)).deletePlaylist(playlistId);
            }
        }
        return msg;
    }

}
