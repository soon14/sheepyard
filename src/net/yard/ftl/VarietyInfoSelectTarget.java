package net.yard.ftl;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import net.yard.model.VarietyInfo;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateNumberModel;

public class VarietyInfoSelectTarget extends BaseTarget {

	public static String targetName="varietyInfoSelect";
	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Environment env, Map args, TemplateModel[] arg2,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		List<VarietyInfo> list= VarietyInfo.dao.find("select id,variety_name from VarietyInfo ");
		Writer out= env.getOut();
		for(VarietyInfo o:list){
			out.append("<option value='"+o.getInt("id")+"'>");
			out.append(o.getStr("variety_name"));
			out.append("</option>");
		}
	}
}
