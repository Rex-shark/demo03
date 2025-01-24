package com.example.demoservice.service.service;

import com.example.demoservice.constant.ApiMessageEnum;
import com.example.demoservice.entity.SysMenu;
import com.example.demoservice.entity.SysRole;
import com.example.demoservice.entity.SysRoleMenu;
import com.example.demoservice.exception.CRUDException;
import com.example.demoservice.repository.ISysMenuRepository;
import com.example.demoservice.repository.ISysRoleMenuRepository;
import com.example.demoservice.repository.ISysRoleRepository;
import com.example.demoservice.request.api.SysMenuRequest;
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
public class SysMenuService {
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

    public Page<SysMenu> getAllBySearch(SysMenuRequest sysMenuRequest, Pageable pageable) {
        return sysMenuRepository.findAllBySearch(sysMenuRequest,pageable);
    }

    public SysMenu saveSysMenu(SysMenuRequest sysMenuRequest) {

        String nid = sysMenuRequest.getNid();
        Optional<SysMenu> sysMenuOptional = sysMenuRepository.findByNid(nid);
        if(sysMenuOptional.isPresent()){
            throw new CRUDException("菜單已存在，NID: " + nid, ApiMessageEnum.ADD_FAIL);
        }

        SysMenu sysMenu = new SysMenu(sysMenuRequest);
        sysMenu.setCreatedUserId(authenticationHelper.getAuthenticatedUserId());

        sysMenuRepository.saveAndFlush(sysMenu);

        //解決快取問題
        entityManager.refresh(sysMenu);
        return sysMenu;
    }


    public SysMenu updateSysMenu(SysMenuRequest sysMenuRequest) {
        Optional<SysMenu> sysMenuOptional = sysMenuRepository.findByNid(sysMenuRequest.getNid());
        if(sysMenuOptional.isEmpty()) {
            throw new CRUDException("菜單不存在，NID: " + sysMenuRequest.getNid(), ApiMessageEnum.UPD_FAIL);
        }
        SysMenu sysMenu = sysMenuOptional.get();
        sysMenu.setMenuName(sysMenuRequest.getMenuName());
        sysMenu.setRemark(sysMenuRequest.getRemark());
        sysMenu.setHref(sysMenuRequest.getHref());
        sysMenu.setIconCls(sysMenuRequest.getIconCls());
        sysMenu.setSortNum(sysMenuRequest.getSortNum());
        sysMenu.setUpdatedAt(new Date());
        sysMenu.setUpdateUserId(authenticationHelper.getAuthenticatedUserId());

        sysMenuRepository.saveAndFlush(sysMenu);
        entityManager.refresh(sysMenu);
        return sysMenu ;
    }

    public void deleteMenuByNid(SysMenuRequest sysMenuRequest) {
        String nid = sysMenuRequest.getNid();

        if (sysMenuRepository.existsByNid(nid)) {
            sysMenuRepository.deleteByNid(nid);
        } else {
            throw new CRUDException("菜單不存在，NID: " + nid, ApiMessageEnum.DEL_FAIL);
        }
    }

}
