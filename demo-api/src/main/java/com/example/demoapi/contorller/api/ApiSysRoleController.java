package com.example.demoapi.contorller.api;

import com.example.demoservice.constant.ApiMessageEnum;
import com.example.demoapi.response.ApiDataResponse;
import com.example.demoservice.entity.SysRole;
import com.example.demoservice.request.api.SysRoleRequest;
import com.example.demoservice.service.service.SysRoleService;
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
    SysRoleService sysRoleService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<?>> getAllBySearch(@RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "size", defaultValue = "10") int size,
                                                            @ModelAttribute SysRoleRequest sysRoleRequest) {
        System.out.println("查詢角色");
        Pageable pageable = PageRequest.of(page, size);
        Page<SysRole> sysRole = sysRoleService.getAllBySearch(sysRoleRequest,pageable);

        return new ResponseEntity<>(new ApiDataResponse<>(new PagedModel<>(sysRole),ApiMessageEnum.QUY_SUCCESS), HttpStatus.OK);

    }

    @GetMapping("/{nid}")
    public ResponseEntity<ApiDataResponse<?>> getSysRole(@PathVariable String nid) {
        System.out.println("-查詢角色 nid" + nid);

        SysRoleRequest sysRoleRequest = new SysRoleRequest();
        sysRoleRequest.setNid(nid);

        Pageable pageable = PageRequest.of(0, 10);
        Page<SysRole> sysRole = sysRoleService.getAllBySearch(sysRoleRequest,pageable);

        if (sysRole.hasContent()) {
            SysRole firstSysRole = sysRole.getContent().get(0);
            return new ResponseEntity<>(new ApiDataResponse<>(firstSysRole,ApiMessageEnum.QUY_SUCCESS), HttpStatus.OK);
        }{
            return new ResponseEntity<>(new ApiDataResponse<>(ApiMessageEnum.QUY_SUCCESS), HttpStatus.OK);
        }

    }


    @PostMapping
    public ResponseEntity<?> saveSysRole(@Valid @RequestBody SysRoleRequest sysRoleRequest) {
        System.out.println("新增角色 : " + sysRoleRequest.getNid());
        SysRole sysRole = sysRoleService.saveSysRole(sysRoleRequest);

        return new ResponseEntity<>(new ApiDataResponse<>(sysRole,ApiMessageEnum.ADD_SUCCESS), HttpStatus.OK);

    }

    /**
     * Updates a system role based on the provided NID.
     *
     * @param nid The unique identifier of the role to be updated
     * @param sysRoleRequest The request body containing the updated role information
     * @return ResponseEntity containing the updated SysRole object wrapped in ApiDataResponse with a success message and HTTP status OK
     */
    @PutMapping("/{nid}")
    public ResponseEntity<ApiDataResponse<?>> updateSysRoleByNid(@PathVariable String nid,
                                           @Valid @RequestBody SysRoleRequest sysRoleRequest){
        System.out.println("更新角色 nid" + nid);
        SysRole sysRole = sysRoleService.updateSysRole(sysRoleRequest);

        return new ResponseEntity<>(new ApiDataResponse<>(sysRole,ApiMessageEnum.UPD_SUCCESS), HttpStatus.OK);
    }


    @DeleteMapping("/{nid}")
    public ResponseEntity<ApiDataResponse<?>> deleteSysRoleByNid(@PathVariable String nid) {
        System.out.println("刪除角色 nid" + nid);
        SysRoleRequest sysRoleRequest = new SysRoleRequest();
        sysRoleRequest.setNid(nid);
        sysRoleService.deleteRoleByNid(sysRoleRequest);
        return new ResponseEntity<>(new ApiDataResponse<>(ApiMessageEnum.DEL_SUCCESS), HttpStatus.OK);
    }
}
