package model;

import java.util.ArrayList;
import java.util.List;

import model.audio.Playlist;
import model.audio.Podcast;
import model.audio.Song;
import model.audio.podcast.Category;
import model.audio.song.Genre;
import model.user.consumer.ConsumerType;
import model.user.consumer.Premium;
import model.user.consumer.Standard;
import model.user.producer.Artist;
import model.user.producer.Creator;
import model.user.producer.ProducerType;

public class Controller {

    private List<User> users;
    private List<Audio> audios;
    private List<Playlist> playlists;

    public Controller() {
        this.users = new ArrayList<User>();
        this.audios = new ArrayList<Audio>();
        this.playlists = new ArrayList<Playlist>();
    }

    public User getUser(String nickname) {
        User user = null;
        for (int i = 0; i < users.size(); i++) {
            if (nickname.equals(users.get(i).getNickname())) {
                user = users.get(i);
            }
        }
        return user;
    }

    public boolean userExist(String nickname) {
        return getUser(nickname) != null;
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
        if (userExist(userNickname)) {
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
            if (playlists.add(new Playlist(userNickname, name, type))) {
                msg = "Playlist successfully created";
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

    public Class<?>[] getAudioClasses() {
        return Audio.children;
    }
}
