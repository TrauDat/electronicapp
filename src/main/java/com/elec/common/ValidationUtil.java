package com.elec.common;

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
	
}
