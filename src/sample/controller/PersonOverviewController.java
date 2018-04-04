package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Main;
import sample.model.Person;
import sample.util.DateUtil;

/*
 * 所有fxml文件需要访问的属性和方法必须加上 @FXML 注解.实际上,只有在私有的情况下才需要, 但是让它们保持私有并且用注解标记的方式更好!
 * initialize() 方法在fxml文件完成载入时被自动调用. 那时, 所有的FXML属性都应已被初始化.
 * 我们在表格列上使用setCellValueFactory(...) 来确定为特定列使用Person对象的某个属性.
 *  箭头 -> 表示我们在使用Java 8的 Lambdas 特性. (另一个选择是使用 PropertyValueFactory, 但它不是类型安全的).
 * */

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;

    @FXML
    private TableColumn<Person, String> firstNameColumn;

    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    private Main mainApp;

    public PersonOverviewController() {

    }

    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        showPersonDetails(null);

        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        personTable.setItems(mainApp.getPersonData());
    }

    private void showPersonDetails(Person person) {
        if (person != null) {
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else {
            // Person is null, remove all the text.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
//            Alert.create()
//                    .title("No Selection")
//                    .masthead("No Person Selected")
//                    .message("Please select a person in the table.")
//                    .showWarning();
            Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.setTitle("No Selection");
            dialog.setHeaderText("No Person Selected");
            dialog.setContentText("Please select a person in the table.");
            dialog.showAndWait();

        }
    }

    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }

    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
        }
    }
}
