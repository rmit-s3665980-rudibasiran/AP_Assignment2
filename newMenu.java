package AP_Assignment2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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

		int shadowWidth = 2;
		int shadowHeight = 2;
		int shadowOffsetX = 5;
		int shadowOffsetY = 5;
		int shadowRadius = 2;

		DropShadow ds = new DropShadow();
		ds.setWidth(shadowWidth);
		ds.setHeight(shadowHeight);
		ds.setOffsetX(shadowOffsetX);
		ds.setOffsetY(shadowOffsetY);
		ds.setRadius(shadowRadius);
		ds.setBlurType(BlurType.GAUSSIAN);

		Rectangle backRectangle = new Rectangle(0, 0, SCENEWIDTH, SCENEHEIGHT);
		backRectangle.setFill(Color.rgb(40, 40, 40));
		pane.getChildren().add(backRectangle);

		Rectangle brandRectangle = new Rectangle();
		brandRectangle.setFill(Color.BLACK);
		brandRectangle.setStroke(Color.GREEN);
		brandRectangle.setHeight(rectHeight);
		brandRectangle.setWidth(rectWidth * 7);
		brandRectangle.setEffect(ds);
		// doEffect(brandRectangle);

		Label brandLabel = new Label("R.Net");
		brandLabel.setTextFill(Color.WHITE);

		StackPane brandPane = new StackPane();
		brandPane.setLayoutX(startX);
		brandPane.setLayoutY(startY);

		brandPane.getChildren().addAll(brandRectangle, brandLabel);

		pane.getChildren().add(brandPane);

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
		Label menuLabel[] = new Label[Helper.menuSize];
		StackPane menuPane[] = new StackPane[Helper.menuSize];

		int i;
		for (i = 1; i < _menuItems.length; i++) {
			menuRectangle[i] = new Rectangle();
			menuRectangle[i].setFill(Color.BLACK);
			menuRectangle[i].setStroke(Color.GREEN);
			menuRectangle[i].setEffect(ds);
			menuRectangle[i].setHeight(rectHeight);
			menuRectangle[i].setWidth(rectWidth);

			menuLabel[i] = new Label();
			menuLabel[i].setTextFill(Color.WHITE);

			menuLabel[i].setMaxWidth(Double.MAX_VALUE);
			menuLabel[i].setAlignment(Pos.CENTER_LEFT);
			menuLabel[i].setText("     " + _menuItems[i]);

			menuPane[i] = new StackPane();
			menuPane[i].getChildren().addAll(menuRectangle[i], menuLabel[i]);
			menuPane[i].setLayoutX(startX);
			menuPane[i].setLayoutY(menuStartY + (i * rectOffset));

			pane.getChildren().add(menuPane[i]);

		}
		i = 0;
		menuRectangle[i] = new Rectangle();
		menuRectangle[i].setFill(Color.BLACK);
		menuRectangle[i].setStroke(Color.GREEN);
		menuRectangle[i].setEffect(ds);
		menuRectangle[i].setHeight(rectHeight);
		menuRectangle[i].setWidth(rectWidth);

		menuLabel[i] = new Label();
		menuLabel[i].setTextFill(Color.WHITE);

		menuLabel[i].setMaxWidth(Double.MAX_VALUE);
		menuLabel[i].setAlignment(Pos.CENTER_LEFT);
		menuLabel[i].setText("     " + _menuItems[i]);

		menuPane[i] = new StackPane();
		menuPane[i].getChildren().addAll(menuRectangle[i], menuLabel[i]);
		menuPane[i].setLayoutX(startX);
		menuPane[i].setLayoutY(menuStartY + (_menuItems.length * rectOffset));

		pane.getChildren().add(menuPane[i]);

		Scene scene = new Scene(pane, SCENEWIDTH, SCENEHEIGHT);
		primaryStage.setTitle("MiniNET"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}

	public static void main(String[] args) {
		launch(args);
	}
}
