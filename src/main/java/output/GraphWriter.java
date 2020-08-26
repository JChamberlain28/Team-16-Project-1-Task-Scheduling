package output;

import algorithm.PartialSchedule;
import graph.Vertex;

public class GraphWriter {



    public void setGraph(PartialSchedule pf){
        Vertex vx = pf.getTask();
        vx.setProcessorNumber(pf.getProcessor());
        vx.setStartTime(pf.getStartTime());
        if (pf.getTask().getIncomingVertices().size() != 0){
            setGraph(pf.getParent());
        }


    }
}