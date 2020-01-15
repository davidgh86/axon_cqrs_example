package david.demo.event;

import lombok.Data;

@Data
public class OrderShippedEvent {
 
    private final String orderId; 

}