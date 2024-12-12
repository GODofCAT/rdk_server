package com.example.rdk.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "table_logs")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "control_num")
    public int controlNum;

    @Column(name = "control_num_status")
    public String controlNumStatus;

    @Column(name = "date")
    public Date date;

    @Column(name = "tag_num")
    public String tagNum;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "work_id")
    public WorkType workType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    public Company company;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "facility_id")
    public Facility facility;

    @Column(name = "uuid")
    public String uuid;

    @Column(name = "process_name")
    public String processName;

    public Log(int controlNum, String controlNumStatus, Date date, String tagNum, WorkType workType, Company company, Facility facility, String uuid, String processName) {
        this.controlNum = controlNum;
        this.controlNumStatus = controlNumStatus;
        this.date = date;
        this.tagNum = tagNum;
        this.workType = workType;
        this.company = company;
        this.facility = facility;
        this.uuid = uuid;
        this.processName = processName;
    }

    public void setControlNum(int controlNum) {
        this.controlNum = controlNum;
    }

    public void setControlNumStatus(String controlNumStatus) {
        this.controlNumStatus = controlNumStatus;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTagNum(String tagNum) {
        this.tagNum = tagNum;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
