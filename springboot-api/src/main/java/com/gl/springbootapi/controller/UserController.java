package com.gl.springbootapi.controller;

import com.gl.springbootcommon.constants.ErrorEnum;
import com.gl.springbootcommon.model.R;
import com.gl.springbootservice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public R addUser(String name, Integer age) {
        return userService.addUser(name, age);
    }

    @PostMapping("/delete")
    public R deleteUser(Integer id) {
        if(id == null){
            return R.error(ErrorEnum.BAD_REQUEST.getCode(), ErrorEnum.BAD_REQUEST.getDesc());
        }
        userService.deleteUser(id);
        return R.ok();
    }
}
