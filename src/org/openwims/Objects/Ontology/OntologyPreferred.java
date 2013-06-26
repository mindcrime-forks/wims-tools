/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openwims.Objects.Ontology;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author jesseenglish
 */
public class OntologyPreferred {
    
    private static Connection conn = null;
    private HashMap<String, LinkedList<String>> descendants;
    
    public static Connection conn() throws Exception {
        if (OntologyPreferred.conn == null) {
            String url = "jdbc:postgresql://localhost/OpenWIMs";
            String user = "jesse";
            String pass = "";

            Class.forName("org.postgresql.Driver");
            
            OntologyPreferred.conn = DriverManager.getConnection(url, user, pass);
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {                    
                    try {
                        OntologyPreferred.conn.close();
                    } catch (Exception err) {
                        err.printStackTrace();
                    }
                }
            }));
        }
        
        return OntologyPreferred.conn;
    }
    
    public OntologyPreferred() {
        this.descendants = new HashMap();
        
        try {
            String query = "SELECT concept, parent FROM ontology_preferred;";
            Statement stmt = Ontology.conn().createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String concept = rs.getString("concept");
                String parent = rs.getString("parent");
                
                LinkedList<String> children = this.descendants.get(parent);
                if (children == null) {
                    children = new LinkedList();
                    this.descendants.put(parent, children);
                }
                
                children.add(concept);
            }
            
            stmt.close();
            
        } catch (Exception err) {
            err.printStackTrace();
        }
        
    }
    
    public LinkedList<String> concepts() {
        return new LinkedList(this.descendants.keySet());
    }
    
    public LinkedList<String> children(String concept) {
        if (this.descendants.get(concept) == null) {
            return new LinkedList();
        }
        
        return new LinkedList(this.descendants.get(concept));
    }
    
    public boolean isDescendant(String concept, String ancestor) {
        if (concept == null || ancestor == null) {
            return false;
        }
        
        if (concept.equalsIgnoreCase(ancestor)) {
            return true;
        }
        
        for (String child : this.descendants.get(ancestor)) {
            if (concept.equalsIgnoreCase(child)) {
                return true;
            }
            
            if (isDescendant(concept, child)) {
                return true;
            }
        }
        
        return false;
    }
    
}
