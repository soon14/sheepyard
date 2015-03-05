package net.yard.ftl;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import net.yard.model.TypeInfo;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateNumberModel;

public class TypeInfoSelectTarget extends BaseTarget {

	public static String targetName="typeInfoSelect";
	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Environment env, Map args, TemplateModel[] arg2,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		List<TypeInfo> list= TypeInfo.dao.find("select id,type_name from typeinfo ");
		Writer out= env.getOut();
		for(TypeInfo o:list){
			out.append("<option value='"+o.getInt("id")+"'>");
			out.append(o.getStr("type_name"));
			out.append("</option>");
		}
	}
}
