package com.greenart.component;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.greenart.service.AirPollutionService;
import com.greenart.vo.AirPollutionHourVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component
public class AirPollutionComponent {
    @Autowired AirPollutionService service;

    @Scheduled(cron = "* */60 * * * *")
    public void getAirPollution()throws Exception{
        Date dt = new Date(); // 현재시간
        SimpleDateFormat dtFormatter = new SimpleDateFormat("YYYYMMdd");
        String today = dtFormatter.format(dt);

            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnStatsSvc/getCtprvnMesureLIst"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=l7DpkJte9KebUJDW8ruDw9sBYPYSDyP4wUiFtnpBH59KSYvK8qDXoRfOcFTC2V7bcFphyC8YUy9W%2FhgzDqZr9Q%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("itemCode","UTF-8") + "=" + URLEncoder.encode("PM10", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("dataGubun","UTF-8") + "=" + URLEncoder.encode(today, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("searchCondition","UTF-8") + "=" + URLEncoder.encode("MONTH", "UTF-8"));

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(urlBuilder.toString());

            doc.getDocumentElement().normalize();
            System.out.println(doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("item");
            System.out.println("size : "+nList.getLength());
            if(nList.getLength() <= 0){
                return;
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
                Date dataTime = new Date();
                SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                dataTime = dtFormat.parse(getTagValue("dataTime", elem));
                vo.setDataTime(dataTime);

                // System.out.println(vo);
                service.insertAirPollutionHour(vo);
            }

    }
    public static String getTagValue(String tag, Element elem){
        NodeList nlList = elem.getElementsByTagName(tag).item(0).getChildNodes();
        if(nlList == null) return null;
        Node node = (Node) nlList.item(0);
        if(node == null) return null;
        return node.getNodeValue();
    }
}
