var ajaxDialog;
function showlockdialog(){
	ajaxDialog=$.dialog({id:'ajaxDialog',
		title:false,
	    fixed: true,
	    lock:true,
	    content:'数据加载中...',
	    icon:'loading.gif'
	});
}
$(document).ajaxStart(showlockdialog);
$(document).ajaxComplete(function(event,request, settings){
	ajaxDialog.get('ajaxDialog',1).close();
});
          $("[rel=tooltip]").tooltip('show');
         $("#loginform").validate({
			rules:{
				userno:{required:true},
				password:{required:true},
				validCode:{required:true}
			},
			errorClass: "help-inline",
			errorElement: "label",
			highlight:function(element, errorClass, validClass) {
				$(element).parents('.control-group').removeClass('success').addClass('error');
			},
			unhighlight: function(element, errorClass, validClass) {
				$(element).parents('.control-group').removeClass('error').addClass('success');
			},
			submitHandler:function (){
	        	$.ajax({url:root+'/admin/login',dataType:'json',type:'POST',data:$("form").serialize(),success:function(msg){
	        		$("#codeimg").click();
	        		var content='欢迎登录后台管理系统！';
	        		if(msg.statusCode!="success"){
	        			content=msg.message;
	        		}
	        		$.dialog({content:"<h4>"+content+"</h4>",title:false,time:1,icon:msg.statusCode+'.gif',close:function(){
	        			if(msg.statusCode=="success")
	        				window.location.reload();
	        			var duration = 400, /*动画时长*/
	        	        api = this,
	        	        opt = api.config,
	        	        wrap = api.DOM.wrap,
	        	        top = $(window).scrollTop() - wrap[0].offsetHeight;
	        	    	wrap.animate({top:top + 'px', opacity:0}, duration, function(){
		        	        opt.close = function(){};
		        	        api.close();
	        	    	});
	        	    return false;
	        		}});
	        	},error:function(e){$("#codeimg").click();$.dialog({time:1,title:false,content:'<h4>登录异常！稍候再试！</h4>',icon:'error.gif',close:function(){
	        		var duration = 400, /*动画时长*/
        	        api = this,
        	        opt = api.config,
        	        wrap = api.DOM.wrap,
        	        top = $(window).scrollTop() - wrap[0].offsetHeight;
        	    	wrap.animate({top:top + 'px', opacity:0}, duration, function(){
	        	        opt.close = function(){};
	        	        api.close();
        	    	});
        	    return false;
	        	}});}});
	        	return false;
			}
		});