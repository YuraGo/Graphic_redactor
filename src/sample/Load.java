package sample;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;

class DOMxmlReader {

    public static void load(HashMap<String, StrokeLine> listOfDrawsLines,
                            HashMap<String, Circle> listOfDrawsCircles,
                            HashMap<String, BezierLine> listOfDrawsBezier) {
        String filepath = "save.xml";
        File xmlFile = new File(filepath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            //System.out.println("Корневой элемент: " + document.getDocumentElement().getNodeName());
            // получаем узлы с именем Language
            // теперь XML полностью загружен в память 
            // в виде объекта Document
            NodeList nodeList = document.getElementsByTagName("Line");

            for (int i = 0; i < nodeList.getLength(); i++)  {
                getLine(nodeList.item(i), listOfDrawsLines);
            }

            nodeList = document.getElementsByTagName("Circle");

            for (int i = 0; i < nodeList.getLength(); i++)  {
                getCircle(nodeList.item(i), listOfDrawsCircles);
            }

            nodeList = document.getElementsByTagName("BezierLine");

            for (int i = 0; i < nodeList.getLength(); i++)  {
                getBezier(nodeList.item(i), listOfDrawsBezier);
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private static void getLine(Node node, HashMap<String, StrokeLine> line) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            line.put(getTagValue("Id", element),
                    new StrokeLine( Double.parseDouble(getTagValue("X1", element)),
                    500 - Double.parseDouble(getTagValue("Y1", element)),
                    Double.parseDouble(getTagValue("X2", element)),
                            500 -Double.parseDouble(getTagValue("Y2", element)),
                    getTagValue("Id", element)));
            line.get(getTagValue("Id", element)).setDashes(Double.parseDouble(getTagValue("Dash", element)));
            line.get(getTagValue("Id", element)).setCopyCounter(Integer.parseInt(getTagValue("CopyCounter", element)));
        }

    }

    private static void getCircle(Node node, HashMap<String, Circle> line) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            line.put(getTagValue("Id", element),
                    new Circle( Double.parseDouble(getTagValue("X1", element)),
                            500 - Double.parseDouble(getTagValue("Y1", element)),
                            Double.parseDouble(getTagValue("Radius", element)),
                            getTagValue("Id", element)));
            line.get(getTagValue("Id", element)).setDashes(Double.parseDouble(getTagValue("Dash", element)));
            line.get(getTagValue("Id", element)).setCopyCounter(Integer.parseInt(getTagValue("CopyCounter", element)));
        }

    }

    private static void getBezier(Node node, HashMap<String, BezierLine> line) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            line.put(getTagValue("Id", element),
                    new BezierLine( Double.parseDouble(getTagValue("X1", element)),
                            500 - Double.parseDouble(getTagValue("Y1", element)),
                            Double.parseDouble(getTagValue("X2", element)),
                            500 -Double.parseDouble(getTagValue("Y2", element)),
                            Double.parseDouble(getTagValue("X3", element)),
                            500 -Double.parseDouble(getTagValue("Y3", element)),
                            getTagValue("Id", element)));
            line.get(getTagValue("Id", element)).setDashes(Double.parseDouble(getTagValue("Dash", element)));
            line.get(getTagValue("Id", element)).setCopyCounter(Integer.parseInt(getTagValue("CopyCounter", element)));
        }

    }

    // получаем значение элемента по указанному тегу
    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

}