package io.stxkxs.webapp.stack.nested;

import io.stxkxs.execute.aws.dynamodb.DynamoDbConstruct;
import io.stxkxs.webapp.stack.model.DbConf;
import io.stxkxs.model._main.Common;
import lombok.Getter;
import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.NestedStack;
import software.amazon.awscdk.NestedStackProps;
import software.constructs.Construct;

import static io.stxkxs.execute.serialization.Format.*;


@Getter
public class DbNestedStack extends NestedStack {
  private final DynamoDbConstruct dynamoDbConstruct;

  public DbNestedStack(Construct scope, Common common, DbConf conf, NestedStackProps props) {
    super(scope, "webapp.db", props);

    this.dynamoDbConstruct = new DynamoDbConstruct(this, common, conf.user());

    CfnOutput.Builder
      .create(this, id(common.id(), "user.table.arn"))
      .exportName(exported(scope, "webappusertablearn"))
      .value(this.dynamoDbConstruct().table().getTableArn())
      .description(describe(common))
      .build();

    CfnOutput.Builder
      .create(this, id(common.id(), "user.table.id"))
      .exportName(exported(scope, "webappusertableid"))
      .value(this.dynamoDbConstruct().table().getTableId())
      .description(describe(common))
      .build();
  }
}
