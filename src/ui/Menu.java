
package ui;

import java.util.ArrayList;
import java.util.List;

import model.Controller;
import model.User;
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
            "Input: ";

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
            default:
                msg = "Invalid option.";
                break;
        }
        System.out.println(msg);
    }

    private String createPlaylist() {
        String msg = null;

        String nickname = readUserNickname();

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

        msg = controller.registerPlaylist(nickname, classesMenu, audioTypes);
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

        return type instanceof ConsumerType ? controller.addConsumer(nickname, id, (ConsumerType) type)
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

        return type instanceof ProducerType ? controller.addProducer(nickname, name, imageURL, (ProducerType) type)
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