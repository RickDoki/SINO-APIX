package com.sinosdx.service.event.bridge.assembler;

import com.sinosdx.service.event.bridge.controller.dto.BusDTO;
import com.sinosdx.service.event.bridge.controller.dto.BusSaveDTO;
import com.sinosdx.service.event.bridge.controller.dto.BusUpdateDTO;
import com.sinosdx.service.event.bridge.dao.entity.Bus;
import org.mapstruct.Mapper;

/**
 * @author pengjiahu
 * @date 2020-12-05 20:58
 * @description
 */
@Mapper(componentModel = "spring")
public interface BusConverter {

    Bus saveDTOToBus(BusSaveDTO busSaveDTO);

    Bus updateDTOToBus(BusUpdateDTO busUpdateDTO);

    BusDTO toBusDTO(Bus bus);
}
