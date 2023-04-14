package com.ss.abtest.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.ss.abtest.exception.IllegalParamException;
import com.ss.abtest.pojo.domain.Flight;
import com.ss.abtest.pojo.domain.Layer;
import com.ss.abtest.pojo.domain.User;
import com.ss.abtest.pojo.domain.Version;
import com.ss.abtest.pojo.status.FlightStatus;
import com.ss.abtest.pojo.status.Position;
import com.ss.abtest.pojo.vo.FlightUser;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
@Data
public class FlightDto {
    private Long flightId;
    private User owner;
    private List<FlightUser> users;
    private Flight flight;
    private FlightStatus status;
    private Layer layer;
    private List<Integer> buckets;
    private List<Version> versions;

    public Flight getFlightEntry(){
        flight.setStatus(getStatus().getValue());
        flight.setCreateTime(LocalDateTime.now());
        flight.setUpdateTime(LocalDateTime.now());
        flight.setOwnerId(owner.getUserId());
        return flight;
    }

    public Long getFlightId() {
        if (flightId == null) {
            flightId = getFlight().getFlightId();
        }
        return flightId;
    }

    public List<Version> getVersionEntry(){
        getVersions().forEach(v -> {
            if (v.getFlightId() == null) {
                v.setFlightId(getFlightId());
            }
            if (v.getCreateTime() == null) {
                v.setCreateTime(LocalDateTime.now());
            }
        });
        return getVersions();
    }

    public void verifyParams() {
        checkOwnerNotNull();
        checkFlight();
        checkLayer();
        checkVersions();
    }

    private void checkVersions() {
        if (getVersions() == null || getVersions().isEmpty()) {
            throw new IllegalParamException("versions is null");
        }
        for (Version version : getVersions()) {
            if (version.getName() == null) {
                throw new IllegalParamException("versions name is null");
            }
            if (version.getConfig() == null) {
                throw new IllegalParamException("versions config is null");
            }
        }
    }

    private void checkLayer() {
        if (getLayer() == null) {
            throw new IllegalParamException("layer is null");
        }
        if (getLayer().getLayerId() == null) {
            throw new IllegalParamException("layer id is null");
        }
    }

    private void checkFlight() {
        if(getFlight() == null) {
            throw new IllegalParamException("flight is null");
        }
        if(getFlight().getFilter() == null) {
            throw new IllegalParamException("flight filter is null");
        }
        if(getFlight().getCompanyId() == null) {
            throw new IllegalParamException("flight companyId is null");
        }
        if(getFlight().getName() == null) {
            throw new IllegalParamException("flight name is null");
        }
        if(getFlight().getTraffic() == null) {
            throw new IllegalParamException("flight traffic is null");
        }
        if(getFlight().getTraffic() < 0 || getFlight().getTraffic() > 100) {
            throw new IllegalParamException("flight traffic error. traffic: " + getFlight().getTraffic());
        }
        if (getStatus() != FlightStatus.CREATED) {
            throw new IllegalParamException("flight status error. status: " + getStatus());
        }
    }

    private void checkOwnerNotNull() {
        if (getOwner() == null) {
            throw new IllegalParamException("user is null");
        }
        if (getOwner().getUserId() == null) {
            throw new IllegalParamException("userId is null");
        }
    }

    public Long getLayerId() {
        return getLayer().getLayerId();
    }

    public int getFlightTraffic() {
        return getFlight().getTraffic();
    }

    public List<FlightUser> getFlightUsers() {
        List<FlightUser> list = new ArrayList<>();
        FlightUser owner = new FlightUser();
        owner.setFlightId(getFlightId());
        owner.setPosition(Position.CREATER.getValue());
        owner.setUserId(getOwner().getUserId());
        list.add(owner);
        if (getUsers() != null && !getUsers().isEmpty()) {
            list.addAll(getUsers());
        }
        return list;
    }
}
