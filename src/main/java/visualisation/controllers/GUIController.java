package visualisation.controllers;





import com.sun.management.OperatingSystemMXBean;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.*;

import java.lang.management.ManagementFactory;
import java.util.Timer;
import java.util.TimerTask;


public class GUIController {

    @FXML
    private HBox chartHBox;

    private XYChart.Series _memorySeries;
    private XYChart.Series _cpuSeries;

    @FXML
    public void initialize() {
        setupUsageCharts();
        startTimer();
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

}
