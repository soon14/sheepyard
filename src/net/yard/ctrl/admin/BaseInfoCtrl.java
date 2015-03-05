package net.yard.ctrl.admin;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

import net.yard.ftl.GeneInfoSelectTarget;
import net.yard.ftl.GrowthStatusSelectTarget;
import net.yard.ftl.TypeInfoSelectTarget;
import net.yard.ftl.VarietyInfoSelectTarget;
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
@RouteBind(path = "/admin/sheep/baseinfo")
@Before({ ManagerPowerInterceptor.class })
@PowerBind
public class BaseInfoCtrl extends BaseController {

	public BaseInfoCtrl(){
		this.tableName="baseinfo";
		this.modelClass=BaseInfo.class;
	}
	@Override
	public void index() {
		// TODO Auto-generated method stub
		StringBuffer whee=new StringBuffer();
		List<Object> param=new ArrayList<Object>();
		String kwd=getPara("kwd");
		if(kwd!=null&&!"".equals(kwd.trim())){
			whee.append(" where type_name like ?");
			param.add("%"+kwd+"%");
		}
		this.keepPara();
		setAttr("currentPage", getParaToInt("currentPage", 1));
		setAttr("pageSize",pageSize);
		setAttr("page", Db.paginate(getParaToInt("currentPage", 1),pageSize,
				"select a.id, a.mark_num as 耳标号, a.father_sheep_ID as 父羊, a.mother_sheep_ID as 母羊,"+
				"b.gene_type as 基因型, concat(a.sex_genda,'') as 性别, c.type_name as 类型, a.birth_day as 出生日期," +
			    "a.enter_day as 入场日期,d.pen_name as 羊舍名字, e.variety_name as 品种, concat(a.is_death,'') as 是否死亡,"+
				"concat(a.is_sell,'') as 是否销售, f.growth_status_name as 状态",
			    "from BaseInfo a, GeneInfo b, TypeInfo c, PenInfo d, Varietyinfo e, GrowthStatus f "+
				"where a.gene_id = b.id and a.type_id=c.id and a.pen_id = d.id and a.variety_id = e.id "+
			    "and a.growth_status = f.id"));
		setAttr("collist", new String[]{"耳标号","父羊","母羊","基因型","性别","类型","出生日期",
				                        "入场日期","羊舍名字","品种","是否死亡","是否销售","状态"});
	}
	
	public void add(){
		try {
			this.setAttr(GeneInfoSelectTarget.targetName, new GeneInfoSelectTarget());
			this.setAttr(TypeInfoSelectTarget.targetName, new TypeInfoSelectTarget());
			this.setAttr(VarietyInfoSelectTarget.targetName, new VarietyInfoSelectTarget());
			this.setAttr(GrowthStatusSelectTarget.targetName, new GrowthStatusSelectTarget());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void save(){
		try{
			Model<BaseInfo> G=this.getModel();
			Integer id=G.getInt("id");
			String mark_num=this.getPara("mark_num");
			Long father_sheep_id=this.getParaToLong("father_sheep_id");
			Long mother_sheep_id=this.getParaToLong("mother_sheep_id");
			Integer gene_id=this.getParaToInt("gene_type");
			Integer sex_genda=this.getParaToInt("sex_genda");
			Integer type_id=this.getParaToInt("type_name");
			Integer growth_status=this.getParaToInt("growth_status");
			Integer variety_id=this.getParaToInt("variety_name");
			String birth_day=this.getPara("birth_day");
			String enter_day=this.getPara("enter_day");
			Integer pen_id=this.getParaToInt("yard_name");
			
			Timestamp birth_day_ts=new Timestamp(System.currentTimeMillis());  
			Timestamp enter_day_ts=new Timestamp(System.currentTimeMillis());  
			birth_day_ts=Timestamp.valueOf(birth_day);
			enter_day_ts=Timestamp.valueOf(enter_day);
			
			G.set("mark_num", mark_num);
			G.set("father_sheep_id", father_sheep_id);
			G.set("mother_sheep_id", mother_sheep_id);
			G.set("gene_id", gene_id);
			G.set("sex_genda", sex_genda);
			G.set("type_id", type_id);
			G.set("birth_day", birth_day_ts);
			G.set("enter_day", enter_day_ts);
			G.set("growth_status", growth_status);
			G.set("pen_id", pen_id);
			G.set("variety_id", variety_id);
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
