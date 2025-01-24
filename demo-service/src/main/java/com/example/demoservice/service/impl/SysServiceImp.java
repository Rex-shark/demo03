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
        sysRole.setName("系統管理員");
        sysRole.setNid("ADMIN");
        sysRole.setRemark("AUTO");
        sysRole.setCreatedUserId(1L);
        sysRoleRepository.save(sysRole);

        //管理者
        sysRole = new SysRole();
        sysRole.setName("商家");
        sysRole.setNid("MERCHANTS");
        sysRole.setRemark("AUTO");
        sysRole.setCreatedUserId(1L);
        sysRoleRepository.save(sysRole);

        //新增角色 一般使用者
        sysRole = new SysRole();
        sysRole.setName("一般使用者");
        sysRole.setNid("USER");
        sysRole.setRemark("AUTO");
        sysRole.setCreatedUserId(1L);
        sysRoleRepository.save(sysRole);

        //新增角色 一般使用者
        sysRole = new SysRole();
        sysRole.setName("一般使用者1");
        sysRole.setNid("USER1");
        sysRole.setRemark("AUTO");
        sysRole.setCreatedUserId(1L);
        sysRoleRepository.save(sysRole);

    }

    @Override
    public void initUser() {
        //system 預設關閉(Status(2))
        if (userBaseRepository.findByAccountAndStatus("system", 2).isPresent()) {
            return;
        }
        UserBase userBase ;
        Optional<SysRole> sysRoleOptional;


        //系統預設，資料表關聯用，無須權限
        userBase = new UserBase();
        userBase.setId(1L);//id = 1(關聯用)
        userBase.setAccount("system");
        userBase.setPassword(passwordEncoder.encode("123456"));
        userBase.setStatus(2);//預設關閉(Status(2))
        userBase.setRemark("AUTO");
        userBaseRepository.save(userBase);

        //系統管理者
        userBase = new UserBase();
        userBase.setAccount("admin");
        userBase.setPassword(passwordEncoder.encode("123456"));
        userBase.setRemark("AUTO");
        sysRoleOptional = sysRoleRepository.findByNid("ADMIN");
        userBase.getRoles().add(sysRoleOptional.get());
        userBaseRepository.save(userBase);

//        //管理者
//        userBase = new UserBase();
//        userBase.setAccount("manager");
//        userBase.setPassword(passwordEncoder.encode("123456"));
//        userBase.setRemark("AUTO");
//        sysRoleOptional = sysRoleRepository.findByNid("MANAGER");
//        userBase.getRoles().add(sysRoleOptional.get());
//        userBaseRepository.save(userBase);

        //賣家 Merchants
        userBase = new UserBase();
        userBase.setAccount("merchants");
        userBase.setPassword(passwordEncoder.encode("123456"));
        userBase.setRemark("AUTO");
        sysRoleOptional = sysRoleRepository.findByNid("MERCHANTS");
        userBase.getRoles().add(sysRoleOptional.get());
        userBaseRepository.save(userBase);

        //一般使用者 user
        userBase = new UserBase();
        userBase.setAccount("user");
        userBase.setPassword(passwordEncoder.encode("123456"));
        userBase.setRemark("AUTO");
        sysRoleOptional = sysRoleRepository.findByNid("USER");
        userBase.getRoles().add(sysRoleOptional.get());
        userBaseRepository.save(userBase);

        //一般使用者
        userBase = new UserBase();
        userBase.setAccount("user1");
        userBase.setPassword(passwordEncoder.encode("123456"));
        userBase.setRemark("AUTO");
        sysRoleOptional = sysRoleRepository.findByNid("USER");
        userBase.getRoles().add(sysRoleOptional.get());
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
        sysMenu.setHref("");
        sysMenu.setIconCls("");
        sysMenu.setPlatformName("DEMO");
        sysMenu.setParentId(0L);//父
        long pid = sysMenuRepository.saveAndFlush(sysMenu).getId();

        sysMenu = new SysMenu();
        sysMenu.setMenuName("帳號修改");
        sysMenu.setNid("帳號修改");
        sysMenu.setRemark("AUTO");
        sysMenu.setParentId(pid);//父
        sysMenu.setHref("");
        sysMenu.setIconCls("");
        sysMenu.setPlatformName("DEMO");
        sysMenuRepository.save(sysMenu);

        sysMenu = new SysMenu();
        sysMenu.setMenuName("群組維護");
        sysMenu.setNid("群組維護");
        sysMenu.setRemark("AUTO");
        sysMenu.setParentId(pid);//父
        sysMenu.setHref("");
        sysMenu.setIconCls("");
        sysMenu.setPlatformName("DEMO");
        sysMenuRepository.save(sysMenu);


        sysMenu = new SysMenu();
        sysMenu.setMenuName("我的訂單");
        sysMenu.setNid("我的訂單");
        sysMenu.setRemark("AUTO");
        sysMenu.setHref("");
        sysMenu.setIconCls("");
        sysMenu.setPlatformName("DEMO");
        sysMenu.setParentId(0L);//父
        pid = sysMenuRepository.saveAndFlush(sysMenu).getId();

        sysMenu = new SysMenu();
        sysMenu.setMenuName("訂單查詢");
        sysMenu.setNid("訂單查詢");
        sysMenu.setRemark("AUTO");
        sysMenu.setParentId(pid);
        sysMenu.setHref("");
        sysMenu.setIconCls("");
        sysMenu.setPlatformName("DEMO");
        sysMenuRepository.save(sysMenu);

        sysMenu = new SysMenu();
        sysMenu.setMenuName("訂單列印");
        sysMenu.setNid("訂單列印");
        sysMenu.setRemark("AUTO");
        sysMenu.setParentId(pid);
        sysMenu.setHref("");
        sysMenu.setIconCls("");
        sysMenu.setPlatformName("DEMO");
        sysMenuRepository.save(sysMenu);

        /*
          商家管理
         */
        sysMenu = new SysMenu();
        sysMenu.setMenuName("商家管理");
        sysMenu.setNid("商家管理");
        sysMenu.setRemark("AUTO");
        sysMenu.setHref("");
        sysMenu.setIconCls("");
        sysMenu.setPlatformName("DEMO");
        sysMenu.setParentId(0L);//父
        pid = sysMenuRepository.saveAndFlush(sysMenu).getId();

        sysMenu = new SysMenu();
        sysMenu.setMenuName("商品維護");
        sysMenu.setNid("商品維護");
        sysMenu.setRemark("AUTO");
        sysMenu.setParentId(pid);
        sysMenu.setHref("");
        sysMenu.setIconCls("");
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

        //要給MERCHANTS 賦予菜單 nid = 商家管理
        sysRoleOptional = sysRoleRepository.findByNid("MERCHANTS");
        if(sysRoleOptional.isPresent()){
            SysRole sysRole = sysRoleOptional.get();
            //找出nid = 我的訂單 的 子菜單與父菜單
            List<SysMenu> sysMenuList =  sysMenuRepository.findMenusByParentIdForNid("商家管理");
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

        // 要給USER 賦予菜單 nid = 我的訂單
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
