$(function(){
	var appRest=$(".resetForm").click(function(){
		//获取文本框清空
		$(".checkReset").each(function(){
			$(this).val("");
		});
		
		//获取下拉框清空
		$(".checkResetSelect").each(function(){
			$(this).val("请选择");
		});
	});
});