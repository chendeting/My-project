package com.ad.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "train")
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column
    private String identifier;
    @Column(name = "hard_sleeper")
    private Integer hardSleeper;
    @Column(name = "soft_sleeper")
    private Integer softSleeper;
    @Column(name = "hard_seat")
    private Integer hardSeat;
    @Column(name = "soft_seat")
    private Integer softSeat;
    @Column(name = "no_seat")
    private Integer noSeat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "train")
    private Set<Trips> trips;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt = new Date();
    @Column(name = "updated_at", insertable = false)
    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getHardSleeper() {
        return hardSleeper;
    }

    public void setHardSleeper(Integer hardSleeper) {
        this.hardSleeper = hardSleeper;
    }

    public Integer getSoftSleeper() {
        return softSleeper;
    }

    public void setSoftSleeper(Integer softSleeper) {
        this.softSleeper = softSleeper;
    }

    public Integer getHardSeat() {
        return hardSeat;
    }

    public void setHardSeat(Integer hardSeat) {
        this.hardSeat = hardSeat;
    }

    public Integer getSoftSeat() {
        return softSeat;
    }

    public void setSoftSeat(Integer softSeat) {
        this.softSeat = softSeat;
    }

    public Integer getNoSeat() {
        return noSeat;
    }

    public void setNoSeat(Integer noSeat) {
        this.noSeat = noSeat;
    }

    public Set<Trips> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trips> trips) {
        this.trips = trips;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
