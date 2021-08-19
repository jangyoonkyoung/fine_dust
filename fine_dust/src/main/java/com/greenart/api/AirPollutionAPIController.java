package com.greenart.api;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.greenart.service.AirPollutionService;
import com.greenart.vo.AirPollutionHourVO;
import com.greenart.vo.AirPollutionVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@RestController
public class AirPollutionAPIController {
    @Autowired AirPollutionService service;

    @GetMapping("/api/finedust")
    public Map<String, Object> getAirPollutionInfo() throws Exception{
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnStatsSvc/getCtprvnMesureLIst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=l7DpkJte9KebUJDW8ruDw9sBYPYSDyP4wUiFtnpBH59KSYvK8qDXoRfOcFTC2V7bcFphyC8YUy9W%2FhgzDqZr9Q%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("itemCode","UTF-8") + "=" + URLEncoder.encode("PM10", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("dataGubun","UTF-8") + "=" + URLEncoder.encode("DAILY", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("searchCondition","UTF-8") + "=" + URLEncoder.encode("MONTH", "UTF-8"));

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(urlBuilder.toString());

        doc.getDocumentElement().normalize();
        System.out.println(doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("item");
        System.out.println("size : "+nList.getLength());
        if(nList.getLength() <= 0){
            resultMap.put("status", false);
            resultMap.put("message", "데이터가 없습니다.");
            return resultMap;
        }
        for(int i=0; i<nList.getLength(); i++){
            // 순차조회
            Node node =nList.item(i);
            Element elem = (Element) node;

            AirPollutionVO vo = new AirPollutionVO();
            vo.setDaegu(Integer.parseInt(getTagValue("daegu", elem)));
            vo.setChungnam(Integer.parseInt(getTagValue("chungnam", elem)));
            vo.setIncheon(Integer.parseInt(getTagValue("incheon", elem)));
            vo.setDaejeon(Integer.parseInt(getTagValue("daejeon", elem)));
            vo.setGyeongbuk(Integer.parseInt(getTagValue("gyeongbuk", elem)));
            vo.setSejong(Integer.parseInt(getTagValue("sejong", elem)));
            vo.setGwangju(Integer.parseInt(getTagValue("gwangju", elem)));
            vo.setJeonbuk(Integer.parseInt(getTagValue("jeonbuk", elem)));
            vo.setGangwon(Integer.parseInt(getTagValue("gangwon", elem)));
            vo.setUlsan(Integer.parseInt(getTagValue("ulsan", elem)));
            vo.setJeonnam(Integer.parseInt(getTagValue("jeonnam", elem)));
            vo.setSeoul(Integer.parseInt(getTagValue("seoul", elem)));
            vo.setBusan(Integer.parseInt(getTagValue("busan", elem)));
            vo.setJeju(Integer.parseInt(getTagValue("jeju", elem)));
            vo.setChungbuk(Integer.parseInt(getTagValue("chungbuk", elem)));
            vo.setGyeongnam(Integer.parseInt(getTagValue("gyeongnam", elem)));
            vo.setDataGubun(getTagValue("dataGubun", elem));
            vo.setGyeonggi(Integer.parseInt(getTagValue("gyeonggi", elem)));
            vo.setItemCode(getTagValue("itemCode", elem));
            Date dt = new Date();
            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
            dt = dtFormat.parse(getTagValue("dataTime", elem));
            vo.setDataTime(dt); 

            // System.out.println(vo);
            service.insertAirPollutionInfo(vo);
        }
        resultMap.put("status", true);
        resultMap.put("message", "데이터가 입력되었습니다.");
        return resultMap;
    }

    @GetMapping("/api/finedust_hour")
    public Map<String, Object> getAirPollutionHour() throws Exception{
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnStatsSvc/getCtprvnMesureLIst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=l7DpkJte9KebUJDW8ruDw9sBYPYSDyP4wUiFtnpBH59KSYvK8qDXoRfOcFTC2V7bcFphyC8YUy9W%2FhgzDqZr9Q%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("itemCode","UTF-8") + "=" + URLEncoder.encode("PM10", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("dataGubun","UTF-8") + "=" + URLEncoder.encode("HOUR", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("searchCondition","UTF-8") + "=" + URLEncoder.encode("MONTH", "UTF-8"));

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(urlBuilder.toString());

        doc.getDocumentElement().normalize();
        System.out.println(doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("item");
        System.out.println("size : "+nList.getLength());
        if(nList.getLength() <= 0){
            resultMap.put("status", false);
            resultMap.put("message", "데이터가 없습니다.");
            return resultMap;
        }
        for(int i=0; i<nList.getLength(); i++){
            // 순차조회
            Node node =nList.item(i);
            Element elem = (Element) node;

            AirPollutionHourVO vo = new AirPollutionHourVO();
            vo.setDaegu(Integer.parseInt(getTagValue("daegu", elem)));
            vo.setChungnam(Integer.parseInt(getTagValue("chungnam", elem)));
            vo.setIncheon(Integer.parseInt(getTagValue("incheon", elem)));
            vo.setDaejeon(Integer.parseInt(getTagValue("daejeon", elem)));
            vo.setGyeongbuk(Integer.parseInt(getTagValue("gyeongbuk", elem)));
            vo.setSejong(Integer.parseInt(getTagValue("sejong", elem)));
            vo.setGwangju(Integer.parseInt(getTagValue("gwangju", elem)));
            vo.setJeonbuk(Integer.parseInt(getTagValue("jeonbuk", elem)));
            vo.setGangwon(Integer.parseInt(getTagValue("gangwon", elem)));
            vo.setUlsan(Integer.parseInt(getTagValue("ulsan", elem)));
            vo.setJeonnam(Integer.parseInt(getTagValue("jeonnam", elem)));
            vo.setSeoul(Integer.parseInt(getTagValue("seoul", elem)));
            vo.setBusan(Integer.parseInt(getTagValue("busan", elem)));
            vo.setJeju(Integer.parseInt(getTagValue("jeju", elem)));
            vo.setChungbuk(Integer.parseInt(getTagValue("chungbuk", elem)));
            vo.setGyeongnam(Integer.parseInt(getTagValue("gyeongnam", elem)));
            vo.setDataGubun(getTagValue("dataGubun", elem));
            vo.setGyeonggi(Integer.parseInt(getTagValue("gyeonggi", elem)));
            vo.setItemCode(getTagValue("itemCode", elem));
            Date dt = new Date();
            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            dt = dtFormat.parse(getTagValue("dataTime", elem));
            vo.setDataTime(dt); 

            // System.out.println(vo);
            service.insertAirPollutionHour(vo);
        }
        resultMap.put("status", true);
        resultMap.put("message", "데이터가 입력되었습니다.");
        return resultMap;
    }

    public static String getTagValue(String tag, Element elem){
        NodeList nlList = elem.getElementsByTagName(tag).item(0).getChildNodes();
        if(nlList == null) return null;
        Node node = (Node) nlList.item(0);
        if(node == null) return null;
        return node.getNodeValue();
    }

    @GetMapping("/api/finedust_hour/{date}")
    public Map<String, Object> getHourDate(@PathVariable String date){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        if(date.equals("today")){
            List<AirPollutionHourVO> list = service.selectTodayAirPollutionHour();
            resultMap.put("status", true);
            resultMap.put("data", list);
        } else {
            List<AirPollutionHourVO> list = service.selectAirPollutionHour(date);
            resultMap.put("status", true);
            resultMap.put("data", list);
        }
        return resultMap;
    }
}
