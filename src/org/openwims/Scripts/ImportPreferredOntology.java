/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openwims.Scripts;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.openwims.Stanford.StanfordHelper;

/**
 *
 * @author jesseenglish
 */
public class ImportPreferredOntology {
    
    public static void main(String[] args) throws Exception {
        String url = "jdbc:postgresql://localhost/OpenWIMs";
        String user = "jesse";
        String pass = "";
        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(url, user, pass);
        Statement stmt = conn.createStatement();
        
        stmt.execute("DELETE FROM ontology_preferred");
        
        
        InputStream in = ImportLexicalStructures.class.getResourceAsStream("/org/openwims/Assets/ontology");
        BufferedReader input = new BufferedReader(new InputStreamReader(in));

        HashMap<Integer, String> indents = new HashMap();
        
        String line = null;
        while ((line = input.readLine()) != null) {
            int indent = 0;
            while (line.charAt(indent) == ' ') {
                indent++;
            }
            
            line = line.trim();
            String[] parts = line.split(":");
            String concept = parts[0].trim();
            String definition = parts[1].trim();
            String gloss = gloss(definition);
            String parent = indents.get(indent - 1);
            
            indents.put(indent, concept);
            
            String query = "INSERT INTO ontology_preferred (concept, parent, definition, gloss) VALUES ('" + clean(concept) + "', '" + clean(parent) + "', '" + clean(definition) + "', '" + clean(gloss) + "');";
            stmt.execute(query);
        }
        
        
        stmt.close();
        conn.close();
    }
    
    public static String gloss(String input) {
        String output = "";
        
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");
        
        Annotation a = StanfordHelper.annotate(input, props);
        
        List<CoreLabel> tokens = a.get(CoreAnnotations.TokensAnnotation.class);
        for (CoreLabel token : tokens) {
            String lemma = token.getString(CoreAnnotations.LemmaAnnotation.class);
            output += lemma + " ";
        }
        
        return output.trim();
    }
    
    private static String clean(String input) {
        if (input == null) {
            input = "";
        }
        
        return input.replaceAll("'", "''");
    }
    
}
