package cn.songm.common.service;

/**
 * 通用错误码（与具体的业务无关）
 * 
 * @author zhangsong
 *
 */
public enum GeneralErr implements ErrorInfo {
    /** 未知异常 */
    UNKNOW("GEN_001"),
    /** 参数输入有误 */
    ARGUMENT("GEN_002"),
    /** HTTP设置异常 */
    HTTP_ERROR("GEN_003"),
    /** 网络连接异常 */
    NETWORK_ERR("GEN_004"),
    ;

    private final String errCode;

    public String getErrCode() {
        return errCode;
    }

    private GeneralErr(String errCode) {
        this.errCode = errCode;
    }

    public GeneralErr getInstance(String errCode) {
        for (GeneralErr g : GeneralErr.values()) {
            if (g.getErrCode().equals(errCode)) {
                return g;
            }
        }
        return null;
    }

}
