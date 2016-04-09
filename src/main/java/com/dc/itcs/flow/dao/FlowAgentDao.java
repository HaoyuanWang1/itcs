package com.dc.itcs.flow.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.dc.flamingo.core.data.BaseDao;
import com.dc.itcs.flow.entity.FlowAgent;

/**
 * 流程代办DAO
 * @author lee
 *
 */
public interface FlowAgentDao extends BaseDao<FlowAgent, Long> {
    @Query(value = "SELECT fa FROM FlowAgent fa WHERE fa.startDate<=?1 AND fa.endDate>=?2")
    List<FlowAgent> getNowAgent(String nowDate, String nowDate1);
}
