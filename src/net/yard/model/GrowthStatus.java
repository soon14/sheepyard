package net.yard.model;

import net.yard.jFinal.anatation.TableBind;

import com.jfinal.plugin.activerecord.Model;
/**
 * GeneInfo
 * @author chenchao
 *2015/02/01
 */
@TableBind(name = "growthstatus")
public class GrowthStatus extends Model<GrowthStatus> {
	private static final long serialVersionUID = 7777142693443549865L;
	public static GrowthStatus dao = new GrowthStatus();
}
