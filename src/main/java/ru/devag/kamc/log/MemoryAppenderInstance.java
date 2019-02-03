package ru.devag.kamc.log;

public final class MemoryAppenderInstance {

   private static MemoryAppender instance;

   private MemoryAppenderInstance() {
   }

   static void setInstance(MemoryAppender instance) {
       MemoryAppenderInstance.instance = instance;
   }

   public static MemoryAppender getInstance() {
       return instance;
   }

}