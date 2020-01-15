package david.demo;

import david.demo.event.OrderConfirmedEvent;
import david.demo.event.OrderPlacedEvent;
import david.demo.event.OrderShippedEvent;
import david.demo.model.OrderedProduct;
import david.demo.query.FindAllOrderedProductsQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderedProductsEventHandler {
 
    private final Map<String, OrderedProduct> orderedProducts = new HashMap<>();
 
    @EventHandler
    public void on(OrderPlacedEvent event) {
        String orderId = event.getOrderId();
        orderedProducts.put(orderId, new OrderedProduct(orderId, event.getProduct()));
    }

    @EventHandler
    public void on(OrderConfirmedEvent event) {
        throw new NotImplementedException();
    }

    @EventHandler
    public void on(OrderShippedEvent event) {
        throw new NotImplementedException();
    }

    @QueryHandler
    public List<OrderedProduct> handle(FindAllOrderedProductsQuery query) {
        return new ArrayList<>(orderedProducts.values());
    }
}