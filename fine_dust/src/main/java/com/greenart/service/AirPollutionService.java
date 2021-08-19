package com.greenart.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.greenart.mapper.AirPollutionMapper;
import com.greenart.vo.AirPollutionHourVO;
import com.greenart.vo.AirPollutionVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirPollutionService {
    @Autowired AirPollutionMapper mapper;

    public void insertAirPollutionInfo(AirPollutionVO vo){
        mapper.insertAirPollutionInfo(vo);
    }

    public void insertAirPollutionHour(AirPollutionHourVO vo){
        mapper.insertAirPollutionHour(vo);
    }

    public List<AirPollutionHourVO> selectTodayAirPollutionHour(){
        Calendar now = Calendar.getInstance();
        Calendar standard = Calendar.getInstance();
        standard.set(Calendar.HOUR_OF_DAY, 9);
        standard.set(Calendar.MINUTE, 00);
        standard.set(Calendar.SECOND, 00);

        if(now.getTimeInMillis() < standard.getTimeInMillis()){
            // 현재 접속시간이 기준시간 (9시 00분) 보다 이전일 때
            // 하루 이전 날짜로 변경
            now.add(Calendar.DATE, -1);
            
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(now.getTime());
        // String date = formatter.format(now);

        return mapper.selectAirPollutionHour(date);

        // 10시 30분에 데이터 업데이트 - 10시 29분까지 이전 날의 데이터를 표시해야함
    }

    public List<AirPollutionHourVO> selectAirPollutionHour(String date){
        return mapper.selectAirPollutionHour(date);
    }
}
