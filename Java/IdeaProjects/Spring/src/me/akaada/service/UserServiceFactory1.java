package me.akaada.service;

public class UserServiceFactory1 {
    public IUserService createUserService() {
        return new UserServiceImpl();
    }
}
