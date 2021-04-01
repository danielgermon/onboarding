package com.thg.voyager.personalisation.controller;

import io.micronaut.context.annotation.Property;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

@Controller
public class ViewController {
  @Property(name = "page-title")
  private String title;

  @View("saas/index")
  @Get("/saas")
  public HttpResponse saasIndex() {
    return HttpResponse.ok(CollectionUtils.mapOf("title", title));
  }
  @View("creative/index")
  @Get("/creative")
  public HttpResponse creativeIndex() {
    return HttpResponse.ok(CollectionUtils.mapOf("title", title));
  }
  @View("modern/index")
  @Get("/modern")
  public HttpResponse modernIndex() {
    return HttpResponse.ok(CollectionUtils.mapOf("title", title));
  }
}
