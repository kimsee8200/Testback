package org.example.plain.domain.user.interfaces;

import org.example.plain.domain.user.dto.UserRequestResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public boolean createUser(UserRequestResponse userRequestResponse);

    boolean updateUser(UserRequestResponse userRequestResponse);

    public boolean deleteUser(String id);
    public UserRequestResponse getUser(String id);
    public UserRequestResponse getUserByEmail(String email);
}
