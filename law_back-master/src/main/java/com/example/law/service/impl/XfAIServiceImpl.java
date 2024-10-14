package com.example.law.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.example.law.component.XfXhStreamClient;
import com.example.law.config.XfXhConfig;
import com.example.law.listener.XfXhWebSocketListener;
import com.example.law.pojo.dto.MsgDTO;
import com.example.law.pojo.entity.LawQuestion;
import com.example.law.pojo.vo.common.BizException;
import com.example.law.pojo.vo.common.ExceptionEnum;
import com.example.law.service.AIService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
//@Service
public class XfAIServiceImpl implements AIService {

    private static final String lawQuestionUserPrompt1 = "[\"录用通知书与劳动合同不一致\",\"不符条件解除劳动合同\",\"协商解除劳动合同\",\"不能胜任工作解除劳动合同\",\"严重违反规章制度解除劳动合同\",\"合同到期签订无固定期限合同\",\"合同订立形式\",\"工伤认定\",\"小时工与非全日制工\",\"超出雇佣关系的工作行为\",\"劳务派遣法律关系的认定\",\"劳务派遣法律责任认定\",\"劳务派遣审查\",\"劳务外包与劳务派遣的区分\",\"不同“外包”法律责任的认定\",\"逆向派遣法律责任的认定\",\"劳务派遣与“三性特征”\",\"劳务派遣的经济补偿金\",\"不接受劳务派遣的经济补偿\",\"共享员工的劳动关系\",\"共享员工的工伤处理\",\"跨地区共享员工\",\"共享员工的工龄计算\",\"共享员工的工资支付\",\"共享员工的责任承担\",\"借调员工的合同变更、解除\",\"员工出借的协议履行\",\"非全日制员工的社会保险\",\"承诺的工资兑现\",\"员工索赔缺乏证据\",\"用人单位的用工自主权\",\"工资调整的依据\",\"13薪变浮动奖\",\"员工的虚假诉讼\",\"拖欠工资，留置车辆\",\"离职未交接，拒付工资\",\"离职是否还需发工资\",\"客户退款，员工的提成处理\",\"离职后不发工资的条款的效力\",\"公司“代签”工资产生争议\",\"履历造假得工资还是劳务费\",\"延长试用期的补工资差额\",\"假冒学历，要求员工退钱\",\"员工违纪，停发工资\",\"撤销解除劳动合同决定后工资补发\",\"“三包一靠”的工资索要\",\"业绩对赌与工资克扣\",\"企业破产，高管的报酬\",\"工资标准的认定\",\"报销款与工资\",\"疫情期间居家工作，拒绝提供正常劳动\",\"“一个工资支付周期”的返岗劳动者的工资发放\",\"疫情期间停工停产的工资待遇\",\"1953年劳动保险条例中病假工资的效力\",\"自行加班的加班费\",\"休息日培训的加班认定\",\"节假日加班\",\"一周六天工作，多出一天的加班认定\",\"工作带回家的加班认定\",\"双休日出差的加班认定\",\"出差中包含双休日的加班认定\",\"外派人员的双休日的加班认定\",\"休息日活动的加班认定\",\"在家待命的加班认定\",\"综合工时制的加班认定\",\"不定时工作制的加班认定\",\"中层以上人员的加班费\",\"特殊岗位的加班费认定\",\"补休代替加班费的认定\",\"拒绝加班与违纪\",\"因故无法加班与旷工认定\",\"因故不能加班与旷工的区别\",\"加班与值班的区别\",\"值班的认定\",\"节假日加班以补休代替加班费\",\"加班费的仲裁时效\",\"违法解除劳动合同的赔偿金\",\"违法缴纳社会保险费的救济\",\"加班工资的追诉期限\",\"劳动诉讼后，之前仲裁的效力认定\",\"销售提成与加班工资\",\"不定时工作制的加班工资认定\",\"房产中介员工的加班工资\",\"计件工资制的加班工资\",\"自行约定不定时工作制的效力\",\"周末开会的加班费认定\",\"节假日培训的加班工资认定\",\"延长假期抵扣休息日加班时间的认定\",\"违法超时加班的劳动合同解除\",\"放弃加班费协议的效力\",\"加班事实的认定\",\"包薪制的加班费认定\",\"未协商增加工作任务\",\"加班费争议的举证责任\",\"超时加班工伤的赔偿责任\",\"规章制度否认加班事实的效力\",\"劳动者在离职文件上的“加班费已结清”签字效力\",\"延时加班的调休与加班费认定\",\"哺乳时间折算加班费\",\"加班补休的申请效力\"]";
    private static final String lawQuestionUserPrompt2 = "你是一个法律顾问，分析我的问题，给出这个问题属于以上分类中的哪三类，按照匹配程度由高到低给出三个分类结果，例如\n" +
            "input:录用通知书与劳动合同不一致，以哪个为准?\n" +
            "output:[\"录用通知书与劳动合同不一致\",\"不符条件解除劳动合同\",\"协商解除劳动合同\"]\n";
    private static final String lawQuestionStaffPrompt = "你是一名中国劳动法律师，我将描述一个法律情况，你查找相关的法律条文和法律解释，提供详细的法律分析和解读。例如\n" +
            "input:劳动合同：录用通知书与劳动合同不一致，以哪个为准?\n" +
            "output:在招聘和录用员工的过程中，录用通知书与劳动合同起着不同的作用。录用通知书属于用人单位希望和员工建立劳动关系的要约，员工可以选择接受，也可以不接受。因此，在实际操作中，仅有一张书面通知不能完全构成合同关系的建立。只有在要约人即员工对录用通知书的内容作出承诺时，这一纸文件才能对双方都产生约束力。而劳动合同则是用人单位与劳动者意思自治的结果，一旦签订就必须全面履行。\n" +
            "劳动合同中的部分内容可以与录用通知书的内容一致，也能出现不一致的情况。录用通知书中通常已经包含了部分劳动合同约定的内容，如工作时间、地点、职位名称、薪酬福利等，与劳动关系建立后签订的劳动合同势必会有内容上的重叠。根据劳资双方合意的时间，劳动合同产生于录用通知书之后，劳动合同中约定的不同于录用通知书的内容，应当视为用人单位与员工\n" +
            "就同一问题作出的新约定。此时，劳动合同条款的效力高于录用通知书。那么,如果录用通知书中具备的内容没有在劳动合同中出现，该以哪个为准?这种情况下，不能完全依据协议形成时间来确定谁更具有效力，而是要看录用通知书在劳动合同签订后是否还有效。如果用人单位并未明确约定录用通知书的有效期，作为受民法保护具有要约承诺法律效力的协议来说，该部分内容在劳动合同签订后仍然有效。反之，如果用人单位在签订劳动合同之时书面说明自劳动合同签订之日起录用通知书自动失效，或者以劳动合同内容为准的，未在劳动\n" +
            "合同中体现的内容将不再具有法律效力。\n" +
            "input:孕期、产期以及哺乳期女职工在试用期内，能否以不符合录用条件解除劳动合同?\n" +
            "output:依据《劳动合同法》第39条规定，在孕期、产期以及哺乳期女职工存在过错的情况下用人单位可以单方解除劳动合同且无需支付经济补偿金，包括：\n" +
            "A.在试用期间被证明不符合录用条件的；\n" +
            "B.严重违反用人单位的规章制度的；\n" +
            "C.严重失职，营私舞弊，给用人单位造成重大损害的；\n" +
            "D.劳动者同时与其他用人单位建立劳动关系，对完成本单位的工作任务造成严重影响，或者经用人单位提出，拒不改正的；\n" +
            "E.因以欺诈、胁迫的手段或者乘人之危，使对方在违背真实意思的情况下订立或者变更劳动合同致使劳动合同无效的；\n" +
            "F.被依法追究刑事责任的。\n" +
            "虽然在孕期、产期以及哺乳期女职工存在过错的情况下，用人单位也可以依据《劳动合同法》第39条规定单方解除劳动合同且无需支付经济补偿金，但是用人单位需要承担非常严格的举证责任，被认定违法解除的风险非常高，因此协商一致解除劳动合同对用人单位来说应是一个优先考虑的解除方式。\n";

    private String getLawQuestionUserPrompt(String question) {
        return lawQuestionUserPrompt1 + lawQuestionUserPrompt2 + "input:" + question + "\n" + "output:";
    }

    private String getLawQuestionStaffPrompt(String question) {
        return lawQuestionStaffPrompt + "input:" + question + "\n" + "output:";
    }

    @Resource
    private XfXhStreamClient xfXhStreamClient;

    @Resource
    private XfXhConfig xfXhConfig;

    private String sendQuestion(String question) {
        // 获取连接令牌
        if (!xfXhStreamClient.operateToken(XfXhStreamClient.GET_TOKEN_STATUS)) {
            log.error("当前大模型连接数过多，请稍后再试");
            return null;
        }

        // 创建消息对象
        MsgDTO msgDTO = MsgDTO.createUserMsg(question);
        // 创建监听器
        XfXhWebSocketListener listener = new XfXhWebSocketListener();
        // 发送问题给大模型，生成 websocket 连接
        WebSocket webSocket = xfXhStreamClient.sendMsg(UUID.randomUUID().toString().substring(0, 10),
                Collections.singletonList(msgDTO), listener);
        if (webSocket == null) {
            // 归还令牌
            xfXhStreamClient.operateToken(XfXhStreamClient.BACK_TOKEN_STATUS);
            log.error("系统内部错误，请联系管理员");
            return null;
        }
        try {
            int count = 0;
            // 为了避免死循环，设置循环次数来定义超时时长
            int maxCount = xfXhConfig.getMaxResponseTime() * 5;
            while (count <= maxCount) {
                Thread.sleep(200);
                if (listener.isWsCloseFlag()) {
                    break;
                }
                count++;
            }
            if (count > maxCount) {
                log.error("大模型响应超时，请联系管理员");
                return null;
            }
            // 响应大模型的答案
            return listener.getAnswer().toString();
        } catch (InterruptedException e) {
            log.error("错误：" + e.getMessage());
            return null;
        } finally {
            // 关闭 websocket 连接
            webSocket.close(1000, "");
            // 归还令牌
            xfXhStreamClient.operateToken(XfXhStreamClient.BACK_TOKEN_STATUS);
        }
    }


    @Override
    public List<String> askLegalDoc(String conversationContent) {
        return null;
    }

    @Override
    public String askConversationWarning(String conversationContent) {
        return null;
    }

    @Override
    public String askContractWarning(String contrcatContent) {
        return null;
    }

    @Override
    public List<LawQuestion> askLawQuestionForUser(String question) {
        String prompt = getLawQuestionUserPrompt(question);
        String answer = sendQuestion(prompt);
        if (answer == null) {
            throw new BizException(ExceptionEnum.AI_ANSWER_ERROR);
        }
        List<String> aiTitleList = JSONArray.parseArray(answer, String.class);
        List<LawQuestion> lawQuestionList = new ArrayList<>();
        for (String aiTitle : aiTitleList) {
            lawQuestionList.add(LawQuestion.builder().aiTitle(aiTitle).build());
        }
        return lawQuestionList;
    }

    @Override
    public String askLawQuestionForStaff(String question) {
        return sendQuestion(getLawQuestionStaffPrompt(question));
    }
}
