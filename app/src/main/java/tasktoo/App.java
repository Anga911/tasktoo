/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package tasktoo;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;

import java.io.File;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try {
            // Load the XML file
            File file = new File("C:\\Users\\sixol\\OneDrive\\Desktop\\tasktoo\\app\\src\\main\\resources\\data.xml");

            // Build Document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            doc.getDocumentElement().normalize();

            // Get all <record> elements
            NodeList recordList = doc.getElementsByTagName("record");

            // User input for selected fields
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the fields you want to display (comma separated — name, postalZip, region, country, address, list): ");
            String input = scanner.nextLine();
            String[] selectedFields = input.toLowerCase().split(",");

            // Validate and clean selected fields
            String[] validFields = {"name", "postalzip", "region", "country", "address", "list"};

            // Start JSON array
            System.out.println("[");

            for (int i = 0; i < recordList.getLength(); i++) {
                Node node = recordList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    StringBuilder json = new StringBuilder();
                    json.append("  {");

                    boolean hasValidField = false;

                    for (int j = 0; j < selectedFields.length; j++) {
                        String field = selectedFields[j].trim();

                        if (!isValidField(field, validFields)) {
                            // Skip invalid fields and warn once at the start
                            if (i == 0) {
                                System.out.println("⚠️  Warning: Ignoring invalid field '" + field + "'");
                            }
                            continue;
                        }

                        String value = "";

                        switch (field) {
                            case "name":
                                value = element.getElementsByTagName("name").item(0).getTextContent();
                                break;
                            case "postalzip":
                                value = element.getElementsByTagName("postalZip").item(0).getTextContent();
                                break;
                            case "region":
                                value = element.getElementsByTagName("region").item(0).getTextContent();
                                break;
                            case "country":
                                value = element.getElementsByTagName("country").item(0).getTextContent();
                                break;
                            case "address":
                                value = element.getElementsByTagName("address").item(0).getTextContent();
                                break;
                            case "list":
                                value = element.getElementsByTagName("list").item(0).getTextContent();
                                break;
                        }

                        json.append("\"").append(field).append("\": \"").append(value).append("\"");

                        // If not the last field, add a comma
                        if (j < selectedFields.length - 1) {
                            json.append(", ");
                        }

                        hasValidField = true;
                    }

                    if (hasValidField) {
                        // Clean up potential trailing commas
                        String cleanJson = json.toString().replaceAll(",\\s*}", "}");
                        System.out.println(cleanJson + (i < recordList.getLength() - 1 ? "," : ""));
                    }
                }
            }

            // Close JSON array
            System.out.println("]");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // Helper: check if a field is valid
    public static boolean isValidField(String field, String[] validFields) {
        for (String valid : validFields) {
            if (valid.equals(field)) {
                return true;
            }
        }
        return false;
    }
}

