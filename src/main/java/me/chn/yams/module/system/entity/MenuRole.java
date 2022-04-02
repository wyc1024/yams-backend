package me.chn.yams.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import lombok.Data;
import me.chn.yams.common.entity.SuperEntity;

@Data
@TableName("sys_menu_role")
@Table(name = "sys_menu_role", comment = "菜单角色关联表")
public class MenuRole extends SuperEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单 ID
     */
    @TableField("menu_id")
    @Column(isNull = false, comment = "菜单 ID")
    private Long menuId;

    /**
     * 权限 ID
     */
    @TableField("role_id")
    @Column(isNull = false, comment = "权限 ID")
    private Long roleId;

}
