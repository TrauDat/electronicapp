package com.elec.view;

import java.util.ResourceBundle;

public enum FxmlView {

	ADMIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("admin.title");
		}
		
		@Override
		public String getFxmlFile() {
			return "/fxml/Admin.fxml";
		}
	},
	USER {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("user.title");
		}
		
		@Override
		public String getFxmlFile() {
			return "/fxml/User.fxml";
		}
	},
	MAINFRAME {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("mainframe.title");
		}
		
		@Override
		public String getFxmlFile() {
			return "/fxml/MainFrame.fxml";
		}
	},
	MANAGEFRAME {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("manageFrame.title");
		}
		
		@Override
		public String getFxmlFile() {
			return "/fxml/ManagerFrame.fxml";
		}
	},
	HOUSEHOLDEDIT {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("householdedit.title");
		}
		
		@Override
		public String getFxmlFile() {
			return "/fxml/HouseHoldEdit.fxml";
		}
	},
	MANAGEACCOUNTEDIT {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("managebill.title");
		}
		
		@Override
		public String getFxmlFile() {
			return "/fxml/ManageAccountEdit.fxml";
		}
	},
	MANAGEBILL {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("householdedit.title");
		}
		
		@Override
		public String getFxmlFile() {
			return "/fxml/BillEdit.fxml";
		}
	}
	,
	DIALOG {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("dialog.title");
		}
		
		@Override
		public String getFxmlFile() {
			return "/fxml/Dialog.fxml";
		}
	},
	LOGIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("login.title");
		}
		
		@Override
		public String getFxmlFile() {
			return "/fxml/Login.fxml";
		}
	};
	
	
	public abstract String getTitle();
	public abstract String getFxmlFile();
	
	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
