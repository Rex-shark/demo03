package com.example.demoservice.service.service;

import com.example.demoservice.constant.ApiMessageEnum;
import com.example.demoservice.entity.SysMenu;
import com.example.demoservice.entity.SysRole;
import com.example.demoservice.entity.SysRoleMenu;
import com.example.demoservice.exception.CRUDException;
import com.example.demoservice.repository.ISysMenuRepository;
import com.example.demoservice.repository.ISysRoleMenuRepository;
import com.example.demoservice.repository.ISysRoleRepository;
import com.example.demoservice.request.api.SysRoleRequest;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
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

    @Resource
    private EntityManager entityManager;


    public Optional<SysRole> getSysRoleByNid(String nid) {
        return sysRoleRepository.findByNid(nid);
    }

    public Page<SysRole> getAllBySearch(SysRoleRequest sysRoleRequest, Pageable pageable) {
        return sysRoleRepository.findAllBySearch(sysRoleRequest,pageable);
    }

    public SysRole saveSysRole(SysRoleRequest sysRoleRequest) {

        String nid = sysRoleRequest.getNid();
        sysRoleRepository.findByNid(nid);
        if(sysRoleRepository.findByNid(nid).isPresent()){
            System.out.println("sysRoleRepository.findByNid(nid) = " + sysRoleRepository.findByNid(nid));
            throw new CRUDException("角色已存在，NID: " + nid, ApiMessageEnum.ADD_FAIL);
        }

        SysRole sysRole = new SysRole();
        sysRole.setNid(sysRoleRequest.getNid());
        sysRole.setName(sysRoleRequest.getSysRoleName());
        sysRole.setRemark(sysRoleRequest.getRemark());
        sysRole.setCreatedUserId(authenticationHelper.getAuthenticatedUserId());
        //Long id = sysRoleRepository.saveAndFlush(sysRole).getId();
        sysRoleRepository.saveAndFlush(sysRole);

        List<String> sysMenuNid = sysRoleRequest.getSysMenuNid();
        for (String menuNid : sysMenuNid) {
            Optional<SysMenu> sysMenu = sysMenuRepository.findByNid(menuNid);
            if(sysMenu.isEmpty()){
                continue;
            }

            //System.out.println("儲存關聯 menuNid = " + menuNid);
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setSysRole(sysRole);  // 設定角色
            sysRoleMenu.setSysMenu(sysMenu.get());  // 設定菜單
            sysRoleMenu.setRemark("AUTO");  // 可選的備註
            // 儲存關聯
            //sysRoleMenuRepository.save(sysRoleMenu);
            sysRoleMenuRepository.saveAndFlush(sysRoleMenu);
        }
        //解決快取問題
        entityManager.refresh(sysRole);
        return sysRole;
    }

    public SysRole updateSysRole(SysRoleRequest sysRoleRequest) {
        Optional<SysRole> sysRoleOptional = sysRoleRepository.findByNid(sysRoleRequest.getNid());
        if(sysRoleOptional.isEmpty()) {
            throw new CRUDException("角色不存在，NID: " + sysRoleRequest.getNid(), ApiMessageEnum.UPD_FAIL);
        }
        SysRole sysRole = sysRoleOptional.get();
        sysRole.setName(sysRoleRequest.getSysRoleName());
        sysRole.setRemark(sysRoleRequest.getRemark());
        sysRole.setUpdatedAt(new Date());
        sysRole.setUpdateUserId(authenticationHelper.getAuthenticatedUserId());
        sysRoleRepository.saveAndFlush(sysRole);
        System.out.println("sysRole.getId() = " + sysRole.getId());

        //先移sysMenu權限
        sysRoleMenuRepository.deleteBySysRole(sysRole);
        //TODO 不知道為什麼，Hibernate都會延遲處理，都要手動加上flush()，須研究解決
        entityManager.flush();

        //修改sysMenu權限
        List<String> sysMenuNid = sysRoleRequest.getSysMenuNid();
        for (String menuNid : sysMenuNid) {
            Optional<SysMenu> sysMenu = sysMenuRepository.findByNid(menuNid);
            if(sysMenu.isEmpty()){
                continue;
            }

            //System.out.println("儲存關聯 menuNid = " + menuNid);
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setSysRole(sysRole);  // 設定角色
            sysRoleMenu.setSysMenu(sysMenu.get());  // 設定菜單
            sysRoleMenu.setRemark("");  // 可選的備註
            // 儲存關聯
            sysRoleMenuRepository.save(sysRoleMenu);
        }
        //解決快取問題
        entityManager.refresh(sysRole);

        return sysRole ;
    }

    public void deleteRoleByNid(SysRoleRequest sysRoleRequest) {
        String nid = sysRoleRequest.getNid();

        if (sysRoleRepository.existsByNid(nid)) {
            sysRoleRepository.deleteByNid(nid);
        } else {
            throw new CRUDException("角色不存在，NID: " + nid, ApiMessageEnum.DEL_FAIL);
        }
    }


}
