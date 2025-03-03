package com.example.demoservice.entity.jpa_n1;

import com.example.demoservice.entity.base.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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

}
