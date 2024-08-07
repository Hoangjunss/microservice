package com.example.profile_hr_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum Error {
    //Client Error
    NOT_FOUND(404, "Resource not found", HttpStatus.NOT_FOUND), //Resource not found
    BAD_REQUEST(400, "Bad request", HttpStatus.BAD_REQUEST), //Syntax error or malformed request
    UNAUTHORIZED(401, "Unauthorized", HttpStatus.UNAUTHORIZED), // unauthenticated user
    FORBIDDEN(403, "Forbidden", HttpStatus.FORBIDDEN), //The user does not have permission to access the resource
    CONFLICT(409, "Conflict", HttpStatus.CONFLICT), // Resource state conflicts. For example, it can happen when trying to create a duplicate record or update data that is being edited at the same time by someone else.
    //Server Error
    UNCATEGORIZED_EXCEPTION(9999, "Unclassified error", HttpStatus.INTERNAL_SERVER_ERROR),
    //Database Error
    DATABASE_ACCESS_ERROR(9998, "Database access error", HttpStatus.INTERNAL_SERVER_ERROR),
    DUPLICATE_KEY(9996, "Duplicate key found", HttpStatus.CONFLICT),
    EMPTY_RESULT(9995, "No result found", HttpStatus.NOT_FOUND),
    NON_UNIQUE_RESULT(9994, "Non-unique result found", HttpStatus.CONFLICT),
    //Company-related errors
    COMPANY_NOT_FOUND(11001, "Company not found", HttpStatus.NOT_FOUND),
    COMPANY_ALREADY_EXISTS(11002, "Company already exists", HttpStatus.CONFLICT),
    COMPANY_UNABLE_TO_SAVE(11003, "Company unable to save", HttpStatus.INTERNAL_SERVER_ERROR),
    COMPANY_UNABLE_TO_UPDATE(11003, "Company unable to update", HttpStatus.INTERNAL_SERVER_ERROR),
    COMPANY_UNABLE_TO_DELETE(11003, "Company unable to delete", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    /**
     * Constructor for ErrorCode.
     *
     * @param code       the error code
     * @param message    the error message
     * @param statusCode the corresponding HTTP status code
     */
    Error(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

}