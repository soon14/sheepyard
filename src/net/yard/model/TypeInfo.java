package net.yard.model;

import net.yard.jFinal.anatation.TableBind;

import com.jfinal.plugin.activerecord.Model;
/**
 * Type
 * @author chenchao
 * 2015/02/01
 */
@TableBind(name = "typeinfo")
public class TypeInfo extends Model<TypeInfo> {
	private static final long serialVersionUID = 288381011680989845L;
	public static TypeInfo dao = new TypeInfo();
}
