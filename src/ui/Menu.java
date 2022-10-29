
package ui;

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
            default:
                msg = "Invalid option.";
                break;
        }
        System.out.println(msg);
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
                System.out.print("What's the genre of the song?\n" + controller.getSongGenres());
                Object type = validateGenre(Reader.readInt());

                msg = type instanceof Genre ? controller.registerSong(name, duration, imageURL, (Genre) type, nickname)
                        : String.valueOf(type);

            } else if (user instanceof Creator) {
                System.out.print("Give a brief description: ");
                String description = Reader.readString();
                System.out.print("What's the podcast category?\n" + controller.getPodcastCategories());
                Object type = validateCategory(Reader.readInt());

                msg = type instanceof Genre
                        ? controller.registerPodcast(name, duration, imageURL, description, (Category) type, nickname)
                        : String.valueOf(type);
            }
        }
        return msg;
    }

    private String readUserNickname() {
        System.out.println("What's your nickname?");
        return Reader.readString();
    }

    private String registerConsumer() {
        System.out.print("What's its nickname? ");
        String nickname = Reader.readString();
        System.out.print("What's its id? ");
        String id = Reader.readString();
        System.out.print(controller.getConsumerTypes());
        Object type = validateConsumerType(Reader.readInt());

        return type instanceof ConsumerType ? controller.addConsumer(nickname, id, (ConsumerType) type)
                : String.valueOf(type);
    }

    private String registerProducer() {
        System.out.print("What's its nickname? ");
        String nickname = Reader.readString();
        System.out.print("What's its name? ");
        String name = Reader.readString();
        System.out.print("Profile photo URL: ");
        String imageURL = Reader.readString();
        System.out.print(controller.getProducerTypes());
        Object type = validateProducerType(Reader.readInt());

        return type instanceof ProducerType ? controller.addProducer(nickname, name, imageURL, (ProducerType) type)
                : String.valueOf(type);
    }

    private Object validateConsumerType(int typeValue) {
        Object result = null;
        try {
            result = ConsumerType.values()[typeValue];
        } catch (Exception e) {
            result = "Invalid type of consumer";
        }
        return result;
    }

    private Object validateProducerType(int typeValue) {
        Object result = null;
        try {
            result = ProducerType.values()[typeValue];
        } catch (Exception e) {
            result = "Invalid type of producer";
        }
        return result;
    }

    private Object validateGenre(int typeValue) {
        Object result = null;
        try {
            result = Genre.values()[typeValue];
        } catch (Exception e) {
            result = "Invalid genre";
        }
        return result;
    }

    private Object validateCategory(int typeValue) {
        Object result = null;
        try {
            result = Category.values()[typeValue];
        } catch (Exception e) {
            result = "Invalid category";
        }
        return result;
    }
}