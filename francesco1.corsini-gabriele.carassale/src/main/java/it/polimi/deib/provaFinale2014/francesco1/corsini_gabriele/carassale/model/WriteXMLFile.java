package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WriteXMLFile {

    public static void main(String argv[]) {
        Map map = new Map();

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("map");
            doc.appendChild(rootElement);

            // roads elements
            Element roads = doc.createElement("Roads");
            rootElement.appendChild(roads);

            Element road;
            Attr attr;
            for (Road thisRoad : map.getRoads()) {
                road = doc.createElement("Road");
                roads.appendChild(road);

                // set attribute ID to road element
                attr = doc.createAttribute("id");
                attr.setValue(String.valueOf(thisRoad.getId()));
                road.setAttributeNode(attr);

                // roadNumber elements
                Element roadNumber = doc.createElement("roadNumber");
                roadNumber.appendChild(doc.createTextNode(String.valueOf(thisRoad.getRoadNumber())));
                road.appendChild(roadNumber);
                // adjacentTerrain1 elements
                Element adjacentTerrain1 = doc.createElement("adjacentTerrain1");
                adjacentTerrain1.appendChild(doc.createTextNode(String.valueOf(thisRoad.getAdjacentTerrain1().getID())));
                road.appendChild(adjacentTerrain1);
                // roadNumber elements
                Element adjacentTerrain2 = doc.createElement("adjacentTerrain2");
                adjacentTerrain2.appendChild(doc.createTextNode(String.valueOf(thisRoad.getAdjacentTerrain2().getID())));
                road.appendChild(adjacentTerrain2);

                Element adjacentRoad = doc.createElement("adjacentRoads");
                road.appendChild(adjacentRoad);
                for (Road adjacent : thisRoad.getAdjacentRoad()) {
                    Element adjacentElement = doc.createElement("adjacentRoadToRoad");
                    adjacentElement.appendChild(doc.createTextNode(String.valueOf(adjacent.getId())));
                    adjacentRoad.appendChild(adjacentElement);
                }
            }

            // terrain elements
            Element terrains = doc.createElement("Terrains");
            rootElement.appendChild(terrains);

            Element terrain;
            for (Terrain thisTerrain : map.getTerrain()) {
                terrain = doc.createElement("Terrain");
                terrains.appendChild(terrain);

                // set attribute ID to road element
                attr = doc.createAttribute("id");
                attr.setValue(String.valueOf(thisTerrain.getID()));
                terrain.setAttributeNode(attr);
                // terrainType elements
                Element terrainType = doc.createElement("terrainType");
                terrainType.appendChild(doc.createTextNode(thisTerrain.getTerrainType()));
                terrain.appendChild(terrainType);
                // sheepsbourg elements
                Element sheepsbourg = doc.createElement("sheepsbourg");
                sheepsbourg.appendChild(doc.createTextNode(String.valueOf(thisTerrain.isSheepsbourg())));
                terrain.appendChild(sheepsbourg);
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("C:\\file2.xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}
