package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
	private Parent createContent(Stage primaryStage) {
		GridPane board = new GridPane();
		HBox buttons = new HBox();
		VBox boardWithButtons = new VBox();
		board.setHgap(0);
		board.setVgap(0);
		for(int r = 0; r < 6; r++)
		{
			for(int c = 0; c < 7; c++)
			{
				board.add(new Tile(Color.WHITE,primaryStage), c, r);
			}
		}
		board.prefWidthProperty().bind(primaryStage.widthProperty().divide(7).multiply(6));
		board.prefHeightProperty().bind(primaryStage.heightProperty().divide(6).multiply(5));
		for(int i = 0; i < 7; i++)
		{
			buttons.getChildren().add(new TopButtons(primaryStage));
		}
		boardWithButtons.getChildren().addAll(buttons, board);
		
		return boardWithButtons;
	
	}
	
	private class TopButtons extends Button {
		public TopButtons(Stage primaryStage)
		{
			this.prefWidthProperty().bind(primaryStage.widthProperty().divide(7));
			this.prefHeightProperty().bind(primaryStage.heightProperty().divide(6));
			
		}
	}
	private class Tile extends StackPane {
		private Color color;
		private Circle circle = new Circle(40);
		private Rectangle border = new Rectangle(100, 100);
	//	private Circle indicator = new Circle(50, 50, 20, null);

		public Tile(Color color, Stage primaryStage) {
			circle.scaleXProperty().bind(primaryStage.widthProperty().divide(700));
			circle.scaleYProperty().bind(primaryStage.heightProperty().divide(700));
			border.widthProperty().bind(primaryStage.widthProperty().divide(8));
			border.heightProperty().bind(primaryStage.heightProperty().divide(7));
			prefWidthProperty().bind(primaryStage.widthProperty().divide(8));
			prefHeightProperty().bind(primaryStage.heightProperty().divide(7));
//			border.widthProperty().bind(primaryStage.widthProperty().multiply(.08));
//			border.heightProperty().bind(primaryStage.heightProperty().multiply(.1));
			this.color = color;
			border.setFill(Color.DARKBLUE);
			border.setStroke(Color.DARKBLUE);
			circle.setFill(color);
			circle.setStroke(color);
			//indicator.setFill(Color.RED);
			setAlignment(Pos.CENTER);
			getChildren().addAll(border, circle);

		};
	}
	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new Scene(createContent(primaryStage));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
