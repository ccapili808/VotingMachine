import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class VirtualKeyboard extends VBox {

    private TextField textField;

    public VirtualKeyboard() {
        textField = new TextField();
        HBox row1 = createRow("qwertyuiop".toUpperCase(), true, false);
        HBox row2 = createRow("asdfghjkl".toUpperCase(), false, true);
        HBox row3 = createRow("zxcvbnm".toUpperCase(), false, false);
        HBox row4 = spacebar();
        this.setSpacing(5);
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        this.getChildren().addAll(textField, row1, row2, row3, row4);
    }

    private HBox createRow(String keys, boolean addBackspace, boolean addEnter) {
        HBox row = new HBox();
        row.setSpacing(5);
        row.setAlignment(Pos.CENTER);
        for (char key : keys.toCharArray()) {
            Button button = new Button(String.valueOf(key));
            button.setOnAction(e -> textField.appendText(button.getText()));
            row.getChildren().add(button);
        }

        if (addBackspace) {
            Button backspace = new Button("Backspace");
            backspace.setOnAction(e -> {
                String currentText = textField.getText();
                if (!currentText.isEmpty()) {
                    textField.setText(currentText.substring(0, currentText.length() - 1));
                }
            });
            row.getChildren().add(backspace);
        }

        if (addEnter) {
            Button enter = new Button("Enter");
            enter.setOnAction(e -> textField.appendText("\n"));
            row.getChildren().add(enter);
        }

        return row;
    }

    private HBox spacebar() {
        HBox row = new HBox();
        row.setSpacing(5);
        row.setAlignment(Pos.CENTER);
        Button spacebar = new Button("Space");
        spacebar.setMinWidth(150);
        spacebar.setOnAction(e -> textField.appendText(" "));
        row.getChildren().add(spacebar);
        return row;
    }
}