package com.kion.bunga.validators;

public class CNPValidator {

  private static final int[] controlList = new int[]{2,7,9,1,4,6,3,5,8,2,7,9};

  public boolean isValidPersonalCode(String value) {
    if (value == null || value.length() != 13){
      return false;
    }
    int sum = 0;
    for (int i = 0; i < 12; i++){
      char c = value.charAt(i);
      int x = c - '0';
      int mult = x * controlList[i];
      sum += mult;
    }

    int expectedControl = (sum % 11) < 10 ? (sum % 11) : 1;
    int actualControl = value.charAt(12) - '0';
    return expectedControl == actualControl;
  }
}
