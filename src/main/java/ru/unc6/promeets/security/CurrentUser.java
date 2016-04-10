package ru.unc6.promeets.security;

import java.lang.annotation.*;

/**
 * Created by Vladimir on 10.04.2016.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {}