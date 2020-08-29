package visualisation.controllers;



import algorithm.Algorithm;
import algorithm.PartialSchedule;
import algorithm.ScheduledTask;
import graph.Graph;
import input.CliParser;
import javafx.collections.FXCollections;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.paint.Color;
import visualisation.GanttChart;

import com.sun.management.OperatingSystemMXBean;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.lang.management.ManagementFactory;
import java.util.Arrays;


public class GUIController {

    private GanttChart<Number,String> chart;


    @FXML
    private HBox chartHBox;

    @FXML
    private HBox ganttHBox;



    @FXML
    private VBox textCont;

    @FXML
    private HBox lowerHbox;

    @FXML
    private Label _statusLower;

    @FXML
    private Label _elapsedLower;

    @FXML
    private HBox parent;

    private  LineChart<Number, Number> _memoryChart;
    private  LineChart<Number, Number> _cpuChart;
    private NumberAxis _xAxisCPU;
    private NumberAxis _xAxisMem;
    private XYChart.Series _memorySeries;
    private XYChart.Series _cpuSeries;

    private Algorithm _algorithm;
    private Graph _graph;

    public GUIController(Algorithm  algorithm,Graph graph ){
        this._algorithm = algorithm;
        this._graph = graph;
    }





    @FXML
    public void initialize() {
        setupTextComponents();
        setupUsageCharts();
        startTimer();
        setUpGanttBox();


        // this should be inside polling but left for testing
        //updateGantt();

        parent.setStyle("-fx-background-color: white");
    }



    @FXML
    private void setupTextComponents(){//todo readd styling and layout ,fix input graph
        Label title = new Label("Team 16 - Saadboys" );//TODO add logo + easter egg
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 20; -fx-text-fill: chocolate; -fx-font-family: Consolas");

        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);

        Label statusUpper = new Label("Program status:");
        _statusLower = new Label(" Running");//TODO add update for this + chart + timer when program ends
        _statusLower.setStyle("-fx-text-fill: chocolate; -fx-font-weight: bold");

        Label elapsedUpper = new Label("Time elapsed:");
        _elapsedLower = new Label("");
        _elapsedLower.setStyle("-fx-text-fill: green; -fx-font-weight: bold");

        Label inputName = new Label("Input file name: " + CliParser.getCliParserInstance().getFileName());
        Label outputName = new Label("Output file name: " + CliParser.getCliParserInstance().getOutputFileName());

        //Label procNum = new Label("Number of processors: " + CliParser.getCliParserInstance().getNumberOfProcessors());//TODO decide add these 2 or nah
        //Label taskNum or total schedules

        Label parallelUpper = new Label("Number of threads:");
        Label parallelLower = new Label(" Off");
        parallelLower.setStyle("-fx-text-fill: chocolate");
        if (CliParser.getCliParserInstance().getNumberOfCores()>1){
            parallelLower.setText(" " + CliParser.getCliParserInstance().getNumberOfCores());
        }

        //Label theme = new Label("");
        textCont.getChildren().addAll(title, filler, inputName, outputName, new HBox(parallelUpper, parallelLower), new HBox(statusUpper, _statusLower), new HBox(elapsedUpper, _elapsedLower));
        textCont.setMinWidth(Region.USE_PREF_SIZE);
        textCont.setStyle("-fx-padding: 10; -fx-background-color: lightgray; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0)");

    }


    /*
    **Timer with increment 1s. Generates new CPU/Memory usage data for JVM every second and
    * adds this data to the series displayed on each linechart
     */
    @FXML
    private void startTimer(){//TODO set X axes to 60 second fix
        OperatingSystemMXBean osMxBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double startTime = System.currentTimeMillis();
        int[] increment = {0};
        Timeline timer = new Timeline(
            new KeyFrame(Duration.seconds(0.1), event -> {
                double currentTime = Math.round((System.currentTimeMillis() - startTime)/10)/100.0;
                if (increment[0]%10 == 0){
                    _memorySeries.getData().add(new XYChart.Data<>(increment[0]/10,(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000000));
                    if (_memorySeries.getData().size()>60){
                        _xAxisMem.setUpperBound(increment[0]/10);
                        _xAxisMem.setLowerBound(increment[0]/10 - 60);
                        _memorySeries.getData().remove(0);
                        _memoryChart.getData().remove(0);
                        _memoryChart.getData().add(_memorySeries);

                    }
                    _cpuSeries.getData().add(new XYChart.Data<>(increment[0]/10, (osMxBean.getProcessCpuLoad())*100));
                    if (_cpuSeries.getData().size()>60){
                        _xAxisCPU.setUpperBound(increment[0]/10);
                        _xAxisCPU.setLowerBound(increment[0]/10 - 60);
                        _cpuSeries.getData().remove(0);
                        _cpuChart.getData().remove(0);
                        _cpuChart.getData().add(_cpuSeries);
                    }
                    updateGantt();
                }
                if (currentTime<60){
                    _elapsedLower.setText(" " + currentTime + " seconds");
                } else {
                    _elapsedLower.setText(" " + (int)Math.floor(currentTime/60) + " minutes " + Math.round((currentTime%60)*10.0)/10.0 + " seconds");
                }
                increment[0]++;
            })
        );
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    /*
    ** Creation of linecharts for both cpu/memory usage, and addition of these to hbox container
     */
    @FXML
    private void setupUsageCharts(){
        _xAxisMem = new NumberAxis();
        final NumberAxis yAxisMem = new NumberAxis();
        _xAxisMem.setUpperBound(60);
        _xAxisMem.setAutoRanging(false);
        _xAxisMem.setLabel("Time (seconds)");
        yAxisMem.setLabel("Memory Usage (Mb)");
        _memoryChart = new LineChart<>(_xAxisMem, yAxisMem);
        _memorySeries = new XYChart.Series();
        _memoryChart.getData().add(_memorySeries);
        _memoryChart.setLegendVisible(false);
        _memoryChart.setCreateSymbols(false);
        _memoryChart.setTitle("JVM Memory Usage");
        _memoryChart.animatedProperty().setValue(false);

        _xAxisCPU = new NumberAxis();
        final NumberAxis yAxisCPU = new NumberAxis();
        yAxisCPU.setUpperBound(100);
        yAxisCPU.setAutoRanging(false);
        _xAxisCPU.setUpperBound(60);
        _xAxisCPU.setAutoRanging(false);
        _xAxisCPU.setLabel("Time (seconds)");
        yAxisCPU.setLabel("CPU Load (%)");
        _cpuChart = new LineChart<>(_xAxisCPU, yAxisCPU);
        _cpuSeries = new XYChart.Series();
        _cpuChart.getData().add(_cpuSeries);
        _cpuChart.setLegendVisible(false);
        _cpuChart.setCreateSymbols(false);
        _cpuChart.setTitle("JVM CPU Usage");
        _cpuChart.animatedProperty().setValue(false);

        chartHBox.getChildren().addAll(_memoryChart, _cpuChart);
    }






    private void setUpGanttBox(){

        // number of processors
        int numberOfProcessors = CliParser.getCliParserInstance().getNumberOfProcessors();

        String[] processorList = new String[numberOfProcessors];
        for (int i = 0;i<numberOfProcessors ;i++){
            processorList[i]="Processor "+i;
        }

        // intitialise x and y axis
        final NumberAxis timeAxis = new NumberAxis();  // x axis
        final CategoryAxis processorsAxis = new CategoryAxis();  // y axis

        // Setting time axis
        timeAxis.setLabel("");
        timeAxis.setTickLabelFill(Color.CHOCOLATE);
        timeAxis.setMinorTickCount(4);


        // Setting processors axis
        processorsAxis.setLabel("");
        timeAxis.setTickLabelFill(Color.CHOCOLATE);
        processorsAxis.setTickLabelGap(10);
        processorsAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(processorList)));

        // Setting chart
        chart = new GanttChart<Number,String>(timeAxis,processorsAxis);
        chart.setTitle("Current best schedule found: ");
        chart.setLegendVisible(false);
        chart.setBlockHeight(200/numberOfProcessors);

        chart.getStylesheets().add(getClass().getResource("/visualisation/visualisationutil/GanttChart.css").toExternalForm());
        //chart.getStylesheets().add(getClass().getResource("visualisationutil/GanttChart.css").toExternalForm());
        chart.setMaxHeight(400);
        ganttHBox.getChildren().add(chart);

    }




    /*This must be put inside the polling and update along with the other aspects. */
    private void updateGantt(){

        int numberOfProcessors = CliParser.getCliParserInstance().getNumberOfProcessors();

        // new array of series to write onto
        Series[] seriesProcessors = new Series[numberOfProcessors];

        // initializing series obj
        for (int i=0;i<numberOfProcessors;i++){
            seriesProcessors[i]=new Series();
        }

        // for every task in schedule, write its data onto the specific series


        //PartialSchedule pSchedule = new PartialSchedule();
        // loop for all processors

        //ScheduledTask scheduledTask = new ScheduledTask(1, 2, 3);



        PartialSchedule currentBestSchedule = this._algorithm.getBestSchedule();

        if (currentBestSchedule!=null) {
            // @@@@@@@@@@@@ this throws a null poiner exception
            // to get rid of null pointer remove the for loop and unco,,emt line 205 with the schedualed task construter
            for (ScheduledTask scheduledTask : currentBestSchedule.getScheduledTasks()) {

                int taskProcessor = scheduledTask.getProcessor();
                XYChart.Data newData = new XYChart.Data(scheduledTask.getStartTime(), ("Processor " + taskProcessor),
                        new GanttChart.ExtraData(scheduledTask, _graph, "status-red"));
                seriesProcessors[taskProcessor].getData().add(newData);
            }


            // clear current gantt and repopulate chart with new series
            chart.getData().clear();
            for (Series series : seriesProcessors) {
                chart.getData().add(series);
            }
        } else {
            System.out.println("null schdule");
        }


    }




}
