package net.yard.ctrl.admin;

import java.util.ArrayList;
import java.util.List;

import net.yard.interceptor.ManagerPowerInterceptor;
import net.yard.jFinal.anatation.PowerBind;
import net.yard.jFinal.anatation.RouteBind;
import net.yard.model.GrowthStatus;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;


/**
 * GeneInfo
 * @author chenchao
 * 2015/02/01
 */
@RouteBind(path = "/admin/config/growthstatus")
@Before({ ManagerPowerInterceptor.class })
@PowerBind
public class GrowthStatusCtrl extends BaseController {
	public GrowthStatusCtrl(){
		this.tableName="growthstatus";
		this.modelClass=GrowthStatus.class;
	}
	@Override
	public void index() {
		StringBuffer whee=new StringBuffer();
		List<Object> param=new ArrayList<Object>();
		String kwd=getPara("kwd");
		if(kwd!=null&&!"".equals(kwd.trim())){
			whee.append(" where growth_status_name like ?");
			param.add("%"+kwd+"%");
		}
		this.keepPara();
		setAttr("currentPage", getParaToInt("currentPage", 1));
		setAttr("pageSize",pageSize);
		setAttr("page", Db.paginate(getParaToInt("currentPage", 1),pageSize,
				"select id,growth_status_name 名称"," from growthstatus "+whee.toString(),param.toArray()));
		setAttr("collist", new String[]{"名称"});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void save(){
		try{
			Model<GrowthStatus> G=this.getModel();
			Integer id=G.getInt("id");
			String growth_status_name=this.getPara("growth_status_name");
			G.set("growth_status_name", growth_status_name);
			if(id!=null&&id!=0){
				G.update();
			}else{
				G.remove("id");
				G.save();
			}
			toDwzJson("success", "保存成功！");
		}catch(Exception e){
			e.printStackTrace();
			toDwzJson( "error", "保存异常！");
		}
	}
}

