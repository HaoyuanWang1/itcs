package com.dc.itcs.flow.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.data.Criteria;
import com.dc.flamingo.core.data.MatchMode;
import com.dc.flamingo.core.data.Restrictions;
import com.dc.flamingo.core.service.BaseService;
import com.dc.flamingo.core.support.PageParam;
import com.dc.flamingo.core.utils.StrUtils;
import com.dc.itcs.core.base.entity.FlowEntity;
import com.dc.itcs.flow.dao.FlowApplyDao;
import com.dc.itcs.flow.entity.FlowApply;
import com.dc.itcs.security.entity.UserInfo;

/**
 * 流程申请服务层
 * @author lee
 *
 */
@Service
@Transactional(readOnly = true)
public class FlowApplyService extends BaseService{
	@Autowired
	private FlowApplyDao flowApplyDao;

	/**
	 * 根据申请实体类名和ID获取流程申请记录
	 * @Methods Name findByClassAndId
	 * @Create In 2014年11月19日 By lee
	 * @param applyClass
	 * @param applyId
	 * @return
	 */
	public FlowApply findByClassAndId(String applyClass, Long applyId) {
		return flowApplyDao.findByApplyClassAndApplyId(applyClass,applyId);
	}
	
	/**
	 * 保存流程申请记录
	 * @Methods Name save
	 * @Create In 2014年11月19日 By lee
	 * @param flowApply
	 * @return
	 */
	@Transactional
	public FlowApply save(FlowApply flowApply) {
		return flowApplyDao.save(flowApply);
	}
	
	/**
	 * 分页查询流程申请记录
	 * @Methods Name findByPage
	 * @Create In 2014年11月19日 By lee
	 * @param pageable
	 * @param paramMap
	 * @return
	 */
	public Page<FlowApply> findByPage(Pageable pageable, Map<String, Object> paramMap) {
		// 待我处理的申请中申请人模糊查询修改
		String createrUserMatcher = (paramMap.get("createrUserMatcher") == null ? "":paramMap.get("createrUserMatcher").toString());
		// 将参数移除，此处的createrUserMatcher只做传递参数使用（从页面到service）
		paramMap.remove("createrUserMatcher");
		Criteria<FlowApply> c = super.getCriteria(paramMap, FlowApply.class);
		
		// 按申请人的uid、code、userName模糊查询
		c.add(Restrictions.or(
				Restrictions.like("createUser.uid", createrUserMatcher, MatchMode.ANYWHERE,true),
				Restrictions.like("createUser.code", createrUserMatcher, MatchMode.ANYWHERE,true),
				Restrictions.like("createUser.userName", createrUserMatcher, MatchMode.ANYWHERE,true)
			)
		);
		return flowApplyDao.findAll(c, pageable);
	}
	
	/**
	 * 分页查询流程申请记录(账号类)
	 * @Methods Name findAccountApplyByPage
	 * @Create In 2014年11月19日 By liuying
	 * @param pageable
	 * @param paramMap
	 * @return
	 */
	public Page<FlowApply> findAccountApplyByPage(Pageable pageable, Map<String, Object> paramMap) {
		Criteria<FlowApply> c = super.getCriteria(paramMap, FlowApply.class);
		c.add(Restrictions.ne("state", FlowEntity.STATE_DRAFT, true));
		c.add(Restrictions.isNotNull("processId"));
		c.add(Restrictions.like("applyNum", "AC-", MatchMode.START,true));
		return flowApplyDao.findAll(c, pageable);
	}
	
	/**
	 * 导出查询流程申请记录(账号类)
	 * @Methods Name findAccountApplyByPage
	 * @Create In 2014年11月19日 By liuying
	 * @param pageable
	 * @param paramMap
	 * @return
	 */
	public List<FlowApply> findAccountApplyByPageForExport(Pageable pageable, Map<String, Object> paramMap) {
		Criteria<FlowApply> c = super.getCriteria(paramMap, FlowApply.class);
		c.add(Restrictions.ne("state", FlowEntity.STATE_DRAFT, true));
		c.add(Restrictions.isNotNull("processId"));
		c.add(Restrictions.like("applyNum", "AC-", MatchMode.START,true));
		return flowApplyDao.findAll(c);
	}
	
	/**
	 * 增加用户关注
	 * @Methods Name addFollow
	 * @Create In 2014年11月19日 By lee
	 * @param curUser
	 * @param applyClass
	 * @param applyId
	 */
	@Transactional
	public void addFollow(UserInfo curUser, String applyClass, Long applyId) {
		FlowApply flowApply = flowApplyDao.findByApplyClassAndApplyId(applyClass, applyId);
		if(curUser!=null){
			String followUserIds = flowApply.getFollowUserIds();
			List<String> uids = StrUtils.splitToList(followUserIds, ",", "【", "】");
			String curUid = curUser.getId().toString();
			if(!uids.contains(curUid)){
				uids.add(curUid);
			}
			flowApply.setFollowUserIds(StrUtils.join(uids,  ",", "【", "】"));
			flowApplyDao.save(flowApply);
		}
	}

	/**
	 * 取消用户关注
	 * @Methods Name removeFollow
	 * @Create In 2014年11月19日 By lee
	 * @param curUser
	 * @param applyClass
	 * @param applyId
	 */
	@Transactional
	public void removeFollow(UserInfo curUser, String applyClass, Long applyId) {
		if(curUser!=null){
			FlowApply flowApply = flowApplyDao.findByApplyClassAndApplyId(applyClass, applyId);
			String followUserIds = flowApply.getFollowUserIds();
			List<String> uids = StrUtils.splitToList(followUserIds, ",", "【", "】");
			String curUid = curUser.getId().toString();
			if(uids.contains(curUid)){
				uids.remove(curUid);
			}
			flowApply.setFollowUserIds(StrUtils.join(uids, ",", "【", "】"));
			flowApplyDao.save(flowApply);
		}
	}

	/**
	 * 获取我的草稿申请（包括驳回的）
	 * @Methods Name findMyDraftByPage
	 * @Create In 2014年12月3日 By lee
	 * @param pageParam
	 * @param param
	 * @return
	 */
	public Page<FlowApply> findMyDraftByPage(PageParam pageParam, Map<String, Object> paramMap) {
		Criteria<FlowApply> c = super.getCriteria(paramMap, FlowApply.class);
		c.add(Restrictions.or(Restrictions.eq("state", FlowEntity.STATE_DRAFT, false),Restrictions.eq("state", FlowEntity.STATE_BACK, false)));
		return flowApplyDao.findAll(c, pageParam);
	}

	public List<FlowApply> findByList(Map<String, Object> paramMap) {
		// 待我处理的申请中申请人模糊查询修改
		String createrUserMatcher = (paramMap.get("createrUserMatcher") == null ? "":paramMap.get("createrUserMatcher").toString());
		// 将参数移除，此处的createrUserMatcher只做传递参数使用（从页面到service）
		paramMap.remove("createrUserMatcher");
		Criteria<FlowApply> c = super.getCriteria(paramMap, FlowApply.class);
		
		// 按申请人的uid、code、userName模糊查询
		c.add(Restrictions.or(
				Restrictions.like("createUser.uid", createrUserMatcher, MatchMode.ANYWHERE,true),
				Restrictions.like("createUser.code", createrUserMatcher, MatchMode.ANYWHERE,true),
				Restrictions.like("createUser.userName", createrUserMatcher, MatchMode.ANYWHERE,true)
			)
		);
		return flowApplyDao.findAll(c);
	}
	
	//根据节点名
	public List<FlowApply> findByNodeNameList(String nodeName) {
		return flowApplyDao.findByNodeName(nodeName);
	}
	
	public int findByNodeNameAndSignerId(String nodename,String signerids){
		return flowApplyDao.findByNodeNameAndSignerId(nodename,signerids);
	}
	
}
