package com.example.demoapi.contorller.api;

import com.example.demoservice.entity.ProductDemo;
import com.example.demoservice.entity.SysRole;
import com.example.demoservice.request.user.SysRoleRequest;
import com.example.demoservice.service.service.SysRoleService;
import com.example.demoservice.service.service.UserBaseService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/sys-role")
public class ApiSysRoleController {

    @Resource
    UserBaseService userBaseService;

    @Resource
    SysRoleService sysRoleService;

    @GetMapping
    public ResponseEntity<PagedModel<SysRole>> getAllOrders(@RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "size", defaultValue = "10") int size,
                                                            @Valid @RequestBody SysRoleRequest sysRoleRequest) {
        System.out.println("查詢角色");
        Pageable pageable = PageRequest.of(page, size);
        Page<SysRole> sysRole = sysRoleService.getAllBySearch(sysRoleRequest,pageable);
        return new ResponseEntity<>( new PagedModel<>(sysRole), HttpStatus.OK);

    }

    @GetMapping("/{nid}")
    public ResponseEntity<SysRole> getSysRole(@PathVariable String nid) {
        System.out.println("查詢角色 nid" + nid);

        SysRoleRequest sysRoleRequest = new SysRoleRequest();
        sysRoleRequest.setNid(nid);

        Pageable pageable = PageRequest.of(0, 10);
        Page<SysRole> sysRole = sysRoleService.getAllBySearch(sysRoleRequest,pageable);

        if (sysRole.hasContent()) {
            SysRole firstSysRole = sysRole.getContent().get(0);
            return new ResponseEntity<>(firstSysRole, HttpStatus.OK);
        }{
            //沒資料不該給NOT_FOUND 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping
    public ResponseEntity<SysRole> saveSysRole(@Valid @RequestBody SysRoleRequest sysRoleRequest) {
        System.out.println("新增角色 : " + sysRoleRequest.getNid());
        SysRole sysRole = sysRoleService.saveSysRole(sysRoleRequest);

        return new ResponseEntity<>(sysRole, HttpStatus.CREATED);
    }

    @PutMapping("/{nid}")
    public ResponseEntity<?> updateSysRole(@PathVariable String nid,
                                           @Valid @RequestBody SysRoleRequest sysRoleRequest){
        System.out.println("更新角色 nid" + nid);

        if(sysRoleService.updateSysRole(sysRoleRequest)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        //不該NOT_FOUND
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
