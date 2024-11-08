package tech.betterwith.tradingsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tech.betterwith.tradingsystem.constants.AppRole;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private AppRole name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<AppUser> users = new HashSet<>();

    public Role(AppRole name) {
        this.name = name;
    }

}
