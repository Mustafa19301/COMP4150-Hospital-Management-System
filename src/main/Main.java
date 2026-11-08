
package main;

import dao.PrescriptionDAO;
import dao.BillDAO;

import model.Prescription;
import model.Bill;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
        BillDAO billDAO = new BillDAO();

        try {
            System.out.println("\n===== PENDING PRESCRIPTIONS =====");

            List<Prescription> pendingPrescriptions =
                    prescriptionDAO.getPendingPrescriptions();

            if (pendingPrescriptions.isEmpty()) {
                System.out.println("No pending prescriptions found.");
            }

            for (Prescription p : pendingPrescriptions) {

                System.out.println(
                    "Prescription ID: " + p.getPrescription_id()
                    + " | Patient ID: " + p.getPatient_id()
                    + " | Medication ID: " + p.getMedication_id()
                    + " | Status: " + p.getStatus_id()
                );
            }


          
            //PATIENT PRESCRIPTIONS
            System.out.println("\n===== PATIENT 1 PRESCRIPTIONS =====");

            List<Prescription> patientPrescriptions =
                    prescriptionDAO.getPatientPrescriptions(1);

            if (patientPrescriptions.isEmpty()) {
                System.out.println("No prescriptions found for patient 1.");
            }

            for (Prescription p : patientPrescriptions) {

                System.out.println(
                    "Prescription ID: " + p.getPrescription_id()
                    + " | Medication ID: " + p.getMedication_id()
                    + " | Status: " + p.getStatus_id()
                );
            }


          
            //ALL UNPAID BILLS
            System.out.println("\n===== UNPAID BILLS =====");

            List<Bill> unpaidBills = billDAO.getUnpaidBills();

            if (unpaidBills.isEmpty()) {
                System.out.println("No unpaid bills found.");
            }

            for (Bill b : unpaidBills) {

                System.out.println(
                    "Bill ID: " + b.getBill_id()
                    + " | Patient ID: " + b.getPatient_id()
                    + " | Amount: " + b.getAmount()
                    + " | Status: " + b.getPayment_status()
                );
            }


        
            //BILLS FOR A PATIENT (PATIENT 1)
            System.out.println("\n===== PATIENT 1 BILLS =====");

            List<Bill> patientBills = billDAO.getPatientBills(1);

            if (patientBills.isEmpty()) {
                System.out.println("No bills found for patient 1.");
            }

            for (Bill b : patientBills) {

                System.out.println(
                    "Bill ID: " + b.getBill_id()
                    + " | Amount: " + b.getAmount()
                    + " | Status: " + b.getPayment_status()
                );
            }

        } catch (SQLException e) {

            System.out.println("\nA database error occurred:");
            e.printStackTrace();
        }
    }
}
