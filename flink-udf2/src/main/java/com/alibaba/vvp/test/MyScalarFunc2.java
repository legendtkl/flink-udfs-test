package com.alibaba.vvp.test;

import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.catalog.DataTypeFactory;
import org.apache.flink.table.functions.ScalarFunction;
import com.alibaba.vvp.BaseUtil;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.inference.CallContext;
import org.apache.flink.table.types.inference.TypeInference;
import org.apache.flink.table.types.inference.TypeStrategy;

import java.util.Optional;


public class MyScalarFunc2 extends ScalarFunction {

    public String eval(String a) {
        BaseUtil util = new BaseUtil();
        return "VVP_TEST_" + a + util.convert("a");
    }

    public long eval(long a) {
        return a + 1000;
    }

    public int eval(int a) {
        return a + 1000;
    }

    @Override
    public TypeInference getTypeInference(DataTypeFactory typeFactory) {
        BaseUtil util = new BaseUtil();
        util.convert("a");
        return TypeInference.newBuilder()
                .typedArguments(DataTypes.STRING(), DataTypes.STRING())
                .outputTypeStrategy(callContext -> {
                    if (!callContext.isArgumentLiteral(1) || callContext.isArgumentNull(1)) {
                        throw callContext.newValidationError("Literal expected for second argument.");
                    }
                    final String literal = callContext.getArgumentValue(1, String.class).orElse("STRING");
                    switch (literal) {
                        case "INT":
                            return Optional.of(DataTypes.INT().notNull());
                        case "DOUBLE":
                            return Optional.of(DataTypes.DOUBLE().notNull());
                        case "STRING":
                        default:
                            return Optional.of(DataTypes.STRING());
                    }
                })
                .build();
    }
}
