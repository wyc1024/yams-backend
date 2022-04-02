package me.chn.yams.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import me.chn.yams.common.entity.BaseEntity;
import me.chn.yams.common.enums.YN;
import me.chn.yams.module.system.enums.Sex;
import me.chn.yams.module.system.enums.UserStatus;

import javax.validation.constraints.NotNull;

@Data
@TableName("sys_user")
@Table(name = "sys_user", comment = "用户表")
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableField("username")
    @NotNull
    @Column(isNull = false, comment = "用户名")
    @Unique
    private String username;

    /**
     * 昵称
     */
    @TableField("nick_name")
    @NotNull
    @Column(isNull = false, comment = "昵称")
    @Unique
    private String nickName;

    /**
     * 性别
     */
    @TableField("sex")
    @NotNull
    @Column(isNull = false, type = MySqlTypeConstant.TINYINT, comment = "性别")
    private Sex sex;

    /**
     * 手机号码
     */
    @TableField("phone")
    @Column(comment = "手机号码")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("email")
    @Column(comment = "邮箱")
    private String email;

    /**
     * 头像
     */
    @TableField("avatar")
    @Column(comment = "头像")
    private String avatar;

    /**
     * 密码
     */
    @TableField("`password`")
    @NotNull
    @Column(isNull = false, comment = "密码")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * 是否为admin账号
     */
    @TableField("is_admin")
    @NotNull
    @Column(isNull = false, type = MySqlTypeConstant.TINYINT, comment = "是否为admin账号")
    private YN isAdmin;

    /**
     * 状态：1启用、0禁用
     */
    @TableField("`status`")
    @NotNull
    @Column(isNull = false, type = MySqlTypeConstant.TINYINT, comment = "状态")
    private UserStatus status;

}
