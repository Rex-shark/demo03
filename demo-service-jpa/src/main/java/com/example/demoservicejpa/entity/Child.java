package com.example.demoservicejpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "child")
@Data
public class Child extends AbstractEntity {
    private String name;

    @JsonBackReference//子
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @JsonManagedReference//父
    @OneToOne(mappedBy = "child")  // 設定反向關聯
    private UserInfo userInfo;

    @JsonManagedReference//父
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "child_course",
            joinColumns = @JoinColumn(name = "child_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    @PrePersist
    private void initPrePersist() {
        super.init();
    }
}
