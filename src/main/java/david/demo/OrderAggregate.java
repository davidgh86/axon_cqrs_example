package david.demo;

import david.demo.command.ConfirmOrderCommand;
import david.demo.command.PlaceOrderCommand;
import david.demo.command.ShipOrderCommand;
import david.demo.event.OrderConfirmedEvent;
import david.demo.event.OrderPlacedEvent;
import david.demo.event.OrderShippedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class OrderAggregate {
 
    @AggregateIdentifier
    private String orderId;
    private boolean orderConfirmed;
 
    @CommandHandler
    public OrderAggregate(PlaceOrderCommand command) {
        AggregateLifecycle.apply(new OrderPlacedEvent(command.getOrderId(), command.getProduct()));
    }
 
    @EventSourcingHandler
    public void on(OrderPlacedEvent event) {
        this.orderId = event.getOrderId();
        orderConfirmed = false;
    }

    @CommandHandler
    public void handle(ConfirmOrderCommand command) {
        AggregateLifecycle.apply(new OrderConfirmedEvent(orderId));
    }

    @CommandHandler
    public void handle(ShipOrderCommand command) {
        if (!orderConfirmed) {
            throw new UnconfirmedOrderException();
        }
        AggregateLifecycle.apply(new OrderShippedEvent(orderId));
    }

    @EventSourcingHandler
    public void on(OrderConfirmedEvent event) {
        orderConfirmed = true;
    }
 
    protected OrderAggregate() { }

    private class UnconfirmedOrderException extends RuntimeException {
    }
}