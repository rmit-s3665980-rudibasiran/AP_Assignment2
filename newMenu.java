package AP_Assignment2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class newMenu extends Application {
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		double SCENEWIDTH = 1024;
		double SCENEHEIGHT = 768;

		int startX = 10;
		int startY = 10;
		int shadowWidth = 5;
		int shadowHeight = 5;
		int shadowOffsetX = 5;
		int shadowOffsetY = 5;
		int shadowRadius = 5;

		int rectWidth = 200;
		int rectHeight = 50;

		Pane pane = new Pane();

		DropShadow e = new DropShadow();
		e.setWidth(shadowWidth);
		e.setHeight(shadowHeight);
		e.setOffsetX(shadowOffsetX);
		e.setOffsetY(shadowOffsetY);
		e.setRadius(shadowRadius);
		e.setBlurType(BlurType.GAUSSIAN);

		Rectangle buttonBack = new Rectangle(0, 0, 175, SCENEHEIGHT);

		buttonBack.setStroke(Helper.menuColor);
		buttonBack.setFill(Helper.menuColor);
		buttonBack.setEffect(e);
		pane.getChildren().add(buttonBack);

		String[] _menuItems = new String[Helper.menuSize];

		_menuItems[Helper.quitMenu] = "Quit";
		_menuItems[Helper.addPerson] = "Add Person";
		_menuItems[Helper.findPerson] = "Find Person";
		_menuItems[Helper.displayProfile] = "Display Single Profile";
		_menuItems[Helper.displayAllProfile] = "Display All Profile(s)";
		_menuItems[Helper.updateProfile] = "Update Profile";
		_menuItems[Helper.deletePerson] = "Delete Person";
		_menuItems[Helper.connectPerson] = "Connect Person";
		_menuItems[Helper.findFriends] = "Find Friends";
		_menuItems[Helper.findFamily] = "Find Family";

		Button menuButtons[] = new Button[Helper.menuSize];

		Label menuLabel[] = new Label[Helper.menuSize];

		for (int i = 1; i < _menuItems.length; i++) {
			menuLabel[i] = new Label(_menuItems[i]);
			menuLabel[i].setLayoutX(startX);
			menuLabel[i].setLayoutY(startY + (i * 40));
			menuLabel[i].setStyle(Helper.labelStyle);
			pane.getChildren().add(menuLabel[i]);

		}

		menuLabel[0] = new Label(_menuItems[0]);
		menuLabel[0].setStyle(Helper.labelStyle);
		menuLabel[0].setLayoutX(startX);
		menuLabel[0].setLayoutY(startY + (Helper.menuSize * 40));
		pane.getChildren().add(menuLabel[0]);

		Scene scene = new Scene(pane, SCENEWIDTH, SCENEHEIGHT);
		scene.setFill(Color.SKYBLUE);
		primaryStage.setTitle("MiniNET"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}

	public static void main(String[] args) {
		launch(args);
	}
}
