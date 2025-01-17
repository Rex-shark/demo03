package com.example.demoservice.service.service;

import com.example.demoservice.entity.SysRole;
import com.example.demoservice.entity.UserBase;
import com.example.demoservice.repository.ISysRoleRepository;
import com.example.demoservice.repository.IUserBaseRepository;
import com.example.demoservice.request.user.UserBaseRequest;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserBaseService {
    @Resource
    private IUserBaseRepository userBaseRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private ISysRoleRepository sysRoleRepository;

    public Page<UserBase> getUseUserBase(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userBaseRepository.findByStatus(1,pageable);
    }

    public Optional<UserBase> getUseUserBaseByUuid(String uuid) {
        return userBaseRepository.findByUuid(uuid);
    }

    public void updateUserBaseByUuid(String uuid , UserBaseRequest userBaseRequest) {
        Optional<UserBase> userBaseOptional = userBaseRepository.findByUuid(uuid);
        if(userBaseOptional.isEmpty()) {
            return;
        }
        for (int i = 0; i < userBaseRequest.getSysRole().size(); i++) {
            System.out.println("userBaseRequest.getSysRole().get(i) = " + userBaseRequest.getSysRole().get(i));
        }
       // System.out.println("userBaseRequest.getRoles() = " + userBaseRequest.getRoles());
        UserBase originalUserBase = userBaseOptional.get();
        originalUserBase.setPassword(passwordEncoder.encode(userBaseRequest.getPassword()));
        //originalUserBase.setRoles(userBaseRequest.getRoles());

        Optional<SysRole> adminRole = sysRoleRepository.findByNid("ADMIN");
        System.out.println("\"ss\" = " + "ss");

        //userBaseRepository.save(originalUserBase);
    }
}
