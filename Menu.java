package AP_Assignment2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/*
Title: RMIT Advanced Programming Assignment 2
Developer(s): 
- Rudi Basiran <s3665980@student.rmit.edu.au> 
Date Created: 5 May 2018 
Description: Menu Class controlling the GUI
Notes: --

Change History:
- [16 May 2018] Rudi Basiran  : Updated GUI
 */

public class Menu extends Application {

	private Driver _driver = new Driver();

	public Menu() {
		_driver = new Driver();
		_driver.loadData();
	}

	public void go() {
		launch();
	}

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		double SCENEWIDTH = 2048;
		double SCENEHEIGHT = 1536;

		Pane pane = new Pane();

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
			menuLabel.setEffect(Helper.dropShadow());
			menuLabel.setId(_menuItems[i]);

			menuPane.setId(_menuItems[i]);
			menuPane.getChildren().addAll(menuRectangle, menuLabel);

			menuPane.setDisable(false);

			menuPane.setLayoutX(Helper.startX);

			if (i == 0)
				menuPane.setLayoutY(Helper.menuStartY + (_menuItems.length * Helper.rectOffset));
			else
				menuPane.setLayoutY(Helper.menuStartY + (i * Helper.rectOffset));

			menuPane.addEventHandler(MouseEvent.MOUSE_ENTERED, (event) -> Helper.rectMouseEntered(menuPane));

			menuPane.addEventHandler(MouseEvent.MOUSE_EXITED, (event) -> Helper.rectMouseExited(menuPane));

			menuPane.addEventHandler(MouseEvent.MOUSE_PRESSED, (event) -> Helper.rectMousePressed(menuPane));

			menuPane.addEventHandler(MouseEvent.MOUSE_RELEASED,
					(event) -> menuAction(primaryStage, workPane, pane, menuRectangle.getId()));

			pane.getChildren().add(menuPane);
		}

		Rectangle bottomRectangle = new Rectangle();
		bottomRectangle.setFill(Helper.menuRectColor);
		bottomRectangle.setStroke(Helper.menuRectBorder);
		bottomRectangle.setHeight(Helper.rectHeight * 4.5);
		bottomRectangle.setWidth(Helper.rectWidth);
		bottomRectangle.setLayoutY(Helper.menuStartY + ((_menuItems.length + 1) * Helper.rectOffset));
		bottomRectangle.setLayoutX(Helper.startX);
		Helper.doRectEffect(bottomRectangle);
		pane.getChildren().add(bottomRectangle);

		Scene scene = new Scene(pane, SCENEWIDTH, SCENEHEIGHT);
		primaryStage.setTitle("MiniNet"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}

	public void menuAction(Stage primaryStage, GridPane wp, Pane p, String menuClicked) {

		if (menuClicked.equals(Helper.menuDesc[Helper.quitMenu])) {
			primaryStage.close();
		} else {

			// menuRectangle.setId(_menuItems[i]);

			String labels[] = new String[Helper.workTextFieldArraySize];
			for (int i = 0; i < Helper.workTextFieldArraySize; i++)
				labels[i] = "";
			String actionButton = "";
			int actionItem = -1;

			if (menuClicked.equals(Helper.menuDesc[Helper.addPerson])) {
				labels[0] = "Enter Full Name: ";
				labels[1] = "Enter Age: ";
				labels[2] = "Enter Gender: ";
				labels[3] = "Enter Info: ";
				actionButton = "Save";
				actionItem = Helper.addPerson;

			} else if (menuClicked.equals(Helper.menuDesc[Helper.findPerson])) {
				labels[0] = "Enter Full Name: ";
				actionButton = "Search";
				actionItem = Helper.findPerson;

			} else if (menuClicked.equals(Helper.menuDesc[Helper.displayProfile])) {
				labels[0] = "Enter Full Name: ";
				actionButton = "Search";
				actionItem = Helper.displayProfile;

			} else if (menuClicked.equals(Helper.menuDesc[Helper.displayAllProfile])) {
				actionButton = "Show";
				actionItem = Helper.displayAllProfile;

			} else if (menuClicked.equals(Helper.menuDesc[Helper.updateProfile])) {
				labels[0] = "Enter Full Name: ";
				labels[1] = "Update Age: ";
				labels[2] = "Update Gender: ";
				labels[3] = "Update Info: ";
				actionButton = "Save";
				actionItem = Helper.updateProfile;

			} else if (menuClicked.equals(Helper.menuDesc[Helper.deletePerson])) {
				labels[0] = "Enter Full Name: ";
				actionButton = "Delete";
				actionItem = Helper.deletePerson;

			} else if (menuClicked.equals(Helper.menuDesc[Helper.connectPerson])) {
				labels[0] = "Enter Full Name of 1st Person \n[Parent first if 2nd Person is a Child]: ";
				labels[1] = "Enter Full Name of 2nd Person: ";

				String options = "Enter Relationship: ";
				for (int i = 0; i < Helper.roleDesc.length; i++) {
					options = options + (i % 3 == 0 ? "\n" : "") + "[" + i + "] " + Helper.roleDesc[i] + " ";
				}
				labels[2] = options;
				actionButton = "Save";
				actionItem = Helper.connectPerson;

			} else if (menuClicked.equals(Helper.menuDesc[Helper.findConnection])) {
				labels[0] = "Enter Full Name of 1st Person: ";
				labels[1] = "Enter Full Name of 2nd Person: ";
				actionButton = "Search";
				actionItem = Helper.findConnection;

			} else if (menuClicked.equals(Helper.menuDesc[Helper.findFamily])) {
				labels[0] = "Enter Full Name: ";
				actionButton = "Search";
				actionItem = Helper.findFamily;

			} else if (menuClicked.equals(Helper.menuDesc[Helper.findClassmates])) {
				labels[0] = "Enter Full Name: ";
				actionButton = "Search";
				actionItem = Helper.findClassmates;
			}

			Boolean createPane = false;
			for (int i = 0; i < Helper.workTextFieldArraySize; i++)
				if (!labels[i].equals("") | actionItem == Helper.displayAllProfile) {
					createPane = true;
					break;
				}

			if (createPane) {

				UIHelper workBox = new UIHelper(labels, actionButton, "Close");
				BorderPane workBorderPane = new BorderPane();
				workBorderPane = workBox.constructPane(_driver, actionItem, wp, p);
				wp.getChildren().add(workBorderPane);
				wp.setVisible(true);

			}

		}
	}

	public void disableMenu(Pane pn) {

	}

}
