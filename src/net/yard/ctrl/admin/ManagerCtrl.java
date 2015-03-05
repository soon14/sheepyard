package net.yard.ctrl.admin;

import java.util.ArrayList;
import java.util.List;

import net.yard.interceptor.ManagerPowerInterceptor;
import net.yard.jFinal.anatation.PowerBind;
import net.yard.jFinal.anatation.RouteBind;
import net.yard.model.Manager;
import net.yard.util.safe.MD5;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;

/**
 * 后台管理用户
 * @author chenchao
 * 2015/02/01
 */
@RouteBind(path = "/admin/manager")
@Before({ ManagerPowerInterceptor.class })
@PowerBind
public class ManagerCtrl extends BaseController {
	public ManagerCtrl(){
		this.tableName="manager";
		this.modelClass=Manager.class;
	}
	@Override
	public void index() {
		StringBuffer whee=new StringBuffer(" where 1=1 ");
		List<Object> param=new ArrayList<Object>();
		String kwd=getPara("kwd");
		if(kwd!=null&&!"".equals(kwd.trim())){
			whee.append(" and (username like ? or phone like ? or userno like ?)");
			param.add("%"+kwd+"%");
			param.add("%"+kwd+"%");
			param.add("%"+kwd+"%");
		}
		this.keepPara();
		setAttr("currentPage", getParaToInt("currentPage", 1));
		setAttr("pageSize",pageSize);
		setAttr("page", Db.paginate(getParaToInt("currentPage", 1),pageSize,
				"select id,userno 用户名,username 姓名,sex 性别,phone 手机,regdate 注册日期,enable 状态",
				" from manager "+whee.toString()+" order by regdate desc",param.toArray()));
		setAttr("collist", new String[]{"用户名","姓名","性别","手机","注册日期","状态"});
	}
	public void dis(){
		try{
			Long id=this.getParaToLong(0);
			if(id==0||id==null){
				throw new Exception();
			}
			Db.update("update manager set enable=(case when enable=1 then  0 else 1 end) where id=?", new Object[]{id});
			this.toDwzJson("success", "设置状态成功！");
		}catch(Exception e){
			this.toDwzJson("error", "设置状态异常！");
		}
	}
}
