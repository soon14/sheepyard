package net.yard.ftl;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import net.yard.model.GrowthStatus;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateNumberModel;

public class GrowthStatusSelectTarget extends BaseTarget {

	public static String targetName="growstatusSelect";
	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Environment env, Map args, TemplateModel[] arg2,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		List<GrowthStatus> list= GrowthStatus.dao.find("select id,growth_status_name from growthstatus ");
		Writer out= env.getOut();
		for(GrowthStatus o:list){
			out.append("<option value='"+o.getInt("id")+"'>");
			out.append(o.getStr("growth_status_name"));
			out.append("</option>");
		}
	}
}
