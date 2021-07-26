package com.elec.view;

public enum Menu {
	ManageAccount("Quản lý tài khoản"),
	Home("Trang chủ"),
	HomeAdmin("Trang chủ Admin"),
	AddHouseHold("Quản lý hộ gia đình"),
	ManageBill("Quản lý hóa đơn");
	
	
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
