package com.cluster.tasktrackingtool.dto;

import org.hibernate.internal.build.AllowPrintStacktrace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MessageResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
  private String message;
}
