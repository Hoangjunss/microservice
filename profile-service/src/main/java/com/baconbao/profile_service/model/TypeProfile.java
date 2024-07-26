package com.baconbao.profile_service.model;

import jakarta.persistence.Id;

public class TypeProfile {
    @Id
    private Integer id;
    private String name;
}
