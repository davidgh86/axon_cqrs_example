package david.demo;

import david.demo.command.PlaceOrderCommand;
import david.demo.command.ShipOrderCommand;
import david.demo.event.OrderConfirmedEvent;
import david.demo.event.OrderPlacedEvent;
import david.demo.event.OrderShippedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	private FixtureConfiguration<OrderAggregate> fixture;

	@Before
	public void setUp() {
		fixture = new AggregateTestFixture<>(OrderAggregate.class);
	}

	@Test
	public void test(){
		String orderId = UUID.randomUUID().toString();
		String product = "Deluxe Chair";
		fixture.givenNoPriorActivity()
				.when(new PlaceOrderCommand(orderId, product))
				.expectEvents(new OrderPlacedEvent(orderId, product));
	}

	@Test
	public void test2(){
		String orderId = UUID.randomUUID().toString();
		String product = "Deluxe Chair";
		fixture.given(new OrderPlacedEvent(orderId, product))
				.when(new ShipOrderCommand(orderId))
				.expectException(IllegalStateException.class);
	}

	@Test
	public void test3(){
		String orderId = UUID.randomUUID().toString();
		String product = "Deluxe Chair";
		fixture.given(new OrderPlacedEvent(orderId, product), new OrderConfirmedEvent(orderId))
				.when(new ShipOrderCommand(orderId))
				.expectEvents(new OrderShippedEvent(orderId));
	}

}
