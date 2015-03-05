package net.yard.ctrl.admin;

import java.util.ArrayList;
import java.util.List;

import net.yard.ftl.GrowthStatusSelectTarget;
import net.yard.interceptor.ManagerPowerInterceptor;
import net.yard.jFinal.anatation.PowerBind;
import net.yard.jFinal.anatation.RouteBind;
import net.yard.model.GrowthStatus;
import net.yard.model.GrowthStatusConfig;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;


/**
 * GeneInfo
 * @author chenchao
 * 2015/02/01
 */
@RouteBind(path = "/admin/config/statusconfig")
@Before({ ManagerPowerInterceptor.class })
@PowerBind
public class StatusConfigCtrl extends BaseController {
	public StatusConfigCtrl(){
		this.tableName="growthstatusconfig";
		this.modelClass=GrowthStatusConfig.class;
	}
	@Override
	public void index() {
		StringBuffer whee=new StringBuffer();
		List<Object> param=new ArrayList<Object>();
		String kwd=getPara("kwd");
		if(kwd!=null&&!"".equals(kwd.trim())){
			whee.append("where growthstatus.id = growthStatusconfig.to_id or " +
					    "growthstatus.id = growthStatusconfig.from_id and " +
					    "growthstatus.growth_status_name like ?");
			param.add("%"+kwd+"%");
		}
		String sql= "select a.id, b.growth_status_name as 前状态, c.growth_status_name as 后状态, a.config_time as 转化时间 " +
                    "from GrowthStatusConfig a " +
                    "inner join growthstatus b ON a.from_id = b.id " +
                    "inner join growthstatus c ON a.to_id = c.id " + whee.toString(); 
		this.keepPara();
		setAttr("currentPage", getParaToInt("currentPage", 1));
		setAttr("pageSize",pageSize);
		setAttr("page", Db.paginate(getParaToInt("currentPage", 1),pageSize, sql,whee.toString(),param.toArray()));
		setAttr("collist", new String[]{"前状态","后状态","转化时间"});
	}
	
	
	public void add(){
		try {
			Long id=this.getParaToLong(0,0L);
			Record po=new Record();
			if(id!=0L){
				String sql= "select a.id, b.growth_status_name as pre_status_name, c.growth_status_name as post_status_name, a.config_time as config_time " +
	                    "from GrowthStatusConfig a " +
	                    "inner join growthstatus b ON a.from_id = b.id " +
	                    "inner join growthstatus c ON a.to_id = c.id where a.id = "+ id; 
				po=Db.findFirst(sql);
			}
			this.setAttr("po",po);
			this.setAttr(GrowthStatusSelectTarget.targetName, new GrowthStatusSelectTarget());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void edit(){
		try {
			Long id=this.getParaToLong(0,0L);
			Record po=new Record();
			if(id!=0L){
				String sql= "select a.id, b.id as pre_status_id, b.growth_status_name as pre_status_name, c.id as post_status_id, c.growth_status_name as post_status_name, a.config_time as config_time " +
	                    "from GrowthStatusConfig a " +
	                    "inner join growthstatus b ON a.from_id = b.id " +
	                    "inner join growthstatus c ON a.to_id = c.id where a.id = "+ id; 
				po=Db.findFirst(sql);
			}
			List<GrowthStatus> status_list=GrowthStatus.dao.find("select id,growth_status_name from growthstatus ");
			this.setAttr("po",po);
			this.setAttr(GrowthStatusSelectTarget.targetName, new GrowthStatusSelectTarget());
			this.setAttr("status_list", status_list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void save(){
		try{
			Model<GrowthStatusConfig> G=this.getModel();
			Long id=G.getLong("id");
			int post_config_status=this.getParaToInt("post_config_status");
			int pre_config_status=this.getParaToInt("pre_config_status");
			int config_time=this.getParaToInt("config_time");
			G.set("from_id", pre_config_status);
			G.set("to_id", post_config_status);
			G.set("config_time", config_time);
			if(id!=null&&id!=0L){
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

