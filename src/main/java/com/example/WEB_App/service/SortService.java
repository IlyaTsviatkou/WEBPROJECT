package com.example.WEB_App.service;

import com.example.WEB_App.comparator.UserComparator;
import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.repository.Repository;
import com.example.WEB_App.repository.impl.LoginSpecification;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SortService {

    public List<CustomUser> SortedList(Comparator<CustomUser> comparator) {
        ArrayList<CustomUser> user;
        Repository rep =new Repository();
        user = new ArrayList(rep.sortByParameter(comparator));
        return user;
    }
}
