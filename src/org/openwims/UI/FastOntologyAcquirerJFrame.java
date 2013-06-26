/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openwims.UI;

import com.jesseenglish.swingftfy.extensions.FNode;
import com.jesseenglish.swingftfy.extensions.FTree;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultTreeModel;
import org.openwims.Objects.Ontology.OntologyPreferred;

/**
 *
 * @author jesseenglish
 */
public class FastOntologyAcquirerJFrame extends javax.swing.JFrame {
    
    private OntologyPreferred ontology;
    private Connection conn;
    private String cursense;

    /**
     * Creates new form FastOntologyAcquirerJFrame
     */
    public FastOntologyAcquirerJFrame() {
        initComponents();
        this.setSize(800, 600);
        this.ontology = new OntologyPreferred();
        
        this.cursense = null;
        
        String url = "jdbc:postgresql://localhost/OpenWIMs";
        String user = "jesse";
        String pass = "";
        
        try {
            Class.forName("org.postgresql.Driver");
            this.conn = DriverManager.getConnection(url, user, pass);
        } catch (Exception ex) {
            Logger.getLogger(FastOntologyAcquirerJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.HistoryJLabel.setText("history: ");
        
        
        refresh();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ObjectFTree = new com.jesseenglish.swingftfy.extensions.FTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        EventFTree = new com.jesseenglish.swingftfy.extensions.FTree();
        SenseJLabel = new javax.swing.JLabel();
        DefinitionJLabel = new javax.swing.JLabel();
        HistoryJLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        jScrollPane1.setViewportView(ObjectFTree);

        jPanel1.add(jScrollPane1);

        jScrollPane2.setViewportView(EventFTree);

        jPanel1.add(jScrollPane2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel1, gridBagConstraints);

        SenseJLabel.setText("@sense:tomap-pos-1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(SenseJLabel, gridBagConstraints);

        DefinitionJLabel.setText("whose definition is here");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(DefinitionJLabel, gridBagConstraints);

        HistoryJLabel.setForeground(new java.awt.Color(51, 51, 51));
        HistoryJLabel.setText("previously mapped: @othersense:tomap-pos-1 -> @concept");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(HistoryJLabel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FastOntologyAcquirerJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FastOntologyAcquirerJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FastOntologyAcquirerJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FastOntologyAcquirerJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FastOntologyAcquirerJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FastOntologyAcquirerJFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel DefinitionJLabel;
    private com.jesseenglish.swingftfy.extensions.FTree EventFTree;
    private javax.swing.JLabel HistoryJLabel;
    private com.jesseenglish.swingftfy.extensions.FTree ObjectFTree;
    private javax.swing.JLabel SenseJLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

    public void refresh() {
        loadTree(this.ObjectFTree, "@object");
        loadTree(this.EventFTree, "@event");
        next();
        
        this.validate();
        this.repaint();
    }
    
    public void loadTree(FTree tree, String concept) {
        ConceptNode root = new ConceptNode(concept);
        DefaultTreeModel model = new DefaultTreeModel(root);
        tree.setModel(model);
        tree.expandAll(true);
        
        tree.validate();
        tree.repaint();
    }
    
    public void next() {
        String query = "select ontology_preferred_mapping.score,\n" +
                       "       ontology_preferred_mapping.sense,\n" +
                       "       ontology_preferred_mapping.concept,\n" +
                       "       senses.definition\n" +
                       "from ontology_preferred_mapping \n" +
                       "join senses on \n" +
                       "ontology_preferred_mapping.sense=senses.id\n" +
                       "where ontology_preferred_mapping.score = 0 limit 1;";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String sense = rs.getString("sense");
                String definition = rs.getString("definition");
                
                this.SenseJLabel.setText(sense);
                this.DefinitionJLabel.setText(definition);
                
                cursense = sense;
            }
        
            stmt.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
        
        this.validate();
        this.repaint();
    }
    
    public void commit(String concept) {
        if (this.cursense == null) {
            return;
        }
        
        String query = "UPDATE ontology_preferred_mapping SET concept = '" + concept.replaceAll("'", "''") + "', score = 0.75 WHERE sense = '" + this.cursense.replaceAll("'", "''") + "';";
        
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
            System.out.println(query);
            stmt.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
        
        history(this.cursense, concept);
        
        refresh();
    }
    
    public void history(String sense, String concept) {
        this.HistoryJLabel.setText("history: " + sense + " -> " + concept);
        
        this.validate();
        this.repaint();
    }
    
    
    private class ConceptNode extends FNode {
        
        private String concept;

        public ConceptNode(String concept) {
            super(concept);
            this.concept = concept;
            
            for (String child : ontology.children(concept)) {
                this.add(new ConceptNode(child));
            }
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            commit(concept);
        }
        
    }

}
