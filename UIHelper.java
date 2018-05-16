package AP_Assignment2;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class UIHelper {
	private Label _lbl[] = new Label[Helper.workTextFieldArraySize];
	private TextField _txt[] = new TextField[Helper.workTextFieldArraySize];
	private Label _info = new Label();
	private Button _buttonExe = new Button();
	private Button _buttonClose = new Button();

	public UIHelper(String lbl[], String be, String bc) {

		for (int i = 0; i < _lbl.length; i++) {
			_lbl[i] = new Label("");
			_txt[i] = new TextField("");

		}
		for (int i = 0; i < lbl.length; i++) {
			_lbl[i] = new Label(lbl[i]);
			_txt[i] = new TextField("");

			_lbl[i].setTextFill(Helper.menuRectTextColor);
			_lbl[i].setEffect(Helper.dropShadow());

			_txt[i].setPrefWidth(Helper.txtFieldWidth);
			_txt[i].setEffect(Helper.dropShadow());

		}

		_buttonExe = new Button(be);
		_buttonExe.setEffect(Helper.dropShadow());
		_buttonExe.setStyle(Helper.btnStyle);
		_buttonExe.setPrefWidth(Helper.workbtnWidth);

		_buttonExe.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				_buttonExe.setStyle(Helper.btnEffect);
			}
		});

		_buttonExe.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				_buttonExe.setStyle(Helper.btnStyle);
			}
		});

		_buttonClose = new Button(bc);
		_buttonClose.setEffect(Helper.dropShadow());
		_buttonClose.setStyle(Helper.btnStyle);
		_buttonClose.setPrefWidth(Helper.workbtnWidth);

		_buttonClose.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				_buttonClose.setStyle(Helper.btnEffect);
			}
		});

		_buttonClose.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				_buttonClose.setStyle(Helper.btnStyle);
			}
		});

		_info = new Label("");
		_info.setEffect(Helper.dropShadow());
		_info.setTextFill(Helper.menuRectTextColor);
		_info.setPrefWidth(Helper.infoLabelWidth);
		_info.setWrapText(true);

	}

	public BorderPane constructPane(Driver d, int menuItem, GridPane wp, Pane p) {

		wp.setVisible(true);

		for (int i = 0; i < p.getChildren().size(); i++)
			if (p.getChildren().get(i) instanceof StackPane)
				p.getChildren().get(i).setDisable(true);

		BorderPane workBorderPane = new BorderPane();
		workBorderPane.setPadding(new Insets(20, 20, 20, 20));
		workBorderPane.setStyle(Helper.workScreenStyle);
		workBorderPane.setLayoutX(wp.getLayoutX());
		workBorderPane.setLayoutY(wp.getLayoutY());

		GridPane queryPane = new GridPane();
		queryPane.setPadding(new Insets(20, 20, 20, 20));
		queryPane.setHgap(5);
		queryPane.setVgap(5);

		Label lblWindow = new Label(Helper.menuDesc[menuItem]);

		lblWindow.setTextFill(Helper.menuRectTextColor);
		lblWindow.setStyle(Helper.lblStyle);
		lblWindow.setEffect(Helper.dropShadow());

		queryPane.add(lblWindow, 0, 0);

		for (int i = 0; i < _lbl.length; i++) {
			if (!_lbl[i].getText().equals("")) {
				Label l = new Label();
				l = _lbl[i];
				TextField t = new TextField();
				t = _txt[i];
				queryPane.add(l, 0, i + 1);
				queryPane.add(t, 1, i + 1);
			}
		}

		ScrollPane infoPane = new ScrollPane();
		infoPane.setPadding(new Insets(20, 20, 20, 20));
		infoPane.setContent(_info);
		infoPane.setPrefHeight(Helper.infoLabelHeight);
		infoPane.setStyle(Helper.workScrollStyle);
		infoPane.setPrefWidth(Helper.infoLabelWidth);
		infoPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		infoPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		infoPane.setVisible(false);

		GridPane buttonPane = new GridPane();
		buttonPane.setPadding(new Insets(20, 20, 20, 20));
		buttonPane.setHgap(5);
		buttonPane.setVgap(5);

		buttonPane.add(_buttonExe, 0, 0);
		buttonPane.add(_buttonClose, 1, 0);

		workBorderPane.setTop(queryPane);
		workBorderPane.setCenter(infoPane);
		workBorderPane.setBottom(buttonPane);

		_buttonExe.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {

				ArrayList<Person> n = d.getNetwork();
				ArrayList<Relationship> r = d.getRelationship();
				infoPane.setVisible(true);
				_info.setText(Helper.menuDesc[menuItem] + ": Not Done Yet!!");

				if (menuItem == Helper.addPerson) {

					_info.setText(d.addPerson(_txt));

				} else if (menuItem == Helper.findPerson) {
					_info.setText(d.findPerson(_txt));

				} else if (menuItem == Helper.displayProfile) {
					_info.setText(d.displayProfile(_txt));

				} else if (menuItem == Helper.displayAllProfile) {
					_info.setText(d.displayAllProfile());

				} else if (menuItem == Helper.updateProfile) {
					_info.setText(d.updateProfile(_txt));

				} else if (menuItem == Helper.deletePerson) {
					_info.setText(d.deletePerson(_txt));

				} else if (menuItem == Helper.connectPerson) {
					_info.setText(d.connectPerson(_txt));

				} else if (menuItem == Helper.findConnection) {
					_info.setText(d.findConnection(_txt));

				} else if (menuItem == Helper.findFamily) {
					_info.setText(d.findFamily(_txt));
				}

				else if (menuItem == Helper.findClassmates) {

				}
			}
		});

		_buttonClose.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				workBorderPane.setVisible(false);

				wp.setVisible(false);

				for (int i = 0; i < p.getChildren().size(); i++)
					if (p.getChildren().get(i) instanceof StackPane)
						p.getChildren().get(i).setDisable(false);
			}
		});
		return workBorderPane;
	}

}
