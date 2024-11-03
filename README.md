# Pass1 Macro Processor

## Overview
A Pass1 Macro Processor is designed to identify macros in assembly language, record their definitions, and store relevant information in tables for later use. It prepares the data needed for macro expansion in the second pass (Pass2).

## Macro Definitions
1. **What is a macro in assembly language?**
   - A macro is a sequence of instructions that can be reused throughout a program. When a macro is called, it expands in place, replacing the macro call with the macro's body, making code writing faster and reducing errors.

2. **What is the purpose of a Pass1 Macro Processor?**
   - The purpose of a Pass1 Macro Processor is to identify macros, record their definitions, and store relevant information in tables like the Macro Name Table (MNT), Macro Definition Table (MDT), and Argument List Array (ALA) for later use. It helps prepare for macro expansion in the second pass (Pass2).

3. **Explain the function of the Macro Name Table (MNT).**
   - The MNT stores information about each macro, including its name, the starting index in the MDT (Macro Definition Table), and the number of parameters it uses. This helps locate and expand macros during Pass2.

4. **What is the Macro Definition Table (MDT) used for?**
   - The MDT holds the body of each macro, including all instructions within the macro definition, ending with the `MEND` directive. It is referenced during macro expansion to replace macro calls with their definitions.

5. **What is the Argument List Array (ALA), and why is it needed?**
   - The ALA stores parameter names for each macro. When a macro is called with specific arguments, these arguments are mapped to the corresponding placeholders in the ALA, allowing correct substitution during expansion.

## Processor Functionality
6. **How does the processor differentiate between macro definitions and normal instructions?**
   - The processor checks for specific keywords: `MACRO` signals the start of a macro definition, and `MEND` marks the end. Any line outside of these markers is treated as a normal instruction.

7. **Why are there two passes in macro processing?**
   - The first pass (Pass1) collects and stores macro definitions without expanding them, allowing the processor to build necessary tables. The second pass (Pass2) uses these tables to replace macro calls with their definitions in the final code.

8. **Can you explain the flow of your code for this Pass1 Macro Processor?**
   - The code reads each line of input:
     - When `MACRO` is found, it initiates a new macro definition.
     - It stores macro details in the MNT, ALA, and MDT as needed.
     - When `MEND` is encountered, it marks the end of the macro in the MDT.
     - Any other instructions outside of `MACRO/MEND` are ignored in Pass1 (but would be expanded in Pass2).

## Data Structures
9. **What data structures did you use for each table, and why?**
   - **MNT**: A `HashMap` to store macro names as keys and `MNTEntry` objects containing their properties. This allows quick lookup by macro name.
   - **MDT**: A `List` to store the sequence of instructions within each macro.
   - **ALA**: A `HashMap` that maps macro names to lists of arguments, making it easy to associate parameters with their respective macros.

## Advantages of Macros
10. **What are the advantages of using a macro?**
    - Macros help reduce code repetition, make code more readable, and reduce the chance of errors since common sequences of instructions only need to be written once.

11. **How does a macro differ from a function or a subroutine?**
    - A macro is expanded in place at compile-time, directly replacing the call with the macro body, while a function or subroutine call happens at runtime and typically involves branching to a separate memory location.

## Error Handling and Improvements
12. **What would happen if there was no MEND statement in the macro?**
    - The Pass1 Macro Processor relies on `MEND` to signal the end of a macro. Without it, the processor would not know where the macro ends, potentially leading to incorrect entries in the MDT or infinite reading errors.

13. **Can macros be nested within other macros? Does your code handle nested macros?**
    - Macros can be nested, though this implementation does not explicitly handle nested macros. Nested macros would require additional logic to process inner macros and expand them within the context of the outer macro.

14. **How would you handle errors, like undefined macros or incorrect parameters?**
    - Error handling could be added to check if a macro is defined before use and if the correct number of arguments is passed. If errors are found, appropriate messages could be displayed, and processing could halt.

15. **What improvements could you make to this Pass1 Macro Processor?**
    - Potential improvements include adding error checking, handling nested macros, supporting different types of parameters, and optimizing table structures for faster access during expansion.

16. **Why do we need to keep track of `numParams` in the MNT?**
    - `numParams` helps validate the number of arguments passed during macro calls and aids in correct argument substitution when expanding macros in Pass2.

## Pass2 Macro Processor
17. **How would the Pass2 Macro Processor use the tables created in Pass1?**
    - Pass2 would scan for macro calls in the code, look them up in the MNT, retrieve their definitions from the MDT, replace parameters with actual arguments from the ALA, and expand them in the final output.

## Testing and Real-World Applications
18. **How can you test this code to ensure it works correctly?**
    - Use sample inputs with various macro definitions, nested macros, and different parameter counts. Verify that the generated MNT, MDT, and ALA tables match expected values.

19. **How can this project be extended for real-world macro processing?**
    - For real-world scenarios, the project could handle complex parameter types, conditional macros, and error handling. It could also integrate with an assembler to process entire assembly programs with macros.
