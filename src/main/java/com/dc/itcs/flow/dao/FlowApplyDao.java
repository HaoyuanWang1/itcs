package com.dc.itcs.flow.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.dc.flamingo.core.data.BaseDao;
import com.dc.itcs.flow.entity.FlowApply;

public interface FlowApplyDao extends BaseDao<FlowApply, Long>{

	FlowApply findByApplyClassAndApplyId(String applyClass, Long applyId);

	List<FlowApply> findByNodeName(String nodeName);
	
	@Query(value="SELECT count(flowApply.signerIds)"
			+" FROM FlowApply flowApply"
			+" WHERE flowApply.nodeName=?1"
			+" AND flowApply.signerIds like %?2")
	int findByNodeNameAndSignerId(String nodename,String signerids);

}
