
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.File;
import java.net.URL;

public class Main extends Application {

    final String RUTA_FORMULARI = "src/main/java/Views/Main.fxml";
    final String TITOL_FORMULARI = "Calculadora";

    @Override
    public void start(Stage primaryStage) throws Exception{

        URL url = new File(RUTA_FORMULARI).toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle(TITOL_FORMULARI);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.getScene().getOnKeyPressed();
        primaryStage.show();

    }

    public static void executar() {launch();}

}


class Init
{
    public static void main (String[] args)
    {
        Main.executar();
    }
}


