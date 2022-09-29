package com.gl.springbootservice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gl.springbootcommon.model.R;
import com.gl.springbootdao.entity.UserDO;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface UserService extends IService<UserDO> {
    R addUser(String name, Integer age);

    void deleteUser(Integer id);
}
