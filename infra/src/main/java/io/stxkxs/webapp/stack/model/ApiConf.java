package io.stxkxs.webapp.stack.model;

import io.stxkxs.model.aws.cognito.client.Authorizer;

public record ApiConf(
  io.stxkxs.model.aws.apigw.ApiConf apigw,
  String resource,
  Authorizer authorizer
) {}
