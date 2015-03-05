package net.yard.ctrl.admin;

import net.yard.interceptor.ManagerPowerInterceptor;
import net.yard.jFinal.anatation.PowerBind;
import net.yard.jFinal.anatation.RouteBind;
import net.yard.model.Manager;
import net.yard.util.safe.MD5;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;

@RouteBind(path = "/admin")
@Before({ ManagerPowerInterceptor.class })
public class AdminIndexCtrl extends BaseController {
	public void index() {
		Manager m=this.getCurrentUser();
		if(m!=null&&m.getLong("id")!=null&&m.getLong("id")!=0){
			this.render("main.html");
		}
	}
	@PowerBind
	public void desktop(){
		
	}
	public void login(){
		String userno=this.getPara("userno");
		String pwd=this.getPara("password");
		String code=this.getPara("validCode");
		String check= this.getSessionAttr("check");
		this.removeSessionAttr("check");
		
		if(userno==null||"".equals(userno.trim())||pwd==null||"".equals(pwd)||(code==null||"".equals(code))){
			this.toDwzJson("error", "信息填写不全！");
		}else
		if((check==null||"".equals(check))){
			this.toDwzJson("error", "验证码超时！");
		}else if((check!=null&&code!=null&&!check.equals(code.toLowerCase()))){
			this.toDwzJson("error", "验证码错误！");
		}else{
			//pwd=MD5.getMD5ofStr(pwd);
			Manager m=Manager.dao.findFirst("select * from manager where userno=? and pwd=?",new Object[]{userno,pwd});
			//this.setSessionAttr("Manager",m);
			//this.toDwzJson("success", "登录成功！");
			if(m!=null&&m.getLong("id")!=0){
				if(1==m.getInt("enable")){
					this.setSessionAttr("Manager",m);
					this.toDwzJson("success", "登录成功！");
				}else{
					this.toDwzJson("error", "此用户已经被冻结！");
				}
			}else{
				this.toDwzJson("error", "用户名或密码错误！");
			}
		}
	}
	public void logout(){
		this.setSessionAttr("Manager",null);
		this.redirect("/admin/");
	}
	@PowerBind
	public void setPwd(){}
	@PowerBind
	public void savePwd(){
		String oldpwd=this.getPara("oldpwd");
		String newpwd=this.getPara("newpwd");
		String reppwd=this.getPara("reppwd");
		if(oldpwd==null||"".equals(oldpwd)){
			this.toDwzJson("error", "旧密码不能为空！");
			return;
		}
		if(newpwd==null||"".equals(newpwd)){
			this.toDwzJson("error", "新密码不能为空！");
			return;
		}
		if(reppwd==null||"".equals(reppwd)){
			this.toDwzJson("error", "二次输入密码不能为空！");
			return;
		}
		if(!newpwd.equals(reppwd)){
			this.toDwzJson("error", "两次输入新密码不一致！");
			return;
		}
		Manager m=this.getCurrentUser();
		oldpwd=MD5.getMD5ofStr(oldpwd);
		if(!oldpwd.equals(m.getStr("pwd"))){
			this.toDwzJson("error", "旧密码错误！");
			return;
		}
		try{
			newpwd=MD5.getMD5ofStr(newpwd);
			Db.update("update manager set pwd=? where id=?", new Object[]{newpwd,m.getLong("id")});
			m.set("pwd",newpwd);
			this.setSessionAttr("Manager",m);
			this.toDwzJson("success", "修改密码成功！");
		}catch(Exception e){
			this.toDwzJson("error", "保存密码异常！");
		}
		
	}
}
