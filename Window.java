
import java.awt.Component;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window {
    private final JFrame frame;
    private final JPanel panel;
    private final HashMap<String, Component> components = new HashMap<>();

    public Window(int width, int height, String title) {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        panel = new JPanel(null);
        panel.setBorder(BorderFactory.createEmptyBorder());
        frame.add(panel);
    }

    public void EnableVisibility() {
        frame.setVisible(true);
    }

    public void DisableVisibility() {
        frame.setVisible(false);
    }

    public void addComponent(String id, final Component component) {
        components.put(id, component);
    }

    public <T extends Component> void createComponent(String id, int x, int y, int width, int height) {
        final T resultant = (T) new Component() {};
        resultant.setBounds(x, y, width, height);
        components.put(id, resultant);
    }

    public void createButton(String id, int x, int y, String onButtonText, int width, int height, Runnable func) {
        final JButton button = new JButton(onButtonText);
        button.setBounds(x, y, width, height);
        button.addActionListener((e) -> {
            if (e.getSource() == button) {
                func.run();
            }
        });
        components.put(id, button);
    }

    public void createLabel(String id, int x, int y, String labelText, int width, int height) {
        final JLabel label = new JLabel(labelText);
        label.setBounds(x, y, width, height);
        components.put(id, label);
    }

    public void updateComponents() {
        for (Component component : components.values()) {
            panel.add(component);
            panel.setComponentZOrder(component, 0);
        }
    }

    // Getters
    public <T extends Component> T getComponent(String id) {
        return (T) components.get(id);
    }

    public JFrame frame() {
        return frame;
    }

    public JPanel panel() {
        return panel;
    }

    public HashMap<String, Component> components() {
        return components;
    }
}
