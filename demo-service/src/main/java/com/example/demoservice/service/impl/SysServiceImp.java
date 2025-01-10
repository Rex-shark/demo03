package com.example.demoservice.service.impl;

import com.example.demoservice.entity.*;
import com.example.demoservice.repository.*;
import com.example.demoservice.service.ISysService;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class SysServiceImp implements ISysService {
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
    public void iniSysRole() {
        if (!sysRoleRepository.findAll().isEmpty()) {
            return;
        }
        //先新增角色 系統管理員
        SysRole sysRole = new SysRole();
        sysRole.setId(1L);
        sysRole.setName("系統管理員");
        sysRole.setNid("ADMIN");
        sysRole.setStatus(1);
        sysRole.setCreatedUserId(1L);
        sysRoleRepository.save(sysRole);

        //新增角色 一般使用者
        sysRole = new SysRole();
        sysRole.setId(2L);
        sysRole.setName("一般使用者");
        sysRole.setNid("USER");
        sysRole.setStatus(1);
        sysRole.setCreatedUserId(1L);
        sysRoleRepository.save(sysRole);
    }

    @Override
    public void initUser() {
        if (userBaseRepository.findByAccount("admin").isPresent()) {
            return;
        }

        //用戶
        UserBase userBase = new UserBase();
        userBase.setAccount("admin");
        userBase.setPassword(passwordEncoder.encode("123456"));
        Optional<SysRole> adminRole = sysRoleRepository.findByNid("ADMIN");
        userBase.getRoles().add(adminRole.get());
        userBaseRepository.save(userBase);

        userBase = new UserBase();
        userBase.setAccount("user1");
        userBase.setPassword(passwordEncoder.encode("123456"));
        adminRole = sysRoleRepository.findByNid("USER");
        userBase.getRoles().add(adminRole.get());
        userBaseRepository.save(userBase);

    }

    @Override
    public void initSysMenu() {

        if (!sysMenuRepository.findAll().isEmpty()) {
            return;
        }

        //預設訂單結構
        //  系統選單
        //  ├─ 帳號修改
        //  └─ 群組維護
        //  我的訂單
        //  ├─ 訂單查詢
        //  └─ 訂單列印
        //  商家管理
        //  ├─ 商品管理


        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuName("系統選單");
        sysMenu.setNid("系統選單");
        sysMenu.setRemark("AUTO");
        sysMenu.setPlatformName("DEMO");
        sysMenu.setParentId(0L);//父
        sysMenuRepository.save(sysMenu);
        long pid = sysMenuRepository.saveAndFlush(sysMenu).getId();

        sysMenu = new SysMenu();
        sysMenu.setMenuName("帳號修改");
        sysMenu.setNid("帳號修改");
        sysMenu.setRemark("AUTO");
        sysMenu.setParentId(pid);//父
        sysMenu.setPlatformName("DEMO");
        sysMenuRepository.save(sysMenu);

        sysMenu = new SysMenu();
        sysMenu.setMenuName("群組維護");
        sysMenu.setNid("群組維護");
        sysMenu.setRemark("AUTO");
        sysMenu.setParentId(pid);//父
        sysMenu.setPlatformName("DEMO");
        sysMenuRepository.save(sysMenu);


        sysMenu = new SysMenu();
        sysMenu.setMenuName("我的訂單");
        sysMenu.setNid("我的訂單");
        sysMenu.setRemark("AUTO");
        sysMenu.setPlatformName("DEMO");
        sysMenu.setParentId(0L);//父
        sysMenuRepository.save(sysMenu);

        pid = sysMenuRepository.saveAndFlush(sysMenu).getId();

        sysMenu = new SysMenu();
        sysMenu.setMenuName("訂單查詢");
        sysMenu.setNid("訂單查詢");
        sysMenu.setRemark("AUTO");
        sysMenu.setParentId(pid);
        sysMenu.setPlatformName("DEMO");
        sysMenuRepository.save(sysMenu);

        sysMenu = new SysMenu();
        sysMenu.setMenuName("訂單列印");
        sysMenu.setNid("訂單列印");
        sysMenu.setRemark("AUTO");
        sysMenu.setParentId(pid);
        sysMenu.setPlatformName("DEMO");
        sysMenuRepository.save(sysMenu);

        /*
          商家管理
         */
        sysMenu = new SysMenu();
        sysMenu.setMenuName("商家管理");
        sysMenu.setNid("商家管理");
        sysMenu.setRemark("AUTO");
        sysMenu.setPlatformName("DEMO");
        sysMenu.setParentId(0L);//父
        sysMenuRepository.save(sysMenu);

        pid = sysMenuRepository.saveAndFlush(sysMenu).getId();

        sysMenu = new SysMenu();
        sysMenu.setMenuName("商品維護");
        sysMenu.setNid("商品維護");
        sysMenu.setRemark("AUTO");
        sysMenu.setParentId(pid);
        sysMenu.setPlatformName("DEMO");
        sysMenuRepository.save(sysMenu);


        //菜單設定完畢 開始關聯角色role

    }

    @Override
    public void initSysRoleMenu() {

        if(sysRoleRepository.findAll().isEmpty()){
            return;
        }
        if(!sysRoleMenuRepository.findAll().isEmpty()){
            return;
        }

        //ADMIN 看到所有菜單
        Optional<SysRole> sysRoleOptional = sysRoleRepository.findByNid("ADMIN");
        if (sysRoleOptional.isPresent()) {
            List<SysMenu> sysMenus = sysMenuRepository.findAll();
            for (SysMenu sysMenu : sysMenus) {
                // 創建 SysRoleMenu 關聯
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setSysRole(sysRoleOptional.get());  // 設定角色
                sysRoleMenu.setSysMenu(sysMenu);  // 設定菜單
                sysRoleMenu.setRemark("AUTO");  // 可選的備註
                // 儲存關聯
                sysRoleMenuRepository.save(sysRoleMenu);
            }
        }

        // //要給USER 賦予菜單 nid = 我的訂單
        sysRoleOptional = sysRoleRepository.findByNid("USER");
        if(sysRoleOptional.isPresent()){
            SysRole sysRole = sysRoleOptional.get();
            //找出nid = 我的訂單 的 子菜單與父菜單
            List<SysMenu> sysMenuList =  sysMenuRepository.findMenusByParentIdForNid("我的訂單");
            for (SysMenu sysMenu :sysMenuList) {
                // 創建 SysRoleMenu 關聯
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setSysRole(sysRole);  // 設定角色
                sysRoleMenu.setSysMenu(sysMenu);  // 設定菜單
                sysRoleMenu.setRemark("AUTO");  // 可選的備註
                // 儲存關聯
                sysRoleMenuRepository.save(sysRoleMenu);
            }
        }

    }
}
