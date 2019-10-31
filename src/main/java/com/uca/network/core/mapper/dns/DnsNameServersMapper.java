package com.uca.network.core.mapper.dns;

import com.uca.network.core.mapper.dns.dto.DnsNameServersDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DnsNameServersMapper {

    public void addDnsNameServersBatch(@Param("dnsList") List<DnsNameServersDto> dnsNameServersDtos);

    public void deleteDnsNameServersBatch(@Param("subnetIds")List<String> subnetIds);

    public List<DnsNameServersDto> queryDnsNameServersList(@Param("subnetIds")List<String> subnetIds);
}
