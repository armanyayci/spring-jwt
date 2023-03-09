package com.jwt.jwt.dto.request;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class RegisterDTO
{
        public String name;
        public String Username;
        public String password;
        public String email;
        public List<String> Role;

}

