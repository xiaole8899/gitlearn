package org.dppc.cardws.frame.card.exp;


/**
 * 读写卡异常基类 - 主要是给本地调用使用
 * 
 * @author maomh
 *
 */
public class CardException extends Exception {
	private static final long serialVersionUID = 6567679668834754180L;

	public CardException(String errorMsg) {
		super(errorMsg);
		/*try {
			byte[] bUtf =errorMsg.getBytes("UTF-8");
			byte[] bGbk =errorMsg.getBytes("GBK");
			byte[] bIso =errorMsg.getBytes("ISO-8859-1");
			System.out.println("UTF8 : " +bUtf.length);
			System.out.println("GBK : " +bGbk.length);
			System.out.println("ISO : " +bIso.length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
	}
	
	
}
