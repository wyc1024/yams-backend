package me.chn.yams.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import lombok.Data;
import me.chn.yams.common.entity.BaseEntity;

@Data
@TableName("sys_role")
@Table(name = "sys_role", comment = "角色表")
public class Role extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @TableField("name")
    @Column(isNull = false, comment = "名称")
    private String name;

    /**
     * 权限标识
     */
    @TableField("permission")
    @Column(isNull = false, comment = "权限标识")
    private String permission;

    /**
     * 备注
     */
    @TableField("remark")
    @Column(comment = "备注")
    private String remark;

}
