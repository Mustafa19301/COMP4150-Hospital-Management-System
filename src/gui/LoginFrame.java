package gui;

import dao.AuthDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.sql.SQLException;
import java.util.Map;

public class LoginFrame extends JFrame {

    private static final Color DARK = new Color(16, 45, 56);
    private static final Color TEAL = new Color(23, 107, 104);
    private static final Color BACKGROUND = new Color(243, 247, 246);
    private static final Color TEXT = new Color(35, 58, 62);
    private static final Color MUTED = new Color(112, 132, 134);

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel errorLabel;
    private JButton loginButton;

    public LoginFrame() {

        setTitle("CarePoint | Hospital Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 760);
        setMinimumSize(new Dimension(1000, 680));
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        mainPanel.add(createBrandPanel());
        mainPanel.add(createLoginPanel());

        setContentPane(mainPanel);
    }

    private JPanel createBrandPanel() {

        JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);

                Graphics2D g2 = (Graphics2D) graphics.create();

                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(13, 39, 49),
                        getWidth(), getHeight(), new Color(24, 76, 79)
                );

                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setColor(new Color(255, 255, 255, 12));
                g2.fill(new Ellipse2D.Double(
                        getWidth() - 170, -100, 300, 300
                ));

                g2.fill(new Ellipse2D.Double(
                        getWidth() - 240, getHeight() - 210, 340, 340
                ));

                g2.dispose();
            }
        };

        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(55, 55, 45, 45));

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JPanel brand = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        brand.setOpaque(false);
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel emblem = new JLabel("+", SwingConstants.CENTER);
        emblem.setFont(new Font("SansSerif", Font.BOLD, 32));
        emblem.setForeground(Color.WHITE);
        emblem.setOpaque(true);
        emblem.setBackground(new Color(31, 105, 103));
        emblem.setPreferredSize(new Dimension(52, 52));

        JPanel brandText = new JPanel();
        brandText.setOpaque(false);
        brandText.setLayout(new BoxLayout(brandText, BoxLayout.Y_AXIS));
        brandText.setBorder(new EmptyBorder(3, 14, 0, 0));

        JLabel brandName = new JLabel("CAREPOINT");
        brandName.setFont(new Font("SansSerif", Font.BOLD, 19));
        brandName.setForeground(Color.WHITE);

        JLabel brandSubtitle = new JLabel("HOSPITAL INFORMATION SYSTEM");
        brandSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 9));
        brandSubtitle.setForeground(new Color(173, 204, 201));

        brandText.add(brandName);
        brandText.add(Box.createVerticalStrut(4));
        brandText.add(brandSubtitle);

        brand.add(emblem);
        brand.add(brandText);

        JLabel eyebrow = new JLabel("CONNECTED CARE");
        eyebrow.setFont(new Font("SansSerif", Font.BOLD, 11));
        eyebrow.setForeground(new Color(125, 203, 185));
        eyebrow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel heading = new JLabel(
                "<html>Better care starts<br>with better<br>coordination.</html>"
        );
        heading.setFont(new Font("SansSerif", Font.BOLD, 36));
        heading.setForeground(Color.WHITE);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel description = new JLabel(
                "<html>A centralized workspace for managing<br>" +
                        "patient information, clinical records and<br>" +
                        "hospital operations.</html>"
        );
        description.setFont(new Font("SansSerif", Font.PLAIN, 15));
        description.setForeground(new Color(195, 216, 214));
        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel security = new JPanel(new FlowLayout(FlowLayout.LEFT, 9, 0));
        security.setOpaque(false);
        security.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel shield = new JLabel("\u2713");
        shield.setForeground(new Color(125, 203, 185));
        shield.setFont(new Font("SansSerif", Font.BOLD, 17));

        JLabel securityText = new JLabel("Secure staff access");
        securityText.setForeground(new Color(195, 216, 214));
        securityText.setFont(new Font("SansSerif", Font.PLAIN, 13));

        security.add(shield);
        security.add(securityText);

        content.add(brand);
        content.add(Box.createVerticalGlue());
        content.add(eyebrow);
        content.add(Box.createVerticalStrut(20));
        content.add(heading);
        content.add(Box.createVerticalStrut(22));
        content.add(description);
        content.add(Box.createVerticalStrut(35));
        content.add(security);
        content.add(Box.createVerticalGlue());

        JLabel footer = new JLabel("CAREPOINT  |  INTERNAL STAFF PORTAL");
        footer.setFont(new Font("SansSerif", Font.PLAIN, 10));
        footer.setForeground(new Color(155, 185, 183));

        panel.add(content, BorderLayout.CENTER);
        panel.add(footer, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createLoginPanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(42, 42, 35, 42));
        card.setPreferredSize(new Dimension(460, 530));

        JPanel formContent = new JPanel();
        formContent.setBackground(Color.WHITE);
        formContent.setLayout(new BoxLayout(formContent, BoxLayout.Y_AXIS));
        formContent.setAlignmentX(Component.CENTER_ALIGNMENT);
        formContent.setPreferredSize(new Dimension(376, 390));
        formContent.setMaximumSize(new Dimension(376, 390));

        JLabel eyebrow = createLabel(
                "STAFF WORKSPACE", 10, Font.BOLD, TEAL
        );
        eyebrow.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel heading = createLabel(
                "Staff sign in", 30, Font.BOLD, TEXT
        );
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel description = createLabel(
                "Enter your credentials to access the system.",
                13, Font.PLAIN, MUTED
        );
        description.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameField = createInputField();

        passwordField = new JPasswordField();
        styleInputField(passwordField);

        errorLabel = createLabel(
                " ", 12, Font.PLAIN, new Color(190, 55, 55)
        );
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton = new JButton("Sign in to workspace");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(TEAL);
        loginButton.setOpaque(true);
        loginButton.setContentAreaFilled(true);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(new EmptyBorder(14, 15, 14, 15));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(376, 50));
        loginButton.setPreferredSize(new Dimension(376, 50));

        loginButton.addActionListener(e -> authenticate());
        passwordField.addActionListener(e -> authenticate());

        formContent.add(eyebrow);
        formContent.add(Box.createVerticalStrut(12));
        formContent.add(heading);
        formContent.add(Box.createVerticalStrut(9));
        formContent.add(description);

        formContent.add(Box.createVerticalStrut(38));
        formContent.add(createCenteredField(
                createFieldLabel("USERNAME"),
                usernameField
        ));

        formContent.add(Box.createVerticalStrut(23));
        formContent.add(createCenteredField(
                createFieldLabel("PASSWORD"),
                passwordField
        ));

        formContent.add(Box.createVerticalStrut(10));
        formContent.add(errorLabel);
        formContent.add(Box.createVerticalStrut(12));
        formContent.add(loginButton);

        JLabel footer = createLabel(
                "Authorized hospital personnel only",
                11, Font.PLAIN, MUTED
        );
        footer.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(formContent);
        card.add(Box.createVerticalGlue());
        card.add(Box.createVerticalStrut(24));
        card.add(footer);

        panel.add(card);

        getRootPane().setDefaultButton(loginButton);

        return panel;
    }

    private JPanel createCenteredField(
            JLabel label, JTextField field) {

        JPanel wrapper = new JPanel();
        wrapper.setBackground(Color.WHITE);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setAlignmentX(Component.CENTER_ALIGNMENT);

        wrapper.setPreferredSize(new Dimension(376, 75));
        wrapper.setMinimumSize(new Dimension(376, 75));
        wrapper.setMaximumSize(new Dimension(376, 75));

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setPreferredSize(new Dimension(350, 20));
        label.setMaximumSize(new Dimension(350, 20));
        label.setMinimumSize(new Dimension(350, 20));

        label.setHorizontalAlignment(SwingConstants.LEFT);

        field.setAlignmentX(Component.CENTER_ALIGNMENT);

        wrapper.add(label);
        wrapper.add(Box.createVerticalStrut(9));
        wrapper.add(field);

        return wrapper;
    }

    private JLabel createLabel(
            String text, int size, int style, Color color) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", style, size));
        label.setForeground(color);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        return label;
    }

    private JLabel createFieldLabel(String text) {

        return createLabel(
                text, 11, Font.BOLD, new Color(69, 91, 93)
        );
    }

    private JTextField createInputField() {

        JTextField field = new JTextField();
        styleInputField(field);

        return field;
    }

    private void styleInputField(JTextField field) {

        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setForeground(TEXT);
        field.setBackground(new Color(250, 252, 252));

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(218, 228, 226)
                ),
                new EmptyBorder(11, 13, 11, 13)
        ));

        field.setPreferredSize(new Dimension(350, 46));
        field.setMinimumSize(new Dimension(350, 46));
        field.setMaximumSize(new Dimension(350, 46));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void authenticate() {

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter both username and password.");
            return;
        }

        loginButton.setEnabled(false);
        loginButton.setText("Signing in...");
        errorLabel.setText(" ");

        try {
            AuthDAO authDAO = new AuthDAO();

            Map<String, Object> staff = authDAO.login(username, password);

            if (staff == null) {
                errorLabel.setText("Invalid username or password.");
                passwordField.setText("");
                return;
            }

            DashboardFrame dashboard = new DashboardFrame(staff);
            dashboard.setVisible(true);

            dispose();

        } catch (SQLException exception) {

            errorLabel.setText("Unable to connect to the database.");

            JOptionPane.showMessageDialog(
                    this,
                    "Database connection failed:\n" + exception.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } finally {
            loginButton.setEnabled(true);
            loginButton.setText("Sign in to workspace");
        }
    }
}