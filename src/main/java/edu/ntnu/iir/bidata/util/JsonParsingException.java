package edu.ntnu.iir.bidata.util;

/**
 * Custom exception to signal errors during JSON parsing.
 */
public class JsonParsingException extends RuntimeException {
  /**
   * Constructs a new JsonParsingException with the specified detail message and cause.
   * @param message The detail message.
   * @param cause The cause of the exception.
   */
  public JsonParsingException(String message, Throwable cause) {
    super(message, cause);
  }
}
