package mainFrame;



import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Employee;
import service.EmployeeManagementService;

public class MainApp extends Application {

    private EmployeeManagementService employeeManagementService = new EmployeeManagementService();
    private ListView<HBox> employeeListView = new ListView<>();

    @Override
    public void start(Stage primaryStage) {
        //employeeListView.getItems().addAll((ObservableList) employeeManagementService.getAllEmployees());
        primaryStage.setTitle("Employee Management System");

        // --- Input Fields ---
        TextField nameField = new TextField();
        nameField.setPromptText("Enter name");

        TextField departmentField = new TextField();
        departmentField.setPromptText("Enter department");

        TextField salaryField = new TextField();
        salaryField.setPromptText("Enter salary");

        Button addButton = new Button("Add Employee");
        Button showAllEmpButton = new Button("Show All Employees");

        // --- Top Input Section ---
        HBox inputBox = new HBox(10);
        inputBox.setPadding(new Insets(10));
        inputBox.getChildren().addAll(
                new Label("Name:"), nameField,
                new Label("Department:"), departmentField,
                new Label("Salary:"), salaryField,
                addButton, showAllEmpButton
        );

        // --- Center ListView ---
        VBox centerBox = new VBox(10);
        centerBox.setPadding(new Insets(10));
        centerBox.getChildren().add(employeeListView);

        // --- Layout ---
        BorderPane root = new BorderPane();
        root.setTop(inputBox);
        root.setCenter(centerBox);
        for(Employee emp:employeeManagementService.getAllEmployees())
        {
            Label empInfo = new Label("ID: " + emp.getId() + ", Name: " + emp.getName() +
                    ", Department: " + emp.getDepartment() + ", Salary: " + emp.getSalary());
            Button removeButton = new Button("Remove");
            HBox empBox = new HBox(10, empInfo, removeButton);
            empBox.setPadding(new Insets(5));
            employeeListView.getItems().add(empBox);
            removeButton.setOnAction(ev -> {
                employeeManagementService.remove(emp); // remove from service
                employeeListView.getItems().remove(empBox);  // remove from UI
            });

        }

        // --- Add Employee Button Action ---
        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText().trim();
                String department = departmentField.getText().trim();
                String salaryStr = salaryField.getText().trim();

                if (name.isEmpty() || department.isEmpty() || salaryStr.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Please fill all fields!");
                    return;
                }

                int salary = Integer.parseInt(salaryStr);
                int id = employeeManagementService.getAllEmployees().size() + 1;
                Employee employee = new Employee(id, name, department, salary);
                employeeManagementService.addEmployee(employee);

                // --- Create HBox for this employee with Remove button ---
                Label empInfo = new Label("ID: " + id + ", Name: " + name +
                        ", Department: " + department + ", Salary: " + salary);
                Button removeButton = new Button("Remove");

                HBox empBox = new HBox(10, empInfo, removeButton);
                empBox.setPadding(new Insets(5));

                // --- Remove Button Action ---
                removeButton.setOnAction(ev -> {
                    employeeManagementService.remove(employee); // remove from service
                    employeeListView.getItems().remove(empBox);  // remove from UI
                });

                employeeListView.getItems().add(empBox);

                // --- Clear input fields ---
                nameField.clear();
                departmentField.clear();
                salaryField.clear();

            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Salary must be a valid number!");
            }
        });

        // --- Show All Employees Button Action ---
        showAllEmpButton.setOnAction(e -> {
            if (employeeManagementService.getAllEmployees().isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "No employees found!");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Employee emp : employeeManagementService.getAllEmployees()) {
                    sb.append("ID: ").append(emp.getId())
                            .append(", Name: ").append(emp.getName())
                            .append(", Department: ").append(emp.getDepartment())
                            .append(", Salary: ").append(emp.getSalary())
                            .append("\n");
                }
                showAlert(Alert.AlertType.INFORMATION, sb.toString());
            }
        });

        // --- Scene and Stage ---
        Scene scene = new Scene(root, 700, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
