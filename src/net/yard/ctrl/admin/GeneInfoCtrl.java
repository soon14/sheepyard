package net.yard.ctrl.admin;

import java.util.ArrayList;
import java.util.List;

import net.yard.interceptor.ManagerPowerInterceptor;
import net.yard.jFinal.anatation.PowerBind;
import net.yard.jFinal.anatation.RouteBind;
import net.yard.model.GeneInfo;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;


/**
 * GeneInfo
 * @author chenchao
 * 2015/02/01
 */
@RouteBind(path = "/admin/config/gene")
@Before({ ManagerPowerInterceptor.class })
@PowerBind
public class GeneInfoCtrl extends BaseController {
	public GeneInfoCtrl(){
		this.tableName="geneinfo";
		this.modelClass=GeneInfo.class;
	}
	@Override
	public void index() {
		StringBuffer whee=new StringBuffer();
		List<Object> param=new ArrayList<Object>();
		String kwd=getPara("kwd");
		if(kwd!=null&&!"".equals(kwd.trim())){
			whee.append(" where gene_type like ?");
			param.add("%"+kwd+"%");
		}
		this.keepPara();
		setAttr("currentPage", getParaToInt("currentPage", 1));
		setAttr("pageSize",pageSize);
		setAttr("page", Db.paginate(getParaToInt("currentPage", 1),pageSize,
				"select id,gene_type 名称"," from geneinfo "+whee.toString(),param.toArray()));
		setAttr("collist", new String[]{"名称"});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void save(){
		try{
			Model<GeneInfo> G=this.getModel();
			Integer id=G.getInt("id");
			String gene_type=this.getPara("gene_type");
			G.set("gene_type", gene_type);
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
