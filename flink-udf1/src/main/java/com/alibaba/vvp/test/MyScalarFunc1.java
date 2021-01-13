package com.alibaba.vvp.test;

import org.apache.flink.table.functions.ScalarFunction;
import com.alibaba.vvp.BaseUtil;


public class MyScalarFunc1 extends ScalarFunction {

    public String eval(String a) {
        BaseUtil util = new BaseUtil();
        return "VVP_TEST_" + a + util.convert("a", "b");
    }

    public long eval(long a) {
        return a + 1000;
    }

    public int eval(int a) {
        return a + 1000;
    }
}
