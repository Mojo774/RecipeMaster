package sample.app_service;

import sample.User;

class UserProcess {
    private User user;

    public UserProcess(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getIdUser() {
        return user.getId();
    }
}
