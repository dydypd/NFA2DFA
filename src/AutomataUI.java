import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

public class AutomataUI extends JFrame implements ActionListener {
    private JTextField nField;
    private JTextField alphabetField;
    private JTextField startField;
    private JTextField endField;
    private JButton createButton;
    private JButton convertButton;
    private JTable table;
    private JScrollPane scrollPane;
    private JLabel messageLabel;

    public AutomataUI() {
        super("Đơn định hoá Otomat");
        setLayout(new FlowLayout());
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        nField = new JTextField(20);
        alphabetField = new JTextField(30);
        startField = new JTextField(20);
        endField = new JTextField(10);
        createButton = new JButton("Tạo Bảng");
        createButton.addActionListener(this);
        table = new JTable();
        scrollPane = new JScrollPane(table);
        messageLabel = new JLabel();
        convertButton = new JButton("Chuyển đổi");
        convertButton.addActionListener(this);

        add(new JLabel("Nhập vào số trạng thái:"));
        add(nField);
        add(new JLabel("Nhập vào bảng chữ cái (Ngăn cách nhau bởi dấu phẩy):"));
        add(alphabetField);
        add(new JLabel("Nhập vào trạng thái bắt đầu:"));
        add(startField);
        add(new JLabel("Nhập vào trạng thái kết thúc (Ngăn cách nhau bằng dấu phẩy):"));
        add(endField);
        add(createButton);
        add(scrollPane);
        add(convertButton);
        add(messageLabel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            String nText = nField.getText();
            String alphabetText = alphabetField.getText();
            String startText = startField.getText();
            String endText = endField.getText();

            try {
                int n = Integer.parseInt(nText);
                if (n <= 0) {
                    throw new NumberFormatException("Số trạng thái phải dương.");
                }

                String[] alphabet = alphabetText.replaceAll(" ", "").split(",");
                if (alphabet.length == 0) {
                    throw new IllegalArgumentException("Bảng chữ cái không được để trống.");
                }

                if (startText.length() == 0) {
                    throw new IllegalArgumentException("Trạng thái bắt đầu phải có dạng 1 số.");
                }
                int start = Integer.parseInt(startText);
                if (start < 0 || start >= n) {
                    throw new IllegalArgumentException("Trạng thái bắt đầu phải lớn hơn 0 và nhỏ hơn " + (n - 1) + ".");
                }

                if (endText.length() == 0) {
                    throw new IllegalArgumentException("Trạng thái kết thúc phải là 1 số hoặc 1 dãy số ngăn cách nhau bơỉ dấu phảy.");
                }
                String[] end = endText.split(",");
                for (int i = 0; i < end.length; i++) {
                    end[i] = end[i].trim();
                    if (Integer.parseInt(end[i]) < 0 || Integer.parseInt(end[i]) >= n) {
                        throw new IllegalArgumentException("Trạng thái kết thúc phải lớn hơn 0 và nhỏ hơn " + (n - 1) + ".");
                    }
                }

                DefaultTableModel model = new DefaultTableModel(n, alphabet.length + 2);
                table.setModel(model);

                table.getColumnModel().getColumn(0).setHeaderValue("Trạng thái:");
                for (int i = 0; i < alphabet.length; i++) {
                    table.getColumnModel().getColumn(i + 1).setHeaderValue(alphabet[i]);
                }
                table.getColumnModel().getColumn(alphabet.length + 1).setHeaderValue("ε");

                for (int i = 0; i < n; i++) {
                    table.setValueAt(i, i, 0);
                }

                messageLabel.setText("");

            } catch (NumberFormatException ex) {
                messageLabel.setText("Đầu vào không hợp lệ: " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                messageLabel.setText("Đầu vào không hợp lệ: " + ex.getMessage());
            }
        } else if (e.getSource() == convertButton) {
            int n = Integer.parseInt(nField.getText());
            String[] alphabet = alphabetField.getText().replaceAll(" ","").split(",");
            String start = startField.getText();
            String end = endField.getText().replaceAll(" ","");
            String[][] tableString = new String[n][alphabet.length + 2];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < alphabet.length + 2; j++) {
                    if (table.getValueAt(i, j) != null)
                        tableString[i][j] = table.getValueAt(i, j).toString();
                    else tableString[i][j] = "";
                }
            }

            StringBuilder result = new StringBuilder();
            result.append(n).append("\n");
            result.append(String.join(" ", alphabet)).append("\n");
            for (int i = 0; i < n; i++) {
                result.append(i).append(": ");

                for (int j = 1; j < alphabet.length + 2; j++) {
                    result.append("{").append(tableString[i][j]).append("} ").append(" ");
                }

                result.append("\n");
            }
            result.append(start).append("\n");
            result.append("{").append(end).append("}").append("\n");
            System.out.println(result.toString());
            Scanner scanner = new Scanner(result.toString());
            NFA nfa = new NFA(scanner);
            NFA dfa = nfa.convertToDFA();
            JFrame frame = new JFrame("Otomat đơn định");
            frame.setSize(500, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Sigma");
            for (Character c : dfa.inputs) {
                model.addColumn(c);
            }
            for (int i = 0; i < dfa.numStates; i++) {
                Object[] row = new Object[dfa.inputs.size() + 1];
                row[0] = i;
                int j = 1;
                for (Character c : dfa.inputs) {
                    row[j] = dfa.getTransitionState(c, i).get(0);
                    j++;
                }
                model.addRow(row);
            }

            JTable table = new JTable(model);

            JScrollPane scrollPane = new JScrollPane(table);
            JLabel startLabel = new JLabel("Trạng thái bắt đầu:" + dfa.initialState);

            JLabel endLabel = new JLabel("Trạng thái kết thúc:" + dfa.finalStates);
            JPanel panel = new JPanel();
            panel.add(scrollPane);
            panel.add(startLabel);
            panel.add(endLabel);
            frame.add(panel);
            frame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        AutomataUI ui = new AutomataUI();
    }
}
