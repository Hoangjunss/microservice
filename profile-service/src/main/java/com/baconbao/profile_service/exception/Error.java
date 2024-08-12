package com.baconbao.profile_service.exception;

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
    // MongoDB-related errors
    MONGO_CONNECTION_FAILURE(3001, "MongoDB connection failed", HttpStatus.INTERNAL_SERVER_ERROR),
    MONGO_DUPLICATE_KEY_ERROR(3002, "MongoDB duplicate key error", HttpStatus.CONFLICT),
    MONGO_VALIDATION_ERROR(3003, "MongoDB validation error", HttpStatus.BAD_REQUEST),
    MONGO_WRITE_CONCERN_ERROR(3004, "MongoDB write concern error", HttpStatus.INTERNAL_SERVER_ERROR),
    MONGO_TIMEOUT_ERROR(3005, "MongoDB operation timed out", HttpStatus.INTERNAL_SERVER_ERROR),
    MONGO_QUERY_EXECUTION_ERROR(3006, "MongoDB query execution error", HttpStatus.INTERNAL_SERVER_ERROR),
    MONGO_UNKNOWN_ERROR(3007, "Unknown MongoDB error", HttpStatus.INTERNAL_SERVER_ERROR),
    //Profile-related errors
    PROFILE_NOT_FOUND(2001, "Profile not found", HttpStatus.NOT_FOUND),
    PROFILE_ALREADY_EXISTS(2002, "Profile already exists", HttpStatus.CONFLICT),
    PROFILE_UNABLE_TO_SAVE(2003, "Unable to save profile", HttpStatus.INTERNAL_SERVER_ERROR),
    PROFILE_UNABLE_TO_UPDATE(2004, "Unable to update profile", HttpStatus.INTERNAL_SERVER_ERROR),
    PROFILE_UNABLE_TO_DELETE(2005, "Unable to delete profile", HttpStatus.INTERNAL_SERVER_ERROR),
    //Contact-related errors
    CONTACT_NOT_FOUND(6001, "Contact not found", HttpStatus.NOT_FOUND),
    CONTACT_ALREADY_EXISTS(6002, "Contact already exists", HttpStatus.CONFLICT),
    CONTACT_UNABLE_TO_SAVE(6003, "Unable to save contact", HttpStatus.INTERNAL_SERVER_ERROR),
    CONTACT_UNABLE_TO_UPDATE(6004, "Unable to update contact", HttpStatus.INTERNAL_SERVER_ERROR),
    CONTACT_UNABLE_TO_DELETE(6005, "Unable to delete contact", HttpStatus.INTERNAL_SERVER_ERROR),
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
