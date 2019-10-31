package com.uca.network.extension.mapper.network.dto;

import com.uca.network.extension.mapper.subnet.dto.PublicSubnetDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PublicNetworkDto {
    private String id;
    private String  subnetId;
    private String name;
    private Boolean adminStateUp;
    private String status;
    private String tenantId;
    private List<PublicSubnetDto> subnetDtoList;
    private Date createdAt;
    private Date updatedAt;
}
