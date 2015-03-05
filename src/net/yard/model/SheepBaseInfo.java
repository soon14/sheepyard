package net.yard.model;

import net.yard.jFinal.anatation.TableBind;

import com.jfinal.plugin.activerecord.Model;
/**
 * 地区
 * @author loyin
 *2013-1-15 下午10:54:45
 */
@TableBind(name = "baseinfo")
public class SheepBaseInfo extends Model<BaseInfo> {
	private static final long serialVersionUID = 5756476309813389610L;
	public static SheepBaseInfo dao = new SheepBaseInfo();
	
	public GeneInfo getGeneInfo() {
        return GeneInfo.dao.findById(get("classesid"));
    }
	
	public TypeInfo getTypeInfo() {
		return TypeInfo.dao.findById(get("classesid"));
	}
}
