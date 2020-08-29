package visualisation;


import algorithm.Algorithm;
import graph.Graph;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;


import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import visualisation.controllers.GUIController;

public class Visualise extends Application {


    private final String visualiserTitle = "Visualisation";
    private final String SCENE_PATH = "views/GUI.fxml";

    static private Algorithm _algorithm;
    static private Graph _graph;

    /*public Visualise(Algorithm algorithm,Graph graph) {
        super();
        this._algorithm = algorithm;
        this._graph = graph;
    }*/



    // start visualisation
    public static void startVisual(String[] args, Algorithm algorithm,Graph graph) {

        Visualise._algorithm = algorithm;
        Visualise._graph = graph;
        launch(args);
    }




    @Override
    public void start(Stage stage) throws Exception {

        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Visualise.class.getResource(SCENE_PATH));
            System.out.println("loader @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@=" + loader);

            GUIController GUIController = new GUIController(Visualise._algorithm, Visualise._graph);
            loader.setController(GUIController);
            Parent root=loader.load();


            Scene scene = new Scene(root);
            scene.getStylesheets().add(Visualise.class.getResource("visualisationutil/GUI.css").toString());
            stage.setWidth(900);
            stage.setHeight(600);


            stage.setTitle(visualiserTitle);

            stage.centerOnScreen();
            stage.setScene(scene);

            stage.show();
            stage.setOnCloseRequest(event -> System.exit(0));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void stop() {
        System.exit(1);
    }


}
