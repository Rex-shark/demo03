package com.example.demoservice.entity.jpa_n1;

import com.example.demoservice.entity.base.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "n1_info")
@Data
public class N1Info  {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    @JsonBackReference//子
    @OneToOne(fetch = FetchType.LAZY)  // 與 Parent 建立一對一關聯
    @JoinColumn(name = "parent_id")  // 指定外鍵
    private Parent parent;

    @JsonBackReference//子
    @OneToOne(fetch = FetchType.LAZY)  // 與 child 建立一對一關聯
    @JoinColumn(name = "child_id")  // 指定外鍵
    private Child child;
}
