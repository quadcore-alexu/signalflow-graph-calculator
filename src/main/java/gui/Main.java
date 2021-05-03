package gui;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Main extends JFrame {

    //Linked list of nodes
    mxGraph graph;
    Object parent;
    long id = 0;
    public void addNode() {
        mxCell vertex;
        vertex = (mxCell) graph.insertVertex(parent, "N"+id, "N "+id+"",200,200,30, 30,
                "strokeColor=#000000;fillColor=#FFFFFF;shape=ellipse;resizable=0");
        vertex.setEdge(false);
        vertex.setAttribute("strokeColor","#66FF00");
        graph.refresh();
        id ++;
    }

    public Main() {
        super("Signal Flow Solver");
        initComponents();

        graph = new mxGraph(){
            @Override
            public boolean isCellSelectable(Object cell)
            {
                if (model.isEdge(cell))
                {
                    return false;
                }

                return super.isCellSelectable(cell);
            }
        };
        parent = graph.getDefaultParent();
        graph.setAllowDanglingEdges(false);
        graph.getModel().beginUpdate();

        try
        {
            addNode();
        }
        finally
        {
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
                addNodeBtn.addActionListener(e -> addNode());
                buttonBar.add(addNodeBtn, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));


            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
//        setLocationRelativeTo(getOwner());
    }

    private JPanel dialogPane;

    public static void main(String[] args) {
        Main frame = new Main();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(1000, 600);
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

}
