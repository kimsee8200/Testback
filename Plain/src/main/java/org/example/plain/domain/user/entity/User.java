package org.example.plain.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @Column(name = "id", length = 100, nullable = false)
    private Long userId;

    @Column(name = "name", length = 100, nullable = false)
    private String userName;
}
