package com.uca.network.extension.mapper.subnet.dto;

import com.uca.network.core.mapper.subnet.dto.SubnetDto;
import lombok.Data;

import java.util.List;

@Data
public class PublicSubnetDtoWrapper {

    public List<PublicSubnetDto> data;
}
