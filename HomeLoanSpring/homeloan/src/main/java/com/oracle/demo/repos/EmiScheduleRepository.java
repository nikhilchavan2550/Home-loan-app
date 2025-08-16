package com.oracle.demo.repos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oracle.demo.entities.EmiSchedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmiScheduleRepository extends JpaRepository<EmiSchedule, Long> {
    List<EmiSchedule> findByAccountNumber(String accountNumber);
    
    Optional<EmiSchedule> findFirstByAccountNumberAndPaidFlagFalseOrderByDueDateAsc(String accountNumber);

	List<EmiSchedule> findByAccountNumberAndPaidFlagTrueOrderByMonthNumberAsc(String accountNumber);

	List<EmiSchedule> findByAccountNumberAndPaidFlagFalseOrderByMonthNumberAsc(String accountNumber);

	Optional<EmiSchedule> findTopByAccountNumberAndDueDateLessThanOrderByDueDateDesc(String accountNumber,
			LocalDate dueDate);
	
	void deleteByAccountNumber(String accountNumber);


}
