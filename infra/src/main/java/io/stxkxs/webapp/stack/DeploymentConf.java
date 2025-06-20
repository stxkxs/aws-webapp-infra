package io.stxkxs.webapp.stack;

import io.stxkxs.model._main.Common;
import io.stxkxs.model.aws.vpc.NetworkConf;
import io.stxkxs.webapp.stack.model.ApiConf;
import io.stxkxs.webapp.stack.model.AuthConf;
import io.stxkxs.webapp.stack.model.DbConf;
import io.stxkxs.webapp.stack.model.SesConf;

public record DeploymentConf(
  Common common,
  NetworkConf vpc,
  SesConf ses,
  DbConf db,
  AuthConf auth,
  ApiConf api
) {}
