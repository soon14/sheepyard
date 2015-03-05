package net.yard.model;

import net.yard.jFinal.anatation.TableBind;

import com.jfinal.plugin.activerecord.Model;
/**
 * Type
 * @author chenchao
 * 2015/02/01
 */
@TableBind(name = "growthstatusconfig")
public class GrowthStatusConfig extends Model<GrowthStatusConfig> {
	private static final long serialVersionUID = 288381011680989845L;
	public static GrowthStatusConfig dao = new GrowthStatusConfig();
}
