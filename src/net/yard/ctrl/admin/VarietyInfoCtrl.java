package net.yard.ctrl.admin;

import java.util.ArrayList;
import java.util.List;

import net.yard.interceptor.ManagerPowerInterceptor;
import net.yard.jFinal.anatation.PowerBind;
import net.yard.jFinal.anatation.RouteBind;
import net.yard.model.VarietyInfo;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;


/**
 * GeneInfo
 * @author chenchao
 * 2015/02/01
 */
@RouteBind(path = "/admin/config/variety")
@Before({ ManagerPowerInterceptor.class })
@PowerBind
public class VarietyInfoCtrl extends BaseController {

	public VarietyInfoCtrl(){
		this.tableName="varietyinfo";
		this.modelClass=VarietyInfo.class;
	}
	@Override
	public void index() {
		// TODO Auto-generated method stub
		StringBuffer whee=new StringBuffer();
		List<Object> param=new ArrayList<Object>();
		String kwd=getPara("kwd");
		if(kwd!=null&&!"".equals(kwd.trim())){
			whee.append(" where variety_name like ?");
			param.add("%"+kwd+"%");
		}
		this.keepPara();
		setAttr("currentPage", getParaToInt("currentPage", 1));
		setAttr("pageSize",pageSize);
		setAttr("page", Db.paginate(getParaToInt("currentPage", 1),pageSize,
				"select id,variety_name 名称"," from  varietyinfo"+whee.toString(),param.toArray()));
		setAttr("collist", new String[]{"名称"});
	}
	
	@Override
	public void save(){
		try{
			Model<VarietyInfo> G=this.getModel();
			Integer id=G.getInt("id");
			String variety_name=this.getPara("variety_name");
			G.set("variety_name", variety_name);
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

