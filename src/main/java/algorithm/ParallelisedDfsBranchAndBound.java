package algorithm;

import graph.Graph;
import org.cliffc.high_scale_lib.NonBlockingHashSet;

import java.lang.reflect.Array;
import java.util.*;

import java.util.concurrent.*;



public class ParallelisedDfsBranchAndBound extends Algorithm {

    private final int _threads;
    private int _earliestFinishTime;
    List<Boolean> _busy = new ArrayList<Boolean>();

    ConcurrentLinkedQueue<Integer> _cacheList;
    Set<Integer> _cacheSet;
    private int _cacheCapacity;

    public ParallelisedDfsBranchAndBound(Graph dependencyGraph, int numProcessors, int threads){

        super(dependencyGraph, numProcessors);
        _threads = threads;
        for (int i = 0; i < threads; i++) {
            _busy.add(false);
        }
        _earliestFinishTime = Integer.MAX_VALUE;

        _cacheList = new ConcurrentLinkedQueue<Integer>();
        _cacheSet = new NonBlockingHashSet<Integer>();
        _cacheCapacity = 100000;

    }

    public void setIfBestSchedule(PartialSchedule ps) {
        _numCompleteSchedulesGenerated++;
        if (ps.getFinishTime() < _earliestFinishTime) {
            _bestSchedule = ps;
            _earliestFinishTime = ps.getFinishTime();
        }
    }

    public int getEarliestFinishTime() {
        return _earliestFinishTime;
    }

    public boolean updateCache(PartialSchedule ps) {

        int hashCode = ps.hashCode();
        if (_cacheSet.add(hashCode)) {
            _cacheList.add(hashCode);
            for (int i = 0; i < _cacheSet.size() - _cacheCapacity; i++) {
                _cacheSet.remove(_cacheList.remove());
            }
            return true;
        } else {
            return false;
        }

    }

    public PartialSchedule findOptimalSchedule() {

        List<PartialSchedule> rootSchedules = new ArrayList<PartialSchedule>();
        rootSchedules.add(new PartialSchedule(_dependencyGraph, _numProcessors));
        while (rootSchedules.size() < _threads) {
            rootSchedules.addAll(rootSchedules.remove(0).extend(_dependencyGraph));
        }

        ExecutorService service = Executors.newFixedThreadPool(_threads);
        List<ArrayList<PartialSchedule>> initStacks = new ArrayList<ArrayList<PartialSchedule>>();
        for (int i = 0; i < _threads; i++) {
            initStacks.add(new ArrayList<PartialSchedule>());
        }
        int remaining = rootSchedules.size();

        for (int i = remaining-1; i >= 0; i--) {
            List<PartialSchedule> initStack = initStacks.get(i % _threads);
            initStack.add(rootSchedules.remove(i));
        }

        List<DfsBranchAndBoundCallable> callables = new ArrayList<DfsBranchAndBoundCallable>();
        for (int i = 0; i < _threads; i++) {
            callables.add(new DfsBranchAndBoundCallable(this, _numProcessors, initStacks.get(i), i));
        }

        try {
            service.invokeAll(callables);
            service.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return _bestSchedule;

    }

}

