package ep.affordable_automation.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrdersDTO {

    private Integer orderId;

    @NotNull
    @Size(max = 50)
    private String status;

    @Size(max = 100)
    private String transactionId;

    @NotNull
    private Integer user;

    @NotNull
    private Integer product;

}
