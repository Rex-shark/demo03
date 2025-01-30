package com.example.demoapi;

import com.example.demoservice.service.ISysService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class SqlDataCommandLineRunner implements CommandLineRunner {



    @Resource
    private ISysService ISysService;



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
        ISysService.iniSysRole();
        ISysService.initUser();
        ISysService.initSysMenu();
        ISysService.initSysRoleMenu();

    }
}
