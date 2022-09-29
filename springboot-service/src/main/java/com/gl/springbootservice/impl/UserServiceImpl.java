package com.gl.springbootservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.gl.springbootcommon.annotation.ApiChecked;
import com.gl.springbootcommon.constants.ErrorEnum;
import com.gl.springbootcommon.constants.StatusEnum;
import com.gl.springbootcommon.model.R;
import com.gl.springbootdao.entity.UserDO;
import com.gl.springbootdao.mapper.UserMapper;
import com.gl.springbootservice.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gelei
 * @since 2022-09-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Override
    @ApiChecked(value = "新增用户")
    public R addUser(String name, Integer age) {
        if(StringUtils.isBlank(name)){
            return R.error(ErrorEnum.BAD_REQUEST.getCode(), ErrorEnum.BAD_REQUEST.getDesc());
        }
        if(age == null){
            return R.error(ErrorEnum.BAD_REQUEST.getCode(), ErrorEnum.BAD_REQUEST.getDesc());
        }
        UserDO userDO = new UserDO();
        userDO.setName(name);
        userDO.setAge(age);
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getName, name);
        List<UserDO> list = this.list(queryWrapper);
        if(CollectionUtils.isNotEmpty(list)){
            return R.error("用户已存在");
        }
        this.save(userDO);
        return R.ok();
    }

    @Override
    @ApiChecked(value = "删除用户")
    public void deleteUser(Integer id) {
        LambdaUpdateWrapper<UserDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(UserDO::getStatus, StatusEnum.DISABLE.getCode());
        updateWrapper.eq(UserDO::getId, id);
        this.update(updateWrapper);
    }
}
