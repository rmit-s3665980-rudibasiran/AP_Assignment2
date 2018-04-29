package AP_Assignment2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class newMenu extends Application {
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		double SCENEWIDTH = 2048;
		double SCENEHEIGHT = 1536;

		Pane pane = new Pane();

		int startX = 5;
		int startY = 5;
		int menuStartY = 10;

		int rectWidth = 200;
		int rectHeight = 50;
		int rectOffset = 60;

		Rectangle backRectangle = new Rectangle(0, 0, SCENEWIDTH, SCENEHEIGHT);
		backRectangle.setFill(Helper.menuBackColor);
		pane.getChildren().add(backRectangle);

		Rectangle brandRectangle = new Rectangle();
		brandRectangle.setFill(Helper.menuRectColor);
		brandRectangle.setStroke(Helper.menuRectBorder);
		brandRectangle.setHeight(rectHeight);
		brandRectangle.setWidth(rectWidth * 7);
		// brandRectangle.setEffect(ds);
		Helper.doRectEffect(brandRectangle);

		Label brandLabel = new Label("R.Net");
		brandLabel.setTextFill(Helper.menuTextColor);

		StackPane brandPane = new StackPane();
		brandPane.setLayoutX(startX);
		brandPane.setLayoutY(startY);

		brandPane.getChildren().addAll(brandRectangle, brandLabel);

		pane.getChildren().add(brandPane);

		String[] _menuItems = new String[Helper.menuSize];

		for (int i = 0; i < Helper.menuSize; i++)
			_menuItems[i] = Helper.menuDesc[i];

		Rectangle menuRectangle[] = new Rectangle[Helper.menuSize];
		Label menuLabel[] = new Label[Helper.menuSize];
		StackPane menuPane[] = new StackPane[Helper.menuSize];

		int i;
		for (i = 0; i < _menuItems.length; i++) {
			menuRectangle[i] = new Rectangle();
			menuRectangle[i].setFill(Helper.menuRectColor);
			menuRectangle[i].setStroke(Helper.menuRectBorder);
			menuRectangle[i].setHeight(rectHeight);
			menuRectangle[i].setWidth(rectWidth);
			Helper.doRectEffect(menuRectangle[i]);

			menuLabel[i] = new Label();
			menuLabel[i].setTextFill(Helper.menuTextColor);

			menuLabel[i].setMaxWidth(Double.MAX_VALUE);
			menuLabel[i].setAlignment(Pos.CENTER_LEFT);
			menuLabel[i].setText("     " + _menuItems[i]);

			menuPane[i] = new StackPane();
			menuPane[i].getChildren().addAll(menuRectangle[i], menuLabel[i]);
			menuPane[i].setLayoutX(startX);

			if (i == 0)
				menuPane[i].setLayoutY(menuStartY + (_menuItems.length * rectOffset));
			else
				menuPane[i].setLayoutY(menuStartY + (i * rectOffset));

			pane.getChildren().add(menuPane[i]);

		}

		Scene scene = new Scene(pane, SCENEWIDTH, SCENEHEIGHT);
		primaryStage.setTitle("MiniNET"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}

	public static void main(String[] args) {
		launch(args);
	}
}
