package model;

import java.util.ArrayList;
import java.util.List;

import model.user.consumer.ConsumerType;
import model.user.consumer.PremiumConsumer;
import model.user.consumer.Standard;
import model.user.producer.Artist;
import model.user.producer.Creator;
import model.user.producer.ProducerType;

public class Controller {

    private List<User> users;

    public Controller() {
        this.users = new ArrayList<User>();
    }

    public List<User> getUsers() {
        return users;
    }

    public String getUser(String nickname) {
        String user = null;
        for (int i = 0; i < users.size(); i++) {
            if (nickname.equals(users.get(i).getNickname())) {
                user = users.get(i).toString();
            }
        }
        return user;
    }

    public boolean userExist(String nickname) {
        return getUser(nickname) != null;
    }

    public String getConsumerTypes() {
        String msg = "";
        for (int i = 0; i < ConsumerType.values().length; i++) {
            msg += i + ". " + ConsumerType.values()[i]+"\n";
        }
        return msg;
    }

    public String addConsumer(String nickname, String id, int type) {
        String msg;
        switch (type) {
            case 0:
                msg = addUser(new Standard(nickname, id));
                break;
            case 1:
                msg = addUser(new PremiumConsumer(nickname, id));
                break;
            default:
                msg = "Invalid type of user";
                break;
        }
        return msg;
    }

    public String getProducerTypes() {
        String msg = "";
        for (int i = 0; i < ProducerType.values().length; i++) {
            msg += i + ". " + ProducerType.values()[i]+"\n";
        }
        return msg;
    }

    public String addProducer(String nickname, String name, String imageURL, int type) {
        String msg;

        switch (type) {
            case 0:
                msg = addUser(new Artist(nickname, name, imageURL));
                break;
            case 1:
                msg = addUser(new Creator(nickname, name, imageURL));
                break;
            default:
                msg = "Invalid type of user";
                break;
        }
        return msg;
    }

    private String addUser(User user) {
        String msg = "Nickname already exist.";
        if (!userExist(user.getNickname())) {
            if (users.add(user)) {
                msg = "User successfully added.";
            } else {
                msg = "Failure. User not added.";
            }
        }
        return msg;
    }
}
