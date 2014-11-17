package com.gooseeker.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class Commons {
	public static String dateFormat(Date date,String pattern)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
}
