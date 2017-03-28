/*
 * Copyright [2016] [zhangsong <songm.cn>].
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package cn.songm.common.service;

/**
 * 业务异常类
 *
 * @author zhangsong
 * 
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = 5118981894942473582L;

    /** 系统错误内部描述(给开发者看的) */
    private String errDesc;
    /** 人机交互错误通知(给使用者看的) */
    private String errNotice;
    /** 错误信息码 */
    private String errCode;

    public String getErrCode() {
        return errCode;
    }

    public String getErrDesc() {
        return errDesc;
    }

    public String getErrNotice() {
        return errNotice;
    }

    public ServiceException(String errCode, String errDesc) {
        super(errCode + ": " + errDesc);
        this.errCode = errCode;
        this.errDesc = errDesc;
        this.errNotice = errDesc;
    }

    public ServiceException(String errCode, String errDesc,
            String errNotice) {
        this(errCode, errDesc);
        this.errNotice = errNotice;
    }

    public ServiceException(String errCode, String errDesc, Throwable e) {
        super(errCode + ": " + errDesc, e);
        this.errCode = errCode;
        this.errDesc = errDesc;
        this.errNotice = errDesc;
    }

    public ServiceException(String errCode, String errDesc, String errNotice,
            Throwable e) {
        this(errCode, errDesc, e);
        this.errNotice = errNotice;
    }

//    public ServiceException(final String eCode, String errDesc,
//            String errNotice, Throwable e) {
//        this(new ErrorInfo() {
//            
//            private static final long serialVersionUID = 5823526915490870978L;
//
//            @Override
//            public String getErrCode() {
//                return eCode;
//            }
//        }, errDesc, e);
//        this.errNotice = errNotice;
//    }

}
