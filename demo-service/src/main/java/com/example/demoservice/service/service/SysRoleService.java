package com.example.demoservice.service.service;

import com.example.demoservice.entity.SysRole;
import com.example.demoservice.repository.ISysRoleRepository;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SysRoleService {
    @Resource
    private ISysRoleRepository sysRoleRepository;

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
