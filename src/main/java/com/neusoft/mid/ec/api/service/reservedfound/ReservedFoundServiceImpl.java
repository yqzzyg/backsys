package com.neusoft.mid.ec.api.service.reservedfound;

import com.alibaba.fastjson.JSON;
import com.neusoft.mid.ec.api.serviceInterface.reservedFund.TraderService;
import com.neusoft.mid.ec.api.serviceInterface.reservedfound.ReservedFoundService;
import com.neusoft.mid.ec.api.util.RopUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Administrator
 * 洛阳公积金
 */
@Service
public class ReservedFoundServiceImpl implements ReservedFoundService {

    private Logger LOG = LoggerFactory.getLogger(ReservedFoundServiceImpl.class);


    @Autowired
    private ReservedFundRepository reservedFundRepository;
    
    
    @Autowired
    private Environment environment;

    private static String RF_ADDR = "";

    private static String RF_AUTHCODE = "";
    private static String RF_FORGCODE = "";
    private static String RF_TORGCODE = "";
    private static String RF_CERTCODE = "";
    private static String RF_TXCHANNEL = "";

    private static TraderService trader;

    public static TraderService getTrader() {
        if (trader == null) {
            JaxWsProxyFactoryBean jwpf = new JaxWsProxyFactoryBean();
            jwpf.setServiceClass(TraderService.class);
            jwpf.setAddress(RF_ADDR);
            trader = (TraderService) jwpf.create();
        }
        return trader;
    }

    /**
     * reservedFound.authcode=72581cep4dbbe0dfb8ec
     * reservedFound.forgcode=72581cep4dbbe0dfb8ec
     * reservedFound.torgcode=5510gjj
     * reservedFound.certcode=828cep4e9e9c794c0267
     * reservedFound.txchannel=1
     */
    @PostConstruct
    public void initConf() {
        RF_ADDR = environment.getProperty("reservedFound.url");
        RF_AUTHCODE = environment.getProperty("reservedFound.authcode");
        RF_FORGCODE = environment.getProperty("reservedFound.forgcode");
        RF_TORGCODE = environment.getProperty("reservedFound.torgcode");
        RF_CERTCODE = environment.getProperty("reservedFound.certcode");
        RF_TXCHANNEL = environment.getProperty("reservedFound.txchannel");
    }


    /**
     * {
     * "cardcode": "411281198801134029",
     * "authcode": "72581cep4dbbe0dfb8ec",
     * "forgcode": "72581cep4dbbe0dfb8ec",
     * "torgcode": "5510gjj",
     * "certcode": "828cep4e9e9c794c0267",
     * "txchannel": "1"
     * }
     * 获取公积金授权信息
     *
     * @param idno
     * @return
     */
    public Map<String, String> getAuthInfo(String idno) {
        Map params = new HashMap(10);
        params.put("cardcode", idno);
        params.put("authcode", RF_AUTHCODE);
        params.put("forgcode", RF_FORGCODE);
        params.put("torgcode", RF_TORGCODE);
        params.put("certcode", RF_CERTCODE);
        params.put("txchannel", RF_TXCHANNEL);
        params.put("txcode", "1PBL010");
        LOG.info("getAuthInfo params={}", params.toString());
        try {
            String signStr = RopUtil.getRequestData(params);
            LOG.info("getAuthInfo signStr={}", signStr.toString());
            String xml = getTrader().doTrader(signStr);
            LOG.info("getAuthInfo result={}", xml);
            Map result = RopUtil.xmlTOMap(xml);
            String percode = MapUtils.getString(result, "percode");
            if (result != null && org.apache.commons.lang3.StringUtils.isNotBlank(percode)) {
                params.put("percode", percode);
                params.remove("txcode");
                return params;
            }
            return null;
        } catch (Exception e) {
            LOG.error("getAuthInfo error", e);
        }
        return null;
    }
    
    //获取共用的参数
    public Map<String, String> getCommonInfo(Map<String, String> map) {
        
    	map.put("forgcode", RF_FORGCODE);
    	map.put("torgcode", RF_TORGCODE);
    	map.put("certcode", RF_CERTCODE);
    	map.put("txchannel", RF_TXCHANNEL);
        
        return map;
    }

    public static String getAccountStateText(String code) {

        if(code == null){
            return "";
        }

        switch (code) {
            case "01":
                return "正常";
            case "02":
                return "封存";
            case "03":
                return "合并销户";
            case "04":
                return "外部转出有户";
            case "05":
                return "提取销户";
            case "06":
                return "冻结";
            case "07":
                return "托管";
            case "09":
                return "待生效";
            case "99":
                return "其他";

        }
        return "";
    }
//
//    public static void main(String[] args) {
//        System.out.println(new ReservedFoundServiceImpl().getAuthInfo("411281198801134029"));
//    }

    
    //将公积金提取的参数传入数据库备份
    public void insertReservedFundContent(Map map) {
     
      Map mapContent=new HashMap();
      mapContent.put("id", UUID.randomUUID().toString().replace("-", ""));
      mapContent.put("content", JSON.toJSONString(map));
      reservedFundRepository.insertReservedFundContent(mapContent);
      
    }
   
}
