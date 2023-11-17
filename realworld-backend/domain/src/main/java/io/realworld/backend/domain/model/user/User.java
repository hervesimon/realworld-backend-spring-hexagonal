package io.realworld.backend.domain.model.user;

import java.util.Optional;
import java.util.StringJoiner;

public class User {
    private long id = 0;

    private String email = "";
    private String username = "";
    private String passwordHash = "";

    private String bio = null;
    private String image = null;

    protected User() {
    }

    /** Creates User instance. */
    public User(String email, String username, String passwordHash) {
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Optional<String> getBio() {
        return Optional.ofNullable(bio);
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Optional<String> getImage() {
        return Optional.ofNullable(image);
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]").add("id=" + id)
                                                                            .add("email='" + email + "'")
                                                                            .add("username='" + username + "'")
                                                                            .add("passwordHash='" + passwordHash + "'")
                                                                            .add("bio='" + bio + "'")
                                                                            .add("image='" + image + "'")
                                                                            .toString();
    }
}
