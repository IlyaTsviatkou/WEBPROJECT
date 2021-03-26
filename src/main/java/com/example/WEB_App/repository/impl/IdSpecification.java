package com.example.WEB_App.repository.impl;

import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.repository.Specification;

public class IdSpecification implements Specification {
    private long id;

    public IdSpecification(long id) {
        this.id = id;
    }

    @Override
    public boolean specify(CustomUser user) {
        boolean result = user.getId() == id;
        return result;
    }
}
