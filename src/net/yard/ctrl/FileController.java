package net.yard.ctrl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import net.yard.jFinal.anatation.PowerBind;
import net.yard.jFinal.anatation.RouteBind;

import com.google.gson.Gson;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

@RouteBind(path = "/file")
public class FileController extends Controller {
	private static String uploadroot = "/upload/";
	private static String root;
	//图片扩展名
	String[] imgFileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
	String[] mediaFileTypes= new String[]{"avi", "flv", "mp4"};
	/**单个或多个文件上传      swfupload*/
	@SuppressWarnings("deprecation")
	@PowerBind
	public void up() {
		String savefilename="";
		StringBuffer files=new StringBuffer();
		List<UploadFile> upfilelist = this.getFiles();
		Map<String,Object> jsonMap=new HashMap<String,Object>();
		jsonMap.put("error",0);
		if(upfilelist!=null&&upfilelist.isEmpty()==false){
			if(root==null)
				root=this.getRequest().getContextPath();
			String realPath = this.getRequest().getRealPath("/");
			int fsize=upfilelist.size();
			int i=1;
			for(UploadFile upfile:upfilelist){
				File file = upfile.getFile();
				String filedataFileName = upfile.getOriginalFileName();
				String filetype=filedataFileName.substring(filedataFileName.lastIndexOf(".")+1).toLowerCase();
				String subdir=this.getPara("dir");
				if(subdir==null||"".equals(subdir)){
					subdir="file";
				}
					File dir=new File(realPath +uploadroot+subdir);
					if(!dir.exists()){
						dir.mkdirs();
					}
				
				savefilename =  new Date().getTime()+"."+filetype ;
				if(file != null){
					file.renameTo(new File(realPath +uploadroot+subdir+"/"+ savefilename));
					files.append(root+uploadroot+subdir+"/"+savefilename);
					if(i<fsize)
						files.append(",");
				}
				i++;
			}
			jsonMap.put("url",files);
		}else{
			jsonMap.put("error",1);
			jsonMap.put("message","上传文件失败。");
		}
		this.renderJson(new Gson().toJson(jsonMap));
	}
	
	/**文件下载 */
	@PowerBind
	public void down() {
		
	}
	public void manager(){
		
	}
}
