package com.example.items;

abstract public class Item {
   public String name;
   public String discription;
   public Item(String name, String discription){
      this.name = name;
      this.discription = discription;
   }

   public String getDiscription() {
      return discription;
   }
   public String getName() {
      return name;
   }
}
