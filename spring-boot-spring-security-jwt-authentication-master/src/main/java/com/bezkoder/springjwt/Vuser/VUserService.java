package com.bezkoder.springjwt.Vuser;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class VUserService {

    private static final List<VUser> USERS_LIST = new ArrayList<>();

    public void register(VUser user) {
        user.setStatus("online");
        USERS_LIST.add(user);
    }

    public VUser login(VUser user) {
        var userIndex = IntStream.range(0, USERS_LIST.size())
                .filter(i -> USERS_LIST.get(i).getEmail().equals(user.getEmail()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("User not found"));
        var cUser = USERS_LIST.get(userIndex);
        if (!cUser.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Password incorrect");
        }
        cUser.setStatus("online");
        return cUser;
    }

    public void logout(String email) {
        var userIndex = IntStream.range(0, USERS_LIST.size())
                .filter(i -> USERS_LIST.get(i).getEmail().equals(email))
                .findAny()
                .orElseThrow(() -> new RuntimeException("User not found"));
        USERS_LIST.get(userIndex).setStatus("offline");
    }

    public List<VUser> findAll() {
        return USERS_LIST;
    }
}
