package visualisation.controllers;



import algorithm.PartialSchedule;
import algorithm.ScheduledTask;
import com.sun.tracing.dtrace.DependencyClass;
import input.CliParser;
import javafx.collections.FXCollections;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.paint.Color;
import visualisation.GanttChart;

import com.sun.management.OperatingSystemMXBean;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.*;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;


import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;


public class GUIController {

    private GanttChart<Number,String> chart;


    @FXML
    private HBox chartHBox;

    @FXML
    private HBox ganttHBox;

    private XYChart.Series _memorySeries;
    private XYChart.Series _cpuSeries;







    @FXML
    public void initialize() {
        setupUsageCharts();
        startTimer();
        setUpGanttBox();


        // this should be inside polling but left for testing
        updateGantt();

    }





    /*
    **Timer with increment 1s. Generates new CPU/Memory usage data for JVM every second and
    * adds this data to the series displayed on each linechart
     */
    @FXML
    private void startTimer(){
        Timer timer = new Timer();
        OperatingSystemMXBean osMxBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        final int[] increment = {0};
        timer.schedule(new TimerTask() {//TODO consider using javafx timeline instead
            @Override
            public void run() {
                increment[0]++;
                Platform.runLater(() -> {
                    _memorySeries.getData().add(new XYChart.Data<>(increment[0],(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000000));
                    _cpuSeries.getData().add(new XYChart.Data<>(increment[0], (osMxBean.getProcessCpuLoad())*100));

                });
            }
        }, 0, 1000);
    }

    /*
    ** Creation of linecharts for both cpu/memory usage, and addition of these to hbox container
     */
    @FXML
    private void setupUsageCharts(){
        final NumberAxis xAxisMem = new NumberAxis();
        final NumberAxis yAxisMem = new NumberAxis();//TODO set upper bound for y axis
        xAxisMem.setLabel("Time (seconds)");
        yAxisMem.setLabel("Memory Usage (MB)");
        final LineChart<Number, Number> memoryChart = new LineChart<>(xAxisMem, yAxisMem);
        _memorySeries = new XYChart.Series();
        memoryChart.getData().add(_memorySeries);
        memoryChart.setLegendVisible(false);

        final NumberAxis xAxisCPU = new NumberAxis();
        final NumberAxis yAxisCPU = new NumberAxis();
        yAxisCPU.setUpperBound(100);
        yAxisCPU.setAutoRanging(false);
        xAxisCPU.setLabel("Time (seconds)");
        yAxisCPU.setLabel("CPU Usage (%)");
        final LineChart<Number, Number> cpuChart = new LineChart<>(xAxisCPU, yAxisCPU);
        _cpuSeries = new XYChart.Series();
        cpuChart.getData().add(_cpuSeries);
        cpuChart.setLegendVisible(false);

        chartHBox.getChildren().addAll(memoryChart, cpuChart);
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
        chart.setTitle("best schedule");
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

        PartialSchedule bestSchedule = null;  //??!!!!!!!!!!!!!!!!!!!!!! SOMEHOW GET SCHEULE HERE
        // @@@@@@@@@@@@ this throws a null poiner exception
        // to get rid of null pointer remove the for loop and unco,,emt line 205 with the schedualed task construter
        for ( ScheduledTask scheduledTask:  bestSchedule.getScheduledTasks()) {
            int taskProcessor = scheduledTask.getProcessor();
            XYChart.Data newData = new XYChart.Data(scheduledTask.getStartTime(), ("Processor " + taskProcessor),
                    new GanttChart.ExtraData(scheduledTask, "status-red"));
            seriesProcessors[taskProcessor].getData().add(newData);
        }


        // clear current gantt and repopulate chart with new series
        chart.getData().clear();
        for (Series series: seriesProcessors){
            chart.getData().add(series);
        }


    }




}
