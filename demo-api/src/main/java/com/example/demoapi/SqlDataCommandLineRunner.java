package com.example.demoapi;

import com.example.demoservice.repository.IUserBaseRepository;
import com.example.demoservice.service.SysService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class SqlDataCommandLineRunner implements CommandLineRunner {



    @Resource
    private SysService sysService;



    /**
     * 目前測試 spring.sql.init.mode=always + data.sql
     * 可以正常啟動執行sql,但是無法做到自動創表後再執行data.sql的sql
     * <P>
     * 所以目前先只用CommandLineRunner 做自動寫入預設資料
     *
     * @param args jar 啟動參數
     */
    @Override
    public void run(String... args) {

        System.out.println( "SqlDataCommandLineRunner Run! auto insert ... ");
        //sysService.initUser();
        sysService.initUser2();
        //System.out.println("passwordEncoder= " + passwordEncoder.encode("aa"));

        System.out.println( "SqlDataCommandLineRunner Run! auto insert order... ");
//        OrderDemo01 od = new OrderDemo01();
//        od.setOrderName("AA");
//        od.setStatus(1);
//        orderDemo01.save(od);
    }
}
