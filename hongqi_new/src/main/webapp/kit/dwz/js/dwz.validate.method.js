/**
 * @requires jquery.validate.js
 * @author ZhangHuihua@msn.com
 */
(function($){
	if ($.validator) {
		$.validator.addMethod("password", function(value, element) {
			return this.optional(element) || /^[0-9a-zA-z_]{6,}$/.test(value);
		}, "请输入六位以上的数字或字母");
		$.validator.addMethod("alphanumeric", function(value, element) {
			return this.optional(element) || /^\w+$/i.test(value);
		}, "Letters, numbers or underscores only please");
		
		$.validator.addMethod("lettersonly", function(value, element) {
			return this.optional(element) || /^[a-z]+$/i.test(value);
		}, "Letters only please"); 
		
		$.validator.addMethod("telephone", function(value, element) {
			return this.optional(element) || /^\d{3}-\d{8}|\d{4}-\d{7}$/.test(value);
		}, "区号-电话号码");
		
		
		
		$.validator.addMethod("ip", function(value, element) {
			return this.optional(element) || /^(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])$/.test(value);
		},"请输入正确的IP地址");
		
		$.validator.addMethod("mac", function(value, element) {
			return this.optional(element) || /^[A-F0-9]{2}(-[A-F0-9]{2}){5}$/.test(value);
		},"请输入正确的MAC地址");
		
		$.validator.addMethod("postcode", function(value, element) {
			return this.optional(element) || /^[1-9][0-9]{5}$/.test(value);
		}, "请输入不为0开头的六位数字");
		
		$.validator.addMethod("nosuply", function(value, element) {
			return this.optional(element) || /^\d{9}$/.test(value);
		},"请输入9位数字");
		
		$.validator.addMethod("date", function(value, element) {
			value = value.replace(/\s+/g, "");
			if (String.prototype.parseDate){
				var $input = $(element);
				var pattern = $input.attr('dateFmt') || 'yyyy-MM-dd';
	
				return !$input.val() || $input.val().parseDate(pattern);
			} else {
				return this.optional(element) || value.match(/^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/);
			}
		}, "Please enter a valid date.");
		
		/*自定义js函数验证
		 * <input type="text" name="xxx" customvalid="xxxFn(element)" title="xxx" />
		 */
		$.validator.addMethod("customvalid", function(value, element, params) {
			try{
				return eval('(' + params + ')');
			}catch(e){
				return false;
			}
		}, "Please fix this field.");
		
		$.validator.addClassRules({
			date: {date: true},
			alphanumeric: { alphanumeric: true },
			lettersonly: { lettersonly: true },
			phone: { phone: true },
			postcode: {postcode: true}
		});
		$.validator.setDefaults({errorElement:"span"});
		$.validator.autoCreateRanges = true;
		
	}

})(jQuery);