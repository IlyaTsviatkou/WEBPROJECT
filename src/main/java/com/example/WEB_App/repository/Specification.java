package com.example.WEB_App.repository;

import com.example.WEB_App.entity.CustomUser;

public interface Specification {
    boolean specify(CustomUser user);
}
