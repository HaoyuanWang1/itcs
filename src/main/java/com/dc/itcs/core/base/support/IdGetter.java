package com.dc.itcs.core.base.support;

import com.dc.flamingo.tools.IdGenerator;

/**
 * 项目内编号生成器统一处理类
 * @author lee
 *
 */
public enum IdGetter {
	userApply {	//申请单编号
		@Override
		public String getNum() {
			return IdGenerator.initLastNumber("UserApply", 8, "CT-");
		}
	};
	public abstract String getNum();
}
