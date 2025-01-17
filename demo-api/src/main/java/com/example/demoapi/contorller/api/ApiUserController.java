package com.example.demoapi.contorller.api;

import com.example.demoservice.request.user.UserBaseRequest;
import com.example.demoservice.entity.UserBase;
import com.example.demoservice.service.service.UserBaseService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/user")
public class ApiUserController {

    @Resource
    UserBaseService userBaseService;

    @GetMapping
    public ResponseEntity<PagedModel<UserBase>> getAllOrders(@RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "size", defaultValue = "10") int size) {
        System.out.println("查詢使用者");
        Page<UserBase> userBase = userBaseService.getUseUserBase(page, size);
        return new ResponseEntity<>( new PagedModel<>(userBase), HttpStatus.OK);

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserBase> getUserBase(@PathVariable String uuid) {
        System.out.println("查詢使用者 uuid"+uuid);
        Optional<UserBase> userBase = userBaseService.getUseUserBaseByUuid(uuid);

//        return new ResponseEntity<>(new WebResponse(
//                HttpStatus.OK.value(),
//                HttpStatus.OK.getReasonPhrase(),
//                userBase.get().toString();
//        ), HttpStatus.OK);


        return userBase.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateUserBase(@PathVariable String uuid,
                                         @Valid @RequestBody UserBaseRequest userBaseRequest){
        System.out.println("更新使用者 uuid = " + uuid);
        /**
         * 沒完成
         */
        userBaseService.updateUserBaseByUuid(uuid, userBaseRequest);

        return null;
    }
}
