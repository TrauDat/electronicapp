package com.elec.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ValidationUtil {

	public static boolean validate(String field, String value, String pattern) {
		if (!value.isEmpty()) {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(value);
			if (m.find() && m.group().equals(value)) {
				return true;
			} else {
				validationAlert(field, false);
				return false;
			}
		} else {
			validationAlert(field, true);
			return false;
		}
	}

	public static boolean emptyValidation(String field, boolean empty) {
		if (!empty) {
			return true;
		} else {
			validationAlert(field, true);
			return false;
		}
	}
	
	public static boolean isEmpty(String string) {
	    return string == null || string.trim().isEmpty();
	}

	public static void validationAlert(String field, boolean empty) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Validation Error");
		alert.setHeaderText(null);
		if (field.equals("Role"))
			alert.setContentText("Vui lòng chọn " + field);
		else {
			if (empty)
				alert.setContentText("Vui lòng nhập " + field);
			else
				alert.setContentText("Vui lòng nhập " + field + " hợp lệ");
		}
		alert.showAndWait();
	}
	
	
	public static void validateField(String content) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Validation Error");
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	public static boolean validationDate(String toDate, String fromDate) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
		Date firstDate = sdf.parse(toDate);
		Date secondDate = sdf.parse(fromDate);
		
		long diffinMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
		long diff = TimeUnit.DAYS.convert(diffinMillies, TimeUnit.MILLISECONDS);
		
		if (diff < 0) {
			validateField("Ngày kết thúc phải lớn hơn ngày bắt đầu");
			return false;
		}
		
		return true;
	}
	
}
