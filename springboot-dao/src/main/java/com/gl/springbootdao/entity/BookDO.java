package com.gl.springbootdao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 书籍表
 * </p>
 *
 * @author gelei
 * @since 2022-09-23
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("book")
public class BookDO extends Model<BookDO> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 书籍类型
     */
    @TableField("type")
    private String type;

    /**
     * 书籍名称
     */
    @TableField("name")
    private String name;

    /**
     * 书籍描述
     */
    @TableField("description")
    private String description;
    /**
     * 删除状态(0未删除；1已删除)
     */
    @TableField("status")
    private Integer status;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
