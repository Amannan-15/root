package com.project.frontend;

import com.project.backend.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    private String currentFilePath = null; // Tracks the currently loaded or created CSV file 
    private ShiftManager shiftManager = new ShiftManager();
    private TableView<Shift> table;

    @Override
    public void start(Stage primaryStage) {
        // Table setup
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Shift, Integer> idColumn = new TableColumn<>("Shift ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("shiftId"));

        TableColumn<Shift, String> nameColumn = new TableColumn<>("Employee Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));

        TableColumn<Shift, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Shift, String> startColumn = new TableColumn<>("Start Time");
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        TableColumn<Shift, String> endColumn = new TableColumn<>("End Time");
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        table.getColumns().addAll(idColumn, nameColumn, dateColumn, startColumn, endColumn);

        // Form to Add/Update Shifts
        TextField nameField = new TextField();
        nameField.setPromptText("Employee Name");
        TextField dateField = new TextField();
        dateField.setPromptText("Date (YYYY-MM-DD)");
        TextField startField = new TextField();
        startField.setPromptText("Start Time (HH:MM AM/PM)");
        TextField endField = new TextField();
        endField.setPromptText("End Time (HH:MM AM/PM)");

        Button addButton = new Button("Add Shift");
        Button updateButton = new Button("Update Shift");
        Button deleteButton = new Button("Delete Shift");

        HBox form = new HBox(10, nameField, dateField, startField, endField, addButton, updateButton, deleteButton);
        form.setPadding(new Insets(10));
        form.setSpacing(10);

        // File Operations Buttons
        Button saveButton = new Button("Save to CSV");
        Button loadButton = new Button("Load from CSV");
        Button newFileButton = new Button("Create New CSV");

        HBox fileOperations = new HBox(10, saveButton, loadButton, newFileButton);
        fileOperations.setPadding(new Insets(10));
        fileOperations.setSpacing(10);

        // Button Actions
        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText();
                String date = dateField.getText();
                String start = startField.getText();
                String end = endField.getText();
                shiftManager.addShift(name, date, start, end);
                refreshTable();
                clearFields(nameField, dateField, startField, endField);
            } catch (Exception ex) {
                showAlert("Error", "Invalid input! Please try again.");
            }
        });

        updateButton.setOnAction(e -> {
            Shift selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    selected.setEmployeeName(nameField.getText());
                    selected.setDate(dateField.getText());
                    selected.setStartTime(startField.getText());
                    selected.setEndTime(endField.getText());
                    refreshTable();
                    clearFields(nameField, dateField, startField, endField);
                } catch (Exception ex) {
                    showAlert("Error", "Invalid input! Please try again.");
                }
            } else {
                showAlert("Warning", "No shift selected to update!");
            }
        });

        deleteButton.setOnAction(e -> {
            Shift selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                shiftManager.deleteShift(selected.getShiftId());
                refreshTable();
            } else {
                showAlert("Warning", "No shift selected to delete!");
            }
        });

        saveButton.setOnAction(e -> {
            if (currentFilePath != null) {
                // Save to the currently loaded file
                shiftManager.saveToCsv(currentFilePath);
                showAlert("Success", "Shifts saved successfully to " + new File(currentFilePath).getName());
            } else {
                // No file loaded, prompt user to save to a new file
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Shifts to CSV");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
                File file = fileChooser.showSaveDialog(primaryStage);
                if (file != null) {
                    currentFilePath = file.getAbsolutePath();
                    shiftManager.saveToCsv(currentFilePath);
                    showAlert("Success", "Shifts saved successfully to " + file.getName());
                }
            }
        });
        
        loadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            // Set the initial directory to the "csv_files" folder
            File csvFolder = new File("csv_files");
            if (csvFolder.exists() && csvFolder.isDirectory()) {
                fileChooser.setInitialDirectory(csvFolder);
            }
        
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage); // Replace 'primaryStage' with your stage variable
        
            if (selectedFile != null) {
                try {
                    currentFilePath = selectedFile.getAbsolutePath();
                    shiftManager.loadFromCsv(currentFilePath);
                    refreshTable();
                    showAlert("Success", "File loaded: " + selectedFile.getName());
                } catch (Exception ex) {
                    showAlert("Error", "Unable to load file: " + ex.getMessage());
                }
            }
        });
        
        

        newFileButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog("shifts.csv");
            dialog.setTitle("Create New CSV");
            dialog.setHeaderText("Enter the name for the new CSV file:");
            dialog.setContentText("Filename:");
            
            dialog.showAndWait().ifPresent(filename -> {
                // Define the directory for CSV files
                File csvFolder = new File("csv_files");
        
                // Ensure the directory exists
                if (!csvFolder.exists()) {
                    if (csvFolder.mkdir()) {
                        System.out.println("Directory created: " + csvFolder.getAbsolutePath());
                    } else {
                        System.out.println("Failed to create directory: " + csvFolder.getAbsolutePath());
                    }
                }
                
        
                // Create the full path for the new file
                File file = new File(csvFolder, filename);
                try {
                    if (file.createNewFile()) {
                        currentFilePath = file.getAbsolutePath(); // Set the current file path
                        shiftManager.getShifts().clear();
                        shiftManager.saveToCsv(currentFilePath); // Initialize the file with headers
                        shiftManager.loadFromCsv(currentFilePath);
                        refreshTable();
                        showAlert("Success", "New file created and loaded: " + file.getAbsolutePath());
                    } else {
                        showAlert("Warning", "File already exists: " + file.getAbsolutePath());
                    }
                } catch (Exception ex) {
                    showAlert("Error", "Unable to create file: " + ex.getMessage());
                }
            });
        });
        
        

        // Layout setup
        VBox layout = new VBox(10, table, form, fileOperations);
        layout.setPadding(new Insets(10));

        // Scene setup
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Shift Scheduler");
        primaryStage.show();
    }

    private void refreshTable() {
        table.getItems().clear();
        table.getItems().addAll(shiftManager.getShifts());
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
