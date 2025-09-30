
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Main {
    private static final Window window = new Window(400, 400, "ToDoList");
    
    // ^ Components
    private static JButton CreateTask;
    private static JButton DeleteTask;
    private static JButton Save;
    private static Font font;
    private static JTextArea ModifiableTextArea;
    private static JScrollPane TextAreaScrollPane;

    // ^ Program Variables
    private static int TaskCount = 0;

    public static String[] showTwoInputDialog(JFrame parentFrame, String title) {
        JTextField input1 = new JTextField(10);
        JTextField input2 = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Name:"));
        panel.add(input1);
        panel.add(new JLabel("Task:"));
        panel.add(input2);

        JDialog dialog = new JDialog(parentFrame, title, true);
        dialog.getContentPane().add(panel, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener((_) -> dialog.dispose());
        okButton.setEnabled(false);

        input2.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (input2.getText().equals("")) {
                    okButton.setEnabled(false);
                } else {
                    okButton.setEnabled(true);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // ! Not Implemented
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // ! Not Implemented
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);

        return new String[]{input1.getText(), input2.getText()};
    }


    public static void PutComponents() {
        CreateTask = new JButton("Create");
        CreateTask.setBounds(0, 315, 100, 50);

        DeleteTask = new JButton("Delete");
        DeleteTask.setBounds(105, 315, 100, 50);

        Save = new JButton("Save to file.");
        Save.setBounds(210, 315, 190, 50);

        font = new Font("Consolas", Font.BOLD, 16);
        font = font.deriveFont(16.0f);

        ModifiableTextArea = new JTextArea();
        ModifiableTextArea.setFont(font);
        ModifiableTextArea.setBackground(Color.WHITE);
        ModifiableTextArea.setForeground(new Color(155, 75, 0));
        ModifiableTextArea.setEditable(false);
        ModifiableTextArea.setLineWrap(true);
        TextAreaScrollPane = new JScrollPane(ModifiableTextArea);
        TextAreaScrollPane.setBounds(0, 0, 400, 300);


        // ~ ------------- Create Task
        CreateTask.addActionListener((e) -> {
            if (e.getSource() == CreateTask) {
                String[] inputs = showTwoInputDialog(window.frame(), "Task Creation");
                String task_name = inputs[0].equals("") ? "Task" + (TaskCount++) + " - " : inputs[0] + " - ";
                String task = inputs[1];
                ModifiableTextArea.setText(ModifiableTextArea.getText() + task_name + task + "\n");
            }
        });
        
        // ~ ------------- Delete Task
        DeleteTask.addActionListener((e) -> {
            if (e.getSource() == DeleteTask) {
                String task_name = JOptionPane.showInputDialog(window.frame(), "Enter the name of the task");
                if (task_name == null || task_name.equals("")) { JOptionPane.showMessageDialog(window.frame(), "No task name given. Operation Incomplete."); }
                String text = ModifiableTextArea.getText();
                String[] tasks = text.split("\n");
                boolean task_found = false;
                ModifiableTextArea.setText("");
                
                for (String task : tasks) {
                    String[] task_parts = task.split("-");
                    String task_n = task_parts[0].trim();
                    
                    if (!task_n.equals(task_name)) {
                        ModifiableTextArea.setText(ModifiableTextArea.getText() + task + "\n");
                    } else task_found = true;
                }
                
                if (!task_found)
                    JOptionPane.showMessageDialog(window.frame(), "Task not found.");
            }
        });

        // ~ ------------- Save to file Task
        Save.addActionListener((e) -> {
            if (e.getSource() == Save) {
                final JFileChooser chooser = new JFileChooser();
                if (chooser.showSaveDialog(window.frame()) == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write(ModifiableTextArea.getText().toCharArray());
                    } catch (IOException exception) {
                        JOptionPane.showMessageDialog(window.frame(), "Unexpected Error.");
                    }
                }
            }
        });

        window.addComponent("Button1", CreateTask);
        window.addComponent("Button2", DeleteTask);
        window.addComponent("Button3", Save);
        window.addComponent("TaskDisplay", TextAreaScrollPane);

        window.updateComponents();
    }

    public static void main(String[] args) {
        PutComponents();
        window.EnableVisibility();
    }
}