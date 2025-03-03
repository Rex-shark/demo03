package com.example.demoservicejpa.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "course")
@Data
public class Course extends AbstractEntity {
    private String title;

    @JsonBackReference
    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private List<Child> children;

    @PrePersist
    private void initPrePersist() {
        super.init();
    }
}
