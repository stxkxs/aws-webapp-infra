package io.stxkxs.webapp.stack.model;

import io.stxkxs.model.aws.ses.Destination;
import io.stxkxs.model.aws.ses.IdentityConf;
import io.stxkxs.model.aws.ses.Receiving;

public record SesConf(
  IdentityConf identity,
  Receiving receiving,
  Destination destination
) {}
