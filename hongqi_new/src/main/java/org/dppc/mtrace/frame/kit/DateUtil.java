package org.dppc.mtrace.frame.kit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * 时间类
 * @author hle
 *
 */
public class DateUtil {

	//时间格式
	public static final String DATETIMEFORMAR="yyyy-MM-dd HH:mm:ss";
	
	//日期格式
	public static final String DATEFORMAT="yyyy-MM-dd";
	
	
	/**
	 * 判断开始时间是否大于结束时间
	 * @param startDate 开始时间
	 * @param endDate 结束日期
	 * @param format  时间规则
	 * @return
	 * @throws ParseException
	 */
	public static boolean compareTwoDate(String startDate,String endDate,String format) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(sdf.parse(startDate).getTime()>sdf.parse(endDate).getTime()){
			return false;
		}
		return true;
	}
	
	/**根据2个日期计算间隔天数
	 * 
	 * @param startDate 开始日期
	 * @param endDate   结束日期
	 * @param format    时间规则
	 * @return
	 * @throws ParseException 
	 */
	public static int compareTwoDateDiff(String startDate,String endDate,String format) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(startDate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(endDate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);      
       return Integer.parseInt(String.valueOf(between_days));     
	}
	
	public static void main(String[] args) throws ParseException {
		int df=compareTwoDateDiff("2015-07-01","2015-07-18",DateUtil.DATEFORMAT);
		System.out.println(df);
	}
	
}
