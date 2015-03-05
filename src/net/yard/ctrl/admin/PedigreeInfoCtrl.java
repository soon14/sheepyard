package net.yard.ctrl.admin;

import java.util.ArrayList;
import java.util.List;

import net.yard.interceptor.ManagerPowerInterceptor;
import net.yard.jFinal.anatation.PowerBind;
import net.yard.jFinal.anatation.RouteBind;
import net.yard.model.BaseInfo;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;


/**
 * GeneInfo
 * @author chenchao
 * 2015/02/01
 */
@RouteBind(path = "/admin/sheep/pedigreeinfo")
@Before({ ManagerPowerInterceptor.class })
@PowerBind
public class PedigreeInfoCtrl extends BaseController {
	public PedigreeInfoCtrl(){
		this.tableName="baseinfo";
		this.modelClass=BaseInfo.class;
	}
	@Override
	public void index() {
		
	}
	
	public void show(){
		StringBuffer whee=new StringBuffer();
		List<Object> param=new ArrayList<Object>();
		String kwd=getPara("kwd");
	}
}
