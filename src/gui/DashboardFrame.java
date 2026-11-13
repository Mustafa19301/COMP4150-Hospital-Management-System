package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;

public class DashboardFrame extends JFrame {

    private static final Color DARK = new Color(16, 45, 56);
    private static final Color TEAL = new Color(23, 107, 104);
    private static final Color BACKGROUND = new Color(243, 247, 246);
    private static final Color TEXT = new Color(35, 58, 62);
    private static final Color MUTED = new Color(112, 132, 134);
    private static final Color WHITE = Color.WHITE;

    private final String name;
    private final String role;

    public DashboardFrame(Map<String, Object> staff) {

        name = (String) staff.get("name");
        role = (String) staff.get("role");

        setTitle("CarePoint | Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1250, 780);
        setMinimumSize(new Dimension(1000, 650));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createSidebar(), BorderLayout.WEST);
        root.add(createMainContent(), BorderLayout.CENTER);

        setContentPane(root);
    }

    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(WHITE);
        header.setBorder(new EmptyBorder(17, 30, 17, 30));
        header.setPreferredSize(new Dimension(0, 76));

        JLabel brand = new JLabel("CAREPOINT");
        brand.setFont(new Font("SansSerif", Font.BOLD, 20));
        brand.setForeground(DARK);

        JPanel profile = new JPanel(new FlowLayout(
                FlowLayout.RIGHT, 12, 0
        ));
        profile.setOpaque(false);

        JLabel avatar = new JLabel(
                name.substring(0, 1).toUpperCase(),
                SwingConstants.CENTER
        );
        avatar.setOpaque(true);
        avatar.setBackground(new Color(222, 239, 235));
        avatar.setForeground(TEAL);
        avatar.setFont(new Font("SansSerif", Font.BOLD, 17));
        avatar.setPreferredSize(new Dimension(40, 40));

        JPanel profileText = new JPanel();
        profileText.setOpaque(false);
        profileText.setLayout(new BoxLayout(
                profileText, BoxLayout.Y_AXIS
        ));

        JLabel employeeName = new JLabel(name);
        employeeName.setFont(new Font("SansSerif", Font.BOLD, 13));
        employeeName.setForeground(TEXT);

        JLabel employeeRole = new JLabel(role);
        employeeRole.setFont(new Font("SansSerif", Font.PLAIN, 11));
        employeeRole.setForeground(MUTED);

        profileText.add(employeeName);
        profileText.add(Box.createVerticalStrut(4));
        profileText.add(employeeRole);

        profile.add(avatar);
        profile.add(profileText);

        header.add(brand, BorderLayout.WEST);
        header.add(profile, BorderLayout.EAST);

        return header;
    }

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel();
        sidebar.setBackground(DARK);
        sidebar.setPreferredSize(new Dimension(245, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(30, 20, 25, 20));

        JLabel sectionTitle = new JLabel("WORKSPACE");
        sectionTitle.setFont(new Font("SansSerif", Font.BOLD, 10));
        sectionTitle.setForeground(new Color(151, 184, 181));
        sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(sectionTitle);
        sidebar.add(Box.createVerticalStrut(22));

        addNavigationItem(sidebar, "Dashboard", true);

        sidebar.add(Box.createVerticalStrut(24));

        JLabel moduleTitle = new JLabel("HOSPITAL SERVICES");
        moduleTitle.setFont(new Font("SansSerif", Font.BOLD, 10));
        moduleTitle.setForeground(new Color(151, 184, 181));
        moduleTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(moduleTitle);
        sidebar.add(Box.createVerticalStrut(15));

        if (role.equals("Doctor")) {

            addNavigationItem(sidebar, "Patients", false);
            addNavigationItem(sidebar, "Medical Records", false);
            addNavigationItem(sidebar, "Appointments", false);

        } else if (role.equals("Nurse")) {

            addNavigationItem(sidebar, "Patients", false);
            addNavigationItem(sidebar, "Appointments", false);

        } else if (role.equals("PharmacyStaff")) {

            addNavigationItem(sidebar, "Prescriptions", false);
            addNavigationItem(sidebar, "Billing", false);
        }

        sidebar.add(Box.createVerticalGlue());

        JLabel portalLabel = new JLabel("CAREPOINT STAFF PORTAL");
        portalLabel.setFont(new Font("SansSerif", Font.PLAIN, 9));
        portalLabel.setForeground(new Color(151, 184, 181));
        portalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(portalLabel);
        sidebar.add(Box.createVerticalStrut(15));

        JButton logoutButton = new JButton("Sign Out");
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        logoutButton.setForeground(WHITE);
        logoutButton.setBackground(new Color(37, 77, 83));
        logoutButton.setOpaque(true);
        logoutButton.setContentAreaFilled(true);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setBorder(new EmptyBorder(12, 15, 12, 15));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoutButton.setMaximumSize(new Dimension(
                Integer.MAX_VALUE, 44
        ));

        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        sidebar.add(logoutButton);

        return sidebar;
    }

    private void addNavigationItem(
            JPanel sidebar, String text, boolean selected) {

        JPanel item = new JPanel(new BorderLayout());
        item.setMaximumSize(new Dimension(
                Integer.MAX_VALUE, 43
        ));
        item.setPreferredSize(new Dimension(205, 43));
        item.setBackground(
                selected ? new Color(35, 91, 91) : DARK
        );
        item.setBorder(new EmptyBorder(0, 13, 0, 10));
        item.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(text);
        label.setFont(new Font(
                "SansSerif",
                selected ? Font.BOLD : Font.PLAIN,
                13
        ));
        label.setForeground(WHITE);

        item.add(label, BorderLayout.CENTER);

        sidebar.add(item);
        sidebar.add(Box.createVerticalStrut(5));
    }

    private JPanel createMainContent() {

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(BACKGROUND);
        content.setBorder(new EmptyBorder(38, 42, 35, 42));

        JPanel topContent = new JPanel();
        topContent.setOpaque(false);
        topContent.setLayout(new BoxLayout(
                topContent, BoxLayout.Y_AXIS
        ));

        JLabel pageTitle = new JLabel("Dashboard");
        pageTitle.setFont(new Font("SansSerif", Font.BOLD, 29));
        pageTitle.setForeground(TEXT);
        pageTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel welcome = new JLabel("Welcome back, " + name);
        welcome.setFont(new Font("SansSerif", Font.PLAIN, 15));
        welcome.setForeground(MUTED);
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        topContent.add(pageTitle);
        topContent.add(Box.createVerticalStrut(8));
        topContent.add(welcome);
        topContent.add(Box.createVerticalStrut(30));

        JPanel statusCard = createWelcomeCard();

        topContent.add(statusCard);
        topContent.add(Box.createVerticalStrut(35));

        JLabel sectionHeading = new JLabel("Your workspace");
        sectionHeading.setFont(new Font("SansSerif", Font.BOLD, 19));
        sectionHeading.setForeground(TEXT);
        sectionHeading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sectionDescription = new JLabel(
                "Services available for your staff role."
        );
        sectionDescription.setFont(new Font("SansSerif", Font.PLAIN, 13));
        sectionDescription.setForeground(MUTED);
        sectionDescription.setAlignmentX(Component.LEFT_ALIGNMENT);

        topContent.add(sectionHeading);
        topContent.add(Box.createVerticalStrut(7));
        topContent.add(sectionDescription);
        topContent.add(Box.createVerticalStrut(20));

        JPanel moduleGrid = createModuleGrid();

        topContent.add(moduleGrid);

        content.add(topContent, BorderLayout.NORTH);

        JLabel footer = new JLabel(
                "CarePoint Hospital Management System"
        );
        footer.setFont(new Font("SansSerif", Font.PLAIN, 11));
        footer.setForeground(MUTED);

        content.add(footer, BorderLayout.SOUTH);

        return content;
    }

    private JPanel createWelcomeCard() {

        JPanel card = new JPanel(new BorderLayout(20, 0));
        card.setBackground(WHITE);
        card.setBorder(new EmptyBorder(25, 28, 25, 28));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
        card.setPreferredSize(new Dimension(700, 140));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(
                textPanel, BoxLayout.Y_AXIS
        ));

        JLabel title = new JLabel("You're successfully signed in");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(DARK);

        JLabel description = new JLabel(
                "Your CarePoint workspace is ready."
        );
        description.setFont(new Font("SansSerif", Font.PLAIN, 13));
        description.setForeground(MUTED);

        JLabel roleLabel = new JLabel("SIGNED IN AS: " + role.toUpperCase());
        roleLabel.setFont(new Font("SansSerif", Font.BOLD, 10));
        roleLabel.setForeground(TEAL);

        textPanel.add(title);
        textPanel.add(Box.createVerticalStrut(9));
        textPanel.add(description);
        textPanel.add(Box.createVerticalStrut(13));
        textPanel.add(roleLabel);

        JLabel icon = new JLabel("\u2713", SwingConstants.CENTER);
        icon.setOpaque(true);
        icon.setBackground(new Color(225, 242, 236));
        icon.setForeground(TEAL);
        icon.setFont(new Font("SansSerif", Font.BOLD, 30));
        icon.setPreferredSize(new Dimension(58, 58));

        card.add(textPanel, BorderLayout.CENTER);
        card.add(icon, BorderLayout.EAST);

        return card;
    }

    private JPanel createModuleGrid() {

        JPanel grid = new JPanel(new GridLayout(0, 2, 16, 16));
        grid.setOpaque(false);
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (role.equals("Doctor")) {

            grid.add(createModuleCard(
                    "Patient Directory",
                    "Access patient information and profiles.",
                    "01"
            ));

            grid.add(createModuleCard(
                    "Medical Records",
                    "Review and manage clinical histories.",
                    "02"
            ));

            grid.add(createModuleCard(
                    "Appointments",
                    "Review scheduled patient appointments.",
                    "03"
            ));

        } else if (role.equals("Nurse")) {

            grid.add(createModuleCard(
                    "Patient Directory",
                    "Access patient information and profiles.",
                    "01"
            ));

            grid.add(createModuleCard(
                    "Appointments",
                    "Review scheduled patient appointments.",
                    "02"
            ));

        } else if (role.equals("PharmacyStaff")) {

            grid.add(createModuleCard(
                    "Prescriptions",
                    "Review and manage prescription information.",
                    "01"
            ));

            grid.add(createModuleCard(
                    "Billing",
                    "Access patient billing information.",
                    "02"
            ));
        }

        return grid;
    }

    private JPanel createModuleCard(
            String title, String description, String number) {

        JPanel card = new JPanel();
        card.setBackground(WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(22, 23, 22, 23));
        card.setPreferredSize(new Dimension(280, 145));

        JLabel numberLabel = new JLabel("MODULE " + number);
        numberLabel.setFont(new Font("SansSerif", Font.BOLD, 10));
        numberLabel.setForeground(TEAL);
        numberLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setForeground(TEXT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descriptionLabel = new JLabel(
                "<html>" + description + "</html>"
        );
        descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        descriptionLabel.setForeground(MUTED);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(numberLabel);
        card.add(Box.createVerticalStrut(13));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(descriptionLabel);

        return card;
    }
}