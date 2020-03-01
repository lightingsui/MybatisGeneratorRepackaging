package com.lighting.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enable the swaggerUI annotation, which, when annotated, can be
 * annotated on the pojo using {@link io.swagger.annotations.ApiModelProperty}, with
 * the information sourced from the annotation in the database
 *
 * @author ：Lightingsui
 * @since ：2020/2/28 22:08
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableSwaggerComments {
}
