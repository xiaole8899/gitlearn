package org.dppc.cardws.frame.card.bean;


/**
 * 卡里面的 用户区域 映射类
 * 
 * @author maomh
 *
 */
public class CardUserInfo {
	
	// 卡类型 - RL/SC
	private String cardType;

	// 启用日期 - yyMMdd
	private String beginDate ="000101";
	// 有效日期 - yyMMdd
	private String validDate ="991231";

	// 卡号 - 10位
	private String cardNumber;

	// 主体编码 （商户备案号）
	private String bizNo;
	// 主体名称 （企业、个体名称） <= 20个汉字
	private String mainName;
	// 用户类型 （1代表买方,2代表卖方，3代表买卖方）
	private String userType;
	// 联系人 （商户名称 - 持卡人名称）
	private String bizName;
	// 身份证
	private String identityCard;
	// 电话（010-12345678 或 13021026875）
	private String phone;

	// 备用数据区 （32个字节 - 字符按编码自己算）- 暂时没用
	private String otherData;

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public String getMainName() {
		return mainName;
	}

	public void setMainName(String mainName) {
		this.mainName = mainName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOtherData() {
		return otherData;
	}

	public void setOtherData(String otherData) {
		this.otherData = otherData;
	}

}
