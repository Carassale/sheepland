package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadXMLFile {

    public static void main(String argv[]) {

        List<Road> roads = new ArrayList<Road>();
        List<Terrain> terrain = new ArrayList<Terrain>();

        try {
            File fXmlFile = new File("C:\\file.xml");
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
                    if (Boolean.valueOf(eElement.getElementsByTagName("sheepsbourg").item(0).getTextContent())) {
                        t.setSheepsbourg(true);
                    } else {
                        t.setSheepsbourg(false);
                    }
                    t.setTerrainType(eElement.getElementsByTagName("terrainType").item(0).getTextContent());
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

            NodeList roadToRoadList = doc.getElementsByTagName("adjacentRoadToRoad>");
            for (int temp = 0; temp < roadToRoadList.getLength(); temp++) {
                Node roadNode = roadToRoadList.item(temp);

                if (roadNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) roadNode;
                    int idStrada1 = Integer.valueOf(((Element) eElement.getParentNode()).getAttribute("id"));
                    int idStrada2 = Integer.valueOf(eElement.getTextContent());
                    roads.get(idStrada1).getAdjacentRoad().add(roads.get(idStrada2));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
