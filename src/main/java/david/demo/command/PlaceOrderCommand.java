package david.demo.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class PlaceOrderCommand {
  
    @TargetAggregateIdentifier
    private final String orderId;
    private final String product;

}