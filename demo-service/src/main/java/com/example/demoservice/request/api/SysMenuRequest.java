package com.example.demoservice.request.api;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class SysMenuRequest {

    private String nid;
    private String href ;
    private String iconCls;
    private String menuName ;
    private String remark ;
    private String platformName ;

    private Integer status = 1;
    private Integer sortNum = 0;

    private Long parentId ;

    private List<String> sysRoleNid = new ArrayList<>();
}

