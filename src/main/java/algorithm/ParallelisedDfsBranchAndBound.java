package algorithm;

import graph.Graph;
import org.apache.commons.lang3.mutable.MutableByte;
import org.omg.CORBA.TIMEOUT;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class  ParallelisedDfsBranchAndBound {

    private Graph _dependencyGraph;
    private int _numProcessors;
    private int _threads;
    List<Boolean> _busy = new ArrayList<Boolean>();

    private List<PartialSchedule> _stack = new ArrayList<PartialSchedule>();
    //private LinkedBlockingDeque<PartialSchedule> _stack = new LinkedBlockingDeque<PartialSchedule>();

    static public PartialSchedule _bestSchedule = null;

    static public int _earliestFinishTime = Integer.MAX_VALUE;

    public  ParallelisedDfsBranchAndBound(Graph dependencyGraph, int numProcessors, int threads){

        _dependencyGraph = dependencyGraph;
        _numProcessors = numProcessors;
        _threads = threads;
        for(int i=0;i<threads;i++){
            _busy.add(false);

        }
    }


    synchronized  PartialSchedule popOffStack(){
        if (_stack.isEmpty()){
            return null;
        }
        return _stack.remove(_stack.size() -1);

    }


    public void addAllToStack(List<PartialSchedule> psList){

        _stack.addAll(psList);

    }

    public void setIfBestSchedule(PartialSchedule ps, int finishTime){

        if (finishTime < _earliestFinishTime){
            _earliestFinishTime = finishTime;
            _bestSchedule = ps;
        }
        //System.out.println(" Stack Size: " + _stack.size());
    }





    public PartialSchedule findOptimalSchedule() {


        _stack.add(new PartialSchedule(_dependencyGraph, _numProcessors));
        ExecutorService service = Executors.newFixedThreadPool(_threads);
        List<helperForRunnable> runnables = new ArrayList<helperForRunnable>();
        for(int i=0; i<_threads;i++) {
            service.submit(new helperForRunnable(_stack, _numProcessors, _busy, i, _dependencyGraph, this));
        }


        service.shutdown();
        try {

            service.awaitTermination(120, TimeUnit.HOURS);
        } catch (InterruptedException e){
            e.printStackTrace();
        }


        System.out.println("Stack Exit size: " + _stack.size());
        return _bestSchedule;
    }


}

