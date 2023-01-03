package com.pixvs.main.dao;


import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ControlMaestroMultipleMainDao extends CrudRepository<ControlMaestroMultiple, String> {
}
