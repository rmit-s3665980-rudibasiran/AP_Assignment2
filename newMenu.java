package AP_Assignment2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class newMenu extends Application {
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		double SCENEWIDTH = 1024;
		double SCENEHEIGHT = 768;

		int startX = 5;
		int startY = 5;
		int menuStartY = 110;
		int shadowWidth = 2;
		int shadowHeight = 2;
		int shadowOffsetX = 5;
		int shadowOffsetY = 5;
		int shadowRadius = 2;

		int rectWidth = 200;
		int rectHeight = 50;
		int rectOffset = 60;
		int menuTextStart = 150;
		int menuTextOffsetX = 20;
		int menuTextOffsetY = 50;

		Pane pane = new Pane();

		DropShadow e = new DropShadow();
		e.setWidth(shadowWidth);
		e.setHeight(shadowHeight);
		e.setOffsetX(shadowOffsetX);
		e.setOffsetY(shadowOffsetY);
		e.setRadius(shadowRadius);
		e.setBlurType(BlurType.GAUSSIAN);

		Rectangle backRectangle = new Rectangle(0, 0, SCENEWIDTH, SCENEHEIGHT);
		backRectangle.setFill(Color.rgb(40, 40, 40));
		pane.getChildren().add(backRectangle);

		Rectangle brandRectangle = new Rectangle(startX, startY, rectWidth, rectHeight * 3);
		brandRectangle.setFill(Color.BLACK);
		brandRectangle.setStroke(Color.AQUAMARINE);
		brandRectangle.setEffect(e);

		Label brandLabel = new Label("R | MiniNet");
		pane.getChildren().add(brandRectangle);

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

		Rectangle menuRectangle[] = new Rectangle[Helper.menuSize];
		Text menuText[] = new Text[Helper.menuSize];

		for (int i = 1; i < _menuItems.length; i++) {
			menuRectangle[i] = new Rectangle(startX, menuStartY + (i * rectOffset), rectWidth, rectHeight);
			menuRectangle[i].setFill(Color.BLACK);
			menuRectangle[i].setStroke(Color.GREEN);
			menuRectangle[i].setEffect(e);

			menuText[i] = new Text(_menuItems[i]);
			menuText[i].setLayoutX(startX + menuTextOffsetX);
			menuText[i].setLayoutY(menuTextStart + (i * menuTextOffsetY));
			menuText[i].setFill(Color.WHITE);

			pane.getChildren().add(menuRectangle[i]);
			pane.getChildren().add(menuText[i]);

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
