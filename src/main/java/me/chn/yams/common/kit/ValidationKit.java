package me.chn.yams.common.kit;

import org.apache.commons.collections4.CollectionUtils;

import javax.validation.*;
import java.util.Set;

public class ValidationKit {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> void check(T entity) {
        Set<ConstraintViolation<T>> validateSet = validator.validate(entity);
        if (CollectionUtils.isNotEmpty(validateSet)) {
            throw new ConstraintViolationException(validateSet);
        }
    }

}
