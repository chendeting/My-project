package com.ad.dao;

import com.ad.entity.Train;
import org.springframework.data.repository.CrudRepository;

public interface TrainDao extends CrudRepository<Train, Integer> {
}
