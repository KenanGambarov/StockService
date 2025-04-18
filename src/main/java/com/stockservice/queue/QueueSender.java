package com.stockservice.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stockservice.dto.request.StockRequestDto;
import com.stockservice.exception.QueueException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class QueueSender {

    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;


    public <T> void sendStockUpdate(String queueName, T dto) {
        try {
            amqpTemplate.convertAndSend(queueName, objectMapper.writeValueAsString(dto));
            log.info("Sent stock {} message: {}",queueName, objectMapper.writeValueAsString(dto));
        } catch (JsonProcessingException e) {
             log.error("Sending message in invalid format: {}", e.getMessage());
        }catch (Exception e) {
            throw new QueueException();
        }

    }

}
