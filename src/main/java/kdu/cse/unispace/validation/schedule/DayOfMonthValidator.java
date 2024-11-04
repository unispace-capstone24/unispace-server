package kdu.cse.unispace.validation.schedule;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class DayOfMonthValidator implements ConstraintValidator<ValidDayOfMonth, Integer[]> {

    @Override
    public void initialize(ValidDayOfMonth constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer[] days, ConstraintValidatorContext context) {
        if (days == null) {
            return true;
        }
        return Arrays.stream(days).allMatch(day -> day >= 1 && day <= 31);
    }
}
