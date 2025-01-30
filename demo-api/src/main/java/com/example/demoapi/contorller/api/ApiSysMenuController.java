package com.example.demoapi.contorller.api;

import com.example.demoapi.response.ApiDataResponse;
import com.example.demoservice.constant.ApiMessageEnum;
import com.example.demoservice.entity.SysMenu;

import com.example.demoservice.request.api.SysMenuRequest;

import com.example.demoservice.service.service.SysMenuService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/api/sys-menu")
@SecurityRequirement(name = "Authorization")
public class ApiSysMenuController {


    @Resource
    SysMenuService SysMenuService;

    @Resource
    SysMenuService sysMenuService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<?>> getAllBySearch(@RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "size", defaultValue = "10") int size,
                                                             @ModelAttribute SysMenuRequest sysMenuRequest) {
        System.out.println("查詢菜單");


        Pageable pageable = PageRequest.of(page, size);
        Page<SysMenu> sysMenu = sysMenuService.getAllBySearch(sysMenuRequest,pageable);

        return new ResponseEntity<>(new ApiDataResponse<>(new PagedModel<>(sysMenu), ApiMessageEnum.QUY_SUCCESS), HttpStatus.OK);

    }
    @GetMapping("/{nid}")
    public ResponseEntity<ApiDataResponse<?>> getSysMenu(@PathVariable String nid) {
        System.out.println("-查詢菜單 nid" + nid);

        SysMenuRequest SysMenuRequest = new SysMenuRequest();
        SysMenuRequest.setNid(nid);

        Pageable pageable = PageRequest.of(0, 10);
        Page<SysMenu> SysMenu = SysMenuService.getAllBySearch(SysMenuRequest,pageable);

        if (SysMenu.hasContent()) {
            SysMenu firstSysMenu = SysMenu.getContent().get(0);
            return new ResponseEntity<>(new ApiDataResponse<>(firstSysMenu,ApiMessageEnum.QUY_SUCCESS), HttpStatus.OK);
        }{
            return new ResponseEntity<>(new ApiDataResponse<>(ApiMessageEnum.QUY_SUCCESS), HttpStatus.OK);
        }

    }

    @PostMapping
    public ResponseEntity<?> saveSysMenu(@Valid @RequestBody SysMenuRequest SysMenuRequest) {
        System.out.println("新增菜單 : " + SysMenuRequest.getNid());
        SysMenu SysMenu = SysMenuService.saveSysMenu(SysMenuRequest);

        return new ResponseEntity<>(new ApiDataResponse<>(SysMenu,ApiMessageEnum.ADD_SUCCESS), HttpStatus.OK);

    }

    @PutMapping("/{nid}")
    public ResponseEntity<ApiDataResponse<?>> updateSysMenuByNid(@PathVariable String nid,
                                                                 @Valid @RequestBody SysMenuRequest sysMenuRequest){
        System.out.println("更新菜單 nid" + nid);
        SysMenu sysMenu = sysMenuService.updateSysMenu(sysMenuRequest);

        return new ResponseEntity<>(new ApiDataResponse<>(sysMenu,ApiMessageEnum.UPD_SUCCESS), HttpStatus.OK);
    }

    @DeleteMapping("/{nid}")
    public ResponseEntity<ApiDataResponse<?>> deleteSysMenuByNid(@PathVariable String nid) {
        System.out.println("刪除菜單 nid" + nid);
        SysMenuRequest sysMenuRequest = new SysMenuRequest();
        sysMenuRequest.setNid(nid);
        sysMenuService.deleteMenuByNid(sysMenuRequest);
        return new ResponseEntity<>(new ApiDataResponse<>(ApiMessageEnum.DEL_SUCCESS), HttpStatus.OK);
    }
}
