package com.example.soa_a1_attempt2;


import org.springframework.web.bind.annotation.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;

@RestController
public class StudentController {

    @PostMapping("/")
    public Student addStudent(@RequestBody Student student){

//        return student;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(SoaA1Attempt2Application.Xml));
            Element universityElement = doc.getDocumentElement();

            Element studentElement = doc.createElement("Student");
            studentElement.setAttribute("ID", Integer.toString(student.getID()));

            Element firstNameElement = doc.createElement("FirstName");
            firstNameElement.appendChild(doc.createTextNode(student.getFirstName()));
            studentElement.appendChild(firstNameElement);

            Element lastNameElement = doc.createElement("LastName");
            lastNameElement.appendChild(doc.createTextNode(student.getLastName()));
            studentElement.appendChild(lastNameElement);

            Element genderElement = doc.createElement("Gender");
            genderElement.appendChild(doc.createTextNode(student.getGender()));
            studentElement.appendChild(genderElement);

            Element gpaElement = doc.createElement("GPA");
            gpaElement.appendChild(doc.createTextNode(Double.toString(student.getGpa())));
            studentElement.appendChild(gpaElement);

            Element levelElement = doc.createElement("Level");
            levelElement.appendChild(doc.createTextNode(Integer.toString(student.getLevel())));
            studentElement.appendChild(levelElement);

            Element addressElement = doc.createElement("Address");
            addressElement.appendChild(doc.createTextNode(student.getAddress()));
            studentElement.appendChild(addressElement);

            doc.getDocumentElement().appendChild(studentElement);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(SoaA1Attempt2Application.Xml));
            transformer.transform(source, result);
            return student;
        }

        catch (Exception E) {
            return (null);
        }


    }

    @DeleteMapping("/deletebyid/{ID}")
    public void deleteByID(@PathVariable int ID){

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(SoaA1Attempt2Application.Xml));
            NodeList students = doc.getElementsByTagName("Student");

            for (int i = 0; i < students.getLength(); i++) {
                Element student = (Element) students.item(i);
                int studentID = Integer.parseInt(student.getAttribute("ID"));

                if (studentID == ID) {
                    student.getParentNode().removeChild(student);
                    break;
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(SoaA1Attempt2Application.Xml));
            transformer.transform(source, result);
        }
        catch(Exception E){
        }
    }

    @GetMapping("/SearchGPA/{gpa}")
    public List<Student> searchGPA(@PathVariable double gpa) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(SoaA1Attempt2Application.Xml));
            NodeList students = doc.getElementsByTagName("Student");
            XPathFactory xPathfactory = XPathFactory.newInstance();
            
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("//Student[GPA=" + gpa + "]");
            
            NodeList result = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

            if (result.getLength() > 0) {
                List<Student> Students = new ArrayList<Student>();
                for (int i = 0; i < result.getLength(); i++) {
                    Node node = result.item(i);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) node;

                        // Get the value of the ID attribute.
                        int ID = Integer.parseInt(node.getAttributes().getNamedItem("ID").getNodeValue());

                        // Get the value of all sub-elements.
                        String firstName = elem.getElementsByTagName("FirstName")
                                .item(0).getChildNodes().item(0).getNodeValue();

                        String lastName = elem.getElementsByTagName("LastName").item(0)
                                .getChildNodes().item(0).getNodeValue();

                        String gender = elem.getElementsByTagName("Gender").item(0)
                                .getChildNodes().item(0).getNodeValue();

                        double gpaa = Double.parseDouble(elem.getElementsByTagName("GPA")
                                .item(0).getChildNodes().item(0).getNodeValue());

                        int level = Integer.parseInt(elem.getElementsByTagName("Level")
                                .item(0).getChildNodes().item(0).getNodeValue());

                        String address = elem.getElementsByTagName("Address").item(0)
                                .getChildNodes().item(0).getNodeValue();

                        Students.add(new Student(firstName, lastName,ID,gender, gpaa, level, address));
                    }

                }
                return Students;
            } else {
                System.out.println("No matching records found.");
            }
        } catch (Exception e) {
            return null;

        }
        return null;
    }

    @GetMapping("/SearchFirstName/{firstName}")
    public List<Student> searchFirstName(@PathVariable String firstName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(SoaA1Attempt2Application.Xml));
            NodeList students = doc.getElementsByTagName("Student");
            XPathFactory xPathfactory = XPathFactory.newInstance();

            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("//Student[FirstName='" + firstName + "']");

            NodeList result = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

            if (result.getLength() > 0) {
                List<Student> studentsList = new ArrayList<>();
                for (int i = 0; i < result.getLength(); i++) {
                    Node node = result.item(i);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) node;

                        int ID = Integer.parseInt(node.getAttributes().getNamedItem("ID").getNodeValue());
                        String lastName = elem.getElementsByTagName("LastName").item(0)
                                .getChildNodes().item(0).getNodeValue();
                        String gender = elem.getElementsByTagName("Gender").item(0)
                                .getChildNodes().item(0).getNodeValue();
                        double gpa = Double.parseDouble(elem.getElementsByTagName("GPA")
                                .item(0).getChildNodes().item(0).getNodeValue());
                        int level = Integer.parseInt(elem.getElementsByTagName("Level")
                                .item(0).getChildNodes().item(0).getNodeValue());
                        String address = elem.getElementsByTagName("Address").item(0)
                                .getChildNodes().item(0).getNodeValue();

                        studentsList.add(new Student(firstName, lastName, ID, gender, gpa, level, address));
                    }
                }
                return studentsList;
            } else {
                System.out.println("No matching records found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList(); // Return an empty list if no matching records are found
    }




}
