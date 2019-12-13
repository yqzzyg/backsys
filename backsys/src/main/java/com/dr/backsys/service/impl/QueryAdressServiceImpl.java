package com.dr.backsys.service.impl;

import com.dr.backsys.utils.dateUtil.DateUtil;
import org.apache.commons.lang3.StringUtils;
import com.dr.backsys.dao.QueryNumDao;
import com.dr.backsys.entity.QueryNewUser;
import com.dr.backsys.service.QueryAdressService;
import com.dr.backsys.utils.JsonResult;
import com.dr.backsys.utils.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Description   :  查询新增用户
 * @ Author        :  yqz
 * @ CreateDate    :  2019/12/12 20:03
 */
@Service
public class QueryAdressServiceImpl implements QueryAdressService {

    @Autowired
    private Environment environment;

    @Autowired(required = false)
    private QueryNumDao queryNumDao;

    @Override
    public List<QueryNewUser> queryNewUsers(String beginTime, String endTime) {
        return queryNumDao.queryNewUsers(beginTime, endTime);
    }

    @Override
    public JsonResult queryNewUser(String beginTime, String endTime) {

        if (StringUtils.isBlank(beginTime)){
            beginTime = "1970-00-00";
        }
        if (StringUtils.isBlank(endTime)){
            endTime=DateUtil.dateFormat();
        }

        List<QueryNewUser> queryNewUsers = queryNumDao.queryNewUsers(beginTime, endTime);


        List<Map> list = new ArrayList<>();

        for (QueryNewUser queryNewUser : queryNewUsers) {
            HashMap<String, Object> hashMap = new HashMap<>();
            Integer resultNum = Integer.valueOf(queryNewUser.getNum());
            Double wan = Double.valueOf(resultNum / 10000.00);
            String area = queryNewUser.getArea();
            String areaName="";
            switch (area){
                case "zhengzhou":
                    areaName= "郑州市";
                    break;
                case "kaifeng":
                    areaName= "开封市";
                    break;
                case "luoyang":
                    areaName= "洛阳市";
                    break;
                case "pingdingshan":
                    areaName= "平顶山市";
                    break;
                case "anyang":
                    areaName= "安阳市";
                    break;
                case "hebi":
                    areaName= "鹤壁市";
                    break;
                case "xinxiang":
                    areaName= "新乡市";
                    break;
                case "jiaozuo":
                    areaName= "焦作市";
                    break;
                case "puyang":
                    areaName= "濮阳市";
                    break;
                case "luohe":
                    areaName= "漯河市";
                    break;
                case "sanmenxia":
                    areaName= "三门峡市";
                    break;
                case "nanyang":
                    areaName= "南阳市";
                    break;
                case "shangqiu":
                    areaName= "商丘市";
                    break;
                case "zhoukou":
                    areaName= "周口市";
                    break;
                case "zhumadian":
                    areaName= "驻马店市";
                    break;
                case "jiyuan":
                    areaName= "济源市";
                    break;
                default:
                   areaName="未知";

            }

            String property = environment.getProperty(area);

            StringBuffer buffer = new StringBuffer();
            buffer.append(property);

            if (StringUtils.isBlank(property)){
                continue;
            }

            Double czrk = Double.valueOf(property);

            double pjlv = wan / czrk;
            //格式化成百分数
            NumberFormat nf = java.text.NumberFormat.getPercentInstance();
            nf.setMinimumFractionDigits(10);// 小数点后保留几位
            String format = nf.format(pjlv);

            hashMap.put("czrk",buffer+"万");
            hashMap.put("areaName",areaName);
            hashMap.put("area", area);
            hashMap.put("resultNum", resultNum);
            hashMap.put("pujv", format);
            list.add(hashMap);
        }

        return JsonResultUtil.setOK(list);
    }
}
