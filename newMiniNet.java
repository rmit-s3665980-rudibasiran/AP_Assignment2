package AP_Assignment2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class newMiniNet extends Application {
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		double SCENEWIDTH = 2048;
		double SCENEHEIGHT = 1536;

		Pane pane = new Pane();

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

		Label brandLabel = new Label("MiniNet");
		brandLabel.setStyle(Helper.lblStyle);
		brandLabel.setEffect(Helper.dropShadow());

		StackPane brandPane = new StackPane();
		brandPane.setLayoutX(Helper.startX);
		brandPane.setLayoutY(Helper.startY);

		brandPane.getChildren().addAll(brandRectangle, brandLabel);

		pane.getChildren().add(brandPane);

		// work window begin
		GridPane workPane = new GridPane();
		Rectangle workWindow = new Rectangle(0, 0, Helper.workWidth, Helper.workHeight);
		workWindow.setEffect(Helper.dropShadow());
		workWindow.setFill(Helper.menuBackColor);

		workWindow.setFill(Helper.menuRectColor);
		workWindow.setStroke(Helper.menuRectBorder);
		workWindow.setHeight(Helper.rectHeight * 15);
		workWindow.setWidth(Helper.rectWidth * 5.75);
		workWindow.setId("workRectangle");
		Helper.doRectEffect(workWindow);

		workPane.add(workWindow, 0, 0);

		workPane.setVisible(false);
		workPane.setLayoutX(Helper.rectWidth + 20);
		workPane.setLayoutY(Helper.startY + Helper.rectHeight + 20);
		pane.getChildren().add(workPane);

		// work window ends

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
			menuRectangle.setId(_menuItems[i]);
			Helper.doRectEffect(menuRectangle);

			menuLabel.setTextFill(Helper.menuRectTextColor);
			menuLabel.setMaxWidth(Double.MAX_VALUE);
			menuLabel.setAlignment(Pos.CENTER_LEFT);
			menuLabel.setText(Helper.spaces + _menuItems[i]);

			menuPane.getChildren().addAll(menuRectangle, menuLabel);

			menuPane.setLayoutX(Helper.startX);

			if (i == 0)
				menuPane.setLayoutY(Helper.menuStartY + (_menuItems.length * rectOffset));
			else
				menuPane.setLayoutY(Helper.menuStartY + (i * rectOffset));

			// add shadow when mouse over
			menuPane.addEventHandler(MouseEvent.MOUSE_ENTERED, (event) -> Helper.rectMouseEntered(menuPane));

			// removing the shadow when the mouse cursor is off
			menuPane.addEventHandler(MouseEvent.MOUSE_EXITED, (event) -> Helper.rectMouseExited(menuPane));

			// darken shadow on click
			menuPane.addEventHandler(MouseEvent.MOUSE_PRESSED, (event) -> Helper.rectMousePressed(menuPane));

			// restore hover style on click end
			menuPane.addEventHandler(MouseEvent.MOUSE_RELEASED,
					(event) -> menuAction(primaryStage, workPane, menuRectangle.getId()));

			pane.getChildren().add(menuPane);
		}

		Rectangle bottomRectangle = new Rectangle();
		bottomRectangle.setFill(Helper.menuRectColor);
		bottomRectangle.setStroke(Helper.menuRectBorder);
		bottomRectangle.setHeight(Helper.rectHeight * 4.5);
		bottomRectangle.setWidth(Helper.rectWidth);
		bottomRectangle.setLayoutY(Helper.menuStartY + ((_menuItems.length + 1) * rectOffset));
		bottomRectangle.setLayoutX(Helper.startX);
		Helper.doRectEffect(bottomRectangle);
		pane.getChildren().add(bottomRectangle);

		Scene scene = new Scene(pane, SCENEWIDTH, SCENEHEIGHT);
		primaryStage.setTitle("MiniNet"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}

	public void menuAction(Stage primaryStage, GridPane wp, String menuClicked) {

		if (menuClicked.equals(Helper.menuDesc[Helper.quitMenu])) {
			primaryStage.close();
		} else {

			wp.setVisible(true);

			// menuRectangle.setId(_menuItems[i]);
			Alert alert = new Alert(AlertType.INFORMATION, menuClicked, ButtonType.CLOSE);
			alert.showAndWait();
			wp.setVisible(false);

		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
