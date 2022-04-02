package me.chn.yams.common.entity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PageParam<T> {
    protected long size = 20;
    protected long current = 1;
    protected List<OrderItem> orders;
    @NotNull
    protected T model;

    public Page<T> toPage() {
        Page<T> page = new Page<>(current, size);
        page.setOrders(orders);
        return page;
    }

    public QueryWrapper<T> toQueryWrapper() {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        wrapper.setEntity(model);
        return wrapper;
    }

}
