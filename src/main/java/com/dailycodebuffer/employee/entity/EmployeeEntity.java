package com.dailycodebuffer.employee.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data // TODO:: remove and test...
@Table(name= "employees",
        schema = "devschema",
        uniqueConstraints = {
            @UniqueConstraint(name = "emailId", columnNames = {"emailId"})
        }
)
// public class EmployeeEntity implements Serializable {
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String firstName;
    @Column
    private String lastName;
    @Column(nullable = false)
    private String emailId;

}
