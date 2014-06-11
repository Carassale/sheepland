package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Classe Mappa che serve ad inizializzare e collegare tra loro i territori e le
 * strade
 *
 * @author Francesco Corsini
 */
public class Map {

    private List<Road> roads = new ArrayList<Road>();
    private List<Terrain> terrain = new ArrayList<Terrain>();

    /**
     * Viene creata la mappa leggendola da un file xml
     */
    public Map() {
        try {
            File fXmlFile = new File(".\\src\\main\\resources\\map.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList terrainList = doc.getElementsByTagName("Terrain");
            for (int temp = 0; temp < terrainList.getLength(); temp++) {
                Node terrainNode = terrainList.item(temp);

                if (terrainNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) terrainNode;
                    Terrain t = new Terrain();
                    t.setID(Integer.valueOf(eElement.getAttribute("id")));
                    t.setTerrainType(eElement.getElementsByTagName("terrainType").item(0).getTextContent());
                    if ("Sheepsbourg".equals(t.getTerrainType())) {
                        t.setSheepsbourg(true);
                    } else {
                        t.setSheepsbourg(false);
                    }
                    terrain.add(t);
                }
            }

            NodeList roadsList = doc.getElementsByTagName("Road");
            for (int temp = 0; temp < roadsList.getLength(); temp++) {
                Node roadNode = roadsList.item(temp);

                if (roadNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) roadNode;
                    Road r = new Road(Integer.valueOf(eElement.getAttribute("id")));
                    r.setFence(false);

                    int adjacentTerrain1 = Integer.valueOf(eElement.getElementsByTagName("adjacentTerrain1").item(0).getTextContent());
                    r.connectTerrainRoad(terrain.get(adjacentTerrain1));
                    int adjacentTerrain2 = Integer.valueOf(eElement.getElementsByTagName("adjacentTerrain2").item(0).getTextContent());
                    r.connectTerrainRoad(terrain.get(adjacentTerrain2));

                    r.setRoadNumber(Integer.valueOf(eElement.getElementsByTagName("roadNumber").item(0).getTextContent()));

                    roads.add(r);
                }
            }

            for (Terrain tmpTerrain : terrain) {
                for (Road road : roads) {
                    if (road.getAdjacentTerrain1().getID() == tmpTerrain.getID()) {
                        tmpTerrain.getAdjacentRoads().add(road);
                    } else if (road.getAdjacentTerrain2().getID() == tmpTerrain.getID()) {
                        tmpTerrain.getAdjacentRoads().add(road);
                    }
                }
            }

            NodeList roadToRoadList = doc.getElementsByTagName("adjacentRoadToRoad");
            for (int temp = 0; temp < roadToRoadList.getLength(); temp++) {
                Node roadNode = roadToRoadList.item(temp);

                if (roadNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) roadNode;
                    Element a = (Element) eElement.getParentNode();
                    Element b = (Element) a.getParentNode();
                    int idStrada1 = Integer.valueOf(b.getAttribute("id"));
                    int idStrada2 = Integer.valueOf(eElement.getTextContent());
                    roads.get(idStrada1).getAdjacentRoad().add(roads.get(idStrada2));
                }
            }
        } catch (ParserConfigurationException e) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } catch (SAXException e) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } catch (IOException e) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } catch (NumberFormatException e) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } catch (DOMException e) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } catch (TerrainBoundariesExeption e) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * Restituisce l'Array conenente le strade della mappa
     *
     * @return ArrayList conenente le strade della mappa
     */
    public List<Road> getRoads() {
        return roads;
    }

    /**
     * Restituisce l'Array conenente i terreni della mappa
     *
     * @return ArrayList conenente i terreni della mappa
     */
    public List<Terrain> getTerrain() {
        return terrain;
    }

}
