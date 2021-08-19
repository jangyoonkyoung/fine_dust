package com.greenart.mapper;

import java.util.List;

import com.greenart.vo.AirPollutionHourVO;
import com.greenart.vo.AirPollutionVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AirPollutionMapper {
    public void insertAirPollutionInfo(AirPollutionVO vo);
    public void insertAirPollutionHour(AirPollutionHourVO vo);

    public List<AirPollutionHourVO> selectAirPollutionHour(String date);
}
