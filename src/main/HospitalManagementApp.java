package main;

import gui.LoginFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class HospitalManagementApp {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception exception) {
            System.out.println("default app");
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            }
        });
    }
}