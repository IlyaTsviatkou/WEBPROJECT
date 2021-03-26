package com.example.WEB_App.comparator;

import com.example.WEB_App.entity.CustomUser;

import java.util.Comparator;

public enum UserComparator implements Comparator<CustomUser> {
    ID {
        @Override
        public int compare (CustomUser user1, CustomUser user2){
            return Long.compare(user1.getId(), user2.getId());
        }
    }
}
