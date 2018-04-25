package com.develop.frame.network.exception;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.develop.frame.network.bean.ErrorBean;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.HttpException;

/**
 * @author Allen
 *         Created by Allen on 2017/10/23.
 *         异常类型
 */

public class ApiException extends Exception {

    private final int code;
    private ErrorBean errorBean;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        this.errorBean= new ErrorBean(throwable.getMessage());
    }

    public int getCode() {
        return code;
    }

    public ErrorBean getErrorBean() {
        return errorBean;
    }

    @Override
    public String getMessage(){
        return errorBean.getMessage();
    }

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;

            ex = new ApiException(httpException, httpException.code());

            try {
                String errorBody= httpException.response().errorBody().string();
                Gson gson = new Gson();
                ErrorBean errorBean = gson.fromJson(errorBody, ErrorBean.class);
                ex.errorBean= errorBean;
                ex.errorBean.setErrorCode(httpException.code());
            }catch (Exception e1){
                e1.printStackTrace();
            }
          //  ex.message = httpException.response().message();



            //TODO : 把 errorBody 中转成 Erorr 类。
           /* try {

                ex.message = httpException.response().errorBody().string();
                ex.message = httpException.response().message();
            } catch (IOException e1) {
                e1.printStackTrace();
                ex.message = e1.getMessage();
            }*/
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.errorBean.setMessage("网络连接超时，请检查您的网络状态，稍后重试！");
            ex.errorBean.setErrorCode(ERROR.TIMEOUT_ERROR);
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.errorBean.setMessage("网络连接异常，请检查您的网络状态，稍后重试！");
            ex.errorBean.setErrorCode(ERROR.TIMEOUT_ERROR);
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.errorBean.setMessage( "网络连接超时，请检查您的网络状态，稍后重试！");
            ex.errorBean.setErrorCode(ERROR.TIMEOUT_ERROR);
            return ex;
        } else if (e instanceof UnknownHostException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.errorBean.setMessage( "网络连接异常，请检查您的网络状态，稍后重试！");
            ex.errorBean.setErrorCode(ERROR.TIMEOUT_ERROR);
            return ex;
        } else if (e instanceof NullPointerException) {
            ex = new ApiException(e, ERROR.NULL_POINTER_EXCEPTION);
            ex.errorBean.setMessage( "空指针异常");
            ex.errorBean.setErrorCode(ERROR.NULL_POINTER_EXCEPTION);
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ApiException(e, ERROR.SSL_ERROR);
            ex.errorBean.setMessage(  "证书验证失败");
            ex.errorBean.setErrorCode(ERROR.SSL_ERROR);
            return ex;
        } else if (e instanceof ClassCastException) {
            ex = new ApiException(e, ERROR.CAST_ERROR);
            ex.errorBean.setMessage( "类型转换错误");
            ex.errorBean.setErrorCode(ERROR.CAST_ERROR);
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof JsonSyntaxException
                || e instanceof JsonSerializer
                || e instanceof NotSerializableException
                || e instanceof ParseException) {
            e.printStackTrace();
            ex = new ApiException(e, ERROR.PARSE_ERROR);
            ex.errorBean.setMessage( "解析错误");
            ex.errorBean.setErrorCode(ERROR.PARSE_ERROR);
            return ex;
        } else if (e instanceof IllegalStateException) {
            ex = new ApiException(e, ERROR.ILLEGAL_STATE_ERROR);
            ex.errorBean.setMessage( e.getMessage());
            ex.errorBean.setErrorCode(ERROR.ILLEGAL_STATE_ERROR);
            return ex;
        } else {
            e.printStackTrace();
            ex = new ApiException(e, ERROR.UNKNOWN);
            ex.errorBean.setMessage( "未知错误");
            ex.errorBean.setErrorCode(ERROR.UNKNOWN);
            return ex;
        }
    }

    /**
     * 约定异常
     */
    public static class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1001;
        /**
         * 空指针错误
         */
        public static final int NULL_POINTER_EXCEPTION = 1002;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1003;

        /**
         * 类转换错误
         */
        public static final int CAST_ERROR = 1004;

        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1005;

        /**
         * 非法数据异常
         */
        public static final int ILLEGAL_STATE_ERROR = 1006;

    }
}
