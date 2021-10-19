package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import controller.Controller;
import controller.ControllerImpl;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import pieces.Pieces;

public class Main extends Application {
	private Controller controller = new ControllerImpl();
	private boolean gameOver = false;
	private Tile[][] tiles = new Tile[6][7];
	private Text gameOverText = new Text();
	private Image titleScreenImage;
	private boolean buttonOnePlayerClicked;
	private boolean buttonTwoPlayerClicked;

	private Parent createContent(Stage primaryStage) {
		GridPane gameWithText = new GridPane();
		GridPane board = new GridPane();

		HBox buttons = new HBox();
		VBox boardWithButtons = new VBox();
		board.setGridLinesVisible(true);
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 7; c++) {
				tiles[r][c] = new Tile(Color.WHITE, primaryStage);
				board.add(tiles[r][c], c, r);
			}
		}

		gameOverText.scaleXProperty().bind(primaryStage.widthProperty().divide(800));
		gameOverText.scaleYProperty().bind(primaryStage.heightProperty().divide(800));

		gameOverText.translateXProperty().bind(primaryStage.widthProperty().divide(6));
		gameOverText.translateYProperty().bind(primaryStage.heightProperty().divide(36));
		for (int i = 0; i < 7; i++) {
			buttons.getChildren().add(new TopButtons(i, primaryStage));
		}
		boardWithButtons.getChildren().addAll(board, buttons);
		gameWithText.add(boardWithButtons, 0, 0, 4, 4);
		gameWithText.add(gameOverText, 0, 0, 4, 4);
		return gameWithText;

	}

	private Parent createTitleScreen(Stage primaryStage) throws FileNotFoundException {
		titleScreenImage = new Image(new FileInputStream("Images/ConnectFour.png"));
		ImageView titleScreen = new ImageView(titleScreenImage);
		GridPane title = new GridPane();
		title.add(titleScreen, 0, 0);
		TitleButton onePlayer = new TitleButton(0, primaryStage);
		onePlayer.setText("One Player");
		TitleButton twoPlayer = new TitleButton(1, primaryStage);
		twoPlayer.setText("Two Player");
		title.add(onePlayer, 0, 0);
		title.add(twoPlayer, 0, 0);
		onePlayer.translateXProperty().bind(primaryStage.widthProperty().divide(1.5));
		onePlayer.translateYProperty().bind(primaryStage.heightProperty().subtract(985));
		twoPlayer.translateXProperty().bind(primaryStage.widthProperty().divide(1.5));
		twoPlayer.translateYProperty().bind(primaryStage.heightProperty().subtract(880));
		return title;
	}

	private class TitleButton extends Button {
		int button;

		public TitleButton(int button, Stage primaryStage) {
			this.button = button;
			this.prefWidthProperty().bind(primaryStage.widthProperty().divide(3));
			this.prefHeightProperty().bind(primaryStage.heightProperty().divide(12));
			this.setOnMouseClicked(event -> {
				if (button == 0) {
					buttonOnePlayerClicked = true;
				} else if (button == 1) {
					buttonTwoPlayerClicked = true;
				}

				Scene scene = new Scene(createContent(primaryStage));
				primaryStage.setScene(scene);
				primaryStage.show();
			});
		}
	}

	private class TopButtons extends Button {
		private int column;

		public TopButtons(int column, Stage primaryStage) {
			this.column = column;
			this.setTextAlignment(TextAlignment.CENTER);
			this.setText("Place Piece\n Here");
			this.prefWidthProperty().bind(primaryStage.widthProperty().divide(7));
			this.prefHeightProperty().bind(primaryStage.heightProperty().divide(7));

			this.setOnMouseClicked(event -> {
				int placePiece;
				if (buttonTwoPlayerClicked) {
					if (controller.getTurns() % 2 == 0) {
						placePiece = controller.placePiece(column, Pieces.Color.RED);
						if (placePiece != -1 && !gameOver) {
							tiles[placePiece][column].circle.setStroke(Color.RED);
							System.out.println(placePiece + " " + column);
							controller.incrementTurns();
							if (controller.pieceEndsGame(placePiece, column)) {
								gameOver = true;
								System.out.println("Game Over. Red Wins!");
								gameOverText.setText("GAME OVER");
								gameOverText.setStroke(Color.BLACK);
								gameOverText.setStrokeWidth(5);
								gameOverText.setFont(new Font(100));
								gameOverText.setFill(Color.RED);
							}
						}
					} else {
						placePiece = controller.placePiece(column, Pieces.Color.YELLOW);
						if (placePiece != -1 && !gameOver) {
							tiles[placePiece][column].circle.setStroke(Color.YELLOW);
							System.out.println(placePiece + " " + column);
							controller.incrementTurns();
							if (controller.pieceEndsGame(placePiece, column)) {
								gameOver = true;
								System.out.println("Game Over. Yellow Wins!");
								gameOverText.setText("GAME OVER");
								gameOverText.setStroke(Color.BLACK);
								gameOverText.setStrokeWidth(5);
								gameOverText.setFont(new Font(100));
								gameOverText.setFill(Color.YELLOW);
							}
						}
					}
				} else {
					if (controller.getTurns() % 2 == 0) {
						placePiece = controller.placePiece(column, Pieces.Color.RED);
						if (placePiece != -1 && !gameOver) {
							tiles[placePiece][column].circle.setStroke(Color.RED);
							System.out.println(placePiece + " " + column);
							if (controller.pieceEndsGame(placePiece, column)) {
								gameOver = true;
								System.out.println("Game Over. Red Wins!");
								gameOverText.setText("GAME OVER");
								gameOverText.setStroke(Color.BLACK);
								gameOverText.setStrokeWidth(5);
								gameOverText.setFont(new Font(100));
								gameOverText.setFill(Color.RED);
							}
							controller.incrementTurns();

							int aiColumn = controller.aiPlacePiece(Pieces.Color.YELLOW);
							placePiece = controller.placePiece(aiColumn, Pieces.Color.YELLOW);
							if (placePiece != -1 && !gameOver) {
								tiles[placePiece][aiColumn].circle.setStroke(Color.YELLOW);
								System.out.println(placePiece + " " + aiColumn);
								if (controller.pieceEndsGame(placePiece, aiColumn)) {
									gameOver = true;
									System.out.println("Game Over. Yellow Wins!");
									gameOverText.setText("GAME OVER");
									gameOverText.setStroke(Color.BLACK);
									gameOverText.setStrokeWidth(5);
									gameOverText.setFont(new Font(100));
									gameOverText.setFill(Color.YELLOW);
								}
								controller.incrementTurns();
							}
						}
					}
				}
			});
		}
	}

	private class Tile extends StackPane {
		private Color color;
		private Circle circle = new Circle();
		private Rectangle border = new Rectangle();
		// private Circle indicator = new Circle(50, 50, 20, null);

		public Tile(Color color, Stage primaryStage) {

			circle.scaleXProperty().bind(primaryStage.widthProperty().divide(9));
			circle.scaleYProperty().bind(primaryStage.heightProperty().divide(9));
			border.widthProperty().bind(primaryStage.widthProperty().divide(7.08));
			border.heightProperty().bind(primaryStage.heightProperty().divide(7));
			prefWidthProperty().bind(primaryStage.widthProperty().divide(7));
			prefHeightProperty().bind(primaryStage.heightProperty().divide(7));
//			border.widthProperty().bind(primaryStage.widthProperty().multiply(.08));
//			border.heightProperty().bind(primaryStage.heightProperty().multiply(.1));
			this.color = color;
			border.setFill(Color.DARKBLUE);
			circle.setStroke(color);
			getChildren().addAll(border, circle);

		};
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			Scene titleScene = new Scene(createTitleScreen(primaryStage));

			primaryStage.setMinHeight(700);
			primaryStage.setMinWidth(800);
			primaryStage.setMaxHeight(700);
			primaryStage.setMaxWidth(800);
			primaryStage.setScene(titleScene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
