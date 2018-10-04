package Modelos.Thompson;

import java.util.ArrayList;

/**
 *
 * @author Jonathan Ramírez
 */
public class EstadosThompson {

    protected ArrayList<NodeThompson> estados = new ArrayList<>();

    public EstadosThompson() {

    }

    public void addEstadosToArray(NodeThompson e) {
        estados.add(e);
    }

    class NodeThompson {

        protected ArrayList<NodeLambda> cierreAlpha = new ArrayList<>();

        public NodeThompson() {

        }

        public void addNodesToArray(NodeLambda e) {
            cierreAlpha.add(e);
        }
    }
}
