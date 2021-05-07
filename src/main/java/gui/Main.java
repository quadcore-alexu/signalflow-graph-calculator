package gui;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.layout.orthogonal.mxOrthogonalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel.mxValueChange;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.view.mxEdgeStyle;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import interfaces.IEdge;
import interfaces.ISignalFlowGraph;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JFrame {

    //Mappers value is object array with mxCell in first index and (node or edge) object in second index
    HashMap<Integer, Object[]> nodeMapper = new HashMap<>();
    HashMap<Integer, Object[]> edgeMapper = new HashMap<>();
    mxGraph graph;
    Object parent;


    ISignalFlowGraph sfg;
    int nodeID = 0;
    int edgeID = 0;

    public boolean checkConnectivity(){
        sfg.setStart(null);
        sfg.setEnd(null);
//        mxEdgeStyle.mxEdgeStyleFunction d
        System.out.println("MAMA" + graph.getDefaultLoopStyle().toString());
        Object[] cells = graph.getChildVertices(graph.getDefaultParent());
        for (Object c: cells)
        {   mxCell cell = (mxCell) c;
            if(cell.getEdgeCount() == 0)
                return false;
            //boolean flags to check if a cell is a source or/and target
            boolean isSource = false;
            boolean isTarget = false;
            System.out.println("id: " + cell.getId() );
            for (int i = 0; i < cell.getEdgeCount(); i++) {
                mxICell source = ((mxCell) cell.getEdgeAt(i)).getSource();
                mxICell target = ((mxCell) cell.getEdgeAt(i)).getTarget();
                //self loop
                if(source.getId().equals(target.getId()))
                    continue;
                if(source.getId().equals(cell.getId()))
                    isSource = true;
                if(target.getId().equals(cell.getId()))
                    isTarget = true;
            }
            if(isSource && !isTarget){ // starting node
                //if starting node isn't set yet
                if(sfg.getStart() == null)
                    sfg.setStart((Node) (nodeMapper.get(Integer.parseInt(cell.getId()))[1]));
                else return false;
            }
            if(isTarget && !isSource){ // ending node
                //if ending node isn't set yet
                if(sfg.getEnd() == null)
                    sfg.setEnd((Node) (nodeMapper.get(Integer.parseInt(cell.getId()))[1]));
                else return false;
            }

        }
        return sfg.getStart() != null && sfg.getEnd() != null;
    }
    public void addNode(int x, int y, String color) {
        graph.getModel().beginUpdate();
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
        graph.getModel().endUpdate();

    }

    public Main() {
        super("Signal Flow Solver");
        initComponents();
        //allowed selection of edges so that the user can modify their position
        graph = new mxGraph();
        graph.setEdgeLabelsMovable(false);
        graph.setAllowLoops(true);
        graph.getModel().addListener(mxEvent.CHANGE, (o, mxEventObject) -> {
            Object change = ((ArrayList) mxEventObject.getProperties().get("changes")).get(0);
            if (change instanceof mxValueChange) {
                mxCell edge = (mxCell) ((mxValueChange) change).getCell();
                if (edge.isEdge()) {
                    try {
                        double gain = Double.parseDouble(((mxValueChange) change).getValue().toString());
                        ((Edge) edgeMapper.get(Integer.parseInt(edge.getId()))[1]).setGain(gain);
                        if(gain == 0)
                            throw new Exception();
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
                mxParallelEdgeLayout parallelEdgeLayout = new mxParallelEdgeLayout(graph);
                parallelEdgeLayout.execute(graph.getDefaultParent());
                mxCell graphEdge = (mxCell) mxEventObject.getProperties().get("edge");

                System.out.println(mxConstants.EDGESTYLE_ENTITY_RELATION);

                graphEdge.setId(edgeID + "");
                Node startNode = (Node) (nodeMapper.get(Integer.parseInt(graphEdge.getSource().getId()))[1]);
                Node endNode = (Node) (nodeMapper.get(Integer.parseInt(graphEdge.getTarget().getId()))[1]);
                if(startNode == endNode)
                    graphEdge.setStyle("strokeColor=#000000;rounded=true;");
                for (IEdge edge: startNode.getOutEdges()) {
                    if (edge.getEndNode().equals(endNode)) {
                        graphEdge.removeFromParent();
                        return;
                    }
                }
                Edge edge = new Edge(edgeID, startNode, endNode, 1);
                edgeMapper.put(edgeID, new Object[]{graphEdge, edge});
                graphEdge.setValue("1");
                edgeID++;
            }
        });
        parent = graph.getDefaultParent();
        graph.setAllowDanglingEdges(false);

        Map<String, Object> style = graph.getStylesheet().getDefaultEdgeStyle();
        style.put(mxConstants.STYLE_SPACING_BOTTOM,5);
        style.put(mxConstants.STYLE_SPACING_LEFT,5);
        mxStylesheet stylesheet = graph.getStylesheet();
        Map<String, Object> edgeStyle = stylesheet.getDefaultEdgeStyle();
        edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_BLOCK);
        edgeStyle.put(mxConstants.STYLE_STROKECOLOR,"#000000");
        edgeStyle.put(mxConstants.STYLE_FONTCOLOR,"#000000");
        graph.getModel().beginUpdate();
        sfg = new SignalFlowGraph();
        try {
            addNode(300, 500, "00FFFF");
            addNode(1200, 500, "FF6666");
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
        JPanel titleBar = new JPanel();
        JButton addNodeBtn = new JButton();
        JButton calculateBtn = new JButton();
        JButton resetBtn = new JButton();
        JLabel title = new JLabel();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPanel.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());

                //---- addNodeBtn ----
                addNodeBtn.setText("Add Node");
                addNodeBtn.addActionListener(e -> addNode(600, 500, "FFFFFF"));
                buttonBar.add(addNodeBtn, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));

                //---- calculate----
                calculateBtn.setText("Calculate");
                calculateBtn.addActionListener(e ->calculate());
                buttonBar.add(calculateBtn, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));

                //---- Reset----
                resetBtn.setText("Reset");
                resetBtn.addActionListener(e -> reset());
                buttonBar.add(resetBtn, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);

            //---title---
            {
                title.setText("Signal Flow Graph Calculator");
                title.setFont(new Font(Font.SERIF, Font.BOLD, 34));
                title.setForeground(Color.decode("#b34180"));
                titleBar.setLayout(new GridBagLayout());
                titleBar.setBorder(new EmptyBorder(0, 0, 12, 0));
                titleBar.add(title, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
                JLabel slogan=new JLabel("As simple as that!");
                slogan.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
                slogan.setForeground(Color.decode("#f8a1d1"));
                titleBar.add(slogan, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.CENTER,
                        new Insets(0, 0, 0, 5), 0, 0));
            }
            dialogPane.add(titleBar,BorderLayout.NORTH);

        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
//        setLocationRelativeTo(getOwner());
    }

    public void reset() {
        graph.removeCells(graph.getChildCells(graph.getDefaultParent()));
        nodeMapper.clear();
        edgeMapper.clear();
        nodeID = 0;
        edgeID = 0;
        selectedEdges = null;
        sfg = new SignalFlowGraph();
        try {
            addNode(300, 500, "00FFFF");
            addNode(1200, 500, "FF6666");
        } finally {
            graph.getModel().endUpdate();
            graph.refresh();
        }
    }

    private JPanel dialogPane;
    static JFrame f;
    private List<IEdge> selectedEdges;


    public void calculate() {
        if(checkConnectivity()){

            sfg.calculatePathsNLoops();
            DeltaCalculator calc = new DeltaCalculator(sfg.getNodes(), sfg.getPaths());

            //------results---

            JPanel p = new JPanel();
            p.setLayout(new GridBagLayout());
            JDialog d = new JDialog(f, "Results");
            int offset = 0;

            //forward paths
            for (int i=0;i<sfg.getPaths().size();i++){
                JLabel l = new JLabel("Path "+(i+1)+"="+ sfg.getPaths().get(i));

                // set the border of this component
                l.setBorder(BorderFactory.createLineBorder(Color.decode("#ff5e78"), 1));
                int finalI = i;
                l.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        clearTracing();
                        List<IEdge> edges = sfg.getPaths().get(finalI).getEdges();
                        for (IEdge edge: edges) {
                            ((mxCell)edgeMapper.get(edge.getId())[0]).setStyle("strokeColor=#ff5e78;");
                        }
                        graph.refresh();
                        selectedEdges = edges;
                    }
                });
                p.add(l, new GridBagConstraints(1,i+1,1,1,0,0,GridBagConstraints.CENTER, GridBagConstraints.BOTH,new Insets(0, 0, 0, 5), 0, 0));
                offset = i+1;
            }

            //loops
            for (int i=0;i<sfg.getLoops().size();i++){
                JLabel l = new JLabel("Loop "+(i+1)+"="+ sfg.getLoops().get(i));

                // set the border of this component
                l.setBorder(BorderFactory.createLineBorder(Color.decode("#ff5e78"), 1));
                int finalI = i;
                l.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        clearTracing();
                        List<IEdge> edges = sfg.getLoops().get(finalI).getEdges();
                        for (IEdge edge: edges) {
                            ((mxCell)edgeMapper.get(edge.getId())[0]).setStyle("strokeColor=#ff5e78;");
                        }
                        if (edges.size() == 1) {
                            ((mxCell) edgeMapper.get(edges.get(0).getId())[0]).setStyle("strokeColor=#ff5e78;rounded=true;");
                        }
                        graph.refresh();
                        selectedEdges = edges;
                    }
                });
                p.add(l, new GridBagConstraints(1,i+1+offset,1,1,0,0,GridBagConstraints.CENTER, GridBagConstraints.BOTH,new Insets(0, 0, 0, 5), 0, 0));
                offset += i+1;
            }
            //non-touching
            for (int i=0;i<sfg.getNonTouchingLoops().size();i++){
                p.add(new JLabel((i+2)+" Non touching loops "+"="+ sfg.getNonTouchingLoops().get(i+2).toString()),new GridBagConstraints(1,i + offset + 1,1,1,0,0,GridBagConstraints.CENTER, GridBagConstraints.BOTH,new Insets(0, 0, 0, 5), 0, 0));
                offset += i +1;
            }
            //delta
            JLabel delta = new JLabel();
            delta.setText("Delta= " + calc.getDelta());
            p.add(delta,new GridBagConstraints(1,offset+1,1,1,0,0,GridBagConstraints.CENTER, GridBagConstraints.BOTH,new Insets(0, 0, 0, 5), 0, 0));

            //delta i
            for (int i=0;i<sfg.getPaths().size();i++){
                p.add(new JLabel("Delta "+(i+1)+"="+ calc.getDelta(i)),new GridBagConstraints(i+1,i + offset+2,1,1,0,0,GridBagConstraints.CENTER, GridBagConstraints.BOTH,new Insets(0, 0, 0, 5), 0, 0));
                offset += i +2;
            }
            //transfer function
            JLabel transfer = new JLabel();
            transfer.setText("Transfer function= " + calc.getTransferFunction());
            p.add(transfer,new GridBagConstraints(1, offset+1,1,1,0,0,GridBagConstraints.CENTER, GridBagConstraints.BOTH,new Insets(0, 0, 0, 5), 0, 0));
            d.add(p);
            d.setSize(500, 250);
            d.setLocation(600, 200);
            d.setVisible(true);
        }
        else{
            //display error
            JDialog d = new JDialog(f, "Error");
            JLabel l = new JLabel("Error");
            d.add(l);
            d.setSize(100, 100);
            d.setLocation(600,200);
            d.setVisible(true);
        }
    }

    private void clearTracing() {
        if (selectedEdges != null) {
            if (selectedEdges.size() == 1) {
                ((mxCell) edgeMapper.get(selectedEdges.get(0).getId())[0]).setStyle("strokeColor=#FA8072;rounded=true;");
            } else {
                for (IEdge edge : selectedEdges) {
                    ((mxCell) edgeMapper.get(edge.getId())[0]).setStyle("strokeColor=#0;");
                }
            }
        }
    }

    public static void main(String[] args) {
        Main frame = new Main();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(1000, 600);
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

}
