package me.akaada.service;

public class UserServiceFactory {
    public static IUserService createUserService() {
        return new UserServiceImpl();
    }
}
