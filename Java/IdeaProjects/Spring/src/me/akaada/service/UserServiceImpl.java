package me.akaada.service;

public class UserServiceImpl implements IUserService {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void add() {
        System.out.println("added user: " + name);
    }
}
