package com.dl.activity.model;

import javax.persistence.*;

@Table(name = "dl_world_cup_plan_config")
public class DlWorldCupPlanConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer amount;

    private Integer count;

    @Column(name = "start_time1")
    private Integer startTime1;

    @Column(name = "end_time1")
    private Integer endTime1;

    private String desc1;

    @Column(name = "start_time2")
    private Integer startTime2;

    @Column(name = "end_time2")
    private Integer endTime2;

    private String desc2;

    @Column(name = "start_time3")
    private Integer startTime3;

    @Column(name = "end_time3")
    private Integer endTime3;

    private String desc3;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return amount
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * @param amount
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * @return count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return start_time1
     */
    public Integer getStartTime1() {
        return startTime1;
    }

    /**
     * @param startTime1
     */
    public void setStartTime1(Integer startTime1) {
        this.startTime1 = startTime1;
    }

    /**
     * @return end_time1
     */
    public Integer getEndTime1() {
        return endTime1;
    }

    /**
     * @param endTime1
     */
    public void setEndTime1(Integer endTime1) {
        this.endTime1 = endTime1;
    }

    /**
     * @return desc1
     */
    public String getDesc1() {
        return desc1;
    }

    /**
     * @param desc1
     */
    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    /**
     * @return start_time2
     */
    public Integer getStartTime2() {
        return startTime2;
    }

    /**
     * @param startTime2
     */
    public void setStartTime2(Integer startTime2) {
        this.startTime2 = startTime2;
    }

    /**
     * @return end_time2
     */
    public Integer getEndTime2() {
        return endTime2;
    }

    /**
     * @param endTime2
     */
    public void setEndTime2(Integer endTime2) {
        this.endTime2 = endTime2;
    }

    /**
     * @return desc2
     */
    public String getDesc2() {
        return desc2;
    }

    /**
     * @param desc2
     */
    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    /**
     * @return start_time3
     */
    public Integer getStartTime3() {
        return startTime3;
    }

    /**
     * @param startTime3
     */
    public void setStartTime3(Integer startTime3) {
        this.startTime3 = startTime3;
    }

    /**
     * @return end_time3
     */
    public Integer getEndTime3() {
        return endTime3;
    }

    /**
     * @param endTime3
     */
    public void setEndTime3(Integer endTime3) {
        this.endTime3 = endTime3;
    }

    /**
     * @return desc3
     */
    public String getDesc3() {
        return desc3;
    }

    /**
     * @param desc3
     */
    public void setDesc3(String desc3) {
        this.desc3 = desc3;
    }
}