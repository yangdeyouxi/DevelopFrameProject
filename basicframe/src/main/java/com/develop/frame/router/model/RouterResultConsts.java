package com.develop.frame.router.model;

/**
 * Created by yangjiahuan on 2017/11/1.
 */

public class RouterResultConsts {

    public class RouterWrong {
        public static final String ROUTER_PATH_WRONG = "router_path_not_exit";
        public static final String ROUTER_PARAMETER_ERROR = "router_parameter_wrong";
        public static final String ROUTER_ERROR = "router_error";

        public static final String ROUTER_CONTEXT_NULL = "router_context_null";
        public static final String ROUTER_REUQEST_CODE_NULL = "router_request_code_null";

        public static final String ROUTER_CALL_ITERATORD = "router_call_iteratored";
    }

    public class RouterData{
        public static final String ROUTER_ACTIVITY_BUNDLE = "router_activity_bundle";
        public static final String ROUTER_FRAGMENT_BUNDLE = "router_fragment_bundle";
    }

    public class RouterDefault{
        public static final int ACTIVITY_REQUEST_CODE = -100;
    }

}
