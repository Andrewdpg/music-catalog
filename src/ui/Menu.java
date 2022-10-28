
package ui;

import model.Controller;

/**
 * Menu
 */
public class Menu {

    private Controller controller;
    private int option;
    private static final String MENU = "\n\n<< Spotify pirata >>\n" +
            "1. Add producer.\n" +
            "2. Add consumer.\n" +
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
            default:
                msg = "Invalid option.";
                break;
        }
        System.out.println(msg);
    }

    private String registerConsumer() {
        System.out.print("What's its nickname? ");
        String nickname = Reader.readString();
        System.out.print("What's its id? ");
        String id = Reader.readString();
        System.out.print(controller.getConsumerTypes());
        int type = Reader.readInt();

        return controller.addConsumer(nickname, id, type);
    }

    private String registerProducer() {
        System.out.print("What's its nickname? ");
        String nickname = Reader.readString();
        System.out.print("What's its name? ");
        String name = Reader.readString();
        System.out.print("Profile photo URL: ");
        String imageURL = Reader.readString();
        System.out.print(controller.getProducerTypes());
        int type = Reader.readInt();

        return controller.addProducer(nickname, name, imageURL, type);
    }
}