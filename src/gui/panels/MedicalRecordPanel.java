package gui.panels;

import dao.MedicalRecordDAO;
import dao.PatientDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MedicalRecordPanel extends JPanel {

    private static final Color DARK = new Color(16, 45, 56);
    private static final Color TEAL = new Color(23, 107, 104);
    private static final Color BACKGROUND = new Color(243, 247, 246);
    private static final Color TEXT = new Color(35, 58, 62);
    private static final Color MUTED = new Color(112, 132, 134);
    private static final Color WHITE = Color.WHITE;
    private static final Color BORDER = new Color(225, 233, 231);

    private final int currentDoctorId;
    private final String currentDoctorName;

    private final PatientDAO patientDAO = new PatientDAO();
    private final MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();

    private JComboBox<PatientOption> patientComboBox;

    private JTable recordsTable;
    private DefaultTableModel tableModel;

    private JTextArea detailsArea;

    private JLabel totalRecordsLabel;
    private JLabel selectedPatientLabel;
    private JLabel latestRecordLabel;

    private JButton editButton;
    private JButton deleteButton;

    private List<Map<String, Object>> currentRecords;

    public MedicalRecordPanel(int doctorId, String doctorName) {

        this.currentDoctorId = doctorId;
        this.currentDoctorName = doctorName;

        setLayout(new BorderLayout(0, 18));
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(25, 28, 25, 28));

        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);

        loadPatients();
    }

    private JPanel createHeader() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Medical Records");
        title.setFont(new Font("SansSerif", Font.BOLD, 27));
        title.setForeground(DARK);

        JLabel subtitle = new JLabel(
                "View and manage patient medical histories."
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);

        textPanel.add(title);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(subtitle);

        panel.add(textPanel, BorderLayout.WEST);

        JLabel doctorLabel = new JLabel(
                "Doctor: " + currentDoctorName
        );
        doctorLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        doctorLabel.setForeground(TEAL);

        panel.add(doctorLabel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createMainContent() {

        JPanel panel = new JPanel(new BorderLayout(0, 16));
        panel.setOpaque(false);

        panel.add(createStatisticsPanel(), BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 14));
        center.setOpaque(false);

        center.add(createToolbar(), BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                createTablePanel(),
                createDetailsPanel()
        );

        splitPane.setResizeWeight(0.68);
        splitPane.setDividerSize(7);
        splitPane.setBorder(null);
        splitPane.setBackground(BACKGROUND);

        center.add(splitPane, BorderLayout.CENTER);

        panel.add(center, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatisticsPanel() {

        JPanel panel = new JPanel(new GridLayout(1, 3, 14, 0));
        panel.setOpaque(false);

        totalRecordsLabel = new JLabel("0");
        selectedPatientLabel = new JLabel("None");
        latestRecordLabel = new JLabel("None");

        panel.add(createStatCard(
                "Records for Selected Patient",
                totalRecordsLabel
        ));

        panel.add(createStatCard(
                "Selected Patient",
                selectedPatientLabel
        ));

        panel.add(createStatCard(
                "Latest Record",
                latestRecordLabel
        ));

        return panel;
    }

    private JPanel createStatCard(String title, JLabel valueLabel) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(15, 17, 15, 17)
        ));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("SansSerif", Font.PLAIN, 12));
        heading.setForeground(MUTED);

        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 21));
        valueLabel.setForeground(DARK);

        card.add(heading);
        card.add(Box.createVerticalStrut(9));
        card.add(valueLabel);

        return card;
    }

    private JPanel createToolbar() {

        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(12, 14, 12, 14)
        ));

        JPanel left = new JPanel(new FlowLayout(
                FlowLayout.LEFT, 9, 0
        ));
        left.setOpaque(false);

        JLabel patientLabel = new JLabel("Patient:");
        patientLabel.setForeground(TEXT);
        patientLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

        patientComboBox = new JComboBox<>();
        patientComboBox.setPreferredSize(new Dimension(230, 34));
        patientComboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));

        patientComboBox.addActionListener(e -> loadRecords());

        left.add(patientLabel);
        left.add(patientComboBox);

        JPanel right = new JPanel(new FlowLayout(
                FlowLayout.RIGHT, 8, 0
        ));
        right.setOpaque(false);

        JButton addButton = createButton("Add Record", TEAL);
        editButton = createButton("Edit", DARK);
        deleteButton = createButton("Delete", new Color(180, 65, 65));
        JButton refreshButton = createButton("Refresh", new Color(85, 105, 108));

        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        addButton.addActionListener(e -> addRecord());
        editButton.addActionListener(e -> editRecord());
        deleteButton.addActionListener(e -> deleteRecord());
        refreshButton.addActionListener(e -> refreshData());

        right.add(addButton);
        right.add(editButton);
        right.add(deleteButton);
        right.add(refreshButton);

        panel.add(left, BorderLayout.WEST);
        panel.add(right, BorderLayout.EAST);

        return panel;
    }

    private JPanel createTablePanel() {

        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(14, 15, 14, 15)
        ));

        JLabel heading = new JLabel("Medical History");
        heading.setFont(new Font("SansSerif", Font.BOLD, 16));
        heading.setForeground(DARK);

        String[] columns = {
                "Record ID",
                "Date",
                "Doctor",
                "Specialization",
                "Diagnosis",
                "Treatment"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        recordsTable = new JTable(tableModel);
        recordsTable.setRowHeight(34);
        recordsTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        recordsTable.setForeground(TEXT);
        recordsTable.setSelectionBackground(new Color(218, 239, 235));
        recordsTable.setSelectionForeground(DARK);
        recordsTable.setGridColor(BORDER);
        recordsTable.setShowVerticalLines(false);
        recordsTable.setFillsViewportHeight(true);

        recordsTable.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 12)
        );
        recordsTable.getTableHeader().setForeground(DARK);
        recordsTable.getTableHeader().setBackground(
                new Color(239, 245, 243)
        );
        recordsTable.getTableHeader().setPreferredSize(
                new Dimension(0, 36)
        );

        recordsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateSelectedRecord();
            }
        });

        JScrollPane scrollPane = new JScrollPane(recordsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));

        panel.add(heading, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createDetailsPanel() {

        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(14, 15, 14, 15)
        ));

        JLabel heading = new JLabel("Record Details");
        heading.setFont(new Font("SansSerif", Font.BOLD, 16));
        heading.setForeground(DARK);

        detailsArea = new JTextArea(
                "Select a medical record to view its details."
        );

        detailsArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        detailsArea.setForeground(TEXT);
        detailsArea.setBackground(WHITE);
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        detailsArea.setBorder(new EmptyBorder(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(detailsArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));

        panel.add(heading, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void loadPatients() {

        try {

            patientComboBox.removeAllItems();

            List<Map<String, Object>> patients =
                    patientDAO.getAllPatients();

            for (Map<String, Object> patient : patients) {

                int patientId = ((Number)
                        patient.get("patient_id")).intValue();

                String patientName =
                        String.valueOf(patient.get("name"));

                patientComboBox.addItem(
                        new PatientOption(patientId, patientName)
                );
            }

            if (patientComboBox.getItemCount() > 0) {
                patientComboBox.setSelectedIndex(0);
                loadRecords();
            } else {
                clearRecords();
            }

        } catch (Exception exception) {

            showError("Unable to load patients.", exception);
        }
    }

    private void loadRecords() {

        PatientOption selectedPatient =
                (PatientOption) patientComboBox.getSelectedItem();

        if (selectedPatient == null) {
            clearRecords();
            return;
        }

        try {

            currentRecords = medicalRecordDAO.getPatientMedicalHistory(
                    selectedPatient.id
            );

            tableModel.setRowCount(0);

            for (Map<String, Object> record : currentRecords) {

                tableModel.addRow(new Object[] {
                        record.get("record_id"),
                        record.get("record_date"),
                        record.get("doctor_name"),
                        record.get("specialization"),
                        record.get("diagnoses"),
                        record.get("treatments")
                });
            }

            totalRecordsLabel.setText(
                    String.valueOf(currentRecords.size())
            );

            selectedPatientLabel.setText(
                    selectedPatient.name
            );

            if (currentRecords.isEmpty()) {
                latestRecordLabel.setText("None");
            } else {
                latestRecordLabel.setText(
                        String.valueOf(
                                currentRecords.get(0).get("record_date")
                        )
                );
            }

            recordsTable.clearSelection();
            detailsArea.setText(
                    "Select a medical record to view its details."
            );

            editButton.setEnabled(false);
            deleteButton.setEnabled(false);

        } catch (Exception exception) {

            showError("Unable to load medical records.", exception);
        }
    }

    private void clearRecords() {

        if (tableModel != null) {
            tableModel.setRowCount(0);
        }

        if (totalRecordsLabel != null) {
            totalRecordsLabel.setText("0");
            selectedPatientLabel.setText("None");
            latestRecordLabel.setText("None");
        }

        if (detailsArea != null) {
            detailsArea.setText("No patient selected.");
        }

        if (editButton != null) {
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
        }
    }

    private Map<String, Object> getSelectedRecord() {

        int selectedRow = recordsTable.getSelectedRow();

        if (selectedRow < 0 || currentRecords == null) {
            return null;
        }

        int modelRow = recordsTable.convertRowIndexToModel(selectedRow);

        if (modelRow >= currentRecords.size()) {
            return null;
        }

        return currentRecords.get(modelRow);
    }

    private void updateSelectedRecord() {

        Map<String, Object> record = getSelectedRecord();

        if (record == null) {

            editButton.setEnabled(false);
            deleteButton.setEnabled(false);

            return;
        }

        int recordDoctorId = ((Number)
                record.get("doctor_id")).intValue();

        boolean isOwnRecord = currentDoctorId == recordDoctorId;

        editButton.setEnabled(isOwnRecord);
        deleteButton.setEnabled(isOwnRecord);

        StringBuilder details = new StringBuilder();

        details.append("Record ID: ")
                .append(record.get("record_id"))
                .append("\n\n");

        details.append("Patient: ")
                .append(record.get("patient_name"))
                .append("\n\n");

        details.append("Doctor: ")
                .append(record.get("doctor_name"))
                .append("\n");

        details.append("Doctor ID: ")
                .append(recordDoctorId)
                .append("\n");

        details.append("Specialization: ")
                .append(record.get("specialization"))
                .append("\n\n");

        details.append("Record Date: ")
                .append(record.get("record_date"))
                .append("\n\n");

        details.append("Diagnosis:\n")
                .append(record.get("diagnoses"))
                .append("\n\n");

        details.append("Treatment:\n")
                .append(record.get("treatments"))
                .append("\n\n");

        details.append("Medical Notes:\n")
                .append(record.get("medical_notes"));

        detailsArea.setText(details.toString());
        detailsArea.setCaretPosition(0);
    }

    private void addRecord() {

        showRecordDialog(null);
    }

    private void editRecord() {

        Map<String, Object> record = getSelectedRecord();

        if (record == null) {
            return;
        }

        int recordDoctorId = ((Number)
                record.get("doctor_id")).intValue();

        if (recordDoctorId != currentDoctorId) {
            JOptionPane.showMessageDialog(
                    this,
                    "You can only edit medical records that you created.",
                    "Permission Denied",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        showRecordDialog(record);
    }

    private void showRecordDialog(Map<String, Object> existingRecord) {

        boolean editing = existingRecord != null;

        JDialog dialog = new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(this),
                editing ? "Edit Medical Record" : "Add Medical Record",
                true
        );

        dialog.setSize(540, 570);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JPanel main = new JPanel(new BorderLayout(12, 12));
        main.setBackground(WHITE);
        main.setBorder(new EmptyBorder(22, 25, 20, 25));

        JLabel heading = new JLabel(
                editing ? "Update Medical Record" : "Create Medical Record"
        );
        heading.setFont(new Font("SansSerif", Font.BOLD, 21));
        heading.setForeground(DARK);

        main.add(heading, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 5, 7, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JComboBox<PatientOption> patientField = new JComboBox<>();

        for (int i = 0; i < patientComboBox.getItemCount(); i++) {
            patientField.addItem(patientComboBox.getItemAt(i));
        }

        JTextField dateField = new JTextField(
                editing
                        ? String.valueOf(existingRecord.get("record_date"))
                        : LocalDate.now().toString()
        );

        JTextArea diagnosisField = new JTextArea(3, 20);
        JTextArea treatmentField = new JTextArea(3, 20);
        JTextArea notesField = new JTextArea(3, 20);

        diagnosisField.setLineWrap(true);
        diagnosisField.setWrapStyleWord(true);

        treatmentField.setLineWrap(true);
        treatmentField.setWrapStyleWord(true);

        notesField.setLineWrap(true);
        notesField.setWrapStyleWord(true);

        if (editing) {

            int existingPatientId = ((Number)
                    existingRecord.get("patient_id")).intValue();

            for (int i = 0; i < patientField.getItemCount(); i++) {

                PatientOption option = patientField.getItemAt(i);

                if (option.id == existingPatientId) {
                    patientField.setSelectedIndex(i);
                    break;
                }
            }

            diagnosisField.setText(
                    String.valueOf(existingRecord.get("diagnoses"))
            );

            treatmentField.setText(
                    String.valueOf(existingRecord.get("treatments"))
            );

            notesField.setText(
                    String.valueOf(existingRecord.get("medical_notes"))
            );
        }

        int row = 0;

        addFormRow(form, gbc, row++, "Patient", patientField);
        addFormRow(form, gbc, row++, "Record Date (YYYY-MM-DD)", dateField);
        addFormRow(form, gbc, row++, "Diagnosis", new JScrollPane(diagnosisField));
        addFormRow(form, gbc, row++, "Treatment", new JScrollPane(treatmentField));
        addFormRow(form, gbc, row++, "Medical Notes", new JScrollPane(notesField));

        main.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 9, 0));
        buttons.setOpaque(false);

        JButton cancelButton = createButton("Cancel", new Color(100, 115, 118));
        JButton saveButton = createButton(
                editing ? "Save Changes" : "Add Record",
                TEAL
        );

        cancelButton.addActionListener(e -> dialog.dispose());

        saveButton.addActionListener(e -> {

            PatientOption selectedPatient =
                    (PatientOption) patientField.getSelectedItem();

            if (selectedPatient == null) {
                JOptionPane.showMessageDialog(
                        dialog,
                        "Please select a patient."
                );
                return;
            }

            String diagnoses = diagnosisField.getText().trim();
            String treatments = treatmentField.getText().trim();
            String notes = notesField.getText().trim();

            if (diagnoses.isEmpty() || treatments.isEmpty()) {
                JOptionPane.showMessageDialog(
                        dialog,
                        "Diagnosis and treatment are required."
                );
                return;
            }

            try {

                LocalDate recordDate = LocalDate.parse(
                        dateField.getText().trim()
                );

                boolean success;

                if (editing) {

                    int recordId = ((Number)
                            existingRecord.get("record_id")).intValue();

                    success = medicalRecordDAO.updateMedicalRecord(
                            recordId,
                            selectedPatient.id,
                            currentDoctorId,
                            recordDate,
                            diagnoses,
                            treatments,
                            notes
                    );

                } else {

                    int newRecordId = medicalRecordDAO.addMedicalRecord(
                            selectedPatient.id,
                            currentDoctorId,
                            recordDate,
                            diagnoses,
                            treatments,
                            notes
                    );

                    success = newRecordId > 0;
                }

                if (success) {

                    JOptionPane.showMessageDialog(
                            dialog,
                            editing
                                    ? "Medical record updated successfully."
                                    : "Medical record added successfully.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    dialog.dispose();

                    loadRecords();

                } else {

                    JOptionPane.showMessageDialog(
                            dialog,
                            "The medical record could not be saved.",
                            "Unable to Save",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (java.time.format.DateTimeParseException exception) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Please enter a valid date using YYYY-MM-DD.",
                        "Invalid Date",
                        JOptionPane.WARNING_MESSAGE
                );

            } catch (Exception exception) {

                showError("Unable to save medical record.", exception);
            }
        });

        buttons.add(cancelButton);
        buttons.add(saveButton);

        main.add(buttons, BorderLayout.SOUTH);

        dialog.setContentPane(main);
        dialog.setVisible(true);
    }

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            JComponent component
    ) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        fieldLabel.setForeground(TEXT);

        panel.add(fieldLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;

        component.setFont(new Font("SansSerif", Font.PLAIN, 13));

        panel.add(component, gbc);
    }

    private void deleteRecord() {

        Map<String, Object> record = getSelectedRecord();

        if (record == null) {
            return;
        }

        int recordDoctorId = ((Number)
                record.get("doctor_id")).intValue();

        if (recordDoctorId != currentDoctorId) {
            JOptionPane.showMessageDialog(
                    this,
                    "You can only delete medical records that you created.",
                    "Permission Denied",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this medical record?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            int recordId = ((Number)
                    record.get("record_id")).intValue();

            boolean deleted = medicalRecordDAO.deleteMedicalRecord(
                    recordId,
                    currentDoctorId
            );

            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Medical record deleted successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                loadRecords();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "The record could not be deleted. It may not belong to your account.",
                        "Unable to Delete",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (Exception exception) {

            showError("Unable to delete medical record.", exception);
        }
    }

    private void refreshData() {

        int selectedIndex = patientComboBox.getSelectedIndex();

        loadPatients();

        if (selectedIndex >= 0 &&
                selectedIndex < patientComboBox.getItemCount()) {

            patientComboBox.setSelectedIndex(selectedIndex);
        }

        loadRecords();
    }

    private JButton createButton(String text, Color color) {

        JButton button = new JButton(text);

        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(9, 13, 9, 13));

        return button;
    }

    private void showError(String message, Exception exception) {

        exception.printStackTrace();

        JOptionPane.showMessageDialog(
                this,
                message + "\n" + exception.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private static class PatientOption {

        private final int id;
        private final String name;

        private PatientOption(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name + " (ID: " + id + ")";
        }
    }
}