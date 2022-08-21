package com.barclays.cardsystem.utility;

import java.time.LocalDateTime;

/**
 * Error Message
 * @author Akshata
 *
 */
public class ErrorInfo {
	private String errorMessage;
	private Integer errorCode;
	private LocalDateTime timestamp;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
