package com.lin.app.common.lottery;

import java.util.List;

/**
 * Created by linhui on 2017/10/25.
 */
public interface IBuyGroupEntity {

    ICycleEntity getCycle();

    List<IOrderEntity> getOrders();

    String getBatchKey();


}
