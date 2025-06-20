package io.stxkxs.webapp;

import com.fasterxml.jackson.core.type.TypeReference;

import io.stxkxs.execute.serialization.Mapper;
import io.stxkxs.execute.serialization.Template;
import io.stxkxs.webapp.stack.DeploymentStack;
import io.stxkxs.webapp.stack.DeploymentConf;
import io.stxkxs.model._main.Common;
import io.stxkxs.model._main.Hosted;
import io.stxkxs.model._main.common.Bare;
import lombok.SneakyThrows;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

import java.util.Map;

import static io.stxkxs.execute.serialization.Format.describe;
import static io.stxkxs.execute.serialization.Format.name;

public class Launch {

  @SneakyThrows
  public static void main(String[] args) {
    var app = new App();

    var conf = get(app);

    new DeploymentStack(
      app, conf.hosted(),
      StackProps.builder()
        .stackName(name(conf.hosted().common().id(), "webapp"))
        .env(Environment.builder()
          .account(conf.hosted().common().account())
          .region(conf.hosted().common().region())
          .build())
        .description(describe(conf.host().common(),
          String.format("%s %s webapp",
            conf.hosted().common().name(), conf.hosted().common().alias())))
        .tags(Common.Maps.from(conf.host().common().tags(), conf.hosted().common().tags()))
        .build());

    app.synth();
  }

  @SneakyThrows
  private static Hosted<Bare, DeploymentConf> get(App app) {
    var parsed = Template.parse(
      app, "conf.mustache",
      Map.ofEntries(
        Map.entry("hosted:ses:hosted:zone", app.getNode().getContext("hosted:ses:hosted:zone")),
        Map.entry("hosted:ses:email", app.getNode().getContext("hosted:ses:email"))));

    var type = new TypeReference<Hosted<Bare, DeploymentConf>>() {};
    return Mapper.get().readValue(parsed, type);
  }
}
