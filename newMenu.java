package AP_Assignment2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
		int menuStartY = 5;

		int rectOffset = 55;

		Rectangle backRectangle = new Rectangle(0, 0, SCENEWIDTH, SCENEHEIGHT);
		backRectangle.setFill(Helper.menuBackColor);
		pane.getChildren().add(backRectangle);

		Rectangle brandRectangle = new Rectangle();
		brandRectangle.setFill(Helper.menuRectColor);
		brandRectangle.setStroke(Helper.menuRectBorder);
		brandRectangle.setHeight(Helper.rectHeight);
		brandRectangle.setWidth(Helper.rectWidth * 7);
		Helper.doRectEffect(brandRectangle);

		Label brandLabel = new Label("R.Net");
		brandLabel.setTextFill(Helper.menuRectTextColor);

		StackPane brandPane = new StackPane();
		brandPane.setLayoutX(startX);
		brandPane.setLayoutY(startY);

		brandPane.getChildren().addAll(brandRectangle, brandLabel);

		pane.getChildren().add(brandPane);

		String[] _menuItems = new String[Helper.menuSize];

		for (int i = 0; i < Helper.menuSize; i++)
			_menuItems[i] = Helper.menuDesc[i];

		for (int i = 0; i < _menuItems.length; i++) {

			Rectangle menuRectangle = new Rectangle();
			Label menuLabel = new Label();
			StackPane menuPane = new StackPane();

			menuRectangle.setFill(Helper.menuRectColor);
			menuRectangle.setStroke(Helper.menuRectBorder);
			menuRectangle.setHeight(Helper.rectHeight);
			menuRectangle.setWidth(Helper.rectWidth);
			Helper.doRectEffect(menuRectangle);

			menuLabel.setTextFill(Helper.menuRectTextColor);
			menuLabel.setMaxWidth(Double.MAX_VALUE);
			menuLabel.setAlignment(Pos.CENTER_LEFT);
			menuLabel.setText(Helper.spaces + _menuItems[i]);

			menuPane.getChildren().addAll(menuRectangle, menuLabel);

			menuPane.setLayoutX(startX);

			if (i == 0)
				menuPane.setLayoutY(menuStartY + (_menuItems.length * rectOffset));
			else
				menuPane.setLayoutY(menuStartY + (i * rectOffset));

			// add shadow when mouse over
			menuPane.addEventHandler(MouseEvent.MOUSE_ENTERED, (event) -> Helper.rectMouseEntered(menuPane));

			// removing the shadow when the mouse cursor is off
			menuPane.addEventHandler(MouseEvent.MOUSE_EXITED, (event) -> Helper.rectMouseExited(menuPane));

			// darken shadow on click
			menuPane.addEventHandler(MouseEvent.MOUSE_PRESSED, (event) -> Helper.rectMousePressed(menuPane));

			// restore hover style on click end
			menuPane.addEventHandler(MouseEvent.MOUSE_RELEASED, (event) -> Helper.rectMouseExited(menuPane));

			pane.getChildren().add(menuPane);
		}

		Rectangle bottomRectangle = new Rectangle();
		bottomRectangle.setFill(Helper.menuRectColor);
		bottomRectangle.setStroke(Helper.menuRectBorder);
		bottomRectangle.setHeight(Helper.rectHeight * 4.5);
		bottomRectangle.setWidth(Helper.rectWidth);
		bottomRectangle.setLayoutY(menuStartY + ((_menuItems.length + 1) * rectOffset));
		bottomRectangle.setLayoutX(startX);
		Helper.doRectEffect(bottomRectangle);
		pane.getChildren().add(bottomRectangle);

		Scene scene = new Scene(pane, SCENEWIDTH, SCENEHEIGHT);
		primaryStage.setTitle("R.Net"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}

	public static void main(String[] args) {
		launch(args);
	}
}
