package com.max.tse.db.mybatis.dao;

import com.max.tse.db.mybatis.DaoAnnotation;
import com.max.tse.db.mybatis.po.ChangeDbParam;
import com.max.tse.db.mybatis.po.OrderInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-20
 * Time: 下午4:45
 * To change this template use File | Settings | File Templates.
 */
@DaoAnnotation
@Repository
public interface OrderInfoDao {

    public int insert(@Param("orderInfo") OrderInfo orderInfo);

    public OrderInfo queryByOrderNo(@Param("orderNo") String orderNo, ChangeDbParam changeDbParam);
}
