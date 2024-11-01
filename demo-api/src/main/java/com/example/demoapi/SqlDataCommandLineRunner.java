package com.example.demoapi;

import com.example.demoservice.model.OrderDemo01;
import com.example.demoservice.model.UserBase;
import com.example.demoservice.repository.IOrderDemo01;
import com.example.demoservice.repository.IUserBaseRepository;
import com.example.demoservice.util.PasswordUtil;
import jakarta.annotation.Resource;
import org.apache.catalina.User;
import org.hibernate.query.Order;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class SqlDataCommandLineRunner implements CommandLineRunner {

    @Resource
    private IUserBaseRepository userBaseRepository;

    @Resource
    private IOrderDemo01 orderDemo01;

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

        if (userBaseRepository.findByAccount("rex").isEmpty()) {
            UserBase userBase = new UserBase();
            userBase.setAccount("rex");
            userBase.setPassword(PasswordUtil.hashWithMD5("123456"));
            userBaseRepository.save(userBase);
        }

        System.out.println( "SqlDataCommandLineRunner Run! auto insert order... ");
        OrderDemo01 od = new OrderDemo01();
        od.setOrderName("AA");
        od.setStatus(1);
        orderDemo01.save(od);
    }
}
