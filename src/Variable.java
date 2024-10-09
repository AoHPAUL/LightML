import java.util.HashMap;

public class Variable {
    // HashMap to store variable names and their corresponding values
    private HashMap<String, String> variables;

    public Variable() {
        this.variables = new HashMap<>();
    }

    // Method to add or update a variable
    public void setVariable(String name, String value) {
        variables.put(name, value);
    }

    // Method to retrieve a variable value
    public String getVariable(String name) {
        return variables.getOrDefault(name, null);
    }

    // Method to check if a variable exists
    public boolean variableExists(String name) {
        return variables.containsKey(name);
    }
}
