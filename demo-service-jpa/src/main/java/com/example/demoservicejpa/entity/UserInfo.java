package com.example.demoservicejpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_info")
@Data
public class UserInfo {
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
