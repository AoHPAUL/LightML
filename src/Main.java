import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    ArrayList<String> head = new ArrayList<>(); // Contains the starting HTML elements like <!DOCTYPE html> and all tags inside <head></head>
    ArrayList<String> body = new ArrayList<>(); // Contains the body HTML elements <body></body>
    Scanner input = new Scanner(System.in); // Used for user input
    Tags tags = new Tags(); // Create instance of tags class
    Stack<String> tagStack = new Stack<>(); // Stores open tags and uses them to check for lists and close tags properly
    public Main(){}

    public static void main(String[] args) {
        // Write the Light ML in a .txt file and use the filepath here
        String filePath = "src/LMLfile.txt";

        // Constructor
        Main main = new Main();

        // Run with the current filepath
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            String currentLine = "*-*"; // Holds the HTML for the current line until complete
            String contents = ""; // Stores all contents after the `=`
            String attributes = ""; // Stores attributes for HTML tag
            String tag;
            String nextTag;

            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();

                // Check the tagStack if the last tag is ul and surround all HTML elements in <li> tags </li>
                if (!main.tagStack.isEmpty()) {
                    nextTag = main.tagStack.peek().toLowerCase();
                    if(nextTag.trim().equals("</ul>")){
                        currentLine = "<li>*-*</li>"; // *-* is used as a reference to replace later
                    }
                }
                // Check for Nav block
                if (line.endsWith("{") && line.toLowerCase().startsWith("nav")) {
                    StringBuilder navContent = new StringBuilder();
                    navContent.append("<nav>\n");

                    // Keep reading lines until we find the closing }
                    while (myReader.hasNextLine()) {
                        String navLine = myReader.nextLine().trim(); // Trim for clean processing
                        if (navLine.equals("}")) { // Stop when we encounter the closing brace
                            break;
                        }
                        String[] links = navLine.split(","); // Split the line by commas to handle multiple Text=URL pairs
                        // Loop through the links
                        for (String link : links) {
                            String[] parts = link.split("="); // Split the link into text and link
                            if (parts.length == 2) {
                                String linkText = parts[0].trim(); // Extract link text (e.g., Home)
                                String linkHref = parts[1].trim().replace("'", ""); // Extract link URL and remove quotes
                                // Only add valid non-empty links
                                if (!linkText.isEmpty() && !linkHref.isEmpty()) {
                                    navContent.append("<a href=\"").append(linkHref).append("\">").append(linkText).append("</a>\n");
                                } else {
                                    // Debugging: Invalid link or href
                                    System.out.println("Invalid link or href detected");
                                }
                            } else {
                                // Debugging: Line format issue
                                System.out.println("Line format not matching expected. 'Text = URL'. Skipping: " + link);
                            }
                        }
                    }
                    navContent.append("</nav>");
                    main.body.add(navContent.toString()); // Add the final nav HTML to the body list
                }

                // Check for } closing bracket
                if (main.checkClose(line)) {
                    continue;
                }

                // Check for (attributes) and extract if found
                String[] lineParts = main.checkParenth(line);
                if (lineParts.length > 1) {
                    tag = lineParts[0];
                    attributes = lineParts[1];
                } else {
                    tag = lineParts[0];
                }

                // Handle opening bracket `{`
                if (tag.contains("{")) {
                    // Use `checkParent` to validate the tag
                    String[] html = main.tags.checkParent(tag);

                    // If the tag is invalid, skip processing
                    if ("INVALID_TAG".equals(html[0]) && (!html[0].trim().equals("nav{"))) {
                        System.out.println("Error: Invalid tag detected: " + tag);
                        continue; // Skip processing this line
                    }

                    // Check line isn't empty and handle the valid tag
                    if (!html[0].equals(" ")) {
                        if (html[0].startsWith("#")) {
                            // If it's a <head> tag, add it to the head list and push to the tag stack
                            main.head.add(html[0].substring(1)); // Use substring to add HTML without the #
                            main.tagStack.push(html[1]);
                            contents = "";
                            attributes = "";
                        } else {
                            // If it's a body tag, add it to the body list and push to the tag stack
                            main.body.add(html[0]);
                            main.tagStack.push(html[1]);
                            contents = "";
                            attributes = "";
                        }
                    }
                    continue; // Continue with the next line
                }

                // Check for contents (`=`)
                if (tag.contains("=")) {
                    String[] parts = tag.split("=");
                    contents = parts[1].trim();
                    tag = parts[0].trim();
                }

                // Use `htmlBuilder` to generate HTML code based on the tag, attributes, and contents
                String html = main.tags.htmlBuilder(tag, attributes, contents);

                // If the generated HTML is not empty, add it to the head or body as appropriate
                if (!html.trim().equals("")) {
                    if (html.startsWith("#")) {
                        main.head.add(currentLine.replace("*-*",html.substring(1))); // Use substring to add HTML without the #
                        currentLine = "*-*";
                    } else {
                        main.body.add(currentLine.replace("*-*",html));
                        currentLine = "*-*";
                    }
                }
            }
            main.generateHTMLDocument();
        } catch (FileNotFoundException e) {
            System.out.println("Invalid filepath: " + filePath);
        }
    }
    public boolean checkClose(String line){
        if (line.trim().endsWith("}")){
            if (!tagStack.isEmpty()){
                String html = tagStack.pop();
                body.add(html);
                return true;
            } else{
                System.out.println("No ending tag stored");
            }
        }
        return false;
    }
    public String[] checkParenth(String line){
        String[] returnline = new String[2];
        returnline[0] = line;
        if (line.contains("(") && line.contains(")")){
            // Check for parentheses and extract contents
            int startIndex = line.indexOf('(');
            int endIndex = line.indexOf(')');

            if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                returnline[1] = " " + line.substring(startIndex + 1, endIndex).trim();
                // Remove the parentheses and their content from the original line
                returnline[0] = line.substring(0, startIndex).trim() + line.substring(endIndex + 1).trim();
            }

            return returnline;
        } else{
            return returnline;
        }
    }
    public String applyIndentation(StringBuilder html) {
        CustomHtmlFormatter formatter = new CustomHtmlFormatter();
        String indentedHtml = formatter.prettyPrintHtml(html);
        return  indentedHtml;
    }
    public void generateHTMLDocument() {
        StringBuilder document = new StringBuilder();

        document.append("<!DOCTYPE html>\n<html>\n<head>\n<meta charset=\"UTF-8\">\n<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n<meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n");
        for (String line : head) {
            document.append(line).append("\n");
        }
        document.append("</head>\n");

        document.append("<body>\n");
        for (String line : body) {
            document.append(line).append("\n");
        }

        document.append("</body>\n</html>");

        String indentedDocument = applyIndentation(document);

        try {
            FileWriter writer = new FileWriter("output.html");
            writer.write(indentedDocument);
            writer.close();
            System.out.println("HTML document generated: output.html");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the file.");
            e.printStackTrace();
        }
    }


}
