package gui;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel.mxValueChange;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEvent;
import com.mxgraph.view.mxGraph;
import model.Edge;
import model.GraphCalculator;
import model.Node;
import model.SignalFlowGraph;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends JFrame {

    //Mappers value is object array with mxCell in first index and (node or edge) object in second index
    HashMap<Integer, Object[]> nodeMapper = new HashMap<>();
    HashMap<Integer, Object[]> edgeMapper = new HashMap<>();
    mxGraph graph;
    Object parent;


    SignalFlowGraph sfg;
    int nodeID = 0;
    int edgeID = 0;

    public void addNode(int x, int y, String color) {
        mxCell vertex;
        vertex = (mxCell) graph.insertVertex(parent, "N" + nodeID, nodeID + "", x, y, 30, 30,
                "strokeColor=#000000;fillColor=#" + color + ";shape=ellipse;resizable=0");
        vertex.setId(nodeID + "");
        vertex.setEdge(false);
        vertex.setAttribute("strokeColor", "#66FF00");
        graph.refresh();
        // Adding node in back
        Node node = new Node(nodeID);
        sfg.addNode(node);
        nodeMapper.put(nodeID, new Object[]{vertex, node});
        nodeID++;
    }

    public Main() {
        super("Signal Flow Solver");
        initComponents();

        graph = new mxGraph() {
            @Override
            public boolean isCellSelectable(Object cell) {
                if (model.isEdge(cell)) {
                    return false;
                }
                return super.isCellSelectable(cell);
            }
        };

        graph.getModel().addListener(mxEvent.CHANGE, (o, mxEventObject) -> {
            Object change = ((ArrayList) mxEventObject.getProperties().get("changes")).get(0);
            if (change instanceof mxValueChange) {
                mxCell edge = (mxCell) ((mxValueChange) change).getCell();
                if (edge.isEdge()) {
                    try {
                        double gain = Double.parseDouble(((mxValueChange) change).getValue().toString());
                        ((Edge) edgeMapper.get(Integer.parseInt(edge.getId()))[1]).setGain(gain);
                    } catch (Exception e) {
                        // print error message
                        edge.setValue(((mxValueChange) change).getPrevious());
                        graph.refresh();
                    }
                }
            }

        });

        graph.addListener(mxEvent.CELL_CONNECTED, (o, mxEventObject) -> {
            if (!(boolean) mxEventObject.getProperties().get("source")) {
                mxCell graphEdge = (mxCell) mxEventObject.getProperties().get("edge");
                graphEdge.setId(edgeID + "");
                Node startNode = (Node) (nodeMapper.get(Integer.parseInt(graphEdge.getSource().getId()))[1]);
                Node endNode = (Node) (nodeMapper.get(Integer.parseInt(graphEdge.getTarget().getId()))[1]);
                Edge edge = new Edge(edgeID, startNode, endNode, 0);
                edgeMapper.put(edgeID, new Object[]{graphEdge, edge});
                edgeID++;
            }
        });
        parent = graph.getDefaultParent();
        graph.setAllowDanglingEdges(false);
        graph.getModel().beginUpdate();
        sfg = new SignalFlowGraph();
        try {
            addNode(300, 500, "00FFFF");
            addNode(1200, 500, "FF6666");
            sfg.setStart((Node) nodeMapper.get(0)[1]);
            sfg.setEnd((Node) nodeMapper.get(1)[1]);
        } finally {
            graph.getModel().endUpdate();
            graph.refresh();

        }

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        dialogPane.add(graphComponent);
    }
    private void initComponents() {
        // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
        dialogPane = new JPanel();
        JPanel contentPanel = new JPanel();
        JPanel buttonBar = new JPanel();
        JButton addNodeBtn = new JButton();
        JButton calculateBtn = new JButton();


        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
//                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 0, 85, 0};
//                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0};

                //---- addNodeBtn ----
                addNodeBtn.setText("Add Node");
                addNodeBtn.addActionListener(e -> addNode(600, 500, "FFFFFF"));
                buttonBar.add(addNodeBtn, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));

                //---- calculate----
                calculateBtn.setText("Calculate");
                calculateBtn.addActionListener(e ->calculate());
                Dimension size = calculateBtn.getPreferredSize();
                calculateBtn.setBounds(200, 100, size.width, size.height);
                buttonBar.add(calculateBtn);
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
//        setLocationRelativeTo(getOwner());
    }

    private JPanel dialogPane;
    static JFrame f;
    private GraphCalculator calc;

    public void calculate() {
        sfg.update();
        calc = new GraphCalculator(sfg.getNodes(), sfg.getPaths());

        //-----error message---

        /*JDialog d = new JDialog(f, "Error");
        JLabel l = new JLabel("Error");
        d.add(l);
        d.setSize(100, 100);
        d.setLocation(600,200);
        d.setVisible(true);*/

        //------results---

        JPanel p = new JPanel();
        JDialog d = new JDialog(f, "Results");
        JLabel l = new JLabel();
        l.setText("Transfer function= " + calc.getTransferFunction());
        p.add(l);

        for (int i=0;i<sfg.getPaths().size();i++){
            p.add(new JLabel("Delta "+(i+1)+"="));
        }
        d.add(p);
        d.setSize(200, 100);
        d.setLocation(600, 200);
        d.setVisible(true);
    }

        public static void main(String[] args) {
        Main frame = new Main();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(1000, 600);
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

}
