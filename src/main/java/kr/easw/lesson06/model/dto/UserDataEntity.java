package kr.easw.lesson06.model.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserDataEntity {
    @Id
    @GeneratedValue
    private long id;

    @Getter
    private String userId;

    @Getter
    private String password;

    @Getter
    private boolean isAdmin;

}
