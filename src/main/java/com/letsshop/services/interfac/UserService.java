package com.letsshop.services.interfac;

import com.letsshop.dto.LoginDTO;
import com.letsshop.dto.UserDTO;

public interface UserService {
    void registerUser(UserDTO userDTO);
    boolean loginUser(LoginDTO loginDTO);
}
