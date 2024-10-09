import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tags {

    // List of valid parent tags
    private ArrayList<String> validParentTags = new ArrayList<String>();
    Variable variables = new Variable();
    // Main main = new Main();
    public Tags(){}

    // Method to check for parent tags and return HTML opening and closing tags
    public String[] checkParent(String line) {

        // List of valid parent tags
        ArrayList<String> validParentTags = new ArrayList<>();
        validParentTags.add("div");
        validParentTags.add("ul");
        validParentTags.add("list");
        validParentTags.add("form");
        validParentTags.add("section");
        validParentTags.add("article");
        validParentTags.add("footer");
        validParentTags.add("nav");

        String[] returnStatement = new String[2];

        // Extract the tag name from the input line
        String tag = line.toLowerCase().trim().replace("{",""); // remove {

        // Check if the tag is a valid parent tag
        if (!validParentTags.contains(tag)) {
            return new String[] {"INVALID_TAG", null}; // Return indicator of invalid tag
        }

        // If the tag is valid, return the appropriate HTML opening and closing tags
        switch (tag) {
            case "div":
                returnStatement[0] = "<div>";
                returnStatement[1] = "</div>";
                break;
            case "ul":
            case "list":
                returnStatement[0] = "<ul>";
                returnStatement[1] = "</ul>";
                break;
            case "form":
                returnStatement[0] = "<form>";
                returnStatement[1] = "</form>";
                break;
            case "section":
                returnStatement[0] = "<section>";
                returnStatement[1] = "</section>";
                break;
            case "article":
                returnStatement[0] = "<article>";
                returnStatement[1] = "</article>";
                break;
            case "footer":
                returnStatement[0] = "<footer>";
                returnStatement[1] = "</footer>";
                break;
            default:
                returnStatement[0] = " ";
                returnStatement[1] = " ";
        }

        return returnStatement;
    }


    public String htmlBuilder(String tag, String attribute, String content){
        String returnStatement = "";
        String startTags = "";
        String endTags = "";
        boolean hasModifier = false;
        // Check if attributes are present
        if (!(attribute == null)){
            attribute = " " + attribute.trim();
        } else {
            attribute = "";
        }
        // Check for variable references in attributes and content
        if (attribute != null && attribute.contains("$")) {
            attribute = replaceVariables(attribute);
        }
        if (content != null && content.contains("$")) {
            content = replaceVariables(content);
        }
        // Check for variable references in attributes, content and tag
        if (attribute != null && attribute.contains("$")) {
            attribute = replaceVariables(attribute);
        }
        if (content != null && content.contains("$")) {
            content = replaceVariables(content);
        }
        if (tag != null && tag.contains("$")) {
            tag = replaceVariables(tag);
        }
        // Check if tag has # at start (this declares a <head> placement </head>)
        if(tag.trim().startsWith("#")){
            returnStatement = "#";
            tag.replace("#",""); // Strip the # from tag for processing
        }
        if (tag.contains(".")) {
            String[] parts = tag.split("\\."); // Split by literal period
            if (parts.length > 1) { // Ensure that there is a part after the split
                hasModifier = true;
                switch (parts[1].trim()) {
                    case "bold":
                        startTags = "<b>";
                        endTags = "</b>";
                        break;
                    case "strong":
                        startTags = "<strong>";
                        endTags = "</strong>";
                        break;
                    case "italics":
                        startTags = "<i>";
                        endTags = "</i>";
                        break;
                    case "underline":
                        startTags = "<u>";
                        endTags = "</u>";
                        break;
                    case "strikethrough":
                        startTags = "<strike>";
                        endTags = "</strike>";
                        break;
                    // Add more cases as necessary
                    default:
                        System.out.println("Warning: Unknown modifier detected - " + parts[1]);
                        break;
                } tag = parts[0];
            }
        }
        // Manage text
        if (tag.trim().equalsIgnoreCase("text") || tag.trim().equalsIgnoreCase("p")){
            content = content.replace("'","");
            content = parseText(content);
            returnStatement += "<p"+ attribute + ">"+ content + "</p>";
        }
        // Manage label
        if (tag.trim().equalsIgnoreCase("label") || tag.trim().equalsIgnoreCase("lbl")){
            content = content.replace("'","");
            content = parseText(content);
            returnStatement += "<label"+ attribute + ">"+ content + "</label>";
        }
        // Manage images
        else if (tag.trim().equalsIgnoreCase("image") || tag.trim().equalsIgnoreCase("img")) {
            content.replace("'","");
            returnStatement += "<img src=\""+ content + "\"" + attribute + ">";
        }
        // Manage links
        else if (tag.trim().equalsIgnoreCase("link")) {
            String[] parts = content.split(","); // split at the comma and split into link & text
            String linkTxt = parts[0].trim();
            String link = parts[1].trim().replace("'","");
            if (attribute == null){
                attribute = "";
            }
            returnStatement += "<a href=\""+ link + "\"" + attribute + ">" + linkTxt + "</a>";
        }
        // Manage title
        else if (tag.trim().equalsIgnoreCase("title")) {
            returnStatement = "#<title>" + content + "</title>";
        }
        // Manage body comments
        else if (tag.trim().startsWith("//")) {
            tag = tag.replace("//","");
            returnStatement = "<!--" + tag + "-->";
        }
        // Manage head comments
        else if (tag.trim().startsWith("#//")) {
            tag = tag.replace("#//","");
            returnStatement = "#<!--" + tag + "-->";
        }
        // Manage BR
        else if (tag.trim().equalsIgnoreCase("br")) {
            returnStatement = "<br>";
        }
        // Manage HR
        else if (tag.trim().equalsIgnoreCase("hr")) {
            returnStatement = "<hr>";
        }
        // Manage raw HTML
        else if (tag.trim().equalsIgnoreCase("html")) {
            returnStatement = content;
        }
        // Manage raw HTML-head
        else if (tag.trim().equalsIgnoreCase("#")) {
            returnStatement = "#"+content;
        }
        // Manage ul
        else if (tag.trim().equalsIgnoreCase("ul") || tag.trim().equalsIgnoreCase("list")) {
            returnStatement = "<ul" + attribute + ">\n";
            String[] parts = content.split(",");
            for (String part:parts) {
                if (!part.isEmpty()){
                    returnStatement += "<li>"+ part.trim() +"</li>\n";
                }
            } returnStatement += "</ul>";
        }
        // Check for Bootstrap
        else if (tag.trim().equalsIgnoreCase("bootstrap")) {
            // Add Bootstrap
            returnStatement = "#<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH\" crossorigin=\"anonymous\">" + "\n<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz\" crossorigin=\"anonymous\"></script>";
        }
        // Check for Tailwind
        else if (tag.trim().equalsIgnoreCase("tailwind")) {
            // Add Tailwind CSS
            returnStatement = "#<link href=\"https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css\" rel=\"stylesheet\">";
        }
        // Manage styles
        else if (tag.trim().equalsIgnoreCase("style") || tag.trim().equalsIgnoreCase("css")) {
            returnStatement = "#<link rel=\"stylesheet\" href=\"" + content + "\">"; // # added to start to tell the program to add into <head></head>
        }
        if(hasModifier){
            String html = startTags + returnStatement;
            html += endTags;
            return html;
        }
        else {
            return returnStatement;
        }
    }
    public static String parseText(String text) {
        if (text == null) {
            return "";
        }

        // Bold: Replace **word** with <b>word</b>
        Pattern boldPattern = Pattern.compile("\\*\\*(.*?)\\*\\*");
        Matcher boldMatcher = boldPattern.matcher(text);
        text = boldMatcher.replaceAll("<b>$1</b>"); // $1 back references the regex

        // Italics: Replace ;;word;; with <i>word</i>
        Pattern italicsPattern = Pattern.compile(";;(.*?);;");
        Matcher italicsMatcher = italicsPattern.matcher(text);
        text = italicsMatcher.replaceAll("<i>$1</i>");

        // Underline: Replace ++word++ with <u>word</u>
        Pattern underlinePattern = Pattern.compile("\\+\\+(.*?)\\+\\+");
        Matcher underlineMatcher = underlinePattern.matcher(text);
        text = underlineMatcher.replaceAll("<u>$1</u>");

        // Abbreviation: Replace ^^abbreviation = word^^ with <abbr title="word">abbreviation</abbr>
        Pattern abbrPattern = Pattern.compile("\\^\\^(.*?)=(.*?)\\^\\^");
        Matcher abbrMatcher = abbrPattern.matcher(text);
        text = abbrMatcher.replaceAll("<abbr title=\"$2\">$1</abbr>");

        return text;
    }
    public String replaceVariables(String line) {
        // Handle variable assignment like $variable = value
        Pattern assignmentPattern = Pattern.compile("\\$(\\w+)\\s*=\\s*(.+)");
        Matcher assignmentMatcher = assignmentPattern.matcher(line);

        if (assignmentMatcher.find()) {
            String variableName = assignmentMatcher.group(1); // Extract variable name (without the $ sign)
            String value = assignmentMatcher.group(2).trim(); // Extract value and trim any whitespace

            // Check if the variable already exists
            if (variables.variableExists(variableName)) {
                // Update the value of the existing variable
                System.out.println("Updating variable: " + variableName + " to value: " + value);
                variables.setVariable(variableName, value);
            } else {
                // Add the variable as a new pair
                System.out.println("Adding new variable: " + variableName + " with value: " + value);
                variables.setVariable(variableName, value);
            }

            // Remove the assignment statement from the line (since it has been processed)
            line = line.replaceFirst(Pattern.quote(assignmentMatcher.group(0)), "").trim();
        }

        // A regular expression to match a variable reference, e.g., $varName
        Pattern pattern = Pattern.compile("\\$(\\w+)");
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            String variableName = matcher.group(1);

            if (variables.variableExists(variableName)) {
                String value = variables.getVariable(variableName);
                // Replace the variable with its value in the line
                System.out.println("Adding: variable: " + variableName + ", value: " + value);
                line = line.replace("$" + variableName, value);
            } else {
                // Remove the variable reference if it doesn't exist
                System.out.println("Cannot find: variable: " + variableName);
                line = line.replace("$" + variableName, "");
            }
        }
        return line;
    }

}
