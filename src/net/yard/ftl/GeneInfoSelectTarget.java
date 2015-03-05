package net.yard.ftl;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import net.yard.model.GeneInfo;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateNumberModel;

public class GeneInfoSelectTarget extends BaseTarget {

	public static String targetName="geneInfoSelect";
	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Environment env, Map args, TemplateModel[] arg2,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		List<GeneInfo> list= GeneInfo.dao.find("select id,gene_type from geneinfo ");
		Writer out= env.getOut();
		for(GeneInfo o:list){
			out.append("<option value='"+o.getInt("id")+"'>");
			out.append(o.getStr("gene_type"));
			out.append("</option>");
		}
	}
}
