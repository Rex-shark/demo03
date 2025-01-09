package com.example.demoservice.service.impl;

import com.example.demoservice.entity.*;
import com.example.demoservice.repository.*;
import com.example.demoservice.service.SysService;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class SysServiceImp implements SysService {
    @Resource
    private IUserBaseRepository userBaseRepository;

    @Resource
    private ISysRoleRepository sysRoleRepository;

    @Resource
    private ISysMenuRepository sysMenuRepository;

    @Resource
    private ISysRoleMenuRepository sysRoleMenuRepository;



    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public void initUser() {

        if (userBaseRepository.findByAccount("admin").isPresent()) {
            return;
        }
        //UserBase（用戶資料）
        this.initUserBase();

        //SysRole（角色資料）
        this.initSysRole();

        //SysMenu（菜單資料）
        this.initSysMenu();

        //SysRoleMenu（角色與菜單關係）
        this.intiSysRoleMenu();

    }

    @Override
    public void initUser2() {
        if (!userBaseRepository.findByAccount("admin").isEmpty()) {
            return;
        }

        //先新增角色
        SysRole sysRole = new SysRole();
        sysRole.setId(1L);
        sysRole.setName("系統管理員");
        sysRole.setNid("ADMIN");
        sysRole.setStatus(1);
        sysRole.setCreatedUserId(1L);
        sysRoleRepository.save(sysRole);

        sysRole = new SysRole();
        sysRole.setId(2L);
        sysRole.setName("一般使用者");
        sysRole.setNid("USER");
        sysRole.setStatus(1);
        sysRole.setCreatedUserId(1L);
        sysRoleRepository.save(sysRole);

        //用戶
        UserBase userBase = new UserBase();
        userBase.setAccount("admin");
        userBase.setPassword(passwordEncoder.encode("123456"));
        Optional<SysRole> adminRole = sysRoleRepository.findByNid("ADMIN");
        userBase.getRoles().add(adminRole.get());
        userBaseRepository.save(userBase);

    }

    private void initUserBase(){
        //1. UserBase（用戶資料）
        UserBase userBase = new UserBase();
        userBase.setAccount("admin");
        userBase.setId(1L);
        userBase.setPassword(passwordEncoder.encode("123456"));
        userBaseRepository.save(userBase);

        userBase = new UserBase();
        userBase.setAccount("user");
        userBase.setId(2L);
        userBase.setPassword(passwordEncoder.encode("123456"));
        userBaseRepository.save(userBase);
    }

    private void initSysRole(){
        SysRole sysRole = new SysRole();
        sysRole.setId(1L);
        sysRole.setName("系統管理員");
        sysRole.setNid("系統管理員");
        sysRole.setStatus(1);
        sysRole.setCreatedUserId(1L);
        sysRoleRepository.save(sysRole);

        sysRole = new SysRole();
        sysRole.setId(2L);
        sysRole.setName("一般使用者");
        sysRole.setNid("一般使用者");
        sysRole.setStatus(1);
        sysRole.setCreatedUserId(1L);
        sysRoleRepository.save(sysRole);
    }


    private void initSysMenu(){
        SysMenu sysMenu = new SysMenu();
        sysMenu.setId(1L);
        sysMenu.setStatus(1);
        sysMenu.setPlatformName("DEMO");
        sysMenu.setParentId(0L);//根目錄
        sysMenu.setCreatedUserId(1L);
        sysMenu.setSortNum(20);
        sysMenu.setMenuName("系統設定");
        sysMenu.setNid("系統設定");
        sysMenuRepository.save(sysMenu);

        sysMenu = new SysMenu();
        sysMenu.setId(2L);
        sysMenu.setStatus(1);
        sysMenu.setPlatformName("DEMO");
        sysMenu.setParentId(1L);
        sysMenu.setCreatedUserId(1L);
        sysMenu.setSortNum(1);
        sysMenu.setMenuName("使用者帳號維護管理");
        sysMenu.setNid("使用者帳號維護管理");
        sysMenuRepository.save(sysMenu);

        sysMenu = new SysMenu();
        sysMenu.setId(3L);
        sysMenu.setStatus(1);
        sysMenu.setPlatformName("DEMO");
        sysMenu.setParentId(0L);//根目錄
        sysMenu.setCreatedUserId(1L);
        sysMenu.setSortNum(2);
        sysMenu.setMenuName("基本資料");
        sysMenu.setNid("基本資料");
        sysMenuRepository.save(sysMenu);

        sysMenu = new SysMenu();
        sysMenu.setId(4L);
        sysMenu.setStatus(1);
        sysMenu.setPlatformName("DEMO");
        sysMenu.setParentId(3L);
        sysMenu.setCreatedUserId(1L);
        sysMenu.setSortNum(1);
        sysMenu.setMenuName("帳戶資料");
        sysMenu.setNid("帳戶資料");
        sysMenuRepository.save(sysMenu);
    }

    private void intiSysRoleMenu(){
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        List<SysMenu> sysMenuList = sysMenuRepository.findByStatus(1);
        for (SysMenu s : sysMenuList) {
            sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setSysMenuId(s.getId());//所有MenuId
            sysRoleMenu.setSysRoleId(1L);//管理者 => RoleId = 1
            sysRoleMenuRepository.save(sysRoleMenu);
        }

        sysRoleMenu = new SysRoleMenu();
        sysRoleMenu.setSysMenuId(3L);
        sysRoleMenu.setSysRoleId(2L);//一般使用者 => RoleId = 1
        sysRoleMenuRepository.save(sysRoleMenu);

        sysRoleMenu = new SysRoleMenu();
        sysRoleMenu.setSysMenuId(4L);
        sysRoleMenu.setSysRoleId(2L);//一般使用者 => RoleId = 1
        sysRoleMenuRepository.save(sysRoleMenu);


    }
}
