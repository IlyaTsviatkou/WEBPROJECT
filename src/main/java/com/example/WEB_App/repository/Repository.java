package com.example.WEB_App.repository;

import com.example.WEB_App.creator.CustomUserFactory;
import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.entity.CustomUser;

import java.util.*;
import java.util.stream.Collectors;

public class Repository {
    private static final Repository INSTANCE = new Repository();
    //CustomUser m = new CustomUser();
    private List<CustomUser> users = new ArrayList<>();

    public Repository() {
        users = CustomUserFactory.getCustomUsers();
    }

    public static Repository getInstance() {
        return INSTANCE;
    }

    public List<CustomUser> getCustomUsers() {
        return Collections.unmodifiableList(users);
    }

    public void addCustomUser(CustomUser user) {
        users.add(user);
    }

    public void removeCustomUser(CustomUser user) {
        users.remove(user);
    }

    public CustomUser get(int index) {
        return users.get(index);
    }

    public CustomUser set(int index, CustomUser element) {
        return users.set(index, element);
    }

    public List<CustomUser> query(Specification specification) {
        List<CustomUser> list = users.stream().filter(specification::specify).collect(Collectors.toList());
        return list;
    }

    public Optional<CustomUser> get(Specification specification) {
        Optional<CustomUser> user = users.stream().filter(specification::specify).findFirst();
        return user;
    }

    public List<CustomUser> sortByParameter(Comparator<CustomUser> comparator) {
        List<CustomUser> list = new ArrayList(users);
        list.sort(comparator);
        return list;
    }
}
