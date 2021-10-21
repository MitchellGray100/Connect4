module tester {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.media;
	
	opens application to javafx.graphics, javafx.fxml;
}
