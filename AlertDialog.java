package AP_Assignment2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
Title: RMIT Advanced Programming Assignment 2
Developer(s): 
- Rudi Basiran <s3665980@student.rmit.edu.au> 
- Sherri McRae <s3117889@student.rmit.edu.au> 
Date Created: 18 May 2018 
Description: Alert Box
Notes: --
Change History:
 */

public class AlertDialog extends Stage {

	private final int WIDTH_DEFAULT = 300;
	private final int HEIGHT_DEFAULT = 200;

	public static final int ICON_INFO = 0;
	public static final int ICON_ERROR = 1;

	public AlertDialog(String msg, int type) {
		// setResizable(false);
		// initModality(Modality.APPLICATION_MODAL);
		// initStyle(StageStyle.TRANSPARENT);

		Label label = new Label(msg);
		label.setWrapText(true);
		label.setTextFill(Helper.menuRectTextColor);
		label.setEffect(Helper.dropShadow());

		Button button = new Button("OK");
		button.setEffect(Helper.dropShadow());
		button.setStyle(Helper.btnStyle);
		button.setPrefWidth(Helper.workbtnWidth);

		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				AlertDialog.this.close();
			}
		});

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(label);
		borderPane.setStyle(Helper.alertBtnStyle);
		borderPane.setEffect(Helper.dropShadow());

		HBox hbox = new HBox();
		hbox.setAlignment(Pos.BASELINE_CENTER);
		hbox.getChildren().add(button);
		hbox.setPadding(new Insets(20, 20, 20, 20));
		borderPane.setBottom(hbox);

		// calculate width of string
		final Text text = new Text(msg);
		text.snapshot(null, null);

		// + 40 to pad it
		int width = (int) text.getLayoutBounds().getWidth() + 40;

		if (width < WIDTH_DEFAULT)
			width = WIDTH_DEFAULT;

		int height = HEIGHT_DEFAULT;

		final Scene scene = new Scene(borderPane, width, height);
		scene.setFill(Color.TRANSPARENT);
		setScene(scene);

	}

}