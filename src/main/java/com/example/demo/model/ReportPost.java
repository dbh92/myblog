package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data @AllArgsConstructor
@NoArgsConstructor
public class ReportPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long report_id;
    private String content;
    private Date report_date;
}
