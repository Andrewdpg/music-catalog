package model.audio;

import java.util.ArrayList;
import java.util.List;

import model.Audio;

public class Playlist {

    private String id;
    private List<Audio> audios;
    private String name;
    private int[][] matrix;
    private final List<Class<?>> audioTypes;

    public Playlist(String name, List<Class<?>> audioTypes, int[][] matrix, String code) {
        this.audios = new ArrayList<Audio>();
        this.name = name;
        this.audioTypes = audioTypes;
        this.matrix = matrix;
        this.id = code;
    }

    /**
     * Removes an audio from the list
     * 
     * @param id id of the audio to remove
     * @return True in case of success. False otherwise.
     */
    public boolean removeAudio(String id) {
        boolean isRemoved = false;
        for (int i = 0; i < audios.size(); i++) {
            if (audios.get(i).getId().equals(id)) {
                isRemoved = audios.remove(audios.get(i));
            }
        }
        return isRemoved;
    }

    /**
     * Adds an audio to the list
     * 
     * @param audio Audio to be added
     * @return True in case of success. False otherwise.
     */
    public boolean addAudio(Audio audio) {
        return audios.add(audio);
    }

    public boolean removeAudio(Audio audio) {
        return audios.remove(audio);
    }

    public List<Audio> getAudios() {
        return audios;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    /**
     * Returns a String with the matrix data.
     * 
     * @return String with the matrix
     */
    public String getMatrix() {
        String matrixS = "";

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrixS += "[" + this.matrix[i][j] + "]";
            }
            matrixS += "\n";
        }
        return matrixS;
    }

    /**
     * List of classes which can be added to this playlist
     * 
     * @return List of "supported" classes
     */
    public List<Class<?>> getAudioTypes() {
        return audioTypes;
    }

    @Override
    public String toString() {
        String value = "Playlist name: " + name + " - ID: " + id + "\n";

        for (int i = 0; i < audios.size(); i++) {
            value += (i + 1) + ". " + audios.get(i).toString() + "\n";
        }
        return value;
    }

    /**
     * Moves an audio to a new position in the list
     * 
     * @param oldPosition position of the audio to be moved
     * @param newPosition new position
     */
    public void changeAudioPosition(int oldPosition, int newPosition) {
        Audio audio = audios.remove(oldPosition);
        audios.add(newPosition, audio);
    }

}
