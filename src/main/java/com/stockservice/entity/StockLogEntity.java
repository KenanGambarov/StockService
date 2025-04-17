package com.stockservice.entity;

import com.stockservice.dto.enums.StockChangeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Table(name="stock_log")
@EntityListeners(AuditingEntityListener.class)
public class StockLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private StockEntity stock;

    @Enumerated(EnumType.STRING)
    @Column(name = "change_type", nullable = false)
    private StockChangeType changeType;

    @Column(nullable = false)
    private Integer amount;

    private String description;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
