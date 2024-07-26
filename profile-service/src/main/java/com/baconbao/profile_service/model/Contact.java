package com.baconbao.profile_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="contact")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    private Integer id;
    private String address;
    private String phone;
    private String email;
}
