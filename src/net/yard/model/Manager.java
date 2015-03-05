package net.yard.model;

import net.yard.jFinal.anatation.TableBind;

import com.jfinal.plugin.activerecord.Model;

/**
 * 管理用户
 * 
 * @author loyin 2013-1-13 下午2:19:04
 */
@TableBind(name = "manager")
public class Manager extends Model<Manager> {
	private static final long serialVersionUID = 2843526515234949937L;
	public static Manager dao = new Manager();
}
