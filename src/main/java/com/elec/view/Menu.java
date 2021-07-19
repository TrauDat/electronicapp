package com.elec.view;

public enum Menu {
	Home("Trang chủ"),
	AddHouseHold("Quản lý hộ gia đình"),
	ListHouseHold("Danh sách hộ gia đình");
	
	private String title;
	
	Menu(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getFxml() {
		return String.format("/fxml/%s.fxml", name());
	}
}