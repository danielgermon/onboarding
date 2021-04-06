package com.thg.voyager.personalisation;

import io.micronaut.runtime.Micronaut;

public class Application {

  public static void main(String[] args) {
    Micronaut.build(args).defaultEnvironments("dev").start();
  }
}
