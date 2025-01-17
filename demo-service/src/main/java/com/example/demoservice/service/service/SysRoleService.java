package com.example.demoservice.service.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demoservice.entity.SysMenu;
import com.example.demoservice.entity.SysRole;
import com.example.demoservice.entity.SysRoleMenu;
import com.example.demoservice.repository.ISysMenuRepository;
import com.example.demoservice.repository.ISysRoleMenuRepository;
import com.example.demoservice.repository.ISysRoleRepository;
import com.example.demoservice.request.user.SysRoleRequest;
import com.example.demoservice.request.user.UserBaseRequest;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class SysRoleService {
    @Resource
    private ISysRoleRepository sysRoleRepository;

    @Resource
    private ISysMenuRepository sysMenuRepository;

    @Resource
    private ISysRoleMenuRepository sysRoleMenuRepository;

    @Resource
    private AuthenticationHelper authenticationHelper;




    public Page<SysRole> getAllBySearch(SysRoleRequest sysRoleRequest, Pageable pageable) {
        System.out.println("authenticatedUserId = " +authenticationHelper.getAuthenticatedUserId());
        return sysRoleRepository.findAllBySearch(sysRoleRequest,pageable);
    }

    public SysRole saveSysRole(SysRoleRequest sysRoleRequest) {

        String nid = sysRoleRequest.getNid();
        sysRoleRepository.findByNid(nid);
        if(sysRoleRepository.findByNid(nid).isPresent()){
            System.out.println("已存在 " + nid);
            return null;
        }


        SysRole sysRole = new SysRole();
        sysRole.setNid(sysRoleRequest.getNid());
        sysRole.setName(sysRoleRequest.getSysRoleName());
        sysRole.setRemark(sysRoleRequest.getRemark());
        Long id = sysRoleRepository.saveAndFlush(sysRole).getId();
        System.out.println("id = " + id);

        List<String> sysMenuNid = sysRoleRequest.getSysMenuNid();
        for (String menuNid : sysMenuNid) {
            Optional<SysMenu> sysMenu = sysMenuRepository.findByNid(menuNid);
            if(sysMenu.isEmpty()){
                continue;
            }

            System.out.println("menuNid = " + menuNid);
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setSysRole(sysRole);  // 設定角色
            sysRoleMenu.setSysMenu(sysMenu.get());  // 設定菜單
            sysRoleMenu.setRemark("AUTO-");  // 可選的備註
            // 儲存關聯
            sysRoleMenuRepository.save(sysRoleMenu);
        }

        return sysRole;
    }

    public boolean updateSysRole(SysRoleRequest sysRoleRequest) {
        Optional<SysRole> sysRoleOptional = sysRoleRepository.findByNid(sysRoleRequest.getNid());
        if(sysRoleOptional.isEmpty()) {
            return false ;
        }
        SysRole sysRole = sysRoleOptional.get();
        sysRole.setName(sysRoleRequest.getSysRoleName());
        sysRole.setRemark(sysRoleRequest.getRemark());
        sysRole.setUpdatedAt(new Date());
        sysRole.setUpdateUserId(1L);
        sysRoleRepository.save(sysRole);
        return true ;
    }


    /**
     * 獲取所有角色
     * @return 角色列表
     */
    public List<SysRole> getAllRoles() {
        return sysRoleRepository.findAll();
    }

    /**
     * 根據 ID 獲取角色
     * @param id 角色 ID
     * @return 角色
     */
    public Optional<SysRole> getRoleById(Long id) {
        return sysRoleRepository.findById(id);
    }

    /**
     * 根據 nid 獲取角色
     * @param nid 角色唯一標識
     * @return 角色
     */
    public Optional<SysRole> getRoleByNid(String nid) {
        return sysRoleRepository.findByNid(nid);
    }

    /**
     * 新增或更新角色
     * @param sysRole 角色對象
     * @return 保存後的角色
     */
    public SysRole saveRole(SysRole sysRole) {
        return sysRoleRepository.save(sysRole);
    }

    /**
     * 刪除角色
     * @param id 角色 ID
     */
    public void deleteRole(Long id) {
        if (sysRoleRepository.existsById(id)) {
            sysRoleRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("角色 ID: " + id + " 不存在");
        }
    }
}
