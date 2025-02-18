package org.example.plain.domain.user.interfaces;

import org.example.plain.domain.user.dto.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public boolean createUser(User user);

    boolean updateUser(User user);

    public boolean deleteUser(String id);
    public User getUser(String id);
    public User getUserByEmail(String email);
}
