package com.neusoft.mid.ec.api.service.civiladministration;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.service.civiladministration.BookDept.BookDeptService;
import com.neusoft.mid.ec.api.service.civiladministration.BookDept.BookDeptServicePortType;
import com.neusoft.mid.ec.api.service.civiladministration.BookTime.BookTimeService;
import com.neusoft.mid.ec.api.service.civiladministration.BookTime.BookTimeServicePortType;
import com.neusoft.mid.ec.api.service.civiladministration.CancelBook.CancelBookMarryService;
import com.neusoft.mid.ec.api.service.civiladministration.CancelBook.CancelBookMarryServicePortType;
import com.neusoft.mid.ec.api.service.civiladministration.MaryyBook.MaryyBookService;
import com.neusoft.mid.ec.api.service.civiladministration.MaryyBook.MaryyBookServicePortType;
import com.neusoft.mid.ec.api.service.civiladministration.QueryBook.QueryBookMarryService;
import com.neusoft.mid.ec.api.service.civiladministration.QueryBook.QueryBookMarryServicePortType;
import com.neusoft.mid.ec.api.serviceInterface.civiladministration.CivilAdministrationService;
import com.neusoft.mid.ec.api.util.MD5Util;

import java.net.URL;

/**
 * 民政业务
 */
public class CivilAdministrationServiceImpl implements CivilAdministrationService {





    // 测试主函数
    public static void main(String args[]) throws Exception {
        URL url = null;
            url = new URL("http://59.207.24.170:8099/marry_book/ws/getDeptInfo?wsdl");
        BookDeptService bds = new BookDeptService(url);
        BookDeptServicePortType bdsp = bds.getBookDeptServicePort();
        String deptInfo = bdsp.getDeptInfo("7af398e2940bed7ad72678c86434e441","IA", "500114");
        System.out.println(deptInfo);

//        BookTimeService bs= new BookTimeService();
//        BookTimeServicePortType bsp = bs.getBookTimeServicePort();
//        String bookTime = bsp.getBookTime("7af398e2940bed7ad72678c86434e441","IA", "50010901");// 业务类型，登记处代码
//        System.out.println(bookTime);
//
//        QueryBookMarryService bookMarryService = new QueryBookMarryService();
//        QueryBookMarryServicePortType qbmspt
//                = bookMarryService.getQueryBookMarryServicePort();
//        String queryBookMarryInfo = qbmspt.queryBookMarryInfo("7af398e2940bed7ad72678c86434e441","IA", "11010119720101015X", "110101197201010125");
//        System.out.println(queryBookMarryInfo);
//
//
//        CancelBookMarryService cbms= new CancelBookMarryService();
//        CancelBookMarryServicePortType cbmspt = cbms.getCancelBookMarryServicePort();
//        String cancelBook = cbmspt.cancelBook("7af398e2940bed7ad72678c86434e441","IA", "11010119720101015X", "110101197201010125");
//        System.out.println(cancelBook);

//        JSONObject json = new JSONObject();
//
//        String abc = MD5Util.string2MD5("0000000053809045015388bb832d0008"+json.toJSONString());
//
//        MaryyBookService ms= new MaryyBookService();
//        MaryyBookServicePortType msp = ms.getMaryyBookServicePort();
//        String saveBookMarryInfo = msp.saveBookMarryInfo(abc, json.toJSONString());
//        System.out.println(saveBookMarryInfo);

    }
}
