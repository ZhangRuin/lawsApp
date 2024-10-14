package com.example.law.pojo.vo.common;

public enum ExceptionEnum implements BaseErrorInfoInterface {
    // 数据操作错误定义
    SUCCESS("2000", "成功"),

    STAFF_PASSWORD_ERROR("5001", "管理员密码错误"),
    NO_STAFF_ONLINE("5002", "没有工作人员在线"),
    STAFF_POLL_ERROR("5003", "工作人员轮询错误"),
    LAW_QUESTION_RECORD_NOT_FOUND("5004", "法律问题记录不存在"),
    PARAM_ERROR("5005", "参数错误"),
    AI_ANSWER_ERROR("5006", "ai回答异常"),
    FiLE_ERROR("5007", "获取文件字节数据异常"),
    JSON_DATA_ERROR("5008", "获取json数据异常"),
    NICKNAME_EXIST_ERROR("5009", "该用户昵称已取"),
    ISSUE_NOT_EXIST("5010", "ISSUE不存在"),
    NICKNAME_EXIST_SAME_ERROR("5011", "该nickname已存在"),
    EDIT_ISSUE_RIGHT_ERROR("5012", "用户只能编辑自己的issue"),
    COMMENT_NOT_EXIST("5013", "该comment不存在"),
    COMMENT_LIKED_NUMBER_IS_ZERO("5014", "点赞数已经为0"),
    SEARCH_RES_NOT_EXIST("5015", "未搜索到相应结果"),
    NOINFO_NEAR_ADDRESS("5016", "所在地址周围缺少渠道信息"),
    IMAGE_EXIST_ERROR("5017", "该用户头像已上传"),
    EMPTY_IMAGE("5018", "图片为空"),
    IMAGE_FORMAT_ERROR("5019", "图片格式有误"),
    SERVER_ERROR("5020", "图片上传失败，服务器出错"),
    WX_LOGIN_ERROR("5021", "微信登录异常"),
    DELETE_IMAGE_ERROR("5022", "删除图片失败");



    /**
     * 错误码
     */
    private final String resultCode;

    /**
     * 错误描述
     */
    private final String resultMsg;

    ExceptionEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public String getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }
}
