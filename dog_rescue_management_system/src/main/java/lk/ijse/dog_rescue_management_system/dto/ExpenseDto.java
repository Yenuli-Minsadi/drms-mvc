package lk.ijse.dog_rescue_management_system.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ExpenseDto {

    private String expenseId;
    private String dogId;
    private String receiptReference;
    private String paymentMethod;
    private String expenseDescription;
    private LocalDate expenseDate;
    private Double expenseAmount;
    private String expenseCategory;
    private  String expenseStatus;

}
