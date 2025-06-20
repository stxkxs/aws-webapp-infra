package io.stxkxs.webapp.stack.nested;

import io.stxkxs.execute.aws.cognito.UserPoolClientConstruct;
import io.stxkxs.execute.aws.cognito.UserPoolConstruct;
import io.stxkxs.webapp.stack.model.AuthConf;
import io.stxkxs.model._main.Common;
import lombok.Getter;
import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.NestedStack;
import software.amazon.awscdk.NestedStackProps;
import software.amazon.awscdk.services.ec2.Vpc;
import software.constructs.Construct;

import static io.stxkxs.execute.serialization.Format.*;

@Getter
public class AuthNestedStack extends NestedStack {
  private final UserPoolConstruct userPoolConstruct;
  private final UserPoolClientConstruct userPoolClientConstruct;

  public AuthNestedStack(Construct scope, Common common, AuthConf conf, Vpc vpc, NestedStackProps props) {
    super(scope, "webapp.auth", props);

    this.userPoolConstruct = new UserPoolConstruct(this, common, conf.userPool(), vpc);
    this.userPoolClientConstruct = new UserPoolClientConstruct(this, common, conf.userPoolClient(), this.userPoolConstruct().userPool());

    CfnOutput.Builder
      .create(this, id(common.id(), "userpool.arn"))
      .exportName(exported(scope, "webappuserpoolarn"))
      .value(this.userPoolConstruct().userPool().getUserPoolArn())
      .description(describe(common, "user pool arn"))
      .build();

    CfnOutput.Builder
      .create(this, id(common.id(), "userpool.id"))
      .exportName(exported(scope, "webappuserpoolid"))
      .value(this.userPoolConstruct().userPool().getUserPoolId())
      .description(describe(common, "user pool id"))
      .build();

    CfnOutput.Builder
      .create(this, id(common.id(), "userpool.client.id"))
      .exportName(exported(scope, "webappuserpoolclientid"))
      .value(this.userPoolClientConstruct().userPoolClient().getUserPoolClientId())
      .description(describe(common, "user pool client id"))
      .build();
  }
}
