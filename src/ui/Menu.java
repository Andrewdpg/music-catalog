
package ui;

import java.util.ArrayList;
import java.util.List;

import model.Audio;
import model.Controller;
import model.User;
import model.audio.Playlist;
import model.audio.podcast.Category;
import model.audio.song.Genre;
import model.user.Producer;
import model.user.consumer.ConsumerType;
import model.user.producer.Artist;
import model.user.producer.Creator;
import model.user.producer.ProducerType;

/**
 * Menu
 */
public class Menu {

    private Controller controller;
    private int option;
    private static final String MENU = "\n\n<< Spotify pirata >>\n" +
            "1. Add producer.\n" +
            "2. Add consumer.\n" +
            "3. Add an audio.\n" +
            "4. Create a playlist.\n" +
            "5. Edit a playlist.\n" +
            "0. Exit.\n" +
            "Input: ";
    private static final String EDITING_PLAYLIST_MENU = "1. Add audio.\n" +
            "2. Remove audio.\n" +
            "3. Change name.\n" +
            "4. Change audio position.\n" +
            "5. Show playlist.\n" +
            "6. Delete playlist.\n" +
            "0. Done.";

    public Menu() {
        option = -1;
        controller = new Controller();
    }

    public boolean isAlive() {
        return this.option != 0;
    }

    public void readOption() {
        this.option = Reader.readInt();
    }

    public void showMenu() {
        System.out.print(MENU);
    }

    public void executeOption() {
        String msg = "";
        switch (option) {
            case 1:
                msg = registerProducer();
                break;
            case 2:
                msg = registerConsumer();
                break;
            case 3:
                msg = registerAudio();
                break;
            case 4:
                msg = createPlaylist();
                break;
            case 5:
                msg = editPlaylist();
                break;
            case 0:
                msg = "Closing....";
                break;
            default:
                msg = "Invalid option.";
                break;
        }
        System.out.println(msg);
    }

    private String editPlaylist() {
        String msg = "Non-existent user";
        String nickname = readUserNickname();

        List<Playlist> playlists = controller.getUserPlaylist(nickname);
        if (playlists != null) {
            int input = -1;
            do {
                msg = "This user has no playlists.";
                if (playlists.size() != 0) {
                    try {
                        msg = "\n" + nickname + "'s playlists: \n";
                        for (int i = 1; i <= playlists.size(); i++) {
                            msg += i + ". " + playlists.get(i - 1).getName() + "\n";
                        }
                        msg += "0. exit.";
                        System.out.println(msg);
                        System.out.print("Input: ");
                        input = Reader.readInt();
                        if (input != 0) {
                            Playlist selectedPlaylist = playlists.get(input - 1);
                            System.out.println(executeEditPlaylist(selectedPlaylist, nickname));
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input");
                    }
                } else {
                    input = 0;
                }
            } while (input != 0);
        }
        return msg;
    }

    private String executeEditPlaylist(Playlist selectedPlaylist, String nickname) {
        String msg = "";
        System.out.print("\nEditing " + selectedPlaylist.getName() + "\n" + EDITING_PLAYLIST_MENU
                + "\nInput: ");
        int input = Reader.readInt();

        switch (input) {
            case 1:
                msg = addAudioToPlaylist(nickname, selectedPlaylist);
                break;
            case 2:
                msg = removeAudioFromPlaylist(nickname, selectedPlaylist);
                break;
            case 3:
                msg = changeNameToPlaylist(nickname, selectedPlaylist);
                break;
            case 4:
                msg = changeAudioPositionFrom(nickname, selectedPlaylist);
                break;
            case 5:
                msg = "\n" + selectedPlaylist.toString();
                break;
            case 6:
                msg = deletePlaylistOf(nickname, selectedPlaylist);
            case 0:
                break;
            default:
                msg = "Invalid input";
                break;
        }
        return msg;
    }

    private String addAudioToPlaylist(String nickname, Playlist selectedPlaylist) {
        String msg = "";
        List<Audio> availableAudios = controller.audiosForUser(nickname,
                selectedPlaylist.getAudioTypes(),
                selectedPlaylist.getId());
        String audioList = "\nList of available audios: \n";
        for (int i = 1; i <= availableAudios.size(); i++) {
            audioList += i + ". " + availableAudios.get(i - 1).getName() + "\n";
        }
        audioList += "0. Cancel";
        System.out.print(audioList + "\nInput: ");
        int input = Reader.readInt();
        if (input != 0) {
            msg = controller.addAudioToUserPlaylist(nickname, selectedPlaylist.getId(),
                    availableAudios.get(input - 1).getId());
        }
        return msg;
    }

    public String removeAudioFromPlaylist(String nickname, Playlist selectedPlaylist) {
        String msg = "";
        System.out.print("\n" + selectedPlaylist.toString() + "0. Cancel. \nInput: ");
        int input = Reader.readInt();
        if (input != 0) {
            msg = controller.removeAudioFromUserPlaylist(nickname, selectedPlaylist.getId(),
                    selectedPlaylist.getAudios().get(input - 1).getId());
        }
        return msg;
    }

    public String changeNameToPlaylist(String nickname, Playlist selectedPlaylist) {
        System.out.print("New name: ");
        String newName = Reader.readString();
        return controller.changeNameToUserPlaylist(nickname, selectedPlaylist.getId(), newName);
    }

    public String changeAudioPositionFrom(String nickname, Playlist selectedPlaylist) {
        System.out.print("\n" + selectedPlaylist.toString() + "0. Cancel. \nInput: ");
        int oldPosition = Reader.readInt();
        System.out.print("New position: ");
        int newPosition = Reader.readInt();

        String msg = "Invalid positions.";
        if (newPosition >= 1 && newPosition <= selectedPlaylist.getAudios().size() && oldPosition >= 1
                && oldPosition <= selectedPlaylist.getAudios().size()) {
            msg = controller.changePositions(nickname, selectedPlaylist.getId(), oldPosition - 1, newPosition - 1);
        }
        return msg;
    }

    public String deletePlaylistOf(String nickname, Playlist selectedPlaylist) {
        System.out.print(
                "\nAre you sure to delete " + selectedPlaylist.getName()
                        + " playlist?\n0: Confirm.\nAny other key: Cancel.\nInput: ");
        String input = Reader.readString();

        String msg = "Operation canceled";
        if (input.equals("0")) {
            msg = controller.deletePlaylistOf(nickname, selectedPlaylist.getId());
        }
        return msg;
    }

    private String createPlaylist() {
        String msg = null;

        String nickname = readUserNickname();

        System.out.print("What will be its name? ");
        String name = Reader.readString();

        List<Class<?>> audioTypes = new ArrayList<>();
        Class<?>[] classes = controller.getAudioClasses();

        String classesMenu = "\nWhich type of audios will it have? \n";
        for (int i = 1; i <= classes.length; i++) {
            classesMenu += i + ". " + classes[i - 1].getSimpleName() + "\n";
        }
        classesMenu += "\n0. Done.";
        System.out.println(classesMenu);

        int input = -1;
        do {
            try {
                System.out.print("Input: ");
                input = Reader.readInt();
                if (input != 0) {
                    if (!audioTypes.contains(classes[input - 1])) {
                        audioTypes.add(classes[input - 1]);
                        System.out.println("- " + classes[input - 1].getSimpleName() + ": added");
                    } else {
                        audioTypes.remove(classes[input - 1]);
                        System.out.println("- " + classes[input - 1].getSimpleName() + ": removed");
                    }
                } else if (audioTypes.isEmpty()) {
                    input = -1;
                    System.out.println("You must select at least 1 type of audio.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        } while (input != 0);

        msg = controller.registerPlaylist(nickname, name, audioTypes);
        return msg;
    }

    private String registerAudio() {
        String msg = "this user is not a producer.";
        String nickname = readUserNickname();
        User user = controller.getUser(nickname);

        if (user instanceof Producer) {
            System.out.print("What's the audio name? ");
            String name = Reader.readString();
            System.out.print("How long the audio is? ");
            int duration = Reader.readInt();
            System.out.print("What's the audio image url? ");
            String imageURL = Reader.readString();

            if (user instanceof Artist) {
                System.out.print("What's the genre of the song?\n" + controller.getEnumTypes(Genre.class));
                Object type = validateType(Reader.readInt(), Genre.class);

                msg = type instanceof Genre ? controller.registerSong(name, duration, imageURL, (Genre) type, nickname)
                        : String.valueOf(type);

            } else if (user instanceof Creator) {
                System.out.print("Give a brief description: ");
                String description = Reader.readString();
                System.out.print("What's the podcast category?\n" + controller.getEnumTypes(Category.class));
                Object type = validateType(Reader.readInt(), Category.class);

                msg = type instanceof Genre
                        ? controller.registerPodcast(name, duration, imageURL, description, (Category) type, nickname)
                        : String.valueOf(type);
            }
        }
        return msg;
    }

    private String readUserNickname() {
        System.out.print("Nickname: ");
        return Reader.readString();
    }

    private String registerConsumer() {
        String nickname = readUserNickname();
        System.out.print("What's its id? ");
        String id = Reader.readString();
        System.out.print(controller.getEnumTypes(ConsumerType.class));
        Object type = validateType(Reader.readInt(), ConsumerType.class);

        return type instanceof ConsumerType ? controller.createConsumer(nickname, id, (ConsumerType) type)
                : String.valueOf(type);
    }

    private String registerProducer() {
        String nickname = readUserNickname();
        System.out.print("What's its name? ");
        String name = Reader.readString();
        System.out.print("Profile photo URL: ");
        String imageURL = Reader.readString();
        System.out.print(controller.getEnumTypes(ProducerType.class));
        Object type = validateType(Reader.readInt(), ProducerType.class);

        return type instanceof ProducerType ? controller.createProducer(nickname, name, imageURL, (ProducerType) type)
                : String.valueOf(type);
    }

    private Object validateType(int typeValue, Class<?> classType) {
        Object result = null;
        try {
            if (classType == ProducerType.class) {
                result = ProducerType.values()[typeValue];
            } else if (classType == ConsumerType.class) {
                result = ConsumerType.values()[typeValue];
            } else if (classType == Genre.class) {
                result = Genre.values()[typeValue];
            } else if (classType == Category.class) {
                result = Category.values()[typeValue];
            } else {
                result = "Invalid entity";
            }
        } catch (Exception e) {
            result = "Value not supported";
        }
        return result;
    }
}