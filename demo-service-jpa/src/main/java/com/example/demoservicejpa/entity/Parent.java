package com.example.demoservicejpa.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "parent")
@Data
public class Parent extends AbstractEntity {
    private String name;

    @JsonManagedReference//父
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Child> children;

    @JsonManagedReference//父
    @OneToOne(mappedBy = "parent")  // 設定反向關聯
    private UserInfo userInfo;

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", childrenCount=" + (children != null ? children.size() : 0) +
                ", userInfoId=" + (userInfo != null ? userInfo.getId() : "null") +
                '}';
    }

    @PrePersist
    private void initPrePersist() {
        super.init();
    }
}
