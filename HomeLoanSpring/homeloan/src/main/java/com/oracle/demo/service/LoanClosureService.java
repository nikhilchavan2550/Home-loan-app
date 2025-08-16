package com.oracle.demo.service;

import com.oracle.demo.entities.EmiSchedule;
import com.oracle.demo.repos.EmiScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanClosureService {

    @Autowired
    private EmiScheduleRepository emiRepo;

    private double getAnnualInterestRate(String accountNumber) {
        return 8.5; // Example fixed rate
    }

    // 🎯 PART-PREPAYMENT
    public String prepayLoan(String accountNumber, double prepayAmount) {
        List<EmiSchedule> allEmis = emiRepo.findByAccountNumber(accountNumber);

        List<EmiSchedule> paidEmis = allEmis.stream()
            .filter(EmiSchedule::getPaidFlag)
            .toList();

        List<EmiSchedule> unpaidEmis = allEmis.stream()
            .filter(e -> !e.getPaidFlag())
            .toList();

        if (paidEmis.size() < 6) {
            return "Prepayment allowed only after 6 EMIs.";
        }

        double monthlyEMI = unpaidEmis.get(0).getEmiAmount();
        if (prepayAmount < 10000 && prepayAmount < monthlyEMI) {
            return "Minimum prepayment is ₹10,000 or 1 EMI.";
        }

        double remainingPrincipal = unpaidEmis.stream()
            .mapToDouble(EmiSchedule::getEmiAmount)
            .sum();

        double newPrincipal = remainingPrincipal - prepayAmount;
        if (newPrincipal <= 0) {
            return "Prepayment exceeds remaining balance. Please use foreclosure.";
        }

        emiRepo.deleteAll(unpaidEmis);

        double annualRate = getAnnualInterestRate(accountNumber);
        double monthlyRate = annualRate / 12 / 100;

        int newTenure = calculateTenureFromEMI(newPrincipal, monthlyRate, monthlyEMI);
        LocalDate startDate = LocalDate.now().plusMonths(1);

        for (int i = 1; i <= newTenure; i++) {
            EmiSchedule emi = new EmiSchedule();
            emi.setAccountNumber(accountNumber);
            emi.setMonthNumber(i);
            emi.setEmiAmount(monthlyEMI);
            emi.setDueDate(startDate.plusMonths(i - 1));
            emi.setPaidFlag(false);
            emiRepo.save(emi);
        }

        return "Prepayment successful. New schedule generated with reduced tenure.";
    }

    private int calculateTenureFromEMI(double principal, double monthlyRate, double emi) {
        double numerator = -Math.log(1 - (monthlyRate * principal / emi));
        double denominator = Math.log(1 + monthlyRate);
        return (int) Math.ceil(numerator / denominator);
    }

    // 🎯 FULL FORECLOSURE
    public String forecloseLoan(String accountNumber) {
        List<EmiSchedule> unpaidEmis = emiRepo.findByAccountNumberAndPaidFlagFalseOrderByMonthNumberAsc(accountNumber);
        
        System.out.println("Herel;mkjhgufufulfliygfiu.g.jhljpjpoipo;k;lk;l;k;k;j;hl;hlj"+ unpaidEmis.size());
        if (unpaidEmis.isEmpty()) {
            return "ℹ️ All EMIs already paid. Nothing to foreclose.";
        }

        double totalDue = unpaidEmis.stream().mapToDouble(EmiSchedule::getEmiAmount).sum();
        double foreclosureCharges = totalDue * 0.02;  // 2% penalty
        double totalToPay = totalDue + foreclosureCharges;

        // Mark all unpaid EMIs as paid
        for (EmiSchedule emi : unpaidEmis) {
            emi.setPaidFlag(true);
            emi.setAmountPaid(emi.getEmiAmount());
            emi.setPaidDate(LocalDate.now());
        }
        emiRepo.saveAll(unpaidEmis);

        return "✅ Loan foreclosed successfully.\n"
                + "Paid EMIs: " + unpaidEmis.size() + "\n"
                + "EMI Total: ₹" + totalDue + "\n"
                + "Foreclosure Charges (2%): ₹" + foreclosureCharges + "\n"
                + "Total Paid: ₹" + totalToPay;
    }
}
