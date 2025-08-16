package com.oracle.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oracle.demo.entities.EmiSchedule;
import com.oracle.demo.repos.EmiScheduleRepository;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmiScheduleService {
    @Autowired
    private EmiScheduleRepository emiScheduleRepository;
    public void generateSchedule(String accountNumber, Double emiAmount, int durationInMonths) 
    {
    	LocalDate createdDate = LocalDate.now(); 
        LocalDate dueDate = LocalDate.now().plusMonths(1); 
        for (int i = 1; i <= durationInMonths; i++) 
        { EmiSchedule emi = new EmiSchedule();
        emi.setAccountNumber(accountNumber);
        emi.setMonthNumber(i);
        emi.setEmiAmount(emiAmount);
        emi.setDueDate(dueDate);
        emi.setPaidFlag(false);
        emi.setPaidDate(null); 
        emi.setAmountPaid(0.0);
        emi.setCreatedDate(createdDate); 
        emiScheduleRepository.save(emi);
        dueDate = dueDate.plusMonths(1);
        }
    }
    
    public List<EmiSchedule> getScheduleByAccountNumber(String accountNumber)
    {
        return emiScheduleRepository.findByAccountNumber(accountNumber);
    }
    
    public String payEmi(String accountNumber, Double amount) {
        Optional<EmiSchedule> optionalEmi = emiScheduleRepository
            .findFirstByAccountNumberAndPaidFlagFalseOrderByDueDateAsc(accountNumber);

        if (optionalEmi.isEmpty()) {
            return "All EMIs are already paid for this account.";
        }

        EmiSchedule emi = optionalEmi.get();
        LocalDate today = LocalDate.now();

        LocalDate createdDate = emi.getCreatedDate(); 
        LocalDate dueDate = emi.getDueDate();

        LocalDate allowedStart = dueDate.minusMonths(1);

        if (today.isBefore(allowedStart) || today.isAfter(dueDate)) {
            return "You can only pay this EMI between " + allowedStart + " and " + dueDate + ".";
        }

        Double amountPaid = emi.getAmountPaid();
        if (amountPaid == null) amountPaid = 0.0;

        double remainingAmount = emi.getEmiAmount() - amountPaid;

        if (amount <= 0) {
            return "Payment amount must be greater than 0.";
        }

        if (amount > remainingAmount) {
            return "Payment exceeds remaining EMI. You still owe ₹" + remainingAmount;
        }

        emi.setAmountPaid(amountPaid + amount);
        if (emi.getAmountPaid() >= emi.getEmiAmount()) {
            emi.setPaidFlag(true);
            emi.setPaidDate(today);
        }

        emiScheduleRepository.save(emi);

        return "Paid ₹" + amount + " for Month " + emi.getMonthNumber() +
               ". Remaining for this month: ₹" + (emi.getEmiAmount() - emi.getAmountPaid());
    }

    public List<EmiSchedule> getPaidEmis(String accountNumber) 
    {
        return emiScheduleRepository.findByAccountNumberAndPaidFlagTrueOrderByMonthNumberAsc(accountNumber);
    }
    public List<EmiSchedule> getUnpaidEmis(String accountNumber) 
    {
        return emiScheduleRepository.findByAccountNumberAndPaidFlagFalseOrderByMonthNumberAsc(accountNumber);
    }
    public void deleteScheduleByAccountNumber(String accountNumber) {
        emiScheduleRepository.deleteByAccountNumber(accountNumber);
    }

    public Map<String, Object> getEmiPaymentSummary(String accountNumber) {
        List<EmiSchedule> scheduleList = emiScheduleRepository.findByAccountNumber(accountNumber);

        double totalPaid = 0.0;
        double totalRemaining = 0.0;

        for (EmiSchedule emi : scheduleList) {
            double paid = emi.getAmountPaid() != null ? emi.getAmountPaid() : 0.0;
            double emiAmount = emi.getEmiAmount() != null ? emi.getEmiAmount() : 0.0;

            totalPaid += paid;
            totalRemaining += (emiAmount - paid);
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("accountNumber", accountNumber);
        response.put("totalPaid", totalPaid);
        response.put("totalRemaining", totalRemaining);

        return response; // Spring will serialize this to JSON automatically
    }



}
