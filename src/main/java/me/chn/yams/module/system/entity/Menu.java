package me.chn.yams.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import me.chn.yams.common.entity.BaseEntity;
import me.chn.yams.common.enums.YN;
import me.chn.yams.module.system.enums.MenuType;

@Data
@TableName("sys_menu")
@Table(name = "sys_menu", comment = "菜单表")
public class Menu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 父 ID
     */
    @TableField(value = "parent_id")
    @Column(isNull = false, comment = "父 ID")
    private Long parentId;

    /**
     * 菜单标题
     */
    @TableField("title")
    @Column(isNull = false, comment = "菜单标题")
    private String title;

    /**
     * 菜单 URL
     */
    @TableField("url")
    @Column(isNull = false, comment = "菜单 URL")
    private String url;

    /**
     * 权限标识
     */
    @TableField("permission")
    @Column(isNull = false, comment = "权限标识")
    private String permission;

    /**
     * 图标
     */
    @TableField("icon")
    @Column(comment = "图标")
    private String icon;

    /**
     * 类型 0菜单 1按钮
     */
    @TableField("type")
    @Column(isNull = false, type = MySqlTypeConstant.TINYINT, comment = "类型 0菜单 1按钮")
    private MenuType type;

    /**
     * 是否隐藏
     */
    @TableField("hidden")
    @Column(isNull = false, type = MySqlTypeConstant.TINYINT, comment = "是否隐藏")
    private YN hidden;

    /**
     * 排序
     */
    @TableField("sort")
    @Column(isNull = false, comment = "排序")
    private Integer sort;

}
