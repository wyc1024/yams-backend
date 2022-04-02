package me.chn.yams.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import lombok.Data;
import me.chn.yams.common.entity.BaseEntity;

@Data
@TableName("sys_user_role")
@Table(name = "sys_user_role", comment = "用户角色关联表")
public class UserRole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户 ID
     */
    @TableField("user_id")
    @Column(isNull = false, comment = "用户 ID")
    private Long userId;

    /**
     * 权限 ID
     */
    @TableField("role_id")
    @Column(isNull = false, comment = "权限 ID")
    private Long roleId;

}
