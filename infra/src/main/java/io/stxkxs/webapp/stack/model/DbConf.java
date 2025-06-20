package io.stxkxs.webapp.stack.model;

import io.stxkxs.model.aws.dynamodb.Table;
import io.stxkxs.model.aws.fn.Lambda;

public record DbConf(
  String vpcName,
  Table user,
  Lambda listener
) {}
