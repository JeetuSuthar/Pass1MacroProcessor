import java.io.*; // Importing classes for input and output
import java.util.*; // Importing classes for data structures

public class Pass1MacroProcessor {

    // Class to represent an entry in the Macro Name Table (MNT)
    static class MNTEntry {
        String macroName; // Name of the macro
        int mdtIndex; // Index in the Macro Definition Table (MDT)
        int numParams; // Number of parameters the macro accepts

        // Constructor to initialize MNTEntry with macro details
        MNTEntry(String macroName, int mdtIndex, int numParams) {
            this.macroName = macroName; // Assigning the macro name
            this.mdtIndex = mdtIndex; // Assigning the MDT index
            this.numParams = numParams; // Assigning the number of parameters
        }
    }

    // List to hold the Macro Definition Table (MDT)
    static List<String> MDT = new ArrayList<>();
    // Map to hold the Macro Name Table (MNT), mapping macro names to MNTEntry
    static Map<String, MNTEntry> MNT = new HashMap<>();
    // Map to hold the Argument List Array (ALA), mapping macro names to their parameters
    static Map<String, List<String>> ALA = new HashMap<>();
    static int mdtPointer = 0; // Pointer to track the current index in the MDT

    // Method to process macro definitions from the input
    public static void processMacro(List<String> inputLines) {
        boolean isMacroDefinition = false; // Flag to track if we're currently processing a macro definition
        String currentMacro = ""; // To store the name of the current macro
        List<String> currentALA = new ArrayList<>(); // List to store the parameters of the current macro

        // Loop through each line of input
        for (String line : inputLines) {
            // Split the line into tokens (words)
            String[] tokens = line.trim().split("\\s+");

            // Check if the current line starts a new macro definition
            if (tokens[0].equals("MACRO")) {
                isMacroDefinition = true; // Set the flag indicating we are now in a macro definition
                currentALA.clear(); // Clear any previous parameters
            } 
            // Check if we have reached the end of a macro definition
            else if (tokens[0].equals("MEND")) {
                isMacroDefinition = false; // Reset the macro definition flag
                MDT.add("MEND"); // Add the MEND directive to the MDT
                mdtPointer++; // Increment the MDT pointer
                currentMacro = "";  // Reset currentMacro after completing a macro definition
            } 
            // If we are within a macro definition
            else if (isMacroDefinition) {
                // If it's the first line after "MACRO", it's the macro name and its parameters
                if (currentMacro.isEmpty()) {
                    currentMacro = tokens[0]; // Store the macro name
                    // Loop through the remaining tokens to capture parameters
                    for (int i = 1; i < tokens.length; i++) {
                        currentALA.add(tokens[i]); // Add parameters to the current ALA
                    }
                    // Store the macro details in MNT and ALA
                    ALA.put(currentMacro, new ArrayList<>(currentALA)); // Map the macro to its parameters
                    MNT.put(currentMacro, new MNTEntry(currentMacro, mdtPointer, currentALA.size())); // Add entry to MNT
                } else {
                    MDT.add(line); // Add the instruction line to the MDT
                    mdtPointer++; // Increment the MDT pointer for the next instruction
                }
            }
        }
    }

    // Main method to execute the macro processor
    public static void main(String[] args) {
        // Example input: list of lines simulating macro definitions
        List<String> inputLines = Arrays.asList(
            "MACRO",
            "INCR &ARG3 &ARG2", // Define macro INCR with parameters
            "ADD AREG, &ARG1", // Instruction inside the macro
            "MOVER BREG, &ARG1", // Another instruction inside the macro
            "MEND", // End of macro definition
            "MACRO",
            "PVG &ARG2 &ARG1", // Define another macro PVG with parameters
            "SUB AREG, &ARG2", // Instruction inside the macro
            "MOVER CREG, &ARG1", // Another instruction inside the macro
            "MEND" // End of macro definition
        );

        // Call the method to process the macro definitions
        processMacro(inputLines);

        // Display the contents of the Macro Name Table (MNT)
        System.out.println("Macro Name Table (MNT):");
        for (Map.Entry<String, MNTEntry> entry : MNT.entrySet()) {
            // Print each macro's name, index in MDT, and number of parameters
            System.out.println("Macro Name: " + entry.getKey() + ", MDT Index: " + entry.getValue().mdtIndex + ", Num Params: " + entry.getValue().numParams);
        }

        // Display the contents of the Macro Definition Table (MDT)
        System.out.println("\nMacro Definition Table (MDT):");
        for (int i = 0; i < MDT.size(); i++) {
            // Print each instruction in the MDT with its index
            System.out.println("MDT[" + i + "]: " + MDT.get(i));
        }

        // Display the contents of the Argument List Array (ALA)
        System.out.println("\nArgument List Array (ALA):");
        for (Map.Entry<String, List<String>> entry : ALA.entrySet()) {
            // Print each macro with its associated parameters
            System.out.println("Macro: " + entry.getKey() + " -> " + entry.getValue());
        }
    }
}

/*
 ********************OUTPUT****************
Macro Name Table (MNT):
Macro Name: PVG, MDT Index: 3, Num Params: 2
Macro Name: INCR, MDT Index: 0, Num Params: 2

Macro Definition Table (MDT):
MDT[0]: ADD AREG, &ARG1
MDT[1]: MOVER BREG, &ARG1
MDT[2]: MEND
MDT[3]: SUB AREG, &ARG2
MDT[4]: MOVER CREG, &ARG1
MDT[5]: MEND

Argument List Array (ALA):
Macro: PVG -> [&ARG2, &ARG1]
Macro: INCR -> [&ARG3, &ARG2]

=== Code Execution Successful ===
 */

/*
  
 javac filename.java // Compile the Java program
 java filename.java // Run the compiled Java program

*/
