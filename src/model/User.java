package model;

import java.sql.Timestamp;
import java.time.Instant;

public abstract class User {

    private Timestamp bindingDate;
    private String nickname;

    public User( String nickname) {
        this.nickname = nickname;
        this.bindingDate = Timestamp.from(Instant.now());
    }

    public Timestamp getBindingDate() {
        return bindingDate;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}