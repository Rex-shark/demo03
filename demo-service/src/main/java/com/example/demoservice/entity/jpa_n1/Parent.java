package com.example.demoservice.entity.jpa_n1;

import com.example.demoservice.entity.base.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

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
    private N1Info n1Info;

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", childrenCount=" + (children != null ? children.size() : 0) +
                ", n1InfoId=" + (n1Info != null ? n1Info.getId() : "null") +
                '}';
    }

}
