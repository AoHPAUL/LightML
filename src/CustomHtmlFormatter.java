public class CustomHtmlFormatter {

    public static String prettyPrintHtml(StringBuilder html) {
        StringBuilder formattedHtml = new StringBuilder();
        int indentLevel = 0;
        String[] lines = html.toString().split("\n");

        for (String line : lines) {
            line = line.trim(); // Remove leading and trailing spaces

            // Skip empty lines
            if (line.isEmpty()) {
                continue;
            }

            // Handle indentation rules for <!DOCTYPE> and <html> tags
            if (line.startsWith("<!DOCTYPE html>") || line.startsWith("<html") || line.startsWith("</html")) {
                formattedHtml.append(line).append("\n");
                continue;
            }

            // Handle <head> and <body> tags
            if (line.startsWith("<head")) {
                indentLevel = 1;
                formattedHtml.append(getIndentedLine(line, indentLevel)).append("\n");
                indentLevel++; // Increase for content inside head
                continue;
            }

            if (line.startsWith("</head>")) {
                indentLevel = 1;
                formattedHtml.append(getIndentedLine(line, indentLevel)).append("\n");
                continue;
            }

            if (line.startsWith("<body")) {
                indentLevel = 1;
                formattedHtml.append(getIndentedLine(line, indentLevel)).append("\n");
                indentLevel++; // Increase indent for body content
                continue;
            }

            if (line.startsWith("</body")) {
                indentLevel = 1;
                formattedHtml.append(getIndentedLine(line, indentLevel)).append("\n");
                continue;
            }

            // Handle closing tags before the line is appended
            if (line.startsWith("</nav") || line.startsWith("</div") || line.startsWith("</form") ||
                    line.startsWith("</header") || line.startsWith("</ol") || line.startsWith("</ul") ||
                    line.startsWith("</table") || line.startsWith("</tr")) {

                indentLevel--; // Decrease indent before appending the closing tag
                formattedHtml.append(getIndentedLine(line, indentLevel)).append("\n");
                continue;
            }

            // Handle self-closing tags
            if (isSelfClosingTag(line)) {
                formattedHtml.append(getIndentedLine(line, indentLevel)).append("\n");
                continue;
            }

            // Handle opening tags and increase indentation
            if (line.startsWith("<nav") || line.startsWith("<div") || line.startsWith("<form") ||
                    line.startsWith("<header") || line.startsWith("<ol") || line.startsWith("<ul") ||
                    line.startsWith("<table") || line.startsWith("<tr")) {

                formattedHtml.append(getIndentedLine(line, indentLevel)).append("\n");
                indentLevel++; // Increase indent after appending the opening tag
                continue;
            }

            // If none of the conditions match, append the line with current indentation
            formattedHtml.append(getIndentedLine(line, indentLevel)).append("\n");
        }

        return formattedHtml.toString().trim();
    }

    private static boolean isSelfClosingTag(String line) {
        // Define a set of self-closing and block-level tags
        String[] selfClosingTags = {
                "area", "base", "br", "col", "embed", "hr", "img", "input",
                "keygen", "link", "meta", "param", "source", "track", "wbr"
        };
        for (String tag : selfClosingTags) {
            if (line.startsWith("<" + tag) && line.endsWith("/>")) {
                return true;
            }
        }
        return false;
    }

    private static String getIndentedLine(String line, int indentLevel) {
        StringBuilder indent = new StringBuilder();
        for (int j = 0; j < indentLevel; j++) {
            indent.append("\t");
        }
        return indent.toString() + line; // Add indentation to the line
    }

    public static void main(String[] args) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n")
                .append("<html>\n")
                .append("<head>\n")
                .append("<title>Test</title>\n")
                .append("<meta charset='UTF-8'/>\n")
                .append("<link rel='stylesheet' href='style.css'/>\n")
                .append("</head>\n")
                .append("<body>\n")
                .append("<nav>\n")
                .append("<ul>\n")
                .append("<li>Home</li>\n")
                .append("<li>About</li>\n")
                .append("</ul>\n")
                .append("</nav>\n")
                .append("<div>\n")
                .append("<p>Hello, world!</p>\n")
                .append("</div>\n")
                .append("<img src='image.jpg'/>\n")
                .append("<script src='script.js'></script>\n")
                .append("</body>\n")
                .append("</html>");

        String prettyHtml = prettyPrintHtml(html);
        System.out.println(prettyHtml);
    }
}
