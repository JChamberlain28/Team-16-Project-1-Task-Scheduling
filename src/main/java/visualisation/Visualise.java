package visualisation;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;


import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class Visualise extends Application {


    private final String visualiserTitle = "Visualisation";
    private final String SCENE_PATH = "views/GUI.fxml";

    public Visualise() {
        super();
    }



    // start visualisation
    public static void startVisual(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Visualise.class.getResource(SCENE_PATH));
            //System.out.println("loader =" + loader);
            Parent root=loader.load();
            Scene scene = new Scene(root);

            stage.setWidth(1000);
            stage.setHeight(700);


            stage.setTitle(visualiserTitle);

            stage.centerOnScreen();
            stage.setScene(scene);

            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void stop() {
        System.exit(1);
    }






}
