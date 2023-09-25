package com.ecommerce.orderservice.service.implementation;

import com.ecommerce.orderservice.dto.InventoryResponse;
import com.ecommerce.orderservice.entities.Order;
import com.ecommerce.orderservice.entities.OrderLineItem;
import com.ecommerce.orderservice.event.OrderPlacedEvent;
import com.ecommerce.orderservice.exceptions.ResourceNotFoundException;
import com.ecommerce.orderservice.models.OrderLineItemModel;
import com.ecommerce.orderservice.models.OrderModel;
import com.ecommerce.orderservice.models.apiResponseModels.ApiResponseOrderModels;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderServiceImplementation implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Override
    public OrderModel placeOrder(OrderModel orderModel) {

        BigDecimal totalPrice = new BigDecimal(0);

        Order order = this.modelMapper.map(orderModel, Order.class);

        List<OrderLineItem> orderLineItems = new ArrayList<>();
        List<String> skuCodes = new ArrayList<>();

        Date date = new Date();

        for (OrderLineItemModel orderLineItemModel : orderModel.getOrderLineItems()) {
            OrderLineItem orderLineItem = this.modelMapper.map(orderLineItemModel, OrderLineItem.class);
            totalPrice = totalPrice.add(
                    orderLineItemModel.getPrice().
                            multiply(BigDecimal.valueOf(orderLineItemModel.getQuantity()))
            );

            orderLineItem.setCreatedAt(date);
            orderLineItem.setUpdatedAt(null);

            orderLineItem.setOrder(order);
            orderLineItems.add(orderLineItem);

            skuCodes.add(orderLineItemModel.getSkuCode());
        }

        order.setOrderLineItems(orderLineItems);
        order.setTotalAmount(totalPrice);
        order.setOrderNumber("OID" + System.currentTimeMillis());
        order.setCreatedAt(date);
        order.setUpdatedAt(null);

        //sync call to inventory service to check product is in stock
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build()
                .get()
                .uri("http://inventory-service/api/inventory/isInStock",
                        uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        assert inventoryResponseArray != null;
        boolean allProductsAreInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::isInStock);

        if (allProductsAreInStock) {
            Order createdOrder = orderRepository.save(order);

            kafkaTemplate
                    .send("notificationTopic", new OrderPlacedEvent(createdOrder.getOrderNumber()));

            return this.modelMapper.map(createdOrder, OrderModel.class);
        }

        return null;
    }

    @Override
    public OrderModel getOrderById(Long orderId) {

        Order order = this.orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "order ID", orderId.toString()));

        return this.modelMapper.map(order, OrderModel.class);

    }

    @Override
    public ApiResponseOrderModels getAllOrders(Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode) {

        // sorting format
        Sort sort = (sortMode == 0) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // paging format
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // retrieving paged data items
        Page<Order> pageOrders = this.orderRepository.findAll(pageable);

        List<Order> allOrders = pageOrders.getContent();

        List<OrderModel> orderModels = allOrders.stream()
                .map((order) -> this.modelMapper.map(order, OrderModel.class))
                .toList();

        ApiResponseOrderModels apiResponseOrderModels = new ApiResponseOrderModels(true, HttpStatus.OK.value(),
                "Order(s) Fetched Successfully", pageOrders.getNumber(), pageOrders.getSize(),
                pageOrders.getTotalElements(), pageOrders.getTotalPages(), pageOrders.isLast(),
                orderModels);

        if (pageOrders.getNumber() >= pageOrders.getTotalPages()) {

            apiResponseOrderModels.setMessage("No more order(s) found");
            apiResponseOrderModels.setOrderModels(null);

        }

        return apiResponseOrderModels;
    }

    @Override
    public ApiResponseOrderModels getOrdersByUser(Integer userId,Integer pageNumber, Integer pageSize, String sortBy, Integer sortMode) {
        return null;
    }

    @Override
    public OrderModel updateOrder(OrderModel orderModel) {

        Order order = this.orderRepository.findById(orderModel.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", "order ID", orderModel.getOrderId().toString()));

        return null;

    }

    @Override
    public void deleteOrder(Long orderId) {

        Order order = this.orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "order ID", orderId.toString()));

        this.orderRepository.delete(order);

    }

}
