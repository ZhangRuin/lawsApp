package com.example.law.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.example.law.component.openai.ChatMessage;
import com.example.law.component.openai.OpenAI;
import com.example.law.component.openai.Request;
import com.example.law.pojo.entity.LawQuestion;
import com.example.law.pojo.vo.common.BizException;
import com.example.law.pojo.vo.common.ExceptionEnum;
import com.example.law.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OpenAIServiceImpl implements AIService {
    @Autowired
    private OpenAI openAI;
    @Value("${openai.model}")
    private String model;

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

    private static final String contractWarningPrompt = "现在你是一名劳动法律师，请审核下面这份合同并找出可能存在风险的条款\n";

    private static final String conversationWarningPrompt = "现在你是一名劳动法律师，我是劳动者，下面有一份我与雇主的劳资谈判的对话记录，请找出我可能遇到的风险。若对话内容过少或者理解不了，请返回“抱歉，语音内容过少或录入不清晰，我无法理解您的意思。请提供更多细节，确保录音环境良好”";

    private static final String legalDocCategory = "法律援助授权委托书:\n" +
            "这是一份委托书，用于授权律师代表申请人进行法律援助事务。申请人通过这份文件授权律师代表他们进行法律程序，包括代理起诉、应诉、申请财产保全等。\n" +
            "复核申请书:\n" +
            "复核申请书用于向上级劳动争议仲裁机构提出复核请求，即对基层劳动争议仲裁机构做出的仲裁裁决提出复核的申请。申请人可通过这份文件要求上级机构重新审查仲裁结果。\n" +
            "工伤调解申请书:\n" +
            "用于向劳动争议调解委员会提出工伤调解申请，希望通过调解解决工伤纠纷。申请人可以通过这份文件请求用人单位和劳动者协商达成工伤纠纷的和解。\n" +
            "工伤和解协议书:\n" +
            "工伤和解协议书是用人单位和工伤员工在工伤纠纷中达成和解时签署的协议书，约定双方和解的具体内容、条件和权利义务，以结束工伤争议。\n" +
            "工伤劳动仲裁申请书:\n" +
            "用于向劳动争议仲裁委员会提出工伤劳动仲裁申请，即对工伤纠纷向有关部门提出劳动仲裁请求。申请人可以通过这份文件要求劳动仲裁机构对工伤争议进行裁决。\n" +
            "工伤认定行政复议申请书:\n" +
            "用于向劳动行政部门提出工伤认定行政复议的申请。当工伤认定结果与事实不符时，申请人可通过这份文件请求行政部门重新审查工伤认定结果。\n" +
            "工伤认定行政起诉状:\n" +
            "用于向人民法院提起工伤认定行政诉讼的起诉状，即对行政部门作出的工伤认定决定提起法律诉讼。申请人可以通过这份文件要求法院重新审查工伤认定结果。\n" +
            "工资欠条:\n" +
            "工资欠条是用于记录债务人欠付劳动者工资的书面凭证，包括欠薪数额、还款方式、期限等内容，用于确保债务人按时偿还欠薪。\n" +
            "克扣工资调解申请书:\n" +
            "用于向劳动争议调解委员会提出克扣工资调解申请，即请求调解解决用人单位克扣工资的劳动争议。申请人可以通过这份文件请求调解委员会介入解决克扣工资问题。\n" +
            "控告申请书:\n" +
            "控告申请书用于向有关部门提出控告申请，即对涉及劳动权益的违法行为或侵权行为向司法机关申请控告和维护自身合法权益。\n" +
            "劳动人事争议仲裁申请书:\n" +
            "用于向劳动争议仲裁委员会提出劳动人事争议仲裁申请，即请求劳动仲裁机构对劳动纠纷进行仲裁裁决。申请人可以通过这份文件请求劳动仲裁机构对劳动争议进行裁决。\n" +
            "民事上诉状:\n" +
            "民事上诉状用于向法院提起上诉，即对下级法院作出的民事裁决结果提起上诉。申请人可以通过这份文件请求上级法院对民事裁决进行复审。\n" +
            "欠薪和解协议书:\n" +
            "欠薪和解协议书是用人单位和劳动者在欠薪纠纷中达成和解时签署的协议书，约定双方和解的具体内容、条件和权利义务，以结束欠薪争议。\n" +
            "欠薪强制执行申请书:\n" +
            "用于向人民法院提出欠薪强制执行申请，即请求法院强制用人单位支付欠薪。申请人可以通过这份文件申请法院对欠薪情况进行强制执行。\n" +
            "申诉申请书:\n" +
            "申诉申请书用于向上级法院提出申诉请求，即对下级法院作出的判决结果提出申诉。申请人可以通过这份文件请求上级法院对判决结果进行复审。\n" +
            "通用调解协议书:\n" +
            "通用调解协议书用于记录当事双方达成的调解协议，约定双方达成和解的具体内容、条件和权利义务，并在调解结束时签署的书面协议。\n" +
            "违法调岗调解申请书:\n" +
            "用于向劳动争议调解委员会提出违法调岗调解申请，即请求调解解决用人单位非法调整劳动者工作岗位的劳动争议。申请人可以通过这份文件请求调解委员会介入解决违法调岗问题。\n" +
            "违法降薪调解申请书:\n" +
            "用于向劳动争议调解委员会提出违法降薪调解申请，即请求调解解决用人单位非法降低劳动者工资待遇的劳动争议。申请人可以通过这份文件请求调解委员会介入解决违法降薪问题。\n" +
            "违法降薪劳动仲裁申请书:\n" +
            "用于向劳动争议仲裁委员会提出违法降薪劳动仲裁申请，即请求劳动仲裁机构对非法降低劳动者工资待遇的纠纷进行仲裁裁决。申请人可以通过这份文件请求劳动仲裁机构对违法降薪问题进行裁决。\n" +
            "未签合同双倍工资未支付劳动仲裁申请书:\n" +
            "用于向劳动争议仲裁委员会提出未签合同双倍工资未支付劳动仲裁申请，即请求劳动仲裁机构对未签订劳动合同且未支付双倍工资的情况进行仲裁裁决。申请人可以通过这份文件请求劳动仲裁机构对未签合同双倍工资问题进行裁决。\n" +
            "用人单位关于工伤的民事答辩状:\n" +
            "用人单位关于工伤的民事答辩状是用人单位在工伤纠纷案件中向法院提交的答辩状，用于回应工伤员工提起的民事诉讼请求，阐明用人单位的相关主张和事实陈述。\n" +
            "证据材料目录:\n" +
            "证据材料目录用于整理和归档与劳动纠纷案件相关的证据材料清单，包括证据名称、来源、数量等内容，用于法律程序中的证据提交和审查。\n" +
            "终止劳动关系和解协议书:\n" +
            "终止劳动关系和解协议书是用人单位和劳动者在终止劳动关系过程中达成和解时签署的协议书，约定双方和解的具体内容、条件和权利义务，以结束劳动关系纠纷。\n";

    private static final String legalDocPrompt = "你是一名法律顾问，分析一位劳动者的问题，给出这个问题属于以上文书分类中的哪三类，按照匹配程度由高到低给出三个分类结果，例如\n" +
            "input:我的工资支付延迟、工资低于法定最低标准、加班工资未按规定支付。\n" +
            "output:[\"工资欠条\",\"劳动人事争议仲裁申请书\",\"违法降薪调解申请书\"]\n";

    private Request getLawQuestionUserPrompt(String question) {
        return Request.builder().model(model)
                .messages(Arrays.asList(ChatMessage.builder().role("user")
                        .content(lawQuestionUserPrompt1 + lawQuestionUserPrompt2 + "input:" + question + "\n" + "output:").build())).build();
    }

    private Request getLawQuestionStaffPrompt(String question) {
        return Request.builder().model(model)
                .messages(Arrays.asList(ChatMessage.builder().role("user")
                        .content(lawQuestionStaffPrompt + "input:" + question + "\n" + "output:").build())).build();
    }

    private Request getContractWarningPrompt(String contrcatContent) {
        return Request.builder().model(model)
                .messages(Arrays.asList(ChatMessage.builder().role("user")
                        .content(contractWarningPrompt + contrcatContent ).build())).build();
    }

    private Request getConversationWarningPrompt(String conversationContent) {
        return Request.builder().model(model)
                .messages(Arrays.asList(ChatMessage.builder().role("user")
                        .content(conversationWarningPrompt +  conversationContent ).build())).build();
    }

    private Request getLegalDocPrompt(String userAsk) {
        return Request.builder().model(model)
                .messages(Arrays.asList(ChatMessage.builder().role("user")
                        .content(legalDocCategory +  legalDocPrompt +  "input:" + userAsk + "\n" + "output:")
                        .build())).build();
    }

    @Override
    public List<String> askLegalDoc(String userAsk) {
        String answer = openAI.askGPT(getLegalDocPrompt(userAsk));
        if (answer == null) {
            throw new BizException(ExceptionEnum.AI_ANSWER_ERROR);
        }
        return JSONArray.parseArray(answer, String.class);
    }

    @Override
    public String askConversationWarning(String conversationContent){
        String answer = openAI.askGPT(getConversationWarningPrompt(conversationContent));
        if (answer == null) {
            throw new BizException(ExceptionEnum.AI_ANSWER_ERROR);
        }
        return answer;
    }

    @Override
    public String askContractWarning(String contrcatContent){
        String answer = openAI.askGPT(getContractWarningPrompt(contrcatContent));
        if (answer == null) {
            throw new BizException(ExceptionEnum.AI_ANSWER_ERROR);
        }
        return answer;
    }

    @Override
    public List<LawQuestion> askLawQuestionForUser(String question) {
        String answer = openAI.askGPT(getLawQuestionUserPrompt(question));
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
        return openAI.askGPT(getLawQuestionStaffPrompt(question));
    }
}
