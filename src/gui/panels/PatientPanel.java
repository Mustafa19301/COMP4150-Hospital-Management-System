package gui.panels;

import dao.PatientDAO;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PatientPanel extends JPanel {

    private static final Color DARK = new Color(16, 45, 56);
    private static final Color TEAL = new Color(23, 107, 104);
    private static final Color BACKGROUND = new Color(243, 247, 246);
    private static final Color TEXT = new Color(35, 58, 62);
    private static final Color MUTED = new Color(112, 132, 134);
    private static final Color WHITE = Color.WHITE;
    private static final Color BORDER = new Color(225, 233, 231);

    private final PatientDAO patientDAO;

    private JTable patientTable;
    private DefaultTableModel tableModel;

    private JTextField searchField;
    private JLabel patientCountLabel;

    private JLabel totalPatientsValue;
    private JLabel withRecordsValue;
    private JLabel withoutRecordsValue;

    public PatientPanel() {

        patientDAO = new PatientDAO();

        setLayout(new BorderLayout());
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(30, 35, 30, 35));

        add(createHeader(), BorderLayout.NORTH);
        add(createPatientContent(), BorderLayout.CENTER);

        loadPatients("");
    }

    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BACKGROUND);
        header.setBorder(new EmptyBorder(0, 0, 23, 0));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(BACKGROUND);

        JLabel title = new JLabel("Patient Directory");
        title.setFont(new Font("SansSerif", Font.BOLD, 27));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "Manage patient information and registration records."
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(6));
        titlePanel.add(subtitle);

        JButton addButton = createButton(
                "+ Add Patient", TEAL, WHITE
        );

        addButton.setPreferredSize(new Dimension(145, 43));

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPatientDialog(false, null);
            }
        });

        header.add(titlePanel, BorderLayout.WEST);
        header.add(addButton, BorderLayout.EAST);

        return header;
    }

    private JPanel createPatientContent() {

        JPanel content = new JPanel(new BorderLayout(0, 15));
        content.setBackground(BACKGROUND);

        JPanel topSection = new JPanel();
        topSection.setLayout(new BoxLayout(
                topSection, BoxLayout.Y_AXIS
        ));
        topSection.setBackground(BACKGROUND);

        topSection.add(createStatisticsPanel());
        topSection.add(Box.createVerticalStrut(15));
        topSection.add(createToolbar());

        String[] columns = {
                "Patient ID",
                "Full Name",
                "Date of Birth",
                "Phone",
                "Address"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        patientTable = new JTable(tableModel);

        patientTable.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );
        patientTable.setForeground(TEXT);
        patientTable.setBackground(WHITE);
        patientTable.setRowHeight(44);

        patientTable.setSelectionBackground(
                new Color(220, 239, 235)
        );
        patientTable.setSelectionForeground(TEXT);

        patientTable.setShowVerticalLines(false);
        patientTable.setShowHorizontalLines(true);
        patientTable.setGridColor(
                new Color(238, 243, 242)
        );

        patientTable.setIntercellSpacing(
                new Dimension(0, 1)
        );

        patientTable.setFillsViewportHeight(true);
        patientTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        patientTable.setRowMargin(0);

        patientTable.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 12)
        );

        patientTable.getTableHeader().setBackground(
                new Color(237, 244, 242)
        );

        patientTable.getTableHeader().setForeground(TEXT);

        patientTable.getTableHeader().setPreferredSize(
                new Dimension(0, 45)
        );

        patientTable.getTableHeader().setReorderingAllowed(false);
        patientTable.getTableHeader().setBorder(
                BorderFactory.createMatteBorder(
                        0, 0, 1, 0, BORDER
                )
        );

        patientTable.getColumnModel().getColumn(0)
                .setPreferredWidth(75);

        patientTable.getColumnModel().getColumn(1)
                .setPreferredWidth(175);

        patientTable.getColumnModel().getColumn(2)
                .setPreferredWidth(120);

        patientTable.getColumnModel().getColumn(3)
                .setPreferredWidth(125);

        patientTable.getColumnModel().getColumn(4)
                .setPreferredWidth(190);

        DefaultTableCellRenderer cellRenderer =
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column
                    ) {

                        Component component =
                                super.getTableCellRendererComponent(
                                        table,
                                        value,
                                        isSelected,
                                        hasFocus,
                                        row,
                                        column
                                );

                        if (isSelected) {
                            component.setBackground(
                                    new Color(220, 239, 235)
                            );
                            component.setForeground(TEXT);
                        } else {
                            component.setBackground(
                                    row % 2 == 0
                                            ? WHITE
                                            : new Color(249, 251, 250)
                            );
                            component.setForeground(TEXT);
                        }

                        setBorder(new EmptyBorder(0, 12, 0, 8));

                        return component;
                    }
                };

        for (int i = 0; i < patientTable.getColumnCount(); i++) {
            patientTable.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(cellRenderer);
        }

        patientTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editSelectedPatient();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(patientTable);

        scrollPane.setBorder(
                new LineBorder(BORDER, 1)
        );

        scrollPane.getViewport().setBackground(WHITE);

        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(WHITE);

        tableContainer.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER, 1),
                        new EmptyBorder(12, 12, 12, 12)
                )
        );

        JLabel tableTitle = new JLabel("Registered Patients");

        tableTitle.setFont(
                new Font("SansSerif", Font.BOLD, 15)
        );

        tableTitle.setForeground(TEXT);

        tableTitle.setBorder(
                new EmptyBorder(5, 5, 13, 5)
        );

        tableContainer.add(tableTitle, BorderLayout.NORTH);
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        JPanel actions = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 12, 0)
        );

        actions.setBackground(BACKGROUND);

        JButton editButton = createButton(
                "Edit Patient", DARK, WHITE
        );

        JButton deleteButton = createButton(
                "Delete Patient",
                new Color(173, 65, 65),
                WHITE
        );

        editButton.setPreferredSize(new Dimension(130, 40));
        deleteButton.setPreferredSize(new Dimension(135, 40));

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSelectedPatient();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedPatient();
            }
        });

        actions.add(editButton);
        actions.add(deleteButton);

        content.add(topSection, BorderLayout.NORTH);
        content.add(tableContainer, BorderLayout.CENTER);
        content.add(actions, BorderLayout.SOUTH);

        return content;
    }

    private JPanel createStatisticsPanel() {

        JPanel statistics = new JPanel(
                new GridLayout(1, 3, 14, 0)
        );

        statistics.setBackground(BACKGROUND);
        statistics.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 100)
        );

        statistics.setPreferredSize(
                new Dimension(0, 100)
        );

        totalPatientsValue = new JLabel("0");
        withRecordsValue = new JLabel("0");
        withoutRecordsValue = new JLabel("0");

        statistics.add(createStatCard(
                "TOTAL PATIENTS",
                totalPatientsValue,
                "Registered in the system",
                TEAL
        ));

        statistics.add(createStatCard(
                "WITH MEDICAL RECORDS",
                withRecordsValue,
                "Patients with clinical history",
                new Color(54, 132, 111)
        ));

        statistics.add(createStatCard(
                "WITHOUT MEDICAL RECORDS",
                withoutRecordsValue,
                "Patients without clinical history",
                new Color(190, 139, 65)
        ));

        return statistics;
    }

    private JPanel createStatCard(
            String title,
            JLabel valueLabel,
            String description,
            Color accent
    ) {

        JPanel card = new JPanel(new BorderLayout(12, 0));
        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER, 1),
                        new EmptyBorder(13, 15, 13, 15)
                )
        );

        JPanel accentBar = new JPanel();
        accentBar.setBackground(accent);
        accentBar.setPreferredSize(new Dimension(4, 0));

        JPanel details = new JPanel();
        details.setOpaque(false);

        details.setLayout(new BoxLayout(
                details, BoxLayout.Y_AXIS
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(
                new Font("SansSerif", Font.BOLD, 10)
        );
        titleLabel.setForeground(MUTED);

        valueLabel.setFont(
                new Font("SansSerif", Font.BOLD, 25)
        );
        valueLabel.setForeground(TEXT);

        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 10)
        );
        descriptionLabel.setForeground(MUTED);

        details.add(titleLabel);
        details.add(Box.createVerticalStrut(3));
        details.add(valueLabel);
        details.add(Box.createVerticalStrut(2));
        details.add(descriptionLabel);

        card.add(accentBar, BorderLayout.WEST);
        card.add(details, BorderLayout.CENTER);

        return card;
    }

    private JPanel createToolbar() {

        JPanel toolbar = new JPanel(new BorderLayout(15, 0));
        toolbar.setBackground(WHITE);

        toolbar.setBorder(
                new CompoundBorder(
                        new LineBorder(BORDER, 1),
                        new EmptyBorder(13, 16, 13, 16)
                )
        );

        JPanel searchPanel = new JPanel(
                new BorderLayout(10, 0)
        );

        searchPanel.setBackground(WHITE);

        searchField = new JTextField();
        searchField.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );

        searchField.setForeground(TEXT);

        searchField.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                new Color(220, 229, 227), 1, true
                        ),
                        new EmptyBorder(9, 12, 9, 12)
                )
        );

        searchField.setPreferredSize(
                new Dimension(250, 40)
        );

        searchField.setToolTipText(
                "Enter a patient name to search"
        );

        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPatients();
            }
        });

        JButton searchButton = createButton(
                "Search", TEAL, WHITE
        );

        searchButton.setPreferredSize(
                new Dimension(90, 40)
        );

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPatients();
            }
        });

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        patientCountLabel = new JLabel("Showing 0 patients");

        patientCountLabel.setFont(
                new Font("SansSerif", Font.BOLD, 12)
        );

        patientCountLabel.setForeground(TEAL);

        JButton refreshButton = createButton(
                "Refresh", DARK, WHITE
        );

        refreshButton.setPreferredSize(
                new Dimension(95, 40)
        );

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchField.setText("");
                loadPatients("");
            }
        });

        JPanel rightToolbar = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 14, 0)
        );

        rightToolbar.setBackground(WHITE);
        rightToolbar.add(patientCountLabel);
        rightToolbar.add(refreshButton);

        toolbar.add(searchPanel, BorderLayout.CENTER);
        toolbar.add(rightToolbar, BorderLayout.EAST);

        return toolbar;
    }

    private void loadPatients(String search) {

        tableModel.setRowCount(0);

        try {

            List<Map<String, Object>> patients;

            if (search == null || search.trim().isEmpty()) {
                patients = patientDAO.getAllPatients();
            } else {
                patients = patientDAO.searchPatientsByName(
                        search.trim()
                );
            }

            for (Map<String, Object> patient : patients) {

                tableModel.addRow(new Object[]{
                        getValue(patient, "patient_id"),
                        getValue(patient, "name"),
                        getValue(patient, "date_of_birth"),
                        getValue(patient, "phone"),
                        getValue(patient, "address")
                });
            }

            patientCountLabel.setText(
                    "Showing " + patients.size() + " patients"
            );

            updateStatistics();

        } catch (SQLException ex) {

            showError(
                    "Unable to load patients.\n" + ex.getMessage()
            );
        }
    }

    private void updateStatistics() throws SQLException {

        List<Map<String, Object>> allPatients =
                patientDAO.getAllPatients();

        List<Map<String, Object>> patientsWithoutRecords =
                patientDAO.getPatientsWithoutMedicalRecords();

        int total = allPatients.size();
        int withoutRecords = patientsWithoutRecords.size();
        int withRecords = total - withoutRecords;

        totalPatientsValue.setText(String.valueOf(total));
        withRecordsValue.setText(String.valueOf(withRecords));
        withoutRecordsValue.setText(String.valueOf(withoutRecords));
    }

    private void searchPatients() {

        loadPatients(searchField.getText());
    }

    private void showPatientDialog(
            boolean editing,
            Map<String, Object> existingPatient
    ) {

        JTextField nameField = new JTextField(22);
        JTextField dobField = new JTextField(22);
        JTextField phoneField = new JTextField(22);
        JTextField addressField = new JTextField(22);

        if (editing && existingPatient != null) {

            nameField.setText(
                    String.valueOf(getValue(existingPatient, "name"))
            );

            dobField.setText(
                    String.valueOf(
                            getValue(existingPatient, "date_of_birth")
                    )
            );

            phoneField.setText(
                    String.valueOf(getValue(existingPatient, "phone"))
            );

            addressField.setText(
                    String.valueOf(getValue(existingPatient, "address"))
            );
        }

        JPanel form = new JPanel(
                new GridLayout(4, 2, 12, 15)
        );

        form.setBackground(WHITE);
        form.setBorder(new EmptyBorder(15, 10, 15, 10));

        addFormField(form, "Full Name", nameField);
        addFormField(form, "Date of Birth (YYYY-MM-DD)", dobField);
        addFormField(form, "Phone Number", phoneField);
        addFormField(form, "Address", addressField);

        String title = editing
                ? "Edit Patient Information"
                : "Register New Patient";

        int result = JOptionPane.showConfirmDialog(
                this,
                form,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String name = nameField.getText().trim();
        String dob = dobField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();

        if (name.isEmpty()
                || dob.isEmpty()
                || phone.isEmpty()
                || address.isEmpty()) {

            showError("Please fill in all patient fields.");
            return;
        }

        try {

            LocalDate dateOfBirth = LocalDate.parse(dob);

            if (dateOfBirth.isAfter(LocalDate.now())) {
                showError("Date of birth cannot be in the future.");
                return;
            }

            if (editing) {

                int patientId = Integer.parseInt(
                        String.valueOf(
                                getValue(existingPatient, "patient_id")
                        )
                );

                boolean updated = patientDAO.updatePatient(
                        patientId,
                        name,
                        dateOfBirth,
                        phone,
                        address
                );

                if (updated) {
                    showSuccess(
                            "Patient information updated successfully."
                    );
                } else {
                    showError("No patient information was updated.");
                }

            } else {

                int newPatientId = patientDAO.addPatient(
                        name,
                        dateOfBirth,
                        phone,
                        address
                );

                showSuccess(
                        "Patient registered successfully.\nPatient ID: "
                                + newPatientId
                );
            }

            loadPatients(searchField.getText());

        } catch (java.time.format.DateTimeParseException ex) {

            showError(
                    "Invalid date format.\nPlease use YYYY-MM-DD."
            );

        } catch (SQLException ex) {

            showError(
                    "Unable to save patient information.\n"
                            + ex.getMessage()
            );
        }
    }

    private void addFormField(
            JPanel form,
            String label,
            JTextField field
    ) {

        JLabel fieldLabel = new JLabel(label);

        fieldLabel.setFont(
                new Font("SansSerif", Font.BOLD, 12)
        );

        fieldLabel.setForeground(TEXT);

        field.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );

        form.add(fieldLabel);
        form.add(field);
    }

    private void editSelectedPatient() {

        int selectedRow = patientTable.getSelectedRow();

        if (selectedRow == -1) {
            showError("Please select a patient to edit.");
            return;
        }

        int patientId = Integer.parseInt(
                String.valueOf(
                        tableModel.getValueAt(selectedRow, 0)
                )
        );

        try {

            Map<String, Object> patient =
                    patientDAO.getPatientById(patientId);

            if (patient == null) {
                showError("Patient could not be found.");
                loadPatients(searchField.getText());
                return;
            }

            showPatientDialog(true, patient);

        } catch (SQLException ex) {

            showError(
                    "Unable to retrieve patient information.\n"
                            + ex.getMessage()
            );
        }
    }

    private void deleteSelectedPatient() {

        int selectedRow = patientTable.getSelectedRow();

        if (selectedRow == -1) {
            showError("Please select a patient to delete.");
            return;
        }

        int patientId = Integer.parseInt(
                String.valueOf(
                        tableModel.getValueAt(selectedRow, 0)
                )
        );

        String patientName = String.valueOf(
                tableModel.getValueAt(selectedRow, 1)
        );

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete "
                        + patientName + "?\n\n"
                        + "Patient ID: " + patientId,
                "Confirm Patient Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            boolean deleted = patientDAO.deletePatient(patientId);

            if (deleted) {
                showSuccess("Patient deleted successfully.");
            } else {
                showError("Patient could not be deleted.");
            }

            loadPatients(searchField.getText());

        } catch (SQLException ex) {

            showError(
                    "Unable to delete patient.\n"
                            + ex.getMessage()
            );
        }
    }

    private Object getValue(
            Map<String, Object> row,
            String columnName
    ) {

        for (Map.Entry<String, Object> entry : row.entrySet()) {

            if (entry.getKey().equalsIgnoreCase(columnName)) {
                return entry.getValue();
            }
        }

        return null;
    }

    private JButton createButton(
            String text,
            Color background,
            Color foreground
    ) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("SansSerif", Font.BOLD, 12)
        );

        button.setForeground(foreground);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        button.setBorder(
                new EmptyBorder(11, 18, 11, 18)
        );

        Color originalColor = background;

        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(
                        originalColor.brighter()
                );
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });

        return button;
    }

    private void showError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "CarePoint",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void showSuccess(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "CarePoint",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}