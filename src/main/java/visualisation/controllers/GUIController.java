package visualisation.controllers;



import algorithm.Algorithm;
import algorithm.PartialSchedule;
import algorithm.ScheduledTask;
import graph.Graph;
import input.CliParser;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
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
    private Label _numSchedule;
    @FXML
    private Label _numScheduleComplete;


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
    private Button _close;
    //private final String logoTitle = "file:///+ C:\\Users\\dh\\2020assignments\\SE306\\project-1-saadboys-16\\src\\main\\resources\\visualisation\\controllers\\cry.png";

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
        setUpGanttAxis();
        startTimer();

        parent.setStyle("-fx-background-color: white");
    }



    @FXML
    private void setupTextComponents(){
        try {
            Label title = new Label("Team 16 - Saadboys" );
            title.setStyle("-fx-font-weight: bold; -fx-font-size: 24; -fx-text-fill: orange; -fx-font-family: 'Century Gothic'");
            HBox fill0 = new HBox();
            fill0.prefHeightProperty().bind(textCont.heightProperty().divide(24));
            HBox fill1 = new HBox();
            fill1.prefHeightProperty().bind(textCont.heightProperty().divide(18));
            HBox fill1v2 = new HBox();
            fill1v2.prefHeightProperty().bind(textCont.heightProperty().divide(20));
            HBox fill2 = new HBox();
            fill2.prefHeightProperty().bind(textCont.heightProperty().divide(20));
            HBox fill2v2 = new HBox();
            fill2v2.prefHeightProperty().bind(textCont.heightProperty().divide(20));

            HBox fill3 = new HBox();
            fill3.prefHeightProperty().bind(textCont.heightProperty().divide(20));
            HBox fill3v2 = new HBox();
            fill3v2.prefHeightProperty().bind(textCont.heightProperty().divide(20));
            HBox fill4 = new HBox();
            fill4.prefHeightProperty().bind(textCont.heightProperty().divide(20));
            HBox fill4v2 = new HBox();
            fill4v2.prefHeightProperty().bind(textCont.heightProperty().divide(20));
            HBox fill5 = new HBox();
            fill5.prefHeightProperty().bind(textCont.heightProperty().divide(10));

            Separator separator1 = new Separator(Orientation.HORIZONTAL);
            Separator separator2 = new Separator(Orientation.HORIZONTAL);
            Separator separator3 = new Separator(Orientation.HORIZONTAL);
            Separator separator4 = new Separator(Orientation.HORIZONTAL);


            Label inputName = new Label("Input file:  " + CliParser.getCliParserInstance().getFileName());
            inputName.setStyle("-fx-font-family: Consolas; -fx-font-size: 14; -fx-text-fill: white");
            inputName.setPadding(new Insets(0,0,5,0));
            Label outputName = new Label("Output file: " + CliParser.getCliParserInstance().getOutputFileName());
            outputName.setStyle("-fx-font-family: Consolas; -fx-font-size: 14; -fx-text-fill: white");


            Label parallelUpper = new Label("Parallelisation:");
            parallelUpper.setStyle("-fx-font-family: Consolas; -fx-font-size: 14; -fx-text-fill: white");
            parallelUpper.setPadding(new Insets(0,0,5,0));
            Label parallelLower = new Label(" Off");
            parallelLower.setStyle("-fx-font-weight: bold; -fx-font-family: Consolas; -fx-font-size: 16; -fx-text-fill: white");
            if (CliParser.getCliParserInstance().getNumberOfCores()>1){
                parallelLower.setText(" " + CliParser.getCliParserInstance().getNumberOfCores() + " threads");
            }

            Label bestScheduleTimeStart = new Label("Current best:   ");
            bestScheduleTimeStart.setStyle("-fx-font-family: Consolas; -fx-font-size: 14; -fx-text-fill: white");
            _bestScheduleTimeEnd = new Label( " ");
            _bestScheduleTimeEnd.setStyle("-fx-font-weight: bold; -fx-font-family: Consolas; -fx-font-size: 16; -fx-text-fill: white");


            Label elapsedUpper = new Label("Time elapsed:");
            elapsedUpper.setStyle("-fx-font-family: Consolas; -fx-font-size: 14; -fx-text-fill: white");
            _elapsedLower = new Label("");
            _elapsedLower.setStyle("-fx-text-fill: orange; -fx-font-weight: bold; -fx-font-family: Consolas; -fx-font-size: 16");
            _elapsedLower.setPadding(new Insets(0,0,5,0));

            Label statusUpper = new Label("Program status: ");
            statusUpper.setStyle("-fx-font-family: Consolas; -fx-font-size: 14; -fx-text-fill: white");
            _statusLower = new Label("Running");
            _statusLower.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;-fx-font-family: Consolas; -fx-font-size: 16");



            Label _numScheduleInfo = new Label("Partial Schedules: ");
            _numScheduleInfo .setStyle("-fx-font-family: Consolas; -fx-font-size: 14; -fx-text-fill: white");
            _numSchedule = new Label("Partial Schedules generated: ");
            _numSchedule.setStyle("-fx-font-weight: bold; -fx-font-family: Consolas; -fx-font-size: 16; -fx-text-fill: white");
            _numScheduleInfo.setPadding(new Insets(0,0,5,0));

            Label _numScheduleCompletedInfo = new Label("Complete Schedules: ");
            _numScheduleCompletedInfo .setStyle("-fx-font-family: Consolas; -fx-font-size: 14; -fx-text-fill: white");
            _numScheduleComplete = new Label("Complete Schedules: ");
            _numScheduleComplete.setStyle("-fx-font-weight: bold; -fx-font-family: Consolas; -fx-font-size: 16; -fx-text-fill: white");


            //_numScheduleComplete = new Label("Complete Schedules: ");
            //_numScheduleComplete.setStyle("-fx-font-weight: bold; -fx-font-family: Consolas; -fx-font-size: 16; -fx-text-fill: white");


            _close = new Button("Exit Program");
            _close.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white; -fx-font-family: Consolas; -fx-font-size: 20; -fx-font-weight: bold");
            _close.setAlignment(Pos.CENTER);
            _close.setOnAction(event -> System.exit(0));

            HBox closeButtonContainer = new HBox(fill5, _close);
            closeButtonContainer.setAlignment(Pos.CENTER);

            textCont.getChildren().addAll(fill0, new HBox(title), fill1 , separator1, fill1v2, inputName, outputName,
                    fill2, separator2, fill2v2, new HBox(parallelUpper, parallelLower),
                    new HBox(bestScheduleTimeStart, _bestScheduleTimeEnd), fill3, new HBox(_numScheduleInfo , _numSchedule),
                    new HBox(_numScheduleCompletedInfo , _numScheduleComplete) , fill3v2, new HBox(elapsedUpper, _elapsedLower),
                     new HBox(statusUpper, _statusLower),
                    fill4, separator4, fill4v2,
                    closeButtonContainer
                    );
            textCont.setMinWidth(Region.USE_PREF_SIZE);
            textCont.setStyle("-fx-padding: 10; -fx-background-color: slategray; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0)");

        } catch (Exception e){
            e.printStackTrace();
        }
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
                    updateGantt(chart);
                }
                double currentTime = Math.round((System.currentTimeMillis() - startTime)/10)/100.0;
                if (currentTime<60){
                    _elapsedLower.setText("   " + currentTime + "s");
                } else {
                    _elapsedLower.setText("   " + (int)Math.floor(currentTime/60) + "m " + Math.round((currentTime%60)*10.0)/10.0 + "s");

                }

                _numSchedule.setText(" " +_algorithm.getNumPartialSchedules());

                _numScheduleComplete.setText("" + _algorithm.getNumCompleteSchedules());

                increment[0]++;
            })
        );

        _timer.setCycleCount(Timeline.INDEFINITE);
        _timer.play();
    }

    private void stopTimer(){
        updateGantt(chart);
        _statusLower.setText("Done");
        _statusLower.setStyle("-fx-text-fill: lightgreen;-fx-font-weight: bold; -fx-font-family: Consolas; -fx-font-size: 14");
        _elapsedLower.setStyle("-fx-text-fill: lightgreen;-fx-font-weight: bold; -fx-font-family: Consolas; -fx-font-size: 14");
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
        chartHBox.setPrefHeight(300);
        chartHBox.getChildren().addAll(_memoryChart, _cpuChart);
    }





    /*
     * Taken from: https://stackoverflow.com/questions/27975898/gantt-chart-from-scratch/27978436
     */
    private void setUpGanttAxis(){


        String[] processorList = new String[CliParser.getCliParserInstance().getNumberOfProcessors()];
        for (int i = 0;i<CliParser.getCliParserInstance().getNumberOfProcessors() ;i++){
            processorList[i]="Processor "+(i+1);
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

        setUpGanttChart(timeAxis , processorsAxis);
    }

    private void setUpGanttChart(NumberAxis timeAxis , CategoryAxis processorsAxis){
        // Setting chart
        chart = new GanttChart<Number,String>(timeAxis,processorsAxis);
        chart.setTitle("Current Best Schedule");
        chart.setLegendVisible(false);
        chart.setBlockHeight(100/CliParser.getCliParserInstance().getNumberOfProcessors());

        chart.getStylesheets().add(getClass().getResource("/visualisation/visualisationutil/GUI.css").toExternalForm());

        //chart.setMaxHeight(400); ganttBox.getPrefHeight()

        chart.animatedProperty().setValue(false);
        ganttHBox.getChildren().add(chart);

        HBox.setHgrow(chart, Priority.ALWAYS);
        VBox.setVgrow(ganttHBox, Priority.ALWAYS);
    }


    /*This must be put inside the polling and update along with the other aspects. */
    private void updateGantt( GanttChart<Number,String> chart){



        // new array of series to write onto
        Series[] seriesProcessors = new Series[CliParser.getCliParserInstance().getNumberOfProcessors()];

        // initializing series obj
        for (int i=0;i<CliParser.getCliParserInstance().getNumberOfProcessors();i++){
            seriesProcessors[i]=new Series();
        }

        // for every task in schedule, write its data onto the specific series


        //PartialSchedule pSchedule = new PartialSchedule();
        // loop for all processors

        //ScheduledTask scheduledTask = new ScheduledTask(1, 2, 3);

        PartialSchedule currentBestSchedule = this._algorithm.getBestSchedule();
        String noTimeYet = "N/A";





        if (currentBestSchedule!=null) {

            _bestScheduleTimeEnd.setText(" " + currentBestSchedule.getFinishTime() + "s");

            // to get rid of null pointer remove the for loop and uncomment line 205 with the scheduled task constructor
            for (ScheduledTask scheduledTask : currentBestSchedule.getScheduledTasks()) {

                int taskProcessor = scheduledTask.getProcessor();
                XYChart.Data newData = new XYChart.Data(scheduledTask.getStartTime(), ("Processor " + (taskProcessor+1)),
                        new GanttChart.ExtraData(scheduledTask, _graph, "task-ganttchart"));
                seriesProcessors[taskProcessor].getData().add(newData);
            }

            // clear current gantt and repopulate chart with new series
            chart.getData().clear();
            for (int i=0;i<CliParser.getCliParserInstance().getNumberOfProcessors();i++) {
                chart.getData().add(seriesProcessors[i]);
            }
        } else {
            //null schedule, do nothing
            _bestScheduleTimeEnd.setText(" " + noTimeYet + "s");


        }

    }


}
