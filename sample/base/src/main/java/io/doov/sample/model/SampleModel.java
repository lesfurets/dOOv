package io.doov.sample.model;

public class SampleModel {

    private User user = new User();
    private Account account = new Account();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
