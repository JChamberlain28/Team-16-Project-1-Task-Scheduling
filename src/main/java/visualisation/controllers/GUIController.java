package visualisation.controllers;



import algorithm.Algorithm;
import algorithm.PartialSchedule;
import algorithm.ScheduledTask;
import graph.Graph;
import input.CliParser;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
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
    private Label _bestScheduleTimeEnd;

    @FXML
    private HBox parent;

    @FXML
    private VBox rhsBox;

    private  LineChart<Number, Number> _memoryChart;
    private  LineChart<Number, Number> _cpuChart;
    private NumberAxis _xAxisCPU;
    private NumberAxis _xAxisMem;
    private XYChart.Series _memorySeries;
    private XYChart.Series _cpuSeries;
    private Timeline _timer;

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
        setUpGanttBox();
        startTimer();

        parent.setStyle("-fx-background-color: white");
    }



    @FXML
    private void setupTextComponents(){
        Label title = new Label("Team 16 - Saadboys" );
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 24; -fx-text-fill: darkorange; -fx-font-family: 'Century Gothic'");
        HBox fill1 = new HBox();
        fill1.prefHeightProperty().bind(textCont.heightProperty().divide(8));

        Label statusUpper = new Label("Program status: ");
        statusUpper.setStyle("-fx-font-family: Consolas; -fx-font-size: 14; -fx-text-fill: white");
        _statusLower = new Label("Running");
        _statusLower.setStyle("-fx-text-fill: darkorange; -fx-font-weight: bold;-fx-font-family: Consolas; -fx-font-size: 14");

        Label elapsedUpper = new Label("Time elapsed:");
        elapsedUpper.setStyle("-fx-font-family: Consolas; -fx-font-size: 14; -fx-text-fill: white");
        _elapsedLower = new Label("");
        _elapsedLower.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-font-family: Consolas; -fx-font-size: 14");
        _elapsedLower.setPadding(new Insets(0,0,5,0));

        Label inputName = new Label("Input file:  " + CliParser.getCliParserInstance().getFileName());
        inputName.setStyle("-fx-font-family: Consolas; -fx-font-size: 14; -fx-text-fill: white");
        inputName.setPadding(new Insets(0,0,5,0));
        Label outputName = new Label("Output file: " + CliParser.getCliParserInstance().getOutputFileName());
        outputName.setStyle("-fx-font-family: Consolas; -fx-font-size: 14; -fx-text-fill: white");
        HBox fill2 = new HBox();
        fill2.prefHeightProperty().bind(textCont.heightProperty().divide(12));

        //Label procNum = new Label("Number of processors: " + CliParser.getCliParserInstance().getNumberOfProcessors());//TODO decide add these 3 or nah
        //Label taskNum or total schedules checked/iterations

        Label parallelUpper = new Label("Parallelisation:");
        parallelUpper.setStyle("-fx-font-family: Consolas; -fx-font-size: 14; -fx-text-fill: white");
        parallelUpper.setPadding(new Insets(0,0,5,0));
        Label parallelLower = new Label(" Off");
        parallelLower.setStyle("-fx-font-weight: bold; -fx-font-family: Consolas; -fx-font-size: 14; -fx-text-fill: white");
        if (CliParser.getCliParserInstance().getNumberOfCores()>1){
            parallelLower.setText(" " + CliParser.getCliParserInstance().getNumberOfCores() + " cores");
        }

        Label bestScheduleTimeStart = new Label("Current best:   ");
        bestScheduleTimeStart.setStyle("-fx-font-family: Consolas; -fx-font-size: 14; -fx-text-fill: white");
        _bestScheduleTimeEnd = new Label( " ");
        _bestScheduleTimeEnd.setStyle("-fx-font-weight: bold; -fx-font-family: Consolas; -fx-font-size: 14; -fx-text-fill: white");
        _bestScheduleTimeEnd.setPadding(new Insets(0, 0, 20, 0));


        textCont.getChildren().addAll(title, fill1, inputName, outputName, fill2, new HBox(parallelUpper, parallelLower),
                new HBox(bestScheduleTimeStart, _bestScheduleTimeEnd),  new HBox(elapsedUpper, _elapsedLower), new HBox(statusUpper, _statusLower));
        textCont.setMinWidth(Region.USE_PREF_SIZE);
        textCont.setStyle("-fx-padding: 10; -fx-background-color: darkslategray; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0)");

    }


    /*
    **Timer with increment 1s. Generates new CPU/Memory usage data for JVM every second and
    * adds this data to the series displayed on each linechart
     */
    @FXML
    private void startTimer(){
        OperatingSystemMXBean osMxBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double startTime = System.currentTimeMillis();
        int[] increment = {0};

        _timer = new Timeline(
            new KeyFrame(Duration.seconds(0.1), event -> {
                if (_algorithm.isFinished()){
                    stopTimer();
                }
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
                double currentTime = Math.round((System.currentTimeMillis() - startTime)/10)/100.0;
                if (currentTime<60){
                    _elapsedLower.setText("   " + currentTime + "s");
                } else {
                    _elapsedLower.setText("   " + (int)Math.floor(currentTime/60) + "m " + Math.round((currentTime%60)*10.0)/10.0 + "s");

                }
                increment[0]++;
            })
        );

        _timer.setCycleCount(Timeline.INDEFINITE);
        _timer.play();
    }

    private void stopTimer(){
        updateGantt();
        _statusLower.setText("Done");
        _statusLower.setStyle("-fx-text-fill: green;-fx-font-weight: bold; -fx-font-family: Consolas; -fx-font-size: 14");
        _elapsedLower.setStyle("-fx-text-fill: green;-fx-font-weight: bold; -fx-font-family: Consolas; -fx-font-size: 14");
        _timer.stop();
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
        _xAxisMem.setTickLabelFill(Color.DARKBLUE);
        yAxisMem.setTickLabelFill(Color.DARKBLUE);

        _memoryChart = new LineChart<>(_xAxisMem, yAxisMem);
        _memorySeries = new XYChart.Series();
        _memoryChart.getData().add(_memorySeries);
        _memoryChart.setLegendVisible(false);
        _memoryChart.setCreateSymbols(false);
        _memoryChart.setTitle("JVM Memory Usage");
        _memoryChart.setStyle("-fx-text-fill: darkblue");
        _memoryChart.animatedProperty().setValue(false);

        _xAxisCPU = new NumberAxis();
        final NumberAxis yAxisCPU = new NumberAxis();
        yAxisCPU.setUpperBound(100);
        yAxisCPU.setAutoRanging(false);
        _xAxisCPU.setUpperBound(60);
        _xAxisCPU.setAutoRanging(false);
        _xAxisCPU.setLabel("Time (seconds)");
        yAxisCPU.setLabel("CPU Load (%)");
        _xAxisCPU.setTickLabelFill(Color.DARKBLUE);
        yAxisCPU.setTickLabelFill(Color.DARKBLUE);

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
        timeAxis.setLabel("Time (seconds)");
        timeAxis.setTickLabelFill(Color.DARKBLUE);
        timeAxis.setMinorTickCount(4);


        // Setting processors axis
        processorsAxis.setLabel("");
        processorsAxis.setTickLabelGap(10);
        processorsAxis.setTickLabelFill(Color.DARKBLUE);
        processorsAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(processorList)));

        // Setting chart
        chart = new GanttChart<Number,String>(timeAxis,processorsAxis);
        chart.setTitle("Current Best Schedule");//TODO gantt modify look, add growth
        chart.setLegendVisible(false);
        chart.setBlockHeight(100/numberOfProcessors);

        chart.getStylesheets().add(getClass().getResource("/visualisation/visualisationutil/GanttChart.css").toExternalForm());
        //chart.getStylesheets().add(getClass().getResource("visualisationutil/GanttChart.css").toExternalForm());
        chart.setMaxHeight(400);
        chart.animatedProperty().setValue(false);
        ganttHBox.getChildren().add(chart);
        HBox.setHgrow(chart, Priority.ALWAYS);

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

        _bestScheduleTimeEnd.setText(" " + currentBestSchedule.getFinishTime() + "s");

        if (currentBestSchedule!=null) {
            // @@@@@@@@@@@@ this throws a null pointer exception
            // to get rid of null pointer remove the for loop and uncomment line 205 with the scheduled task constructor
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
            System.out.println("null schedule");
        }

    }


}
