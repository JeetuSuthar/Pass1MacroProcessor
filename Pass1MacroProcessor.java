import java.io.*;
import java.util.*;

public class Pass1MacroProcessor {

    static class MNTEntry {
        String macroName;
        int mdtIndex;
        int numParams;

        MNTEntry(String macroName, int mdtIndex, int numParams) {
            this.macroName = macroName;
            this.mdtIndex = mdtIndex;
            this.numParams = numParams;
        }
    }

    static List<String> MDT = new ArrayList<>();  // Macro Definition Table
    static Map<String, MNTEntry> MNT = new HashMap<>();  // Macro Name Table
    static Map<String, List<String>> ALA = new HashMap<>();  // Argument List Array
    static int mdtPointer = 0;  // Keeps track of MDT index

    // Process the macro definitions in the input
    public static void processMacro(List<String> inputLines) {
        boolean isMacroDefinition = false;
        String currentMacro = "";
        List<String> currentALA = new ArrayList<>();

        for (String line : inputLines) {
            String[] tokens = line.trim().split("\\s+");

            if (tokens[0].equals("MACRO")) {
                isMacroDefinition = true;
                currentALA.clear();
            } else if (tokens[0].equals("MEND")) {
                isMacroDefinition = false;
                MDT.add("MEND");
                mdtPointer++;
                currentMacro = "";  // Reset currentMacro after completing a macro definition
            } else if (isMacroDefinition) {
                // First line after "MACRO" is the macro name and its parameters
                if (currentMacro.isEmpty()) {
                    currentMacro = tokens[0];
                    for (int i = 1; i < tokens.length; i++) {
                        currentALA.add(tokens[i]);
                    }
                    // Store in MNT and ALA
                    ALA.put(currentMacro, new ArrayList<>(currentALA));
                    MNT.put(currentMacro, new MNTEntry(currentMacro, mdtPointer, currentALA.size()));
                } else {
                    MDT.add(line);
                    mdtPointer++;
                }
            }
        }
    }

    public static void main(String[] args) {
        // Example input
        List<String> inputLines = Arrays.asList(
            "MACRO",
            "INCR &ARG3 &ARG2",
            "ADD AREG, &ARG1",
            "MOVER BREG, &ARG1",
            "MEND",
            "MACRO",
            "PVG &ARG2 &ARG1",
            "SUB AREG, &ARG2",
            "MOVER CREG, &ARG1",
            "MEND"
        );

        // Process the macro definitions
        processMacro(inputLines);

        // Display MNT
        System.out.println("Macro Name Table (MNT):");
        for (Map.Entry<String, MNTEntry> entry : MNT.entrySet()) {
            System.out.println("Macro Name: " + entry.getKey() + ", MDT Index: " + entry.getValue().mdtIndex + ", Num Params: " + entry.getValue().numParams);
        }

        // Display MDT
        System.out.println("\nMacro Definition Table (MDT):");
        for (int i = 0; i < MDT.size(); i++) {
            System.out.println("MDT[" + i + "]: " + MDT.get(i));
        }

        // Display ALA
        System.out.println("\nArgument List Array (ALA):");
        for (Map.Entry<String, List<String>> entry : ALA.entrySet()) {
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
  
 javac filename.java
 java filename.java

  */