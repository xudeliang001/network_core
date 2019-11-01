package com.uca.network.core.mapper.attr;


import com.uca.network.core.mapper.attr.dto.StandardattributesDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StandardattributesMapper {

    public List<StandardattributesDto> queryAttrsByParam(@Param("attrids") List<String> attrids);

    public int insertStandardattributes(StandardattributesDto standardattributesDto);

    public void deleteStandardattributesById(@Param("id") String id);

    public void deleteStandardattributesBatch(@Param("ids") List<String> ids);
}
