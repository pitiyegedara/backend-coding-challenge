package com.entertainment.domain.user.core;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Setter
public class User {

    private UUID id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private Date createdAt;
    private Date updatedAt;
}
