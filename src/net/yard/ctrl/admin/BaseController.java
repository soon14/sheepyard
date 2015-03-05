package net.yard.ctrl.admin;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.yard.model.Manager;

import com.jfinal.core.Controller;
import com.jfinal.core.TypeConverter;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.TableInfo;
import com.jfinal.plugin.activerecord.TableInfoMapping;

/**
 * BaseController
 * @author chenchao
 * 2015/02/01
 */
public abstract class BaseController extends Controller {
	protected int pageSize=10;
	protected String tableName;
	protected Class modelClass;
	public abstract void index();
	@Override
	public void render(String view) {
		super.render(view);
	}
	/**获取当前系统操作人*/
	public Manager getCurrentUser(){
		return this.getSessionAttr("Manager");
	}
	/**
	 * 转换dwz json格式输出
	 * @param statusCode
	 * @param message
	 * @param navTabId
	 * @return
	 */
	public void toDwzJson(String statusCode,String message,String... navTabId){
		Map<String,Object> jsonMap=new HashMap<String,Object>();
		jsonMap.put("statusCode", statusCode);
		if(message!=null)
		jsonMap.put("message",message);
		if(navTabId!=null)
		jsonMap.put("navTabId", navTabId);
		this.renderJson(jsonMap);
	}
	public void toDwzJson(String statusCode,String message,Long id){
		Map<String,Object> jsonMap=new HashMap<String,Object>();
		jsonMap.put("statusCode", statusCode);
		if(message!=null)
		jsonMap.put("message",message);
		if(id!=null)
		jsonMap.put("idVal",id);
		this.renderJson(jsonMap);
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void save() {
		try{
			Model m=this.getModel();
			Long id=m.getLong("id");
			if(id!=null&&id!=0){
				m.update();
			}else{
				m.save();
			}
			toDwzJson("success", "保存成功！",id);
		}catch(Exception e){
			e.printStackTrace();
			toDwzJson( "error", "保存异常！");
		}
	}

	public void del() {
		try{
			Long id=this.getParaToLong(0,0L);
			Db.deleteById(tableName, id);
			toDwzJson("success", "删除成功！",id);
		}catch(Exception e){
			e.printStackTrace();
			toDwzJson( "error", "删除异常！");
		}
	}

	public void edit() {
		try {
			Long id=this.getParaToLong(0,0L);
			Record po=new Record();
			if(id!=0L){
				po=Db.findById(tableName,id);
			}
			this.setAttr("po",po);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({"unchecked","rawtypes"})
	public void update(){
		try{
			Long id=this.getParaToLong(0,0L);		
			Model m=this.getModel(modelClass);
			if(id!=null&&id!=0){
				m.update();
			}else{
				m.save();
			}
			toDwzJson("success", "保存成功！");
		}catch(Exception e){
			e.printStackTrace();
			toDwzJson( "error", "保存异常！");
		}
	}
	public void view() {
		this.edit();
	}
	
	public Model getModel() throws Exception {
		HttpServletRequest request = this.getRequest();
		Model model_ = (Model) modelClass.newInstance();
		TableInfo tableInfo = TableInfoMapping.me().getTableInfo(modelClass);
		model_.getAttrNames();
		Enumeration<String> attrNames = request.getParameterNames();
		while(attrNames.hasMoreElements()) {
			String attr = attrNames.nextElement();
			Class<?> colType = null;
			if (tableInfo.hasColumnLabel(attr.toLowerCase()))
				colType = tableInfo.getColType(attr.toLowerCase());
			if (tableInfo.hasColumnLabel(attr.toUpperCase())) {
				colType = tableInfo.getColType(attr.toUpperCase());
			}
			if (colType != null) {
				String value = request.getParameter(attr);
				model_.set(attr.toLowerCase(),
						value != null ? TypeConverter.convert(colType, value): null);
			}
		}
		return model_;
	}
}
