package com.example.demoservice.request.api;


import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class SysRoleRequest {

    private String nid = "";
    private String sysRoleName = "";
    private Integer status = 1;
    private String remark = "";
    private List<String> sysMenuNid = new ArrayList<>();
}

