package com.max.tse.guava.utils;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static final Logger LOG = LoggerFactory
			.getLogger(DateUtil.class);
	// 格式：年－月－日 小时：分钟：秒
	public static final String FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";

	// 格式：年－月－日 小时：分钟
	public static final String FORMAT_TWO = "yyyy-MM-dd HH:mm";

	// 格式：年月日 小时分钟秒
	public static final String FORMAT_THREE = "yyyyMMdd-HHmmss";
	
	// 格式：年月日小时分钟秒
	public static final String FORMAT_FOUR = "yyyyMMddHHmmss";
	
	// 格式：月日小时分钟
	public static final String FORMAT_FIVE = "MM-dd HH:mm";

	// 格式：年－月－日
	public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";

	// 格式：年月日
	public static final String EIGHT_STYLE_DATE_FORMAT = "yyyyMMdd";

	// 格式：月－日
	public static final String SHORT_DATE_FORMAT = "MM-dd";

	// 格式：小时：分钟：秒
	public static final String LONG_TIME_FORMAT = "HH:mm:ss";

	// 格式：年-月
	public static final String MONTG_DATE_FORMAT = "yyyy-MM";
	
	// 格式: 年/月
	public static final String MONTH_DATE_FORMAT_2 = "yyyy/MM";

	// 年的加减
	public static final int SUB_YEAR = Calendar.YEAR;

	// 月加减
	public static final int SUB_MONTH = Calendar.MONTH;

	// 天的加减
	public static final int SUB_DAY = Calendar.DATE;

	// 小时的加减
	public static final int SUB_HOUR = Calendar.HOUR;

	// 分钟的加减
	public static final int SUB_MINUTE = Calendar.MINUTE;

	// 秒的加减
	public static final int SUB_SECOND = Calendar.SECOND;
	
	// 格式：年月日小时分钟秒
	public static final String FORMAT_Five_New = "yyyyMMddHHmmssSS";

	static final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四",
			"星期五", "星期六" };

	@SuppressWarnings("unused")
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat(
			FORMAT_ONE);

	public DateUtil() {
	}

	/**
	 * 把符合日期格式的字符串转换为日期类型
	 */
	public static Date toDate(String dateStr) {
		Date d = null;
		SimpleDateFormat formater = new SimpleDateFormat(FORMAT_ONE);
		try {
			formater.setLenient(false);
			d = formater.parse(dateStr);
		} catch (Exception e) {
			d = null;
		}
		return d;
	}

	public static Date toDate(String dateStr, String format) {
		Date d = null;
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			formater.setLenient(false);
			d = formater.parse(dateStr);
		} catch (Exception e) {
			d = null;
		}
		return d;
	}

	/**
	 * 把日期转换为字符串
	 *
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date) {
		String result = "";
		SimpleDateFormat formater = new SimpleDateFormat(FORMAT_ONE);
		try {
			result = formater.format(date);
		} catch (Exception e) {
			// log.error(e);
		}
		return result;
	}

	public static String formatDateTime(Date date, String format) {
		String result = "";
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			result = formater.format(date);
		} catch (Exception e) {
			// log.error(e);
		}
		return result;
	}

	/**
	 * 获取当前时间的指定格式
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrDate(String format) {
		return formatDateTime(new Date(), format);
	}

	/**
	 * 两个日期相减
	 * @param firstTime
	 * @param secTime
	 * @return 相减得到的分钟数
	 */
	public static long timeSubMinute(Date firstTime, Date secTime) {
		long first = firstTime.getTime();
		long second = secTime.getTime();
		return (second - first) / 60000;
	}
	
	/**
	 * 两个日期相减
	 * @param firstTime
	 * @param secTime
	 * @return 相减得到的天数
	 */
	public static long timeSubDay(Date firstTime, Date secTime) {
		long first = firstTime.getTime();
		long second = secTime.getTime();
		return (second - first) / 86400000;
	}
	
	/**
	 * 取得指定日期过 months 月后的日期 (当 months 为负数表示指定月之前);
	 * @param date 日期 为null时表示当天
	 * @param months 相加(相减)的月数
	 */
	public static Date nextMonth(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}
	
	/**
	 * 得到指定时间day之后的日期
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date nextDay(Date date, int day){
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}


    public static Date nextMinute(Date date, int minute){
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();
    }

    //yyyy-MM-dd -> yyyyMMdd
    public static String processSearchTime(String date) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(date));
        Preconditions.checkArgument(date.length() == 10, "日期参数不合法");
        return new StringBuilder(date.substring(0, 4)).append(date.substring(5, 7)).append(date.substring(8, 10)).toString();

    }

}