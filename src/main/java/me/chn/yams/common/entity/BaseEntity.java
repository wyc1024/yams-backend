package me.chn.yams.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity extends SuperEntity {

    /**
     * 创建者
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT_UPDATE)
    @Column(isNull = false, comment = "创建者")
    private Long createUser;

    /**
     * 更新者
     */
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    @Column(comment = "更新者")
    private Long updateUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Column(isNull = false, comment = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT)
    @Column(comment = "更新时间")
    private LocalDateTime updateTime;

}
