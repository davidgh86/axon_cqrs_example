package david.demo.event;

import lombok.Data;

@Data
public class OrderPlacedEvent {
  
    private final String orderId;
    private final String product;

}