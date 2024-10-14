package com.example.law.service.impl;

import com.example.law.dao.LegalDocHelpRepository;
import com.example.law.dao.LinkOfLegalDocRepository;
import com.example.law.pojo.entity.LegalDocumentHelp;
import com.example.law.pojo.entity.LinkOfLegalDoc;
import com.example.law.service.LegalDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LegalDocServiceImpl implements LegalDocService {
    @Autowired
    private OpenAIServiceImpl openAIService;
    @Autowired
    private LegalDocHelpRepository legalDocHelpRepository;
    @Autowired
    private LinkOfLegalDocRepository linkOfLegalDocRepository;

    public Map<String, String> docLinkMap = new HashMap<>();

    public void initMap(){
        docLinkMap.put("法律援助授权委托书", "1");
        docLinkMap.put("复核申请书", "2");
        docLinkMap.put("工伤调解申请书", "3");
        docLinkMap.put("工伤和解协议书", "4");
        docLinkMap.put("工伤劳动仲裁申请书", "5");
        docLinkMap.put("工伤认定行政复议申请书", "6");
        docLinkMap.put("工伤认定行政起诉状", "7");
        docLinkMap.put("工资欠条", "8");
        docLinkMap.put("克扣工资调解申请书", "9");
        docLinkMap.put("控告申请书", "10");
        docLinkMap.put("劳动人事争议仲裁申请书", "11");
        docLinkMap.put("民事上诉状", "12");
        docLinkMap.put("欠薪和解协议书", "13");
        docLinkMap.put("欠薪强制执行申请书", "14");
        docLinkMap.put("申诉申请书", "15");
        docLinkMap.put("通用调解协议书", "16");
        docLinkMap.put("违法调岗调解申请书", "17");
        docLinkMap.put("违法降薪调解申请书", "18");
        docLinkMap.put("违法降薪劳动仲裁申请书", "19");
        docLinkMap.put("未签合同双倍工资未支付劳动仲裁申请书", "20");
        docLinkMap.put("用人单位关于工伤的民事答辩状", "21");
        docLinkMap.put("证据材料目录", "22");
        docLinkMap.put("终止劳动关系和解协议书", "23");
    }

    @Override
    public List<LegalDocumentHelp> legalDocTemplate(String userId, String userAsk){
        initMap();
        List<String> docCategory = openAIService.askLegalDoc(userAsk);
        List<LegalDocumentHelp> legalDocLinkList = new ArrayList<>();
        for(String docName: docCategory){
            LinkOfLegalDoc docLink = linkOfLegalDocRepository.findById(Long.valueOf(docLinkMap.get(docName))).get();
            legalDocLinkList.add(legalDocHelpRepository.save(
                    LegalDocumentHelp.builder().userId(userId).docCategory(docName)
                            .userAsk(userAsk).docLink(docLink.getLink()).build()));
        }
        return legalDocLinkList;
    }
}
