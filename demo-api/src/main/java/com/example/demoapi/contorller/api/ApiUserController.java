package com.example.demoapi.contorller.api;

import com.example.demoapi.response.ApiDataResponse;
import com.example.demoservice.constant.ApiMessageEnum;
import com.example.demoservice.request.api.UserBaseRequest;
import com.example.demoservice.entity.UserBase;
import com.example.demoservice.service.service.UserBaseService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@RequestMapping("/api/user")
public class ApiUserController {

    @Resource
    UserBaseService userBaseService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<?>> getAllBySearch(@RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "size", defaultValue = "10") int size,
                                                             @ModelAttribute UserBaseRequest userBaseRequest) {
        System.out.println("查詢使用者");
        Pageable pageable = PageRequest.of(page, size);
        Page<UserBase> userBase = userBaseService.getAllBySearch(userBaseRequest,pageable);
        return new ResponseEntity<>(new ApiDataResponse<>(new PagedModel<>(userBase), ApiMessageEnum.QUY_SUCCESS), HttpStatus.OK);

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiDataResponse<?>> getUserBase(@PathVariable String uuid) {
        System.out.println("查詢使用者 uuid"+uuid);

        UserBaseRequest userBaseRequest = new UserBaseRequest();
        userBaseRequest.setUuid(uuid);

        Pageable pageable = PageRequest.of(0, 10);
        Page<UserBase> userBasePage = userBaseService.getAllBySearch(userBaseRequest,pageable);

        if (userBasePage.hasContent()) {
            UserBase userBase = userBasePage.getContent().get(0);
            return new ResponseEntity<>(new ApiDataResponse<>(userBase,ApiMessageEnum.QUY_SUCCESS), HttpStatus.OK);
        }{
            return new ResponseEntity<>(new ApiDataResponse<>(ApiMessageEnum.QUY_SUCCESS), HttpStatus.OK);
        }

    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<?>> saveSysRole(@Valid @RequestBody UserBaseRequest userBaseRequest) {
        System.out.println("新增使用者 : " + userBaseRequest.getAccount());


        UserBase userBase = userBaseService.saveUserBase(userBaseRequest);
        return new ResponseEntity<>(new ApiDataResponse<>(userBase,ApiMessageEnum.ADD_SUCCESS), HttpStatus.OK);

    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateUserBase(@PathVariable String uuid,
                                            @RequestBody UserBaseRequest userBaseRequest){
        System.out.println("更新使用者 uuid = " + uuid);
        UserBase userBase = userBaseService.updateUserBaseByUuid(uuid, userBaseRequest);

        return new ResponseEntity<>(new ApiDataResponse<>(userBase,ApiMessageEnum.UPD_SUCCESS), HttpStatus.OK);
    }

    @PutMapping("pw/{uuid}")
    public ResponseEntity<?> updateUserBasePassword(
            @PathVariable String uuid,
            @Valid @RequestBody UserBaseRequest userBaseRequest) {
        System.out.println("更新使用者密碼 uuid = " + uuid);
        userBaseService.updateUserBasePasswordByUuid(uuid, userBaseRequest);
        return new ResponseEntity<>(new ApiDataResponse<>(ApiMessageEnum.UPD_SUCCESS), HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiDataResponse<?>> deleteUserBaseByUuid(@PathVariable String uuid) {
        System.out.println("刪除角色 uuid" + uuid);

        UserBaseRequest userBaseRequest = new UserBaseRequest();
        userBaseRequest.setUuid(uuid);
        userBaseService.deleteUserBaseByUuid(userBaseRequest);

        return new ResponseEntity<>(new ApiDataResponse<>(ApiMessageEnum.DEL_SUCCESS), HttpStatus.OK);
    }


    /*
     當範例用
     */
    @Hidden
    @PutMapping("pw/v2/{uuid}")
    public ResponseEntity<?> updateUserBasePasswordV2(
            @PathVariable String uuid,
            @RequestParam @NotBlank(message = "Password is mandatory")
            @Size(min = 6, message = "Password must be at least 6 characters")
            String password,
            @RequestParam(required = false,defaultValue = "") String remark) {
        return new ResponseEntity<>(null, HttpStatus.OK);
        //return ResponseEntity.noContent().build();
        //return ResponseEntity.ok().build();
    }
}
