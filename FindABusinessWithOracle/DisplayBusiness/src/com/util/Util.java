/**
 * 
 */
package com.util;

import java.util.ArrayList;
import java.util.List;

import com.model.dao.BusinessCategoryDao;
import com.model.dao.BusinessDao;

/**
 * @author Sheen
 *
 */
public class Util {
	
	BusinessCategoryDao businessCategoryDao = new BusinessCategoryDao();
	public List<String> createDaysList(){
		List<String> days = new ArrayList<String>();
		days.add("Monday");
		days.add("Tuesday");
		days.add("Wednesday");
		days.add("Thursday");
		days.add("Friday");
		days.add("Saturday");
		days.add("Sunday");
		return days;
	}
	
	public List<String> createTimeList(){
		List<String> times = new ArrayList<String>();
		times.add("00:00");
		times.add("01:00");
		times.add("02:00");
		times.add("03:00");
		times.add("04:00");
		times.add("05:00");
		times.add("06:00");
		times.add("07:00");
		times.add("08:00");
		times.add("09:00");
		times.add("10:00");
		times.add("11:00");
		times.add("12:00");
		times.add("13:00");
		times.add("14:00");
		times.add("15:00");
		times.add("16:00");
		times.add("17:00");
		times.add("18:00");
		times.add("19:00");
		times.add("20:00");
		times.add("21:00");
		times.add("22:00");
		times.add("23:00");
		times.add("24:00");
		return times;
	}
	
	public List<String> getCategoryList(){
		List<String> categoryList = businessCategoryDao.getBusinessCategoryNames();
		return categoryList;
	}
}
