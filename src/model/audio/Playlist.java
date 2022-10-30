package model.audio;

import java.util.ArrayList;
import java.util.List;

import model.Audio;
import model.UtilMatrix;

public class Playlist {

    private String id;
    private List<Audio> audios;
    private String owner;
    private String name;
    private int[][] matrix;

    public Playlist(String owner, String name, int codeType) {
        this.audios = new ArrayList<Audio>();
        this.name = name;
        this.owner = owner;
        this.id = generateCode(codeType);
    }

    public String generateCode(int type) {
        String code = "";
        this.matrix = UtilMatrix.randomMatrix(6, 6);

        switch (type) {
            case 1:
                code += UtilMatrix.vLine(matrix, UtilMatrix.UP, 0, 5, 1);
                code += UtilMatrix.dLine(matrix, UtilMatrix.DOWN, UtilMatrix.RIGHT, 0, 0, 1);
                code += UtilMatrix.vLine(matrix, UtilMatrix.UP, 5, 5, 0);
                break;
            case 2:
                code += UtilMatrix.hLine(matrix, UtilMatrix.RIGHT, 0, 0, -3);
                code += UtilMatrix.vLine(matrix, UtilMatrix.DOWN, 2, 1, 0);
                code += UtilMatrix.vLine(matrix, UtilMatrix.UP, 3, 5, -1);
                code += UtilMatrix.hLine(matrix, UtilMatrix.RIGHT, 3, 0, 0);
                break;
            case 3:
                for (int i = 5; i >= 0 && code.length() < 16; i--) {
                    for (int j = 5; j >= 0 && code.length() < 16; j--) {
                        if ((i + j) % 2 != 0 && (i + j) > 1) {
                            code += matrix[i][j];
                        }
                    }
                }
                break;
            default:
                break;
        }
        return code;
    }

    public boolean addAudio(Audio audio) {
        return audios.add(audio);
    }

    public List<Audio> getAudios() {
        return audios;
    }

    public String getOwner() {
        return owner;
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

}
