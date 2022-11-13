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
        Map<String, Integer> stadistics = new HashMap<String, Integer>();
        int currentCount = 0;
        String currentClass = "";
        for (int i = 0; i < audios.size(); i++) {
            currentClass = audios.get(i).getClass().getSimpleName();
            currentCount = stadistics.get(currentClass) != null ? stadistics.get(currentClass) : 0;
            stadistics.put(currentClass, currentCount + audios.get(i).getTimesReproduced());
        }
        return stadistics;
    }

    public Map<String,Integer> classificationStadistics(Class<?> type){
        Map<String, Integer> stadistics = new HashMap<String, Integer>();
        int currentCount = 0;
        String currentClass = "";
        for (int i = 0; i < audios.size(); i++) {
            if(audios.get(i).getClassication().getClass() == type){
                currentClass = audios.get(i).getClassication().name();
                currentCount = stadistics.get(currentClass) != null ? stadistics.get(currentClass) : 0;
                stadistics.put(currentClass, currentCount + audios.get(i).getTimesReproduced());
            }
        }
        return stadistics;
    }

    public int totalReproductions(){
        int count = 0;
        for (Audio audio : audios) {
            count += audio.getTimesReproduced();
        }
        return count;
    }

    @Override
    public int compareTo(User o) {
        int result = super.compareTo(o);
        if(o instanceof Producer){
            int reproductions = totalReproductions();
            int compareReproductions = ((Producer) o).totalReproductions();
            if(reproductions > compareReproductions){
                result = -1;
            }else if(reproductions == compareReproductions){
                result = 0;
            }else{
                result = 1;
            }
        }
        return result;
    }

    @Override
    public String toString(){
        return name + " - Total reproductions: " + totalReproductions(); 
    }

}
