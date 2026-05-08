package com.vicentefirtline.rest_with_spring_boot_and_java_erudio.exception;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {}
