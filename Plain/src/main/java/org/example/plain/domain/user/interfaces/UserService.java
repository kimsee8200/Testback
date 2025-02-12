package org.example.plain.domain.user.interfaces;

import org.example.plain.domain.user.dto.User;

public interface UserService {
    public boolean createUser(User user);
    public boolean updateUser(User user);
    public boolean deleteUser(String id);
    public User getUser(String id);
}
