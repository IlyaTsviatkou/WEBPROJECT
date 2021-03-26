package com.example.WEB_App.creator;

import com.example.WEB_App.entity.CustomUser;

import java.util.ArrayList;
import java.util.List;

public class CustomUserFactory {
    public static List<CustomUser> getCustomUsers() {
        List<CustomUser> list = new ArrayList<>();
        list.add(new CustomUser(1, "first", "one" , "oops"));
        list.add(new CustomUser(2, "second", "two", "oops, im second"));
        list.add(new CustomUser(3, "third", "three", "yeeap its me"));
        list.add(new CustomUser(4, "fourth", "four", "ugu"));
        list.add(new CustomUser(5, "fifth", "five", "BYE BYE"));
        list.add(new CustomUser(6, "sixth", "six", "OPEN UR EYES"));
        list.add(new CustomUser(7, "seventh", "seven", "HAHAHAHAHAH"));

        return list;
    }
}
