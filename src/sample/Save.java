package sample;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

class DOMxmlWriter {

    public static void save(HashMap<String, StrokeLine> listOfDrawsLines,
                            HashMap<String, Circle> listOfDrawsCircles,
                            HashMap<String, BezierLine> listOfDrawsBezier) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();

            // создаем пустой объект Document, в котором будем
            // создавать наш xml-файл
            Document doc = builder.newDocument();
            // создаем корневой элемент
            Element rootElement =
                    doc.createElementNS("objects", "Objects");
            // добавляем корневой элемент в объект Document
            doc.appendChild(rootElement);

            // добавляем первый дочерний элемент к корневому
            for (Map.Entry<String, StrokeLine> entry : listOfDrawsLines.entrySet()) {
                rootElement.appendChild(getLines(doc, entry.getKey(), listOfDrawsLines.get(entry.getKey())));
            }

            for (Map.Entry<String, Circle> entry : listOfDrawsCircles.entrySet()) {
                rootElement.appendChild(getCircles(doc, entry.getKey(), listOfDrawsCircles.get(entry.getKey())));
            }

            for (Map.Entry<String, BezierLine> entry : listOfDrawsBezier.entrySet()) {
                rootElement.appendChild(getBezier(doc, entry.getKey(), listOfDrawsBezier.get(entry.getKey())));
            }

            //создаем объект TransformerFactory для печати в консоль
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // для красивого вывода в консоль
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            //печатаем в консоль или файл
            StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(new File("save.xml"));

            //записываем данные
            transformer.transform(source, console);
            transformer.transform(source, file);
            System.out.println("Создание XML файла закончено");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // метод для создания нового узла XML-файла
    private static Node getLines(Document doc, String id, StrokeLine line) {
        Element type = doc.createElement("Line");
        // устанавливаем атрибут id
        //type.setAttribute("id", id);
        // создаем элемент name
        type.appendChild(getLinesElements(doc, type, "Id", "" + line.getId()));
        type.appendChild(getLinesElements(doc, type, "X1", ""+line.getPointX1()));
        type.appendChild(getLinesElements(doc, type, "Y1", ""+line.getPointY1()));
        type.appendChild(getLinesElements(doc, type, "X2", ""+line.getPointX2()));
        type.appendChild(getLinesElements(doc, type, "Y2", ""+line.getPointY2()));
        type.appendChild(getLinesElements(doc, type, "CopyCounter", ""+line.getCopyCounter() ));
        type.appendChild(getLinesElements(doc, type, "Color", ""+line.getColor() ));
        type.appendChild(getLinesElements(doc, type, "Dash", ""+line.getDashes() ));
        // создаем элемент age
        return type;
    }
    // утилитный метод для создание нового узла XML-файла
    private static Node getLinesElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    private static Node getCircles(Document doc, String id, Circle cir) {
        Element type = doc.createElement("Circle");
        // устанавливаем атрибут id
        //type.setAttribute("id", id);
        // создаем элемент name
        type.appendChild(getLinesElements(doc, type, "Id", "" + cir.getId()));
        type.appendChild(getLinesElements(doc, type, "X1", ""+cir.getPointX1()));
        type.appendChild(getLinesElements(doc, type, "Y1", ""+cir.getPointY1()));
        type.appendChild(getLinesElements(doc, type, "Radius", ""+cir.getRadius()));
        type.appendChild(getLinesElements(doc, type, "CopyCounter", ""+cir.getCopyCounter() ));
        type.appendChild(getLinesElements(doc, type, "Color", ""+cir.getColor() ));
        type.appendChild(getLinesElements(doc, type, "Dash", ""+cir.getDashes() ));
        return type;
    }

    private static Node getBezier(Document doc, String id, BezierLine line) {
        Element type = doc.createElement("BezierLine");
        // устанавливаем атрибут id
        //type.setAttribute("id", id);
        // создаем элемент name
        type.appendChild(getLinesElements(doc, type, "Id", ""+line.getId()));
        type.appendChild(getLinesElements(doc, type, "X1", ""+line.getPointX1()));
        type.appendChild(getLinesElements(doc, type, "Y1", ""+line.getPointY1()));
        type.appendChild(getLinesElements(doc, type, "X2", ""+line.getPointX2()));
        type.appendChild(getLinesElements(doc, type, "Y2", ""+line.getPointY2()));
        type.appendChild(getLinesElements(doc, type, "X3", ""+line.getPointX3()));
        type.appendChild(getLinesElements(doc, type, "Y3", ""+line.getPointY3()));
        type.appendChild(getLinesElements(doc, type, "CopyCounter", ""+line.getCopyCounter() ));
        type.appendChild(getLinesElements(doc, type, "Color", ""+line.getColor() ));
        type.appendChild(getLinesElements(doc, type, "Dash", ""+line.getDashes() ));
        // создаем элемент age
        return type;
    }

}