package visualisation;


import algorithm.Algorithm;
import graph.Graph;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;


import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import visualisation.controllers.GUIController;

public class Visualise extends Application {


    private final String visualiserTitle = "Team 16 - Scheduling Visualisation";
    private final String SCENE_PATH = "views/GUI.fxml";

    static private Algorithm _algorithm;
    static private Graph _graph;


    // start visualisation of program
    public static void startVisual(String[] args, Algorithm algorithm,Graph graph) {
        // initialise algorithm and graph needed for visualisation
        // passed into GUI controller
        Visualise._algorithm = algorithm;
        Visualise._graph = graph;
        launch(args);
    }




    @Override
    public void start(Stage stage) throws Exception {

        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Visualise.class.getResource(SCENE_PATH));

            GUIController GUIController = new GUIController(Visualise._algorithm, Visualise._graph);
            loader.setController(GUIController);
            Parent root=loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Visualise.class.getResource("visualisationutil/GUI.css").toString());
            stage.setWidth(1000);
            stage.setHeight(600);


            stage.setTitle(visualiserTitle);

            stage.centerOnScreen();
            stage.setScene(scene);

            stage.show();
            stage.setOnCloseRequest(event -> System.exit(0));

            // this is in case the gantt chart is resized. To reinitialise the labels
            ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->{
                GUIController.resizeReinitialise();
            };
            stage.widthProperty().addListener(stageSizeListener);
            stage.heightProperty().addListener(stageSizeListener);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void stop() {
        System.exit(1);
    }


}
