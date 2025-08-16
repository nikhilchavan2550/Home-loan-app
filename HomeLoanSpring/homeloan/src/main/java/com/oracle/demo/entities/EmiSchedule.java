package com.oracle.demo.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "emischedule")
public class EmiSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "month_number", nullable = false)
    private Integer monthNumber;

    @Column(name = "emi_amount", nullable = false)
    private Double emiAmount;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "paid_flag", nullable = false)
    private Boolean paidFlag = false;
    
    @Column(name = "paid_date")
    private LocalDate paidDate;
    
    @Column(name = "amount_paid")
    private Double amountPaid = 0.0;
    
    public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "created_date", nullable = false)
    private LocalDate createdDate = LocalDate.now();

    public EmiSchedule(Long scheduleId, String accountNumber, Integer monthNumber, Double emiAmount, LocalDate dueDate,
			Boolean paidFlag, LocalDate paidDate,Double amountPaid,LocalDate createdDate) {
		super();
		this.scheduleId = scheduleId;
		this.accountNumber = accountNumber;
		this.monthNumber = monthNumber;
		this.emiAmount = emiAmount;
		this.dueDate = dueDate;
		this.paidFlag = paidFlag;
		this.paidDate = paidDate;
		this.amountPaid=amountPaid;
		this.createdDate=createdDate;
	}

	public Double getAmountPaid() {
		return amountPaid;
	}

	 public void setAmountPaid(Double amountPaid) {
	        if (amountPaid != null) {
	            this.amountPaid = Math.round(amountPaid * 100.0) / 100.0;
	        } else {
	            this.amountPaid = null;
	        }
	    }

	public LocalDate getPaidDate() {
		return paidDate;
	}
    
	public void setPaidDate(LocalDate paidDate) {
		this.paidDate = paidDate;
	}

	public EmiSchedule() {
		super();
		// TODO Auto-generated constructor stub
	}

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(Integer monthNumber) {
        this.monthNumber = monthNumber;
    }

    public Double getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(Double emiAmount) {
        if (emiAmount != null) {
            this.emiAmount = Math.round(emiAmount * 100.0) / 100.0;
        } else {
            this.emiAmount = null;
        }
    }

   

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getPaidFlag() {
        return paidFlag;
    }

    public void setPaidFlag(Boolean paidFlag) {
        this.paidFlag = paidFlag;
    }
}
