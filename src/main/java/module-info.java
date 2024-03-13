module com.example.dz2_testjson {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;

    opens com.example.dz2_testjson to javafx.fxml;
    exports com.example.dz2_testjson;
}