
package ui;

import java.util.ArrayList;
import java.util.List;

import model.Audio;
import model.Controller;
import model.User;
import model.audio.Playlist;
import model.audio.Song;
import model.audio.podcast.Category;
import model.audio.song.Genre;
import model.user.Consumer;
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
    // Main menu options
    private static final String MENU = "\n\n<< Spotify pirata >>\n" +
            "1. Add producer.\n" +
            "2. Add consumer.\n" +
            "3. Add an audio.\n" +
            "4. Create a playlist.\n" +
            "5. Playlist options.\n" +
            "6. Search playlist by ID.\n" +
            "7. Play an audio.\n" +
            "8. Buy a song.\n" +
            "9. Total reproductions by audio type. \n" +
            "10. Most reproduced by classification. \n" +
            "11. Producers ranking. \n" +
            "12. Audios ranking. \n" +
            "13. Total sales. \n" +
            "14. Top selling song \n" +
            "0. Exit.\n" +
            "Input: ";
    // Options for editing a playlist.
    private static final String EDITING_PLAYLIST_MENU = "1. Add audio.\n" +
            "2. Remove audio.\n" +
            "3. Change name.\n" +
            "4. Change audio position.\n" +
            "5. Show playlist.\n" +
            "6. Share playlist.\n" +
            "7. Delete playlist.\n" +
            "0. Done.";

    public Menu() {
        option = -1;
        controller = new Controller();
    }

    /**
     * @return menu's state (alive or closed)
     */
    public boolean isAlive() {
        return this.option != 0;
    }

    /**
     * Reads user's selection
     */
    public void readOption() {
        this.option = Reader.readInt();
    }

    /**
     * Shows the main menu
     */
    public void showMenu() {
        System.out.print(MENU);
    }

    /**
     * Executes menu's current selected option.
     */
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
                msg = playlistOptions();
                break;
            case 6:
                msg = searchPlaylistById();
                break;
            case 7:
                playAudios();
                break;
            case 8:
                msg = buyASong();
                break;
            case 9:
                msg = controller.getTotalReproductionsByType();
                break;
            case 10:
                msg = mostPlayedByClassification();
                break;
            case 11:
                msg = controller.producersRanking();
                break;
            case 12:
                msg = controller.audiosRanking();
                break;
            case 13:
                msg = controller.getTotalSales();
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

    public String mostPlayedByClassification() {
        String msg = null;
        System.out.print("\n1. An specific user.\n2. The entire platform.\nInput: ");
        int input = Reader.readInt();
        try {
            switch (input) {
                case 1:
                    String nickname = readUserNickname();
                    msg = controller.getMostPlayedClassificationOf(nickname);
                    break;
                case 2:
                    Class<?>[] classifications = controller.getAudioClassification();
                    String classesMenu = "\nWhich classification are you looking for? \n";
                    for (int i = 1; i <= classifications.length; i++) {
                        classesMenu += i + ". " + classifications[i - 1].getSimpleName() + "\n";
                    }
                    System.out.print(classesMenu + "Input: ");
                    input = Reader.readInt();
                    msg = controller.getMostPlayedByClassification(classifications[input - 1]);
                    break;
                default:
                    msg = "Invalid option";
                    break;
            }
        } catch (Exception e) {
            msg = "Invalid input";
        }
        return msg;
    }

    public String buyASong() {
        User user = controller.getUser(readUserNickname());

        String msg = "This user doesn't exist";
        if (user != null) {
            msg = "This user is not a consumer";
            if (user instanceof Consumer) {
                List<Song> availableSongs = controller.getAvailableSongsForPurchase(user.getNickname());
                msg = "There are not new songs available for purchase";
                if (availableSongs != null) {
                    System.out.print("\n" + listToString(availableSongs) + "\n0. Cancel\n\nInput: ");
                    int input = Reader.readInt();
                    msg = "Invalid input";
                    if (input >= 1 && input <= availableSongs.size()) {
                        System.out.print("\nConfirm purchase for " + availableSongs.get(input - 1).getPrice()
                                + "$? \n1. Confirm\n2. Cancel\nInput: ");
                        int confirmation = Reader.readInt();
                        msg = "Cancelled";
                        if (confirmation == 1) {
                            msg = controller.buySongFor(user.getNickname(), availableSongs.get(input - 1).getId());
                        } else if (confirmation != 2) {
                            msg = "Invalid input";
                        }
                    }
                }
            }
        }
        return msg;
    }

    public void playAudios() {
        String nickname = readUserNickname();

        User user = controller.getUser(nickname);
        if (user != null && user instanceof Consumer) {
            try {
                List<Audio> availableAudios = controller.audiosForUser(nickname);
                System.out.print(
                        "\nList of available audios: \n" + listToString(availableAudios) + "\n0. Cancel\n\nInput: ");
                int input = Reader.readInt();
                if (input != 0) {
                    String ad = controller.increaseAdPercentageTo(nickname, availableAudios.get(input - 1).getId());
                    if (ad != null) {
                        System.out.println(ad + "\n");
                    }
                    controller.userPlayed(nickname, availableAudios.get(input - 1).getId());
                    availableAudios.get(input - 1).play();
                }

            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        } else {
            System.out.println("Looks like the user doesn't exist or it is no a consumer.\n");
        }

    }

    public String searchPlaylistById() {
        String msg = "Playlist not found.";
        System.out.print("What's the playlist id? ");
        String id = Reader.readString();

        Playlist playlist = controller.getPlaylistById(id);
        if (playlist != null) {
            msg = "\n" + playlist.toString();
        }
        return msg;
    }

    /**
     * Ask for an user's nickname, shows its playlists and a menu with option for
     * editing them
     * 
     * @return A message with an ending operation
     */
    private String playlistOptions() {
        String msg = "Non-existent user";
        String nickname = readUserNickname();

        List<Playlist> playlists = controller.getUserPlaylists(nickname);
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
                            System.out.println(executePlaylistOption(selectedPlaylist, nickname));
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

    /**
     * Reads and executes an option for editing a playlist
     * 
     * @param selectedPlaylist user's selected playlist
     * @param nickname         user's nickname
     * @return message with the resulting operation
     */
    private String executePlaylistOption(Playlist selectedPlaylist, String nickname) {
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
                msg = sharePlaylist(selectedPlaylist);
                break;
            case 7:
                msg = deletePlaylistOf(nickname, selectedPlaylist);
            case 0:
                break;
            default:
                msg = "Invalid input";
                break;
        }
        return msg;
    }

    /**
     * Lists all available audios (podcasts and bought songs) for current user and
     * asks for the user to select which one to add to the selected playlist.
     * 
     * @param nickname         user's nickname
     * @param selectedPlaylist user's selected playlist
     * @return a message with the final result
     */
    private String addAudioToPlaylist(String nickname, Playlist selectedPlaylist) {
        String msg = "";
        List<Audio> availableAudios = controller.audiosForUser(nickname,
                selectedPlaylist.getAudioTypes(),
                selectedPlaylist.getId());
        System.out.print("\nList of available audios: \n" + listToString(availableAudios) + "\n0. Cancel\n\nInput: ");
        int input = Reader.readInt();
        if (input != 0) {
            msg = controller.addAudioToUserPlaylist(nickname, selectedPlaylist.getId(),
                    availableAudios.get(input - 1).getId());
        }
        return msg;
    }

    /**
     * Lists all audios in selected playlist and asks to the user which one to
     * delete from it.
     * 
     * @param nickname         user's nickname
     * @param selectedPlaylist user's selected playlist
     * @return a message with the final result
     */
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

    /**
     * Allows the user to rename the current playlist
     * 
     * @param nickname         user's nickname
     * @param selectedPlaylist user's selected playlist
     * @return a message with the final result
     */
    public String changeNameToPlaylist(String nickname, Playlist selectedPlaylist) {
        System.out.print("New name: ");
        String newName = Reader.readString();
        return controller.changeNameToUserPlaylist(nickname, selectedPlaylist.getId(), newName);
    }

    /**
     * Lists all audios in selected playlist and allows the user to change one os
     * them's position in playlist.
     * 
     * @param nickname         user's nickname
     * @param selectedPlaylist user's selected playlist
     * @return a message with the final result
     */
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

    public String sharePlaylist(Playlist selectedPlaylist) {
        return "\nPlaylist ID: " + selectedPlaylist.getId() + "\n\nUsed matrix: \n" + selectedPlaylist.getMatrix();
    }

    /**
     * Asks to the user to confirm the deleting operation and executes it.
     * 
     * @param nickname         user's nickname
     * @param selectedPlaylist user's selected playlist
     * @return a message with the final result
     */
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

    /**
     * Asks for a customer user and a new playlist's data to send a petition for
     * creating it.
     * 
     * @return a message with the result of this operation
     */
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

    /**
     * Asks for a producer type user and a new audio to be registered. Them it sends
     * the petition to the controller.
     * 
     * @return a message with controller response.
     */
    private String registerAudio() {
        String msg = "this user is not a producer.";
        String nickname = readUserNickname();
        User user = controller.getUser(nickname);

        if (user instanceof Producer) {
            System.out.print("What's the audio name? ");
            String name = Reader.readString();
            System.out.print("How long the audio is? (seconds) ");
            int duration = Reader.readInt();
            System.out.print("What's the audio image url? ");
            String imageURL = Reader.readString();

            if (user instanceof Artist) {
                System.out.print("What's the genre of the song?\n" + controller.getEnumTypes(Genre.class));
                Object type = validateType(Reader.readInt(), Genre.class);
                System.out.print("What is its price? ");
                double price = Reader.readDouble();

                msg = "Invalid price";
                if (price >= 0) {
                    msg = "Invalid duration";
                    if (duration > 0) {
                        msg = type instanceof Genre
                                ? controller.registerSong(name, duration * 1000, imageURL, (Genre) type, nickname,
                                        price)
                                : String.valueOf(type);
                    }
                }

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

    /**
     * Reads an users's nickname
     * 
     * @return read nickname
     */
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

    /**
     * Sends to controller a registering petition of a new producer type user.
     * 
     * @return message with controller's response
     */
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

    /**
     * Validates if a type of user (consumer or producer) or classification of audio
     * (podcast category or song genre) actually exists.
     * 
     * Using their respective enums tries to select the one the user selected.
     * In case it fails, a message will be returned. Otherwise it will return the
     * enum value of user's selection.
     * 
     * @param typeValue input of the user (the enum value it selected).
     * @param classType class of the enum it will validate (ProducerType,
     *                  ConsumerType, Genre or Category).
     * @return An enum object in case of success. A String message otherwise.
     */
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

    public String listToString(List<?> list) {
        String out = null;
        if (list != null) {
            if (!list.isEmpty()) {
                out = "";
                for (int i = 0; i < list.size(); i++) {
                    out += (i + 1) + ". " + list.get(i).toString() + "\n";
                }
            }
        }
        return out;
    }
}