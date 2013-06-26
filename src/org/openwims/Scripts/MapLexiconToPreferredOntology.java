/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openwims.Scripts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import org.openwims.Objects.Lexicon.Sense;
import org.openwims.Objects.Lexicon.Word;
import org.openwims.WIMGlobals;

/*

select ontology_preferred_mapping.score,
       ontology_preferred_mapping.sense,
       ontology_preferred_mapping.concept,
       senses.definition
from ontology_preferred_mapping 
join senses on 
ontology_preferred_mapping.sense=senses.id
order by score desc;

 */

/*

select ontology_preferred_mapping.score,
       ontology_preferred_mapping.sense,
       ontology_preferred_mapping.concept,
       senses.definition
from ontology_preferred_mapping 
join senses on 
ontology_preferred_mapping.sense=senses.id
order by score desc;

 */

//Delete all of these, they are FR
//select * from senses where definition ~ '\([0-9][0-9]*-[0-9]*\)' order by word asc;

//I think that everything that says "Old World x" and "New World x" is going to be an animal

//Delete all of these, they are FR (locations that start with a capital letter)
/*

select foo.id from 
(select substring(id, '@[0-9a-z\-]*') as concept,
        trim(leading ':' from substring(id, ':[A-z0-9\-]*')) as token,
        id, definition from senses) as foo
where foo.concept in
 (select concept from ontology where parent ilike '%@location%') 
and
 (foo.definition ilike 'a %' or foo.definition ilike 'an %')
and
 (foo.token ~ '[A-Z][A-z0-9\-]*');

 */



/**
 *
 * @author jesseenglish
 */
public class MapLexiconToPreferredOntology {
    
    private static String[] stopwords = { "and", "or", "the", "of", "in", "with", "a", "to", "be", "is", "for", "on", "it", ";", ",", "." };
    
    public static void main(String[] args) throws Exception {
        String url = "jdbc:postgresql://localhost/OpenWIMs";
        String user = "jesse";
        String pass = "";
        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(url, user, pass);
        Statement stmt = conn.createStatement();
        
        stmt.execute("DELETE FROM ontology_preferred_mapping;");
        
        
        HashMap<String, String> ontology = loadPreferredOntology(stmt);
        
        LinkedList<String> words = WIMGlobals.lexicon().roots();
        
        for (int i = 0; i < 15000; i++) {
            String word = words.get(i);
            
            Word w = WIMGlobals.lexicon().word(word);
            for (Sense sense : w.listSenses()) {
                String gloss = ImportPreferredOntology.gloss(sense.getDefinition());
                
                int bestIntersection = -1;
                String bestMatch = "none";
                
                for (String concept : ontology.keySet()) {
//                    if (concept.equalsIgnoreCase("@mathematical-object") && sense.getId().startsWith("@twenty-four")) {
//                        System.out.println("debug");
//                    }
                    
                    String cgloss = ontology.get(concept);
                    int intersection = intersection(gloss, cgloss);
                    if (intersection > bestIntersection) {
                        bestIntersection = intersection;
                        bestMatch = concept;
                    }
                }
                
                double score = (double)bestIntersection / (double)gloss.split(" ").length;
                
                if (score <= 0) {
                    if (sense.pos().equalsIgnoreCase("n")) {
                        bestMatch = "@object";
                    } else if (sense.pos().equalsIgnoreCase("v")) {
                        bestMatch = "@event";
                    } else {
                        bestMatch = "@object";
                    }
                }
                
                if (WIMGlobals.ontology().isDescendant(sense.concept(), "@plant")) {
                    bestMatch = "@plant";
                    score = 0.65;
                }
                
                if (sense.getDefinition().startsWith("Old World") || sense.getDefinition().startsWith("New World")) {
                    bestMatch = "@animal";
                    score = 0.65;
                }
                
                String query = "INSERT INTO ontology_preferred_mapping (sense, concept, score) VALUES ('" + sense.getId().replaceAll("'", "''") + "', '" + bestMatch.replaceAll("'", "''") + "', " + score + ");";
                stmt.execute(query);
                
                //System.out.println(score + ": Mapping " + sense.getId() + " to " + bestMatch + " (" + sense.getDefinition() + ")");
            }
        }
        
        
        stmt.close();
        conn.close();
    }
    
    private static int intersection(String g1, String g2) {
        String[] g1parts = g1.split(" ");
        String[] g2parts = g2.split(" ");
        
        int intersection = 0;
        
        OUTER:
        for (String g1part : g1parts) {
            
            for (String stopword : stopwords) {
                if (g1part.equalsIgnoreCase(stopword)) {
                    continue OUTER;
                }
            }
            
            
            INNER:
            for (String g2part : g2parts) {
                if (g1part.equalsIgnoreCase(g2part)) {
                    intersection += 1;
                    break INNER;
                }
            }
        }
        
        return intersection;
    }
    
    
    private static HashMap<String, String> loadPreferredOntology(Statement stmt) throws Exception {
        HashMap<String, String> ontology = new HashMap();
        
        
        
        ResultSet rs = stmt.executeQuery("SELECT concept, gloss FROM ontology_preferred;");
        while (rs.next()) {
            ontology.put(rs.getString("concept"), rs.getString("gloss"));
        }
        
        return ontology;
    }
    
}
