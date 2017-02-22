package com.ad.dao;

import com.ad.entity.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDao extends CrudRepository<Order, Integer> {
    List<Order> findAllByUserId(Integer userId);
}
