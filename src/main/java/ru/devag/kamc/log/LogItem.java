package ru.devag.kamc.log;

import ch.qos.logback.classic.spi.ILoggingEvent;

public class LogItem {
   public long timestamp;
   public String level;
   public String message;

   public LogItem(ILoggingEvent e) {
      this.timestamp = e.getTimeStamp();
      this.level = e.getLevel().toString();
      this.message = e.getFormattedMessage();
   }
}