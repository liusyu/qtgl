package qtgl.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateUtil {
	
	public final static String  DEFAULT_STANDARD_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public final static String  DEFAULT_SHORT_DATE_PATTERN = "yyyy-MM-dd";
	public final static String  DEFAULT_STRING_DATE_PATTERN = "yyyyMMdd";
	public final static String  DEFAULT_STRING_DATETIME_PATTERN = "yyyyMMddHHmmss";
	public final static String  DEFAULT_TIME_PATTERN = "HH:mm:ss";
	public final static String  DEFAULT_TIME_PATTERN_SF = "HH:mm";//时分
	public final static String  DEFAULT_YEAR_PATTERN = "yyyy";
	public final static String  DEFAULT_MONTH_PATTERN = "yyyy-MM";
	public final static String  NYR_PATTERN="yyyy年MM月dd日";//年月日
	public final static String  NY_PATTERN="yyyy年MM月";//年月
	public final static String  YR_PATTERN="MM月dd日";//月日
	
	public final static DateFormat  DEFAULT_STANDARD_DATE_FORMAT = new SimpleDateFormat(DEFAULT_STANDARD_DATE_PATTERN);
	public final static DateFormat  DEFAULT_SHORT_DATE_FORMAT = new SimpleDateFormat(DEFAULT_SHORT_DATE_PATTERN);
	public final static DateFormat  DEFAULT_STRING_DATE_FORMAT = new SimpleDateFormat(DEFAULT_STRING_DATE_PATTERN);
	public final static DateFormat  DEFAULT_STRING_DATETIME_FORMAT = new SimpleDateFormat(DEFAULT_STRING_DATETIME_PATTERN);
	public final static DateFormat  DEFAULT_TIME_FORMAT = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
	public final static DateFormat  DEFAULT_YEAR_FORMAT = new SimpleDateFormat(DEFAULT_MONTH_PATTERN);
	public final static DateFormat  DEFAULT_MONTH_FORMAT = new SimpleDateFormat(DEFAULT_MONTH_PATTERN);
	public final static DateFormat  NYR_FORMAT = new SimpleDateFormat(NYR_PATTERN);
	public final static DateFormat  NY_FORMAT = new SimpleDateFormat(NY_PATTERN);
	
	public static Date getNow() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}
	
	public static Date getToday() {
		return getStartDate(getNow());
	}
	
	public static Date getYesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		setStartDay(calendar);
		return calendar.getTime();
	}

	public static String getDateFormat(String strDate, String inPattern, String outPattern) {
		Date date = parseDate(strDate, inPattern);
		if(date != null) {
			return formatDate(date, outPattern);
		}
		return strDate;
	}

	public static Date getStartDate(Date date) {
    	if(date != null) {
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(date);
    		setStartDay(cal);
    		return cal.getTime();
    	}
    	
    	return null;
    }
	
	//year, month, day都是自然日期
	public static Date getDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, day);
		return cal.getTime();
	}

	public static Date getEndDate(Date date) {
    	if(date != null) {
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(date);
    		setEndDay(cal);
    		return cal.getTime();
    	}
    	
    	return null;
    }
	
	public static Calendar setStartDay(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }
	
	public static Calendar setEndDay(Calendar cal) {
    	cal = setStartDay(cal);
    	cal.add(Calendar.DAY_OF_MONTH, 1);
    	cal.add(Calendar.MILLISECOND, -1);
        return cal;
    }
	
    public static String formatDate(Date date, String pattern) {
    	return formatDate(date, new SimpleDateFormat(pattern));
    }
    
    public static String formatDate_en(Date date, String pattern) {
    	return formatDate(date, new SimpleDateFormat(pattern,Locale.ENGLISH));
    }
   
    
    private static String formatDate(Date date, DateFormat dateFormat) {
    	if(date != null) {
    		try {
                return dateFormat.format(date);
    		} catch (Exception e) {
    			return date.toString();
    		}
    	}
    	
    	return "";
    }
    
    public static String formatDate(Date date) {
		return formatDate(date, DEFAULT_STANDARD_DATE_PATTERN);
	}

    public static String formatShortDate(Date date) {
		return formatDate(date, DEFAULT_SHORT_DATE_PATTERN);
	}
    
    public static String formatDatatimeDate(Date date) {
		return formatDate(date, DEFAULT_STRING_DATETIME_PATTERN);
	}
    
    public static Date parseDate(String date, String pattern) {
		return parseDate(date, new SimpleDateFormat(pattern));
    }
    
    private static Date parseDate(String strDate, DateFormat dateFormat) {
		try {
			strDate=strDate.replace("/", "-");
			return dateFormat.parse(strDate);
		} catch (Exception e) {
			return null;
		}
	}
    
    public static Date parseDate(String date) {
    	return parseDate(date, DEFAULT_STANDARD_DATE_PATTERN);
    }
    
    public static Date parseShortDate(String date) {
    	return parseDate(date, DEFAULT_SHORT_DATE_PATTERN);
    }
    
    public static Calendar getCalendar(Calendar cal,int day) {
    	cal = setStartDay(cal);
    	cal.add(Calendar.DAY_OF_MONTH, day);
        return cal;
    }
    
    public static boolean isDate(String date, String pattern) {
    	try {
			DateFormat sdf = new SimpleDateFormat(pattern);
            sdf.parse(date);
		} catch (Exception e) {
			return false;
		}
		
		return true;
    }
    
    private static Date add(Calendar cal, int field, int amount) {
		cal.add(field, amount);
		return cal.getTime();
    }
    
    public static Date addNow(int field, int amount) {
    	return add(Calendar.getInstance(), field, amount);
    }
    
    public static Date add(Date date, int field, int amount) {
    	if(date != null) {
	    	Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return add(cal, field, amount);
    	}
    	
    	return null;
    }
    
    public static String add(String dateStr, int field, int amount) {
    	Date date = parseShortDate(dateStr);
    	date = add(date, field, amount);
    	return formatShortDate(date);
    }

	public static Date getBefore(String actualYear, String actualMonth, int beforeMonths, int day) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, Integer.parseInt(actualYear));
		cal.set(Calendar.MONTH, Integer.parseInt(actualMonth) - 1);	
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.add(Calendar.MONTH, -beforeMonths);
		return cal.getTime();
	}

	public static String getYearByLastMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		return String.valueOf(calendar.get(Calendar.YEAR));
	}

	public static String getLastMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		return formatDate(calendar.getTime(), "MM");
	}
	
	public static Date getStandardDate(String strDate) {
		return parseDate(strDate, DEFAULT_STANDARD_DATE_FORMAT);
	}
	
	public static Date getShortDate(String strDate) {
		return parseDate(strDate, DEFAULT_SHORT_DATE_FORMAT);
	}
	
	public static Date getYearDate(String strDate) {
		return parseDate(strDate, DEFAULT_YEAR_FORMAT);
	}
	
	public static Date getMonthDate(String strDate) {
		return parseDate(strDate, DEFAULT_MONTH_FORMAT);
	}
	
	public static String getStringStandard(Date date) {
		return formatDate(date, DEFAULT_STANDARD_DATE_FORMAT);
	}
	
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public static int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}
	
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH)+1;
	}
	
	/** 
     * 获取今天是几月
     * @return      1-12
     */ 
	public static int getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH)+1;
	}
	
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/** 
     * 获取今天是几号
     * @return      1-31
     */ 
	public static int getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DATE);
	}

	public static int intervalWeeks(Date date1, Date date2) {
    	long interval = date1.getTime() - date2.getTime();
    	return Long.valueOf(interval/604800000l).intValue();
    }
	
	
	public static Date getMonday(Date date){
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTimeInMillis( date.getTime() );
		calendar.set(Calendar.DAY_OF_WEEK , Calendar.MONDAY);
		return calendar.getTime();
	}
	
	/**
	 * 2016-11-15转换为2016年11月15日
	 * @param rq  日期
	 * @param en  是否为英文格式
	 * @return
	 */
	public static String Get_NYR(String rq,boolean en)
	{
		Date date=parseDate(rq);
		if(date==null)
		{
			
			date=parseShortDate(rq);
		}
		
		if(en)
		{
			return	formatDate_en(date, "MMMMM dd, yyyy");
			
		}
	return	formatDate(date, NYR_PATTERN);
	}
	/**
	 * 2016-11-15转换为2016年11月15日
	 * @param date
	 * @param en  是否为英文格式
	 * @return
	 */
	public static String Get_NYR(Date date,boolean en)
	{
		if(en)
		{
			return	formatDate_en(date, "MMMMM dd, yyyy");
			
		}	
	return	formatDate(date, NYR_PATTERN);
	}
	/**
	 * 2016-11转换为2016年11月
	 * @param rq
	 * @param en  是否为英文格式
	 * @return
	 */
	public static String Get_NY(String rq,boolean en)
	{
		Date date=parseDate(rq);// yyyy-MM-dd HH:mm:ss
		String YEAR="";
		String MONTH="";
		if(date==null)
		{
			date=parseShortDate(rq);// yyyy-MM-dd
		}
		if(date==null)
		{
			date=getYearDate(rq);//yyyy-MM
		}
		if(en)
		{
			return	formatDate_en(date, "MMMMM yyyy");
			
		}
		return	formatDate(date, NY_PATTERN);
	}
	 /**
     * 获取当前日期是星期几<br>
     * 
     * @param dt
     * @return 当前日期是星期几
     */
    public static int getWeekOfDate_int(Date dt) {
       // String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return w;
    }
    
	 /**
     * 获取当前日期是星期几<br>
     * 
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
	
    /**
     * 日期 加上指定的天数  得到新的日期
     * @param dt
     * @param day
     * @return
     */
    public static Date Date_Add_Day(Date dt,int day)
    {
    	Calendar c = Calendar.getInstance();  
        c.setTime(dt);  
        c.add(Calendar.DAY_OF_MONTH, day);// 今天+1天  
   
        return c.getTime();  
    }
    /**
     * 将时间戳转换为时间
     * @param s
     * @return
     */
    public static Date stampToDate(String s){
        long lt = new Long(s);
        Date date = new Date(lt*1000);
        return date;
    }
    
    /**
     * 获得5月3日
     * @param date
     * @return
     */
    public static String formatDate_YR(Date date) {
    	return formatDate(date, new SimpleDateFormat(YR_PATTERN));
    }
    /**
     * 获得时分 11:20
     * @param date
     * @return
     */
    public static String formatDatatimeDate_SF(Date date) {
		return formatDate(date, DEFAULT_TIME_PATTERN_SF);
	}
    
    
    /**
     * 获取两个日期之间的日期
     * @param start 开始日期
     * @param end 结束日期
     * @return 日期集合
     */
    private List<Date> getBetweenDates(Date start, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);
        
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }
}
