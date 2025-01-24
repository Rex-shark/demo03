package com.example.demoservice.service.service;

import com.example.demoservice.constant.ApiMessageEnum;
import com.example.demoservice.entity.SysRole;
import com.example.demoservice.entity.UserBase;
import com.example.demoservice.exception.CRUDException;
import com.example.demoservice.repository.ISysRoleRepository;
import com.example.demoservice.repository.IUserBaseRepository;
import com.example.demoservice.request.api.UserBaseRequest;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserBaseService {
    @Resource
    private IUserBaseRepository userBaseRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private ISysRoleRepository sysRoleRepository;

    @Resource
    private AuthenticationHelper authenticationHelper;

    @Resource
    private EntityManager entityManager;

    public Page<UserBase> getAllBySearch(UserBaseRequest userBaseRequest, Pageable pageable) {
        return userBaseRepository.findAllBySearch(userBaseRequest,pageable);
    }

    public UserBase saveUserBase(UserBaseRequest userBaseRequest) {

        String account = userBaseRequest.getAccount();

        if(userBaseRepository.findByAccount(account).isPresent()){
            throw new CRUDException("帳號已存在，account: " + account, ApiMessageEnum.ADD_FAIL);
        }

        UserBase userBase = new UserBase();
        userBase.setAccount(account);
        userBase.setPassword(passwordEncoder.encode(userBaseRequest.getPassword()));
        userBase.setStatus(1);
        userBase.setRemark(userBaseRequest.getRemark());
        userBase.setCreatedUserId(authenticationHelper.getAuthenticatedUserId());

        List<String> sysRoleNidList = userBaseRequest.getSysRole();

        for (String nid : sysRoleNidList) {
            Optional<SysRole> SysRole = sysRoleRepository.findByNid(nid);
            SysRole.ifPresent(sysRole -> userBase.getRoles().add(sysRole));
        }

        userBaseRepository.saveAndFlush(userBase);
        entityManager.refresh(userBase);

        return userBase;
    }

    public void deleteUserBaseByUuid(UserBaseRequest userBaseRequest) {
        String uuid = userBaseRequest.getUuid();

        if (userBaseRepository.existsByUuid(uuid)) {
            userBaseRepository.deleteByUuid(uuid);
        } else {
            throw new CRUDException("使用者 uuid: " + uuid + " 不存在", ApiMessageEnum.DEL_FAIL);
        }
    }


    public UserBase updateUserBaseByUuid(String uuid , UserBaseRequest userBaseRequest) {

        Optional<UserBase> userBaseOptional = userBaseRepository.findByUuid(uuid);

        if(userBaseOptional.isEmpty()) {
            throw new CRUDException("使用者 uuid: " + uuid + " 不存在", ApiMessageEnum.UPD_FAIL);
        }

        UserBase userBase = userBaseOptional.get();
        userBase.setRemark(userBaseRequest.getRemark());
        userBase.setUpdateUserId(authenticationHelper.getAuthenticatedUserId());
        userBase.setRoles(new ArrayList<>());//清空原本sys_role

        List<String> sysRoleNidList = userBaseRequest.getSysRole();

        for (String nid : sysRoleNidList) {
            Optional<SysRole> SysRole = sysRoleRepository.findByNid(nid);
            SysRole.ifPresent(sysRole -> userBase.getRoles().add(sysRole));
        }

        userBaseRepository.saveAndFlush(userBase);
        entityManager.refresh(userBase);

        return userBase;

    }

    public void updateUserBasePasswordByUuid(String uuid , UserBaseRequest userBaseRequest) {

        Optional<UserBase> userBaseOptional = userBaseRepository.findByUuid(uuid);

        if(userBaseOptional.isEmpty()) {
            throw new CRUDException("使用者 uuid: " + uuid + " 不存在", ApiMessageEnum.UPD_FAIL);
        }

        UserBase userBase = userBaseOptional.get();
        userBase.setPassword(passwordEncoder.encode(userBaseRequest.getPassword()));
        userBase.setUpdateUserId(authenticationHelper.getAuthenticatedUserId());
        userBaseRepository.save(userBase);

        //改密碼要有log紀錄
        //userBaseRequest.getRemark(); 這個應該記錄log
    }
}
