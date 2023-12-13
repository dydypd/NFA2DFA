# Deterministic Finite Automaton (DFA) Converter UI

This Java code implements a graphical user interface (GUI) for creating and converting finite automata. The program allows users to input the details of a finite automaton (FA) and then converts it into a Deterministic Finite Automaton (DFA). The GUI is built using the Swing framework.

## Class: AutomataUI

### Constructor

```java
public AutomataUI();
```

Creates the main GUI window for the Deterministic Finite Automaton converter.

### Fields

- `nField`: JTextField for entering the number of states.
- `alphabetField`: JTextField for entering the alphabet separated by commas.
- `startField`: JTextField for entering the start state.
- `endField`: JTextField for entering the end states separated by commas.
- `createButton`: JButton to create the automaton table.
- `convertButton`: JButton to convert the automaton to DFA.
- `table`: JTable for displaying the automaton table.
- `scrollPane`: JScrollPane for scrolling through the automaton table.
- `messageLabel`: JLabel for displaying error messages.

### Methods

```java
public void actionPerformed(ActionEvent e);
```

Handles button click events. Implements logic for creating the automaton table and converting it to a DFA.

### Usage Example

```java
public static void main(String[] args) {
    AutomataUI ui = new AutomataUI();
}
```

Run this code to launch the GUI application for creating and converting finite automata.

## How to Use the GUI

1. Enter the number of states in the `Nhập vào số trạng thái` field.
2. Enter the alphabet, separating characters by commas, in the `Nhập vào bảng chữ cái` field.
3. Enter the start state in the `Nhập vào trạng thái bắt đầu` field.
4. Enter the end states, separated by commas, in the `Nhập vào trạng thái kết thúc` field.
5. Click the `Tạo Bảng` button to create the automaton table.
6. Click the `Chuyển đổi` button to convert the automaton to a DFA.
7. The converted DFA is displayed in a new window with its transition table.

Note: The GUI provides error messages if the input is invalid.

Feel free to use and modify this code for your projects. Refer to the code comments for additional details on the implementation.
