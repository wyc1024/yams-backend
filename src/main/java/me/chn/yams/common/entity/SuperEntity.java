package me.chn.yams.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import lombok.Data;

import java.io.Serializable;

@Data
public class SuperEntity implements Serializable {

    /**
     * 主键 ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @IsKey
    @Column(comment = "主键 ID")
    private Long id;

}
