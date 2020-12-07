package ru.devag.kamc.prclcost;

import ru.devag.kamc.CellIndex;

public class PrclCostItem {
   @CellIndex("Кадастровый номер участка")
   String cadnum;
   
   @CellIndex("Кадастровая стоимость по состоянию на 01.01.2020, руб.")
   String cadcost;

   public String getCadnum() {
      return cadnum;
   }

   public void setCadnum(String cadnum) {
      this.cadnum = cadnum;
   }

   public String getCadcost() {
      return cadcost;
   }

   public void setCadcost(String cadcost) {
      this.cadcost = cadcost;
   }
}

