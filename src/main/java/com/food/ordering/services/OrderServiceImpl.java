package com.food.ordering.services;

import com.food.ordering.model.*;
import com.food.ordering.repository.AddressRepository;
import com.food.ordering.repository.OrderItemRepository;
import com.food.ordering.repository.OrderRepository;
import com.food.ordering.repository.UserRepository;
import com.food.ordering.request.OrderRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    private OrderRepository orderRepository;

    private OrderItemRepository orderItemRepository;

    private AddressRepository addressRepository;

    private UserRepository userRepository;

    private RestaurantService restaurantService;

    private CartService cartService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, AddressRepository addressRepository, UserRepository userRepository, RestaurantService restaurantService, CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.restaurantService = restaurantService;
        this.cartService = cartService;
    }

    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception{


        Address shipAddress = order.getDeliveryAddress();

        Address savedAddress = addressRepository.save(shipAddress);

        if(!user.getAddresses().contains(savedAddress))
        {
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }

        Restaurant restaurant = restaurantService.findRestaurantById(order.getRestaurantId());


        Order createdOrder = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setRestaurant(restaurant);
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setCreatedAt(new Date());
        createdOrder.setDeliveryAddress(order.getDeliveryAddress());


        Cart cart = cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem: cart.getItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }
        Long totalPrice = cartService.calculateCartTotals(cart);
        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotal());

        Order savedOrder = orderRepository.save(createdOrder);
        restaurant.getOrders().add(savedOrder);

        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        if(orderStatus.equals("OUT_FOR_DELIVERY")
            || orderStatus.equals("DELEVERED")
            || orderStatus.equals("COMPLETED")
            || orderStatus.equals("PENDING")
        ) {
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new Exception("Please select a valid order status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);

    }

    @Override
    public List<Order> getUserOrder(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders=orderRepository.findByRestaurantId(restaurantId);
        if(orderStatus!=null)
        {
            orders= orders.stream().filter(order ->
                    order.getOrderStatus().equals(orderStatus)).toList();
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long id) throws Exception {
        Optional<Order> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isEmpty()) throw new Exception("Order Not Found");


        return optionalOrder.get();
    }
}
