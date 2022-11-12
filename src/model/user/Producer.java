package model.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Audio;
import model.User;

public abstract class Producer extends User {

    private String name;
    private String imageURL;
    private List<Audio> audios;

    public Producer(String nickname, String name, String imageURL) {
        super(nickname);
        this.name = name;
        this.imageURL = imageURL;
        this.audios = new ArrayList<Audio>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<Audio> getAudios() {
        return audios;
    }

    public boolean addAudio(Audio audio) {
        return this.audios.add(audio);
    }

    public void playedAudio(String audioID) {
        for (int i = 0; i < audios.size(); i++) {
            if (audios.get(i).getId().equals(audioID)) {
                audios.get(i).increaseReproductions();
                i = audios.size();
            }
        }
    }

    public Map<String, Integer> audioTypeStadistics() {
        Map<String, Integer> classification = new HashMap<String, Integer>();
        int currentCount = 0;
        String currentClass = "";
        for (int i = 0; i < audios.size(); i++) {
            currentClass = audios.get(i).getClass().getSimpleName();
            currentCount = classification.get(currentClass) != null ? classification.get(currentClass) : 0;
            classification.put(currentClass, currentCount + audios.get(i).getTimesReproduced());
        }
        return classification;
    }

}
