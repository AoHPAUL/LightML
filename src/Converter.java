import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {

    ArrayList<String> head = new ArrayList<>(); // Contains the starting HTML elements like <!DOCTYPE html> and all tags inside <head></head>
    ArrayList<String> body = new ArrayList<>(); // Contains the body HTML elements <body></body>

    public Converter() {
    }

    public static void main(String[] args) {
        int lineNumber = 1;
        Scanner input = new Scanner(System.in);
        Converter converter = new Converter(); // Create an instance of the class
        Stack<String> tagStack = new Stack<>();

        // Write the Light ML in a .txt file and use the filepath here
        String filePath = "## INSERT-FILEPATH-HERE ##";

        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String line = myReader.nextLine().trim();  // Trim for clean processing

                // Check for title
                if (line.toLowerCase().startsWith("title")) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        String tagPart = parts[0].trim(); // title
                        String contents = parts[1].trim().replace("'", ""); // Remove quotes
                        converter.head.add("<" + tagPart + ">" + contents + "</" + tagPart + ">");
                    }
                }
                // Check for CSS
                else if (line.toLowerCase().startsWith("css") || (line.toLowerCase().startsWith("stylesheet"))) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        String contents = parts[1].trim().replace("'", ""); // Extract the path and remove quotes
                        converter.head.add("<link rel=\"stylesheet\" href=\"" + contents + "\">");
                    }
                }
                // Check for Script
                else if (line.toLowerCase().startsWith("script") || (line.toLowerCase().startsWith("js"))) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        String contents = parts[1].trim().replace("'", ""); // Extract the path and remove quotes
                        converter.head.add("<script src=\"" + contents + "\" defer></script>");
                    }
                }
                // Check for label
                else if (line.toLowerCase().startsWith("label")) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        String contents = parts[1].trim().replace("'", ""); // Extract the path and remove quotes
                        converter.body.add("<label for =\"" + contents + "\">" + contents + "</label>");
                    }
                }
                // Check for text and modifiers
                else if (line.toLowerCase().startsWith("text") || line.toLowerCase().startsWith("p =") || line.toLowerCase().startsWith("p.") || line.toLowerCase().startsWith("p(")) {
                    StringBuilder startTags;
                    StringBuilder endTags;

                    // First, handle attributes inside parentheses using extractAttributes()
                    String[] tagAndAttributes = converter.extractAttributes(line.trim()); // Extract cleaned tag and attributes

                    String firstPart = tagAndAttributes[0]; // p.bold or just p
                    String attributes = (tagAndAttributes.length > 1) ? tagAndAttributes[1] : ""; // Get attributes or empty string

                    // Now split the cleaned firstPart (after removing attributes) at the first '=' to get the content
                    String[] parts = firstPart.split("=", 2);

                    if (parts.length == 2) {
                        String tagPart = parts[0].trim();  // Contains p.bold or just p
                        String content = parts[1].trim().replace("'", ""); // Extract the actual content

                        // Start assembling the <p> tag with attributes
                        if (!attributes.isEmpty()) {
                            startTags = new StringBuilder("<p " + attributes.trim() + ">");
                            endTags = new StringBuilder("</p>");
                        } else {
                            // No attributes, use a simple <p> tag
                            startTags = new StringBuilder("<p>");
                            endTags = new StringBuilder("</p>");
                        }

                        // If there's a dot, it indicates a modifier, e.g., p.bold
                        if (tagPart.contains(".")) {
                            String[] modifiers = tagPart.substring(2).split("\\."); // Extract the modifiers after 'p.'

                            // Loop through the modifiers and add the appropriate tags
                            for (String modifier : modifiers) {
                                switch (modifier.toLowerCase()) {
                                    case "bold":
                                        startTags.append("<b>");
                                        endTags.insert(0, "</b>");
                                        break;
                                    case "strong":
                                        startTags.append("<strong>");
                                        endTags.insert(0, "</strong>");
                                        break;
                                    case "italics":
                                        startTags.append("<i>");
                                        endTags.insert(0, "</i>");
                                        break;
                                    case "strikethrough":
                                        startTags.append("<strike>");
                                        endTags.insert(0, "</strike>");
                                        break;
                                    case "em":
                                        startTags.append("<em>");
                                        endTags.insert(0, "</em>");
                                        break;
                                    case "mark":
                                        startTags.append("<mark>");
                                        endTags.insert(0, "</mark>");
                                        break;
                                    case "small":
                                        startTags.append("<small>");
                                        endTags.insert(0, "</small>");
                                        break;
                                    case "del":
                                        startTags.append("<del>");
                                        endTags.insert(0, "</del>");
                                        break;
                                    case "ins":
                                        startTags.append("<ins>");
                                        endTags.insert(0, "</ins>");
                                        break;
                                    case "sub":
                                        startTags.append("<sub>");
                                        endTags.insert(0, "</sub>");
                                        break;
                                    case "sup":
                                        startTags.append("<sup>");
                                        endTags.insert(0, "</sup>");
                                        break;
                                }
                            }
                        }

                        // Assemble the final tag with content
                        String finalTag = startTags.toString() + content + endTags.toString();

                        // Add to the body
                        converter.body.add(finalTag);
                    }
                }

                // Check for link
                else if (line.toLowerCase().startsWith("link") || (line.toLowerCase().startsWith("a href"))) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        String contents = parts[1].trim().replace("'", ""); // Extract the path and remove quotes
                        String[] subParts = contents.split(",");
                        String link = subParts[0].trim();
                        String name = subParts[1].trim();
                        converter.body.add("<a href =\"" + link + "\">" + name + "</a>");
                    }
                }
                // Check for image
                else if (line.toLowerCase().startsWith("img") || line.toLowerCase().startsWith("image")) {
                    String attributes = ""; // For storing any attributes inside parentheses
                    String imgTag = "";     // For storing the <img> tag

                    // Check if there are parentheses for attributes
                    if (line.contains("(") && line.contains(")")) {
                        // Extract attributes from parentheses using regex
                        Pattern pattern = Pattern.compile("\\((.*?)\\)");
                        Matcher matcher = pattern.matcher(line);
                        if (matcher.find()) {
                            attributes = matcher.group(1); // Get the attributes inside parentheses
                            attributes = attributes.replace("'", "").trim(); // Clean up quotes and trim spaces
                            line = line.replace("(" + matcher.group(1) + ")", ""); // Remove the attributes part from the original line
                        }
                    }

                    // Now handle the image source (split at the first '=')
                    String[] parts = line.split("=", 2); // Split only once at the first '='
                    if (parts.length == 2) {
                        String src = parts[1].trim().replace("'", ""); // Extract the image source and remove quotes

                        // Build the <img> tag
                        StringBuilder imgTagBuilder = new StringBuilder("<img src=\"" + src + "\"");

                        // If there are attributes, append them
                        if (!attributes.isEmpty()) {
                            imgTagBuilder.append(" ").append(attributes);
                        }

                        imgTagBuilder.append(">");
                        imgTag = imgTagBuilder.toString(); // Convert to string

                        // Add the final image tag to the body
                        converter.body.add(imgTag);
                    }
                }


                // Check for Bootstrap
                else if (line.trim().equalsIgnoreCase("bootstrap")) {
                    // Add Bootstrap CSS
                    converter.head.add("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH\" crossorigin=\"anonymous\">");
                    // Add Bootstrap JS
                    converter.head.add("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz\" crossorigin=\"anonymous\"></script>");
                }
                // Check for Tailwind
                else if (line.trim().equalsIgnoreCase("tailwind")) {
                    // Add Tailwind CSS
                    converter.head.add("<link href=\"https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css\" rel=\"stylesheet\">");
                }
                // Handle line breaks
                else if (line.trim().equalsIgnoreCase("br")) {
                    // Add Line break
                    converter.body.add("<br>");
                }
                // Handle horizontal breaks
                else if (line.trim().equalsIgnoreCase("hr")) {
                    // Add horizontal break
                    converter.body.add("<hr>");
                }
                // Handle raw HTML
                else if (line.toLowerCase().startsWith("html(")) {
                    String html = "";
                    Pattern pattern = Pattern.compile("\\((.*?)\\)");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        html = matcher.group(1); // Get contents inside ()
                        converter.body.add("" + html);
                    }
                }
                // Handle HTML entries to the <head> section
                else if (line.toLowerCase().startsWith("html-head")) {
                    String html = "";
                    Pattern pattern = Pattern.compile("\\((.*?)\\)");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        html = matcher.group(1); // Get contents inside ()
                        converter.head.add("" + html);
                    }
                }
                // Handle nav blocks
                else if (line.endsWith("{") && line.toLowerCase().startsWith("nav")) {
                    StringBuilder navContent = new StringBuilder();
                    navContent.append("<nav>\n");
                    lineNumber++;

                    // Keep reading lines until we find the closing }
                    while (myReader.hasNextLine()) {
                        String navLine = myReader.nextLine().trim(); // Trim for clean processing

                        if (navLine.equals("}")) { // Stop when we encounter the closing brace
                            break;
                        }

                        // Split the line by commas to handle multiple Text=URL pairs
                        String[] links = navLine.split(",");

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
                                    System.out.println("Invalid link or href detected on line " + lineNumber);
                                }
                            } else {
                                // Debugging: Line format issue
                                System.out.println("Line format not matching expected on line: " + lineNumber + ". 'Text = URL'. Skipping: " + link);
                            }
                        }
                        lineNumber++;
                    }
                    navContent.append("</nav>");
                    converter.body.add(navContent.toString()); // Add the final nav HTML to the body list
                }
                // Handle form blocks
                else if (line.endsWith("{") && line.toLowerCase().startsWith("form")) {
                    StringBuilder formContent = new StringBuilder();
                    tagStack.push("form");

                    // Create opening form tag
                    formContent.append("<form>\n"); // Opening tag
                    converter.body.add(formContent.toString()); // Add opening form to body
                }

                // Handle input types
                else if (line.trim().startsWith("input-")) {
                    String attributes = "" ;
                    if (line.trim().contains("(") && line.trim().contains(")")) {
                        // Extract attributes from parentheses using a regular expression
                        Pattern pattern = Pattern.compile("\\((.*?)\\)");
                        Matcher matcher = pattern.matcher(line);
                        if (matcher.find()) {
                            attributes = matcher.group(1); // Get the attributes inside parentheses
                            line = line.replace(attributes, "");
                        }
                    }
                    if (line.trim().startsWith("input-text")) {
                        // First, split the line by the first '=' outside parentheses
                        String[] parts = line.split("=", 2); // Split only once at the first '='
                        if (parts.length >= 2) {
                            String inputName = parts[1].trim(); // Extract the input name

                            // Prepare input attributes
                            StringBuilder inputHTML = new StringBuilder("<input type=\"text\" name=\"" + inputName + "\"");

                            // Check if there are attributes in parentheses
                            if (!attributes.isEmpty()) {
                                inputHTML.append(" ").append(attributes.replace("'", "")); // Add attributes, remove quotes
                                inputHTML.append(">");
                                converter.body.add(inputHTML.toString());
                            } else {
                                inputHTML.append(">");
                                converter.body.add(inputHTML.toString());
                            }
                        }
                    } else if (line.trim().startsWith("input-email")) {
                        // Similar logic for email input fields
                        String[] parts = line.split("=", 2); // Split only once at the first '='
                        if (parts.length >= 2) {
                            String inputName = parts[1].trim(); // Extract the input name

                            // Prepare input attributes
                            StringBuilder inputHTML = new StringBuilder("<input type=\"email\" name=\"" + inputName + "\"");

                            // Check if there are attributes in parentheses
                            if (!attributes.isEmpty()) {
                                inputHTML.append(" ").append(attributes.replace("'", "")); // Add attributes, remove quotes
                                inputHTML.append(">");
                                converter.body.add(inputHTML.toString());
                            } else {
                                inputHTML.append(">");
                                converter.body.add(inputHTML.toString());
                            }
                        }
                    }
                }

                    // Handle div blocks
                    else if (line.endsWith("{") && line.toLowerCase().startsWith("div")) {
                        StringBuilder divContent = new StringBuilder();
                        String attributes;
                        String tag = line.substring(0, line.length() - 1).trim(); // Get the tag name without '{'
                        tagStack.push("div"); // Push "div" onto the stack, assuming the tag is "div"

                        // Handling attributes
                        attributes = "";
                        Pattern pattern = Pattern.compile("\\((.*?)\\)"); // Regex to match attributes in parentheses
                        Matcher matcher = pattern.matcher(tag);
                        if (matcher.find()) {
                            attributes = matcher.group(1); // Get attributes
                            attributes = attributes.replace("'", ""); // Remove quotes from attributes
                            tag = tag.substring(0, matcher.start()).trim(); // Get the tag name without attributes
                        }

                        // Create opening tag with attributes if present
                        divContent.append("<" + tag);
                        if (!attributes.isEmpty()) {
                            divContent.append(" " + attributes); // Add attributes if present
                        }
                        divContent.append(">\n"); // Close opening tag

                        converter.body.add(divContent.toString()); // Add opening div to body
                    }

                    // Handle closing tags
                    else if (line.equals("}")) {
                        if (!tagStack.isEmpty()) {
                            String lastTag = tagStack.pop(); // Pop the last tag from the stack (it should be "div")
                            converter.body.add("</" + lastTag + ">"); // Properly close the last tag
                        } else {
                            // Handle the case where no opening tag exists for the closing brace
                            System.out.println("Warning: No opening tag found for closing brace.");
                        }
                    }
                // Increment line number at the end of each iteration
                lineNumber++;
            }
            myReader.close();
            converter.generateHTMLDocument(); // Call generateHTMLDocument
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    public String applyIndentation(StringBuilder html) {
        CustomHtmlFormatter formatter = new CustomHtmlFormatter();
        String indentedHtml = formatter.prettyPrintHtml(html);
        return  indentedHtml;
    }

    // Generate the final HTML document and save it to a file
    public void generateHTMLDocument() {
        StringBuilder document = new StringBuilder();

        // Add the head section
        document.append("<!DOCTYPE html>\n<html>\n<head>\n<meta charset=\"UTF-8\">\n<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n<meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n");
        for (String line : head) {
            document.append(line).append("\n");
        }
        document.append("</head>\n");

        // Add the body section
        document.append("<body>\n");
        for (String line : body) {
            document.append(line).append("\n");
        }
        document.append("</body>\n</html>");

        // Apply indentation to the entire HTML document
        String indentedDocument = applyIndentation(document);

        // Write the document to a file
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
    // Function to extract attributes from parentheses and return the cleaned line and attributes
    // Function to extract attributes from parentheses and return the cleaned line and attributes
    private String[] extractAttributes(String line) {
        String attributes = "";

        // Check if there are parentheses for attributes
        if (line.contains("(") && line.contains(")")) {
            Pattern pattern = Pattern.compile("\\((.*?)\\)"); // Regex to match attributes inside parentheses
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                attributes = matcher.group(1).trim(); // Extract attributes and trim spaces
                attributes = attributes.replace("'", "\""); // Replace single quotes with double quotes for proper HTML
                // Remove the attributes from the line (including the parentheses)
                line = line.replace("(" + matcher.group(1) + ")", "").trim();
            }
        }

        // Return the cleaned line and attributes as an array
        return new String[] { line, attributes };
    }


}
