/**
 * 拓展 JQuery 一个公共方法
 */

(function($) {
	var defaultBaseUrl ="http://127.0.0.1:8080/cardws/card";
	$.extend({
		/**
		 *  readuser(params);
		 */
		readuser : function(params) {
			var p ={url:defaultBaseUrl+"/read/user", callback:$.noop};
			// 若 params 是个函数，则直接认定为 callback 
			if ($.isFunction(params)) {
				p.callback =params;
			} else {
				// 否则，进行参数合并
				p =$.extend(p, params);
			}
			// 进行跨域回调
			$.ajax({
				url:p.url,
				dataType:"jsonp",
				jsonp:"callback",
				success:p.callback
			});
		},
		//params 函数  info 存放用户数据的对象
		writeuser:function(params,info){
			var p ={url:defaultBaseUrl+"/write/user", callback:$.noop};
			// 若 params 是个函数，则直接认定为 callback 
			if ($.isFunction(params)) {
				p.callback =params;
			} else {
				// 否则，进行参数合并
				p =$.extend(p, params);
			}
			// 进行跨域回调
			$.ajax({
				url:p.url,
				dataType:"jsonp",
				jsonp:"callback",
				success:p.callback,
				data:{
					'bizNo':info.bizNo,
					'cardType':info.cardType,
					'cardNumber':info.cardNumber,
					'phone':info.phone,
					'identityCard':info.identityCard,
					'bizName':info.bizName,
					'userType':info.userType,
					'mainName':info.mainName
				}
			});
		}
	});
})(jQuery);